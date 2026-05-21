import {
  AppButton,
  AppDialog,
  AppDivider,
  AppText,
  AppBadge,
} from "@/components/atoms";

type AssignTechnicianDialogProps = {
  open: boolean;
  incidentId: string | null;
  technicianName: string;
  estimatedSla: string;
  onClose: () => void;
};

export function AssignTechnicianDialog({
  open,
  incidentId,
  technicianName,
  estimatedSla,
  onClose,
}: AssignTechnicianDialogProps) {
  return (
    <AppDialog
      open={open}
      onOpenChange={(v) => {
        if (!v) onClose();
      }}
      title="✅ Técnico Asignado"
      showCloseButton={false}
      footer={
        <AppButton
          className="w-full"
          onClick={onClose}
          label="Volver al dashboard"
        />
      }
    >
      <AppDivider />
      <div className="flex flex-col gap-3 py-1">
        <div className="flex items-center justify-between">
          <AppText className="text-sm text-muted-foreground">Incidente</AppText>
          <AppText className="text-sm font-medium">#{incidentId}</AppText>
        </div>
        <div className="flex items-center justify-between">
          <AppText className="text-sm text-muted-foreground">Técnico</AppText>
          <AppBadge label={technicianName} variant="secondary" />
        </div>
        <div className="flex items-center justify-between">
          <AppText className="text-sm text-muted-foreground">
            SLA estimado
          </AppText>
          <AppText className="text-sm font-medium">{estimatedSla}</AppText>
        </div>
      </div>
    </AppDialog>
  );
}
