import { AppLabel, AppInput } from "../atoms";
import type { FormFieldProps } from "./types";

export function FormField({
  label,
  id,
  errorMessage,
  ...inputProps
}: FormFieldProps) {
  const fieldId = id ?? label.toLowerCase().replace(/\s+/g, "-");

  return (
    <div className="flex flex-col gap-1.5">
      <AppLabel htmlFor={fieldId}>{label}</AppLabel>
      <AppInput id={fieldId} errorMessage={errorMessage} {...inputProps} />
    </div>
  );
}
