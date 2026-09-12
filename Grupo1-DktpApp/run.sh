#!/bin/bash
ant clean jar || exit 1
java -cp "dist/Grupo1-DktpApp.jar:lib/AbsoluteLayout.jar:lib/eclipselink.jar:lib/jakarta.persistence-api-2.2.3.jar:lib/mariadb-java-client-3.5.10.jar" Presentacion.JFMain
