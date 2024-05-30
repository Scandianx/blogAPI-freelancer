package blogapi.dev.BlogAPI.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import blogapi.dev.BlogAPI.model.Post;

public interface PostRepository extends MongoRepository<Post, String>{
    
}
