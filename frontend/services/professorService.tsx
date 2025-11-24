import { API_URL, Professor } from "./api";

export async function fetchProfessorsSet(
  setProfessors: React.Dispatch<React.SetStateAction<Professor[]>>
) {
  try {
    const response = await fetch(`${API_URL}/professors`);
    const data: Professor[] = await response.json();
    setProfessors(data);
  } catch (error) {
    console.error("Failed to fetch professors:", error);
  }
}

export async function fetchProfessors() {
  try {
    const response = await fetch(`${API_URL}/professors`);
    return await response.json();
  } catch (error) {
    console.error("Failed to fetch professors:", error);
  }
}

export async function fetchProfessorById(id: string) {
  try {
    const response = await fetch(`${API_URL}/professors/${id}`);
    return await response.json();
  } catch (error) {
    console.error("Failed to fetch professor:", error);
  }
}

export async function createProfessor(professor: {
  name: string;
  email: string;
}) {
  try {
    const response = await fetch(`${API_URL}/professors`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(professor),
    });

    return response.json();
  } catch (error) {
    console.error("Failed to create professor:", error);
  }
}

export async function updateProfessor(
  id: string,
  professor: {
    name: string;
    email: string;
  }
) {
  try {
    const response = await fetch(`${API_URL}/professors/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(professor),
    });

    return response.json();
  } catch (error) {
    console.error("Failed to update professor:", error);
  }
}

export async function deleteProfessor(id: string) {
  try {
    await fetch(`${API_URL}/professors/${id}`, { method: "DELETE" });
  } catch (error) {
    console.error("Failed to delete professor:", error);
  }
}
