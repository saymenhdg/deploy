let stompClient = null;

function openPostForm() {
    document.getElementById("postFormModal").style.display = "flex";
}

function closePostForm() {
    document.getElementById("postFormModal").style.display = "none";
}


function connectWebSocket() {
    if (stompClient !== null) {
        console.log("WebSocket already connected");
        return;
    }

    const socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected to WebSocket:', frame);

        stompClient.subscribe('/topic/followUpdate', function (response) {
            try {
                const data = JSON.parse(response.body);
                console.log('Received follow update:', data);
                updateFollowButton(data.follower, data.following, data.isFollowing);
            } catch (error) {
                console.error('Error processing follow update:', error);
            }
        });
    }, function (error) {
        console.error('WebSocket connection error:', error);
        setTimeout(connectWebSocket, 5000);
    });
}
document.addEventListener('DOMContentLoaded', function () {
    const followButton = document.getElementById('followButton');

    if (followButton) {
        followButton.addEventListener('click', function () {
            if (!stompClient || !stompClient.connected) {
                console.error('WebSocket not connected');
                return;
            }
            const follower = this.getAttribute('data-follower');
            const following = this.getAttribute('data-following');

            if (!follower || !following) {
                console.error('Missing follower or following data');
                return;
            }
            this.textContent = "Processing...";
            this.disabled = true;
            console.log('Sending follow/unfollow request:', { follower, following });
            stompClient.send("/app/followRequest", {}, JSON.stringify({
                followerUsername: follower,
                followingUsername: following
            }));
        });
    }

    // Establish WebSocket connection on page load
    connectWebSocket();
});

// Function to update the follow button UI based on the follow state
function updateFollowButton(follower, following, isFollowing) {
    const button = document.getElementById('followButton');
    if (button) {
        // Ensure the button corresponds to the correct user
        if (button.getAttribute('data-following') === following) {
            // Update the button text based on follow state
            if (isFollowing) {
                button.textContent = 'Unfollow';
            } else {
                button.textContent = 'Follow';
            }
            button.classList.toggle('following', isFollowing);
            button.disabled = false; // Re-enable the button after updating state
        }
    }
}
