"use client";

import { forwardRef, useEffect, useRef } from "react";
import { cn } from "@/lib/utils";

interface TimeInput24hProps
  extends Omit<React.InputHTMLAttributes<HTMLInputElement>, "type"> {}

const TimeInput24h = forwardRef<HTMLInputElement, TimeInput24hProps>(
  ({ value, onChange, className, ...props }, ref) => {
    const inputRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
      if (typeof ref === "function") {
        ref(inputRef.current);
      } else if (ref) {
        ref.current = inputRef.current;
      }
    }, [ref]);

    const formatTimeValue = (val: string): string => {
      const digits = val.replace(/\D/g, "");

      if (digits.length === 0) return "";
      if (digits.length <= 2) {
        const hours = Math.min(parseInt(digits) || 0, 23);
        return hours.toString().padStart(digits.length, "0");
      }

      const hours = Math.min(parseInt(digits.slice(0, 2)) || 0, 23);
      const minutes = Math.min(parseInt(digits.slice(2, 4)) || 0, 59);

      return `${hours.toString().padStart(2, "0")}:${minutes
        .toString()
        .padStart(2, "0")}`;
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
      const cursorPosition = e.target.selectionStart || 0;
      const oldValue = (value as string) || "";
      const newValue = e.target.value;

      if (newValue.length < oldValue.length) {
        onChange?.(e);
        return;
      }

      const formatted = formatTimeValue(newValue);
      const syntheticEvent = {
        ...e,
        target: { ...e.target, value: formatted },
      } as React.ChangeEvent<HTMLInputElement>;

      onChange?.(syntheticEvent);

      setTimeout(() => {
        if (inputRef.current) {
          const newCursorPos =
            formatted.length >= 3 && cursorPosition === 2
              ? 3
              : Math.min(
                  cursorPosition + (formatted.length - oldValue.length),
                  formatted.length
                );
          inputRef.current.setSelectionRange(newCursorPos, newCursorPos);
        }
      }, 0);
    };

    const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
      const input = e.currentTarget;

      if (
        [46, 8, 9, 27, 13, 37, 39].indexOf(e.keyCode) !== -1 ||
        (e.keyCode === 65 && (e.ctrlKey || e.metaKey)) ||
        (e.keyCode === 67 && (e.ctrlKey || e.metaKey)) ||
        (e.keyCode === 86 && (e.ctrlKey || e.metaKey)) ||
        (e.keyCode === 88 && (e.ctrlKey || e.metaKey))
      ) {
        return;
      }

      if (
        (e.shiftKey || e.keyCode < 48 || e.keyCode > 57) &&
        (e.keyCode < 96 || e.keyCode > 105)
      ) {
        e.preventDefault();
      }

      const currentValue = (value as string) || "";
      if (
        currentValue.replace(/:/g, "").length >= 4 &&
        e.keyCode >= 48 &&
        e.keyCode <= 105
      ) {
        e.preventDefault();
      }
    };

    return (
      <input
        ref={inputRef}
        type="text"
        inputMode="numeric"
        placeholder="HH:MM"
        maxLength={5}
        value={value}
        onChange={handleChange}
        onKeyDown={handleKeyDown}
        className={cn(
          "flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-base ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium file:text-foreground placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50 md:text-sm",
          className
        )}
        {...props}
      />
    );
  }
);

TimeInput24h.displayName = "TimeInput24h";

export { TimeInput24h };
