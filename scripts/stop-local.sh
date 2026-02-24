#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
DOCKER_DIR="${ROOT_DIR}/backend/deploy/docker"

cd "${DOCKER_DIR}"
docker compose --env-file .env down -v
