import { useState } from "react";
import { convertAll } from "../../services/api";
import CurrencySelect from "../common/CurrencySelect";

export default function ConvertAllTable() {
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [form, setForm] = useState({
    fromCurrency: "USD",
    amount: "",
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!form.amount || Number(form.amount) <= 0) {
      setError("請輸入有效金額");
      return;
    }
    setLoading(true);
    setError("");
    try {
      const res = await convertAll(form.fromCurrency, form.amount);
      setResults(res.data);
    } catch (err) {
      setError("查詢失敗" + err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="card p-4 shadow-sm">
      <h2 className="mb-4">全幣別換算</h2>

      <form onSubmit={handleSubmit}>
        <div className="row g-3 align-item-end">
          <div className="col-md-4">
            <CurrencySelect
              label="來源幣別"
              value={form.amount}
              onChange={(value) => setForm({ ...form, fromCurrency: value })}
            />
          </div>
          
          <div className="col-md-4">
            <label className="form-label fw-bold">金額</label>
            <input
              className="form-control"
              type="number"
              placeholder="金額"
              value={form.amount}
              onChange={(e) => setForm({ ...form, amount: e.target.value })}
              min="0"
              step="any"
            />
          </div>

          <div className="col-md-4 d-flex align-items-end">
            <label className="form-label">&nbsp;</label>
            <button
              type="submit"
              className="btn btn-primary"
              disabled={loading}
            >
              {loading ? (
                <>
                  <span className="spinner-border spinner-border-sm me-2" />
                  "查詢中..."
                </>
              ) : (
                "查詢所有匯率"
              )}
            </button>
          </div>
        </div>
      </form>

      {error && <p className="text-danger mt-3">{error}</p>}

      {Array.isArray(results) && results.length > 0 && (
        <div className="table-responsive mt-4">
          <table className="table table-striped table-hover">
            <thead className="table-dark">
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
        </div>
      )}
    </div>
  );
}
