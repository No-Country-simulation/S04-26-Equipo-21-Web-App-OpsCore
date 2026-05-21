-- ============================================================
-- ENUMS
-- ============================================================

CREATE TYPE usuario_rol AS ENUM (
    'OPERADOR',
    'SUPERVISOR',
    'TECNICO',
    'GERENTE'
    );

CREATE TYPE incidente_estado AS ENUM (
    'ABIERTO',
    'ASIGNADO',
    'EN_PROCESO',
    'RESUELTO',
    'CERRADO'
    );

CREATE TYPE incidente_prioridad AS ENUM (
    'BAJA',
    'NORMAL',
    'ALTA',
    'CRITICA'
    );

CREATE TYPE incidente_tipo AS ENUM (
    'FALLA_OPERATIVA',
    'ACCIDENTE',
    'CASI_ACCIDENTE',
    'CALIDAD',
    'MANTENIMIENTO_PREVENTIVO',
    'MANTENIMIENTO_CORRECTIVO',
    'SEGURIDAD',
    'AMBIENTAL',
    'OTRO'
    );

-- ============================================================
-- TABLAS BASE
-- ============================================================

CREATE TABLE areas
(
    id         BIGSERIAL PRIMARY KEY,
    nombre     VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_areas_nombre_not_blank
        CHECK (trim(nombre) <> '')
);

CREATE TABLE especialidades
(
    id         BIGSERIAL PRIMARY KEY,
    nombre     VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_especialidades_nombre_not_blank
        CHECK (trim(nombre) <> '')
);

-- ============================================================
-- USUARIOS
-- ============================================================

CREATE TABLE usuarios
(
    id           BIGSERIAL PRIMARY KEY,
    nombre       VARCHAR(100) NOT NULL,
    username     VARCHAR(255) NOT NULL UNIQUE,
    numero_reloj VARCHAR(50)  NOT NULL UNIQUE,
    rol          usuario_rol  NOT NULL,
    password     VARCHAR(255) NOT NULL,

    area_id      BIGINT       NOT NULL
        REFERENCES areas (id)
            ON DELETE RESTRICT,

    conectado    BOOLEAN      NOT NULL DEFAULT FALSE,
    disponible   BOOLEAN      NOT NULL DEFAULT TRUE,

    created_at   TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_usuarios_nombre_not_blank
        CHECK (trim(nombre) <> ''),

    CONSTRAINT chk_usuarios_numero_reloj_not_blank
        CHECK (trim(numero_reloj) <> '')
);

-- ============================================================
-- USUARIO - ESPECIALIDADES (N:M)
-- ============================================================

CREATE TABLE usuario_especialidades
(
    usuario_id      BIGINT NOT NULL
        REFERENCES usuarios (id)
            ON DELETE CASCADE,

    especialidad_id BIGINT NOT NULL
        REFERENCES especialidades (id)
            ON DELETE CASCADE,

    PRIMARY KEY (usuario_id, especialidad_id)
);

-- ============================================================
-- ESTACIONES DE TRABAJO
-- ============================================================

CREATE TABLE estaciones_trabajo
(
    id         BIGSERIAL PRIMARY KEY,
    nombre     VARCHAR(100) NOT NULL,
    codigo     VARCHAR(50)  NOT NULL UNIQUE,

    area_id    BIGINT
        REFERENCES areas (id)
            ON DELETE RESTRICT,

    created_at TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_estaciones_nombre_not_blank
        CHECK (trim(nombre) <> ''),

    CONSTRAINT chk_estaciones_codigo_not_blank
        CHECK (trim(codigo) <> '')
);

-- ============================================================
-- INCIDENTES
-- ============================================================

CREATE TABLE incidentes
(
    id                  BIGSERIAL PRIMARY KEY,

    titulo              VARCHAR(255)        NOT NULL,
    descripcion         TEXT,

    estado              incidente_estado    NOT NULL,
    prioridad           incidente_prioridad NOT NULL,
    tipo                incidente_tipo      NOT NULL,

    solucion_tecnica    TEXT,
    fecha_cierre        TIMESTAMPTZ,

    area_id             BIGINT              NOT NULL
        REFERENCES areas (id)
            ON DELETE RESTRICT,

    estacion_id         BIGINT              NOT NULL
        REFERENCES estaciones_trabajo (id)
            ON DELETE RESTRICT,

    reportado_por_id    BIGINT              NOT NULL
        REFERENCES usuarios (id)
            ON DELETE RESTRICT,

    tecnico_asignado_id BIGINT              NOT NULL
        REFERENCES usuarios (id)
            ON DELETE RESTRICT,

    created_at          TIMESTAMPTZ         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMPTZ         NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_incidentes_titulo_not_blank
        CHECK (trim(titulo) <> ''),

    CONSTRAINT chk_incidentes_fecha_cierre
        CHECK (
            fecha_cierre IS NULL
                OR fecha_cierre >= created_at
            )
);

-- ============================================================
-- CHECKLISTS
-- ============================================================

CREATE TABLE checklists
(
    id         BIGSERIAL PRIMARY KEY,

    titulo     VARCHAR(255) NOT NULL,

    created_at TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_checklists_titulo_not_blank
        CHECK (trim(titulo) <> '')
);

CREATE TABLE checklist_items
(
    id           BIGSERIAL PRIMARY KEY,

    descripcion  VARCHAR(255) NOT NULL,

    checklist_id BIGINT       NOT NULL
        REFERENCES checklists (id)
            ON DELETE CASCADE,

    created_at   TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_checklist_items_descripcion_not_blank
        CHECK (trim(descripcion) <> '')
);

-- ============================================================
-- EJECUCIÓN DE CHECKLISTS
-- ============================================================

CREATE TABLE checklists_ejecucion
(
    id                     BIGSERIAL PRIMARY KEY,

    checklist_plantilla_id BIGINT      NOT NULL
        REFERENCES checklists (id)
            ON DELETE RESTRICT,

    estacion_id            BIGINT      NOT NULL
        REFERENCES estaciones_trabajo (id)
            ON DELETE RESTRICT,

    operador_id            BIGINT      NOT NULL
        REFERENCES usuarios (id)
            ON DELETE RESTRICT,

    created_at             TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at             TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE respuestas_puntos_control
(
    id            BIGSERIAL PRIMARY KEY,

    ejecucion_id  BIGINT      NOT NULL
        REFERENCES checklists_ejecucion (id)
            ON DELETE CASCADE,

    item_id       BIGINT      NOT NULL
        REFERENCES checklist_items (id)
            ON DELETE RESTRICT,

    completado    BOOLEAN     NOT NULL DEFAULT FALSE,
    observaciones TEXT,

    created_at    TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_respuesta_ejecucion_item
        UNIQUE (ejecucion_id, item_id)
);

-- ============================================================
-- RESOLUCIONES
-- ============================================================

CREATE TABLE resoluciones
(
    id                   BIGSERIAL PRIMARY KEY,

    incidente_id         BIGINT      NOT NULL UNIQUE
        REFERENCES incidentes (id)
            ON DELETE CASCADE,

    responsable_id       BIGINT
                                     REFERENCES usuarios (id)
                                         ON DELETE SET NULL,

    descripcion_solucion TEXT        NOT NULL,

    fecha_asignacion     TIMESTAMPTZ NOT NULL,
    fecha_cierre         TIMESTAMPTZ,

    tiempo_resolucion    BIGINT,

    CONSTRAINT chk_resoluciones_tiempo_positivo
        CHECK (
            tiempo_resolucion IS NULL
                OR tiempo_resolucion >= 0
            ),

    CONSTRAINT chk_resoluciones_fecha
        CHECK (
            fecha_cierre IS NULL
                OR fecha_cierre >= fecha_asignacion
            )
);

-- ============================================================
-- TABLA: metricas
-- ============================================================

CREATE TABLE metricas
(
    id                         BIGSERIAL PRIMARY KEY,

    periodo                    VARCHAR(50) NOT NULL,

    tiempo_promedio_resolucion DOUBLE PRECISION,
    tasa_cierre                DOUBLE PRECISION,
    incidentes_criticos        INTEGER,

    patrones_recurrentes       TEXT
);

-- ============================================================
-- INDICES
-- ============================================================

-- usuarios
CREATE INDEX idx_usuarios_area_id
    ON usuarios (area_id);

CREATE INDEX idx_usuarios_rol
    ON usuarios (rol);

-- estaciones
CREATE INDEX idx_estaciones_trabajo_area_id
    ON estaciones_trabajo (area_id);

-- usuario_especialidades
CREATE INDEX idx_usuario_especialidades_especialidad_id
    ON usuario_especialidades (especialidad_id);

-- incidentes
CREATE INDEX idx_incidentes_area_id
    ON incidentes (area_id);

CREATE INDEX idx_incidentes_estacion_id
    ON incidentes (estacion_id);

CREATE INDEX idx_incidentes_reportado_por_id
    ON incidentes (reportado_por_id);

CREATE INDEX idx_incidentes_tecnico_asignado_id
    ON incidentes (tecnico_asignado_id);

CREATE INDEX idx_incidentes_estado
    ON incidentes (estado);

CREATE INDEX idx_incidentes_prioridad
    ON incidentes (prioridad);

CREATE INDEX idx_incidentes_created_at
    ON incidentes (created_at DESC);

-- índice compuesto muy útil
CREATE INDEX idx_incidentes_estado_tecnico_created_at
    ON incidentes (
                   estado,
                   tecnico_asignado_id,
                   created_at DESC
        );

-- checklist_items
CREATE INDEX idx_checklist_items_checklist_id
    ON checklist_items (checklist_id);

-- ejecucion
CREATE INDEX idx_checklists_ejecucion_plantilla_id
    ON checklists_ejecucion (checklist_plantilla_id);

CREATE INDEX idx_checklists_ejecucion_estacion_id
    ON checklists_ejecucion (estacion_id);

CREATE INDEX idx_checklists_ejecucion_operador_id
    ON checklists_ejecucion (operador_id);

-- respuestas
CREATE INDEX idx_respuestas_ejecucion_id
    ON respuestas_puntos_control (ejecucion_id);

CREATE INDEX idx_respuestas_item_id
    ON respuestas_puntos_control (item_id);

-- resoluciones
CREATE INDEX idx_resoluciones_responsable_id
    ON resoluciones (responsable_id);

-- metricas
CREATE INDEX idx_metricas_periodo
    ON metricas (periodo);