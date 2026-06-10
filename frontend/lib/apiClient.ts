import { API_URL } from "@/services/api";
import { auth } from "@/lib/firebase";

export const apiClient = async (url: string, options: RequestInit = {}) => {
  const user = auth.currentUser;
  const token = user ? await user.getIdToken() : "";
  
  const headers = {
    ...options.headers,
    "Authorization": token ? `Bearer ${token}` : "",
    "Content-Type": "application/json",
  };

  const response = await fetch(`${API_URL}${url}`, { ...options, headers });
  
  if (!response.ok) {
    throw new Error(`Erro ${response.status}: ${response.statusText}`);
  }
  
  // Se a resposta for vazia (ex: 204 No Content), retorna null
  const text = await response.text();
  return text ? JSON.parse(text) : null;
};
