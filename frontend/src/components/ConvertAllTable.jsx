import { useState } from "react";
import { convertAll } from "../services/api";

export default function ConvertAllTable() {
  const [formCurrency, setFromCurrency] = useState("USD");
  const [amount, setAmount] = useState("");
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!amount || Number(amount) <= 0) {
      setError("請輸入有效金額");
      return;
    }
    setLoading(true);
    setError("");
    try {
      const res = await convertAll(formCurrency, amount);
      setResults(res.data);
    } catch (err) {
      setError("查詢失敗" + err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h2>全幣別換算</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="USD"
          value={formCurrency}
          onChange={(e) => setFromCurrency(e.target.value.toUpperCase)}
        />
        <input
          type="number"
          placeholder="金額"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
          min="0"
          step="any"
        />
        <button type="submit" disabled={loading}>
          {loading ? "查詢中..." : "查詢所有匯率"}
        </button>
      </form>

      {error && <p>{error}</p>}

      {Array.isArray(results) && results.length > 0 && (
        <table>
          <thead>
            <tr>
              <th>目標幣別</th>
              <th>匯率</th>
              <th>換算金額</th>
            </tr>
          </thead>
          <tbody>
            {results.map((r) => (
              <tr key={r.toCurrency}>
                <td>{r.toCurrency}</td>
                <td>{Number(r.exchangeRate).toFixed(4)}</td>
                <td>{Number(r.convertedAmount).toLocaleString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
