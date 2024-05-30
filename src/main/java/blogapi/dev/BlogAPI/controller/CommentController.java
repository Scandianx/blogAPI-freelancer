package blogapi.dev.BlogAPI.controller;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import blogapi.dev.BlogAPI.DTOs.CommentRequest;
import blogapi.dev.BlogAPI.DTOs.CommentResponse;
import blogapi.dev.BlogAPI.model.Comment;
import blogapi.dev.BlogAPI.service.CommentService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByPostId(@PathVariable String postId) {

        List<Comment> comments = commentService.findAllByPostId(postId);
        List<CommentResponse> commentsResponse = new ArrayList<>();
        for (Comment c: comments) {
            commentsResponse.add(convertToResponse(c));
        }
        if (comments.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(commentsResponse);
    }

    @PostMapping
    public ResponseEntity<EntityModel<CommentResponse>> createComment(@RequestBody CommentRequest commentRequest) {
        if (commentRequest.getPostId() == null || commentRequest.getPostId().isEmpty() ||
        commentRequest.getContent() == null || commentRequest.getContent().isEmpty()) {
        return ResponseEntity.status(422).body(null);
    }
        Comment comment = new Comment();
        comment.setPostId(commentRequest.getPostId());
        comment.setCommenter(commentRequest.getCommenter());
        comment.setContent(commentRequest.getContent());
        comment.setCommentedDate(LocalDateTime.now());
        Comment savedComment = commentService.save(comment);
        EntityModel<CommentResponse> resource = EntityModel.of(convertToResponse(savedComment));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CommentController.class).getCommentsByPostId(savedComment.getPostId())).withRel("comments"));
        return ResponseEntity.status(201).body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable String id) {
        if (!commentService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        commentService.deleteById(id);
        return ResponseEntity.status(204).build();
    }
    private CommentResponse convertToResponse(Comment comment) {
        return modelMapper.map(comment, CommentResponse.class);
    }

    
}
