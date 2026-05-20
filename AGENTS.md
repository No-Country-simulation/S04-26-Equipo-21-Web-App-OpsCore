# AGENTS.md

## Resumen del proyecto
Este repositorio es un monorepo con dos partes principales:

- **Backend**: aplicación Java Spring Boot ubicada en `Backend/incident-management`.
- **Frontend**: aplicación React + Vite ubicada en `Frontend/incident-front`.

El backend está gestionado con Maven y el frontend con npm/Vite.

## Lenguajes y tecnologías clave

- Backend: Java 17, Spring Boot 4.0.6, Maven, Lombok, Flyway, PostgreSQL.
- Frontend: TypeScript 6, React 19, Vite 8, Tailwind CSS 4.

## Comandos principales

- Backend:
  - `cd Backend/incident-management && ./mvnw clean test`
  - `cd Backend/incident-management && ./mvnw spring-boot:run`
- Frontend:
  - `cd Frontend/incident-front && npm install`
  - `cd Frontend/incident-front && npm run build`
  - `cd Frontend/incident-front && npm run dev`

## Estructura relevante

- `Backend/incident-management/src/main/java`: código fuente Java.
- `Backend/incident-management/src/main/resources/application.properties`: configuración principal del backend.
- `Backend/incident-management/src/test/java`: pruebas unitarias/integración del backend.
- `Frontend/incident-front/src`: aplicación React/TypeScript.
- `Frontend/incident-front/package.json`: dependencias y scripts del frontend.

## Convenciones específicas

- Usa el wrapper de Maven (`Backend/incident-management/mvnw`) para el backend.
- El backend se organiza en capas clásicas: `controller`, `service`, `repository`, `config`, `dto`, `mapper`, `exception`, `security`, `scheduler`.
- El frontend usa Vite + React y mantiene componentes en `src/components`, páginas en `src/pages` y proveedores en `src/app/providers`.
- No modifiques `target/` ni `node_modules/`.

## Documentación útil

- [README principal](README.md)
- [Backend README](Backend/Readme.md)
- [Frontend README](Frontend/incident-front/README.md)

## Cómo ayudar a este repositorio

- Priorizar cambios en el backend o frontend según el contexto de la tarea.
- Normalizar nombres de paquetes y rutas según `com.opscore.incident` en el backend.
- Para problemas de compilación en el backend, revisar primero `pom.xml` y plugins de Maven.
- Para problemas de tipo en el frontend, revisar primero `tsconfig.json` y `package.json`.
