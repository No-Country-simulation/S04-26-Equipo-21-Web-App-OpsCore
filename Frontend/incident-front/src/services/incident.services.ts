import { api } from "@/services/api";

export type AreaDTO = {
  id: number;
  nombre: string;
};

export type EstacionTrabajoDTO = {
  id: number;
  nombre: string;
  codigo: string;
};

export async function fetchAreas(): Promise<AreaDTO[]> {
  const { data } = await api.get<AreaDTO[]>("/api/areas");
  return data;
}

export async function fetchEstacionesByArea(
  idArea: string,
): Promise<EstacionTrabajoDTO[]> {
  const { data } = await api.get<EstacionTrabajoDTO[]>(
    `/api/areas/${idArea}/estaciontrabajo`,
  );
  return data;
}
