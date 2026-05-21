import { useForm, Controller } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import {
  MapPin,
  Cpu,
  AlertCircle,
  Gauge,
  FileText,
  Paperclip,
} from "lucide-react";
import {
  AppButton,
  AppLabel,
  AppSelect,
  AppTextarea,
  AppDivider,
  AppText,
} from "@/components/atoms";
import { SeveritySelector } from "@/components/molecules/incidents/SeveritySelector";
import { SafetyChecklist } from "@/components/molecules/incidents/SafetyCheckList";
import { EvidenceUploader } from "@/components/molecules/incidents/EvidenceUploader";
import { incidentReportSchema } from "../schemas";
import type { IncidentReportFormProps } from "../types";

export type IncidentReportData = yup.InferType<typeof incidentReportSchema>;

function SectionTitle({ children }: { children: string }) {
  return (
    <AppText className="text-xs font-semibold uppercase tracking-widest text-muted-foreground">
      {children}
    </AppText>
  );
}

export function IncidentReportForm({
  machines,
  areas,
  incidentTypes,
  onSubmit,
  onAreaChange,
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
      {/* Sección: Datos del incidente */}
      <div className="flex flex-col gap-4">
        <SectionTitle>Datos del incidente</SectionTitle>

        <div className="flex flex-col gap-1.5">
          <AppLabel>
            <MapPin size={14} className="text-muted-foreground" />
            Área
          </AppLabel>
          <Controller
            control={control}
            name="area"
            render={({ field }) => (
              <AppSelect
                options={areas}
                placeholder="Selecciona un área"
                value={field.value}
                onValueChange={(v) => {
                  field.onChange(v);
                  onAreaChange?.(v);
                }}
                disabled={isLoading}
              />
            )}
          />
          {errors.area && (
            <span className="text-xs text-red-500">{errors.area.message}</span>
          )}
        </div>

        <div className="flex flex-col gap-1.5">
          <AppLabel>
            <Cpu size={14} className="text-muted-foreground" />
            Estación de trabajo
          </AppLabel>
          <Controller
            control={control}
            name="machine"
            render={({ field }) => (
              <AppSelect
                options={machines}
                placeholder="Selecciona una estación"
                value={field.value}
                onValueChange={field.onChange}
                disabled={isLoading || machines.length === 0}
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
          <AppLabel>
            <AlertCircle size={14} className="text-muted-foreground" />
            Tipo de incidente
          </AppLabel>
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
              icon={<Gauge size={14} className="text-muted-foreground" />}
            />
          )}
        />
      </div>

      <AppDivider />

      {/* Sección: Seguridad */}
      <div className="flex flex-col gap-4">
        <SectionTitle>Verificación de seguridad</SectionTitle>
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
      </div>

      <AppDivider />

      {/* Sección: Descripción y evidencia */}
      <div className="flex flex-col gap-4">
        <SectionTitle>Detalle del incidente</SectionTitle>

        <div className="flex flex-col gap-1.5">
          <AppLabel>
            <FileText size={14} className="text-muted-foreground" />
            Descripción
          </AppLabel>
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

        <EvidenceUploader
          icon={<Paperclip size={14} className="text-muted-foreground" />}
        />
      </div>

      <AppButton
        className="w-full mb-6"
        onClick={handleSubmit(onSubmit)}
        disabled={isLoading}
        label={isLoading ? "Enviando..." : "Enviar Incidente"}
      />
    </div>
  );
}
