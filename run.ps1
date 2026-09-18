Write-Host "======================================"
Write-Host " College Event Management System"
Write-Host "        Pure Java Launcher"
Write-Host "======================================"

if (-not (Get-Command javac -ErrorAction SilentlyContinue)) {
    Write-Host "ERROR: JDK is not installed or javac is not in PATH."
    exit 1
}

if (Test-Path "out") {
    Remove-Item -Recurse -Force "out"
}
New-Item -ItemType Directory -Path "out" | Out-Null

Write-Host "Compiling..."
$sources = Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName }
javac -d out $sources

if ($LASTEXITCODE -ne 0) {
    Write-Host "Compilation failed."
    exit $LASTEXITCODE
}

Write-Host "Starting application..."
java -cp out com.collegeevent.Main
