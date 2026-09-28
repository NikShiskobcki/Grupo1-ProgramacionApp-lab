#!/bin/bash
set -e

# Detecta el JDK activo (usa JAVA_HOME si está definido; si no, lo deduce de javac)
if [ -z "$JAVA_HOME" ]; then
  JAVAC_PATH=$(readlink -f "$(which javac)")
  export JAVA_HOME=$(dirname "$(dirname "$JAVAC_PATH")")
fi

ant -Dplatforms.JDK_21.home="$JAVA_HOME" clean jar
java -cp "dist/Grupo1-DktpApp.jar:lib/AbsoluteLayout.jar:lib/eclipselink.jar:lib/jakarta.persistence-api-2.2.3.jar:lib/mariadb-java-client-3.5.10.jar" Presentacion.JFMain
