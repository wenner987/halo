package cc.ryanc.halo.web.controller.admin.api;

import cc.ryanc.halo.model.dto.post.PostSimpleOutputDTO;
import cc.ryanc.halo.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Post controller.
 *
 * @author johnniang
 * @date 3/19/19
 */
@RestController
@RequestMapping("/api/admin/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("latest")
    public Page<PostSimpleOutputDTO> pageLatest(@RequestParam(name = "top", defaultValue = "10") int top) {
        return postService.pageLatest(top);
    }

}