#!/bin/bash
set -e

# ==========================================================================
# IMPORTANTE - Por qué este script YA NO crea tablas manualmente:
#
# El proyecto Grupo1-DktpApp usa JPA/EclipseLink con la propiedad
#   eclipselink.ddl-generation = create-or-extend-tables
# (ver Grupo1-DktpApp/src/META-INF/persistence.xml)
#
# Esto significa que la PROPIA APLICACIÓN crea y extiende las tablas
# automáticamente la primera vez que se conecta, en base a las clases
# marcadas con @Entity. Si además nosotros creamos tablas "a mano" acá,
# corremos el riesgo de que los nombres/tipos de columna no coincidan
# exactamente con lo que EclipseLink espera (usa su propia convención
# de nombres), generando conflictos difíciles de debuggear.
#
# Por eso este script SOLO se asegura de que la base y los permisos
# existan. El DDL real de las tablas lo aplica la app al arrancar.
# ==========================================================================

echo ">> Verificando base de datos y permisos..."

mysql -u root -p"${MARIADB_ROOT_PASSWORD}" <<-EOSQL
    CREATE DATABASE IF NOT EXISTS ${MARIADB_DATABASE};
    GRANT ALL PRIVILEGES ON ${MARIADB_DATABASE}.* TO '${MARIADB_USER}'@'%';
    FLUSH PRIVILEGES;
EOSQL

echo ">> Base '${MARIADB_DATABASE}' y permisos de '${MARIADB_USER}' listos."
echo ">> Las tablas las va a crear la aplicación Java al conectarse (EclipseLink ddl-generation)."
