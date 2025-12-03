import { API_URL, Classroom } from "./api";

export async function fetchClassrooms(
  setLabs: React.Dispatch<React.SetStateAction<Classroom[]>>
) {
  try {
    const response = await fetch(`${API_URL}/classrooms`);
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    const data: Classroom[] = await response.json();
    setLabs(data);
  } catch (error) {
    console.error("Failed to fetch classrooms:", error);
  }
}

export async function fetchClassroomById(id: string) {
  try {
    const response = await fetch(`${API_URL}/classrooms/${id}`);
    return await response.json();
  } catch (error) {
    console.error("Failed to fetch classroom:", error);
  }
}

export async function createClassroom(lab: Classroom) {
  try {
    await fetch(`${API_URL}/classrooms`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(lab),
    });
  } catch (error) {
    console.error("Failed to create classroom:", error);
  }
}

export async function updateClassroom(id: string, lab: Classroom) {
  try {
    await fetch(`${API_URL}/classrooms/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(lab),
    });
  } catch (error) {
    console.error("Failed to update classroom:", error);
  }
}

export async function deleteClassroom(id: number) {
  try {
    await fetch(`${API_URL}/classrooms/${id}`, { method: "DELETE" });
  } catch (error) {
    console.error("Failed to delete classroom:", error);
  }
}
