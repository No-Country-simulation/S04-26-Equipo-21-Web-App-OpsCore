import type { ReactNode } from "react";
import { useState } from "react";
import { Bell, LogOut } from "lucide-react";

import { LogoutDialog } from "./auth/LogoutDialog";
import { AppIconButton } from "@/components/atoms";
import { useLogout } from "@/hooks/useLogout";

type BasePageContainerProps = {
  children: ReactNode;
};

export function BasePageContainer({ children }: BasePageContainerProps) {
  const handleLogout = useLogout();
  const [showDialog, setShowDialog] = useState(false);

  return (
    <div className="min-h-screen bg-background">
      <div className="fixed top-4 right-4 z-50 flex items-center gap-2">
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

      {children}

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
