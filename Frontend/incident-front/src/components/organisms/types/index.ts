import type {
  IncidentQueueItemData,
  Severity,
  SupervisorIncident,
  TimelineEvent,
} from "@/components/molecules/types";
import type { IncidentReportData } from "../incidents/IncidentReportForm";
import type { AssignPayload } from "../supervisor/AssingTechnicianForm";
import type { ReactNode } from "react";

export type LoginMode = "default" | "technician";

export type LoginCredentials = {
  username: string;
  password: string;
  mode: LoginMode;
};

export type LoginFormErrors = Partial<Record<"email" | "password", string>>;

export type LoginFormProps = {
  onSubmit: (credentials: LoginCredentials) => Promise<void> | void;
  isLoading?: boolean;
};

export type TwoFactorPayload = {
  code: string;
  trustDevice: boolean;
};

export type TwoFactorFormProps = {
  onSubmit: (payload: TwoFactorPayload) => Promise<void> | void;
  onBack?: () => void;
  isLoading?: boolean;
};

export type IncidentConfirmationData = {
  incidentId: string;
  status: string;
  supervisorNotified: boolean;
};

export type IncidentConfirmationDialogProps = {
  open: boolean;
  data: IncidentConfirmationData | null;
  onClose: () => void;
};

export type IncidentQueueProps = {
  incidents: IncidentQueueItemData[];
  onView: (id: string) => void;
  onStartWork: (id: string) => void;
  isLoading?: boolean;
};

export type IncidentReportFormProps = {
  machines: { value: string; label: string }[];
  areas: { value: string; label: string }[];
  incidentTypes: { value: string; label: string }[];
  onSubmit: (data: IncidentReportData) => Promise<void> | void;
  isLoading?: boolean;
  onAreaChange?: (areaId: string) => void;
};

export type IncidentDetail = {
  id: string;
  machine: string;
  area: string;
  severity: Severity;
  status: string;
};

export type WorkspacePayload = {
  checklist: Record<string, boolean>;
  diagnosticNotes: string;
  rootCause: string;
};

export type IncidentWorkspaceProps = {
  incident: IncidentDetail;
  onSave: (payload: WorkspacePayload) => Promise<void> | void;
  onResolve: (payload: WorkspacePayload) => Promise<void> | void;
  isSaving?: boolean;
  isResolving?: boolean;
};

export type SlaStatus = "within_sla" | "breach_risk" | "breached";

export type IncidentTimelineData = {
  incidentId: string;
  events: TimelineEvent[];
  resolutionTime?: string;
  slaStatus?: SlaStatus;
};

export type IncidentTimelineProps = {
  data: IncidentTimelineData;
};

export type AssignTechnicianFormProps = {
  incidentId: string;
  technicians: { value: string; label: string }[];
  onSubmit: (payload: AssignPayload) => Promise<void> | void;
  isLoading?: boolean;
};

export type IncidentListProps = {
  incidents: SupervisorIncident[];
  onView: (id: string) => void;
  onAssign: (id: string) => void;
  isLoading?: boolean;
};

export type RouteTheme = {
  gradient: string;
  accent: string;
};

export type BasePageContainerProps = {
  children: ReactNode;
  title?: string;
};
