$ErrorActionPreference = 'Stop'

$rootDir = Resolve-Path "$PSScriptRoot\.."
$contractsDir = Resolve-Path "$rootDir\..\backend\libs\contracts\src\main\resources\openapi"
$outputDir = "$rootDir\libs\api\src\lib\generated"

Remove-Item "$outputDir\product" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item "$outputDir\order" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item "$outputDir\inventory" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item "$outputDir\notification" -Recurse -Force -ErrorAction SilentlyContinue

npx @openapitools/openapi-generator-cli generate -g typescript-angular -i "$contractsDir\product-service.yaml" -o "$outputDir\product" --additional-properties ngVersion=19.2.0,providedIn=root,stringEnums=true
npx @openapitools/openapi-generator-cli generate -g typescript-angular -i "$contractsDir\order-service.yaml" -o "$outputDir\order" --additional-properties ngVersion=19.2.0,providedIn=root,stringEnums=true
npx @openapitools/openapi-generator-cli generate -g typescript-angular -i "$contractsDir\inventory-service.yaml" -o "$outputDir\inventory" --additional-properties ngVersion=19.2.0,providedIn=root,stringEnums=true
npx @openapitools/openapi-generator-cli generate -g typescript-angular -i "$contractsDir\notification-service.yaml" -o "$outputDir\notification" --additional-properties ngVersion=19.2.0,providedIn=root,stringEnums=true

Write-Host 'OpenAPI client generation finished'
