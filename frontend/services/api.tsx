export const API_URL = "https://fatec-glab.onrender.com";

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
  createdAt: string | null;
  startTime: string;
  endTime: string;
  repeat: boolean;
  type: string;
  title: string;
  description: string;
  professorId: string;
  roomId: string;
};

export type Software = {
  id?: number;
  name: string;
  version: string;
};
