# Nombres Integrantes
#### Nitchen Martinez
#### Domingo Velazquez
#### Areliz Isla

# Ecomarket Microservices

Este proyecto implementa una arquitectura de microservicios para la gestión de un e-commerce, incluyendo usuarios, ventas, productos y sucursales.

Usamos los microservicios con nombres en ingles para respetar la notación y...
aprender inglés(?)

## Base de Datos DEV

```sql
CREATE DATABASE ecomarketdtbs;
```

## Base de Datos TEST
```sql
CREATE DATABASE ecomarketdtbs_test;
--y solo por si acaso
ALTER USER 'root'@'localhost' IDENTIFIED BY '';
```

## Microservicios y Endpoints

- **Usuarios**
  - URL: [http://localhost:8050/api/v1/users](http://localhost:8050/api/v1/users)
  - URL: [http://localhost:8050/api/v2/users](http://localhost:8050/api/v2/users)
- **Ventas**
  - URL: [http://localhost:9090/api/v1/sale](http://localhost:9090/api/v1/sale)
- **Productos**
  - URL: [http://localhost:8060/api/v1/product](http://localhost:8060/api/v1/product)
- **Sucursales**
  - URL: [http://localhost:8070/api/v1/branches](http://localhost:8070/api/v1/branches)
  - URL: [http://localhost:8070/api/v2/branches](http://localhost:8070/api/v2/branches)

## Gateway

- URL: [http://localhost:8086](http://localhost:8086)

## Ruta definida para cada microservicio para visualizar la documentación

- **Usuarios**
  - URL: [http://localhost:8050/doc/swagger-ui/index.html](http://localhost:8050/doc/swagger-ui/index.html)
- **Sucursales**
  - URL: [http://localhost:8070/doc/swagger-ui/index.html](http://localhost:8070/doc/swagger-ui/index.html)

## Requisitos

- Java
- Maven

## Ejecución

1. Clona el repositorio.
2. Crea la base de datos ejecutando el script SQL en Laragon, tanto el de dev como el de test.
3. Accede a los endpoints usando las URLs listadas arriba.

## Estructura del Proyecto

- `microservice-user`: Gestión de usuarios
- `microservice-sale`: Gestión de ventas
- `microservice-product`: Gestión de productos
- `microservice-branch`: Gestión de sucursales
- `microservice-gateway`: Gateway API
- `microservice-eureka`: Service discovery
- `microservice-config`: Configuración centralizada


## Maven terminal

- `microservice-user`: mvn install -pl microservice-user || mvn install -pl microservice-user -am -DskipTests
- `microservice-branch`: mvn install -pl microservice-branch || mvn install -pl microservice-branch -am -DskipTests

---
![Ecomarket drawio](https://github.com/user-attachments/assets/0c0f2a14-3ab4-487c-809f-272082edeb09)


## IMPORT SQL que se eliminaron al integrar los test

- User
```sql
INSERT INTO users (id, name, email, rol) VALUES (1, 'Ana López', 'ana@example.com', 'ADMIN');
INSERT INTO users (id, name, email, rol) VALUES (2, 'Juan Pérez', 'juan@example.com', 'USER');
INSERT INTO users (id, name, email, rol) VALUES (3, 'Lucía Gómez', 'lucia@example.com', 'USER');
INSERT INTO users (id, name, email, rol) VALUES (4, 'Carlos Ruiz', 'carlos@example.com', 'USER');
INSERT INTO users (id, name, email, rol) VALUES (5, 'María Fernández', 'maria@example.com', 'USER');
INSERT INTO users (id, name, email, rol) VALUES (6, 'Pedro Salas', 'pedro@example.com', 'USER');
INSERT INTO users (id, name, email, rol) VALUES (7, 'Elena Torres', 'elena@example.com', 'USER');
INSERT INTO users (id, name, email, rol) VALUES (8, 'David Navas', 'david@example.com', 'USER');
INSERT INTO users (id, name, email, rol) VALUES (9, 'Laura Díaz', 'laura@example.com', 'USER');
INSERT INTO users (id, name, email, rol) VALUES (10, 'Mateo Ríos', 'mateo@example.com', 'ADMIN');
```
- Branch
```sql
INSERT INTO branches (name, address, city, country) VALUES ('Sucursal Central', 'Avenida Libertador 1234', 'Santiago', 'Chile');
INSERT INTO branches (name, address, city, country) VALUES ('Sucursal Norte', 'Rua das Flores 456', 'Santiago', 'Chile');
INSERT INTO branches (name, address, city, country) VALUES ('Sucursal Este', 'Calle de la Paz 789', 'Santiago', 'Chile');
INSERT INTO branches (name, address, city, country) VALUES ('Sucursal Sur', 'Rue de Rivoli 101', 'Santiago', 'Chile');
INSERT INTO branches (name, address, city, country) VALUES ('Sucursal Italia', 'Via Roma 321', 'Santiago', 'Chile');
INSERT INTO branches (name, address, city, country) VALUES ('Sucursal Oeste', 'Calle del Sol 654', 'Santiago', 'Chile');
```
