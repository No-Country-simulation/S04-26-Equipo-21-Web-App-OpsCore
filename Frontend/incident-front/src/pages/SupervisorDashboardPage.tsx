import { useState } from "react";
import { LayoutDashboard } from "lucide-react";
import { AppSheet } from "@/components/atoms/AppSheet/AppSheet";
import { AppText } from "@/components/atoms";
import { BasePageContainer } from "@/components/organisms/BasePageContainer";
import { StatsBar } from "@/components/molecules/supervisor/StatsBar";
import { IncidentList } from "@/components/organisms/supervisor/IncidentList";
import {
  AssignTechnicianForm,
  type AssignPayload,
} from "@/components/organisms/supervisor/AssingTechnicianForm";
import { AssignTechnicianDialog } from "@/components/organisms/supervisor/AssignTechnicianDialog";
import { IncidentTimeline } from "@/components/organisms/incidents/IncidentTimeline";
import type { IncidentTimelineData } from "@/components/organisms/types";
import type {
  IncidentStats,
  SupervisorIncident,
} from "@/components/molecules/types";

type SheetMode = "timeline" | "assign" | null;

// ─── Mocks ────────────────────────────────────────────────────────────────────

const MOCK_STATS: IncidentStats = {
  open: 12,
  inProgress: 5,
  critical: 2,
  slaRisk: 4,
};

const MOCK_INCIDENTS: SupervisorIncident[] = [
  {
    id: "201",
    machine: "CNC-22",
    area: "Línea 3",
    type: "Falla",
    severity: "CRITICA",
    status: "ABIERTO",
    assignedTo: null,
    slaRisk: true,
  },
  {
    id: "202",
    machine: "Prensa-8",
    area: "Línea 2",
    type: "Accidente",
    severity: "ALTA",
    status: "EN PROGRESO",
    assignedTo: "Miguel",
    slaRisk: false,
  },
  {
    id: "203",
    machine: "Horno-4",
    area: "Mantenimiento",
    type: "Preventivo",
    severity: "BAJA",
    status: "ABIERTO",
    assignedTo: null,
    slaRisk: false,
  },
];

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
    ],
    slaStatus: "breach_risk",
  },
  "202": {
    incidentId: "202",
    events: [
      {
        id: "1",
        time: "09:10",
        description: "Incidente reportado por operador",
      },
      { id: "2", time: "09:15", description: "Asignado a Miguel" },
      { id: "3", time: "09:30", description: "Técnico inició trabajo" },
    ],
    slaStatus: "within_sla",
  },
  "203": {
    incidentId: "203",
    events: [
      {
        id: "1",
        time: "08:00",
        description: "Incidente reportado por operador",
      },
    ],
    slaStatus: "within_sla",
  },
};

const MOCK_TECHNICIANS = [
  { value: "1", label: "Carlos Ramírez" },
  { value: "2", label: "Miguel Torres" },
  { value: "3", label: "Ana López" },
  { value: "4", label: "Luis Herrera" },
];

async function assignTechnician(
  incidentId: string,
  payload: AssignPayload,
): Promise<void> {
  await new Promise((r) => setTimeout(r, 800));
  console.log("Técnico asignado:", incidentId, payload);
}

// ─────────────────────────────────────────────────────────────────────────────

type AssignConfirmation = {
  incidentId: string;
  technicianName: string;
  estimatedSla: string;
};

export function SupervisorDashboardPage() {
  const [selectedId, setSelectedId] = useState<string | null>(null);
  const [sheetMode, setSheetMode] = useState<SheetMode>(null);
  const [isAssigning, setIsAssigning] = useState(false);
  const [assignDialog, setAssignDialog] = useState<AssignConfirmation | null>(
    null,
  );

  const selectedTimeline = selectedId ? MOCK_TIMELINES[selectedId] : null;

  const handleView = (id: string) => {
    setSelectedId(id);
    setSheetMode("timeline");
  };
  const handleAssign = (id: string) => {
    setSelectedId(id);
    setSheetMode("assign");
  };
  const handleClose = () => {
    setSelectedId(null);
    setSheetMode(null);
  };

  const handleAssignSubmit = async (payload: AssignPayload) => {
    if (!selectedId) return;
    setIsAssigning(true);
    try {
      await assignTechnician(selectedId, payload);
      const technicianName =
        MOCK_TECHNICIANS.find((t) => t.value === payload.technicianId)?.label ??
        payload.technicianId;
      setAssignDialog({
        incidentId: selectedId,
        technicianName,
        estimatedSla: payload.estimatedSla,
      });
      handleClose();
    } finally {
      setIsAssigning(false);
    }
  };

  return (
    <BasePageContainer title="Dashboard Supervisor">
      <div className="bg-background">
        <div className="px-4 py-6 max-w-lg mx-auto flex flex-col gap-6">
          <div className="flex gap-3 rounded-lg bg-muted/50 border border-border px-4 py-3">
            <LayoutDashboard
              size={16}
              className="text-muted-foreground shrink-0 mt-0.5"
            />
            <div className="flex flex-col gap-0.5">
              <AppText className="text-sm font-medium">
                {MOCK_INCIDENTS.length} incidente
                {MOCK_INCIDENTS.length !== 1 ? "s" : ""} activo
                {MOCK_INCIDENTS.length !== 1 ? "s" : ""}
              </AppText>
              <AppText className="text-xs text-muted-foreground">
                Toca 👁 para ver la trazabilidad o asigna un técnico con el
                botón de usuario.
              </AppText>
            </div>
          </div>

          <StatsBar stats={MOCK_STATS} />

          <IncidentList
            incidents={MOCK_INCIDENTS}
            onView={handleView}
            onAssign={handleAssign}
          />
        </div>

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

        <AppSheet
          open={sheetMode === "assign" && !!selectedId}
          onOpenChange={(v) => {
            if (!v) handleClose();
          }}
          title={selectedId ? `Asignar técnico — Incidente #${selectedId}` : ""}
          side="bottom"
          showCloseButton
        >
          {selectedId && (
            <AssignTechnicianForm
              incidentId={selectedId}
              technicians={MOCK_TECHNICIANS}
              onSubmit={handleAssignSubmit}
              isLoading={isAssigning}
            />
          )}
        </AppSheet>

        <AssignTechnicianDialog
          open={!!assignDialog}
          incidentId={assignDialog?.incidentId ?? null}
          technicianName={assignDialog?.technicianName ?? ""}
          estimatedSla={assignDialog?.estimatedSla ?? ""}
          onClose={() => setAssignDialog(null)}
        />
      </div>
    </BasePageContainer>
  );
}
