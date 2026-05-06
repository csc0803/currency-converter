import { useState } from "react";
import { fetchLatestRates } from "../services/api";

export default function FetchLatestRateButton() {
  const handleFetchRates = async () => {
    try {
      await fetchLatestRates();
      alert("匯率更新成功！");
    } catch (err) {
      alert("匯率更新失敗，請稍後再試");
    }
  };

  return(
    <button onClick={handleFetchRates}>手動更新匯率</button>
  )
}
