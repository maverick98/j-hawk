async function sendQuery() {

    const queryBox = document.getElementById("query");
    const dslView = document.getElementById("dsl");
    const resultView = document.getElementById("result");
    const rawView = document.getElementById("raw");
    const statusBar = document.getElementById("statusBar");

    const query = queryBox.value.trim();

    if (!query) {
        alert("Please enter a query.");
        return;
    }

    // Reset UI
    dslView.innerText = "Generating Hawk DSL...";
    resultView.innerText = "";
    rawView.innerText = "";
    statusBar.innerText = "Compiling Hawk DSL...";

    switchTabProgrammatically("dsl");

    const startTime = performance.now();

    try {
        // ================================
        // 1️⃣ Compile (TopoMind)
        // ================================
        const compileResponse = await fetch("http://localhost:8000/query", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ query })
        });

        const compileData = await compileResponse.json();

        if (!compileData.output || !compileData.output.hawk_dsl) {
            dslView.innerText = "No Hawk DSL generated.";
            rawView.innerText = JSON.stringify(compileData, null, 2);
            statusBar.innerText = "Compilation failed.";
            switchTabProgrammatically("raw");
            return;
        }

        const hawkDsl = compileData.output.hawk_dsl;
        dslView.innerText = hawkDsl;

        statusBar.innerText = "Compilation successful. Executing...";

        // ================================
        // 2️⃣ Execute (Hawk Nexus)
        // ================================
        const executeResponse = await fetch("http://localhost:9000/executeHawk", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ hawk_dsl: hawkDsl })
        });

        const executeData = await executeResponse.json();

        const endTime = performance.now();
        const latency = (endTime - startTime).toFixed(2);

        if (executeData.status === "success") {
            resultView.innerText = executeData.output.result;
            statusBar.innerText = `Execution successful (${latency} ms)`;
            switchTabProgrammatically("result");
        } else {
            resultView.innerText = "Execution failed: " + executeData.error;
            statusBar.innerText = "Execution failed.";
            switchTabProgrammatically("result");
        }

        rawView.innerText = JSON.stringify({
            compile: compileData,
            execute: executeData
        }, null, 2);

    } catch (err) {
        dslView.innerText = "Error calling server.";
        rawView.innerText = err.toString();
        statusBar.innerText = "Network or server error.";
        switchTabProgrammatically("raw");
    }
}

/* ===========================
   Helper: Switch Tabs Programmatically
=========================== */
function switchTabProgrammatically(tabName) {
    document.querySelectorAll(".tab-content").forEach(el => el.style.display = "none");
    document.querySelectorAll(".tab").forEach(el => el.classList.remove("active"));

    document.getElementById(tabName).style.display = "block";

    const tabButtons = document.querySelectorAll(".tab");
    tabButtons.forEach(tab => {
        if (tab.innerText.toLowerCase().includes(tabName)) {
            tab.classList.add("active");
        }
    });
}

document.getElementById("generateBtn").addEventListener("click", sendQuery);
