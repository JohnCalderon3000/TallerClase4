@echo off
REM Simple wrapper that uses the local gradle distribution unpacked at ../gradle-8.4
setlocal
set "GRADLE_HOME=%~dp0..\gradle-8.4\gradle-8.4"
set "GRADLE_CMD=%GRADLE_HOME%\bin\gradle.bat"
if exist "%GRADLE_CMD%" (
  "%GRADLE_CMD%" %*
) else (
  echo Gradle executable not found at "%GRADLE_CMD%"
  exit /b 1
)
