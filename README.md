Servicio de Registro de Usuarios
================================

Este proyecto es una API RESTful desarrollada con Spring Boot para la gestión de usuarios, incluyendo funcionalidades de registro, validación de datos y manejo de errores.

🚀 Tecnologías Utilizadas
-------------------------

*   **Java 17**

*   **Spring Boot 3.x**

*   **Spring Data JPA**

*   **Spring Security**

*   **H2 Database**

*   **Lombok**

*   **Gradle**

*   **Swagger/OpenAPI 3**

*   **JUnit 5** y **Mockito**


🛠️ Cómo Empezar
----------------

### Prerrequisitos

Asegúrate de tener instalado lo siguiente:

*   **Java Development Kit (JDK) 17 o superior**

*   **Gradle 8.x o superior**


### Instalación y Configuración

1.  git clone cd user-service

2.  Si deseas usar una base de datos externa (ej. PostgreSQL), deberás modificar el archivo src/main/resources/application.properties o crear un perfil de Spring específico (application-prod.properties).


### Ejecutar la Aplicación

#### Opción 1: Ejecutar como Aplicación Spring Boot

1.  ./gradlew clean build Esto generará el archivo JAR en build/libs/.

2.  java -jar build/libs/user-service-0.0.1-SNAPSHOT.jar


La aplicación se iniciará en http://localhost:8080.

#### Opción 2: Ejecutar con Docker Compose (Recomendado para Despliegue Local)

1.  **Asegúrate de tener el Dockerfile y docker-compose.yml en la raíz de tu proyecto.**

2.  docker compose up -d --build Esto construirá la imagen Docker de tu aplicación (si no existe o ha cambiado) y luego iniciará el contenedor.


La aplicación estará accesible en http://localhost:8080.

📄 Documentación de la API (Swagger UI)
---------------------------------------

Una vez que la aplicación esté en ejecución, puedes acceder a la documentación interactiva de la API a través de Swagger UI:

*   **URL de Swagger UI:** http://localhost:8080/swagger-ui.html

*   **URL de la especificación OpenAPI:** http://localhost:8080/v3/api-docs


La documentación incluye detalles sobre los endpoints, modelos de datos, y permite probar las solicitudes directamente desde el navegador.

🧪 Pruebas Unitarias
--------------------

El proyecto incluye pruebas unitarias para las capas de Service, Controller y Repository.

Para ejecutar todas las pruebas:

`   ./gradlew test   `

⚠️ Manejo de Errores
--------------------

La API implementa un manejo de errores robusto para proporcionar respuestas claras al cliente:

*   **400 Bad Request**: Para errores de validación de entrada (ej. campos vacíos, formato de email/contraseña incorrecta).

*   **409 Conflict**: Cuando se intenta registrar un usuario con un correo electrónico que ya existe.

*   **500 Internal Server Error**: Para errores inesperados en el servidor.


Las respuestas de error siguen un formato estandarizado (CustomErrorResponse) para facilitar el consumo por parte del cliente.

🔑 Configuración de Seguridad
-----------------------------

La aplicación utiliza Spring Security para:

*   **Codificación de Contraseñas:** Las contraseñas de los usuarios se codifican utilizando BCryptPasswordEncoder antes de ser almacenadas en la base de datos.

*   **Seguridad Básica de Endpoints:** Los endpoints de registro (/v1/users/register), Swagger UI (/swagger-ui/\*\*) y la especificación OpenAPI (/v3/api-docs/\*\*) están configurados para ser accesibles públicamente. Cualquier otro endpoint requerirá autenticación (aunque la autenticación JWT completa no está implementada en este alcance, el campo token en la entidad User sugiere su futura integración).

*   **Sesiones Stateless:** La gestión de sesiones está configurada como STATELESS, lo que es ideal para APIs RESTful que utilizan tokens.