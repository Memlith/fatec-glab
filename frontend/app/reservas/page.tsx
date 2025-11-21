import Reservas from "@/components/utils/pages/Reservas";
import { Metadata } from "next";

export const metadata: Metadata = {
  title: "Reservas",
};

export default function page() {
  return <Reservas />;
}
