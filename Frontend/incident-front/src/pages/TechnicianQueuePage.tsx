import { useState } from "react";
import { AppSheet } from "@/components/atoms/AppSheet/AppSheet";
import { IncidentQueue } from "@/components/organisms/incidents/IncidentQueue";
import {
  IncidentWorkspace,
  type IncidentDetail,
  type WorkspacePayload,
} from "@/components/organisms/incidents/IncidentWorkspace";
import type { IncidentQueueItemData } from "@/components/molecules/incidents/IncidentQueueItem";

// ─── Stubs — reemplazar con API + websockets ──────────────────────────────────

const MOCK_INCIDENTS: IncidentQueueItemData[] = [
  { id: "201", machine: "CNC-22", severity: "critical", status: "ABIERTO" },
  { id: "198", machine: "Prensa-8", severity: "medium", status: "EN PROGRESO" },
  { id: "176", machine: "Horno-4", severity: "low", status: "ABIERTO" },
];

const MOCK_DETAIL: Record<string, IncidentDetail> = {
  "201": {
    id: "201",
    machine: "CNC-22",
    area: "Línea de Producción 3",
    severity: "critical",
    status: "ABIERTO",
  },
  "198": {
    id: "198",
    machine: "Prensa-8",
    area: "Línea de Producción 1",
    severity: "medium",
    status: "EN PROGRESO",
  },
  "176": {
    id: "176",
    machine: "Horno-4",
    area: "Mantenimiento",
    severity: "low",
    status: "ABIERTO",
  },
};

async function saveProgress(
  id: string,
  payload: WorkspacePayload,
): Promise<void> {
  await new Promise((r) => setTimeout(r, 800));
  console.log("Progreso guardado:", id, payload);
}

async function resolveIncident(
  id: string,
  payload: WorkspacePayload,
): Promise<void> {
  await new Promise((r) => setTimeout(r, 800));
  console.log("Incidente resuelto:", id, payload);
}

// ─── Page ────────────────────────────────────────────────────────────────────

export function TechnicianQueuePage() {
  const [selectedId, setSelectedId] = useState<string | null>(null);
  const [isSaving, setIsSaving] = useState(false);
  const [isResolving, setIsResolving] = useState(false);

  const selectedIncident = selectedId ? MOCK_DETAIL[selectedId] : null;

  const handleView = (id: string) => setSelectedId(id);
  const handleStartWork = (id: string) => setSelectedId(id);
  const handleClose = () => setSelectedId(null);

  const handleSave = async (payload: WorkspacePayload) => {
    if (!selectedId) return;
    setIsSaving(true);
    try {
      await saveProgress(selectedId, payload);
    } finally {
      setIsSaving(false);
    }
  };

  const handleResolve = async (payload: WorkspacePayload) => {
    if (!selectedId) return;
    setIsResolving(true);
    try {
      await resolveIncident(selectedId, payload);
      handleClose();
    } finally {
      setIsResolving(false);
    }
  };

  return (
    <div className="min-h-screen bg-background">
      {/* Header */}
      <div className="sticky top-0 z-10 bg-background border-b border-border px-4 py-4">
        <h1 className="text-lg font-semibold">Incidentes Asignados</h1>
      </div>

      {/* Queue */}
      <div className="px-4 py-6 max-w-lg mx-auto">
        <IncidentQueue
          incidents={MOCK_INCIDENTS}
          onView={handleView}
          onStartWork={handleStartWork}
        />
      </div>

      {/* Workspace Sheet — slides up from bottom */}
      <AppSheet
        open={!!selectedId}
        onOpenChange={(v) => {
          if (!v) handleClose();
        }}
        title={selectedIncident ? `Incidente #${selectedIncident.id}` : ""}
        side="bottom"
        showCloseButton
      >
        {selectedIncident && (
          <IncidentWorkspace
            incident={selectedIncident}
            onSave={handleSave}
            onResolve={handleResolve}
            isSaving={isSaving}
            isResolving={isResolving}
          />
        )}
      </AppSheet>
    </div>
  );
}
