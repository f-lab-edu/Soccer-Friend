package soccerfriend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import soccerfriend.dto.Bulletin;
import soccerfriend.dto.Member;
import soccerfriend.dto.Post;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.mapper.PostMapper;

import java.time.Duration;
import java.util.List;

import static soccerfriend.exception.ExceptionInfo.*;

@Service
public class PostService {

    private final PostMapper mapper;
    private final MemberService memberService;
    private final BulletinService bulletinService;
    private final ClubMemberService clubMemberService;
    private final RedisTemplate redisTemplate;
    private final PostImageService postImageService;
    private final int imageAmount;
    public static final String RECENTLY_POST = "recentlyPost";

    public PostService(PostMapper mapper, MemberService memberService,
                       BulletinService bulletinService, ClubMemberService clubMemberService,
                       RedisTemplate redisTemplate, PostImageService postImageService,
                       @Value("${post.image.amount}") int imageAmount) {
        this.mapper = mapper;
        this.memberService = memberService;
        this.bulletinService = bulletinService;
        this.clubMemberService = clubMemberService;
        this.redisTemplate = redisTemplate;
        this.postImageService = postImageService;
        this.imageAmount = imageAmount;
    }

    /**
     * 게시판에 게시물을 작성합니다.
     *
     * @param bulletinId 게시판의 id
     * @param memberId   member의 id
     * @param post       게시물 정보
     * @param images     첨부된 이미지
     */
    @Transactional
    public void create(int bulletinId, int memberId, Post post, List<MultipartFile> images) {
        if (post == null) {
            throw new BadRequestException(POST_NOT_EXIST);
        }

        Bulletin bulletin = bulletinService.getBulletinById(bulletinId);
        Member member = memberService.getMemberById(memberId);
        if (bulletin == null) {
            throw new BadRequestException(BULLETIN_NOT_EXIST);
        }
        if (member == null) {
            throw new BadRequestException(MEMBER_NOT_EXIST);
        }

        int clubId = bulletin.getClubId();
        if (!clubMemberService.isClubMember(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        if (images.size() >= imageAmount) {
            throw new BadRequestException(TOO_MUCH_FILES);
        }

        Post newPost = Post.builder()
                           .bulletinId(bulletinId)
                           .writer(memberId)
                           .title(post.getTitle())
                           .content(post.getContent())
                           .views(0)
                           .build();

        int postId = mapper.insert(newPost);

        if (!images.isEmpty()) {
            postImageService.uploadImage(postId, images);
        }
    }

    /**
     * 특정 id의 게시판을 반환합니다.
     *
     * @param id 게시물의 id
     * @return 특정 id의 게시물
     */
    @Cacheable(value = "POST", key = "#id")
    public Post getPostById(int id) {
        Post post = mapper.getPostById(id);
        if (post == null) {
            throw new BadRequestException(POST_NOT_EXIST);
        }

        return post;
    }

    /**
     * 특정 페이지의 게시물들을 조회합니다. 한 페이지에 10개의 게시물이 존재합니다.
     *
     * @param bulletinId 게시판의 id
     * @param page       페이지 정보
     * @return 특정 페이지의 게시물들
     */
    @Cacheable(value = "POSTPAGE", key = "#bulletinId +' '+ #page")
    public List<Post> getPostByBulletinPage(int bulletinId, int page) {
        Bulletin bulletin = bulletinService.getBulletinById(bulletinId);
        if (bulletin == null) {
            throw new BadRequestException(BULLETIN_NOT_EXIST);
        }
        if (page < 1) {
            throw new BadRequestException(POST_PAGE_NOT_EXIST);
        }

        int numPost = getPostCountFromBulletin(bulletinId);
        if (page > (numPost / 10) + 1) {
            throw new BadRequestException(POST_PAGE_NOT_EXIST);
        }

        int start = (page - 1) * 10;
        List<Post> post = mapper.getPostByBulletinPage(bulletinId, start);

        if (post.isEmpty()) {
            throw new BadRequestException(BULLETIN_NOT_EXIST);
        }

        return post;
    }

    /**
     * 해당 게시판에 게시물 수를 반환합니다.
     *
     * @param bulletinId 게시판의 id
     * @return 게시판에 존재하는 게시물 수
     */
    public int getPostCountFromBulletin(int bulletinId) {
        return mapper.getPostCountFromBulletin(bulletinId);
    }

    /**
     * 특정 id의 post를 반환합니다. 이 때 post를 읽으면 조회수를 증가시킵니다.
     *
     * @param memberId 게시물을 조회하는 member의 id
     * @param id       게시물의 id
     * @return 특정 id의 게시물
     */
    @Cacheable(value = "POST", key = "#id")
    public Post readPost(int memberId, int id) {
        Member member = memberService.getMemberById(memberId);
        if (member == null) {
            throw new BadRequestException(MEMBER_NOT_EXIST);
        }
        Post post = getPostById(id);
        if (post == null) {
            throw new BadRequestException(POST_NOT_EXIST);
        }

        if (!postViewCheck(memberId, id)) {
            increaseViews(id);
        }

        redisTemplate.opsForValue().set("POST " + id + " MEMBER " + memberId, 1, Duration.ofDays(1));

        return post;
    }

    /**
     * 해당 게시물을 24시간 이내에 읽었는지 확인합니다.
     *
     * @param id 게시물의 id
     * @return 게시물을 24시간 이내에 읽었는지 여부
     */
    public boolean postViewCheck(int memberId, int id) {
        String str = (String) redisTemplate.opsForValue().get("POST " + id + " MEMBER " + memberId);
        if (str == null) {
            return false;
        }
        return true;
    }

    /**
     * 특정 id의 게시물을 1 증가시킵니다.
     *
     * @param id 게시물의 id
     */
    public void increaseViews(int id) {
        Post post = getPostById(id);
        if (post == null) {
            throw new BadRequestException(POST_NOT_EXIST);
        }

        mapper.increaseViews(id);
    }
}
