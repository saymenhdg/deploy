// Open the modal with backdrop
function openModal() {
    document.getElementById("searchResultsModal").style.display = "block";
    document.getElementById("modalBackdrop").style.display = "block";
}

// Close the modal and hide the backdrop
function closeModal() {
    document.getElementById("searchResultsModal").style.display = "none";
    document.getElementById("modalBackdrop").style.display = "none";
}

// Load the modal dynamically if needed
function loadModal() {
    const modal = document.getElementById("searchResultsModal");
    if (modal) return Promise.resolve();

    return fetch("/userSearch.html") // Adjust path as needed
        .then(response => {
            if (!response.ok) throw new Error(`Failed to load modal: ${response.statusText}`);
            return response.text();
        })
        .then(html => {
            const modalContainer = document.createElement("div");
            modalContainer.innerHTML = html;
            document.body.appendChild(modalContainer);
        })
        .catch(error => console.error("Error loading modal:", error));
}

function searchUsers() {
    const query = document.getElementById("searchInput").value.trim();
    if (!query) {
        alert("Please enter a username to search.");
        return;
    }

    fetch(`/api/users/search?query=${encodeURIComponent(query)}`)
        .then(response => {
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
            return response.json();
        })
        .then(data => {
            const resultsList = document.getElementById("resultsList");
            resultsList.innerHTML = ""; // Clear previous results

            if (data.length === 0) {
                resultsList.innerHTML = "<li>No users found.</li>";
            } else {
                data.forEach(user => {
                    const listItem = document.createElement("li");

                    // Create username link
                    const usernameLink = document.createElement("a");
                    usernameLink.href = `/visitProfile/${user.username}`;
                    usernameLink.textContent = `${user.firstName} ${user.lastName} (@${user.username})`;

                    // Append the username link to the list item
                    listItem.appendChild(usernameLink);
                    resultsList.appendChild(listItem);
                });
            }

            openModal(); // Show the modal
        })
        .catch(error => console.error("Error fetching users:", error));
}
