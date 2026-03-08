[![Typing SVG](https://readme-typing-svg.demolab.com?font=Fira+Code&pause=1000&color=2E9BF7&width=435&lines=LiterAlura+%F0%9F%93%9A;Spring+Boot+%2B+Gutendex+API)](https://git.io/typing-svg)

# LiterAlura

![arrowRight](https://readmecodegen.vercel.app/api/social-icon?name=arrowRight&size=16&color=%232e9bf7) Este repositorio es parte de un challenge de backend en el cual estoy participando!

![arrowRight](https://readmecodegen.vercel.app/api/social-icon?name=arrowRight&size=16&color=%232e9bf7) Tecnologías utilizadas:

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-green?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-blue?logo=postgresql)
![API](https://img.shields.io/badge/API-Gutendex-green)
![Jackson](https://img.shields.io/badge/Jackson-Databind-red)

![arrowRight](https://readmecodegen.vercel.app/api/social-icon?name=arrowRight&size=16&color=%232e9bf7) Funcionamiento:

1. Ejecuta el programa desde VS Code o la terminal
2. Selecciona una de las 5 opciones del menú interactivo
3. **Buscar libro por título**: consulta la API de Gutendex en tiempo real y guarda el libro en PostgreSQL
4. **Listar libros registrados**: muestra todos los libros guardados en la base de datos
5. **Listar autores registrados**: muestra todos los autores sin duplicados
6. **Listar autores vivos en un año**: filtra autores que estaban vivos en el año ingresado
7. **Listar libros por idioma**: filtra libros por idioma (ES, EN, FR, PT)
8. Sigue explorando o elige la opción 0 para salir

![arrowRight](https://readmecodegen.vercel.app/api/social-icon?name=arrowRight&size=16&color=%232e9bf7) Configuración:

Antes de ejecutar, asegúrate de tener PostgreSQL instalado y crea la base de datos:
```sql
CREATE DATABASE ["Nombre_de_la_base_de_datos_aqui"];
```

Configura las credenciales en `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:["puerto_aqui"]/literalura_db
spring.datasource.username=["nombre_de_usuario"]
spring.datasource.password=["contrasena"]
```

![arrowRight](https://readmecodegen.vercel.app/api/social-icon?name=arrowRight&size=16&color=%232e9bf7) Ejecución:

```bash
.\mvnw.cmd spring-boot:run (en caso de ejecutar spring-boot con Maven en local)
```

