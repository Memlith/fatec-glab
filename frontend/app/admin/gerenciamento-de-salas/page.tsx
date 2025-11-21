import GerenciamentoSalas from "@/components/utils/pages/admin/GerenciamentoSalas";
import { Metadata } from "next";

export const metadata: Metadata = {
  title: "Gerenciamento de Salas",
};

export default function page() {
  return <GerenciamentoSalas />;
}
