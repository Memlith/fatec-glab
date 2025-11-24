import GerenciamentoProfessores from "@/components/utils/pages/admin/GerenciamentoProfessores";
import SuspensePage from "@/components/utils/pages/SuspensePage";
import { Metadata } from "next";
import { Suspense } from "react";

export const metadata: Metadata = {
  title: "Gerenciamento de Professores",
};

export default function page() {
  return (
    <Suspense
      fallback={
        <SuspensePage text="Carregando página de Gerenciamento de Professores" />
      }
    >
      <GerenciamentoProfessores />
    </Suspense>
  );
}
