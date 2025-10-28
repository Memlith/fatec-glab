"use client";

import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { zodResolver } from "@hookform/resolvers/zod";
import { useSearchParams } from "next/navigation";
import { useForm } from "react-hook-form";
import { z } from "zod";

const softwares = [
  { id: "vscode", label: "VSCode" },
  { id: "eclipse", label: "Eclipse" },
  { id: "java", label: "Java" },
  { id: "node", label: "Node" },
  { id: "autocad", label: "AutoCAD" },
  { id: "arduino", label: "Arduino" },
];

const equipamentos = [
  { id: "laptops", label: "Laptops" },
  { id: "projetor", label: "Projetor" },
  { id: "tv", label: "TV" },
  { id: "makita", label: "Makita" },
];

const formSchema = z.object({
  titulo: z
    .string()
    .min(2, "O nome da reserva deve ter no mínimo 2 caracteres"),
  responsavel: z
    .string()
    .min(2, "O nome do responsável deve ter no mínimo 2 caracteres"),
  data: z.string().min(10, "A data deve ser preenchida"),
  recorrencia: z.boolean().optional(),
  horaInicio: z.string().min(5, "A hora de início deve ser preenchida"),
  horaFim: z.string().min(5, "A hora de fim deve ser preenchida"),
  softwares: z.array(z.string()).refine((value) => value.some((item) => item)),
});

export default function NovaReservaForm() {
  const searchParams = useSearchParams();
  const data = searchParams.get("date");
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      titulo: "",
      responsavel: "",
      data: data || "",
      recorrencia: false,
      horaInicio: "",
      horaFim: "",
    },
  });

  function onSubmit(values: z.infer<typeof formSchema>) {
    console.log(values);
  }

  return (
    <div className="h-full border rounded-md shadow-sm">
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit)}
          className="h-full flex flex-col justify-between p-4"
        >
          <div className="flex flex-col gap-4">
            <FormField
              control={form.control}
              name="titulo"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Título</FormLabel>
                  <FormControl>
                    <Input placeholder="Título da reserva" {...field} />
                  </FormControl>
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="responsavel"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Responsável</FormLabel>
                  <FormControl>
                    <Input placeholder="Responsável da reserva" {...field} />
                  </FormControl>
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="recorrencia"
              render={({ field }) => (
                <FormItem className="flex w-full justify-center gap-2">
                  <FormControl>
                    <Checkbox id="recorrencia" />
                  </FormControl>
                  <FormLabel>A reserva se repete semanalmente?</FormLabel>
                </FormItem>
              )}
            />
            <div className="grid grid-cols-2 gap-4">
              <FormField
                control={form.control}
                name="horaInicio"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Horário de início</FormLabel>
                    <FormControl>
                      <Input type="time" {...field} />
                    </FormControl>
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="horaFim"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Horário de Término</FormLabel>
                    <FormControl>
                      <Input type="time" {...field} />
                    </FormControl>
                  </FormItem>
                )}
              />
            </div>
            <div className="grid grid-cols-2 gap-8">
              <div className="flex flex-col gap-2">
                <h1>Softwares</h1>
                <div className="grid grid-cols-2 gap-4">
                  {softwares.map((software) => (
                    <FormField
                      key={software.id}
                      control={form.control}
                      name="softwares"
                      render={({ field }) => {
                        return (
                          <FormItem
                            key={software.id}
                            className="flex flex-row items-center gap-2"
                          >
                            <FormControl>
                              <Checkbox
                                checked={field.value?.includes(software.id)}
                                onCheckedChange={(checked) => {
                                  return checked
                                    ? field.onChange([
                                        ...field.value,
                                        software.id,
                                      ])
                                    : field.onChange(
                                        field.value?.filter(
                                          (value) => value !== software.id
                                        )
                                      );
                                }}
                              />
                            </FormControl>
                            <FormLabel className="text-sm font-normal">
                              {software.label}
                            </FormLabel>
                          </FormItem>
                        );
                      }}
                    />
                  ))}
                </div>
              </div>
              <div className="flex flex-col gap-2">
                <h1>Equipamentos</h1>
                <div className="grid grid-cols-2 gap-4">
                  {equipamentos.map((equipamento) => (
                    <FormField
                      key={equipamento.id}
                      control={form.control}
                      name="softwares"
                      render={({ field }) => {
                        return (
                          <FormItem
                            key={equipamento.id}
                            className="flex flex-row items-center gap-2"
                          >
                            <FormControl>
                              <Checkbox
                                checked={field.value?.includes(equipamento.id)}
                                onCheckedChange={(checked) => {
                                  return checked
                                    ? field.onChange([
                                        ...field.value,
                                        equipamento.id,
                                      ])
                                    : field.onChange(
                                        field.value?.filter(
                                          (value) => value !== equipamento.id
                                        )
                                      );
                                }}
                              />
                            </FormControl>
                            <FormLabel className="text-sm font-normal">
                              {equipamento.label}
                            </FormLabel>
                          </FormItem>
                        );
                      }}
                    />
                  ))}
                </div>
              </div>
            </div>
          </div>

          <Button type="submit">Criar reserva</Button>
        </form>
      </Form>
    </div>
  );
}
