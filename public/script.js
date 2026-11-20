
async function sendMessage(message) {
    try {
        const response = await fetch("/message", {
            method: "POST",
            headers: {
                "Content-Type": "text/plain"
            },
            body: message
        });

        if (!response.ok) {
            throw new Error(`HTTP error: ${response.status}`);
        }

        const json = await response.json();

        console.log("Server response:", json);

        return json;

    } catch (error) {
        console.error("Request failed:", error);
    }
}
