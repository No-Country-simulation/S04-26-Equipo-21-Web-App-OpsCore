import { useState } from "react";
import {
  IncidentReportForm,
  type IncidentReportData,
} from "@/components/organisms/incidents/IncidentReportForm";
import { IncidentConfirmationDialog } from "@/components/organisms/incidents/IncidentConfirmationDialog";
import type { IncidentConfirmationData } from "@/components/organisms/types";
import { BasePageContainer } from "@/components/organisms/BasePageContainer";
import { INCIDENT_TYPES } from "@/constants";
import { useAreas, useEstaciones } from "@/hooks/useInfo";
import { TriangleAlert } from "lucide-react";

// ─── Catálogos estáticos — reemplazar con llamadas a API ─────────────────────

const MACHINES = [
  { value: "cnc-22", label: "CNC-22" },
  { value: "cnc-15", label: "CNC-15" },
  { value: "press-01", label: "Prensa-01" },
  { value: "robot-03", label: "Robot-03" },
];

const AREAS = [
  { value: "production-line-3", label: "Línea de Producción 3" },
  { value: "production-line-1", label: "Línea de Producción 1" },
  { value: "warehouse", label: "Almacén" },
  { value: "maintenance", label: "Mantenimiento" },
];

// ─── WIP API ──────────────────────

async function submitIncident(
  data: IncidentReportData,
): Promise<IncidentConfirmationData> {
  await new Promise((r) => setTimeout(r, 1000));
  // Aquí irá el POST + conexión a websocket
  return {
    incidentId: "201",
    status: "ABIERTO",
    supervisorNotified:
      (data.safetyChecklist as Record<string, boolean>)[
        "supervisor_notified"
      ] ?? false,
  };
}

// ───────────────────────────────────────────────────────────────────────

export function MobileIncidentReportPage() {
  const [isLoading, setIsLoading] = useState(false);
  const [confirmation, setConfirmation] =
    useState<IncidentConfirmationData | null>(null);
  const [formKey, setFormKey] = useState(0);
  const [selectedArea, setSelectedArea] = useState("");

  const { options: areaOptions, isLoading: areasLoading } = useAreas();

  const {
    options: estacionOptions,
    isLoading: estacionesLoading,
    isError: estacionesError,
  } = useEstaciones(selectedArea);
  const handleSubmit = async (data: IncidentReportData) => {
    setIsLoading(true);
    try {
      const result = await submitIncident(data);
      setConfirmation(result);
    } finally {
      setIsLoading(false);
    }
  };

  const handleClose = () => {
    setConfirmation(null);
    setFormKey((k) => k + 1);
  };

  return (
    <BasePageContainer title="Reportar Incidente">
      <div className="bg-background">
        <div className="px-4 py-6 max-w-lg mx-auto flex flex-col gap-6">
          <div className="flex gap-3 rounded-lg bg-amber-50 border border-amber-200 px-4 py-3">
            <TriangleAlert
              size={16}
              className="text-amber-500 shrink-0 mt-0.5"
            />
            <p className="text-sm text-amber-800">
              Completa todos los campos con precisión. La información que
              ingreses será asignada al técnico responsable de atender el
              incidente.
            </p>
          </div>
          <IncidentReportForm
            key={formKey}
            areas={areaOptions.length > 0 ? areaOptions : AREAS}
            machines={
              estacionesError || estacionOptions.length === 0
                ? MACHINES
                : estacionOptions
            }
            incidentTypes={INCIDENT_TYPES}
            onSubmit={handleSubmit}
            onAreaChange={setSelectedArea}
            isLoading={isLoading || areasLoading || estacionesLoading}
          />
        </div>

        <IncidentConfirmationDialog
          open={!!confirmation}
          data={confirmation}
          onClose={handleClose}
        />
      </div>
    </BasePageContainer>
  );
}
