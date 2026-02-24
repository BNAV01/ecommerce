#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
CONTRACTS_DIR="${ROOT_DIR}/../backend/libs/contracts/src/main/resources/openapi"
OUTPUT_DIR="${ROOT_DIR}/libs/api/src/lib/generated"

rm -rf "${OUTPUT_DIR}/product" "${OUTPUT_DIR}/order" "${OUTPUT_DIR}/inventory" "${OUTPUT_DIR}/notification"

npx @openapitools/openapi-generator-cli generate \
  -g typescript-angular \
  -i "${CONTRACTS_DIR}/product-service.yaml" \
  -o "${OUTPUT_DIR}/product" \
  --additional-properties=ngVersion=19.2.0,providedIn=root,stringEnums=true

npx @openapitools/openapi-generator-cli generate \
  -g typescript-angular \
  -i "${CONTRACTS_DIR}/order-service.yaml" \
  -o "${OUTPUT_DIR}/order" \
  --additional-properties=ngVersion=19.2.0,providedIn=root,stringEnums=true

npx @openapitools/openapi-generator-cli generate \
  -g typescript-angular \
  -i "${CONTRACTS_DIR}/inventory-service.yaml" \
  -o "${OUTPUT_DIR}/inventory" \
  --additional-properties=ngVersion=19.2.0,providedIn=root,stringEnums=true

npx @openapitools/openapi-generator-cli generate \
  -g typescript-angular \
  -i "${CONTRACTS_DIR}/notification-service.yaml" \
  -o "${OUTPUT_DIR}/notification" \
  --additional-properties=ngVersion=19.2.0,providedIn=root,stringEnums=true

echo "OpenAPI client generation finished"
