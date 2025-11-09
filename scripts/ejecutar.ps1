param(
    [string] $DbPath
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
$salida = Join-Path $repoRoot "out"
$libPath = Join-Path $repoRoot "lib\sqlite-jdbc.jar"
$javaExe = Get-JavaTool "java"

if (-not (Test-Path $salida)) {
    & (Join-Path $PSScriptRoot "compilar.ps1")
}

$javaArgs = @("-cp", "$salida;$libPath")

if ($DbPath) {
    $propiedad = "-Dtodolist.db.url=jdbc:sqlite:$DbPath"
    $javaArgs = @($propiedad) + $javaArgs
}

$javaArgs += "src.Main"

& $javaExe @javaArgs

