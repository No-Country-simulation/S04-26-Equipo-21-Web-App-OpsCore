import { AppText, AppDot } from "@/components/atoms";
import type { TimelineItemProps } from "../types";

export function TimelineItem({ event, isLast }: TimelineItemProps) {
  return (
    <div className="flex gap-3">
      <div className="flex flex-col items-center">
        <AppDot />
        {!isLast && <div className="w-px flex-1 bg-border mt-1" />}
      </div>

      <div className="flex gap-3 pb-4">
        <AppText className="text-xs text-muted-foreground w-12 shrink-0 tabular-nums">
          {event.time}
        </AppText>
        <AppText className="text-sm">{event.description}</AppText>
      </div>
    </div>
  );
}
