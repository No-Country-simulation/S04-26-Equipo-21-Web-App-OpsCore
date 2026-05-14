import { useForm, Controller } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import { AppButton } from "@/components/atoms";
import { AppLabel } from "@/components/atoms";
import { AppSelect } from "@/components/atoms";
import { AppTextarea } from "@/components/atoms";
import { AppDivider } from "@/components/atoms";
import { SeveritySelector } from "@/components/molecules/incidents/SeveritySelector";
import { SafetyChecklist } from "@/components/molecules/incidents/SafetyCheckList";
import { EvidenceUploader } from "@/components/molecules/incidents/EvidenceUploader";
import { incidentReportSchema } from "../schemas";
import type { IncidentReportFormProps } from "../types";

export type IncidentReportData = yup.InferType<typeof incidentReportSchema>;

export function IncidentReportForm({
  machines,
  areas,
  incidentTypes,
  onSubmit,
  isLoading,
}: IncidentReportFormProps) {
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<IncidentReportData>({
    resolver: yupResolver(incidentReportSchema),
    defaultValues: {
      machine: "",
      area: "",
      incidentType: "",
      severity: undefined,
      safetyChecklist: {},
      description: "",
    },
    mode: "onChange",
  });

  return (
    <div className="flex flex-col gap-6">
      <div className="flex flex-col gap-4">
        <div className="flex flex-col gap-1.5">
          <AppLabel>Máquina</AppLabel>
          <Controller
            control={control}
            name="machine"
            render={({ field }) => (
              <AppSelect
                options={machines}
                placeholder="Selecciona una máquina"
                value={field.value}
                onValueChange={field.onChange}
                disabled={isLoading}
              />
            )}
          />
          {errors.machine && (
            <span className="text-xs text-red-500">
              {errors.machine.message}
            </span>
          )}
        </div>

        <div className="flex flex-col gap-1.5">
          <AppLabel>Área</AppLabel>
          <Controller
            control={control}
            name="area"
            render={({ field }) => (
              <AppSelect
                options={areas}
                placeholder="Selecciona un área"
                value={field.value}
                onValueChange={field.onChange}
                disabled={isLoading}
              />
            )}
          />
          {errors.area && (
            <span className="text-xs text-red-500">{errors.area.message}</span>
          )}
        </div>

        <div className="flex flex-col gap-1.5">
          <AppLabel>Tipo de incidente</AppLabel>
          <Controller
            control={control}
            name="incidentType"
            render={({ field }) => (
              <AppSelect
                options={incidentTypes}
                placeholder="Selecciona el tipo"
                value={field.value}
                onValueChange={field.onChange}
                disabled={isLoading}
              />
            )}
          />
          {errors.incidentType && (
            <span className="text-xs text-red-500">
              {errors.incidentType.message}
            </span>
          )}
        </div>

        <Controller
          control={control}
          name="severity"
          render={({ field }) => (
            <SeveritySelector
              value={field.value ?? ""}
              onChange={field.onChange}
              errorMessage={errors.severity?.message}
              disabled={isLoading}
            />
          )}
        />
      </div>

      <AppDivider />

      <Controller
        control={control}
        name="safetyChecklist"
        render={({ field }) => (
          <SafetyChecklist
            checked={field.value as Record<string, boolean>}
            onChange={(id, value) =>
              field.onChange({ ...field.value, [id]: value })
            }
            errorMessage={errors.safetyChecklist?.message}
            disabled={isLoading}
          />
        )}
      />

      <AppDivider />

      <div className="flex flex-col gap-4">
        <div className="flex flex-col gap-1.5">
          <AppLabel>Descripción</AppLabel>
          <Controller
            control={control}
            name="description"
            render={({ field }) => (
              <AppTextarea
                placeholder="Describe el incidente con el mayor detalle posible..."
                value={field.value}
                onChange={field.onChange}
                errorMessage={errors.description?.message}
                disabled={isLoading}
                rows={4}
              />
            )}
          />
        </div>

        <EvidenceUploader />
      </div>

      <AppButton
        className="w-full"
        onClick={handleSubmit(onSubmit)}
        disabled={isLoading}
        label={isLoading ? "Enviando..." : "Enviar Incidente"}
      />
    </div>
  );
}
