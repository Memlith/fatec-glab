import GerenciamentoEquipamentos from "@/components/utils/pages/admin/GerenciamentoEquipamentos";
import SuspensePage from "@/components/utils/pages/SuspensePage";
import { Metadata } from "next";
import { Suspense } from "react";

export const metadata: Metadata = {
  title: "Gerenciamento de Equipamentos",
};

export default function page() {
  return (
    <Suspense
      fallback={
        <SuspensePage text="Carregando página de Gerenciamento de Equipamentos" />
      }
    >
      <GerenciamentoEquipamentos />;
    </Suspense>
  );
}
