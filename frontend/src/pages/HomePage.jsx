import { useState } from "react";
import CurrencyForm from "../components/conversion/CurrencyForm";
import ResultCard from "../components/conversion/ResultCard";

export default function HomePage() {
  const [result, setResult] = useState(null);
  const [error, setError] = useState("");
  const [refresh, setRefresh] = useState(0);

  const handleResult = (data) => {
    setResult(data);
    setError("");
    setRefresh((r) => r + 1);
  };

  return (
    <div className="container mt-4">
      <CurrencyForm onResult={handleResult} onError={setError} />
      {error && <p className="text-danger">{error}</p>}
      <ResultCard result={result} />
    </div>
  );
}
