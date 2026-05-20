import { useQuery } from "@tanstack/react-query";
import {
  fetchAreas,
  fetchEstacionesByArea,
} from "@/services/incident.services";

export function useAreas() {
  const {
    data = [],
    isLoading,
    isError,
  } = useQuery({
    queryKey: ["areas"],
    queryFn: fetchAreas,
    retry: 1,
    staleTime: 1000 * 60,
  });

  const options = data.map((area) => ({
    value: String(area.id),
    label: area.nombre,
  }));

  return { options, isLoading, isError };
}

export function useEstaciones(idArea: string) {
  const {
    data = [],
    isLoading,
    isError,
  } = useQuery({
    queryKey: ["estaciones", idArea],
    queryFn: () => fetchEstacionesByArea(idArea),
    enabled: !!idArea,
  });

  const options = data.map((estacion) => ({
    value: String(estacion.id),
    label: `${estacion.nombre} — ${estacion.codigo}`,
  }));

  return { options, isLoading, isError };
}
