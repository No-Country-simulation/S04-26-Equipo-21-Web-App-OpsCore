import type { Severity } from "@/components/molecules/types";
import { SAFETY_ITEMS, TECHNICIAN_CHECKLIST_ITEMS } from "@/constants";
import * as yup from "yup";

export const incidentReportSchema = yup.object({
  machine: yup.string().required("Selecciona una máquina"),
  area: yup.string().required("Selecciona un área"),
  incidentType: yup.string().required("Selecciona el tipo de incidente"),
  severity: yup
    .mixed<Severity>()
    .oneOf(["low", "medium", "critical"], "Selecciona la severidad")
    .required("Selecciona la severidad"),
  safetyChecklist: yup
    .object()
    .test("all-checked", "Completa todos los puntos del checklist", (value) =>
      SAFETY_ITEMS.every(
        (item) => !!(value as Record<string, boolean>)[item.id],
      ),
    )
    .required(),
  description: yup.string().trim().required("La descripción es requerida"),
});

export const loginFormSchema = yup.object({
  email: yup
    .string()
    .required("Email es requerido")
    .email("Ingresa un email válido"),
  password: yup.string().required("Password es requerido"),
});

export const incidentWorkspaceSchema = yup.object({
  checklist: yup
    .object()
    .test("all-checked", "Completa todos los puntos del checklist", (value) =>
      TECHNICIAN_CHECKLIST_ITEMS.every(
        (item) => !!(value as Record<string, boolean>)[item.id],
      ),
    )
    .required(),
  diagnosticNotes: yup
    .string()
    .trim()
    .required("Las notas de diagnóstico son requeridas"),
  rootCause: yup.string().required("Selecciona la causa raíz"),
});
