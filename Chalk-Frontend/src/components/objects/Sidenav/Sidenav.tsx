import "./Sidenav.css"
import { SidebarIcon, DownArrowIcon, FoldersIcon, CheckListIcon, SettingsIcon, GroupIcon, PenIcon, MessageBoxIcon, HelpIcon, TeacherIcon, GraduateIcon, AdjustIcon } from "../SVGImages.tsx"
import { useState } from "react";

const groups: { [key: string]: string } = {
    "1": "Professores da escola AFS Gualtar",
    "2": "Turma A",
    "3": "Turma F"
  };



export function Sidenav() {
    // const [selected,setSelected] = useState(0)
    const [sidebarIsOpen,setSidebarIsOpen] = useState(true)
    const [group,setGroup] = useState('0')
    const [showGroup,setShowGroup] = useState(false)


    function getGroup (num: string) {
        console.log(groups[num]);
        
        if (num === "0" || showGroup){
            return(
                <>
                    {GroupIcon()}
                    <span className={`sidenav-dropdown-item ${sidebarIsOpen? "":"hidden"}`}>Grupos</span>
                    {sidebarIsOpen? 
                        DownArrowIcon()
                        :
                        null
                    }
                </>
            )
        } else if (num in groups){
            return(
                <>
                    {GraduateIcon()}
                    <span className={`sidenav-dropdown-item ${sidebarIsOpen? "":"hidden"}`}>{groups[num]}</span>
                    {sidebarIsOpen? 
                        DownArrowIcon()
                        :
                        null
                    }
                </>
            )
        }
    }


    return(
        <>
            <button type="button" onClick={() => {setSidebarIsOpen(!sidebarIsOpen);setShowGroup(false)}} className="sidenav">
                <span className="sr-only">Open sidebar</span>
                {SidebarIcon()}
            </button>
            <aside id="default-sidenav" className={`sidenav-background ${sidebarIsOpen? "":"w-max"}`} aria-label="Sidenav">
                <div className="sidenav-content">
                    <ul className="sidenav-divisions border-t-0">
                        <li>
                            <button type="button" onClick={() => {setSidebarIsOpen(true);setShowGroup(!showGroup)}} className="sidenav-item">
                                {getGroup(group)}
                            </button>
                            <ul className={`${showGroup? "":"hidden"} sidenav-dropdown`}>
                                <li>
                                    <button onClick={()=>setGroup("0")} className="sidenav-item">
                                        {GraduateIcon()}
                                        <span className={sidebarIsOpen? "":"hidden"}>Geral</span>
                                    </button>
                                </li>
                                {Object.entries(groups).map(([key, item])=>(
                                    <li key={key}>
                                        <button onClick={()=>setGroup(key)} className="sidenav-item">
                                            {GraduateIcon()}
                                            <span className={sidebarIsOpen? "":"hidden"}>{item}</span>
                                        </button>
                                    </li>
                                ))}
                            </ul>
                        </li>
                    </ul>

                    <ul className="sidenav-divisions">
                        <li>
                            <button className="sidenav-item">
                                {CheckListIcon()}
                                <span className={sidebarIsOpen? "":"hidden"}>Avaliações</span>
                            </button>
                        </li>
                        <li>
                            <button className="sidenav-item">
                                {PenIcon()}
                                <span className={sidebarIsOpen? "":"hidden"}>Exercicos</span>
                            </button>
                        </li>
                        <li>
                            <button className="sidenav-item">
                                {FoldersIcon()}
                                <span className={sidebarIsOpen? "":"hidden"}>Rubricas</span>
                            </button>
                        </li>
                    </ul>
                    <ul className="sidenav-divisions">
                        <li>
                            <button className="sidenav-item">
                                {HelpIcon()}
                                <span className={sidebarIsOpen? "":"hidden"}>Ajuda</span>
                            </button>
                        </li>
                        <li>
                            <button className="sidenav-item">
                                {TeacherIcon()}
                                <span className={sidebarIsOpen? "":"hidden"}>Tutoriais</span>
                            </button>
                        </li>
                        <li>
                            <button className="sidenav-item">
                                {MessageBoxIcon()}
                                <span className={sidebarIsOpen? "":"hidden"}>Sugestões</span>
                            </button>
                        </li>
                        <li>
                            <button className="sidenav-item">
                                {SettingsIcon()}
                                <span className={sidebarIsOpen? "":"hidden"}>Defenições</span>
                            </button>
                        </li>
                    </ul>
                </div>
            </aside>
        </>
    );
  }