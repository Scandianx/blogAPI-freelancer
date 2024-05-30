package blogapi.dev.BlogAPI.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "posts")
public class Post {
    
    @Id
    private String id;
    private String title;
    private String content;
    private String author;
    private String hashtags;
    private LocalDateTime createdDate;
    private int inactive;

}
