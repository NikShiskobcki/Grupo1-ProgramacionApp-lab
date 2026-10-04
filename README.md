# Grupo1-ProgramacionApp-lab
Para levantarlo:

$ cd mariadb-stack-ajustado/mariadb-stack \
$ docker compose up \
$ cd .. \
$ cd .. \
$ cd Grupo1-DktpApp \
$ ./run.sh
\
Para vaciar la bd (parado en mariadb-stack-ajustado/mariadb-stack):\
$ docker volume rm mariadb-stack_mariadb_data

## Perfil de usuario (US-04 a US-08)

Páginas/servlets agregados en `ServidorWeb`:

| URL | Descripción |
|---|---|
| `/usuarios` | Lista de usuarios (visitantes y registrados) con filtro por texto |
| `/perfil` y `/perfil?user=<nick>` | Perfil con pestañas General / Cursos / Programas (Bootstrap Tabs) |
| `/modificar-perfil` | Edición de datos básicos (no nickname ni correo) |
| `/edicion-detalle?nombre=` | Consulta de edición de curso (destino de la navegación desde el perfil) |
| `/programa-detalle?nombre=` | Consulta de programa de formación (destino de la navegación desde el perfil) |

**Importante:** se modificó `ServidorCentral` (`ManejadorUsuario.buscarDetalleUsuario`) para que el
perfil de un estudiante incluya los programas en que está inscripto. Hay que regenerar el JAR y
copiarlo a ambas aplicaciones:

```
cd ServidorCentral
mvn clean package -Dmaven.test.skip=true
cp target/ServidorCentral-1.0-SNAPSHOT.jar ../ServidorWeb/web/WEB-INF/lib/ServidorCentral.jar
cp target/ServidorCentral-1.0-SNAPSHOT.jar ../EstacionDeTrabajo/lib/ServidorCentral.jar
```
