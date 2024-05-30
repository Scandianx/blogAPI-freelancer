package blogapi.dev.BlogAPI.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blogapi.dev.BlogAPI.model.Comment;
import blogapi.dev.BlogAPI.repository.CommentRepository;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> findAllByPostId(String postId) {
        return commentRepository.findAllByPostId(postId);
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }

    public Optional<Comment> findById(String id) {
        return commentRepository.findById(id);
    }
}
