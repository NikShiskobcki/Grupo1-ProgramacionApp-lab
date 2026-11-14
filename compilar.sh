#!/usr/bin/env sh
set -eu

cd "$(dirname "$0")"

mvn clean package -Dmaven.test.skip=true

mkdir -p entrega

cp EstacionDeTrabajo/target/servidor.jar entrega/servidor.jar
cp ServidorWeb/target/web.war entrega/web.war

echo "Paquetes generados en la carpeta entrega. Tests omitidos."
