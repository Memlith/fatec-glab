"use client";

import { useAuth } from "@/context/AuthContext";
import { useRouter, usePathname } from "next/navigation";
import { useEffect } from "react";

export const ProtectedRoute = ({
  children,
  adminOnly = false,
}: {
  children: React.ReactNode;
  adminOnly?: boolean;
}) => {
  const { user, userProfile, loading } = useAuth();
  const router = useRouter();
  const pathname = usePathname();

  useEffect(() => {
    if (loading) return;

    if (!user) {
      if (pathname !== "/auth") router.push("/auth");
    } else if (adminOnly && userProfile?.role !== "ADMIN") {
      // Se for apenas admin e não for admin, redireciona para reservas
      if (pathname !== "/reservas") router.push("/reservas");
    }
  }, [user, userProfile, loading, adminOnly, router, pathname]);

  if (loading)
    return (
      <div className="flex items-center justify-center min-h-screen">
        Carregando...
      </div>
    );
  
  // Se não estiver logado e não for página de login, não renderiza nada (redirecionamento cuida disso)
  if (!user && pathname !== "/auth") return null;
  
  // Se for adminOnly e não for admin, não renderiza
  if (user && adminOnly && userProfile?.role !== "ADMIN") return null;

  return <>{children}</>;
};
