# MariaDB Stack - Grupo1-ProgramacionApp-lab

## Qué cambió respecto a la versión anterior
Se revisó `persistence.xml` de `Grupo1-DktpApp` y se hicieron estos ajustes:

1. **Nombre de la base**: `appdb` → `edext` (así se llama en el proyecto real)
2. **Se eliminó el DDL manual de tablas**: la app usa EclipseLink con
   `ddl-generation=create-or-extend-tables`, o sea que **la propia aplicación
   crea y actualiza las tablas sola** al conectarse, según las clases `@Entity`.
   Un DDL manual paralelo podía generar conflictos de nombres/tipos de columna.
   El script de init ahora solo crea la base y otorga permisos.
3. Se dejó un DDL de referencia **opcional y desactivado** en
   `ddl-manual-opcional.sql` (no se ejecuta solo) por si en el futuro
   deciden manejar el esquema a mano en vez de dejarlo en manos de JPA.
4. Se armó un `persistence.xml` corregido en `app-config/` para que la app
   se conecte con usuario `admin`/`4dm1n` en vez de `root` con password
   vacía (esto último no es válido en MariaDB salvo configuración especial,
   y además es mala práctica).

## Qué es esto
Un contenedor Docker con MariaDB que:
- Crea la base `edext` con el usuario `admin` / password `4dm1n`
- Guarda los datos en un disco persistente (no se pierden si reiniciás el contenedor)
- Deja que la app Java (EclipseLink/JPA) cree sus propias tablas al conectarse por primera vez

## Requisitos previos
Necesitás Docker y Docker Compose instalados.

- **Windows/Mac**: Docker Desktop → https://www.docker.com/products/docker-desktop
- **Linux**: instalá `docker` y `docker-compose-plugin` desde el gestor de paquetes de tu distro

Verificá con:
```
docker --version
docker compose version
```

## Paso 1: Descomprimir
Descomprimí el zip. Vas a tener:

```
mariadb-stack/
├── Dockerfile
├── docker-compose.yml
├── ddl-manual-opcional.sql   (referencia, no se ejecuta solo)
└── init/
    └── 01-init.sh
app-config/
└── persistence.xml           (reemplazar en Grupo1-DktpApp/src/META-INF/)
```

## Paso 2: Reemplazar el persistence.xml en tu proyecto
Copiá `app-config/persistence.xml` y pegalo (reemplazando) en:

```
Grupo1-DktpApp/src/META-INF/persistence.xml
```

Esto hace que la app use el usuario `admin`/`4dm1n` en vez de `root` sin password.

## Paso 3: Abrir una terminal en la carpeta mariadb-stack
- **Windows**: click derecho dentro de la carpeta → "Abrir en Terminal" / "Abrir ventana de PowerShell aquí"
- **Mac/Linux**: `cd` y arrastrá la carpeta `mariadb-stack` a la terminal, Enter

## Paso 4: Levantar el contenedor
```
docker compose up -d --build
```

Esto descarga MariaDB, construye la imagen, crea la base `edext` y deja el
usuario `admin` con permisos listos.

## Paso 5: Verificar
```
docker ps
```
Buscá `mariadb_stack` con estado "Up" (y "healthy" después de unos segundos).

```
docker logs mariadb_stack
```
Buscá la línea `>> Base 'edext' y permisos de 'admin' listos.`

## Paso 6: Correr la app Java
Abrí el proyecto `Grupo1-DktpApp` en NetBeans y ejecutalo normalmente.
La primera vez que la app se conecte, EclipseLink va a crear automáticamente
la tabla `INSTITUTO` (y las que se vayan agregando con `@Entity` en el futuro)
dentro de la base `edext`.

## Verificar las tablas manualmente (opcional)
```
docker exec -it mariadb_stack mysql -u admin -p4dm1n edext -e "SHOW TABLES;"
```
Antes de correr la app por primera vez esto va a estar vacío — es normal,
las tablas las crea la app, no el contenedor.

## Comandos útiles

| Acción | Comando |
|---|---|
| Apagar el contenedor | `docker compose down` |
| Apagar y borrar también los datos | `docker compose down -v` |
| Prender de nuevo | `docker compose up -d` |
| Ver logs en vivo | `docker logs -f mariadb_stack` |
| Reconstruir desde cero | `docker compose down -v && docker compose up -d --build` |

## Notas importantes
- Si agregan más clases con `@Entity` (Curso, Docente, Estudiante, etc.),
  no hace falta tocar nada del stack: EclipseLink va a crear esas tablas
  solo la próxima vez que la app se conecte.
- Si en algún momento prefieren manejar el esquema a mano en vez de con
  JPA, hay instrucciones para eso en `ddl-manual-opcional.sql`.
- Las contraseñas están en texto plano en `docker-compose.yml` — para un
  entorno real conviene moverlas a un archivo `.env` fuera de git.
