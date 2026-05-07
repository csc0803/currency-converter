import useCurrencies from "../../hooks/useCurrencies";

export default function CurrencySelect({ label, value, onChange }) {
  const { currencies, error } = useCurrencies();

  return (
    <div>
      <label className="form-label fw-bold">{label}</label>
      {error && <p className="text-danger">{error}</p>}
      <select
        className="form-select"
        value={value}
        onChange={(e) => onChange(e.target.value)}
      >
        {currencies.map((c) => (
          <option key={c}>{c}</option>
        ))}
      </select>
    </div>
  );
}
