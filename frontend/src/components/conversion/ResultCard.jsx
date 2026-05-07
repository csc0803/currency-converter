export default function ResultCard({ result }) {
  if (!result) return null;

  return (
    <div className="card mt-4 shadow-sm">
      <div className="card-body text-center">
        <div className="d-flex justify-content-center align-item-center gap-3 flex-wrap">
          <div>
            <span className="fs-3 fw-bold">
              {Number(result.amount).toLocaleString()}
            </span>
            <span className="ms-2 badge bg-secondary fs-6">
              {result.fromCurrency}
            </span>
          </div>
          <span className="fs-3 text-muted">=</span>
          <div>
            <span className="fs-3 fw-bold">
              {Number(result.convertedAmount).toLocaleString()}
            </span>
            <span className="ms-2 badge bg-secondary fs-6">
              {result.toCurrency}
            </span>
          </div>
          <p className="text-muted mt-3 mb-0">
            匯率:1 {result.fromCurrency} =
            {Number(result.exchangeRate).toFixed(4)} {result.toCurrency}
          </p>
        </div>
      </div>
    </div>
  );
}
