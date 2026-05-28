-- ============================================================
-- V3__seed_data.sql
-- ============================================================

-- ============================================================
-- AREAS
-- ============================================================
INSERT INTO areas (nombre)
VALUES ('Producción A'),
       ('Mantenimiento'),
       ('Calidad'),
       ('Logística');

-- ============================================================
-- ESPECIALIDADES
-- ============================================================
INSERT INTO especialidades (nombre)
VALUES ('Electricidad Industrial'),
       ('Mecánica'),
       ('Sistemas CNC'),
       ('Automatización'),
       ('Neumática');

-- ============================================================
-- ESTACIONES DE TRABAJO
-- ============================================================
INSERT INTO estaciones_trabajo (nombre, codigo, area_id)
VALUES ('Prensa Hidráulica PH-01', 'PH01', 1),
       ('Torno CNC-02', 'CNC02', 1),
       ('Estación de Limpieza Química', 'ELQ01', 1),
       ('Brazo Robótico Ensamblador', 'BRE01', 2),
       ('Compresor Industrial de Gas', 'CIG01', 2),
       ('Banda Transportadora B-01', 'BT01', 4);

-- ============================================================
-- USUARIOS (1 por rol + coherentes con area)
-- ============================================================
INSERT INTO usuarios
(nombre, username, numero_reloj, rol, password, area_id, conectado, disponible)
VALUES
-- OPERADOR
('Operador Principal', 'operador.main', 'OP100', 'OPERADOR',
 '{bcrypt}$2a$12$0l.w9.kG6.hLRMqgTTYv9Ol9iN1kZXpGscvpnmjjFH/hIvyVrdCiy',
 1, false, true),

-- SUPERVISOR
('Supervisor Planta', 'supervisor.main', 'SV200', 'SUPERVISOR',
 '{bcrypt}$2a$12$0l.w9.kG6.hLRMqgTTYv9Ol9iN1kZXpGscvpnmjjFH/hIvyVrdCiy',
 1, false, true),

-- TECNICO
('Técnico Especialista', 'tecnico.main', 'TC300', 'TECNICO',
 '{bcrypt}$2a$12$0l.w9.kG6.hLRMqgTTYv9Ol9iN1kZXpGscvpnmjjFH/hIvyVrdCiy',
 2, false, true),

-- GERENTE
('Gerente General', 'gerente.main', 'GR400', 'GERENTE',
 '{bcrypt}$2a$12$0l.w9.kG6.hLRMqgTTYv9Ol9iN1kZXpGscvpnmjjFH/hIvyVrdCiy',
 3, false, true);

-- ============================================================
-- USUARIO ESPECIALIDADES
-- ============================================================
INSERT INTO usuario_especialidades (usuario_id, especialidad_id)
VALUES (3, 1),
       (3, 2),
       (2, 4);

-- ============================================================
-- CHECKLISTS
-- ============================================================
INSERT INTO checklists (titulo)
VALUES ('Inspección de Seguridad Diaria'),
       ('Mantenimiento Preventivo Básico');

-- checklist items
INSERT INTO checklist_items (descripcion, checklist_id)
VALUES ('Verificar botones de emergencia', 1),
       ('Revisar señalización de seguridad', 1),
       ('Inspección de lubricación', 2),
       ('Verificar presión neumática', 2);

-- ============================================================
-- CHECKLIST EJECUCIÓN
-- ============================================================
INSERT INTO checklists_ejecucion
    (checklist_plantilla_id, estacion_id, operador_id)
VALUES (1, 1, 1),
       (2, 2, 1);

-- ============================================================
-- RESPUESTAS CHECKLIST
-- ============================================================
INSERT INTO respuestas_puntos_control
    (ejecucion_id, item_id, completado, observaciones)
VALUES (1, 1, true, 'OK'),
       (1, 2, true, 'Todo en orden'),
       (2, 3, false, 'Falta lubricación'),
       (2, 4, true, 'Presión estable');

-- ============================================================
-- INCIDENTES (datos realistas para pruebas)
-- ============================================================
INSERT INTO incidentes
(titulo, descripcion, estado, prioridad,tipo,
 area_id, estacion_id, reportado_por_id, tecnico_asignado_id)
VALUES ('Falla en prensa hidráulica',
        'La máquina no responde al pedal principal',
        'ABIERTO',
        'CRITICA',
        'FALLA_OPERATIVA',
        1, 1, 1, 3),

       ('Ruido anormal en torno CNC',
        'Vibración excesiva durante operación',
        'ASIGNADO',
        'NORMAL',
        'MANTENIMIENTO_PREVENTIVO',
        1, 2, 1, 3),

       ('Fuga de aire en compresor',
        'Pérdida de presión constante',
        'EN_PROCESO',
        'CRITICA',
        'MANTENIMIENTO_CORRECTIVO',
        2, 5, 2, 3),

       ('Sensor robótico descalibrado',
        'Error en alineación de brazo',
        'RESUELTO',
        'NORMAL',
        'MANTENIMIENTO_CORRECTIVO',
        2, 4, 2, 3);

-- ============================================================
-- RESOLUCIONES
-- ============================================================
INSERT INTO resoluciones
(incidente_id, responsable_id, descripcion_solucion,
 fecha_asignacion, fecha_cierre, tiempo_resolucion)
VALUES (4, 3, 'Recalibración del sistema óptico del brazo robótico',
        NOW() - INTERVAL '2 days',
        NOW() - INTERVAL '1 day',
        1440),

       (3, 3, 'Cambio de válvula de presión y sellos',
        NOW() - INTERVAL '5 hours',
        NULL,
        NULL);
