# 🧬 Arquitectura Frontend - OpsCore Incident System

Sistema de gestión de incidentes industriales desarrollado como proyecto **NoCountry 2026**.

Construido sobre **Atomic Design** con separación estricta de responsabilidades, pensado para escalar sin deuda técnica.

---

## 🛠 Stack principal

| Herramienta | Rol |
|---|---|
| React + Vite | Framework UI |
| TypeScript | Tipado estático |
| TailwindCSS + shadcn/ui | Estilos y componentes base |
| Zustand 5 | Estado global de UI |
| TanStack Query | Server state y caché |
| Axios | Cliente HTTP |
| React Hook Form + Yup | Formularios y validación |
| React Router v6 | Navegación |

---

## 📁 Estructura del proyecto

```
src/
├── app/
│   ├── config/          # env.ts, configuración global
│   └── router/          # Definición de rutas
│
├── components/
│   ├── atoms/           # Wrappers de shadcn/ui (AppButton, AppInput...)
│   ├── molecules/
│   │   ├── auth/        # OtpInput, TrustDeviceCheckbox, RoleSelector
│   │   ├── incidents/   # SeveritySelector, SafetyChecklist, TimelineItem...
│   │   └── supervisor/  # StatsBar, IncidentCard
│   └── organisms/
│       ├── auth/        # LoginForm, TwoFactorForm, LogoutDialog
│       ├── incidents/   # IncidentReportForm, IncidentWorkspace, IncidentTimeline...
│       ├── supervisor/  # IncidentList, AssignTechnicianForm, AssignTechnicianDialog
│       └── schemas/     # Schemas de Yup por organismo
│
├── pages/               # Composición final — lógica + UI
│   ├── LoginPage.tsx
│   ├── MobileIncidentReportPage.tsx
│   ├── TechnicianQueuePage.tsx
│   └── SupervisorDashboardPage.tsx
│
├── services/            # Llamadas HTTP puras (sin React)
│   ├── api.ts           # Instancia axios + interceptor refresh
│   ├── auth.service.ts
│   └── area.service.ts
│
├── hooks/               # TanStack Query encapsulado
│   ├── useAreas.ts
│   ├── useEstaciones.ts
│   └── useLogout.ts
│
├── store/               # Zustand — solo estado UI
│   └── authStore.ts
│
├── constants/           # Enums y catálogos estáticos del dominio
│
├── lib/                 # Utilidades puras
│   ├── getErrorMessage.ts
│   └── authRedirect.ts
│
└── types/               # Contratos TypeScript del dominio
```

---

## 🗺 Rutas

| Ruta | Page | Rol |
|---|---|---|
| `/auth` | LoginPage | Todos |
| `/check` | MobileIncidentReportPage | Operador |
| `/tec-queue` | TechnicianQueuePage | Técnico |
| `/supervisor` | SupervisorDashboardPage | Supervisor |
| `/ui` | UIPage | Design system |

La redirección por rol ocurre automáticamente al hacer login según el campo `role` que devuelve el backend.

---

## 🧱 Atomic Design

### Atoms
Wrappers mínimos sobre shadcn/ui. Siguen la convención `App<Nombre>`. No tienen lógica, solo extienden props y añaden lo específico del proyecto (ej: `errorMessage` en `AppInput`).

### Molecules
Combinaciones de atoms organizadas **por dominio** (`auth/`, `incidents/`, `supervisor/`). Tienen lógica de UI ligera pero son agnósticas al backend — reciben todo por props.

### Organisms
Bloques funcionales completos. Pueden contener formularios con validación (React Hook Form + Yup). Sus schemas viven en `organisms/schemas/`. Son agnósticos a la page — solo emiten callbacks.

### Pages
Único lugar con lógica de negocio. Consumen hooks, manejan estado, orquestan el flujo y pasan datos a los organismos. Los stubs de API están marcados con `// ─── WIP API` para reemplazar fácilmente.

---

## 🔐 Autenticación

- Login → `POST /api/auth/login` → devuelve `AuthUser` + cookies `httpOnly`
- 2FA → hardcodeado `999999` (endpoint pendiente)
- Refresh → interceptor automático en `api.ts` cuando recibe `401`
- Logout → `POST /api/auth/logout` + limpieza de store + redirect `/auth`
- Estado del usuario en `useAuthStore` (Zustand)

```
signIn() → setPendingUser() → setStep('2fa')
verify2fa() → setUser(store) → navigate(getRoleRedirect(role))
```

---

## 🌐 Manejo de errores HTTP

Centralizado en `lib/getErrorMessage.ts`. Mapea status codes a mensajes amigables en español. Si el backend devuelve `message` en el body, lo usa. Si no, cae al mapa de status codes.

```
403 → "Usuario o contraseña incorrectos"
401 → "No autorizado, inicia sesión nuevamente"
500 → "Error en el servidor, intenta más tarde"
```

---

## ⚡ Server State

TanStack Query para todos los GETs. Patrón estándar:

```ts
// service
export async function fetchAreas(): Promise<AreaDTO[]> {
  const { data } = await api.get('/api/areas');
  return data;
}

// hook
export function useAreas() {
  const { data = [], isLoading, isError } = useQuery({
    queryKey: ['areas'],
    queryFn: fetchAreas,
    retry: 1,
  });
  return { options: data.map(...), isLoading, isError };
}

// page
const { options, isLoading, isError } = useAreas();
```

Los selects tienen fallback estático cuando el backend no responde — la UI nunca explota.

---

## 🧠 Reglas de arquitectura

### ❌ Prohibido
- Llamar APIs dentro de components o atoms
- Zustand para server state
- Lógica de negocio fuera de pages
- `any` — usar `axios.isAxiosError()` y tipos del dominio

### ✅ Permitido
- UI pura en atoms/molecules/organisms
- Data fetching solo en hooks/pages
- Zustand solo para estado de UI (auth, modales)
- Services aislados sin React

---

## 🔄 Flujo de datos

```
Backend API
    ↓
services/ (Axios + interceptor)
    ↓
hooks/ (TanStack Query)
    ↓
Pages (lógica + composición)
    ↓
Organisms (bloques funcionales)
    ↓
Molecules (por dominio)
    ↓
Atoms (App* wrappers)
```

---

## 🚀 Levantar en local

```bash
# Instalar dependencias
npm install

# Variables de entorno
cp .env.example .env
# Editar VITE_API_URL si el back no corre en localhost:9090

# Levantar el backend (requiere Docker)
docker compose pull
docker compose up -d

# Correr el frontend
npm run dev
```

---

*Desarrollo forntEnd @fhdzleon* |
*OpsCore 2026 — NoCountry Project · Team 21*