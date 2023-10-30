import { Link } from "react-router-dom";
import "./HomePage.css";
export function HomePage() {
  return (
    <>
      <div className="bg-slate-700">
        <ul className="center-options">
          <li>
            <Link to="/login">Login</Link>
          </li>
          <li>
            <Link to="/register">Register</Link>
          </li>
        </ul>
      </div>
    </>
  );
}
