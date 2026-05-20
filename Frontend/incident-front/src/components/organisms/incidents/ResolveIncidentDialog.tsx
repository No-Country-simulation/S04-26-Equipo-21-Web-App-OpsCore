import {
  AppButton,
  AppDialog,
  AppDivider,
  AppText,
  AppBadge,
} from "@/components/atoms";

type ResolveIncidentDialogProps = {
  open: boolean;
  incidentId: string | null;
  rootCause: string;
  resolutionTime?: string;
  onClose: () => void;
};

export function ResolveIncidentDialog({
  open,
  incidentId,
  rootCause,
  resolutionTime,
  onClose,
}: ResolveIncidentDialogProps) {
  return (
    <AppDialog
      open={open}
      onOpenChange={(v) => {
        if (!v) onClose();
      }}
      title="✅ Incidente Resuelto"
      showCloseButton={false}
      footer={
        <AppButton
          className="w-full"
          onClick={onClose}
          label="Volver a mis incidentes"
        />
      }
    >
      <AppDivider />
      <div className="flex flex-col gap-3 py-1">
        <div className="flex items-center justify-between">
          <AppText className="text-sm text-muted-foreground">
            ID de incidente
          </AppText>
          <AppText className="text-sm font-medium">#{incidentId}</AppText>
        </div>
        <div className="flex items-center justify-between">
          <AppText className="text-sm text-muted-foreground">Estado</AppText>
          <AppBadge label="RESUELTO" variant="secondary" />
        </div>
        <div className="flex items-center justify-between">
          <AppText className="text-sm text-muted-foreground">
            Causa raíz
          </AppText>
          <AppText className="text-sm font-medium">{rootCause}</AppText>
        </div>
        {resolutionTime && (
          <div className="flex items-center justify-between">
            <AppText className="text-sm text-muted-foreground">
              Tiempo de resolución
            </AppText>
            <AppText className="text-sm font-medium">{resolutionTime}</AppText>
          </div>
        )}
      </div>
    </AppDialog>
  );
}
