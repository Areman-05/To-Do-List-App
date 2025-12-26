param(
    [string] $DbPath = ""
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

$classpath = "$sqliteJar;$javafxBase;$javafxControls;$javafxGraphics;$javafxFxml"

$salida = Join-Path $repoRoot "out"
$javaExe = Get-JavaTool "java"

if (-not (Test-Path $salida)) {
    & (Join-Path $PSScriptRoot "compilar.ps1")
}

$props = @()
if ($DbPath) {
    $props += "-Dtodolist.db.url=jdbc:sqlite:$DbPath"
}

$javaArgs = @(
    "--module-path", "$javafxBase;$javafxControls;$javafxGraphics;$javafxFxml",
    "--add-modules", "javafx.controls,javafx.fxml",
    "-cp", "$classpath;$salida"
) + $props + @("src.view.ToDoListApp")

& $javaExe $javaArgs
