import { API_URL, Software } from "./api";

export async function fetchSoftwaresSet(
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
export async function fetchSoftwares() {
  try {
    const response = await fetch(`${API_URL}/softwares`);
    const data: Software[] = await response.json();
    return data;
  } catch (error) {
    console.error("Failed to fetch softwares:", error);
  }
}

export async function fetchSoftwareById(id: string) {
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

export async function updateSoftware(id: string, software: Software) {
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

export async function deleteSoftware(id: string) {
  try {
    await fetch(`${API_URL}/softwares/${id}`, { method: "DELETE" });
  } catch (error) {
    console.error("Failed to delete software:", error);
  }
}
