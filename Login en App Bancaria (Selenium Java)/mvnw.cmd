@echo off
REM Minimal mvnw.cmd wrapper that executes the maven-wrapper jar
setlocal
set "SCRIPT_DIR=%~dp0"
set "JAR_PATH=%SCRIPT_DIR%.mvn\wrapper\maven-wrapper.jar"
if exist "%JAR_PATH%" (
  java -jar "%JAR_PATH%" %*
) else (
  echo maven-wrapper.jar no encontrado en "%JAR_PATH%"
  echo Ejecuta .\download-maven-wrapper.ps1 para descargarlo
  exit /b 1
)

