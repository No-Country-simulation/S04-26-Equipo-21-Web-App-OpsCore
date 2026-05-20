import type { SafetyItem, Severity } from "@/components/molecules/types";
import type { SlaStatus } from "@/components/organisms/types";
import type { TechnicianChecklistItem } from "@/types";

export const SAFETY_ITEMS: SafetyItem[] = [
  { id: "emergency_stop", label: "Paro de emergencia activado" },
  { id: "area_secured", label: "Área asegurada" },
  { id: "supervisor_notified", label: "Supervisor notificado" },
  { id: "ppe_verified", label: "EPP verificado" },
];

export const TECHNICIAN_CHECKLIST_ITEMS: TechnicianChecklistItem[] = [
  { id: "emergency_stop", label: "Paro de emergencia activado" },
  { id: "machine_disconnected", label: "Máquina desconectada" },
  { id: "area_secured", label: "Área asegurada" },
];

export const OTP_LENGTH = 6;

export const SEVERITY_VARIANT: Record<
  Severity,
  "destructive" | "secondary" | "outline"
> = {
  BAJA: "outline",
  NORMAL: "secondary",
  ALTA: "destructive",
  CRITICA: "destructive",
};

export const SEVERITY_LABEL: Record<Severity, string> = {
  BAJA: "Baja",
  NORMAL: "Media",
  ALTA: "Alta",
  CRITICA: "Crítica",
};

export const OPTIONS: { value: Severity; label: string }[] = [
  { value: "BAJA", label: "Baja" },
  { value: "NORMAL", label: "Media" },
  { value: "ALTA", label: "Alta" },
  { value: "CRITICA", label: "Crítica" },
];

export const ROOT_CAUSES = [
  { value: "electrical", label: "Falla eléctrica" },
  { value: "mechanical", label: "Falla mecánica" },
  { value: "software", label: "Falla de software" },
  { value: "operator_error", label: "Error de operador" },
  { value: "wear", label: "Desgaste natural" },
  { value: "external", label: "Factor externo" },
];

export const SLA_BADGE: Record<
  SlaStatus,
  { label: string; variant: "secondary" | "outline" | "destructive" }
> = {
  within_sla: { label: "Dentro de SLA", variant: "secondary" },
  breach_risk: { label: "Riesgo de incumplimiento", variant: "outline" },
  breached: { label: "SLA incumplido", variant: "destructive" },
};

export const INCIDENT_TYPES = [
  { value: "FALLA_OPERATIVA", label: "Falla operativa" },
  { value: "ACCIDENTE", label: "Accidente" },
  { value: "CASI_ACCIDENTE", label: "Casi accidente" },
  { value: "CALIDAD", label: "Calidad" },
  { value: "MANTENIMIENTO_PREVENTIVO", label: "Mantenimiento preventivo" },
  { value: "MANTENIMIENTO_CORRECTIVO", label: "Mantenimiento correctivo" },
  { value: "SEGURIDAD", label: "Seguridad" },
  { value: "AMBIENTAL", label: "Ambiental" },
  { value: "OTRO", label: "Otro" },
];
