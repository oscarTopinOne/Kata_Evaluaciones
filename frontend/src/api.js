import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL;

export const enviarEvaluacion = async (evaluacion) => {
  try {
    await axios.post(`${API_URL}/evaluaciones`, evaluacion);
    return true;
  } catch (error) {
    console.error("Error al enviar evaluación", error);
    return false;
  }
};

export const obtenerResultados = async () => {
  try {
    const res = await axios.get(`${API_URL}/evaluaciones/resultados`);
    return res.data;
  } catch (error) {
    console.error("Error al obtener resultados", error);
    return [];
  }
};
