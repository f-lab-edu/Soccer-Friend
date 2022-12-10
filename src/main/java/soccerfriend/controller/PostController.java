package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import soccerfriend.authentication.BulletinWriteAuth;
import soccerfriend.authentication.NotRecentlyPost;
import soccerfriend.authentication.PostWriteAuth;
import soccerfriend.dto.Comment;
import soccerfriend.dto.Comment.ContentInput;
import soccerfriend.dto.Post;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.CommentService;
import soccerfriend.service.LoginService;
import soccerfriend.service.PostImageService;
import soccerfriend.service.PostService;

import java.util.List;

import static soccerfriend.exception.ExceptionInfo.COMMENT_NOT_EXIST;
import static soccerfriend.exception.ExceptionInfo.NO_COMMENT_PERMISSION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final LoginService loginService;
    private final CommentService commentService;
    private final PostImageService postImageService;

    /**
     * 특정 게시판에 게시물을 생성합니다.
     *
     * @param bulletinId 게시판의 id
     * @param post       게시물 정보
     */
    @PostMapping("/bulletin/{bulletinId}")
    @BulletinWriteAuth
    @NotRecentlyPost
    public void create(@PathVariable int bulletinId,
                       @Validated @RequestBody Post post,
                       @RequestPart List<MultipartFile> images) {
        int memberId = loginService.getMemberId();
        postService.create(bulletinId, memberId, post, images);
    }

    /**
     * 특정 게시판에 존재하는 게시물을 조회합니다.
     *
     * @param bulletinId 게시판의 id
     * @param postId     게시물의 id
     * @return 특정 id의 게시물
     */
    @GetMapping("/bulletin/{bulletinId}/{postId}")
    @BulletinWriteAuth
    public Post readPost(@PathVariable int bulletinId, @PathVariable int postId) {
        int memberId = loginService.getMemberId();

        return postService.readPost(memberId, postId);
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

    /**
     * 게시물에 댓글을 작성합니다.
     *
     * @param bulletinId 게시판의 id
     * @param postId     게시물의 id
     * @param content    댓글 내용
     */
    @PostMapping("/bulletin/{bulletinId}/{postId}/comment")
    @BulletinWriteAuth
    public void writeComment(@PathVariable int bulletinId,
                             @PathVariable int postId,
                             @RequestBody ContentInput content) {
        int memberId = loginService.getMemberId();

        commentService.create(postId, memberId, content.getContent());
    }

    /**
     * 댓글을 삭제합니다.
     *
     * @param bulletinId 게시판의 id
     * @param commentId  댓글의 id
     */
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

    /**
     * 게시물 내에 존재하는 모든 댓글을 조회합니다.
     *
     * @param bulletinId 게시판의 id
     * @param postId     게시물의 id
     * @return 게시판에 존재하는 모든 댓글들의 정보
     */
    @GetMapping("/bulletin/{bulletinId}/{postId}/comments")
    @BulletinWriteAuth
    public List<Comment> getComments(@PathVariable int bulletinId, @PathVariable int postId) {
        return commentService.getCommentsByPostId(postId);
    }

    /**
     * 이미 업로드된 게시물의 이미지를 삭제합니다. 게시물 수정권한이 있는 자만 삭제가능합니다.
     *
     * @param postId      게시물의 id
     * @param postImageId 삭제하려는 이미지의 id
     */
    @DeleteMapping("post/{postId}/post-image/{postImageId}")
    @PostWriteAuth
    public void deleteImage(@PathVariable int postId, @PathVariable int postImageId) {
        postImageService.delete(postImageId);
    }
}