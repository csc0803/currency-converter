import { useState, useEffect } from "react";
import { getConversionHistory } from "../services/api";

export default function HistoryTable({ refreshTrigger }) {
  const [history, setHistory] = useState([]);

  useEffect(() => {
    getConversionHistory()
      .then((res) => {
        setHistory(res.data)
      })
      .catch((err) => console.error("無法取得歷史紀錄", err));
  }, [refreshTrigger]);

  if (history.length === 0) return null;

  return (
    <div>
      <h3>最近換算紀錄</h3>
      <table>
        <thead>
          <tr>
            <th>時間</th>
            <th>來源</th>
            <th>目標</th>
            <th>金額</th>
            <th>結果</th>
            <th>匯率</th>
          </tr>
        </thead>

        <tbody>
          {history.map((r) => (
            <tr key={r.id}>
              <td>{new Date(r.createdAt).toLocaleString("zh-TW")}</td>
              <td>{r.fromCurrency}</td>
              <td>{r.toCurrency}</td>
              <td>{Number(r.amount).toLocaleString()}</td>
              <td>{Number(r.convertedAmount).toLocaleString()}</td>
              <td>{Number(r.exchangeRate).toFixed(4)}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
