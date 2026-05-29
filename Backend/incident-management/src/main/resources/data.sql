-- =====================================================
-- AREAS
-- =====================================================

INSERT INTO areas (id, nombre, created_at, updated_at)
VALUES
(1, 'Operaciones Críticas', NOW(), NOW()),
(2, 'Mantenimiento', NOW(), NOW()),
(3, 'Calidad', NOW(), NOW());

-- =====================================================
-- ESPECIALIDADES
-- =====================================================

INSERT INTO especialidades (id, nombre, created_at, updated_at)
VALUES
(1, 'Electricidad Industrial', NOW(), NOW()),
(2, 'Mecánica', NOW(), NOW()),
(3, 'Automatización', NOW(), NOW());

-- =====================================================
-- ESTACIONES DE TRABAJO
-- =====================================================

INSERT INTO estaciones_trabajo (id, nombre, codigo, area_id, created_at, updated_at)
VALUES
(1, 'Servidor Principal', 'SERV-001', 1, NOW(), NOW()),
(2, 'Firewall Central', 'FW-001', 1, NOW(), NOW()),
(3, 'Torno CNC', 'CNC-001', 2, NOW(), NOW());

-- =====================================================
-- USUARIOS
-- =====================================================

INSERT INTO usuarios
(id, nombre, username, numero_reloj, password, rol, conectado, disponible, area_id, created_at, updated_at)
VALUES
(1, 'Operador Demo', 'operador.demo', 'OP100', 'password', 'OPERADOR', true, true, 1, NOW(), NOW()),
(2, 'Técnico Demo', 'tecnico.demo', 'TC200', 'password', 'TECNICO', true, true, 1, NOW(), NOW()),
(3, 'Supervisor Demo', 'supervisor.demo', 'SV300', 'password', 'SUPERVISOR', true, true, 1, NOW(), NOW()),
(4, 'Gerente Demo', 'gerente.demo', 'GR400', 'password', 'GERENTE', true, true, 1, NOW(), NOW());

-- =====================================================
-- USUARIO ESPECIALIDADES (MANY TO MANY)
-- =====================================================

INSERT INTO usuario_especialidades (usuario_id, especialidad_id)
VALUES
(2, 1),
(2, 3);

-- =====================================================
-- INCIDENTES
-- =====================================================

INSERT INTO incidentes
(
 id,
 titulo,
 descripcion,
 prioridad,
 tipo_falla,
 estado_operativo,
 estado_validacion,
 area_id,
 estacion_id,
 operador_id,
 tecnico_id,
 fecha_asignacion,
 fecha_inicio_trabajo,
 fecha_resolucion,
 fecha_cierre,
 created_at,
 updated_at
)
VALUES
(
 1,
 'Servidor fuera de servicio',
 'El servidor principal no responde a solicitudes',
 'CRITICA',
 'ELECTRICA',
 'ABIERTO',
 'PENDIENTE',
 1,
 1,
 1,
 2,
 NOW(),
 NULL,
 NULL,
 NULL,
 NOW(),
 NOW()
),
(
 2,
 'Falla en firewall',
 'Pérdida de conectividad externa',
 'ALTA',
 'SEGURIDAD',
 'EN_PROCESO',
 'PENDIENTE',
 1,
 2,
 1,
 2,
 NOW(),
 NOW() - INTERVAL '10 minutes',
 NULL,
 NULL,
 NOW(),
 NOW()
);

-- =====================================================
-- RESOLUCIONES
-- =====================================================

INSERT INTO resoluciones
(id, incidente_id, tecnico_id, descripcion_solucion, created_at)
VALUES
(
 1,
 2,
 2,
 'Reinicio de reglas de firewall y limpieza de cache',
 NOW()
);

-- =====================================================
-- INCIDENT HISTORY (opcional demo timeline)
-- =====================================================

INSERT INTO incident_history
(id, incidente_id, usuario_id, descripcion, timestamp, usuario_rol)
VALUES
(1, 1, 1, 'Incidente creado', NOW(), 'OPERADOR'),
(2, 1, 2, 'Técnico asignado', NOW(), 'TECNICO'),
(3, 2, 2, 'Trabajo iniciado', NOW(), 'TECNICO');