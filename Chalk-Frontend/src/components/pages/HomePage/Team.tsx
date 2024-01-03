import { useEffect, useRef, useState } from "react";
import "./HomePage.css";
import { TeamMember } from "./TeamMember";
import { useIsVisible } from "./HomePage";

export interface Member {
  name: string;
  photoPath: string;
  position: string;
  facebook?: string;
  twitter?: string;
  github?: string;
  linkdin?: string;
  instagram?: string;
}

export function Team() {
  const ref1 = useRef(null);
  const isVisible1 = useIsVisible(ref1);
  const [triggered1, setTriggered1] = useState(false);

  useEffect(() => {
    if (isVisible1) setTriggered1(true);
  }, [ref1, isVisible1]);

  return (
    <>
      <div className="container py-40 mx-auto px-4" id="team">
        <section ref={ref1} className=" text-center">
          <h2
            className={`mb-32 text-5xl font-bold transition-all duration-1000 ${
              isVisible1 || triggered1
                ? "translate-y-0 opacity-100 scale-100"
                : " -translate-y-96 opacity-0 scale-150"
            }`}
          >
            Meet the <u className=" text-primary dark:text-primary-400">team</u>
          </h2>

          <div className="grid gap-x-6 gap-y-32 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 lg:gap-x-12">
            <TeamMember
              name="Gonçalo Figueiredo"
              position="CEO"
              photoPath="ganso.jpg"
              facebook={"/#"}
              twitter={undefined}
              github={undefined}
              linkdin={"undefined"}
              instagram={"/#"}
            />

            <TeamMember
              name="Francisco Faria"
              position="Frontend Developer"
              photoPath="chico.jpg"
              facebook={"/#"}
              twitter={undefined}
              github={undefined}
              linkdin={"undefined"}
              instagram={"/#"}
            />

            <TeamMember
              name="Alexandre Silva"
              position="Backend designer"
              photoPath="alex.jpg"
              facebook={"/#"}
              twitter={undefined}
              github={undefined}
              linkdin={"undefined"}
              instagram={"/#"}
            />

            <TeamMember
              name="Luís Peixoto"
              position="Backend developer"
              photoPath="luis.jpg"
              facebook={"/#"}
              twitter={undefined}
              github={undefined}
              linkdin={"undefined"}
              instagram={"/#"}
            />

            <TeamMember
              name="Hugo Nogueira"
              position="Security expert"
              photoPath="hugo.jpg"
              facebook={"/#"}
              twitter={undefined}
              github={undefined}
              linkdin={"undefined"}
              instagram={"/#"}
            />

            <TeamMember
              name="Rui Braga"
              position="Backend Developer"
              photoPath="rui.jpg"
              facebook={"/#"}
              twitter={undefined}
              github={undefined}
              linkdin={"undefined"}
              instagram={"/#"}
            />

            <TeamMember
              name="Gonçalo dos Santos"
              position="AI expert"
              photoPath="bronze.jpg"
              facebook={"/#"}
              twitter={undefined}
              github={undefined}
              linkdin={"undefined"}
              instagram={"/#"}
            />

            <TeamMember
              name="Diogo Casal Novo"
              position="Diogo Casal Novo"
              photoPath="diogo.jpg"
              facebook={"/#"}
              twitter={undefined}
              github={undefined}
              linkdin={"undefined"}
              instagram={"/#"}
            />
          </div>
        </section>
      </div>
    </>
  );
}
