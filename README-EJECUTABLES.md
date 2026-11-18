# edEXT — Compilación y ejecución en dos PC

Una PC ejecuta Central, la Estación y MariaDB.
La otra ejecuta Tomcat con la aplicación Web.

## 1. Compilar en ambas PC

Necesitan JDK 21, Docker con Docker Compose e Internet.
No necesitan instalar Maven ni NetBeans: el Wrapper descarga Maven 3.9.11
la primera vez y lo reutiliza después.

Desde la raíz del proyecto, SIN sudo:

```bash
sh compilar.sh
```

En Windows, el equivalente es:

```powershell
powershell -ExecutionPolicy Bypass -File .\compilar.ps1
```

El script crea central.properties y web.properties en ~/.edEXT si faltan.
Si ya existen, conserva sus valores. Luego compila los tres módulos,
omitiendo los tests, y genera:

- entrega/servidor.jar
- entrega/web.war
- ServidorWeb/target/web.war, utilizado por Docker Compose.

Esperar BUILD SUCCESS. No es necesario iniciar Central ni MariaDB
para compilar: si Central está apagado, se utiliza el contrato SOAP local.

## 2. PC de Central

Desde la raíz del proyecto:

```bash
cd mariadb-stack-ajustado/mariadb-stack
sudo docker compose up -d --wait mariadb adminer
cd ../..
java -jar entrega/servidor.jar
```

Adminer es opcional y se abre en http://localhost:8080/.

Los valores iniciales de ~/.edEXT/central.properties son:
- Publicación: http://0.0.0.0:9000/edext/central
- Base: edext_web en localhost:3306
- Usuario: admin
- Contraseña: 4dm1n

Deben coincidir con la MariaDB utilizada. Si modifican este archivo,
reiniciar Central. No ejecutar el JAR con sudo.

El JAR abre la Estación y publica Central. Si necesitan datos de prueba,
cargarlos desde la Estación. Cerrar su ventana deja Central funcionando:
mantener abierta la terminal; Ctrl+C detiene el proceso.

## 3. PC de Web

Después de compilar, editar ~/.edEXT/web.properties con un editor de texto.
Reemplazar IP_DE_CENTRAL por la IP real de la otra computadora:

```properties
central.ws.url=http://IP_DE_CENTRAL:9000/edext/central
```

Esta URL NO lleva ?wsdl. No usar localhost ni host.docker.internal cuando
Central está en otra PC. Las dos computadoras deben tener acceso al puerto 9000.

Desde la raíz del proyecto:

```bash
cd mariadb-stack-ajustado/mariadb-stack
sudo EDEXT_CONFIG_DIR="$HOME/.edEXT" docker compose up -d --no-deps --force-recreate tomcat
```

El comando indica la carpeta de configuración correcta aunque se use sudo.
No mover el WAR ni modificar el Compose: el script ya lo genera en target.

Abrir http://localhost:8081/edext/ en la PC de Web.

## 4. Comprobar la comunicación

Desde la PC de Web, abrir en el navegador:

http://IP_DE_CENTRAL:9000/edext/central?wsdl

Debe aparecer XML. Luego consultar un curso desde edEXT para comprobar
también el acceso de Central a MariaDB.

Si modifican web.properties con Tomcat iniciado, recrear Tomcat con el
comando anterior. Los puertos 3306, 9000 y 8081 deben estar disponibles
en las PC donde se utilicen.
