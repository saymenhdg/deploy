package smweb.chillana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smweb.chillana.model.PostModel;

import java.util.List;

public interface PostRepository extends JpaRepository<PostModel, Integer> {
    List<PostModel> getAllByUserId(int userId);
    List<PostModel> findAllByUser_Username(String username);
    long countByUserId(int userId);
    PostModel findPostById(int postId);
}
