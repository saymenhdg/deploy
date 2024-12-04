function openPostForm() {
    document.getElementById("postFormModal").style.display = "flex";
}

function closePostForm() {
    document.getElementById("postFormModal").style.display = "none";
}
function openFollowersModal() {
    const modal = document.getElementById("followersModal");
    modal.style.display = "block";
}

function closeFollowersModal() {
    const modal = document.getElementById("followersModal");
    modal.style.display = "none";
}

// Close modal when clicking outside of it
window.onclick = function(event) {
    const modal = document.getElementById("followersModal");
    if (event.target === modal) {
        modal.style.display = "none";
    }
};
