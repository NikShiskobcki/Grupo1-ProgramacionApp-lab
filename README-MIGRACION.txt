edEXT - MIGRACION A WEB SERVICES
==============================
Requisitos: JDK 21, Maven y Docker funcionando.
Los comandos parten de la raiz del proyecto y usan Maven en PATH.
En Windows pueden ejecutar Maven desde NetBeans si no tienen mvn en PATH.

COMO EJECUTAR

1. Crear la configuracion (solo la primera vez):

   java herramientas/ConfigurarCentral.java
   java herramientas/ConfigurarWeb.java

   Se guarda en la carpeta .edEXT del home del usuario.
   Si los archivos existen, las herramientas los conservan.

   central.properties: URL de publicacion y conexion a MariaDB.
   web.properties: URL de Central que consume Web y carpeta de imagenes.

   Para Web en Docker Desktop y Central en nuestra PC:
   central.ws.url=http://host.docker.internal:9000/edext/central
   imagenes.dir=/var/lib/edext/imagenes

   Si Central corre en otra PC, usar su IP en central.ws.url.
   En Docker Engine Linux hay que configurar el acceso al anfitrion;
   host.docker.internal no siempre esta disponible automaticamente.

2. Levantar MariaDB:

   cd mariadb-stack-ajustado/mariadb-stack
   docker compose up -d --build --wait mariadb
   cd ../..

3. Compilar Central y la Estacion:

   mvn -f ServidorCentral/pom.xml clean package -Dmaven.test.skip=true
   mvn -f EstacionDeTrabajo/pom.xml package -Dmaven.test.skip=true

   Central actualiza automaticamente el JAR que utiliza la Estacion.
   Estos comandos omiten los tests; no significa que hayan pasado.

4. Iniciar UNA de estas opciones y dejarla funcionando:

   - Con ventana: ejecutar Presentacion.JFMain desde NetBeans.
     Inicia Central y abre la Estacion. Cerrar la ventana deja SOAP activo.

   - Sin ventana: desde ServidorCentral ejecutar:
     mvn compile exec:java -Dexec.mainClass=publicadores.CentralMain

   No iniciar ambas: intentarian ocupar el mismo puerto.
   Para apagar todo, detener el proceso desde NetBeans o su terminal.

5. Compilar Web y levantar Tomcat, desde la raiz:

   cd mariadb-stack-ajustado/mariadb-stack
   docker compose rm -sf tomcat
   cd ../..
   mvn -f ServidorWeb/pom.xml clean package -Dmaven.test.skip=true

   Si Maven falla, corregirlo antes de continuar.

   cd mariadb-stack-ajustado/mariadb-stack
   docker compose up -d tomcat adminer

   Web:     http://localhost:8081/edext/
   Adminer: http://localhost:8080/

   No usar down -v: elimina los volumenes con datos e imagenes.

COMO FUNCIONA AHORA

El navegador pide paginas a los servlets de Web.
Los servlets llaman a ClienteCentral, que consume Central mediante SOAP.
Central ejecuta el controlador y accede a MariaDB.
La Estacion sigue llamando directamente al controlador en el mismo proceso.
Web ya no utiliza ServidorCentral.jar ni accede directamente a la base.

CAMBIAR LA DIRECCION SIN COMPILAR

Editar .edEXT/web.properties y, desde la carpeta del Compose, ejecutar:

   docker compose up -d --no-deps --force-recreate tomcat

Compose encuentra el home automaticamente. No hay que definir variables.
Si cambian central.properties, reiniciar Central tambien.

PARA AGREGAR OPERACIONES NUEVAS

1. Reutilizar o agregar la logica en el controlador de Central.
2. Exponerla en publicadores/ICentralWS y delegar desde CentralWS.
3. Si hacen falta datos nuevos, agregar un DTO en publicadores/dto
   y convertirlo en MapeadorSOAP. Reutilizar los existentes si alcanzan.
4. Compilar y reiniciar Central. Desde la raiz actualizar el contrato:

   java herramientas/ActualizarContrato.java "http://localhost:9000/edext/central?wsdl" ServidorWeb/src/main/resources/wsdl
   mvn -f ServidorWeb/pom.xml generate-sources

5. Agregar la llamada en ClienteCentral. Si hace falta, agregar el modelo
   de Web en src/modelo y su conversion en MapeadorCliente.
6. Utilizar ClienteCentral desde el servlet, compilar Web y recrear Tomcat.

No editar cliente.central: Maven lo genera desde el WSDL.
Los DTO transportan datos; las reglas del negocio siguen en Central.
Subir a Git tambien los WSDL/XSD actualizados, no los archivos de target.
Si solo cambia la logica interna y no el contrato, no regenerar el cliente.

PENDIENTE DE ENTREGA

Prueba completa en Linux y entre equipos, pruebas funcionales restantes
y empaquetado final de servidor.jar/web.war. Esta guia describe el arranque
actual de desarrollo; no supone que esos ejecutables ya esten terminados.
