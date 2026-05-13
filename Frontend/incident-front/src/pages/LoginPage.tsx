import { useState } from "react";
import { LoginForm } from "@/components/organisms/auth/LoginForm";
import { TwoFactorForm } from "@/components/organisms/auth/TwoFactorForm";
import { useNavigate } from "react-router-dom";
import type {
  LoginCredentials,
  TwoFactorPayload,
} from "@/components/organisms/types";
import type { Step } from "@/types";

// ─── WIP - API HELPERS ────────────────────────────
async function signIn(credentials: LoginCredentials): Promise<void> {
  await new Promise((r) => setTimeout(r, 800));

  const isValidUser =
    credentials.email === "admin@admin.com" && credentials.password === "admin";

  if (!isValidUser) {
    throw new Error("Credenciales inválidas");
  }
}

async function verify2fa(payload: TwoFactorPayload): Promise<void> {
  await new Promise((r) => setTimeout(r, 800));

  if (payload.code !== "999999") {
    throw new Error("Código MFA inválido");
  }
}

// ───────────────────────────────────────────────────────────────────────
export function LoginPage() {
  const navigate = useNavigate();
  const [step, setStep] = useState<Step>("login");
  const [isLoading, setIsLoading] = useState(false);
  const [pageError, setPageError] = useState<string | undefined>();

  const handleLogin = async (credentials: LoginCredentials) => {
    setIsLoading(true);
    setPageError(undefined);

    try {
      await signIn(credentials);
      setStep("2fa");
    } catch (err) {
      setPageError(
        err instanceof Error
          ? err.message
          : "Ingreso fallido. intenta otra vez.",
      );
    } finally {
      setIsLoading(false);
    }
  };

  const handle2fa = async (payload: TwoFactorPayload) => {
    setIsLoading(true);
    setPageError(undefined);

    try {
      await verify2fa(payload);
      navigate("/check");
    } catch (err) {
      setPageError(
        err instanceof Error
          ? err.message
          : "Verificación fallida. intenta otra vez.",
      );
      setIsLoading(false);
    }
  };

  const handleBack = () => {
    setStep("login");
    setPageError(undefined);
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-background px-4">
      <div className="w-full max-w-sm">
        <div className="mb-8 text-center">
          <h1 className="text-2xl font-semibold tracking-tight">OpsCore</h1>
          <p className="text-sm text-muted-foreground mt-1">
            {step === "login"
              ? "Inicia sesión en tu cuenta"
              : "Autenticación de dos factores"}
          </p>
        </div>

        <div className="rounded-xl border bg-card p-6 shadow-sm">
          {pageError && (
            <div
              role="alert"
              className="mb-4 rounded-lg bg-destructive/10 border border-destructive/20 px-4 py-2.5 text-sm text-destructive"
            >
              {pageError}
            </div>
          )}

          {step === "login" && (
            <LoginForm onSubmit={handleLogin} isLoading={isLoading} />
          )}

          {step === "2fa" && (
            <TwoFactorForm
              onSubmit={handle2fa}
              onBack={handleBack}
              isLoading={isLoading}
            />
          )}
        </div>

        <p className="text-center text-xs text-muted-foreground mt-6">
          OpsCore · Incident Operations Platform
        </p>
        <p className="text-center text-xs text-muted-foreground mt-6">
          Team 21 - NoCountry
        </p>
      </div>
    </div>
  );
}
