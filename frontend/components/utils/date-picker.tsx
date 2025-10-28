"use client";

import * as React from "react";
import { ChevronDownIcon } from "lucide-react";

import { Button } from "@/components/ui/button";
import { Calendar } from "@/components/ui/calendar";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";

export function DatePicker() {
  const [open, setOpen] = React.useState(false);
  const [date, setDate] = React.useState<Date | undefined>(undefined);

  return (
    <div className="flex gap-4">
      {/* <div className="flex flex-col gap-3">
        <Label htmlFor="date-picker" className="px-1">
          Date
        </Label>
        <Popover open={open} onOpenChange={setOpen}>
          <PopoverTrigger asChild>
            <Button
              variant="outline"
              id="date-picker"
              className="w-32 justify-between font-normal"
            >
              {date ? date.toLocaleDateString() : "Select date"}
              <ChevronDownIcon />
            </Button>
          </PopoverTrigger>
          <PopoverContent className="w-auto overflow-hidden p-0" align="start">
            <Calendar
              mode="single"
              selected={date}
              captionLayout="dropdown"
              onSelect={(date) => {
                setDate(date);
                setOpen(false);
              }}
            />
          </PopoverContent>
        </Popover>
      </div> */}

      <div className="flex flex-col gap-2">
        <div className="flex items-center gap-2">
          <Input
            type="time"
            id="time-picker"
            defaultValue="10:30"
            className="w-fit bg-background appearance-none [&::-webkit-calendar-picker-indicator]:hidden [&::-webkit-calendar-picker-indicator]:appearance-none"
          />
          <Label htmlFor="time-picker" className="px-1">
            Horário Início
          </Label>
        </div>
        <div className="flex items-center gap-2">
          <Input
            type="time"
            id="time-picker"
            defaultValue="10:30"
            className="w-fit bg-background appearance-none [&::-webkit-calendar-picker-indicator]:hidden [&::-webkit-calendar-picker-indicator]:appearance-none"
          />
          <Label htmlFor="time-picker" className="px-1">
            Horário Término
          </Label>
        </div>
      </div>
    </div>
  );
}
