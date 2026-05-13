import { type ComponentProps } from "react";
import { AppInput } from "@/components/atoms";

export type FormFieldProps = ComponentProps<typeof AppInput> & {
  label: string;
};

export type OtpInputProps = {
  label?: string;
  value: string;
  onChange: (value: string) => void;
  errorMessage?: string;
  disabled?: boolean;
};

export type TrustDeviceCheckboxProps = {
  checked: boolean;
  onCheckedChange: (checked: boolean) => void;
  disabled?: boolean;
};

export type IncidentQueueItemData = {
  id: string;
  machine: string;
  severity: Severity;
  status: "ABIERTO" | "EN PROGRESO" | "RESUELTO";
};

export type IncidentQueueItemProps = {
  incident: IncidentQueueItemData;
  onView: (id: string) => void;
  onStartWork: (id: string) => void;
};

export type SafetyItem = {
  id: string;
  label: string;
};

export type SafetyChecklistProps = {
  checked: Record<string, boolean>;
  onChange: (id: string, value: boolean) => void;
  errorMessage?: string;
  disabled?: boolean;
};

export type Severity = "low" | "medium" | "critical";

export type SeveritySelectorProps = {
  value: Severity | "";
  onChange: (value: Severity) => void;
  errorMessage?: string;
  disabled?: boolean;
};

export type TechnicianChecklistProps = {
  checked: Record<string, boolean>;
  onChange: (id: string, value: boolean) => void;
  errorMessage?: string;
  disabled?: boolean;
};

export type TimelineEvent = {
  id: string;
  time: string;
  description: string;
};

export type TimelineItemProps = {
  event: TimelineEvent;
  isLast?: boolean;
};
