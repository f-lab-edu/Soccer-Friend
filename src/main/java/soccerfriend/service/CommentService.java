package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.Comment;
import soccerfriend.dto.Member;
import soccerfriend.dto.Post;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.mapper.CommentMapper;

import java.util.List;

import static soccerfriend.exception.ExceptionInfo.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostService postService;
    private final MemberService memberService;
    private final CommentMapper mapper;

    /**
     * 댓글을 생성합니다.
     *
     * @param postId  게시물의 id
     * @param writer  작성자의 id
     * @param content 댓글 내용
     */
    public void create(int postId, int writer, String content) {
        Post post = postService.getPostById(postId);
        if (post == null) {
            throw new BadRequestException(POST_NOT_EXIST);
        }

        Member member = memberService.getMemberById(writer);
        if (member == null) {
            throw new BadRequestException(MEMBER_NOT_EXIST);
        }

        Comment comment = Comment.builder()
                                 .postId(postId)
                                 .writer(writer)
                                 .content(content)
                                 .build();

        mapper.insert(comment);
    }

    /**
     * 댓글을 삭제합니다.
     *
     * @param id 댓글의 id
     */
    public void delete(int id) {
        Comment comment = getCommentById(id);
        if (comment == null) {
            throw new BadRequestException(COMMENT_NOT_EXIST);
        }

        mapper.delete(id);
    }

    /**
     * 댓글을 영구적으로 삭제합니다.
     *
     * @param id 댓글의 id
     */
    public void deletePermanently(int id) {
        if (!isCommentExistIncludeDeleted(id)) {
            throw new BadRequestException(COMMENT_NOT_EXIST);
        }

        mapper.deletePermanently(id);
    }

    /**
     * 특정 id의 댓글을 반환합니다.
     *
     * @param id 댓글의 id
     * @return 특정 id의 댓글
     */
    public Comment getCommentById(int id) {
        Comment comment = mapper.getCommentById(id);
        if (comment == null) {
            throw new BadRequestException(COMMENT_NOT_EXIST);
        }

        return comment;
    }

    /**
     * 특정 게시물에 존재하는 모든 댓글들을 반환합니다.
     *
     * @param postId 게시물의 id
     * @return 특정 게시물에 존재하는 모든 댓글들
     */
    public List<Comment> getCommentsByPostId(int postId) {
        Post post = postService.getPostById(postId);
        if (post == null) {
            throw new BadRequestException(POST_NOT_EXIST);
        }

        List<Comment> comments = mapper.getCommentsByPostId(postId);
        if (comments.isEmpty()) {
            throw new BadRequestException(COMMENT_NOT_EXIST);
        }

        return comments;
    }

    public boolean isCommentExistIncludeDeleted(int id) {
        return mapper.isCommentExistIncludeDeleted(id);
    }
}
