import { AppText, AppBadge, AppDivider } from "@/components/atoms";
import { TimelineItem } from "@/components/molecules/incidents/TimelineItem";
import type { IncidentTimelineProps } from "../types";
import { SLA_BADGE } from "@/constants";

export function IncidentTimeline({ data }: IncidentTimelineProps) {
  const sla = data.slaStatus ? SLA_BADGE[data.slaStatus] : null;

  return (
    <div className="flex flex-col gap-0">
      <div className="px-4 py-4">
        {data.events.map((event, index) => (
          <TimelineItem
            key={event.id}
            event={event}
            isLast={index === data.events.length - 1}
          />
        ))}
      </div>

      <AppDivider />

      <div className="px-4 py-4 flex flex-col gap-3">
        {data.resolutionTime && (
          <div className="flex items-center justify-between">
            <AppText className="text-sm text-muted-foreground">
              Tiempo de resolución
            </AppText>
            <AppText className="text-sm font-medium">
              {data.resolutionTime}
            </AppText>
          </div>
        )}

        {sla && (
          <div className="flex items-center justify-between">
            <AppText className="text-sm text-muted-foreground">
              Estado SLA
            </AppText>
            <AppBadge label={sla.label} variant={sla.variant} />
          </div>
        )}
      </div>
    </div>
  );
}
