#!/bin/bash
set -e

echo "======================================"
echo " College Event Management System"
echo "        Pure Java Launcher"
echo "======================================"

if ! command -v javac >/dev/null 2>&1; then
    echo "ERROR: JDK is not installed or javac is not in PATH."
    echo "Install JDK 17 or newer and try again."
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1)
echo "Java: $JAVA_VERSION"

rm -rf out
mkdir -p out

echo "Compiling..."
javac -d out $(find src -name "*.java")

echo "Starting application..."
java -cp out com.collegeevent.Main
