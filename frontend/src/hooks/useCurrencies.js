import { useState, useEffect } from "react";
import { getAvailableCurrencies } from "../services/api";

export default function useCurrencies() {
  const [currencies, setCurrencies] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    getAvailableCurrencies()
      .then((res) => setCurrencies(res.data))
      .catch((err) => setError("無法取得幣別清單" + err));
  }, []);

  return { currencies, error };
}
