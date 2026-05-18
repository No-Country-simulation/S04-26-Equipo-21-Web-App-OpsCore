import { api } from "@/services/api";
import type { AuthUser } from "@/store/Authstore";
import type {
  LoginCredentials,
  TwoFactorPayload,
} from "@/components/organisms/types";

export async function signIn(credentials: LoginCredentials): Promise<AuthUser> {
  try {
    const { data } = await api.post<AuthUser>("/api/auth/login", {
      username: credentials.username,
      password: credentials.password,
    });
    return data;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
export async function verify2fa(payload: TwoFactorPayload): Promise<void> {
  await new Promise((r) => setTimeout(r, 800));
  if (payload.code !== "999999") throw new Error("Código MFA inválido");
}
