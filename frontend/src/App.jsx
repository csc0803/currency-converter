import logo from './logo.svg';
import './App.css';
import { useState } from 'react';
import CurrencyForm from './components/CurrencyForm';
import ResultCard from './components/ResultCard';
import HistoryTable from './components/HistoryTable';

function App() {
  const [result, setResult] = useState(null);
  const [error, setError] = useState('');
  const [refresh, setRefresh] = useState(0);

  const handleResult = (data) => {
    setResult(data);
    setRefresh((r) => r + 1);
  };

  return (
    <div>
      <h1>即時匯率轉換器</h1>

      <CurrencyForm onResult={handleResult} onError={setError} />

      {error && <p>{error}</p>}

      <ResultCard result={result} />

      <HistoryTable refreshTrigger={refresh} />
    </div>
  );
}

export default App;
