# Nombres Integrantes
#### Nitchen Martinez
#### Domingo Velazquez
#### Areliz Isla

# Ecomarket Microservices

Este proyecto implementa una arquitectura de microservicios para la gestión de un e-commerce, incluyendo usuarios, ventas, productos y sucursales.

Usamos los microservicios con nombres en ingles para respetar la notación y...
aprender inglés(?)

## Base de Datos

```sql
CREATE DATABASE ecomarketdtbs;
```

## Microservicios y Endpoints

- **Usuarios**
  - URL: [http://localhost:8050/api/v1/users](http://localhost:8050/api/v1/users)
- **Ventas**
  - URL: [http://localhost:9090/api/v1/sale](http://localhost:9090/api/v1/sale)
- **Productos**
  - URL: [http://localhost:8060/api/v1/product](http://localhost:8060/api/v1/product)
- **Sucursales**
  - URL: [http://localhost:8070/api/v1/branches](http://localhost:8070/api/v1/branches)

## Gateway

- URL: [http://localhost:8086](http://localhost:8086)

## Requisitos

- Java
- Maven

## Ejecución

1. Clona el repositorio.
2. Crea la base de datos ejecutando el script SQL en Laragon.
3. Accede a los endpoints usando las URLs listadas arriba.

## Estructura del Proyecto

- `microservice-user`: Gestión de usuarios
- `microservice-sale`: Gestión de ventas
- `microservice-product`: Gestión de productos
- `microservice-branch`: Gestión de sucursales
- `microservice-gateway`: Gateway API
- `microservice-eureka`: Service discovery
- `microservice-config`: Configuración centralizada

---
![Ecomarket drawio](https://github.com/user-attachments/assets/0c0f2a14-3ab4-487c-809f-272082edeb09)

