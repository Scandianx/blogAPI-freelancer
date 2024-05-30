package blogapi.dev.BlogAPI.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import blogapi.dev.BlogAPI.model.Comment;


public interface CommentRepository extends MongoRepository<Comment, String>{

    List<Comment> findAllByPostId(String postId);
    
}
