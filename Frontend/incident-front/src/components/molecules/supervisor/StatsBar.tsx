import { AppText } from "@/components/atoms";
import type { IncidentStats, StatItemProps } from "../types";

function StatItem({ label, value, highlight }: StatItemProps) {
  return (
    <div className="flex flex-col items-center gap-0.5">
      <AppText
        className={`text-lg font-bold ${highlight ? "text-destructive" : "text-foreground"}`}
      >
        {value}
      </AppText>
      <AppText className="text-xs text-muted-foreground text-center">
        {label}
      </AppText>
    </div>
  );
}

type StatsBarProps = {
  stats: IncidentStats;
};

export function StatsBar({ stats }: StatsBarProps) {
  return (
    <div className="grid grid-cols-4 gap-2 rounded-lg border border-border bg-card px-4 py-3">
      <StatItem label="Abiertos" value={stats.open} />
      <StatItem label="En progreso" value={stats.inProgress} />
      <StatItem label="Críticos" value={stats.critical} highlight />
      <StatItem label="Riesgo SLA" value={stats.slaRisk} highlight />
    </div>
  );
}
