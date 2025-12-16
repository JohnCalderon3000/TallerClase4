# download-maven-wrapper.ps1
# Descarga el archivo maven-wrapper.jar en .mvn\wrapper para que mvnw funcione

$wrapperDir = Join-Path -Path $PSScriptRoot -ChildPath '.mvn\wrapper'
if (!(Test-Path $wrapperDir)) { New-Item -ItemType Directory -Path $wrapperDir -Force | Out-Null }
$jarPath = Join-Path -Path $wrapperDir -ChildPath 'maven-wrapper.jar'
$url = 'https://repo1.maven.org/maven2/io/takari/maven-wrapper/0.5.6/maven-wrapper-0.5.6.jar'

Write-Host "Descargando maven-wrapper.jar desde: $url"
try {
    Invoke-WebRequest -Uri $url -OutFile $jarPath -UseBasicParsing -ErrorAction Stop
    Write-Host "Descargado en: $jarPath" -ForegroundColor Green
} catch {
    Write-Host "Error al descargar: $_" -ForegroundColor Red
    exit 1
}
