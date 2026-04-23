export default function ResultCard({result}){
    if(!result) return null;

    return(
        <div>
            <div>
                <span>{Number(result.amount).toLocaleString()}</span>
                <span>{result.fromCurrency}</span>
                <span>=</span>
                <span>{Number(result.convertedAmount).toLocaleString()}</span>
                <span>{result.toCurrency}</span>
            </div>
            <p>
                匯率:1 {result.fromCurrency} = {Number(result.exchangeRate).toFixed(4)} {result.toCurrency}
            </p>
        </div>
    )
}