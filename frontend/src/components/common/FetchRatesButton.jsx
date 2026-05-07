import { useState } from "react";
import { fetchLatestRates } from "../../services/api";

export default function FetchRatesButton() {
  const [loading, setLoading] = useState(false);

  const handleFetchRates = async () => {
    try {
      await fetchLatestRates();
      alert("匯率更新成功！");
    } catch (err) {
      alert("匯率更新失敗，請稍後再試");
    } finally {
      setLoading(false);
    }
  };

  return(
    <button
      className="btn btn-outline-light btn-sm"
      onClick={handleFetchRates}
      disabled={loading}>
      {loading ? (
        <>
          <span className="spinner-border spinner-border-sm me-2" />
          更新中...
        </>
      ) : '🔄 更新匯率'}
    </button>
  );
  
}
