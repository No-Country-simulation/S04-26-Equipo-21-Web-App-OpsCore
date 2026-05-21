import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { LoginForm } from "@/components/organisms/auth/LoginForm";
import { TwoFactorForm } from "@/components/organisms/auth/TwoFactorForm";
import { useAuthStore } from "@/store/Authstore";
import { getRoleRedirect } from "@/lib/authRedirect";
import { getErrorMessage } from "@/lib/getErrorMessage";
import type { AuthUser } from "@/store/Authstore";
import type {
  LoginCredentials,
  TwoFactorPayload,
} from "@/components/organisms/types";
import type { Step } from "@/types";
import { signIn, verify2fa } from "@/services/auth.services";

export function LoginPage() {
  const navigate = useNavigate();
  const setUser = useAuthStore((s) => s.setUser);

  const [step, setStep] = useState<Step>("login");
  const [isLoading, setIsLoading] = useState(false);
  const [pageError, setPageError] = useState<string | undefined>();
  const [pendingUser, setPendingUser] = useState<AuthUser | null>(null);

  const handleLogin = async (credentials: LoginCredentials) => {
    setIsLoading(true);
    setPageError(undefined);
    try {
      const user = await signIn(credentials);
      setPendingUser(user);
      setStep("2fa");
    } catch (err) {
      setPageError(getErrorMessage(err, "Ingreso fallido. Intenta otra vez."));
    } finally {
      setIsLoading(false);
    }
  };

  const handle2fa = async (payload: TwoFactorPayload) => {
    setIsLoading(true);
    setPageError(undefined);
    try {
      await verify2fa(payload);

      if (pendingUser) {
        setUser(pendingUser);
        navigate(getRoleRedirect(pendingUser.role));
      }
    } catch (err) {
      setPageError(
        getErrorMessage(err, "Verificación fallida. Intenta otra vez."),
      );
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
