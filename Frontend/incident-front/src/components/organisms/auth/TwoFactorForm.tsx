import { useState } from "react";
import { AppButton } from "@/components/atoms";
import { OtpInput } from "@/components/molecules/auth/OtpInput";
import { TrustDeviceCheckbox } from "@/components/molecules/auth/TrustDeviceCheckbox";
import type { TwoFactorFormProps } from "../types";

export function TwoFactorForm({
  onSubmit,
  onBack,
  isLoading,
}: TwoFactorFormProps) {
  const [code, setCode] = useState("");
  const [trustDevice, setTrustDevice] = useState(false);
  const [error, setError] = useState<string | undefined>();

  const handleSubmit = async () => {
    if (code.length < 6) {
      setError("Ingresa el código de 6 dígitos");
      return;
    }
    setError(undefined);
    await onSubmit({ code, trustDevice });
  };

  return (
    <div className="flex flex-col gap-5">
      <OtpInput
        value={code}
        onChange={(v) => {
          setCode(v);
          if (error) setError(undefined);
        }}
        errorMessage={error}
        disabled={isLoading}
      />

      <TrustDeviceCheckbox
        checked={trustDevice}
        onCheckedChange={setTrustDevice}
        disabled={isLoading}
      />

      <AppButton
        className="w-full mt-2"
        onClick={handleSubmit}
        disabled={isLoading}
        label={isLoading ? "Verificando..." : "Verificar"}
      />

      {onBack && (
        <button
          type="button"
          onClick={onBack}
          disabled={isLoading}
          className="text-sm text-muted-foreground hover:text-foreground transition-colors text-center"
        >
          ← Volver a iniciar sesión
        </button>
      )}
    </div>
  );
}
