"use client";

import { createContext, useContext, useEffect, useState, ReactNode } from "react";
import { onAuthStateChanged, User } from "firebase/auth";
import { auth } from "@/lib/firebase";
import { API_URL } from "@/services/api";

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
    const unsubscribe = onAuthStateChanged(auth, async (firebaseUser) => {
      console.log("AuthProvider: Auth state changed:", firebaseUser?.email);
      if (firebaseUser) {
        setUser(firebaseUser);
        // Busca o perfil do usuário no backend
        const token = await firebaseUser.getIdToken();
        try {
          const response = await fetch(`${API_URL}/users/me`, {
            headers: { Authorization: `Bearer ${token}` },
          });
            if (response.ok) {
            const profile = await response.json();
            console.log("AuthProvider: User profile loaded:", profile);
            console.log("AuthProvider: User role detected:", profile.role); // Log adicionado
            setUserProfile(profile);
          } else {
             console.error("AuthProvider: Failed to load profile. Status:", response.status);
          }
        } catch (error) {
          console.error("AuthProvider: Error fetching profile", error);
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
