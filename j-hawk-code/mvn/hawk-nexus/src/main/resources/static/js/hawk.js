async function sendQuery() {

    const queryBox = document.getElementById("query");
    const dslBox = document.getElementById("dslOutput");
    const rawBox = document.getElementById("rawOutput");
    const resultBox = document.getElementById("resultOutput");

    const query = queryBox.value.trim();

    if (!query) {
        alert("Please enter a query.");
        return;
    }

    dslBox.innerText = "Generating Hawk DSL...";
    rawBox.innerText = "";
    resultBox.innerText = "";

    try {
        // ---------------------------------------
        // 1️⃣ Call TopoMind (compileHawk)
        // ---------------------------------------
        const compileResponse = await fetch("http://localhost:8000/query", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ query })
        });

        const compileData = await compileResponse.json();

        if (!compileData.output || !compileData.output.hawk_dsl) {
            dslBox.innerText = "No Hawk DSL generated.";
            rawBox.innerText = JSON.stringify(compileData, null, 2);
            return;
        }

        const hawkDsl = compileData.output.hawk_dsl;
        dslBox.innerText = hawkDsl;

        // ---------------------------------------
        // 2️⃣ Call Hawk Nexus (executeHawk)
        // ---------------------------------------
        resultBox.innerText = "Executing Hawk DSL...";

        const executeResponse = await fetch("http://localhost:9000/executeHawk", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ hawk_dsl: hawkDsl })
        });

        const executeData = await executeResponse.json();

        if (executeData.status === "success") {
            resultBox.innerText = executeData.output.result;
        } else {
            resultBox.innerText = "Execution failed: " + executeData.error;
        }

        rawBox.innerText = JSON.stringify({
            compile: compileData,
            execute: executeData
        }, null, 2);

    } catch (err) {
        dslBox.innerText = "Error calling server.";
        rawBox.innerText = err.toString();
    }
}

document.getElementById("generateBtn").addEventListener("click", sendQuery);
