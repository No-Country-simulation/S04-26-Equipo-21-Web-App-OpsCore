import { AppLabel, AppBadge, AppButton } from "@/components/atoms";
import { Camera, Video } from "lucide-react";
import { type ReactNode } from "react";

type EvidenceUploaderProps = {
  icon?: ReactNode;
};

export function EvidenceUploader({ icon }: EvidenceUploaderProps) {
  return (
    <div className="flex flex-col gap-1.5">
      <div className="flex items-center gap-2">
        <AppLabel>
          {icon}
          Evidencia
        </AppLabel>
        <AppBadge label="Próximamente" variant="destructive" />
      </div>

      <div className="flex gap-2">
        <AppButton
          variant="outline"
          className="flex-1 h-16 border-dashed flex flex-col gap-1 opacity-50 cursor-not-allowed"
          disabled
        >
          <Camera size={18} />
          <span className="text-xs">Subir Imagen</span>
        </AppButton>

        <AppButton
          variant="outline"
          className="flex-1 h-16 border-dashed flex flex-col gap-1 opacity-50 cursor-not-allowed"
          disabled
        >
          <Video size={18} />
          <span className="text-xs">Subir Video</span>
        </AppButton>
      </div>
    </div>
  );
}
