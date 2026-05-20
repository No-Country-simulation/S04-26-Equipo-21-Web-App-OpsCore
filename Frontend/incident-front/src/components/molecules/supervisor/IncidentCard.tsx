import { AppBadge, AppText, AppIconButton } from "@/components/atoms";
import { Eye, UserPlus } from "lucide-react";
import { SEVERITY_LABEL, SEVERITY_VARIANT } from "@/constants";
import type { IncidentCardProps } from "@/components/molecules/types";

export function IncidentCard({
  incident,
  onView,
  onAssign,
}: IncidentCardProps) {
  const isCritical = incident.severity === "CRITICA";

  return (
    <div
      className={`flex flex-col gap-2 rounded-lg border border-border bg-card p-4 ${isCritical ? "border-l-2 border-l-destructive" : ""}`}
    >
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-2">
          <AppText className="text-sm font-semibold">#{incident.id}</AppText>
          <AppText className="text-sm text-muted-foreground">
            {incident.machine}
          </AppText>
          {incident.slaRisk && <span title="Riesgo SLA">⚠️</span>}
        </div>
        <div className="flex items-center gap-1">
          <AppIconButton
            aria-label="Ver trazabilidad"
            title="Ver trazabilidad"
            icon={<Eye size={15} />}
            onClick={() => onView(incident.id)}
          />
          <AppIconButton
            aria-label="Asignar técnico"
            title="Asignar técnico"
            icon={<UserPlus size={15} />}
            onClick={() => onAssign(incident.id)}
          />
        </div>
      </div>

      <AppText className="text-xs text-muted-foreground">
        {incident.area} · {incident.type}
      </AppText>

      <div className="flex items-center gap-2 flex-wrap">
        <AppBadge
          label={SEVERITY_LABEL[incident.severity]}
          variant={SEVERITY_VARIANT[incident.severity]}
        />
        <AppBadge label={incident.status} variant="outline" />
        {incident.assignedTo ? (
          <AppBadge label={incident.assignedTo} variant="secondary" />
        ) : (
          <AppBadge label="Sin asignar" variant="outline" />
        )}
      </div>
    </div>
  );
}
