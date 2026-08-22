FROM mariadb:11.4

# --- Credenciales ---
# Ajustado para coincidir con el proyecto Grupo1-DktpApp (persistence.xml):
#   - Base de datos: edext
#   - La app original usaba 'root' con password vacía (inseguro y además
#     MariaDB no permite root sin password salvo que se habilite explícitamente).
#   - Se recomienda que la app use el usuario 'admin' de acá abajo.
#     Ver instrucciones en README.md para actualizar persistence.xml.
ENV MARIADB_ROOT_PASSWORD=4dm1n
ENV MARIADB_USER=admin
ENV MARIADB_PASSWORD=4dm1n
ENV MARIADB_DATABASE=edext

# Directorio de datos persistente (se monta como volumen desde docker-compose)
VOLUME ["/var/lib/mysql"]

# Scripts de inicialización: se ejecutan SOLO la primera vez
# (cuando /var/lib/mysql está vacío), en orden alfabético.
COPY init/ /docker-entrypoint-initdb.d/
RUN chmod +x /docker-entrypoint-initdb.d/*.sh

EXPOSE 3306

CMD ["mariadbd"]
