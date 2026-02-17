document.getElementById("generateBtn").addEventListener("click", sendQuery);

async function sendQuery() {

    const query = document.getElementById("query").value;
    const thinking = document.getElementById("thinking");
    const output = document.getElementById("output");

    output.style.display = "none";
    output.textContent = "";
    thinking.style.display = "block";

    try {

        const response = await fetch("http://127.0.0.1:8000/query", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ query: query })
        });

        const data = await response.json();

        thinking.style.display = "none";

        if (data.status === "success" && data.output && data.output.code) {
            output.textContent = data.output.code;
        } else {
            output.textContent = JSON.stringify(data, null, 2);
        }

        output.style.display = "block";

    } catch (err) {
        thinking.style.display = "none";
        output.textContent = "Error: " + err;
        output.style.display = "block";
    }
}
