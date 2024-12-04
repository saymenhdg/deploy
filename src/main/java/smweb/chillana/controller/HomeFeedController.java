package smweb.chillana.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import smweb.chillana.Service.PostService;
import smweb.chillana.model.PostModel;
import smweb.chillana.repository.PostRepository;
import smweb.chillana.repository.UserProfileRepository;


import java.util.List;

@Controller
public class HomeFeedController {
    private final PostRepository postRepository;
    private  final UserProfileRepository userProfileRepository;
    private final PostService postService;

    @Autowired
    public HomeFeedController(PostRepository postRepository, UserProfileRepository userProfileRepository, PostService postService) {
        this.postRepository = postRepository;
        this.userProfileRepository = userProfileRepository;
        this.postService =  postService;
    }

    @GetMapping("/homeFeed/{username}")
    public String Homepage(HttpSession session, @PathVariable String username, Model model) {
        String sessionUsername = (String) session.getAttribute("username");
        if (sessionUsername == null ) {
            return "redirect:/login";
        }
        model.addAttribute("username", username);
        List<PostModel> allPosts = postService.getAllPosts();
        model.addAttribute("posts", allPosts);
        return "homeFeed";
    }




}
