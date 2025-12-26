param(
    [switch] $IncluirTests
)

$ErrorActionPreference = "Stop"

function Get-JavaTool([string] $tool) {
    if ($Env:JAVA_HOME) {
        $candidate = Join-Path $Env:JAVA_HOME ("bin\" + $tool + ".exe")
        if (Test-Path $candidate) {
            return $candidate
        }
    }
    $command = Get-Command $tool -ErrorAction SilentlyContinue
    if ($command) {
        return $command.Source
    }
    throw "No se encontró '$tool'. Define JAVA_HOME apuntando a tu JDK 17 o añade el JDK al PATH."
}

$repoRoot = Resolve-Path (Join-Path $PSScriptRoot "..")
$libDir = Join-Path $repoRoot "lib"
$sqliteJar = Join-Path $libDir "sqlite-jdbc.jar"
$javafxBase = Join-Path $libDir "javafx-base.jar"
$javafxControls = Join-Path $libDir "javafx-controls.jar"
$javafxGraphics = Join-Path $libDir "javafx-graphics.jar"
$javafxFxml = Join-Path $libDir "javafx-fxml.jar"

$libPath = "$sqliteJar;$javafxBase;$javafxControls;$javafxGraphics;$javafxFxml"

$fuentePrincipal = Join-Path $repoRoot "src\main\java"
$recursos = Join-Path $repoRoot "src\main\resources"
$fuenteTests = Join-Path $repoRoot "src\test\java"
$salida = Join-Path $repoRoot "out"
$javacExe = Get-JavaTool "javac"

if (-not (Test-Path $sqliteJar)) {
    throw "No se encontró $sqliteJar. Descarga sqlite-jdbc y colócalo en la carpeta lib."
}
if (-not (Test-Path $javafxBase)) {
    throw "No se encontró $javafxBase. Descarga los módulos de JavaFX y colócalos en la carpeta lib."
}

if (Test-Path $salida) {
    Remove-Item $salida -Recurse -Force
}
New-Item $salida -ItemType Directory | Out-Null

$fuentes = Get-ChildItem $fuentePrincipal -Recurse -Filter *.java | ForEach-Object { $_.FullName }

if ($IncluirTests -and (Test-Path $fuenteTests)) {
    $fuentes += Get-ChildItem $fuenteTests -Recurse -Filter *.java | ForEach-Object { $_.FullName }
}

& $javacExe "-cp" $libPath "-d" $salida $fuentes

if (Test-Path $recursos) {
    Copy-Item (Join-Path $recursos "*") $salida -Recurse -Force
}

Write-Host "Compilación completada en $salida"

