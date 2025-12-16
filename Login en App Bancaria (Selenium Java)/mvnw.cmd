@echo off
REM Minimal mvnw.cmd wrapper that executes the maven-wrapper jar
setlocal
set "SCRIPT_DIR=%~dp0"
set "JAR_PATH=%SCRIPT_DIR%.mvn\wrapper\maven-wrapper.jar"
if exist "%JAR_PATH%" (
  rem First try: run as executable jar
  java -jar "%JAR_PATH%" %*
  if not %ERRORLEVEL%==0 (
    rem If running as -jar failed (no main manifest), try invoking the wrapper main class explicitly
    java -cp "%JAR_PATH%" org.apache.maven.wrapper.MavenWrapperMain %*
  )
) else (
  echo maven-wrapper.jar no encontrado en "%JAR_PATH%"
  echo Ejecuta .\download-maven-wrapper.ps1 para descargarlo
  exit /b 1
)

