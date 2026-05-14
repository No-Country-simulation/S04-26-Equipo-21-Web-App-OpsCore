import { AppBadge, AppText, AppIconButton } from "@/components/atoms";
import { Eye, Wrench } from "lucide-react";
import type { IncidentQueueItemProps } from "../types";
import { SEVERITY_LABEL, SEVERITY_VARIANT } from "@/constants";

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

      <div className="flex items-center gap-2">
        <AppIconButton
          aria-label="Ver incidente"
          icon={<Eye size={16} />}
          onClick={() => onView(incident.id)}
        />

        {incident.status !== "RESUELTO" && (
          <AppIconButton
            aria-label="Atender incidente"
            icon={<Wrench size={16} />}
            onClick={() => onStartWork(incident.id)}
          />
        )}
      </div>
    </div>
  );
}
