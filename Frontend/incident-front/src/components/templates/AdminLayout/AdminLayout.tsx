import { type ReactNode, useState } from "react";
import { AppText } from "@/components/atoms/Text/AppText";
import { AppDivider } from "@/components/atoms/Divider/AppDivider";
import { AppAvatarPlaceholder } from "@/components/atoms/AvatarPlaceholder/AppAvatarPlaceholder";
import { AppIconButton } from "@/components/atoms/IconButton/AppIconButton";

interface AdminLayoutProps {
  children: ReactNode;
}

const navItems = [
  { label: "Dashboard", icon: "📊", active: true },
  { label: "Usuarios", icon: "👥", active: false },
  { label: "Configuración", icon: "⚙️", active: false },
];

export function AdminLayout({ children }: AdminLayoutProps) {
  const [sidebarOpen, setSidebarOpen] = useState(false);

  return (
    <div className="flex h-screen bg-background overflow-hidden">

      {/* Overlay oscuro en mobile cuando sidebar está abierto */}
      {sidebarOpen && (
        <div
          className="fixed inset-0 bg-black/50 z-20 lg:hidden"
          onClick={() => setSidebarOpen(false)}
        />
      )}

      {/* Sidebar */}
      <aside
        className={`
          fixed top-0 left-0 h-full w-64 border-r flex flex-col p-4 gap-4 bg-background z-30
          transform transition-transform duration-300 ease-in-out
          ${sidebarOpen ? "translate-x-0" : "-translate-x-full"}
          lg:static lg:translate-x-0 lg:z-auto
        `}
      >
        {/* Logo + botón cerrar en mobile */}
        <div className="flex items-center justify-between px-2 py-3">
          <div className="flex items-center gap-2">
            <span className="text-xl">🛠️</span>
            <AppText variant="h3">Admin Panel</AppText>
          </div>
          <div className="lg:hidden">
            <AppIconButton
              aria-label="Cerrar menú"
              icon={<span>✕</span>}
              onClick={() => setSidebarOpen(false)}
            />
          </div>
        </div>

        <AppDivider />

        {/* Nav */}
        <nav className="flex flex-col gap-1">
          {navItems.map((item) => (
            <div
              key={item.label}
              onClick={() => setSidebarOpen(false)}
              className={`flex items-center gap-3 px-3 py-2 rounded-md cursor-pointer transition-colors
                ${item.active
                  ? "bg-primary text-primary-foreground"
                  : "hover:bg-muted text-muted-foreground hover:text-foreground"
                }`}
            >
              <span>{item.icon}</span>
              <AppText variant="caption">{item.label}</AppText>
            </div>
          ))}
        </nav>

        <div className="mt-auto">
          <AppDivider />
          <div className="flex items-center gap-3 px-2 pt-4">
            <AppAvatarPlaceholder fallback="Admin User" size="sm" />
            <div className="flex flex-col">
              <AppText variant="caption">Admin User</AppText>
              <AppText variant="muted">admin@app.com</AppText>
            </div>
          </div>
        </div>
      </aside>

      {/* Contenido principal */}
      <div className="flex flex-col flex-1 min-w-0">

        {/* Topbar — solo visible en mobile */}
        <header className="flex items-center gap-4 px-4 py-3 border-b lg:hidden">
          <AppIconButton
            aria-label="Abrir menú"
            icon={<span className="text-lg">☰</span>}
            onClick={() => setSidebarOpen(true)}
          />
          <div className="flex items-center gap-2">
            <span>🛠️</span>
            <AppText variant="h3">Admin Panel</AppText>
          </div>
        </header>

        <main className="flex-1 overflow-y-auto p-4 lg:p-8">
          {children}
        </main>

      </div>
    </div>
  );
}