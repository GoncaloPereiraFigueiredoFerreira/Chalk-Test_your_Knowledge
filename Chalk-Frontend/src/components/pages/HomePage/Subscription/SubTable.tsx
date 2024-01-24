import { Link } from "react-router-dom";
import { SubTableProps } from "./Subscription";
import { CheckMark } from "../../../objects/SVGImages/SVGImages";

export function SubTable({
  name,
  color,
  colorIntensity,
  price,
  billingType,
  features,
  guaranted,
  buttonText,
  link,
}: SubTableProps) {
  return (
    <div className="flex flex-col bg-white shadow-2xl rounded-lg py-4 hover:scale-105 transition-all duration-100 h-fit">
      <p
        className={
          "text-2xl text-center font-bold text-" +
          color +
          "-" +
          colorIntensity.toString()
        }
      >
        {name}
      </p>
      <p className="text-center py-8 hover:scale-110 transition-all duration-100">
        <span className="text-4xl font-bold text-gray-700">
          <span>{price}</span>
        </span>
        {billingType !== "" ? (
          <span className="text-xs uppercase text-gray-500">
            /{billingType}
          </span>
        ) : (
          <></>
        )}
      </p>
      <ul className="border-t border-gray-300 py-8 space-y-6">
        {Object.entries(features).map(([, feature], index) => (
          <li className="flex items-center space-x-2 px-8 hover:scale-105">
            <CheckMark
              style={` 
              ${
                index < guaranted
                  ? "bg-" + color + "-" + colorIntensity.toString()
                  : "bg-gray-300"
              }`}
              size=""
            />
            <span
              className={` 
              ${index < guaranted ? "text-gray-600 " : "text-gray-400 "}`}
            >
              {feature}
            </span>
          </li>
        ))}
      </ul>
      <div className="flex items-center justify-center mt-6">
        <a
          href="#"
          className={
            "bg-" +
            color +
            "-" +
            colorIntensity.toString() +
            " hover:bg-" +
            color +
            "-" +
            (colorIntensity + 300).toString() +
            " px-8 py-2 text-sm text-white uppercase rounded font-bold hover:scale-110 transition duration-150"
          }
          title="Purchase"
        >
          <Link to={link}>{buttonText}</Link>
        </a>
      </div>
    </div>
  );
}
