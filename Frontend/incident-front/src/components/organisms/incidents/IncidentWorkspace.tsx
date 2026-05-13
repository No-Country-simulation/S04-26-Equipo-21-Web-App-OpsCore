import { useState } from "react";
import { AppButton } from "@/components/atoms/AppButton/AppButton";
import { AppLabel } from "@/components/atoms";
import { AppSelect } from "@/components/atoms";
import { AppTextarea } from "@/components/atoms";
import { AppBadge } from "@/components/atoms";
import { AppText } from "@/components/atoms";
import { AppDivider } from "@/components/atoms";
import { TechnicianChecklist } from "@/components/molecules/incidents/Technicianchecklist";
import { RepairEvidenceUploader } from "@/components/molecules/incidents/RepairEvidenceUploader";
import type { Severity } from "@/components/molecules/incidents/SeveritySelector";

// ─── Types ───────────────────────────────────────────────────────────────────

export type IncidentDetail = {
  id: string;
  machine: string;
  area: string;
  severity: Severity;
  status: string;
};

export type WorkspacePayload = {
  checklist: Record<string, boolean>;
  diagnosticNotes: string;
  rootCause: string;
};

type IncidentWorkspaceProps = {
  incident: IncidentDetail;
  onSave: (payload: WorkspacePayload) => Promise<void> | void;
  onResolve: (payload: WorkspacePayload) => Promise<void> | void;
  isSaving?: boolean;
  isResolving?: boolean;
};

const ROOT_CAUSES = [
  { value: "electrical", label: "Falla eléctrica" },
  { value: "mechanical", label: "Falla mecánica" },
  { value: "software", label: "Falla de software" },
  { value: "operator_error", label: "Error de operador" },
  { value: "wear", label: "Desgaste natural" },
  { value: "external", label: "Factor externo" },
];

const SEVERITY_LABEL: Record<Severity, string> = {
  critical: "Crítica",
  medium: "Media",
  low: "Baja",
};

// ─── Organism ────────────────────────────────────────────────────────────────

export function IncidentWorkspace({
  incident,
  onSave,
  onResolve,
  isSaving,
  isResolving,
}: IncidentWorkspaceProps) {
  const [checklist, setChecklist] = useState<Record<string, boolean>>({});
  const [diagnosticNotes, setDiagnosticNotes] = useState("");
  const [rootCause, setRootCause] = useState("");
  const [errors, setErrors] = useState<
    Partial<Record<"checklist" | "diagnosticNotes" | "rootCause", string>>
  >({});

  const handleChecklistChange = (id: string, value: boolean) => {
    setChecklist((prev) => ({ ...prev, [id]: value }));
  };

  const validate = () => {
    const e: typeof errors = {};
    const allChecked = [
      "emergency_stop",
      "machine_disconnected",
      "area_secured",
    ].every((id) => checklist[id]);
    if (!allChecked) e.checklist = "Completa todos los puntos del checklist";
    if (!diagnosticNotes.trim())
      e.diagnosticNotes = "Las notas de diagnóstico son requeridas";
    if (!rootCause) e.rootCause = "Selecciona la causa raíz";
    return e;
  };

  const buildPayload = (): WorkspacePayload => ({
    checklist,
    diagnosticNotes,
    rootCause,
  });

  const handleSave = async () => {
    setErrors({});
    await onSave(buildPayload());
  };

  const handleResolve = async () => {
    const validation = validate();
    if (Object.keys(validation).length > 0) {
      setErrors(validation);
      return;
    }
    setErrors({});
    await onResolve(buildPayload());
  };

  return (
    <div className="flex flex-col gap-6 px-4 py-4 overflow-y-auto">
      {/* Info del incidente */}
      <div className="grid grid-cols-2 gap-3">
        <div>
          <AppText className="text-xs text-muted-foreground">Máquina</AppText>
          <AppText className="text-sm font-medium">{incident.machine}</AppText>
        </div>
        <div>
          <AppText className="text-xs text-muted-foreground">Área</AppText>
          <AppText className="text-sm font-medium">{incident.area}</AppText>
        </div>
        <div>
          <AppText className="text-xs text-muted-foreground">Severidad</AppText>
          <AppBadge
            label={SEVERITY_LABEL[incident.severity]}
            variant="destructive"
          />
        </div>
        <div>
          <AppText className="text-xs text-muted-foreground">Estado</AppText>
          <AppBadge label={incident.status} variant="secondary" />
        </div>
      </div>

      <AppDivider />

      {/* Checklist del técnico */}
      <TechnicianChecklist
        checked={checklist}
        onChange={handleChecklistChange}
        errorMessage={errors.checklist}
      />

      <AppDivider />

      {/* Notas de diagnóstico */}
      <div className="flex flex-col gap-1.5">
        <AppLabel>Notas de Diagnóstico</AppLabel>
        <AppTextarea
          placeholder="Describe el diagnóstico del problema..."
          value={diagnosticNotes}
          onChange={(e) => setDiagnosticNotes(e.target.value)}
          errorMessage={errors.diagnosticNotes}
          rows={3}
        />
      </div>

      <AppDivider />

      {/* Evidencia */}
      <RepairEvidenceUploader />

      <AppDivider />

      {/* Causa raíz */}
      <div className="flex flex-col gap-1.5">
        <AppLabel>Causa Raíz</AppLabel>
        <AppSelect
          options={ROOT_CAUSES}
          placeholder="Selecciona la causa raíz"
          value={rootCause}
          onValueChange={setRootCause}
        />
        {errors.rootCause && (
          <span className="text-xs text-red-500">{errors.rootCause}</span>
        )}
      </div>

      <AppDivider />

      {/* Acciones */}
      <div className="flex gap-3">
        <AppButton
          variant="outline"
          className="flex-1"
          onClick={handleSave}
          disabled={isSaving || isResolving}
          label={isSaving ? "Guardando..." : "Guardar Progreso"}
        />
        <AppButton
          className="flex-1"
          onClick={handleResolve}
          disabled={isSaving || isResolving}
          label={isResolving ? "Resolviendo..." : "Resolver Incidente"}
        />
      </div>
    </div>
  );
}
