export const playlists = fetchPlaylists()
    .then(playlists => {
        // Check if songs is an object
        if (typeof playlists !== 'object' || playlists === null) {
            console.error('Error: playlists is not an object.');
            return;
        }
        appendToList(playlists);
    })
    .catch(error => {
        console.error('Error getting playlists:', error.message);
    });

// Function to fetch songs from the backend
async function fetchPlaylists() {
    try {
        const username = await getJWTSub();
        // Fetch data from the backend
        const response = await fetch("http://localhost:8080/playlists/api/" + username);

        if (!response.ok) {
            throw new Error(`Failed to fetch data. Status: ${response.status}`);
        }

        // Parse the JSON response
        const playlists = await response.json();

        return playlists;
    } catch (error) {
        console.error('Error getting playlists:', error.message);
        throw error; // Re-throw the error to be caught in the outer promise chain
    }
}
function appendToList(playlists) {
    const playlistContainer = document.getElementById("playlist-container");
    playlists.forEach(playlist => {
        const listItem = document.createElement("li");
        listItem.textContent = `${playlist.title}`;
        playlistContainer.appendChild(listItem);
    });
}

/*
appendToPlaylist(playlists);
function appendToPlaylist(playlists) {
    console.log("here");
    const selectElement = document.getElementById("playlistSelect");

    playlists.forEach(function(playlist) {
        const option = document.createElement("option");
        option.value = playlist.title.toLowerCase();
        option.textContent = playlist.title;
        selectElement.appendChild(option);
    });
}*/
