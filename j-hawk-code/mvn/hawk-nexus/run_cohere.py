import os
import cohere
from pathlib import Path

# -----------------------------
# Configuration
# -----------------------------

HOME = Path.home()
PROMPT_PATH = HOME / "j-hawk" / "plugin" / "hawkeye-26.03" / "ai" / "prompt.txt"
USER_PROMPT = "Calculate std deviation of series from 1 to 100"
MODEL = "command-a-03-2025"

# -----------------------------
# Validate environment
# -----------------------------

api_key = os.getenv("COHERE_API_KEY")
if not api_key:
    raise RuntimeError("COHERE_API_KEY environment variable not set")

if not PROMPT_PATH.exists():
    raise FileNotFoundError(f"Prompt file not found at {PROMPT_PATH}")

# -----------------------------
# Load system prompt
# -----------------------------

system_prompt = PROMPT_PATH.read_text(encoding="utf-8")

print("========== SYSTEM PROMPT LOADED ==========")
print(f"Prompt length: {len(system_prompt)}")
print("==========================================")

# -----------------------------
# Call Cohere
# -----------------------------

client = cohere.ClientV2(api_key=api_key)

response = client.chat(
    model=MODEL,
    temperature=0.0,
    messages=[
        {
            "role": "user",
            "content": system_prompt
        },
        {
            "role": "user",
            "content": USER_PROMPT
        }
    ],
)

# -----------------------------
# Print raw output
# -----------------------------

print("\n========== MODEL OUTPUT ==========")
print(response.message.content[0].text)
print("==================================")
