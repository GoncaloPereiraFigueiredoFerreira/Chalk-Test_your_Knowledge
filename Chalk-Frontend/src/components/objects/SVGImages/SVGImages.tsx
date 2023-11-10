import "./SVGImages.css";

interface IconProps {
  style?: string;
  size?: string;
}

export function UpArrowIcon() {
  return (
    <svg
      aria-hidden="true"
      className="icon size-6 origin-center rotate-180"
      fill="currentColor"
      viewBox="0 0 20 20"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        fillRule="evenodd"
        d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
        clipRule="evenodd"
      ></path>
    </svg>
  );
}

export function DownArrowIcon() {
  return (
    <svg
      aria-hidden="true"
      className="icon size-6 focus:origin-center focus:rotate-180"
      fill="currentColor"
      viewBox="0 0 20 20"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        fillRule="evenodd"
        d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
        clipRule="evenodd"
      ></path>
    </svg>
  );
}

export function SidebarIcon({ style, size }: IconProps) {
  return (
    <svg
      className={`icon 
      ${style ? style : "black-icon"}
      ${size ? size : "size-6"}`}
      aria-hidden="true"
      fill="currentColor"
      viewBox="0 0 20 20"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        clipRule="evenodd"
        fillRule="evenodd"
        d="M2 4.75A.75.75 0 012.75 4h14.5a.75.75 0 010 1.5H2.75A.75.75 0 012 4.75zm0 10.5a.75.75 0 01.75-.75h7.5a.75.75 0 010 1.5h-7.5a.75.75 0 01-.75-.75zM2 10a.75.75 0 01.75-.75h14.5a.75.75 0 010 1.5H2.75A.75.75 0 012 10z"
      ></path>
    </svg>
  );
}

export function AdjustIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="currentColor"
      viewBox="0 0 20 20"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path d="M5 4a1 1 0 00-2 0v7.268a2 2 0 000 3.464V16a1 1 0 102 0v-1.268a2 2 0 000-3.464V4zM11 4a1 1 0 10-2 0v1.268a2 2 0 000 3.464V16a1 1 0 102 0V8.732a2 2 0 000-3.464V4zM16 3a1 1 0 011 1v7.268a2 2 0 010 3.464V16a1 1 0 11-2 0v-1.268a2 2 0 010-3.464V4a1 1 0 011-1z"></path>
    </svg>
  );
}

export function SettingsIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="currentColor"
      viewBox="0 0 20 20"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        fillRule="evenodd"
        d="M11.49 3.17c-.38-1.56-2.6-1.56-2.98 0a1.532 1.532 0 01-2.286.948c-1.372-.836-2.942.734-2.106 2.106.54.886.061 2.042-.947 2.287-1.561.379-1.561 2.6 0 2.978a1.532 1.532 0 01.947 2.287c-.836 1.372.734 2.942 2.106 2.106a1.532 1.532 0 012.287.947c.379 1.561 2.6 1.561 2.978 0a1.533 1.533 0 012.287-.947c1.372.836 2.942-.734 2.106-2.106a1.533 1.533 0 01.947-2.287c1.561-.379 1.561-2.6 0-2.978a1.532 1.532 0 01-.947-2.287c.836-1.372-.734-2.942-2.106-2.106a1.532 1.532 0 01-2.287-.947zM10 13a3 3 0 100-6 3 3 0 000 6z"
        clipRule="evenodd"
      ></path>
    </svg>
  );
}

export function CircularGraficIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="currentColor"
      viewBox="0 0 20 20"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path d="M2 10a8 8 0 018-8v8h8a8 8 0 11-16 0z"></path>
      <path d="M12 2.252A8.014 8.014 0 0117.748 8H12V2.252z"></path>
    </svg>
  );
}

export function PageIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="currentColor"
      viewBox="0 0 20 20"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        fillRule="evenodd"
        d="M4 4a2 2 0 012-2h4.586A2 2 0 0112 2.586L15.414 6A2 2 0 0116 7.414V16a2 2 0 01-2 2H6a2 2 0 01-2-2V4zm2 6a1 1 0 011-1h6a1 1 0 110 2H7a1 1 0 01-1-1zm1 3a1 1 0 100 2h6a1 1 0 100-2H7z"
        clipRule="evenodd"
      ></path>
    </svg>
  );
}

export function SalesIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="currentColor"
      viewBox="0 0 20 20"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        fillRule="evenodd"
        d="M10 2a4 4 0 00-4 4v1H5a1 1 0 00-.994.89l-1 9A1 1 0 004 18h12a1 1 0 00.994-1.11l-1-9A1 1 0 0015 7h-1V6a4 4 0 00-4-4zm2 5V6a2 2 0 10-4 0v1h4zm-6 3a1 1 0 112 0 1 1 0 01-2 0zm7-1a1 1 0 100 2 1 1 0 000-2z"
        clipRule="evenodd"
      ></path>
    </svg>
  );
}

export function MessageBoxIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="currentColor"
      viewBox="0 0 20 20"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path d="M8.707 7.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l2-2a1 1 0 00-1.414-1.414L11 7.586V3a1 1 0 10-2 0v4.586l-.293-.293z"></path>
      <path d="M3 5a2 2 0 012-2h1a1 1 0 010 2H5v7h2l1 2h4l1-2h2V5h-1a1 1 0 110-2h1a2 2 0 012 2v10a2 2 0 01-2 2H5a2 2 0 01-2-2V5z"></path>
    </svg>
  );
}

export function LockIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="currentColor"
      viewBox="0 0 20 20"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        fillRule="evenodd"
        d="M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z"
        clipRule="evenodd"
      ></path>
    </svg>
  );
}

export function CheckListIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="currentColor"
      viewBox="2 2 16 16"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path d="M9 2a1 1 0 000 2h2a1 1 0 100-2H9z"></path>
      <path
        fillRule="evenodd"
        d="M4 5a2 2 0 012-2 3 3 0 003 3h2a3 3 0 003-3 2 2 0 012 2v11a2 2 0 01-2 2H6a2 2 0 01-2-2V5zm3 4a1 1 0 000 2h.01a1 1 0 100-2H7zm3 0a1 1 0 000 2h3a1 1 0 100-2h-3zm-3 4a1 1 0 100 2h.01a1 1 0 100-2H7zm3 0a1 1 0 100 2h3a1 1 0 100-2h-3z"
        clipRule="evenodd"
      ></path>
    </svg>
  );
}

export function FoldersIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="currentColor"
      viewBox="0 0 20 20"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path d="M7 3a1 1 0 000 2h6a1 1 0 100-2H7zM4 7a1 1 0 011-1h10a1 1 0 110 2H5a1 1 0 01-1-1zM2 11a2 2 0 012-2h12a2 2 0 012 2v4a2 2 0 01-2 2H4a2 2 0 01-2-2v-4z"></path>
    </svg>
  );
}

export function WheelIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="currentColor"
      viewBox="0 0 20 20"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        fillRule="evenodd"
        d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-2 0c0 .993-.241 1.929-.668 2.754l-1.524-1.525a3.997 3.997 0 00.078-2.183l1.562-1.562C15.802 8.249 16 9.1 16 10zm-5.165 3.913l1.58 1.58A5.98 5.98 0 0110 16a5.976 5.976 0 01-2.516-.552l1.562-1.562a4.006 4.006 0 001.789.027zm-4.677-2.796a4.002 4.002 0 01-.041-2.08l-.08.08-1.53-1.533A5.98 5.98 0 004 10c0 .954.223 1.856.619 2.657l1.54-1.54zm1.088-6.45A5.974 5.974 0 0110 4c.954 0 1.856.223 2.657.619l-1.54 1.54a4.002 4.002 0 00-2.346.033L7.246 4.668zM12 10a2 2 0 11-4 0 2 2 0 014 0z"
        clipRule="evenodd"
      ></path>
    </svg>
  );
}

export function GroupIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="currentColor"
      viewBox="0 0 20 20"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        fillRule="evenodd"
        d="M13 6a3 3 0 11-6 0 3 3 0 016 0zM18 8a2 2 0 11-4 0 2 2 0 014 0zM14 15a4 4 0 00-8 0v3h8v-3zM6 8a2 2 0 11-4 0 2 2 0 014 0zM16 18v-3a5.972 5.972 0 00-.75-2.906A3.005 3.005 0 0119 15v3h-3zM4.75 12.094A5.973 5.973 0 004 15v3H1v-3a3 3 0 013.75-2.906z"
        clipRule="evenodd"
      ></path>
    </svg>
  );
}

export function PenIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="currentColor"
      viewBox="-2 -1 22 22"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        fillRule="evenodd"
        d="m13.835 7.578-.005.007-7.137 7.137 2.139 2.138 7.143-7.142-2.14-2.14Zm-10.696 3.59 2.139 2.14 7.138-7.137.007-.005-2.141-2.141-7.143 7.143Zm1.433 4.261L2 12.852.051 18.684a1 1 0 0 0 1.265 1.264L7.147 18l-2.575-2.571Zm14.249-14.25a4.03 4.03 0 0 0-5.693 0L11.7 2.611 17.389 8.3l1.432-1.432a4.029 4.029 0 0 0 0-5.689Z"
        clipRule="evenodd"
      ></path>
    </svg>
  );
}

export function HelpIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="currentColor"
      viewBox="0 0 20 20"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        fillRule="evenodd"
        d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-8-3a1 1 0 00-.867.5 1 1 0 11-1.731-1A3 3 0 0113 8a3.001 3.001 0 01-2 2.83V11a1 1 0 11-2 0v-1a1 1 0 011-1 1 1 0 100-2zm0 8a1 1 0 100-2 1 1 0 000 2z"
        clipRule="evenodd"
      ></path>
    </svg>
  );
}

export function TeacherIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="currentColor"
      viewBox="0 0 640 512"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path d="M208 352c-2.39 0-4.78.35-7.06 1.09C187.98 357.3 174.35 360 160 360c-14.35 0-27.98-2.7-40.95-6.91-2.28-.74-4.66-1.09-7.05-1.09C49.94 352-.33 402.48 0 464.62.14 490.88 21.73 512 48 512h224c26.27 0 47.86-21.12 48-47.38.33-62.14-49.94-112.62-112-112.62zm-48-32c53.02 0 96-42.98 96-96s-42.98-96-96-96-96 42.98-96 96 42.98 96 96 96zM592 0H208c-26.47 0-48 22.25-48 49.59V96c23.42 0 45.1 6.78 64 17.8V64h352v288h-64v-64H384v64h-76.24c19.1 16.69 33.12 38.73 39.69 64H592c26.47 0 48-22.25 48-49.59V49.59C640 22.25 618.47 0 592 0z" />
    </svg>
  );
}

export function GraduateIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="currentColor"
      viewBox="0 0 448 512"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path d="M319.4 320.6L224 416l-95.4-95.4C57.1 323.7 0 382.2 0 454.4v9.6c0 26.5 21.5 48 48 48h352c26.5 0 48-21.5 48-48v-9.6c0-72.2-57.1-130.7-128.6-133.8zM13.6 79.8l6.4 1.5v58.4c-7 4.2-12 11.5-12 20.3 0 8.4 4.6 15.4 11.1 19.7L3.5 242c-1.7 6.9 2.1 14 7.6 14h41.8c5.5 0 9.3-7.1 7.6-14l-15.6-62.3C51.4 175.4 56 168.4 56 160c0-8.8-5-16.1-12-20.3V87.1l66 15.9c-8.6 17.2-14 36.4-14 57 0 70.7 57.3 128 128 128s128-57.3 128-128c0-20.6-5.3-39.8-14-57l96.3-23.2c18.2-4.4 18.2-27.1 0-31.5l-190.4-46c-13-3.1-26.7-3.1-39.7 0L13.6 48.2c-18.1 4.4-18.1 27.2 0 31.6z" />
    </svg>
  );
}

export function SearchIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="none"
      viewBox="0 0 20 20"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        stroke="currentColor"
        strokeLinecap="round"
        strokeLinejoin="round"
        strokeWidth="2"
        d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"
      />
    </svg>
  );
}

export function WorldIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="none"
      stroke="currentColor"
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      viewBox="1 1 22 22"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path />
      <path stroke="none" d="M0 0h24v24H0z" fill="none" />
      <path d="M3 12a9 9 0 1 0 18 0a9 9 0 0 0 -18 0" />
      <path d="M3.6 9h16.8" />
      <path d="M3.6 15h16.8" />
      <path d="M11.5 3a17 17 0 0 0 0 18" />
      <path d="M12.5 3a17 17 0 0 1 0 18" />
    </svg>
  );
}

export function WorldSearchIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="none"
      stroke="currentColor"
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      viewBox="1 1 22 22"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
      <path d="M21 12a9 9 0 1 0 -9 9"></path>
      <path d="M3.6 9h16.8"></path>
      <path d="M3.6 15h7.9"></path>
      <path d="M11.5 3a17 17 0 0 0 0 18"></path>
      <path d="M12.5 3a16.984 16.984 0 0 1 2.574 8.62"></path>
      <path d="M18 18m-3 0a3 3 0 1 0 6 0a3 3 0 1 0 -6 0"></path>
      <path d="M20.2 20.2l1.8 1.8"></path>
    </svg>
  );
}

export function GarbageIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="none"
      stroke="currentColor"
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      viewBox="1 1 22 22"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
      <path d="M4 7l16 0"></path>
      <path d="M10 11l0 6"></path>
      <path d="M14 11l0 6"></path>
      <path d="M5 7l1 12a2 2 0 0 0 2 2h8a2 2 0 0 0 2 -2l1 -12"></path>
      <path d="M9 7v-3a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v3"></path>
    </svg>
  );
}

export function EyeIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="none"
      stroke="currentColor"
      strokeWidth="2"
      viewBox="1 1 22 22"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path d="M2.036 12.322a1.012 1.012 0 010-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178z"></path>
      <path d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path>
    </svg>
  );
}

export function EyeSlashIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
      fill="none"
      stroke="currentColor"
      strokeWidth="2"
      viewBox="1 1 22 22"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path d="M3.98 8.223A10.477 10.477 0 001.934 12C3.226 16.338 7.244 19.5 12 19.5c.993 0 1.953-.138 2.863-.395M6.228 6.228A10.45 10.45 0 0112 4.5c4.756 0 8.773 3.162 10.065 7.498a10.523 10.523 0 01-4.293 5.774M6.228 6.228L3 3m3.228 3.228l3.65 3.65m7.894 7.894L21 21m-3.228-3.228l-3.65-3.65m0 0a3 3 0 10-4.243-4.243m4.242 4.242L9.88 9.88"></path>
    </svg>
  );
}

export function LinkIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
      ${style ? style : ""}
      ${size ? size : "size-6"}`}
      fill="none"
      stroke="currentColor"
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      viewBox="1 1 22 22"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
      <path d="M9 15l6 -6"></path>
      <path d="M11 6l.463 -.536a5 5 0 0 1 7.071 7.072l-.534 .464"></path>
      <path d="M13 18l-.397 .534a5.068 5.068 0 0 1 -7.127 0a4.972 4.972 0 0 1 0 -7.071l.524 -.463"></path>
    </svg>
  );
}

export function SchoolIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
      ${style ? style : ""}
      ${size ? size : "size-6"}`}
      fill="none"
      stroke="currentColor"
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      viewBox="1 1 22 22"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path d="M6 11.4999H7M6 15.4999H7M17 15.4999H18M17 11.4999H18M11.5 11.4999H12.5M10 20.9999V16.9999C10 15.8954 10.8954 14.9999 12 14.9999C13.1046 14.9999 14 15.8954 14 16.9999V20.9999M17 7.49995L18.5761 7.89398C19.4428 8.11064 19.8761 8.21898 20.1988 8.46057C20.4834 8.67373 20.7061 8.95895 20.8439 9.28682C21 9.65843 21 10.1051 21 10.9984V17.7999C21 18.9201 21 19.4801 20.782 19.9079C20.5903 20.2843 20.2843 20.5902 19.908 20.782C19.4802 20.9999 18.9201 20.9999 17.8 20.9999H6.2C5.0799 20.9999 4.51984 20.9999 4.09202 20.782C3.71569 20.5902 3.40973 20.2843 3.21799 19.9079C3 19.4801 3 18.9201 3 17.7999V10.9984C3 10.1051 3 9.65843 3.15613 9.28682C3.29388 8.95895 3.51657 8.67373 3.80124 8.46057C4.12389 8.21898 4.55722 8.11064 5.42388 7.89398L7 7.49995L9.85931 4.92657C10.6159 4.2456 10.9943 3.90512 11.4221 3.77598C11.799 3.66224 12.201 3.66224 12.5779 3.77598C13.0057 3.90512 13.3841 4.2456 14.1407 4.92657L17 7.49995Z"></path>
    </svg>
  );
}

export function CheckedListIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
      ${style ? style : ""}
      ${size ? size : "size-6"}`}
      fill="none"
      stroke="currentColor"
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="2"
      viewBox="1 1 22 22"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path d="M11 6L21 6.00072M11 12L21 12.0007M11 18L21 18.0007M3 11.9444L4.53846 13.5L8 10M3 5.94444L4.53846 7.5L8 4M4.5 18H4.51M5 18C5 18.2761 4.77614 18.5 4.5 18.5C4.22386 18.5 4 18.2761 4 18C4 17.7239 4.22386 17.5 4.5 17.5C4.77614 17.5 5 17.7239 5 18Z"></path>
    </svg>
  );
}

export function CheckboxIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
      ${style ? style : ""}
      ${size ? size : "size-6"}`}
      fill="none"
      stroke="currentColor"
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="1"
      viewBox="0 0 16 16"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path d="M3 14.5A1.5 1.5 0 0 1 1.5 13V3A1.5 1.5 0 0 1 3 1.5h8a.5.5 0 0 1 0 1H3a.5.5 0 0 0-.5.5v10a.5.5 0 0 0 .5.5h10a.5.5 0 0 0 .5-.5V8a.5.5 0 0 1 1 0v5a1.5 1.5 0 0 1-1.5 1.5H3z" />
      <path d="m8.354 10.354 7-7a.5.5 0 0 0-.708-.708L8 9.293 5.354 6.646a.5.5 0 1 0-.708.708l3 3a.5.5 0 0 0 .708 0z" />
    </svg>
  );
}

export function TextIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
      ${style ? style : ""}
      ${size ? size : "size-6"}`}
      fill="none"
      stroke="currentColor"
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="1"
      viewBox="0 0 16 16"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path d="M12.258 3h-8.51l-.083 2.46h.479c.26-1.544.758-1.783 2.693-1.845l.424-.013v7.827c0 .663-.144.82-1.3.923v.52h4.082v-.52c-1.162-.103-1.306-.26-1.306-.923V3.602l.431.013c1.934.062 2.434.301 2.693 1.846h.479L12.258 3z" />
    </svg>
  );
}

export function CodeIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
      ${style ? style : ""}
      ${size ? size : "size-6"}`}
      fill="none"
      stroke="currentColor"
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="1"
      viewBox="0 0 16 16"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path d="M10.478 1.647a.5.5 0 1 0-.956-.294l-4 13a.5.5 0 0 0 .956.294l4-13zM4.854 4.146a.5.5 0 0 1 0 .708L1.707 8l3.147 3.146a.5.5 0 0 1-.708.708l-3.5-3.5a.5.5 0 0 1 0-.708l3.5-3.5a.5.5 0 0 1 .708 0zm6.292 0a.5.5 0 0 0 0 .708L14.293 8l-3.147 3.146a.5.5 0 0 0 .708.708l3.5-3.5a.5.5 0 0 0 0-.708l-3.5-3.5a.5.5 0 0 0-.708 0z" />
    </svg>
  );
}

export function InputIcon({ style, size }: IconProps) {
  return (
    <svg
      aria-hidden="true"
      className={`icon 
      ${style ? style : ""}
      ${size ? size : "size-6"}`}
      fill="none"
      stroke="currentColor"
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth="1"
      viewBox="0 0 16 16"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path d="M10 5h4a1 1 0 0 1 1 1v4a1 1 0 0 1-1 1h-4v1h4a2 2 0 0 0 2-2V6a2 2 0 0 0-2-2h-4v1zM6 5V4H2a2 2 0 0 0-2 2v4a2 2 0 0 0 2 2h4v-1H2a1 1 0 0 1-1-1V6a1 1 0 0 1 1-1h4z" />
      <path
        fillRule="evenodd"
        d="M8 1a.5.5 0 0 1 .5.5v13a.5.5 0 0 1-1 0v-13A.5.5 0 0 1 8 1z"
      />
    </svg>
  );
}

export function SunIcon({ style, size }: IconProps) {
  return (
    <>
      <svg
        className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
        id="theme-toggle-light-icon"
        fill="currentColor"
        viewBox="0 0 20 20"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path d="M10 2a1 1 0 011 1v1a1 1 0 11-2 0V3a1 1 0 011-1zm4 8a4 4 0 11-8 0 4 4 0 018 0zm-.464 4.95l.707.707a1 1 0 001.414-1.414l-.707-.707a1 1 0 00-1.414 1.414zm2.12-10.607a1 1 0 010 1.414l-.706.707a1 1 0 11-1.414-1.414l.707-.707a1 1 0 011.414 0zM17 11a1 1 0 100-2h-1a1 1 0 100 2h1zm-7 4a1 1 0 011 1v1a1 1 0 11-2 0v-1a1 1 0 011-1zM5.05 6.464A1 1 0 106.465 5.05l-.708-.707a1 1 0 00-1.414 1.414l.707.707zm1.414 8.486l-.707.707a1 1 0 01-1.414-1.414l.707-.707a1 1 0 011.414 1.414zM4 11a1 1 0 100-2H3a1 1 0 000 2h1z"></path>
      </svg>
    </>
  );
}

export function MoonIcon({ style, size }: IconProps) {
  return (
    <>
      <svg
        className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-6"}`}
        id="theme-toggle-dark-icon"
        fill="currentColor"
        viewBox="0 0 20 20"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path d="M17.293 13.293A8 8 0 016.707 2.707a8.001 8.001 0 1010.586 10.586z"></path>
      </svg>
    </>
  );
}

export function DownloadIcon({ style, size }: IconProps) {
  return (
    <>
      <svg
        className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-4"}`}
        aria-hidden="true"
        xmlns="http://www.w3.org/2000/svg"
        fill="currentColor"
        viewBox="0 0 20 20"
      >
        <path d="M14.707 7.793a1 1 0 0 0-1.414 0L11 10.086V1.5a1 1 0 0 0-2 0v8.586L6.707 7.793a1 1 0 1 0-1.414 1.414l4 4a1 1 0 0 0 1.416 0l4-4a1 1 0 0 0-.002-1.414Z" />
        <path d="M18 12h-2.55l-2.975 2.975a3.5 3.5 0 0 1-4.95 0L4.55 12H2a2 2 0 0 0-2 2v4a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2v-4a2 2 0 0 0-2-2Zm-3 5a1 1 0 1 1 0-2 1 1 0 0 1 0 2Z" />
      </svg>
    </>
  );
}

export function FileUploadIcon({ style, size }: IconProps) {
  return (
    <>
      <svg
        className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-4"}`}
        aria-hidden="true"
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 12 20"
      >
        <path
          stroke="currentColor"
          strokeLinejoin="round"
          strokeWidth="2"
          d="M1 6v8a5 5 0 1 0 10 0V4.5a3.5 3.5 0 1 0-7 0V13a2 2 0 0 0 4 0V6"
        />
      </svg>
    </>
  );
}

export function ListIcon({ style, size }: IconProps) {
  return (
    <>
      <svg
        className={`icon 
        ${style ? style : ""}
        ${size ? size : "size-4"}`}
        aria-hidden="true"
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 21 18"
      >
        <path
          stroke="currentColor"
          strokeLinecap="round"
          strokeLinejoin="round"
          strokeWidth="2"
          d="M9.5 3h9.563M9.5 9h9.563M9.5 15h9.563M1.5 13a2 2 0 1 1 3.321 1.5L1.5 17h5m-5-15 2-1v6m-2 0h4"
        />
      </svg>
    </>
  );
}
