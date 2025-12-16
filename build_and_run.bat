@echo off
echo Building OpenPnP...
call mvn clean package -DskipTests
if %errorlevel% neq 0 (
    echo Build failed!
    exit /b %errorlevel%
)

echo Build successful. Launching OpenPnP...
call openpnp.bat
