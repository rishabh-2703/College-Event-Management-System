@echo off
echo ======================================
echo  College Event Management System
echo         Pure Java Launcher
echo ======================================

where javac >nul 2>nul
if errorlevel 1 (
    echo ERROR: JDK is not installed or javac is not in PATH.
    pause
    exit /b 1
)

if exist out rmdir /s /q out
mkdir out

echo Compiling...
javac -d out src\com\collegeevent\Main.java src\com\collegeevent\model\*.java src\com\collegeevent\service\*.java src\com\collegeevent\exception\*.java src\com\collegeevent\util\*.java

if errorlevel 1 (
    echo Compilation failed.
    pause
    exit /b 1
)

echo Starting application...
java -cp out com.collegeevent.Main
pause
