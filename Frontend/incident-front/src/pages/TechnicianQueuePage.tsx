import { useState } from "react";
import { AppSheet } from "@/components/atoms/AppSheet/AppSheet";
import { IncidentQueue } from "@/components/organisms/incidents/IncidentQueue";
import { IncidentWorkspace } from "@/components/organisms/incidents/IncidentWorkspace";
import { IncidentTimeline } from "@/components/organisms/incidents/IncidentTimeline";
import type { IncidentQueueItemData } from "@/components/molecules/types";
import type {
  IncidentDetail,
  WorkspacePayload,
  IncidentTimelineData,
} from "@/components/organisms/types";

// ─── WIP API ──────────────────────────────────

type SheetMode = "timeline" | "workspace" | null;

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

const MOCK_TIMELINES: Record<string, IncidentTimelineData> = {
  "201": {
    incidentId: "201",
    events: [
      {
        id: "1",
        time: "10:01",
        description: "Incidente reportado por operador",
      },
      { id: "2", time: "10:03", description: "Supervisor notificado" },
      { id: "3", time: "10:06", description: "Asignado a Carlos" },
      { id: "4", time: "10:42", description: "Técnico inició trabajo" },
      { id: "5", time: "11:15", description: "Causa raíz clasificada" },
      { id: "6", time: "11:48", description: "Incidente resuelto" },
    ],
    resolutionTime: "1h 47m",
    slaStatus: "within_sla",
  },
  "198": {
    incidentId: "198",
    events: [
      {
        id: "1",
        time: "09:10",
        description: "Incidente reportado por operador",
      },
      { id: "2", time: "09:12", description: "Supervisor notificado" },
      { id: "3", time: "09:20", description: "Asignado a Carlos" },
      { id: "4", time: "09:45", description: "Técnico inició trabajo" },
    ],
    slaStatus: "breach_risk",
  },
  "176": {
    incidentId: "176",
    events: [
      {
        id: "1",
        time: "08:30",
        description: "Incidente reportado por operador",
      },
      { id: "2", time: "08:32", description: "Supervisor notificado" },
    ],
    slaStatus: "within_sla",
  },
};

// ─── API helpers ──────────────────────────────

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

// ─────────────────────────────────────────────

export function TechnicianQueuePage() {
  const [selectedId, setSelectedId] = useState<string | null>(null);
  const [sheetMode, setSheetMode] = useState<SheetMode>(null);
  const [isSaving, setIsSaving] = useState(false);
  const [isResolving, setIsResolving] = useState(false);

  const selectedIncident = selectedId ? MOCK_DETAIL[selectedId] : null;
  const selectedTimeline = selectedId ? MOCK_TIMELINES[selectedId] : null;

  const handleView = (id: string) => {
    setSelectedId(id);
    setSheetMode("timeline");
  };
  const handleStartWork = (id: string) => {
    setSelectedId(id);
    setSheetMode("workspace");
  };
  const handleClose = () => {
    setSelectedId(null);
    setSheetMode(null);
  };

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
      <div className="sticky top-0 z-10 bg-background border-b border-border px-4 py-4">
        <h1 className="text-lg font-semibold">Incidentes Asignados</h1>
      </div>

      <div className="px-4 py-6 max-w-lg mx-auto">
        <IncidentQueue
          incidents={MOCK_INCIDENTS}
          onView={handleView}
          onStartWork={handleStartWork}
        />
      </div>

      {/* Ver — trazabilidad */}
      <AppSheet
        open={sheetMode === "timeline" && !!selectedTimeline}
        onOpenChange={(v) => {
          if (!v) handleClose();
        }}
        title={selectedId ? `Incidente #${selectedId} — Trazabilidad` : ""}
        side="bottom"
        showCloseButton
      >
        {selectedTimeline && <IncidentTimeline data={selectedTimeline} />}
      </AppSheet>

      {/* Atender — workspace */}
      <AppSheet
        open={sheetMode === "workspace" && !!selectedIncident}
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
