import { NavBar } from "../../objects/NavBar/NavBar";
import { Footer } from "../../objects/Footer/Footer";
import { Team } from "./Team";
import { Contacts } from "./Contacts";
import { About } from "./About";
import "./HomePage.css";
import { Slide } from "./Slide";
import { Front } from "./Front";

export function HomePage() {
  return (
    <div className="h-screen overflow-auto">
      <NavBar></NavBar>
      {/**<!-- Front section -->*/}
      <Front></Front>
      {/**<!-- Slide section -->
      // <Slide></Slide>*/}
      {/**<!-- ABOUT section -->*/}
      <About></About>
      {/**<!-- TEAM section -->*/}
      <Team></Team>
      {/**<!-- CONTACT section -->*/}
      <Contacts></Contacts>
      {/**<!-- Footer section -->*/}
      <Footer></Footer>
    </div>
  );
}
