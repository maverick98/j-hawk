async function sendQuery() {

    const queryBox = document.getElementById("query");
    const dslBox = document.getElementById("dslOutput");
    const rawBox = document.getElementById("rawOutput");

    const query = queryBox.value.trim();

    if (!query) {
        alert("Please enter a query.");
        return;
    }

    // Clear previous output
    dslBox.innerText = "Generating Hawk DSL...";
    rawBox.innerText = "";

    try {
        const response = await fetch("http://127.0.0.1:8000/query", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ query })
        });

        const data = await response.json();

        // Render DSL
        if (data.output && data.output.hawk_dsl) {
            dslBox.innerText = data.output.hawk_dsl;
        } else {
            dslBox.innerText = "No Hawk DSL generated.";
        }

        // Render raw JSON (collapsed by default)
        rawBox.innerText = JSON.stringify(data, null, 2);

    } catch (err) {
        dslBox.innerText = "Error calling server.";
        rawBox.innerText = err.toString();
    }
}

document.getElementById("generateBtn").addEventListener("click", sendQuery);
