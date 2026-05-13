import { AppButton, AppBadge, AppLabel, AppText } from "@/components/atoms";

export function EvidenceUploader() {
  return (
    <div className="flex flex-col gap-1.5">
      <div className="flex items-center gap-2">
        <AppLabel>Evidencia</AppLabel>
        <AppBadge variant="outline" label="Proximamente" />
      </div>

      <AppButton
        variant="outline"
        className="w-full h-20 border-dashed flex flex-col gap-1"
        disabled
        label=""
      >
        <span className="text-xl">📷</span>
        <AppText className="text-xs text-muted-foreground">
          Capturar / Subir foto
        </AppText>
      </AppButton>
    </div>
  );
}
