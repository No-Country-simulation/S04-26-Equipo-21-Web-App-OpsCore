# 📊 Documentación de Base de Datos - OpsCore

## 📑 Tabla de Contenidos
1. [Descripción General](#descripción-general)
2. [Tablas y Campos](#tablas-y-campos)
3. [Relaciones](#relaciones)
4. [Diagrama ER](#diagrama-entidad-relación)
5. [Script SQL](#script-sql-completo)

---

## 🎯 Descripción General

El sistema OpsCore es una **plataforma de gestión de incidentes** para ambientes de manufactura. La base de datos fue diseñada para:

- ✅ Registrar incidentes en estaciones de trabajo
- ✅ Asignar técnicos para resolver problemas
- ✅ Ejecutar checklists de mantenimiento preventivo
- ✅ Registrar resoluciones y métricas de desempeño
- ✅ Organizar usuarios y áreas de trabajo

---

## 📋 Tablas y Campos

### 1. **AREAS** 
*Representa los departamentos o zonas de la planta*

| Campo | Tipo | Constraints | Descripción |
|-------|------|-------------|-------------|
| `id` | BIGSERIAL | PRIMARY KEY | Identificador único |
| `nombre` | VARCHAR(100) | NOT NULL | Nombre del área (Ej: Ensamble, Pintura) |

**Ejemplo:**
```
Línea de Ensamble
Área de Pintura
Almacén
```

---

### 2. **USUARIOS**
*Almacena la información de los usuarios del sistema*

| Campo | Tipo | Constraints | Descripción |
|-------|------|-------------|-------------|
| `id` | BIGSERIAL | PRIMARY KEY | Identificador único |
| `nombre` | VARCHAR(100) | NOT NULL | Nombre completo del usuario |
| `numero_reloj` | VARCHAR(50) | UNIQUE, NOT NULL | ID único de la empresa |
| `rol` | VARCHAR(20) | NOT NULL | OPERADOR, SUPERVISOR, TECNICO, GERENTE |
| `password` | VARCHAR(255) | - | Contraseña encriptada |
| `area_id` | BIGINT | FOREIGN KEY → areas(id) | Área asignada del usuario |

**Roles disponibles:**
- 👨‍🔧 **OPERADOR**: Opera las máquinas (puede reportar incidentes)
- 👀 **SUPERVISOR**: Supervisa operadores y máquinas
- 🔨 **TECNICO**: Resuelve incidentes técnicos
- 👔 **GERENTE**: Acceso total y análisis de métricas

---

### 3. **ESTACIONES_TRABAJO**
*Representa las máquinas o estaciones de producción*

| Campo | Tipo | Constraints | Descripción |
|-------|------|-------------|-------------|
| `id` | BIGSERIAL | PRIMARY KEY | Identificador único |
| `nombre` | VARCHAR(100) | NOT NULL | Nombre de la máquina |
| `codigo` | VARCHAR(50) | UNIQUE, NOT NULL | Código único (Ej: EST-001) |
| `area_id` | BIGINT | FOREIGN KEY → areas(id) | Área donde está ubicada |

**Ejemplo:**
```
EST-001 → Soldadora A1 → Área: Ensamble
EST-002 → Pintura Robot B2 → Área: Pintura
EST-003 → Empaque Manual C1 → Área: Empaque
```

---

### 4. **INCIDENTES**
*Registro de todos los problemas reportados en estaciones de trabajo*

| Campo | Tipo | Constraints | Descripción |
|-------|------|-------------|-------------|
| `id` | BIGSERIAL | PRIMARY KEY | Identificador único |
| `estacion_id` | BIGINT | NOT NULL, FK → estaciones_trabajo(id) | Máquina donde ocurrió |
| `reportado_por_id` | BIGINT | NOT NULL, FK → usuarios(id) | Usuario que reportó |
| `tecnico_asignado_id` | BIGINT | FK → usuarios(id) | Técnico encargado de resolver |
| `descripcion` | TEXT | NOT NULL | Descripción del problema |
| `fecha_reporte` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Cuándo se reportó |
| `fecha_cierre` | TIMESTAMP | - | Cuándo se resolvió |
| `estado` | VARCHAR(20) | NOT NULL, DEFAULT: 'ABIERTO' | ABIERTO → ASIGNADO → RESUELTO → CERRADO |
| `prioridad` | VARCHAR(20) | NOT NULL, DEFAULT: 'NORMAL' | NORMAL o CRITICO |
| `solucion_tecnica` | TEXT | - | Descripción de cómo se resolvió |

**Flujo de Estados:**
```
ABIERTO (se reporta) 
    ↓
ASIGNADO (se asigna técnico)
    ↓
RESUELTO (técnico lo arregla)
    ↓
CERRADO (se verifica y cierra)
```

---

### 5. **CHECKLISTS_EJECUCION**
*Encabezado de cada ejecución de checklist de mantenimiento*

| Campo | Tipo | Constraints | Descripción |
|-------|------|-------------|-------------|
| `id` | BIGSERIAL | PRIMARY KEY | Identificador único |
| `estacion_id` | BIGINT | NOT NULL, FK → estaciones_trabajo(id) | Máquina auditada |
| `operador_id` | BIGINT | NOT NULL, FK → usuarios(id) | Operador que realizó checklist |
| `fecha_realizacion` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Cuándo se ejecutó |

**Propósito:** Mantenimiento preventivo diario/semanal

---

### 6. **RESPUESTAS_PUNTOS_CONTROL**
*Detalles de cada punto del checklist (relación 1:N con CHECKLISTS_EJECUCION)*

| Campo | Tipo | Constraints | Descripción |
|-------|------|-------------|-------------|
| `id` | BIGSERIAL | PRIMARY KEY | Identificador único |
| `checklist_id` | BIGINT | NOT NULL, FK → checklists_ejecucion(id) ON DELETE CASCADE | Checklist padre |
| `categoria` | VARCHAR(50) | - | Mecánica, Eléctrica, Hidráulica, etc. |
| `descripcion_punto` | VARCHAR(255) | NOT NULL | Qué se revisa (Ej: "Aceite del motor") |
| `es_critico` | BOOLEAN | DEFAULT FALSE | ¿Es un punto crítico? |
| `estado_ok` | BOOLEAN | DEFAULT TRUE | ¿Está OK o no? |
| `observaciones` | TEXT | - | Notas adicionales |

**Ejemplo:**
```
✓ Nivel de aceite → OK
✗ Correa de transmisión → Desgastada, cambiar urgente
✓ Pernos de anclaje → OK
```

---

## 🔗 Relaciones

### Diagrama de Relaciones Simplificado

```
┌─────────────┐
│   AREAS     │
└──────┬──────┘
       │ 1:N
       ├─────────────────────┬──────────────────┐
       │                     │                  │
    (area_id)            (area_id)          (area_id)
       │                     │                  │
   ┌───▼──────┐        ┌────▼────────┐   ┌────▼──────────┐
   │ USUARIOS  │        │ESTACIONES   │   │(area en usuarios)
   │           │        │TRABAJO      │   │                │
   └─────┬──────┘        └────┬───────┘   └────────────────┘
         │ 1:N                │ 1:N
    (reportado_por_id)   (estacion_id)
    (tecnico_asignado_id) (estacion_id)
         │                    │
         └─────────┬──────────┘
                   │
              ┌────▼──────────┐
              │  INCIDENTES   │
              └────────────────┘

    (operador_id)
    (estacion_id)
         │
    ┌────▼─────────────────────┐
    │CHECKLISTS_EJECUCION      │
    └────┬─────────────────────┘
         │ 1:N
    (checklist_id)
         │
    ┌────▼──────────────────────────┐
    │RESPUESTAS_PUNTOS_CONTROL      │
    └────────────────────────────────┘
```

### Detalle de Relaciones

| Tabla Origen | Tabla Destino | Tipo | Foreign Key | Cascada | Descripción |
|--------------|---------------|------|-------------|---------|-------------|
| AREAS | USUARIOS | 1:N | area_id | NO | Un área tiene muchos usuarios |
| AREAS | ESTACIONES_TRABAJO | 1:N | area_id | NO | Un área tiene muchas máquinas |
| USUARIOS | INCIDENTES | 1:N | reportado_por_id | NO | Un usuario reporta muchos incidentes |
| USUARIOS | INCIDENTES | 1:N | tecnico_asignado_id | NO | Un técnico resuelve muchos incidentes |
| USUARIOS | CHECKLISTS_EJECUCION | 1:N | operador_id | NO | Un operador realiza muchos checklists |
| ESTACIONES_TRABAJO | INCIDENTES | 1:N | estacion_id | NO | Una máquina tiene muchos incidentes |
| ESTACIONES_TRABAJO | CHECKLISTS_EJECUCION | 1:N | estacion_id | NO | Una máquina tiene muchos checklists |
| CHECKLISTS_EJECUCION | RESPUESTAS_PUNTOS_CONTROL | 1:N | checklist_id | **SÍ** | Un checklist tiene muchas respuestas |

**⚠️ Nota sobre CASCADE:** Solo RESPUESTAS_PUNTOS_CONTROL usa ON DELETE CASCADE porque es un detalle que no puede existir sin su encabezado.

---

## 📊 Diagrama Entidad-Relación

```
╔══════════════════════════════════════════════════════════════════════════╗
║                        DIAGRAMA ER - OpsCore                            ║
╚══════════════════════════════════════════════════════════════════════════╝

                                 ┌──────────────┐
                                 │    AREAS     │
                                 ├──────────────┤
                                 │ id (PK)      │
                                 │ nombre       │
                                 └──────┬───────┘
                                        │
                    1 ┌─────────────────┼─────────────────┐ 1
                      │                 │                 │
                    N │                 │                 │ N
                      │                 │                 │
         ┌────────────▼────────┐  ┌────▼──────────────┐ ┌──▼───────────┐
         │    USUARIOS         │  │ESTACIONES_TRABAJO │ │   (details)  │
         ├─────────────────────┤  ├───────────────────┤ └──────────────┘
         │ id (PK)             │  │ id (PK)           │
         │ nombre              │  │ nombre            │
         │ numero_reloj        │  │ codigo (UNIQUE)   │
         │ rol                 │  │ area_id (FK)      │
         │ password            │  └─────┬──────────────┘
         │ area_id (FK) ◄──────┘        │
         └────────┬──────────┬──────────┘
                  │          │
                  │ 1:N      │ 1:N
     reportado_por│          │estacion_id
     tecnico_....│          │
                  │          │
                  └────┬─────┘
                       │
         ┌─────────────▼────────────────┐
         │     INCIDENTES              │
         ├─────────────────────────────┤
         │ id (PK)                     │
         │ estacion_id (FK) ────────┐  │
         │ reportado_por_id (FK) ──┐│  │
         │ tecnico_asignado_id (FK)││  │
         │ descripcion             ││  │
         │ fecha_reporte           ││  │
         │ fecha_cierre            ││  │
         │ estado                  ││  │
         │ prioridad               ││  │
         │ solucion_tecnica        ││  │
         └─────────────────────────┬┬──┘
                                   ││
                        1:N ────────┘│
                        operador_id  │
                                     │ 1:N
                        ┌────────────▼───────────────────┐
                        │  CHECKLISTS_EJECUCION         │
                        ├───────────────────────────────┤
                        │ id (PK)                       │
                        │ estacion_id (FK) ─────────────┤
                        │ operador_id (FK) ────────────┘│
                        │ fecha_realizacion             │
                        └────┬─────────────────────────┘
                             │
                             │ 1:N
                        checklist_id
                             │
                ┌────────────▼───────────────────────┐
                │ RESPUESTAS_PUNTOS_CONTROL         │
                ├────────────────────────────────────┤
                │ id (PK)                            │
                │ checklist_id (FK) [ON DELETE CASCADE]
                │ categoria                          │
                │ descripcion_punto                  │
                │ es_critico                         │
                │ estado_ok                          │
                │ observaciones                      │
                └────────────────────────────────────┘

Leyenda:
  (PK) = Primary Key (Clave Primaria)
  (FK) = Foreign Key (Clave Foránea)
  1:N = Relación Uno a Muchos
```

---

## 🗄️ Script SQL Completo

```sql
-- ============================================
-- BASE DE DATOS: OpsCore - Gestión de Incidentes
-- ============================================

-- Tabla de Áreas
CREATE TABLE areas (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Tabla de Usuarios
CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    numero_reloj VARCHAR(50) UNIQUE NOT NULL,
    rol VARCHAR(20) NOT NULL, -- OPERADOR, SUPERVISOR, TECNICO, GERENTE
    password VARCHAR(255),
    area_id BIGINT REFERENCES areas(id)
);

-- Tabla de Estaciones de Trabajo (Máquinas)
CREATE TABLE estaciones_trabajo (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    codigo VARCHAR(50) UNIQUE NOT NULL,
    area_id BIGINT REFERENCES areas(id)
);

-- Tabla de Incidentes
CREATE TABLE incidentes (
    id BIGSERIAL PRIMARY KEY,
    estacion_id BIGINT NOT NULL REFERENCES estaciones_trabajo(id),
    reportado_por_id BIGINT NOT NULL REFERENCES usuarios(id),
    tecnico_asignado_id BIGINT REFERENCES usuarios(id),
    descripcion TEXT NOT NULL,
    fecha_reporte TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_cierre TIMESTAMP,
    estado VARCHAR(20) NOT NULL DEFAULT 'ABIERTO', -- ABIERTO, ASIGNADO, RESUELTO, CERRADO
    prioridad VARCHAR(20) NOT NULL DEFAULT 'NORMAL', -- NORMAL, CRITICO
    solucion_tecnica TEXT
);

-- Tabla de Encabezado de Checklist
CREATE TABLE checklists_ejecucion (
    id BIGSERIAL PRIMARY KEY,
    estacion_id BIGINT NOT NULL REFERENCES estaciones_trabajo(id),
    operador_id BIGINT NOT NULL REFERENCES usuarios(id),
    fecha_realizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de Detalle de Checklist (Puntos de Control)
CREATE TABLE respuestas_puntos_control (
    id BIGSERIAL PRIMARY KEY,
    checklist_id BIGINT NOT NULL REFERENCES checklists_ejecucion(id) ON DELETE CASCADE,
    categoria VARCHAR(50), -- Mecánica, Eléctrica, etc.
    descripcion_punto VARCHAR(255) NOT NULL,
    es_critico BOOLEAN DEFAULT FALSE,
    estado_ok BOOLEAN DEFAULT TRUE,
    observaciones TEXT
);

-- ============================================
-- ÍNDICES PARA OPTIMIZACIÓN DE QUERIES
-- ============================================

CREATE INDEX idx_usuarios_area ON usuarios(area_id);
CREATE INDEX idx_estaciones_area ON estaciones_trabajo(area_id);
CREATE INDEX idx_incidentes_estacion ON incidentes(estacion_id);
CREATE INDEX idx_incidentes_estado ON incidentes(estado);
CREATE INDEX idx_incidentes_tecnico ON incidentes(tecnico_asignado_id);
CREATE INDEX idx_incidentes_reportador ON incidentes(reportado_por_id);
CREATE INDEX idx_checklists_estacion ON checklists_ejecucion(estacion_id);
CREATE INDEX idx_checklists_operador ON checklists_ejecucion(operador_id);
CREATE INDEX idx_respuestas_checklist ON respuestas_puntos_control(checklist_id);
```

---

## 📌 Casos de Uso

### 1️⃣ Operador Reporta Incidente
```
1. Operador (USUARIO) reporta problema en EST-001 (ESTACION)
2. Se crea INCIDENTE con estado = ABIERTO
3. Sistema asigna TECNICO
4. Estado cambia a ASIGNADO
```

### 2️⃣ Técnico Resuelve Incidente
```
1. Técnico accede al INCIDENTE asignado
2. Actualiza estado a RESUELTO
3. Documenta SOLUCION_TECNICA
4. Registra FECHA_CIERRE
5. Supervisor aprueba y cambia a CERRADO
```

### 3️⃣ Ejecución de Checklist
```
1. Operador inicia CHECKLIST_EJECUCION para EST-001
2. Responde cada PUNTO_CONTROL (Ej: ¿Nivel de aceite OK?)
3. Sistema registra RESPUESTAS_PUNTOS_CONTROL
4. Si hay puntos críticos en rojo, genera alerta
```

---

## 📈 Escalabilidad Futura

**Tablas que podrían agregarse:**
- RESOLUCIONES (Tabla separada con detalles de solución)
- METRICAS (Análisis de tiempo de resolución, tasas de cierre)
- AUDITORIAS (Log de cambios en incidentes)
- TIPOS_INCIDENTE (Categorización: Mecánico, Eléctrico, etc.)

---

## 👤 Autor
**Brian Silenski** - Diseño de Base de Datos  
**Fecha:** 2026-05-05  
**Equipo:** No Country - S04-26-Equipo-21

---

*Última actualización: 2026-05-05*
