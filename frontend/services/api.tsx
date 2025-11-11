import { useEffect, useState } from "react";
import { fetchBookings } from "./bookingsService";

export const API_URL = "http://localhost:3333";


export type User = {
  id?: number;
  name: string;
  email: string;
};

export type Laboratory = {
  id?: number;
  name: string;
  location: string;
};

export type Equipment = {
  id?: number;
  name: string;
  description: string;
};

export type Booking = {
  id: string;
  title: string;
  startTime: string;
  endTime: string;
  user?: string;
  type?: "Aula" | "Agendamento";
  resource?: string;
  color?: string;
};

export type Software = {
  id?: number;
  name: string;
  version: string;
};
