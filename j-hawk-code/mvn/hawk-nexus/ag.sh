#!/bin/bash

# Read API key from file (trim whitespace/newline)
API_KEY=$(tr -d '[:space:]' < apikey.txt)

# Basic safety check
if [ -z "$API_KEY" ]; then
  echo "Error: apikey.txt is empty or missing"
  exit 1
fi

curl -s https://api.groq.com/openai/v1/chat/completions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $API_KEY" \
  -d '{
    "model": "llama-3.1-8b-instant",
    "temperature": 0,
    "messages": [
      {"role": "system", "content": "Return only the numeric result."},
      {"role": "user", "content": "Calculate sum from 1 to 5"}
    ]
  }' | jq -r '.choices[0].message.content'
  
curl https://api.groq.com/openai/v1/models \
  -H "Authorization: Bearer $API_KEY"
  
