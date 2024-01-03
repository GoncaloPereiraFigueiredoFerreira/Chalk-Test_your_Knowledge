import { Footer } from "../../../objects/Footer/Footer";
import { NavBar } from "../../../objects/NavBar/NavBar";
import "./Subscription.css";
import { SubTable } from "./SubTable";

export interface SubTableProps {
  name: string;
  color: string;
  price: string;
  colorIntensity: number;
  billingType: string;
  features: string[];
  guaranted: number;
  buttonText: string;
}

export function Subscription() {
  let features: string[] = [
    "Knowledge Evalutation wiht our public tests and exercises",
    "Class/Group Management",
    "Reports",
    "Unlimited Users",
    "Data Export",
    "Automated Workflows",
    "API Access",
  ];

  return (
    <div className="h-screen overflow-auto">
      <NavBar></NavBar>

      <div className="min-h-full py-12 first-section dark:bg-black">
        <div className="w-full pt-16 pb-24 text-center hover:scale-125 transition-all duration-75">
          <h4 className=" text-4xl text-black font-pacifico dark:text-white">
            Choose the right plan for you
          </h4>
          <p className=" text-lg text-black mt-2 dark:text-white">
            Pricing built to accomodate all individuals and institutions. Choose
            package that suits your needs.
          </p>
        </div>
        <div className="w-full 2xl:w-3/4 items-center justify-center px-8 md:px-32 lg:px-16 2xl:px-0 mx-auto -mt-8 ">
          <div className="w-fit h-fit grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-3 gap-8">
            {/* Basic */}
            <SubTable
              name="Free"
              billingType=""
              color="yellow"
              price="Free"
              colorIntensity={400}
              features={features}
              guaranted={1}
              buttonText="Create Account"
            />
            {/* Premium */}
            <SubTable
              name="Premium"
              billingType="MONTH"
              color="blue"
              price="$29"
              colorIntensity={700}
              features={features}
              guaranted={5}
              buttonText="Purchase"
            />

            {/* Pro */}
            <SubTable
              name="Pro"
              billingType="YEAR"
              color="blue"
              price="$200"
              colorIntensity={600}
              features={features}
              guaranted={7}
              buttonText="Purchase"
            />
          </div>
        </div>
      </div>

      <Footer></Footer>
    </div>
  );
}
