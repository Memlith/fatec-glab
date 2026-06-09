import NovaReserva from "@/components/utils/pages/NovaReserva";
import SuspensePage from "@/components/utils/pages/SuspensePage";
import { Metadata } from "next";
import { Suspense } from "react";
import { ProtectedRoute } from "@/components/ProtectedRoute";

export const metadata: Metadata = {
  title: "Nova Reserva",
};

export default function page() {
  return (
    <ProtectedRoute adminOnly={true}>
      <Suspense
        fallback={<SuspensePage text="Carregando formulário de reserva..." />}
      >
        <NovaReserva />
      </Suspense>
    </ProtectedRoute>
  );
}
