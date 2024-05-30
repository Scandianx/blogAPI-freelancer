package blogapi.dev.BlogAPI.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import blogapi.dev.BlogAPI.DTOs.PostRequest;
import blogapi.dev.BlogAPI.DTOs.PostResponse;
import blogapi.dev.BlogAPI.model.Post;
import blogapi.dev.BlogAPI.service.CommentService;
import blogapi.dev.BlogAPI.service.PostService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<EntityModel<PostResponse>>> getAllPosts() {
        List<Post> posts = postService.findAll();
        if (posts.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        List<EntityModel<PostResponse>> postResources = new ArrayList<>();

        for (Post post : posts) {
            EntityModel<PostResponse> resource = EntityModel.of(convertToResponse(post));
            resource.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getPostById(post.getId())).withSelfRel());

            if (!commentService.findAllByPostId(post.getId()).isEmpty()) {
                resource.add(WebMvcLinkBuilder
                        .linkTo(WebMvcLinkBuilder.methodOn(CommentController.class).getCommentsByPostId(post.getId()))
                        .withRel("comments"));
            }
            postResources.add(resource);
        }

        return ResponseEntity.ok(postResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PostResponse>> getPostById(@PathVariable String id) {
        Optional<Post> post = postService.findById(id);
        if (post.isPresent()) {
            EntityModel<PostResponse> resource = EntityModel.of(convertToResponse(post.get()));
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getPostById(id))
                    .withSelfRel());
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getAllPosts())
                    .withRel("posts"));
            resource.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(CommentController.class).getCommentsByPostId(id))
                    .withRel("comments"));
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<PostResponse>> createPost(@RequestBody PostRequest postRequest) {
        if (postRequest.getTitle() == null || postRequest.getTitle().isEmpty() ||
                postRequest.getContent() == null || postRequest.getContent().isEmpty() ||
                postRequest.getAuthor() == null || postRequest.getAuthor().isEmpty() ||
                postRequest.getHashtags() == null || postRequest.getHashtags().isEmpty()) {

            return ResponseEntity.status(422).body(null); //
        }

        Post post = convertToPost(postRequest);
        post.setCreatedDate(LocalDateTime.now());
        Post savedPost = postService.save(post);
        PostResponse postResponse = convertToResponse(savedPost);

        EntityModel<PostResponse> resource = EntityModel.of(postResponse);
        resource.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getPostById(String.valueOf(savedPost.getId())))
                .withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getAllPosts())
                .withRel("posts"));

        return ResponseEntity.status(201).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PostResponse>> updatePost(@PathVariable String id,
            @RequestBody PostRequest postRequest) {
        if (!postService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if (postRequest.getTitle() == null || postRequest.getTitle().isEmpty() ||
                postRequest.getContent() == null || postRequest.getContent().isEmpty() ||
                postRequest.getAuthor() == null || postRequest.getAuthor().isEmpty() ||
                postRequest.getHashtags() == null || postRequest.getHashtags().isEmpty()) {

            return ResponseEntity.status(422).body(null); //
        }
        Post post = convertToPost(postRequest);
        post.setId(id);
        post.setCreatedDate(postService.findById(id).get().getCreatedDate());
        Post updatedPost = postService.save(post);
        PostResponse postResponse = convertToResponse(updatedPost);
        EntityModel<PostResponse> resource = EntityModel.of(postResponse);
        resource.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getPostById(updatedPost.getId()))
                .withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getAllPosts())
                .withRel("posts"));
        return ResponseEntity.ok(resource);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<PostResponse>> updatePostPartially(@PathVariable String id,
            @RequestBody PostRequest updates) {
        Optional<Post> optionalPost = postService.findById(id);
        if (!optionalPost.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Post post = optionalPost.get();

        if (updates.getTitle() != null) {
            post.setTitle(updates.getTitle());
        }
        if (updates.getHashtags() != null) {
            post.setHashtags(updates.getHashtags());
        }
        if (updates.getContent() != null) {
            post.setContent(updates.getContent());
        }

        Post updatedPost = postService.save(post);

        EntityModel<PostResponse> resource = EntityModel.of(convertToResponse(updatedPost));
        resource.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getPostById(updatedPost.getId()))
                .withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getAllPosts())
                .withRel("posts"));

        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        Optional<Post> optionalPost = postService.findById(id);
        if (!optionalPost.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Post post = optionalPost.get();
        post.setInactive(1);
        postService.save(post);

        return ResponseEntity.status(204).build();
    }

    private PostResponse convertToResponse(Post post) {
        return modelMapper.map(post, PostResponse.class);
    }

    private Post convertToPost(PostRequest postRequest) {
        return modelMapper.map(postRequest, Post.class);
    }
}
