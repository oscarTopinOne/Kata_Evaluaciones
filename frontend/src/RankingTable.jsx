import { useEffect, useState } from "react";
import { FaMedal } from "react-icons/fa";
import { obtenerRanking } from "./api";

function RankingTable() {
  const [ranking, setRanking] = useState([]);

  const fetchRanking = async () => {
    const data = await obtenerRanking();
    setRanking(data);
  };

  useEffect(() => {
    fetchRanking();
    const interval = setInterval(fetchRanking, 5000);
    return () => clearInterval(interval);
  }, []);

  const obtenerIcono = (posicion) => {
    const size = 20;
    switch (posicion) {
      case 1: return <FaMedal color="#FFD700" size={size} title="1° lugar" />;
      case 2: return <FaMedal color="#C0C0C0" size={size} title="2° lugar" />;
      case 3: return <FaMedal color="#CD7F32" size={size} title="3° lugar" />;
      default: return <span style={{ color: "#fff" }}>{posicion}</span>;
    }
  };

  return (
    <div style={{ backgroundColor: "#1e1e1e", padding: "20px", borderRadius: "8px" }}>
      <h2 style={{ color: "#fff" }}>🏆 Ranking de Participantes</h2>
      <table style={{ width: "100%", borderCollapse: "collapse", backgroundColor: "#2c2c2c", color: "#fff", border: "1px solid #444" }}>
        <thead style={{ backgroundColor: "#3a3a3a" }}>
          <tr>
            <th style={{ padding: "10px", border: "1px solid #444" }}>Posición</th>
            <th style={{ padding: "10px", border: "1px solid #444" }}>Participante</th>
            <th style={{ padding: "10px", border: "1px solid #444" }}>Puntaje</th>
            <th style={{ padding: "10px", border: "1px solid #444" }}>¿Aprueba?</th>
          </tr>
        </thead>
        <tbody>
          {ranking.map((r, index) => (
            <tr key={index}>
              <td style={{ textAlign: "center", padding: "10px", border: "1px solid #444" }}>
                {obtenerIcono(index + 1)}
              </td>
              <td style={{ padding: "10px", border: "1px solid #444" }}>{r.nombre}</td>
              <td style={{ padding: "10px", border: "1px solid #444" }}>{r.puntaje.toFixed(2)}</td>
              <td style={{ textAlign: "center", padding: "10px", border: "1px solid #444" }}>
                {r.aprobado ? "✅" : "❌"}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default RankingTable;

