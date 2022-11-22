package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import soccerfriend.authentication.BulletinWriteAuth;
import soccerfriend.authentication.NotRecentlyPost;
import soccerfriend.dto.Post;
import soccerfriend.service.LoginService;
import soccerfriend.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final LoginService loginService;

    @PostMapping("/bulletin/{bulletinId}")
    @BulletinWriteAuth
    @NotRecentlyPost
    public void create(@PathVariable int bulletinId, @Validated @RequestBody Post post) {
        int memberId = loginService.getMemberId();
        postService.create(bulletinId, memberId, post);
    }

    @GetMapping("/bulletin/{bulletinId}/{id}")
    @BulletinWriteAuth
    public Post getPostById(@PathVariable int bulletinId, @PathVariable int id) {
        return postService.getPostById(id);
    }

    @GetMapping("/bulletin/{bulletinId}/page/{page}")
    @BulletinWriteAuth
    public List<Post> getPostByPage(@PathVariable int bulletinId, @PathVariable int page) {
        return postService.getPostByBulletinPage(bulletinId, page);
    }
}
