import React from "react";
import { API_URL, Laboratory } from "./api";
async function fetchLaboratories(setLabs : React.Dispatch<React.SetStateAction<Laboratory[]>>) {
    try {
      const response = await fetch(`${API_URL}/laboratories`);
      if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
      const data: Laboratory[] = await response.json();
      setLabs(data);
    } catch (error) {
      console.error("Failed to fetch laboratories:", error);
    }
  }

  async function fetchLaboratoryById(id: number) {
    try {
      const response = await fetch(`${API_URL}/laboratories/${id}`);
      return await response.json();
    } catch (error) {
      console.error("Failed to fetch laboratory:", error);
    }
  }

  async function createLaboratory(lab: Laboratory) {
    try {
      await fetch(`${API_URL}/laboratories`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(lab),
      });
    } catch (error) {
      console.error("Failed to create laboratory:", error);
    }
  }

  async function updateLaboratory(id: number, lab: Laboratory) {
    try {
      await fetch(`${API_URL}/laboratories/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(lab),
      });
    } catch (error) {
      console.error("Failed to update laboratory:", error);
    }
  }

  async function deleteLaboratory(id: number) {
    try {
      await fetch(`${API_URL}/laboratories/${id}`, { method: "DELETE" });
    } catch (error) {
      console.error("Failed to delete laboratory:", error);
    }
  }
