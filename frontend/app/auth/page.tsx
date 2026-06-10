"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { signInWithEmailAndPassword, signInWithPopup, type User } from "firebase/auth";
import { auth, googleProvider } from "@/lib/firebase";
import { API_URL } from "@/services/api";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";

export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const router = useRouter();

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await signInWithEmailAndPassword(auth!, email, password);
      router.push("/");
    } catch (error) {
      console.error("Login error:", error);
    }
  };

  const syncGoogleUserWithBackend = async (firebaseUser: User | null) => {
    if (!API_URL) {
      console.error("Backend URL is not configured. Set NEXT_PUBLIC_BACKEND_URL.");
      return;
    }

    if (!firebaseUser) return;

    try {
      const token = await firebaseUser.getIdToken();
      await fetch(`${API_URL}/users/me`, {
        headers: { Authorization: `Bearer ${token}` },
      });
    } catch (error) {
      console.error("Failed to sync Google user with backend:", error);
    }
  };

  const handleGoogleLogin = async () => {
    try {
      const result = await signInWithPopup(auth!, googleProvider);
      await syncGoogleUserWithBackend(result.user);
      router.push("/");
    } catch (error) {
      console.error("Google login error:", error);
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen">
      <Card className="w-[350px]">
        <CardHeader>
          <CardTitle>Login</CardTitle>
          <CardDescription>Acesse sua conta</CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleLogin} className="space-y-4">
            <div className="space-y-2">
              <Label htmlFor="email">Email</Label>
              <Input id="email" type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
            </div>
            <div className="space-y-2">
              <Label htmlFor="password">Senha</Label>
              <Input id="password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
            </div>
            <Button type="submit" className="w-full">Entrar</Button>
          </form>
          <div className="my-4 text-center text-sm text-muted-foreground">ou</div>
          <Button variant="outline" className="w-full" onClick={handleGoogleLogin}>Entrar com Google</Button>
          <Button variant="secondary" className="w-full mt-2" onClick={() => router.push("/auth/register")}>Criar conta</Button>
        </CardContent>
      </Card>
    </div>
  );
}
