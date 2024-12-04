package smweb.chillana.model;

import lombok.Data;

@Data
public class FollowUpdate {

    private boolean isFollowing;
    private String follower;
    private String following;

    public FollowUpdate(boolean isFollowing, String follower, String following) {
        this.isFollowing = isFollowing;
        this.follower = follower;
        this.following = following;
    }
}
