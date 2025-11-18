import { API_URL, Software } from "./api";

export async function fetchSoftwares(
  setSoftwares: React.Dispatch<React.SetStateAction<Software[]>>
) {
  try {
    const response = await fetch(`${API_URL}/softwares`);
    const data: Software[] = await response.json();
    setSoftwares(data);
  } catch (error) {
    console.error("Failed to fetch softwares:", error);
  }
}

export async function fetchSoftwareById(id: number) {
  try {
    const response = await fetch(`${API_URL}/softwares/${id}`);
    return await response.json();
  } catch (error) {
    console.error("Failed to fetch software:", error);
  }
}

export async function createSoftware(software: Software) {
  try {
    await fetch(`${API_URL}/softwares`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(software),
    });
  } catch (error) {
    console.error("Failed to create software:", error);
  }
}

export async function updateSoftware(id: number, software: Software) {
  try {
    await fetch(`${API_URL}/softwares/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(software),
    });
  } catch (error) {
    console.error("Failed to update software:", error);
  }
}

export async function deleteSoftware(id: number) {
  try {
    await fetch(`${API_URL}/softwares/${id}`, { method: "DELETE" });
  } catch (error) {
    console.error("Failed to delete software:", error);
  }
}
