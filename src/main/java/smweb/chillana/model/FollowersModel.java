package smweb.chillana.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "followers")
public class FollowersModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "follower_id" ,referencedColumnName = "id")
    private UserModel follower;

    @ManyToOne
    @JoinColumn(name = "following_id", referencedColumnName = "id")
    private UserModel following;

    public FollowersModel(UserModel follower, UserModel following) {
        this.follower = follower;
        this.following = following;
    }
}
