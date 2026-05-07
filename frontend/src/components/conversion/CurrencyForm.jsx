import { useState, useEffect } from "react";
import CurrencySelect from "../common/CurrencySelect";
import { getAvailableCurrencies, convertCurrency } from "../../services/api";

export default function CurrencyForm({ onResult, onError }) {
  const [currencies, setCurrencies] = useState([]);
  const [form, setForm] = useState({
    fromCurrency: "USD",
    toCurrency: "TWD",
    amount: "",
  });
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    getAvailableCurrencies()
      .then((res) => setCurrencies(res.data))
      .catch(() => onError("無法取得幣別清單"));
  }, []);

  const hanndleSwap = () => {
    setForm({
      ...form,
      fromCurrency: form.toCurrency,
      toCurrency: form.fromCurrency,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!form.amount || Number(form.amount <= 0)) {
      onError("請輸入有效的金額");
      return;
    }

    setLoading(true);
    try {
      const res = await convertCurrency({
        fromCurrency: form.fromCurrency,
        toCurrency: form.toCurrency,
        amount: parseFloat(form.amount),
      });
      onResult(res.data);
      onError("");
    } catch (err) {
      onError(err.response?.data?.message || "換算失敗，請稍後再試");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="card p-4 shadow-sm">
      <form onSubmit={handleSubmit}>
        <div className="row g-3 align-item-end">
          <div className="col-md-4">
            <CurrencySelect
              label="來源幣別"
              value={form.fromCurrency}
              onChange={(value) => setForm({ ...form, fromCurrency: value })}
            />
          </div>

          <div className="col-md-4">
            <CurrencySelect
              label="目標幣別"
              value={form.toCurrency}
              onChange={(value) => setForm({ ...form, toCurrency: value })}
            />
          </div>

          <div className="col-md-4">
            <label className="form-label fw-bold">金額</label>
            <input
              className="form-control"
              type="number"
              placeholder="請輸入金額"
              value={form.amount}
              onChange={(e) => setForm({ ...form, amount: e.target.value })}
              min="0"
              step="any"
            />
          </div>
        </div>

        <div className="mt-3 d-flex gap-2">
          <button
            type="button"
            className="btn btn-outline-secondary"
            onClick={hanndleSwap}
          >
            ⇄ 交換幣別
          </button>
          <button type="submit" className="btn btn-primary" disabled={loading}>
            {loading ? "查詢中..." : "立即換算"}
          </button>
        </div>
      </form>
    </div>
  );
}
