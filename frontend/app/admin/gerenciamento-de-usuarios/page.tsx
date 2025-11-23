import GerenciamentoUsuarios from "@/components/utils/pages/admin/GerenciamentoUsuarios";
import SuspensePage from "@/components/utils/pages/SuspensePage";
import { Metadata } from "next";
import { Suspense } from "react";

export const metadata: Metadata = {
  title: "Gerenciamento de Usuários",
};

export default function page() {
  return (
    <Suspense
      fallback={
        <SuspensePage text="Carregando página de Gerenciamento de Usuários" />
      }
    >
      <GerenciamentoUsuarios />
    </Suspense>
  );
}
