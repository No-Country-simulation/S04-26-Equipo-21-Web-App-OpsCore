import { useState } from "react";
import { useLocation } from "react-router-dom";
import { Bell, LogOut } from "lucide-react";
import { LogoutDialog } from "./auth/LogoutDialog";
import { AppIconButton, AppText } from "@/components/atoms";
import { useLogout } from "@/hooks/useLogout";
import { useAuthStore } from "@/store/Authstore";
import type { BasePageContainerProps, RouteTheme } from "./types";
import { ROUTE_THEMES, DEFAULT_THEME } from "@/constants";

function useRouteTheme(): RouteTheme {
  const { pathname } = useLocation();
  return ROUTE_THEMES[pathname] ?? DEFAULT_THEME;
}

export function BasePageContainer({ children, title }: BasePageContainerProps) {
  const handleLogout = useLogout();
  const [showDialog, setShowDialog] = useState(false);
  const theme = useRouteTheme();
  const user = useAuthStore((s) => s.user);
  const displayName = user?.nombre ?? "John Doe";

  return (
    <div className="min-h-screen bg-background flex flex-col">
      <div className="sticky top-0 z-10">
        <div className={`h-1 w-full bg-linear-to-r ${theme.gradient}`} />

        <header className="flex items-center justify-between px-4 py-3 bg-background border-b border-border">
          <div className="flex flex-col">
            <span className="text-base font-semibold tracking-tight">
              OpsCore
            </span>
            <span className={`text-xs font-medium ${theme.accent}`}>
              {displayName}
            </span>
          </div>

          <div className="flex items-center gap-2">
            <AppIconButton
              icon={<Bell className="w-4 h-4" />}
              aria-label="Notificaciones"
              title="Notificaciones"
              variant="outline"
              onClick={() => {
                // TODO: abrir panel de notificaciones
              }}
            />
            <AppIconButton
              icon={<LogOut className="w-4 h-4" />}
              aria-label="Cerrar sesión"
              title="Cerrar sesión"
              variant="destructive"
              onClick={() => setShowDialog(true)}
            />
          </div>
        </header>

        {title && (
          <div className="px-4 py-3 bg-background border-b border-border">
            <h1 className="text-lg font-semibold">{title}</h1>
          </div>
        )}
      </div>

      {/* Contenido */}
      <main className="flex-1">{children}</main>

      {/* Footer */}
      <footer className="border-t border-border px-4 py-3 flex flex-col items-center gap-0.5">
        <div
          className={`h-0.5 w-12 rounded-full bg-linear-to-r ${theme.gradient} mb-2`}
        />
        <AppText className="text-xs text-muted-foreground font-medium">
          OpsCore 2026
        </AppText>
        <AppText className="text-xs text-muted-foreground">
          @NoCountry Project
        </AppText>
      </footer>

      <LogoutDialog
        open={showDialog}
        onConfirm={() => {
          setShowDialog(false);
          handleLogout();
        }}
        onCancel={() => setShowDialog(false)}
      />
    </div>
  );
}
