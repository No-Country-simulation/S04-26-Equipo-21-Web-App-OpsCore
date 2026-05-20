import { AppButton, AppDialog, AppText } from "@/components/atoms";

type LogoutDialogProps = {
  open: boolean;
  onConfirm: () => void;
  onCancel: () => void;
};

export function LogoutDialog({ open, onConfirm, onCancel }: LogoutDialogProps) {
  return (
    <AppDialog
      open={open}
      onOpenChange={(v) => {
        if (!v) onCancel();
      }}
      title="Cerrar sesión"
      showCloseButton={false}
      footer={
        <div className="flex gap-2 w-full">
          <AppButton
            variant="outline"
            className="flex-1"
            onClick={onCancel}
            label="Cancelar"
          />
          <AppButton
            variant="destructive"
            className="flex-1"
            onClick={onConfirm}
            label="Cerrar sesión"
          />
        </div>
      }
    >
      <AppText className="text-sm text-muted-foreground">
        ¿Estás seguro que deseas cerrar sesión?
      </AppText>
    </AppDialog>
  );
}
