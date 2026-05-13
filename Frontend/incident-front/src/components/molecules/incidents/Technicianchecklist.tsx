import { AppCheckbox } from "@/components/atoms/AppCheckbox/AppCheckbox";
import { AppLabel } from "@/components/atoms";
import { AppText } from "@/components/atoms";
import { AppDivider } from "@/components/atoms";
import { TECHNICIAN_CHECKLIST_ITEMS } from "@/constants";

type TechnicianChecklistProps = {
  checked: Record<string, boolean>;
  onChange: (id: string, value: boolean) => void;
  errorMessage?: string;
  disabled?: boolean;
};

export function TechnicianChecklist({
  checked,
  onChange,
  errorMessage,
  disabled,
}: TechnicianChecklistProps) {
  return (
    <div className="flex flex-col gap-0 rounded-lg border border-border overflow-hidden">
      <div className="px-4 py-3 bg-muted/50">
        <AppText className="text-sm font-medium">
          Verificación de Checklist
        </AppText>
      </div>

      <AppDivider />

      <div className="flex flex-col divide-y divide-border">
        {TECHNICIAN_CHECKLIST_ITEMS.map((item) => (
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
