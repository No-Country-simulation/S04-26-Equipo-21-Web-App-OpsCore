import type { UserRole } from "@/store/Authstore";

export function getRoleRedirect(role: UserRole): string {
  switch (role) {
    case "TECNICO":
      return "/tec-queue";
    case "OPERADOR":
      return "/check";
    case "GERENTE":
      return "/ui";
    default:
      return "/";
  }
}
