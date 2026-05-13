import { AppButton } from "@/components/atoms/AppButton/AppButton";
import { AppBadge } from "@/components/atoms";
import { AppLabel } from "@/components/atoms";

export function RepairEvidenceUploader() {
  return (
    <div className="flex flex-col gap-1.5">
      <div className="flex items-center gap-2">
        <AppLabel>Evidencia de Reparación</AppLabel>
        <AppBadge label="Próximamente" variant="secondary" />
      </div>

      <div className="flex gap-2">
        <AppButton
          variant="outline"
          className="flex-1 h-16 border-dashed flex flex-col gap-1"
          disabled
          label=""
        >
          <span className="text-lg">📷</span>
          <span className="text-xs text-muted-foreground">Subir Imagen</span>
        </AppButton>

        <AppButton
          variant="outline"
          className="flex-1 h-16 border-dashed flex flex-col gap-1"
          disabled
          label=""
        >
          <span className="text-lg">🎥</span>
          <span className="text-xs text-muted-foreground">Subir Video</span>
        </AppButton>
      </div>
    </div>
  );
}
