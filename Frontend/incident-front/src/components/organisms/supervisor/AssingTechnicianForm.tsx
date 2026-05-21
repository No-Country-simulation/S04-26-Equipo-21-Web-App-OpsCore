import { useForm, Controller } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import { User, Gauge, Clock } from "lucide-react";
import {
  AppButton,
  AppLabel,
  AppSelect,
  AppDivider,
  AppText,
} from "@/components/atoms";
import { AsignSchema } from "../schemas";
import type { AssignTechnicianFormProps } from "../types";

type FormValues = yup.InferType<typeof AsignSchema>;
export type AssignPayload = FormValues;

//TODO: Replace for API responses

const PRIORITIES = [
  { value: "BAJA", label: "Baja" },
  { value: "NORMAL", label: "Normal" },
  { value: "ALTA", label: "Alta" },
  { value: "CRITICA", label: "Crítica" },
];

const SLA_OPTIONS = [
  { value: "1h", label: "1 hora" },
  { value: "2h", label: "2 horas" },
  { value: "4h", label: "4 horas" },
  { value: "8h", label: "8 horas" },
  { value: "24h", label: "24 horas" },
];

function SectionTitle({ children }: { children: React.ReactNode }) {
  return (
    <AppText className="text-xs font-semibold uppercase tracking-widest text-muted-foreground">
      {children}
    </AppText>
  );
}

export function AssignTechnicianForm({
  incidentId,
  technicians,
  onSubmit,
  isLoading,
}: AssignTechnicianFormProps) {
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<FormValues>({
    resolver: yupResolver(AsignSchema),
    defaultValues: { technicianId: "", priority: "", estimatedSla: "" },
    mode: "onChange",
  });

  return (
    <div className="flex flex-col gap-6 px-4 py-4">
      <div className="flex flex-col gap-4">
        <SectionTitle>Asignación — Incidente #{incidentId}</SectionTitle>

        {/* Técnico */}
        <div className="flex flex-col gap-1.5">
          <AppLabel>
            <User size={14} className="text-muted-foreground" />
            Técnico
          </AppLabel>
          <Controller
            control={control}
            name="technicianId"
            render={({ field }) => (
              <AppSelect
                options={technicians}
                placeholder="Selecciona un técnico"
                value={field.value}
                onValueChange={field.onChange}
                disabled={isLoading}
              />
            )}
          />
          {errors.technicianId && (
            <span className="text-xs text-red-500">
              {errors.technicianId.message}
            </span>
          )}
        </div>

        {/* Prioridad */}
        <div className="flex flex-col gap-1.5">
          <AppLabel>
            <Gauge size={14} className="text-muted-foreground" />
            Prioridad
          </AppLabel>
          <Controller
            control={control}
            name="priority"
            render={({ field }) => (
              <AppSelect
                options={PRIORITIES}
                placeholder="Selecciona la prioridad"
                value={field.value}
                onValueChange={field.onChange}
                disabled={isLoading}
              />
            )}
          />
          {errors.priority && (
            <span className="text-xs text-red-500">
              {errors.priority.message}
            </span>
          )}
        </div>

        {/* SLA estimado */}
        <div className="flex flex-col gap-1.5">
          <AppLabel>
            <Clock size={14} className="text-muted-foreground" />
            SLA Estimado
          </AppLabel>
          <Controller
            control={control}
            name="estimatedSla"
            render={({ field }) => (
              <AppSelect
                options={SLA_OPTIONS}
                placeholder="Selecciona el tiempo estimado"
                value={field.value}
                onValueChange={field.onChange}
                disabled={isLoading}
              />
            )}
          />
          {errors.estimatedSla && (
            <span className="text-xs text-red-500">
              {errors.estimatedSla.message}
            </span>
          )}
        </div>
      </div>

      <AppDivider />

      <AppButton
        className="w-full mb-6"
        onClick={handleSubmit(onSubmit)}
        disabled={isLoading}
        label={isLoading ? "Asignando..." : "Asignar Incidente"}
      />
    </div>
  );
}
