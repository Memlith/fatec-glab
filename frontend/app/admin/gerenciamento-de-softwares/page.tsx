import GerenciamentoSoftwares from "@/components/utils/pages/admin/GerenciamentoSoftwares";
import SuspensePage from "@/components/utils/pages/SuspensePage";
import { Metadata } from "next";
import { Suspense } from "react";

export const metadata: Metadata = {
  title: "Gerenciamento de Softwares",
};

export default function page() {
  return (
    <Suspense
      fallback={
        <SuspensePage text="Carregando página de Gerenciamento de Softwares" />
      }
    >
      <GerenciamentoSoftwares />
    </Suspense>
  );
}
