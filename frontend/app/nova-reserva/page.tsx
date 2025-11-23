import NovaReserva from "@/components/utils/pages/NovaReserva";
import SuspensePage from "@/components/utils/pages/SuspensePage";
import { Metadata } from "next";
import { Suspense } from "react";

export const metadata: Metadata = {
  title: "Nova Reserva",
};

export default function page() {
  return (
    <Suspense
      fallback={<SuspensePage text="Carregando formulário de reserva..." />}
    >
      <NovaReserva />
    </Suspense>
  );
}
