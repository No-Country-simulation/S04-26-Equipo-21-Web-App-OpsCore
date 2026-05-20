import { useNavigate } from "react-router-dom";
import { useAuthStore } from "@/store/Authstore";
import { logout } from "@/services/auth.services";

export function useLogout() {
  const navigate = useNavigate();
  const clearUser = useAuthStore((s) => s.clearUser);

  const handleLogout = async () => {
    try {
      await logout();
    } finally {
      clearUser();
      navigate("/auth");
    }
  };

  return handleLogout;
}
