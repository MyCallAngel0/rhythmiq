const audio = document.getElementById('audioPlayer');
const audioSource = document.getElementById('audioSource');
const songTitleElement = document.getElementById('songTitle');
const artistElement = document.getElementById('artistName');
const seekBar = document.getElementById('seekBar');
var playPauseButton = document.getElementById('playPauseBtn');

let currentSongIndex = 0;

const songs = fetchSongs()
    .then(songs => {
        // Check if songs is an object
        if (typeof songs !== 'object' || songs === null) {
            console.error('Error: songs is not an object.');
            return;
        }
        populateTable(songs);
    })
    .catch(error => {
        console.error('Error accessing songs:', error.message);
    });
// Function to fetch songs from the backend
async function fetchSongs() {
    try {
        // Fetch data from the backend
        const response = await fetch("http://localhost:8080/songs");

        // Check if the response is successful (status code 200-299)
        if (!response.ok) {
            throw new Error(`Failed to fetch data. Status: ${response.status}`);
        }

        // Parse the JSON response
        const songs = await response.json();

        // Now 'songs' contains the array of songs from the backend
        console.log('Fetched songs:', songs);

        // You can do further processing with the 'songs' array here

        return songs;
    } catch (error) {
        console.error('Error fetching songs:', error.message);
        throw error; // Re-throw the error to be caught in the outer promise chain
    }
}
// Function to populate the table with songs
function populateTable(songs) {
    const tableBody = document.getElementById("songTableBody");
    for (const song of songs) {
        console.log(`${song.title}`)
        const row = tableBody.insertRow();
        const titleCell = row.insertCell(0);
        const artistCell = row.insertCell(1);
        const timestampCell = row.insertCell(2);
        const albumCell = row.insertCell(3);
        const actionCell = row.insertCell(4);

        titleCell.textContent = `${song.title}`;
        artistCell.textContent = `${song.artist}`;
        timestampCell.textContent = Math.random(3, 4).toFixed(0).toString();
        albumCell.textContent = `${song.album}`;
        titleCell.style.cursor = 'pointer';
        titleCell.onclick = function () {
            playSong(songs.indexOf(song));
        };

        // Add dropdown menu with "Add to playlist" option
        const dropdown = document.createElement("div");
        dropdown.classList.add("dropdown");

        const dots = document.createElement("span");
        dots.textContent = "•••";
        dots.classList.add("dots");
        dropdown.appendChild(dots);

        const dropdownContent = document.createElement("div");
        dropdownContent.classList.add("dropdown-content");

        const addToPlaylistBtn = document.createElement("button");
        addToPlaylistBtn.classList.add("add-to-playlist-btn");
        addToPlaylistBtn.textContent = "Add to playlist";
        addToPlaylistBtn.onclick = function(event) {
            event.stopPropagation(); // Prevent the dropdown from closing when clicking the button
            showPopup(song.title); // Display the popup when the button is clicked
        };

        dropdownContent.appendChild(addToPlaylistBtn);
        dropdown.appendChild(dropdownContent);

        actionCell.appendChild(dropdown);
    }
}

// Function to close the popup and overlay
function closePopup() {
    const popup = document.getElementById("playlistPopup");
    const overlay = document.getElementById("overlay");

    // Hide the overlay and popup
    overlay.style.display = "none";
    popup.style.display = "none";
}

function playSong(index) {
    if (index >= 0 && index < songs.length) {
        currentSongIndex = index;
        const song = songs[currentSongIndex];
        console.log('Playing song:', song.mp3FilePath);
        var audiopath = new Audio(song.mp3FilePath)
        //audioSource.src =  audio;
        audio.load();
        audio.play();
        updateSongInfo(song.title, song.artist);
    }
}

function updateSongInfo(title, artist) {
    songTitleElement.textContent = title;
    artistElement.textContent = artist;
}
function togglePlayPause() {
    const button = document.getElementById('playPauseBtn');
    const buttonImage = document.getElementById('buttonImage');
    if (audio.paused) {
        audio.play();
        buttonImage.src = 'https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-pause-512.png';
    } else {
        audio.pause();
        buttonImage.src = 'https://cdn.icon-icons.com/icons2/2226/PNG/512/play_icon_134504.png';
    }
}
function playNextSong() {
    currentSongIndex = (currentSongIndex + 1) % songs.length;
    playSong(currentSongIndex);
}

function playPreviousSong() {
    currentSongIndex = (currentSongIndex - 1 + songs.length) % songs.length;
    playSong(currentSongIndex);
}

function updateSeekBar() {
    const newPosition = seekBar.value;
    audio.currentTime = newPosition;
}

// Update the seek bar as the audio plays
audio.addEventListener('timeupdate', function () {
    seekBar.value = audio.currentTime;
});

// Set the max attribute of the seek bar when the audio metadata is loaded
audio.addEventListener('loadedmetadata', function () {
    seekBar.max = audio.duration;
});

// Play the next song when the current one ends
audio.addEventListener('ended', function () {
    playNextSong();
});

// Function to show the popup with playlist options
function showPopup(songTitle) {
    const popup = document.getElementById("playlistPopup");
    const overlay = document.getElementById("overlay");
    const addToPlaylistBtn = document.getElementById("addToPlaylistBtn");
    const playlistSelect = document.getElementById("playlistSelect");

    addToPlaylistBtn.onclick = function() {
        const selectedPlaylist = playlistSelect.value;
        alert(`Song "${songTitle}" added to playlist "${selectedPlaylist}"`);
        closePopup();
    };

    // Show the overlay and popup
    overlay.style.display = "block";
    popup.style.display = "block";
}