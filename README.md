# Task Tracker (CLI + API)

Aplicación en Spring Boot para crear, listar y actualizar tareas desde línea de comando (CLI) o HTTP‑API.

## Tecnologías usadas
- Java 17, Spring Boot
- Spring Web / REST API
- Spring Data JPA con PostgreSQL (Supabase)
- (Opcional) Spring Security
- Maven o Gradle

## Configuración
1. Crea un proyecto en Supabase.
2. Copia el JDBC URL tipo *session pooler*.
3. Exporta variables:
   ```bash
   export SPRING_DATASOURCE_URL="jdbc:postgresql://..."
   export SPRING_DATASOURCE_USERNAME="postgres"
   export SPRING_DATASOURCE_PASSWORD="..."
   
4. Clona este repositorio y construye con mvn clean package.

   Uso en CLI (simulando el Task Tracker CLI del roadmap.sh)

   ```bash
   java -jar target/task-tracker.jar add "Mi tarea"
   java -jar target/task-tracker.jar list
   java -jar target/task-tracker.jar mark-done <id>

5. API REST

   ```bash
   POST /tasks ➝ crea tarea
   GET /tasks ➝ lista tareas
   GET /tasks/{id} ➝ ver una tarea
   PUT /tasks/{id} ➝ actualizar descripción o estado
   DELETE /tasks/{id} ➝ eliminar tarea
