package blogapi.dev.BlogAPI.DTOs;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private String id;
    private String title;
    private String content;
    private String author;
    private String hashtags;
    private LocalDateTime createdDate;
    private int inactive;
}
