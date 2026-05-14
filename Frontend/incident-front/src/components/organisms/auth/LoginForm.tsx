import { useForm, Controller } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import { AppButton } from "@/components/atoms";
import { FormField } from "@/components/molecules/FormField";
import type { LoginFormProps, LoginMode } from "../types";
import { loginFormSchema } from "../schemas";

type FormValues = yup.InferType<typeof loginFormSchema>;

export function LoginForm({ onSubmit, isLoading }: LoginFormProps) {
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<FormValues>({
    resolver: yupResolver(loginFormSchema),
    defaultValues: { email: "", password: "" },
    mode: "onChange",
  });

  const submit = (mode: LoginMode) =>
    handleSubmit((values) => onSubmit({ ...values, mode }))();

  return (
    <div className="flex flex-col gap-5">
      <Controller
        control={control}
        name="email"
        render={({ field }) => (
          <FormField
            label="Email"
            type="email"
            autoComplete="email"
            placeholder="you@company.com"
            value={field.value}
            onChange={field.onChange}
            errorMessage={errors.email?.message}
            disabled={isLoading}
          />
        )}
      />

      <Controller
        control={control}
        name="password"
        render={({ field }) => (
          <FormField
            label="Password"
            type="password"
            autoComplete="current-password"
            placeholder="••••••••"
            value={field.value}
            onChange={field.onChange}
            errorMessage={errors.password?.message}
            disabled={isLoading}
          />
        )}
      />

      <div className="flex flex-col gap-3 pt-2">
        <AppButton
          className="w-full"
          onClick={() => submit("default")}
          disabled={isLoading}
          label={isLoading ? "Ingresando..." : "Ingresar"}
        />
      </div>
    </div>
  );
}
