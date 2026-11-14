$ErrorActionPreference = 'Stop'

Push-Location $PSScriptRoot
try {
    $maven = 'mvn.cmd'

    if (-not (Get-Command $maven -ErrorAction SilentlyContinue)) {
        $maven = 'C:\Program Files\Apache NetBeans\java\maven\bin\mvn.cmd'
    }

    & $maven clean package '-Dmaven.test.skip=true'
    if ($LASTEXITCODE -ne 0) {
        throw 'Fallo la compilacion.'
    }

    New-Item -ItemType Directory -Force '.\entrega' | Out-Null

    Copy-Item '.\EstacionDeTrabajo\target\servidor.jar' '.\entrega\servidor.jar' -Force
    Copy-Item '.\ServidorWeb\target\web.war' '.\entrega\web.war' -Force

    Write-Host 'Paquetes generados en la carpeta entrega. Tests omitidos.'
} finally {
    Pop-Location
}
