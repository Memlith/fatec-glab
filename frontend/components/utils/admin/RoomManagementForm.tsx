"use client";

import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Switch } from "@/components/ui/switch";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import z from "zod";

const formSchema = z.object({
  desktops: z.string().optional(),
  laptops: z.string().optional(),
  projector: z.boolean(),
  tv: z.boolean(),
});

export type FormSchema = z.infer<typeof formSchema>;

interface RoomManagementFormProps {
  roomName: string;
}

export default function RoomManagementForm({
  roomName,
}: RoomManagementFormProps) {
  const form = useForm<FormSchema>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      desktops: "",
      laptops: "",
      projector: false,
      tv: false,
    },
  });

  function onSubmit(values: FormSchema) {
    console.log("Form submitted with values:", values);
  }

  return (
    <div className="h-full col-span-2 bg-card rounded-xl border p-4 shadow-sm flex flex-col gap-4">
      <h1 className="text-2xl font-bold">{roomName}</h1>

      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
          <div className="grid grid-cols-2 gap-4">
            <FormField
              control={form.control}
              name="desktops"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Desktops</FormLabel>
                  <FormControl>
                    <Input type="number" {...field} />
                  </FormControl>
                  <FormDescription>
                    Quantidade de desktops na sala (0 para sala sem desktops).
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="laptops"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Laptops</FormLabel>
                  <FormControl>
                    <Input type="number" {...field} />
                  </FormControl>
                  <FormDescription>
                    Quantidade de laptops na sala (0 para sala sem laptops).
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="projector"
              render={({ field }) => (
                <FormItem className="flex flex-row items-center justify-between rounded-lg border p-3 shadow-sm">
                  <div className="space-y-0.5">
                    <FormLabel>A sala possui Projetor?</FormLabel>
                    <FormDescription>
                      Ative para indicar que a sala está equipada com um
                      projetor.
                    </FormDescription>
                  </div>
                  <FormControl>
                    <Switch
                      checked={field.value}
                      onCheckedChange={field.onChange}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="tv"
              render={({ field }) => (
                <FormItem className="flex flex-row items-center justify-between rounded-lg border p-3 shadow-sm">
                  <div className="space-y-0.5">
                    <FormLabel>A sala possui Televisão?</FormLabel>
                    <FormDescription>
                      Ative para indicar que a sala está equipada com uma
                      televisão.
                    </FormDescription>
                  </div>
                  <FormControl>
                    <Switch
                      checked={field.value}
                      onCheckedChange={field.onChange}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>
          <Button type="submit">Atualizar Sala</Button>
        </form>
      </Form>
    </div>
  );
}
