import { AppText } from "@/components/atoms";
import {
  IncidentQueueItem,
  type IncidentQueueItemData,
} from "@/components/molecules/incidents/IncidentQueueItem";

type IncidentQueueProps = {
  incidents: IncidentQueueItemData[];
  onView: (id: string) => void;
  onStartWork: (id: string) => void;
  isLoading?: boolean;
};

export function IncidentQueue({
  incidents,
  onView,
  onStartWork,
  isLoading,
}: IncidentQueueProps) {
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
      <div className="flex items-center justify-center py-12">
        <AppText className="text-sm text-muted-foreground">
          No tienes incidentes asignados
        </AppText>
      </div>
    );
  }

  return (
    <div className="rounded-lg border border-border overflow-hidden">
      {incidents.map((incident) => (
        <IncidentQueueItem
          key={incident.id}
          incident={incident}
          onView={onView}
          onStartWork={onStartWork}
        />
      ))}
    </div>
  );
}
