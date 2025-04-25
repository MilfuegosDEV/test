-- sql/schema.sql
CREATE DATABASE BANCO;

USE BANCO;

CREATE TABLE Banco (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       nombre VARCHAR(100) NOT NULL,
                       cantidad_cajas INT NOT NULL
);

CREATE TABLE Usuario (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         usuario VARCHAR(50) UNIQUE NOT NULL,
                         contrasena VARCHAR(100) NOT NULL
);

CREATE TABLE Cliente (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         edad INT NOT NULL,
                         tipo_cliente CHAR(1) NOT NULL,
                         tipo_tramite VARCHAR(50) NOT NULL
);

CREATE TABLE Tiquete (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         cliente_id INT NOT NULL,
                         hora_creacion DATETIME,
                         hora_atencion DATETIME,
                         FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);

CREATE TABLE TiquetePendiente (
                                  tiquete_id INT PRIMARY KEY,
                                  tipo_caja CHAR(1),
                                  index_caja INT,
                                  FOREIGN KEY (tiquete_id) REFERENCES Tiquete(id)
);

CREATE TABLE Reporte (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         tiquete_id INT,
                         tipo_caja CHAR(1),
                         num_caja INT,
                         segundos_espera BIGINT,
                         hora_registro DATETIME,
                         FOREIGN KEY (tiquete_id) REFERENCES Tiquete(id)
);
