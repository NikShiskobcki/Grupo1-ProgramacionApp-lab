# edEXT — Ejecutables en dos computadoras

## Opción 1: Llevar los ejecutables listos

Llevá `servidor.jar` a la PC de Central y `web.war` a la PC de Web.
No hace falta Maven. Llevá también los scripts de configuración en `herramientas/`
y la carpeta `mariadb-stack-ajustado/mariadb-stack`.

## Opción 2: Generar los ejecutables en Linux

En una sola PC con JDK 21 y Maven, desde la raíz del repo y sin `sudo`:

```bash
java herramientas/ConfigurarWeb.java
sh compilar.sh
ls -lh entrega/servidor.jar entrega/web.war
```

Si Central va a correr en esa misma PC, podés aceptar la URL del WSDL
`http://localhost:9000/edext/central?wsdl`. Si Central está apagado, se usa el
contrato guardado en el repo. El script compila los tres módulos, omite los
tests y deja ambos archivos en `entrega/`. Copiá cada uno a su PC.

## PC de Central

1. Desde la carpeta del Compose, levantá MariaDB y esperá a que esté `healthy`.
   Adminer es opcional; podés agregarlo para consultar la base.

   ```bash
   sudo docker compose up -d mariadb
   sudo docker compose ps
   ```

2. Desde la raíz del repo, configurá Central sin `sudo`:

   ```bash
   java herramientas/ConfigurarCentral.java
   ```

   La base es `edext_web`. Para que Web pueda conectarse desde otra PC,
   publicá Central en `http://0.0.0.0:9000/edext/central`.

3. Desde donde guardaste el JAR, ejecutá `java -jar servidor.jar`.
   Si la base está vacía, podés cargar los datos de prueba desde la Estación.

## PC de Web

1. Desde la raíz del repo, ejecutá `java herramientas/ConfigurarWeb.java`
   sin `sudo`. En `~/.edEXT/web.properties`, la URL que usa Tomcat debe ser
   `central.ws.url=http://IP_DE_CENTRAL:9000/edext/central`, sin `?wsdl`.

2. Poné `web.war` junto a `docker-compose.yml`. En el montaje del WAR dejá
   `source: ./web.war` y conservá el destino `/usr/local/tomcat/webapps/edext.war`.

3. Mové `linux/.env` al lado del Compose y comprobá que
   `EDEXT_CONFIG_DIR` apunte a la carpeta `.edEXT` de tu usuario en esa PC.
   Luego, desde esa carpeta:

   ```bash
   sudo docker compose up -d --no-deps tomcat
   ```

   Si no usás `.env`, podés pasar la ruta en el comando:

   ```bash
   sudo EDEXT_CONFIG_DIR="$HOME/.edEXT" docker compose up -d --no-deps tomcat
   ```

4. Abrí [http://localhost:8081/edext/](http://localhost:8081/edext/) y consultá
   un curso para comprobar que Web, Central y MariaDB se comunican.
