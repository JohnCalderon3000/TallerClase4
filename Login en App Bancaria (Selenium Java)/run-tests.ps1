# run-tests.ps1
# Comprueba si 'mvn' está disponible y ejecuta 'mvn test'.
# Si no está disponible, muestra instrucciones para instalar Maven en Windows.

$projectDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
Set-Location $projectDir

Write-Host "Directorio del proyecto: $projectDir"

if (Get-Command mvn -ErrorAction SilentlyContinue) {
    Write-Host "Maven detectado en PATH. Ejecutando tests..."
    mvn -B test
} else {
    Write-Host "Maven no se encuentra en el PATH. Opciones para instalarlo:" -ForegroundColor Yellow
    Write-Host "1) Usar Chocolatey (recomendado si tienes choco):" -ForegroundColor Cyan
    Write-Host "   choco install maven -y"
    Write-Host "2) Descargar manualmente desde: https://maven.apache.org/download.cgi" -ForegroundColor Cyan
    Write-Host "3) Usar Maven Wrapper si existe (./mvnw.cmd):" -ForegroundColor Cyan
    if (Test-Path .\mvnw.cmd) {
        Write-Host "   Wrapper encontrado: Ejecuta .\mvnw.cmd test" -ForegroundColor Green
    }
    Write-Host "\nDespués de instalar Maven, ejecuta:" -ForegroundColor Green
    Write-Host "   mvn -B test" -ForegroundColor Green
}
