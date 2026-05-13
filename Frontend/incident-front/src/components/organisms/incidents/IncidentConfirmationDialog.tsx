import { AppDialog } from "@/components/atoms/AppDialog/AppDialog";
import { AppButton } from "@/components/atoms";
import { AppBadge } from "@/components/atoms";
import { AppDivider } from "@/components/atoms";
import { AppText } from "@/components/atoms";

export type IncidentConfirmationData = {
  incidentId: string;
  status: string;
  supervisorNotified: boolean;
};

type IncidentConfirmationDialogProps = {
  open: boolean;
  data: IncidentConfirmationData | null;
  onClose: () => void;
};

export function IncidentConfirmationDialog({
  open,
  data,
  onClose,
}: IncidentConfirmationDialogProps) {
  if (!data) return null;

  return (
    <AppDialog
      open={open}
      onOpenChange={(v) => {
        if (!v) onClose();
      }}
      title="✅ Incidente Enviado"
      showCloseButton={false}
      footer={
        <AppButton
          className="w-full"
          onClick={onClose}
          label="Volver al inicio"
        />
      }
    >
      <AppDivider />

      <div className="flex flex-col gap-3 py-1">
        <div className="flex items-center justify-between">
          <AppText className="text-sm text-muted-foreground">
            ID de incidente
          </AppText>
          <AppText className="text-sm font-medium">#{data.incidentId}</AppText>
        </div>

        <div className="flex items-center justify-between">
          <AppText className="text-sm text-muted-foreground">Estado</AppText>
          <AppBadge label={data.status} variant="secondary" />
        </div>

        {data.supervisorNotified && (
          <div className="flex items-center justify-between">
            <AppText className="text-sm text-muted-foreground">
              Supervisor
            </AppText>
            <AppText className="text-sm text-green-600 font-medium">
              Notificado exitosamente
            </AppText>
          </div>
        )}
      </div>
    </AppDialog>
  );
}
