import { API_URL, Equipment } from "./api";

export async function fetchEquipments(
  setEquipments: React.Dispatch<React.SetStateAction<Equipment[]>>
) {
  try {
    const response = await fetch(`${API_URL}/equipments`);
    const data: Equipment[] = await response.json();
    setEquipments(data);
  } catch (error) {
    console.error("Failed to fetch equipments:", error);
  }
}

export async function fetchEquipmentById(id: number) {
  try {
    const response = await fetch(`${API_URL}/equipments/${id}`);
    return await response.json();
  } catch (error) {
    console.error("Failed to fetch equipment:", error);
  }
}

export async function createEquipment(equipment: Equipment) {
  try {
    await fetch(`${API_URL}/equipments`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(equipment),
    });
  } catch (error) {
    console.error("Failed to create equipment:", error);
  }
}

export async function updateEquipment(id: number, equipment: Equipment) {
  try {
    await fetch(`${API_URL}/equipments/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(equipment),
    });
  } catch (error) {
    console.error("Failed to update equipment:", error);
  }
}

export async function deleteEquipment(id: number) {
  try {
    await fetch(`${API_URL}/equipments/${id}`, { method: "DELETE" });
  } catch (error) {
    console.error("Failed to delete equipment:", error);
  }
}
