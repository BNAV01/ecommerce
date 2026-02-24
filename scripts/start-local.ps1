$ErrorActionPreference = 'Stop'

$rootDir = Resolve-Path "$PSScriptRoot\.."
$dockerDir = Resolve-Path "$rootDir\backend\deploy\docker"

if (-not (Test-Path "$dockerDir\.env")) {
  Copy-Item "$dockerDir\.env.template" "$dockerDir\.env"
}

Push-Location $dockerDir
try {
  docker compose --env-file .env up -d --build
} finally {
  Pop-Location
}
