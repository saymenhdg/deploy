package smweb.chillana.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import smweb.chillana.Service.FollowersService;
import smweb.chillana.Service.PostService;
import smweb.chillana.Service.UserProfileService;
import smweb.chillana.model.PostModel;
import smweb.chillana.model.UserModel;
import smweb.chillana.model.UserProfileModel;
import smweb.chillana.repository.FollowRepository;
import smweb.chillana.repository.PostRepository;
import smweb.chillana.repository.UserProfileRepository;
import smweb.chillana.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class UserProfileController {

    private final UserProfileRepository userProfileRepository;
    private final PostService postService;
    private UserRepository userRepository;
    private UserProfileService userProfileService;
    private PostRepository postRepository;
    private FollowRepository followRepository;
    private FollowersService followersService;

    @Autowired
    public UserProfileController(UserRepository userRepository, UserProfileService userProfileService,
                                 UserProfileRepository userProfileRepository, PostRepository postRepository, PostService postService,
                                 FollowRepository followRepository, FollowersService followersService) {
        this.userRepository = userRepository;
        this.userProfileService = userProfileService;
        this.userProfileRepository = userProfileRepository;
        this.postRepository = postRepository;
        this.postService = postService;
        this.followRepository = followRepository;
        this.followersService = followersService;
    }


    @GetMapping("/userProfile/{username}")
    public String profile(@PathVariable String username, Model model, HttpSession session) {
        String sessionUsername = (String) session.getAttribute("username");

        if (sessionUsername == null || !sessionUsername.equals(username)) {
            return "redirect:/login";
        }
        UserModel userModel = userRepository.findByUsername(username);
        UserProfileModel userProfileModel = userProfileService.getUserProfile(userModel);
        if(userProfileModel == null) {
            userProfileModel = new UserProfileModel();
            userProfileModel.setUser(userModel);
        }
        List<PostModel> userPosts = postService.getAllPostsById(userModel.getId());

        List<UserModel> followers = followersService.getFollowers(userModel);
        model.addAttribute("userProfile", userProfileModel);
        model.addAttribute("username", username);
        model.addAttribute("userPosts", userPosts);
        model.addAttribute("followers", followers);
        System.out.println("User ID: " + userModel.getId());
        System.out.println("User Posts: " + userPosts);
        System.out.println("Followers: " + followers);
        return "userProfile";
    }

    @GetMapping("/profile/edit/{userId}")
    public String edit(@PathVariable Integer userId, Model model) {
        UserModel userModel = userRepository.findById(userId).orElse(null);
        UserProfileModel userProfile = userProfileService.getUserProfile(userModel);
        if(userProfile == null) {
            userProfile = new UserProfileModel();
            userProfile.setUser(userModel);
            userProfileService.saveUserProfile(userProfile);

        }
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("username", userModel.getUsername());
        return "editProfile";

    }
    @PostMapping("/profile/edit/{userId}")
    public String updateProfile(@PathVariable Integer userId,
                                @RequestParam(required = false) String bio,
                                @RequestParam(required = false) String location,
                                @RequestParam(required = false) MultipartFile profileImage,
                                @RequestParam(required = false) MultipartFile backgroundImage,
                                Model model) {
        try {
            UserModel userModel = userRepository.findById(userId).orElse(null);
            UserProfileModel userProfile = userProfileService.getUserProfile(userModel);
            if (userProfile == null) {
                userProfile = new UserProfileModel();
                userProfile.setUser(userModel);
            }
            userProfile.setBio(bio);
            userProfile.setLocation(location);
            if (profileImage != null && !profileImage.isEmpty()) {
                userProfile.setProfileImage(profileImage.getBytes());
            }
            if (backgroundImage != null && !backgroundImage.isEmpty()) {
                userProfile.setBackgroundImage(backgroundImage.getBytes());
            }

            userProfileService.saveUserProfile(userProfile);
            model.addAttribute("successMessage", "Profile updated successfully");
            return "redirect:/userProfile/" + userModel.getUsername();

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to update profile: " + e.getMessage());
            return "redirect:/profile/edit/" + userId;
        }
    }

    @PostMapping("/userProfile/post")
    public String createPost(@RequestParam String caption, @RequestParam MultipartFile postImg, HttpSession session, Model model) {
        String sessionUsername = (String) session.getAttribute("username");
        try {
            UserModel userModel = userRepository.findByUsername(sessionUsername);

            PostModel postModel = new PostModel();
            postModel.setCaption(caption);
            postModel.setUser(userModel);
            if (postImg != null && !postImg.isEmpty()) {
                postModel.setPostImg(postImg.getBytes());

            }
            postService.createPost(postModel);
            System.out.println("post has been created");
            return "redirect:/userProfile/" + userModel.getUsername();
        }catch (Exception e){
            model.addAttribute("errorMessage", "Failed to create post: " + e.getMessage());

            return "redirect:/userProfile/"+  sessionUsername;
        }
    }



}
