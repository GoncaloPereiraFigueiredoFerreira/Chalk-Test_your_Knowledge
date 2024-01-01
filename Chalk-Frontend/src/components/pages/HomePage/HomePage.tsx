import { useEffect, useState } from "react";
import { NavBar } from "../../objects/NavBar/NavBar";
import { Footer } from "../../objects/Footer/Footer";
import { Team } from "./Team";
import { Contacts } from "./Contacts";
import { About } from "./About";
import { Front } from "./Front";
//import { Slide } from "./Slide";
import "./HomePage.css";
import { Reviews } from "./Reviews";
import { Features } from "./Features";

export function useIsVisible(ref: any) {
  const [isIntersecting, setIntersecting] = useState(false);

  useEffect(() => {
    const observer = new IntersectionObserver(([entry]) => {
      setIntersecting(entry.isIntersecting);
    });

    observer.observe(ref.current);
    return () => {
      observer.disconnect();
    };
  }, [ref]);

  return isIntersecting;
}

export function HomePage() {
  return (
    <div className="h-screen overflow-y-auto overflow-x-hidden">
      <NavBar></NavBar>
      {/**<!-- Front section -->*/}
      <Front></Front>
      {/**<!-- FEATURES section -->*/}
      <Features></Features>
      {/**<!-- ABOUT section -->*/}
      <About></About>
      {/**<!-- Reviews section -->*/}
      <Reviews></Reviews>
      {/**<!-- TEAM section -->*/}
      <Team></Team>
      {/**<!-- CONTACT section -->*/}
      <Contacts></Contacts>
      {/**<!-- Footer section -->*/}
      <Footer></Footer>
    </div>
  );
}
