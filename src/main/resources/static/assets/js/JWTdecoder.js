getJWTSub()
    .then(data => console.log(data))
    .catch(error => {
        console.error('Error accessing songs:', error.message);
    });

async function getJWTSub() {
    try {
        const response = await fetch("http://localhost:8080/api/token");

        if (!response.ok) {
            console.error(`Failed to fetch data. Status: ${response.status}`);
            throw new Error(`Failed to fetch data. Status: ${response.status}`);
        }

        const username = await response.text();

        console.log('Username:', username);

        return username;
    } catch (error) {
        console.error('Error fetching user:', error.message);
        throw error;
    }
}