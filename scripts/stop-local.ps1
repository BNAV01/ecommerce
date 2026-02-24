$ErrorActionPreference = 'Stop'

$rootDir = Resolve-Path "$PSScriptRoot\.."
$dockerDir = Resolve-Path "$rootDir\backend\deploy\docker"

Push-Location $dockerDir
try {
  docker compose --env-file .env down -v
} finally {
  Pop-Location
}
