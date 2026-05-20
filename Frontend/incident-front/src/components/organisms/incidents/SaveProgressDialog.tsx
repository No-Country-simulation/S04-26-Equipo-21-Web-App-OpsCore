import { AppButton, AppDialog, AppDivider, AppText } from "@/components/atoms";

type SaveProgressDialogProps = {
  open: boolean;
  incidentId: string | null;
  onClose: () => void;
};

export function SaveProgressDialog({
  open,
  incidentId,
  onClose,
}: SaveProgressDialogProps) {
  return (
    <AppDialog
      open={open}
      onOpenChange={(v) => {
        if (!v) onClose();
      }}
      title="✅ Progreso Guardado"
      showCloseButton={false}
      footer={
        <AppButton className="w-full" onClick={onClose} label="Continuar" />
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
        <AppText className="text-sm text-muted-foreground">
          El avance ha sido registrado correctamente. Puedes continuar
          trabajando en el incidente.
        </AppText>
      </div>
    </AppDialog>
  );
}
