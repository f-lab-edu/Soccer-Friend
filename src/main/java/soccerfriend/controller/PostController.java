package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import soccerfriend.authentication.BulletinWriteAuth;
import soccerfriend.authentication.NotRecentlyPost;
import soccerfriend.dto.Comment;
import soccerfriend.dto.Comment.ContentInput;
import soccerfriend.dto.Post;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.CommentService;
import soccerfriend.service.LoginService;
import soccerfriend.service.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static soccerfriend.exception.ExceptionInfo.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final LoginService loginService;
    private final CommentService commentService;

    /**
     * 특정 게시판에 게시물을 생성합니다.
     *
     * @param bulletinId 게시판의 id
     * @param post       게시물 정보
     */
    @PostMapping("/bulletin/{bulletinId}")
    @BulletinWriteAuth
    @NotRecentlyPost
    public void create(@PathVariable int bulletinId, @Validated @RequestBody Post post) {
        int memberId = loginService.getMemberId();
        postService.create(bulletinId, memberId, post);
    }

    /**
     * 특정 게시판에 존재하는 게시물을 조회합니다.
     *
     * @param bulletinId 게시판의 id
     * @param id         게시물의 id
     * @return 특정 id의 게시물
     */
    @GetMapping("/bulletin/{bulletinId}/{id}")
    @BulletinWriteAuth
    public Post readPost(@PathVariable int bulletinId, @PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
        int memberId = loginService.getMemberId();

        return postService.readPost(memberId, id, req, res);
    }

    /**
     * 특정 페이지의 게시물들을 조회합니다. 한 페이지에 10개의 게시물이 존재합니다.
     *
     * @param bulletinId 게시판의 id
     * @param page       페이지 정보
     * @return 특정 페이지의 게시물들
     */
    @GetMapping("/bulletin/{bulletinId}/page/{page}")
    @BulletinWriteAuth
    public List<Post> getPostByPage(@PathVariable int bulletinId, @PathVariable int page) {
        return postService.getPostByBulletinPage(bulletinId, page);
    }

    @PostMapping("/bulletin/{bulletinId}/{id}/comment")
    @BulletinWriteAuth
    public void writeComment(@PathVariable int bulletinId,
                             @PathVariable int id,
                             @RequestBody ContentInput content) {
        int memberId = loginService.getMemberId();

        commentService.create(id, memberId, content.getContent());
    }

    @DeleteMapping("/bulletin/{bulletinId}/comment/{commentId}")
    @BulletinWriteAuth
    public void deleteComment(@PathVariable int bulletinId, @PathVariable int commentId) {
        Comment comment = commentService.getCommentById(commentId);
        if (comment == null) {
            throw new BadRequestException(COMMENT_NOT_EXIST);
        }

        int memberId = loginService.getMemberId();
        if (memberId != comment.getWriter()) {
            throw new NoPermissionException(NO_COMMENT_PERMISSION);
        }

        commentService.delete(commentId);
    }

    @GetMapping("/bulletin/{bulletinId}/{id}/comments")
    @BulletinWriteAuth
    public List<Comment> getComments(@PathVariable int bulletinId, @PathVariable int id) {
        return commentService.getCommentsByPostId(id);
    }
}
