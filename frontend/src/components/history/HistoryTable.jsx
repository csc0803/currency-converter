import { useState, useEffect } from "react";
import { getConversionHistory } from "../../services/api";

export default function HistoryTable({ refreshTrigger }) {
  const [history, setHistory] = useState([]);

  useEffect(() => {
    getConversionHistory()
      .then((res) => {
        setHistory(res.data);
      })
      .catch((err) => console.error("無法取得歷史紀錄", err));
  }, [refreshTrigger]);

  if (history.length === 0)
    return (
      <div className="text-center text-muted mt-4">
        <p>目前沒有換算紀錄</p>
      </div>
    );

  return (
    <div className="card p-4 shadow-sm">
      <h3 className="mb-4">最近換算紀錄</h3>
      <div className="table-responsive">
        <table className="table table-striped table-hover">
          <thead className="table-dark">
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
                <td>
                  <span className="badge bg-secondary">{r.fromCurrency}</span>
                </td>
                <td>
                  <span className="badge bg-secondary">{r.toCurrency}</span>
                </td>
                <td>{Number(r.amount).toLocaleString()}</td>
                <td className="fw-bold">{Number(r.convertedAmount).toLocaleString()}</td>
                <td className="text-muted">{Number(r.exchangeRate).toFixed(4)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
