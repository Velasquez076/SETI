# Proyecto Base Implementando Clean Architecture

## Antes de Iniciar

Empezaremos por explicar los diferentes componentes del proyectos y partiremos de los componentes externos, continuando con los componentes core de negocio (dominio) y por último el inicio y configuración de la aplicación.

# Arquitectura

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

## Domain

Es el módulo más interno de la arquitectura, pertenece a la capa del dominio y encapsula la lógica y reglas del negocio mediante modelos y entidades del dominio.

## Usecases

Este módulo gradle perteneciente a la capa del dominio, implementa los casos de uso del sistema, define lógica de aplicación y reacciona a las invocaciones desde el módulo de entry points, orquestando los flujos hacia el módulo de entities.

## Infrastructure

### Driven Adapters

Los driven adapter representan implementaciones externas a nuestro sistema, como lo son conexiones a servicios rest,
soap, bases de datos, lectura de archivos planos, y en concreto cualquier origen y fuente de datos con la que debamos
interactuar.

### Entry Points

Los entry points representan los puntos de entrada de la aplicación o el inicio de los flujos de negocio.

## Application

Este módulo es el más externo de la arquitectura, es el encargado de ensamblar los distintos módulos, resolver las dependencias y crear los beans de los casos de use (UseCases) de forma automática, inyectando en éstos instancias concretas de las dependencias declaradas. Además inicia la aplicación (es el único módulo del proyecto donde encontraremos la función “public static void main(String[] args)”.


# Sistema de Gestión de Franquicias, Sucursales y Productos

Este proyecto fue desarrollado utilizando el [Scaffold de Bancolombia](https://github.com/Bancolombia/scaffold-clean-architecture), con arquitectura limpia y programación reactiva usando **Spring WebFlux**. La base de datos utilizada es **PostgreSQL**.

## Tecnologías

- Java 21
- Spring Boot 3+
- Spring WebFlux
- PostgreSQL
- Docker
- Reactive PostgreSQL Driver (`r2dbc-postgresql`)
- Gradle

## Módulos principales

- **Franquicias**: gestión de franquicias comerciales.
- **Sucursales**: administración de sucursales asociadas a una franquicia.
- **Productos**: catálogo de productos disponibles por franquicia/sucursal.

## Scripts Base de datos
A continuación se muestra un ejemplo del script para crear las tablas necesarias en PostgreSQL:  
**Pdt:** Ejecutar este comando Docker para crear contenedor PostgreSQL: 
  
( **docker run -d --name postgres -p 5432:5432 -e POSTGRES_USER={your-user} -e POSTGRES_PASSWORD={your-password} postgres** )  

Estos están ubicados en la siguiente URL:  
https://github.com/Velasquez076/SETI/blob/master/applications/app-service/src/main/resources/db.sql  

![image](https://github.com/user-attachments/assets/14d5510a-30cf-4860-b8b4-04394f80ec24)

---
## Variables de entorno 
| Variable   | Descripción                      | Ejemplo         |
| ---------- | -------------------------------- | --------------- |
| `HOST`     | Dirección del servidor           | `localhost`     |
| `PORT`     | Puerto del servicio PostgreSQL   | `5432`          |
| `DB`       | Nombre de la base de datos       | `my_company_db` |
| `SCHEMA`   | Esquema de trabajo en PostgreSQL | `public`        |
| `USERNAME` | Usuario de la base de datos      | `postgres`      |
| `PASSWORD` | Contraseña del usuario           | `admin123`      |

## Endpoints
Todos los endpoints devuelven respuestas en formato JSON y están implementados con Spring WebFlux (reactivos).

### Franquicias
	        
- **POST:** /api/franchise - (Crear una nueva franquicia)
- **PUT:**  /api/franchise/{id}	 -  (Actualizar una franquicia)

### Branch
- **POST:** /api/branch - (Crear una nueva sucursal asociada a una franquicia)
- **PUT:** /api/branch/{id} - (Actualizar el nombre de la sucursal)
  
### Product
- **GET:** /api/products?id-franchise={id} - (Consultar los productos con mas stock de cada sucursal por franquicia)
- **POST:** /api/products  - (Crear un nuevo producto asociado a una sucursal)
- **PATCH:** /api/products/name/{id} - (Actualiza el nombre del producto)
- **PATCH:** /api/products/stock/{id} - (Actualiza el stock de productos)
- **DELETE:** /api/products/{id} - (Elimina el producto)
---

## Pruebas Unitarias

El proyecto incluye pruebas unitarias enfocadas en validar el comportamiento de la lógica de negocio de forma aislada, especialmente en la capa del dominio (casos de uso y entidades). Estas pruebas garantizan que cada componente funcione correctamente de manera independiente, sin depender de conexiones externas como bases de datos o servicios.
Este cuenta con un total de ***59*** pruebas automatizadas, combinando pruebas unitarias e integrales, alcanzando una cobertura del ***70.4%*** del código. 

## Tecnologías utilizadas
- **JUnit 5:** Framework principal para escribir pruebas.
- **WebTestClient:**  Cliente reactivo para pruebas de APIs WebFlux
- **Mockito:** Para simular dependencias externas (como repositorios o servicios).

## 🧠 Análisis Estático con SonarQube
Se utilizó SonarQube para realizar un análisis estático del código fuente con el objetivo de mejorar la calidad del software y asegurar el cumplimiento de buenas prácticas.

## ✅ Objetivos del análisis:
- Identificar code smells, bugs y vulnerabilidades. 
- Medir la cobertura de pruebas unitarias e integración. 
- Evaluar la mantenibilidad, seguridad y confiabilidad del proyecto.

## 🚀 Análisis:  

![image](https://github.com/user-attachments/assets/1987a9b0-5f15-49c8-9fda-5e308ac29157)

## Colección de Postman:  

[SETI.postman_collection.json](https://github.com/user-attachments/files/20353955/SETI.postman_collection.json)
