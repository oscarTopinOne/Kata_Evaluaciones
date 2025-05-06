import React, { useState } from "react";
import { enviarEvaluacion } from "./api";

function EvaluacionForm() {
  const [form, setForm] = useState({
    participanteId: "",
    juradoId: "",
    perfil: "",
    comunicacion: "",
    tecnico: "",
    puntosExtra: ""
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const payload = {
      ...form,
      participanteId: parseFloat(form.participanteId),
      juradoId: parseFloat(form.juradoId),
      perfil: parseFloat(form.perfil),
      comunicacion: parseFloat(form.comunicacion),
      tecnico: parseFloat(form.tecnico),
      puntosExtra: parseFloat(form.puntosExtra)
    };

    const exito = await enviarEvaluacion(payload);
    alert(exito ? "✅ Evaluación registrada" : "❌ Error al registrar evaluación");
  };

  return (
    <form onSubmit={handleSubmit}>
      <input type="text" name="participanteId" placeholder="ID Participante" onChange={handleChange} required />
      <input type="text" name="juradoId" placeholder="ID Evaluador" onChange={handleChange} required />
      <input type="number" name="perfil" placeholder="Perfil (0-100)" onChange={handleChange} required />
      <input type="number" name="comunicacion" placeholder="Comunicación (0-100)" onChange={handleChange} required />
      <input type="number" name="tecnico" placeholder="Técnico (0-100)" onChange={handleChange} required />
      <input type="number" name="puntosExtra" placeholder="Puntos extra (opcional)" onChange={handleChange} />
      <button type="submit">Enviar Evaluación</button>
    </form>
  );
}

export default EvaluacionForm;
