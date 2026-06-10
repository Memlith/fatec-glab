"use client";

import { useAuth } from "@/context/AuthContext";
import { API_URL } from "@/services/api";

export const useAuthApi = () => {
  const { user } = useAuth();

  const fetchWithAuth = async (url: string, options: RequestInit = {}) => {
    const token = await user?.getIdToken();
    
    const headers = {
      ...options.headers,
      "Authorization": token ? `Bearer ${token}` : "",
      "Content-Type": "application/json",
    };

    return fetch(`${API_URL}${url}`, { ...options, headers });
  };

  return { fetchWithAuth };
};
