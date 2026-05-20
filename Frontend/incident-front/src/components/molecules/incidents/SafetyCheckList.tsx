import { AppLabel, AppDivider, AppText, AppCheckbox } from "@/components/atoms";
import { SAFETY_ITEMS } from "@/constants";
import type { SafetyChecklistProps } from "../types";
import { ShieldCheck } from "lucide-react";

export function SafetyChecklist({
  checked,
  onChange,
  errorMessage,
  disabled,
}: SafetyChecklistProps) {
  return (
    <div className="flex flex-col gap-0 rounded-lg border border-border overflow-hidden">
      <div className="px-4 py-3 bg-muted/50">
        <div className="px-4 py-3 bg-muted/50 flex items-center gap-2">
          <ShieldCheck size={14} className="text-muted-foreground" />
          <AppText className="text-sm font-medium">
            Checklist de Seguridad
          </AppText>
        </div>
      </div>

      <AppDivider />

      <div className="flex flex-col divide-y divide-border">
        {SAFETY_ITEMS.map((item) => (
          <label
            key={item.id}
            className="flex items-center gap-3 px-4 py-3 cursor-pointer hover:bg-muted/30 transition-colors"
          >
            <AppCheckbox
              id={item.id}
              checked={!!checked[item.id]}
              onCheckedChange={(v) => onChange(item.id, v === true)}
              disabled={disabled}
            />
            <AppLabel
              htmlFor={item.id}
              className="cursor-pointer font-normal text-sm"
            >
              {item.label}
            </AppLabel>
          </label>
        ))}
      </div>

      {errorMessage && (
        <div className="px-4 pb-3">
          <span className="text-xs text-red-500">{errorMessage}</span>
        </div>
      )}
    </div>
  );
}
