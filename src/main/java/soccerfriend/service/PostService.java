package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
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
@RequiredArgsConstructor
public class PostService {

    private final PostMapper mapper;
    private final MemberService memberService;
    private final BulletinService bulletinService;
    private final ClubMemberService clubMemberService;
    private final RedisTemplate redisTemplate;
    public static final String RECENTLY_POST = "recentlyPost";

    public void create(int bulletinId, int memberId, Post post) {
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

        Post newPost = Post.builder()
                           .bulletinId(bulletinId)
                           .writer(memberId)
                           .title(post.getTitle())
                           .content(post.getContent())
                           .views(0)
                           .build();

        mapper.insert(newPost);
        deletePostPageCache(bulletinId);
        redisTemplate.opsForValue().set(RECENTLY_POST + " " + memberId, memberId, Duration.ofMinutes(1));
    }

    @Cacheable(value = "POST", key = "#id")
    public Post getPostById(int id) {
        Post post = mapper.getPostById(id);
        if (post == null) {
            throw new BadRequestException(POST_NOT_EXIST);
        }

        return post;
    }

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

    public int getPostCountFromBulletin(int bulletinId) {
        return mapper.getPostCountFromBulletin(bulletinId);
    }

    public void deletePostPageCache(int bulletinId) {
        Bulletin bulletin = bulletinService.getBulletinById(bulletinId);
        if (bulletin == null) {
            throw new BadRequestException(BULLETIN_NOT_EXIST);
        }

        int numPost = getPostCountFromBulletin(bulletinId);
        for (int i = 1; i < (numPost / 10) + 1; i++) {
            redisTemplate.delete("POSTPAGE::" + bulletinId + " " + i);
        }
    }
}
