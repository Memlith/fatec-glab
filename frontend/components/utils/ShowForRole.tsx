"use client";

import { useAuth } from "@/context/AuthContext";
import React from "react";

export const ShowForRole = ({ 
  children, 
  role 
}: { 
  children: React.ReactNode; 
  role: "ADMIN" | "USER";
}) => {
  const { userProfile, loading } = useAuth();

  if (loading || userProfile?.role !== role) {
    return null;
  }

  return <>{children}</>;
};
