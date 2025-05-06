import React from 'react';
import ConfluenceBoton from './Confluence';
import EvaluacionForm from './EvaluacionForm';
import RankingTable from './RankingTable';


function App() {
  return (
    <div>
      <h1>Plataforma de Evaluaciones - Reto Kata</h1>
      <EvaluacionForm />
      <RankingTable />
      <ConfluenceBoton />
    </div>
  );
}

export default App;
