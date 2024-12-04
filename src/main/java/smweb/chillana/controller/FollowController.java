package smweb.chillana.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import smweb.chillana.Service.FollowersService;
import smweb.chillana.model.FollowRequest;
import smweb.chillana.model.FollowUpdate;
import smweb.chillana.repository.FollowRepository;



@Controller
public class FollowController {
    private final FollowersService followersService;
    private final SimpMessagingTemplate messagingTemplate;
    private final Logger logger = LoggerFactory.getLogger(FollowController.class);

    @Autowired
    public FollowController(FollowersService followersService, SimpMessagingTemplate messagingTemplate) {
        this.followersService = followersService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/followRequest")
    @SendTo("/topic/followUpdate")
    public FollowUpdate handleFollowRequest(FollowRequest request) {
        try {
            String followerUsername = request.getFollowerUsername();
            String followingUsername = request.getFollowingUsername();

            logger.info("Follow request: follower={}, following={}", followerUsername, followingUsername);

            boolean isCurrentlyFollowing = followersService.isFollowing(followerUsername, followingUsername);

            if (isCurrentlyFollowing) {
                followersService.unfollowUser(followerUsername, followingUsername);
                logger.info("Unfollowed successfully");
            } else {
                followersService.followUser(followerUsername, followingUsername);
                logger.info("Followed successfully");
            }

            return new FollowUpdate(!isCurrentlyFollowing, followerUsername, followingUsername);
        } catch (Exception e) {
            logger.error("Error processing follow request: {}", e.getMessage(), e);
            throw e;
        }
    }

}
