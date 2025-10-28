"use client";

import { useEffect, useState } from "react";
import { useIsMobile } from "@/hooks/use-mobile";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Monitor } from "lucide-react";

export default function AlertMobile() {
  const isMobile = useIsMobile();
  const [open, setOpen] = useState(false);

  useEffect(() => {
    if (isMobile) {
      setOpen(true);
    }
  }, [isMobile]);

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogContent className="sm:max-w-md">
        <DialogHeader>
          <div className="mx-auto mb-4 flex h-12 w-12 items-center justify-center rounded-full bg-muted">
            <Monitor className="h-6 w-6 text-muted-foreground" />
          </div>
          <DialogTitle className="text-center">
            Melhor Experiência no Computador
          </DialogTitle>
          <DialogDescription className="text-center">
            Esta página não oferece suporte para dispositivos móveis. Para uma
            melhor experiência, por favor acesse através de um computador.
          </DialogDescription>
        </DialogHeader>
      </DialogContent>
    </Dialog>
  );
}
