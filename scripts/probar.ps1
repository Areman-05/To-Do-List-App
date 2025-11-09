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

& (Join-Path $PSScriptRoot "compilar.ps1") -IncluirTests

$classPath = "$salida;$libPath"

& $javaExe "-cp" $classPath "src.controller.GestorTareasTest"

if ($LASTEXITCODE -ne 0) {
    throw "Las pruebas fallaron. Revisa los mensajes anteriores."
}

