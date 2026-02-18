#!/bin/bash

echo "======================================"
echo "Starting Hawk Platform"
echo "======================================"

# -------------------------------------------------------
# Resolve script directory (so it works from anywhere)
# -------------------------------------------------------
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# -------------------------------------------------------
# Start TopoMind
# -------------------------------------------------------
echo ""
echo "Starting TopoMind..."

TOPOMIND_DIR="$SCRIPT_DIR/../../../../topomind-agent/topomind-agent"

if [ ! -d "$TOPOMIND_DIR" ]; then
  echo "ERROR: TopoMind directory not found at $TOPOMIND_DIR"
  exit 1
fi

cd "$TOPOMIND_DIR" || exit 1
sh start_topomind.sh &

echo "TopoMind started in background."

# -------------------------------------------------------
# Start Hawk Nexus (Spring Boot)
# -------------------------------------------------------
echo ""
echo "Starting Hawk Nexus..."

cd "$SCRIPT_DIR" || exit 1
mvn spring-boot:run
