import React from "react";
import { publicarEnConfluence } from "./api"; // importa la función desde api.js

function ConfluenceBoton() {
  const handleClick = async () => {
    const exito = await publicarEnConfluence();
    alert(exito ? "✅ Reporte publicado en Confluence" : "❌ Error al publicar");
  };

  return (
    <button onClick={handleClick}>
      Publicar reporte en Confluence
    </button>
  );
}

export default ConfluenceBoton;
