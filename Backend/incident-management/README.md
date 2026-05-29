# 🔧 Incident Management Backend - OpsCore

Sistema de gestión de incidentes para operaciones críticas. API REST desarrollada con **Spring Boot 4.0.6** y **PostgreSQL**.

---

## 📋 Tabla de Contenidos

- [Requisitos Previos](#requisitos-previos)
- [Configuración Inicial](#configuración-inicial)
- [Ejecución del Proyecto](#ejecución-del-proyecto)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [API Endpoints](#api-endpoints)
  - [Usuarios](#usuarios)
  - [Incidentes](#incidentes)
  - [Resoluciones](#resoluciones)
- [Enumeraciones](#enumeraciones)
- [Guía de Testing en Postman](#guía-de-testing-en-postman)

---

## 🔧 Requisitos Previos

- **Java 17** o superior
- **Maven 3.6+**
- **PostgreSQL 14+** (ejecutarse en Docker)
- **Postman** (para testing de APIs)

---

## 🚀 Configuración Inicial

### 1. Levantar PostgreSQL con Docker

```bash
docker-compose up -d
```

La base de datos está configurada en `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/opscore_db
spring.datasource.username=brian_dev
spring.datasource.password=opscore_pass
```

### 2. Configurar JPA/Hibernate

El archivo `application.properties` ya contiene:
```properties
spring.jpa.hibernate.ddl-auto=update  # Auto-crear tablas
spring.jpa.show-sql=true               # Ver SQL en consola
```

---

## ▶️ Ejecución del Proyecto

```bash
# Navegar al directorio del backend
cd Backend/incident-management

# Compilar y descargar dependencias
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

**URL Base:** `http://localhost:8081`

---

## 📁 Estructura del Proyecto

```
Backend/incident-management/
├── src/main/java/com/opscore/incident/
│   ├── controller/          # REST Controllers
│   │   ├── IncidenteController.java
│   │   ├── UsuarioController.java
│   │   └── ResolucionController.java
│   ├── service/             # Lógica de negocio
│   ├── model/               # Entidades JPA
│   ├── repository/          # Acceso a datos
│   ├── dto/                 # Data Transfer Objects
│   ├── enums/               # Enumeraciones
│   ├── config/              # Configuraciones
│   └── exception/           # Manejo de excepciones
├── src/main/resources/
│   └── application.properties
└── pom.xml
```

---

## 📡 API Endpoints

### **USUARIOS** (`/usuarios`)

#### 1. POST - Crear Usuario
```http
POST http://localhost:8081/usuarios
Content-Type: application/json
```

**Request Body:**
```json
{
  "nombre": "Brian Plasencia",
  "username": "brian_dev",
  "numeroReloj": "12345",
  "password": "password123",
  "rol": "TECNICO",
  "areaId": 1,
  "conectado": true,
  "disponible": true
}
```

**Response (201):**
```json
{
  "id": 5,
  "nombre": "Brian Plasencia",
  "username": "brian_dev",
  "numeroReloj": "12345",
  "rol": "TECNICO",
  "password": "password123",
  "area": {
    "id": 1,
    "nombre": "Operaciones"
  },
  "conectado": true,
  "disponible": true,
  "especialidades": null,
  "createdAt": "2026-05-28T18:44:19.7732672",
  "updatedAt": "2026-05-28T18:44:19.7732672",
  "authorities": [
    {
      "authority": "ROLE_TECNICO"
    }
  ]
}
```

---

#### 2. GET - Listar Todos los Usuarios
```http
GET http://localhost:8081/usuarios
```

**Response (200):**
```json
[
  {
    "id": 5,
    "nombre": "Brian Plasencia",
    "username": "brian_dev",
    "numeroReloj": "12345",
    "rol": "TECNICO",
    "area": {
      "id": 1,
      "nombre": "Operaciones"
    },
    "conectado": true,
    "disponible": true,
    "createdAt": "2026-05-28T18:44:19",
    "updatedAt": "2026-05-28T18:44:19"
  },
  {
    "id": 6,
    "nombre": "Carlos Operador",
    "username": "carlos_op",
    "numeroReloj": "54321",
    "rol": "OPERADOR",
    "area": {
      "id": 1,
      "nombre": "Operaciones"
    },
    "conectado": true,
    "disponible": true,
    "createdAt": "2026-05-28T18:50:00",
    "updatedAt": "2026-05-28T18:50:00"
  }
]
```

---

#### 3. GET - Buscar Usuario por Número de Reloj
```http
GET http://localhost:8081/usuarios/12345
```

**Response (200):**
```json
{
  "id": 5,
  "nombre": "Brian Plasencia",
  "username": "brian_dev",
  "numeroReloj": "12345",
  "rol": "TECNICO",
  "area": {
    "id": 1,
    "nombre": "Operaciones"
  },
  "conectado": true,
  "disponible": true,
  "createdAt": "2026-05-28T18:44:19",
  "updatedAt": "2026-05-28T18:44:19"
}
```

**Response (404) si no existe:**
```json
{
  "timestamp": "2026-05-29T10:15:23.123Z",
  "status": 404,
  "error": "Not Found",
  "message": "Usuario no encontrado",
  "path": "/usuarios/99999"
}
```

---

### **INCIDENTES** (`/incidentes`)

#### 1. POST - Reportar/Crear Incidente
```http
POST http://localhost:8081/incidentes
Content-Type: application/json
```

**Request Body:**
```json
{
  "titulo": "Servidor Principal DOWN",
  "descripcion": "El servidor de producción no responde a los ping",
  "prioridad": "CRITICA",
  "tipoFalla": "HARDWARE",
  "estadoOperativo": "PENDIENTE",
  "estadoValidacion": "PENDIENTE",
  "areaId": 1,
  "estacionId": 1,
  "operadorId": 6,
  "tecnicoId": 5
}
```

**Response (201):**
```json
{
  "id": 1,
  "titulo": "Servidor Principal DOWN",
  "descripcion": "El servidor de producción no responde a los ping",
  "prioridad": "CRITICA",
  "tipoFalla": "HARDWARE",
  "estadoOperativo": "PENDIENTE",
  "estadoValidacion": "PENDIENTE",
  "area": {
    "id": 1,
    "nombre": "Operaciones"
  },
  "estacion": {
    "id": 1,
    "nombre": "Servidor Principal"
  },
  "operador": {
    "id": 6,
    "nombre": "Carlos Operador",
    "numeroReloj": "54321"
  },
  "tecnico": {
    "id": 5,
    "nombre": "Brian Plasencia",
    "numeroReloj": "12345"
  },
  "fechaAsignacion": "2026-05-29T10:10:00",
  "fechaInicioTrabajo": null,
  "fechaResolucion": null,
  "fechaCierre": null,
  "createdAt": "2026-05-29T10:10:00",
  "updatedAt": "2026-05-29T10:10:00"
}
```

---

#### 2. GET - Listar Incidentes por Estado
```http
GET http://localhost:8081/incidentes/estado/PENDIENTE
```

**Estados válidos:** `PENDIENTE`, `EN_PROGRESO`, `RESUELTO`, `CERRADO`

**Response (200):**
```json
[
  {
    "id": 1,
    "titulo": "Servidor Principal DOWN",
    "descripcion": "El servidor de producción no responde a los ping",
    "prioridad": "CRITICA",
    "tipoFalla": "HARDWARE",
    "estadoOperativo": "PENDIENTE",
    "area": {
      "id": 1,
      "nombre": "Operaciones"
    },
    "estacion": {
      "id": 1,
      "nombre": "Servidor Principal"
    },
    "operador": {
      "id": 6,
      "nombre": "Carlos Operador"
    },
    "tecnico": {
      "id": 5,
      "nombre": "Brian Plasencia"
    },
    "fechaAsignacion": "2026-05-29T10:10:00",
    "createdAt": "2026-05-29T10:10:00"
  }
]
```

---

#### 3. GET - Listar Incidentes por Prioridad
```http
GET http://localhost:8081/incidentes/prioridad/CRITICA
```

**Prioridades válidas:** `BAJA`, `MEDIA`, `ALTA`, `CRITICA`

---

#### 4. PUT - Actualizar Estado del Incidente
```http
PUT http://localhost:8081/incidentes/1/estado?nuevoEstado=EN_PROGRESO
```

**Response (200):**
```json
{
  "id": 1,
  "titulo": "Servidor Principal DOWN",
  "estadoOperativo": "EN_PROGRESO",
  "fechaInicioTrabajo": "2026-05-29T10:15:00",
  "updatedAt": "2026-05-29T10:15:00"
}
```

---

### **RESOLUCIONES** (`/resoluciones`)

#### 1. POST - Asignar Responsable (Técnico)
```http
POST http://localhost:8081/resoluciones/asignar?incidenteId=1&usuarioId=5
```

**Response (201):**
```json
{
  "id": 1,
  "incidente": {
    "id": 1,
    "titulo": "Servidor Principal DOWN"
  },
  "usuario": {
    "id": 5,
    "nombre": "Brian Plasencia",
    "numeroReloj": "12345"
  },
  "createdAt": "2026-05-29T10:15:00"
}
```

---

#### 2. POST - Iniciar Trabajo en Piso
```http
POST http://localhost:8081/resoluciones/iniciar?incidenteId=1&tecnicoId=5
```

*Este endpoint registra la fecha de inicio del trabajo* (`fechaInicioTrabajo`)

**Response (204 No Content) o (200):**
```json
{
  "incidente": {
    "id": 1,
    "titulo": "Servidor Principal DOWN",
    "fechaInicioTrabajo": "2026-05-29T10:20:00"
  }
}
```

---

#### 3. PUT - Cerrar Incidente con Solución
```http
PUT http://localhost:8081/resoluciones/1/cerrar?descripcionSolucion=Se reinició el servidor y se validó funcionamiento
```

**Response (200):**
```json
{
  "id": 1,
  "incidente": {
    "id": 1,
    "titulo": "Servidor Principal DOWN",
    "estadoOperativo": "CERRADO",
    "fechaResolucion": "2026-05-29T10:25:00"
  },
  "descripcionSolucion": "Se reinició el servidor y se validó funcionamiento",
  "usuario": {
    "id": 5,
    "nombre": "Brian Plasencia"
  },
  "createdAt": "2026-05-29T10:25:00"
}
```

---

#### 4. POST - Resolver Incidente (Alternativo)
```http
POST http://localhost:8081/resoluciones/resolver
Content-Type: application/json
```

**Request Body:**
```json
{
  "incidenteId": 1,
  "tecnicoId": 5,
  "descripcionSolucion": "Se rebooteó el servidor y se validó funcionamiento correcto. Sistema en línea"
}
```

**Response (201):**
```json
{
  "id": 1,
  "incidente": {
    "id": 1,
    "titulo": "Servidor Principal DOWN"
  },
  "descripcionSolucion": "Se rebooteó el servidor y se validó funcionamiento correcto. Sistema en línea",
  "tecnico": {
    "id": 5,
    "nombre": "Brian Plasencia"
  },
  "createdAt": "2026-05-29T10:25:00"
}
```

---

## 📊 Enumeraciones

### **Rol**
- `ADMINISTRADOR`
- `OPERADOR`
- `TECNICO`
- `SUPERVISOR`

### **Prioridad**
- `BAJA`
- `MEDIA`
- `ALTA`
- `CRITICA`

### **TipoFalla**
- `HARDWARE`
- `SOFTWARE`
- `RED`
- `OTRO`

### **EstadoIncidente / EstadoOperativo**
- `PENDIENTE`
- `EN_PROGRESO`
- `RESUELTO`
- `CERRADO`

### **EstadoValidacion**
- `PENDIENTE`
- `VALIDADO`
- `RECHAZADO`

---

## 🧪 Guía de Testing en Postman

### **Paso 1: Crear Usuario Técnico**
```
POST http://localhost:8081/usuarios
```
```json
{
  "nombre": "Brian Plasencia",
  "username": "brian_dev",
  "numeroReloj": "12345",
  "password": "password123",
  "rol": "TECNICO",
  "areaId": 1,
  "conectado": true,
  "disponible": true
}
```

**Guardar `id` = 5** para pasos posteriores

---

### **Paso 2: Crear Usuario Operador**
```
POST http://localhost:8081/usuarios
```
```json
{
  "nombre": "Carlos Operador",
  "username": "carlos_op",
  "numeroReloj": "54321",
  "password": "password123",
  "rol": "OPERADOR",
  "areaId": 1,
  "conectado": true,
  "disponible": true
}
```

**Guardar `id` = 6**

---

### **Paso 3: Crear Incidente**
```
POST http://localhost:8081/incidentes
```
```json
{
  "titulo": "Servidor Principal DOWN",
  "descripcion": "El servidor de producción no responde",
  "prioridad": "CRITICA",
  "tipoFalla": "HARDWARE",
  "estadoOperativo": "PENDIENTE",
  "estadoValidacion": "PENDIENTE",
  "areaId": 1,
  "estacionId": 1,
  "operadorId": 6,
  "tecnicoId": 5
}
```

**Guardar `id` = 1**

---

### **Paso 4: Asignar Responsable**
```
POST http://localhost:8081/resoluciones/asignar?incidenteId=1&usuarioId=5
```

---

### **Paso 5: Iniciar Trabajo**
```
POST http://localhost:8081/resoluciones/iniciar?incidenteId=1&tecnicoId=5
```

---

### **Paso 6: Cerrar/Resolver Incidente**
```
PUT http://localhost:8081/resoluciones/1/cerrar?descripcionSolucion=Se reinició el servidor y se validó el funcionamiento
```

---

### **Paso 7: Verificar Incidente Cerrado**
```
GET http://localhost:8081/incidentes/estado/CERRADO
```

---

## ⚙️ Configuración Avanzada

### Variables de Entorno (Opcional)

```bash
export DB_HOST=localhost
export DB_PORT=5433
export DB_NAME=opscore_db
export DB_USER=brian_dev
export DB_PASSWORD=opscore_pass
export SERVER_PORT=8081
```

---

## 🐛 Troubleshooting

### Error: `Connection refused` en PostgreSQL
```bash
# Verificar que Docker está corriendo
docker ps

# Reiniciar contenedor
docker-compose restart
```

### Error: `Port already in use :8081`
```bash
# Cambiar puerto en application.properties
server.port=8082
```

### Error: `Tabla no encontrada`
Verificar que `spring.jpa.hibernate.ddl-auto=update` esté configurado.

---

## 📚 Referencias

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)

---

## 👨‍💻 Autor

**Brian Plasencia** - Equipo OpsCore 21

---

## 📝 Licencia

Este proyecto es parte de S04-26-Equipo-21-Web-App-OpsCore
