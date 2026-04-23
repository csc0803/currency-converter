import { useState, useEffect } from "react";
import { convertCurrency, getAvailableCurrencies } from "../services/api";

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

    if(!form.amount || Number(form.amount <= 0)){
        onError('請輸入有效的金額');
        return;
    }

    setLoading(true);
    try{
        const res = await convertCurrency({
            fromCurrency: form.fromCurrency,
            toCurrency: form.toCurrency,
            amount: parseFloat(form.amount)
        });
        onResult(res.data);
        onError('');
    } catch (err){
        onError(err.response?.data?.message || '換算失敗，請稍後再試');
    } finally{
        setLoading(false);
    }
  };

  return(
    <form onSubmit={handleSubmit}>
        <div>
            <label>來源幣別</label>
            <select
                value={form.fromCurrency}
                onChange={(e) => setForm({...form, fromCurrency: e.target.value})}>{currencies.map((c) => <option key={c}>{c}</option>)}
                </select>
        </div>

        <div>
            <label>金額</label>
            <input
                type="number"
                placeholder="請輸入金額"
                value={form.amount}
                onChange={(e) => setForm({...form, amount: e.target.value})}
                min= "0"
                step="any"
                />
        </div>
        <button type="submit" disabled={loading}>
          {loading ? '查詢中...' : '立即換算'}
        </button>
    </form>
  )
}
