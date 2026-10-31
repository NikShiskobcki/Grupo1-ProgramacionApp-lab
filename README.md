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


```
cd ServidorCentral
mvn clean package -Dmaven.test.skip=true
cp target/ServidorCentral-1.0-SNAPSHOT.jar ../ServidorWeb/web/WEB-INF/lib/ServidorCentral.jar
cp target/ServidorCentral-1.0-SNAPSHOT.jar ../EstacionDeTrabajo/lib/ServidorCentral.jar
```

```
cd ServidorCentral
mvn clean install -Dmaven.test.skip=true
cd ../ServidorWeb
mvn clean package
cd ../mariadb-stack-ajustado/mariadb-stack
docker compose down
docker volume rm mariadb-stack_mariadb_data
docker compose up -d
```
