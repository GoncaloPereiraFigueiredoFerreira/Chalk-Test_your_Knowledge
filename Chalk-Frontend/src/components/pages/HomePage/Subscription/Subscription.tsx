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
  link: string;
}

export function Subscription() {


  return (
    <div className="h-screen overflow-y-auto overflow-x-hidden">
      <NavBar></NavBar>

      <SubscriptionBopy/>

      <Footer></Footer>
    </div>
  );
}

export function SubscriptionBopy() {
  const features: string[] = [
    "Criação, resolução e correção de exercicios e testes",
    "Banco de perguntas e testes da comunidade",
    "Serviços IA básicos",
    "Acesso completo",
    "Serviços IA extendidos",
    "Sem anúncios",
    "Gestão de instituição",
    "Banco de perguntas privado",
  ];
  return(
  <div className="min-h-[120%] py-12 first-section">
  <div className="w-full pt-16 pb-24 text-center hover:scale-125 transition-all duration-75">
    <h4 className=" text-4xl text-black font-pacifico dark:text-white">
      Choose the right plan for you
    </h4>
    <p className=" text-lg text-black mt-2 dark:text-white">
      Pricing built to accomodate all individuals and institutions. Choose
      package that suits your needs.
    </p>
  </div>
  <div className="w-full 2xl:w-11/12 items-center justify-center px-8 md:px-32 lg:px-16 2xl:px-0 mx-auto -mt-8 ">
    <div className="w-fit h-fit grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-8">
      {/* Basic */}
      <SubTable
        name="Base"
        billingType=""
        color="yellow"
        price="Grátis"
        colorIntensity={400}
        features={features}
        guaranted={2}
        buttonText="Registar"
        link="/register"
      />

      {/* Premium */}
      <SubTable
        name="Premium"
        billingType="mês"
        color="green"
        price="20€"
        colorIntensity={700}
        features={features}
        guaranted={5}
        buttonText="Comprar"
        link="/register"
      />

      {/* Institution */}
      <SubTable
        name="Instituição"
        billingType="ano"
        color="blue"
        price="Negociável"
        colorIntensity={600}
        features={features}
        guaranted={8}
        buttonText="Comprar"
        link="/register"
      />

      {/* Question Pack */}
      <SubTable
        name="Add-on Pack de Questões"
        billingType="mês"
        color="red"
        price="~5€ cada"
        colorIntensity={600}
        features={["Perguntas exclusivas dos vários parceiros:"]}
        guaranted={5}
        buttonText="Compra In-app"
        link="/webapp/profile"
      />
    </div>
  </div>
</div>
  )
}