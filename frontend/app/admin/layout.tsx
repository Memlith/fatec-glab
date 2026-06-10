"use client";

import { SidebarProvider } from "@/components/ui/sidebar";
import { AppSidebar } from "@/components/utils/admin/AppSidebar";
import { ProtectedRoute } from "@/components/ProtectedRoute";

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <ProtectedRoute adminOnly={true}>
      <SidebarProvider>
        <AppSidebar />
        {children}
      </SidebarProvider>
    </ProtectedRoute>
  );
}
