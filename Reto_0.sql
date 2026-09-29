/*CREATE DATABASE GestionTiendaOnline;*/
USE GestionTiendaOnline;

/*
CREATE TABLE PRODUCTO(
ID INTEGER AUTO_INCREMENT PRIMARY KEY,
NOMBRE VARCHAR(20) NOT NULL,
PRECIO DOUBLE NOT NULL,
CATEGORIA ENUM('CALZADO','TEXTIL','ACCESORIO'),
STOCK INTEGER,
RUTA VARCHAR(50)
);

CREATE TABLE CLIENTE(
ID INTEGER AUTO_INCREMENT PRIMARY KEY,
NOMBRE VARCHAR(20) NOT NULL,
EMAIL VARCHAR(30) NOT NULL,
TELEFONO VARCHAR(20),
DIRECCION VARCHAR(100)
);
*/

INSERT INTO PRODUCTO (NOMBRE, PRECIO, CATEGORIA, STOCK, RUTA) VALUES
('Zapatillas Nike', 79.99, 'CALZADO', 25, 'img/nike.jpg'),
('Camiseta Adidas', 29.99, 'TEXTIL', 40, 'img/camiseta.jpg'),
('Gorra Puma', 19.99, 'ACCESORIO', 15, 'img/gorra.jpg'),
('Botas Timberland', 129.99, 'CALZADO', 10, 'img/botas.jpg'),
('Sudadera Nike', 59.99, 'TEXTIL', 30, 'img/sudadera.jpg');

INSERT INTO CLIENTE (NOMBRE, EMAIL, TELEFONO, DIRECCION) VALUES
('Juan Garcia', 'juan@gmail.com', '600123456', 'Calle Mayor 12'),
('Maria Lopez', 'maria@gmail.com', '611234567', 'Calle Sol 25'),
('Carlos Martin', 'carlos@gmail.com', '622345678', 'Avenida España 8'),
('Ana Fernandez', 'ana@gmail.com', '633456789', 'Calle Real 34'),
('Pedro Sanchez', 'pedro@gmail.com', '644567890', 'Plaza Nueva 5');
