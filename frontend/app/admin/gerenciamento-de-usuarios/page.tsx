import GerenciamentoUsuarios from "@/components/utils/pages/admin/GerenciamentoUsuarios";
import { Metadata } from "next";

export const metadata: Metadata = {
  title: "Gerenciamento de Usuários",
};

export default function page() {
  return <GerenciamentoUsuarios />;
}
