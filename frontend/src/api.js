import axios from "axios";

const API_URL = import.meta.env.DEV
  ? '' // en desarrollo, Vite usará el proxy
  : import.meta.env.VITE_API_URL;

export const enviarEvaluacion = async (evaluacion) => {
  try {
    await axios.post(`${API_URL}/api/evaluaciones`, evaluacion);
    return true;
  } catch (error) {
    console.error("Error al enviar evaluación", error);
    return false;
  }
};

export const obtenerResultados = async () => {
  try {
    const res = await axios.get(`${API_URL}/api/evaluaciones/resultados`);
    return res.data;
  } catch (error) {
    console.error("Error al obtener resultados", error);
    return [];
  }
};

export const obtenerRanking = async () => {
  try {
    const res = await axios.get(`${API_URL}/api/evaluaciones/ranking`);
    return res.data;
  } catch (error) {
    console.error("Error al obtener ranking", error);
    return [];
  }
};

export const publicarEnConfluence = async () => {
  try {
    await axios.post(`${API_URL}/api/confluence/publicar`);
    return true;
  } catch (error) {
    console.error("Error al publicar en Confluence", error);
    return false;
  }
};
