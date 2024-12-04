package smweb.chillana.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smweb.chillana.model.PostModel;
import smweb.chillana.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private PostRepository postRepository;
    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;

    }

    public PostModel createPost(PostModel postModel) {
        return postRepository.save(postModel);
    }

    public List<PostModel> getAllPostsById(int userId) {
        return postRepository.getAllByUserId(userId);
    }
    public List<PostModel> getAllPostsByUsername(String username) {
        return postRepository.findAllByUser_Username(username);
    }
    public void updatePost(PostModel post) {
        postRepository.save(post);
    }

    public void deletePostById(int id) {
        postRepository.deleteById(id);
    }
    public long countPostsByUserId(int userId) {
        return postRepository.countByUserId(userId);
    }

    public List<PostModel> getAllPosts() {
        return postRepository.findAll();
    }

    public PostModel findPostById(Integer postId) {

        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
    }



}
