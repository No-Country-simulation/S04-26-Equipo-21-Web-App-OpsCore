import { AppBadge } from "@/components/atoms";
import { AppText } from "@/components/atoms";
import type { Severity } from "@/components/molecules/incidents/SeveritySelector";

export type IncidentQueueItemData = {
  id: string;
  machine: string;
  severity: Severity;
  status: "ABIERTO" | "EN PROGRESO" | "RESUELTO";
};

type IncidentQueueItemProps = {
  incident: IncidentQueueItemData;
  onView: (id: string) => void;
  onStartWork: (id: string) => void;
};

const SEVERITY_VARIANT: Record<
  Severity,
  "destructive" | "secondary" | "outline"
> = {
  critical: "destructive",
  medium: "secondary",
  low: "outline",
};

const SEVERITY_LABEL: Record<Severity, string> = {
  critical: "Crítica",
  medium: "Media",
  low: "Baja",
};

export function IncidentQueueItem({
  incident,
  onView,
  onStartWork,
}: IncidentQueueItemProps) {
  return (
    <div className="flex items-center justify-between px-4 py-3 border-b border-border last:border-0">
      <div className="flex flex-col gap-1">
        <div className="flex items-center gap-2">
          <AppText className="text-sm font-medium">#{incident.id}</AppText>
          <AppText className="text-sm text-muted-foreground">
            {incident.machine}
          </AppText>
        </div>
        <div className="flex items-center gap-2">
          <AppBadge
            label={SEVERITY_LABEL[incident.severity]}
            variant={SEVERITY_VARIANT[incident.severity]}
          />
          <AppBadge label={incident.status} variant="outline" />
        </div>
      </div>

      <div className="flex gap-2">
        <button
          onClick={() => onView(incident.id)}
          className="text-xs text-primary underline underline-offset-2"
        >
          Ver
        </button>
        {incident.status !== "RESUELTO" && (
          <button
            onClick={() => onStartWork(incident.id)}
            className="text-xs text-primary underline underline-offset-2"
          >
            Atender
          </button>
        )}
      </div>
    </div>
  );
}
