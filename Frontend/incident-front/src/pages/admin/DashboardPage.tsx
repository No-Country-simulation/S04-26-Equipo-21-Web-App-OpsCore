import { AdminLayout } from "@/components/templates/AdminLayout/AdminLayout";
import { AppText } from "@/components/atoms/Text/AppText";
import { AppBadge } from "@/components/atoms/Badge/AppBadge";
import { AppDot } from "@/components/atoms/Dot/AppDot";
import { AppDivider } from "@/components/atoms/Divider/AppDivider";
import { AppButton } from "@/components/atoms/AppButton/AppButton";

const metrics = [
    { label: "Usuarios totales", value: "1,240", change: "+12%", status: "green" as const },
    { label: "Sesiones activas", value: "340", change: "+5%", status: "green" as const },
    { label: "Errores hoy", value: "8", change: "-3%", status: "red" as const },
    { label: "Tiempo de respuesta", value: "120ms", change: "estable", status: "yellow" as const },
];

const recentActivity = [
    { user: "María García", action: "Inició sesión", time: "Hace 2 min", status: "green" as const },
    { user: "Carlos López", action: "Actualizó perfil", time: "Hace 10 min", status: "yellow" as const },
    { user: "Juan Pérez", action: "Error de autenticación", time: "Hace 15 min", status: "red" as const },
    { user: "Ana Torres", action: "Cerró sesión", time: "Hace 30 min", status: "gray" as const },
];

export function DashboardPage() {
    return (
        <AdminLayout>
            {/* Header */}
            <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-8">
                <div>
                    <AppText variant="h1">Dashboard</AppText>
                    <AppText variant="muted">Bienvenido de nuevo, Admin</AppText>
                </div>
                <AppButton label="Exportar reporte" variant="outline" />
            </div>

            {/* Tarjetas de métricas */}
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 mb-8">
                {metrics.map((metric) => (
                    <div
                        key={metric.label}
                        className="border rounded-xl p-5 flex flex-col gap-3 bg-card"
                    >
                        <div className="flex items-center justify-between">
                            <AppText variant="muted">{metric.label}</AppText>
                            <AppDot color={metric.status} />
                        </div>
                        <AppText variant="h2">{metric.value}</AppText>
                        <AppBadge
                            label={metric.change}
                            variant={metric.status === "red" ? "destructive" : "secondary"}
                        />
                    </div>
                ))}
            </div>

            <AppDivider />

            {/* Actividad reciente */}
            <div className="mt-8">
                <div className="flex items-center justify-between mb-4">
                    <AppText variant="h3">Actividad reciente</AppText>
                    <AppButton label="Ver todo" variant="ghost" />
                </div>

                <div className="flex flex-col gap-3">
                    {recentActivity.map((item, index) => (
                        <div
                            key={index}
                            className="flex items-center justify-between border rounded-lg px-4 py-3 bg-card"
                        >
                            <div className="flex items-center gap-3">
                                <AppDot color={item.status} />
                                <div>
                                    <AppText variant="caption">{item.user}</AppText>
                                    <AppText variant="muted">{item.action}</AppText>
                                </div>
                            </div>
                            <AppText variant="muted">{item.time}</AppText>
                        </div>
                    ))}
                </div>
            </div>
        </AdminLayout>
    );
}