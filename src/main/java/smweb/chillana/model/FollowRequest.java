package smweb.chillana.model;

import lombok.Data;

@Data
public class FollowRequest {
    private String followerUsername;
    private String followingUsername;

    public FollowRequest(String followerUsername, String followingUsername) {
        this.followerUsername = followerUsername;
        this.followingUsername = followingUsername;
    }
}
