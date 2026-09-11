USE edext;

CREATE TABLE IF NOT EXISTS instituto (
    nombre VARCHAR(255) NOT NULL,
    PRIMARY KEY (nombre)
);

-- Ejemplo de cómo quedarían las demás, cuando se activen sus @Entity
-- (ajustar tipos/longitudes/claves según se definan en las clases Java):

-- CREATE TABLE IF NOT EXISTS usuario (
--     nickname VARCHAR(100) NOT NULL,
--     nombre VARCHAR(150) NOT NULL,
--     apellido VARCHAR(150) NOT NULL,
--     correo VARCHAR(150) NOT NULL UNIQUE,
--     f_nacimiento DATE,
--     PRIMARY KEY (nickname)
-- );

-- CREATE TABLE IF NOT EXISTS curso (
--     nombre VARCHAR(150) NOT NULL,
--     descripcion TEXT,
--     duracion VARCHAR(50),
--     cant_horas INT,
--     creditos INT,
--     url VARCHAR(255),
--     fecha_alta DATE,
--     PRIMARY KEY (nombre)
-- );

-- CREATE TABLE IF NOT EXISTS programa_formacion (
--     nombre VARCHAR(150) NOT NULL,
--     descripcion TEXT,
--     f_inicio DATE,
--     f_fin DATE,
--     f_alta DATE,
--     PRIMARY KEY (nombre)
-- );

-- CREATE TABLE IF NOT EXISTS edicion_curso (
--     nombre VARCHAR(150) NOT NULL,
--     fecha_inicio DATE,
--     fecha_fin DATE,
--     cupo INT,
--     f_publicacion DATE,
--     PRIMARY KEY (nombre)
-- );
