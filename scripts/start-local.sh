#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
DOCKER_DIR="${ROOT_DIR}/backend/deploy/docker"

if [[ ! -f "${DOCKER_DIR}/.env" ]]; then
  cp "${DOCKER_DIR}/.env.template" "${DOCKER_DIR}/.env"
fi

cd "${DOCKER_DIR}"
docker compose --env-file .env up -d --build
