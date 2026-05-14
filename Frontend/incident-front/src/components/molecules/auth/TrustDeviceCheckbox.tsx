import { AppCheckbox } from "@/components/atoms/AppCheckbox/AppCheckbox";
import { AppLabel } from "@/components/atoms";
import type { TrustDeviceCheckboxProps } from "../types";

export function TrustDeviceCheckbox({
  checked,
  onCheckedChange,
  disabled,
}: TrustDeviceCheckboxProps) {
  return (
    <div className="flex items-center gap-2">
      <AppCheckbox
        id="trust-device"
        checked={checked}
        onCheckedChange={(v) => onCheckedChange(v === true)}
        disabled={disabled}
      />
      <AppLabel
        htmlFor="trust-device"
        className="cursor-pointer font-normal text-sm text-muted-foreground"
      >
        Confiar en este dispositivo por 7 dias
      </AppLabel>
    </div>
  );
}
