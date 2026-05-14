import { useState } from "react";
import {
  IncidentReportForm,
  type IncidentReportData,
} from "@/components/organisms/incidents/IncidentReportForm";
import { IncidentConfirmationDialog } from "@/components/organisms/incidents/IncidentConfirmationDialog";
import type { IncidentConfirmationData } from "@/components/organisms/types";

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

const INCIDENT_TYPES = [
  { value: "failure", label: "Falla" },
  { value: "accident", label: "Accidente" },
  { value: "near_miss", label: "Casi accidente" },
  { value: "quality", label: "Calidad" },
  { value: "maintenance", label: "Mantenimiento preventivo" },
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
    <div className="min-h-screen bg-background">
      <div className="sticky top-0 z-10 bg-background border-b border-border px-4 py-4">
        <h1 className="text-lg font-semibold">Reportar Incidente</h1>
      </div>

      <div className="px-4 py-6 max-w-lg mx-auto">
        <IncidentReportForm
          key={formKey}
          machines={MACHINES}
          areas={AREAS}
          incidentTypes={INCIDENT_TYPES}
          onSubmit={handleSubmit}
          isLoading={isLoading}
        />
      </div>

      <IncidentConfirmationDialog
        open={!!confirmation}
        data={confirmation}
        onClose={handleClose}
      />
    </div>
  );
}
