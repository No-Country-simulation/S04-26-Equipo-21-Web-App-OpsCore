import { useForm, Controller } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";

import {
  AppLabel,
  AppButton,
  AppSelect,
  AppTextarea,
  AppBadge,
  AppText,
  AppDivider,
} from "@/components/atoms";
import { TechnicianChecklist } from "@/components/molecules/incidents/Technicianchecklist";
import { RepairEvidenceUploader } from "@/components/molecules/incidents/RepairEvidenceUploader";
import type { IncidentWorkspaceProps, WorkspacePayload } from "../types";
import { ROOT_CAUSES, SEVERITY_LABEL } from "@/constants";
import { incidentWorkspaceSchema } from "../schemas";

type FormValues = yup.InferType<typeof incidentWorkspaceSchema>;

export function IncidentWorkspace({
  incident,
  onSave,
  onResolve,
  isSaving,
  isResolving,
}: IncidentWorkspaceProps) {
  const {
    control,
    handleSubmit,
    getValues,
    formState: { errors },
  } = useForm<FormValues>({
    resolver: yupResolver(incidentWorkspaceSchema),
    defaultValues: {
      checklist: {},
      diagnosticNotes: "",
      rootCause: "",
    },
    mode: "onChange",
  });

  const buildPayload = (values: FormValues): WorkspacePayload => ({
    checklist: values.checklist as Record<string, boolean>,
    diagnosticNotes: values.diagnosticNotes,
    rootCause: values.rootCause,
  });

  const handleSave = () => onSave(buildPayload(getValues()));

  const handleResolve = handleSubmit((values) =>
    onResolve(buildPayload(values)),
  );

  return (
    <div className="flex flex-col gap-6 px-4 py-4 overflow-y-auto">
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

      <Controller
        control={control}
        name="checklist"
        render={({ field }) => (
          <TechnicianChecklist
            checked={field.value as Record<string, boolean>}
            onChange={(id, value) =>
              field.onChange({ ...field.value, [id]: value })
            }
            errorMessage={errors.checklist?.message}
          />
        )}
      />

      <AppDivider />

      <div className="flex flex-col gap-1.5">
        <AppLabel>Notas de Diagnóstico</AppLabel>
        <Controller
          control={control}
          name="diagnosticNotes"
          render={({ field }) => (
            <AppTextarea
              placeholder="Describe el diagnóstico del problema..."
              value={field.value}
              onChange={field.onChange}
              errorMessage={errors.diagnosticNotes?.message}
              rows={3}
            />
          )}
        />
      </div>

      <AppDivider />

      <RepairEvidenceUploader />

      <AppDivider />

      <div className="flex flex-col gap-1.5">
        <AppLabel>Causa Raíz</AppLabel>
        <Controller
          control={control}
          name="rootCause"
          render={({ field }) => (
            <AppSelect
              options={ROOT_CAUSES}
              placeholder="Selecciona la causa raíz"
              value={field.value}
              onValueChange={field.onChange}
            />
          )}
        />
        {errors.rootCause && (
          <span className="text-xs text-red-500">
            {errors.rootCause.message}
          </span>
        )}
      </div>

      <AppDivider />

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
