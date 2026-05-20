import { AppText } from "@/components/atoms";
import { ClipboardList } from "lucide-react";
import { IncidentCard } from "@/components/molecules/supervisor/IncidentCard";
import type { IncidentListProps } from "../types";

export function IncidentList({
  incidents,
  onView,
  onAssign,
  isLoading,
}: IncidentListProps) {
  if (isLoading) {
    return (
      <div className="flex items-center justify-center py-12">
        <AppText className="text-sm text-muted-foreground">
          Cargando incidentes...
        </AppText>
      </div>
    );
  }

  if (incidents.length === 0) {
    return (
      <div className="flex flex-col items-center justify-center gap-2 py-12">
        <ClipboardList size={32} className="text-muted-foreground" />
        <AppText className="text-sm text-muted-foreground">
          No hay incidentes activos
        </AppText>
      </div>
    );
  }

  return (
    <div className="flex flex-col gap-3">
      {incidents.map((incident) => (
        <IncidentCard
          key={incident.id}
          incident={incident}
          onView={onView}
          onAssign={onAssign}
        />
      ))}
    </div>
  );
}
