"use client";

import { createContext, useContext, useEffect, useState, ReactNode } from "react";
import { onAuthStateChanged, User } from "firebase/auth";
import { auth } from "@/lib/firebase";
import { apiClient } from "@/lib/apiClient";

interface UserProfile {
  name: string;
  email: string;
  role: "ADMIN" | "USER";
}

interface AuthContextType {
  user: User | null;
  userProfile: UserProfile | null;
  loading: boolean;
}

const AuthContext = createContext<AuthContextType>({ user: null, userProfile: null, loading: true });

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);
  const [userProfile, setUserProfile] = useState<UserProfile | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    console.log("AuthProvider: Initializing Auth listener...");
    const unsubscribe = onAuthStateChanged(auth!, async (firebaseUser) => {
      console.log("AuthProvider: Auth state changed:", firebaseUser?.email);
      if (firebaseUser) {
        setUser(firebaseUser);
        // Busca o perfil do usuário no backend e cria no MongoDB se ainda não existir
        try {
          const profile = await apiClient("/users/me");
          console.log("AuthProvider: User profile loaded:", profile);
          console.log("AuthProvider: User role detected:", profile.role);
          setUserProfile(profile as UserProfile);
        } catch (error) {
          console.error("AuthProvider: Error fetching profile", error);
          setUserProfile(null);
        }
      } else {
        setUser(null);
        setUserProfile(null);
      }
      setLoading(false);
    });
    return unsubscribe;
  }, []);

  return (
    <AuthContext.Provider value={{ user, userProfile, loading }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
