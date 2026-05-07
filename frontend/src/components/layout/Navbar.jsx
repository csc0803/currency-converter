import { Link } from "react-router-dom";
import FetchRatesButton from "../common/FetchRatesButton"

export default function Navbar() {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark px-4">
      <span className="navbar-brand">💱 匯率轉換器</span>
      <div className="navbar-nav">
        <Link className="nav-link" to="/">
          首頁
        </Link>
        <Link className="nav-link" to="/convert-all">
          全幣別換算
        </Link>
        <Link className="nav-link" to="/history">
          換算紀錄
        </Link>
      </div>
      <div className="ms-auto">
        <FetchRatesButton />
      </div>
    </nav>
  );
}
