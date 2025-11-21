import { SidebarProvider } from "@/components/ui/sidebar";
import { AppSidebar } from "@/components/utils/admin/AppSidebar";
import type { Metadata } from "next";

export const metadata: Metadata = {
  title: {
    default: "Admin | GLab",
    template: "%s | Admin | GLab",
  },
  description: "Página para visualização de reservas na FATEC Indaiatuba",
  icons: {
    icon: "/icon.png",
  },
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <SidebarProvider>
      <AppSidebar />
      {children}
    </SidebarProvider>
  );
}
