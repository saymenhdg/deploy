package smweb.chillana.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_posts")
public class PostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String caption;

    @Lob
    @Column(columnDefinition = "bytea")
    private byte[] postImg;

    @Transient  // This will prevent Spring from trying to persist this field
    private MultipartFile postImgFile;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    UserModel user;


    public String getPostImageBase64() {
        return postImg != null ? Base64.getEncoder().encodeToString(postImg) : null;
    }


}
