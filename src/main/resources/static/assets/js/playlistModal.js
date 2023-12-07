document.addEventListener("DOMContentLoaded", function () {
    const openModalBtn = document.getElementById("openModalBtn");
    const modal = document.getElementById("modal");
    const closeModalBtn = document.getElementById("closeModalBtn");
    const playlistForm = document.getElementById("playlistForm");

    openModalBtn.addEventListener("click", function () {
        modal.style.display = "block";
    });

    closeModalBtn.addEventListener("click", function () {
        modal.style.display = "none";
    });

    window.addEventListener("click", function (event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });

    playlistForm.addEventListener("submit", function (event) {
        event.preventDefault();

        // Get the playlist title from the input
        const playlistTitle = document.getElementById("playlistTitle").value;

        // Perform the logic to create the playlist (replace this with your actual logic)
        console.log("Creating playlist with title:", playlistTitle);

        fetch('http://localhost:8080/createPlaylist', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ playlistTitle }),
        })
            .then(response => response.text())
            .then(data => {
                console.log('Success:', data);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
        modal.style.display = "none";

        // Optionally, you can perform further actions, such as updating the UI or making an AJAX request
    });
});
