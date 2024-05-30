package blogapi.dev.BlogAPI.DTOs;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

    private String title;
    private String content;
    private String author;
    private String hashtags;
    private int inactive;
}
