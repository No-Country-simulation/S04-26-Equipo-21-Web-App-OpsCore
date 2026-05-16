-- ============================================================
-- V2__triggers_audit.sql
-- ============================================================

-- ============================================================
-- 1. FUNCIÓN GENÉRICA: updated_at automático
-- ============================================================
CREATE OR REPLACE FUNCTION set_updated_at()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- ============================================================
-- 2. APLICAR updated_at A TODAS LAS TABLAS IMPORTANTES
-- ============================================================

CREATE TRIGGER trg_areas_updated_at
    BEFORE UPDATE ON areas
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_especialidades_updated_at
    BEFORE UPDATE ON especialidades
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_usuarios_updated_at
    BEFORE UPDATE ON usuarios
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_estaciones_updated_at
    BEFORE UPDATE ON estaciones_trabajo
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_incidentes_updated_at
    BEFORE UPDATE ON incidentes
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_checklists_updated_at
    BEFORE UPDATE ON checklists
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_checklist_items_updated_at
    BEFORE UPDATE ON checklist_items
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_checklists_ejecucion_updated_at
    BEFORE UPDATE ON checklists_ejecucion
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_respuestas_updated_at
    BEFORE UPDATE ON respuestas_puntos_control
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_resoluciones_updated_at
    BEFORE UPDATE ON resoluciones
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- ============================================================
-- 3. SEGURIDAD: evitar updates inválidos en incidentes
-- ============================================================

CREATE OR REPLACE FUNCTION validate_incidente_fechas()
    RETURNS TRIGGER AS $$
BEGIN
    -- fecha_cierre no puede ser menor a created_at
    IF NEW.fecha_cierre IS NOT NULL
        AND NEW.fecha_cierre < NEW.created_at THEN
        RAISE EXCEPTION 'fecha_cierre no puede ser menor que created_at';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_incidentes_validate_fechas
    BEFORE INSERT OR UPDATE ON incidentes
    FOR EACH ROW
EXECUTE FUNCTION validate_incidente_fechas();

-- ============================================================
-- 4. SEGURIDAD: resoluciones coherentes
-- ============================================================

CREATE OR REPLACE FUNCTION validate_resoluciones()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.fecha_cierre IS NOT NULL
        AND NEW.fecha_cierre < NEW.fecha_asignacion THEN
        RAISE EXCEPTION 'fecha_cierre no puede ser menor que fecha_asignacion';
    END IF;

    IF NEW.tiempo_resolucion IS NOT NULL
        AND NEW.tiempo_resolucion < 0 THEN
        RAISE EXCEPTION 'tiempo_resolucion no puede ser negativo';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_resoluciones_validate
    BEFORE INSERT OR UPDATE ON resoluciones
    FOR EACH ROW
EXECUTE FUNCTION validate_resoluciones();

-- ============================================================
-- 5. (OPCIONAL PRO) AUDITORÍA BÁSICA
-- ============================================================
-- Si quieres tracking de cambios futuros

CREATE TABLE IF NOT EXISTS audit_log (
                                         id BIGSERIAL PRIMARY KEY,
                                         table_name TEXT NOT NULL,
                                         operation TEXT NOT NULL,
                                         old_data JSONB,
                                         new_data JSONB,
                                         changed_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE FUNCTION audit_generic()
    RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO audit_log(table_name, operation, old_data, new_data)
    VALUES (
               TG_TABLE_NAME,
               TG_OP,
               to_jsonb(OLD),
               to_jsonb(NEW)
           );

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- ejemplo aplicado SOLO a incidentes (puedes expandirlo)
CREATE TRIGGER trg_incidentes_audit
    AFTER INSERT OR UPDATE OR DELETE ON incidentes
    FOR EACH ROW
EXECUTE FUNCTION audit_generic();