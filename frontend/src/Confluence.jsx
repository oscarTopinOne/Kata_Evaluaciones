import React from "react";
import axios from "axios";

function ConfluenceBoton() {
  const publicarEnConfluence = async () => {
    try {
      await axios.post("/api/confluence/publicar");
      alert("✅ Reporte publicado en Confluence");
    } catch (error) {
      console.error("❌ Error al publicar:", error);
      alert("Ocurrió un error al enviar el reporte a Confluence");
    }
  };

  return (
    <button onClick={publicarEnConfluence}>
      Publicar reporte en Confluence
    </button>
  );
}

export default ConfluenceBoton;