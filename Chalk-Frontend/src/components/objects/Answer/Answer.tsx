import {
  Exercise,
  ExerciseJustificationKind,
  ExerciseType,
} from "../Exercise/Exercise";
import { useState, useEffect } from "react";
import { TFAnswer } from "./TFAnswer";
import { OAAnswer } from "./OAAnswer";
import { MCAnswer } from "./MCAnswer";
//import { students } from "../../pages/Correction/example";
import { Student } from "../../pages/Correction/Correction";

interface StudentList {
  [key: string]: Student;
}

export const students: Student[] = [
  { id: "student1", email: "sdam@msam.com", name: "Luis caneca" },
  { id: "student2", email: "sdam@msam.com", name: "Maria caneca" },

  { id: "student16", email: "sdam@msam.com", name: "Goncalo caneca" },

  { id: "student3", email: "sdam@msam.com", name: "Alex caneca" },

  { id: "student4", email: "sdam@msam.com", name: "Rui caneca" },

  { id: "student5", email: "sdam@msam.com", name: "Hugo caneca" },

  { id: "student6", email: "sdam@msam.com", name: "Diogo caneca" },
  { id: "student12", email: "sdam@msam.com", name: "Manuel caneca" },
  { id: "student15", email: "sdam@msam.com", name: "Francisco caneca" },
  { id: "student13", email: "sdam@msam.com", name: "Bronze caneca" },
];

export function Answer({
  id,
  cotation,
  title,
  specialistId,
  statement,
  visibility,
  type,
  justifyKind,
  resolution,
  solution,
  comments,
}: Exercise) {
  const [preview, setPreview] = useState(<></>);

  const [selectedAnswer, setSelectedAnswer] = useState(false);
  const [studentList, setStudentList] = useState<{
    [key: string]: Student;
  }>({});

  useEffect(() => {
    let tempList: StudentList = {};
    students.forEach((student: Student) => (tempList[student.id] = student));
    setStudentList(tempList);
  }, [id]);

  console.log(studentList);

  useEffect(() => {
    switch (type) {
      case "multiple-choice":
        setPreview(
          <MCAnswer
            key={id}
            id={id}
            cotation={cotation}
            title={title}
            specialistId={specialistId}
            statement={statement}
            visibility={visibility}
            type={type}
            resolution={resolution}
            justifyKind={justifyKind}
            solution={solution}
            comments={comments}
          ></MCAnswer>
        );
        break;
      case "open-answer":
        setPreview(
          <OAAnswer
            key={id}
            id={id}
            cotation={cotation}
            title={title}
            specialistId={specialistId}
            statement={statement}
            visibility={visibility}
            type={type}
            resolution={resolution}
            justifyKind={justifyKind}
            solution={solution}
            comments={comments}
          ></OAAnswer>
        );
        break;
      case "true-or-false":
        setPreview(
          <TFAnswer
            key={id}
            id={id}
            cotation={cotation}
            title={title}
            specialistId={specialistId}
            statement={statement}
            visibility={visibility}
            type={type}
            resolution={resolution}
            justifyKind={justifyKind}
            solution={solution}
            comments={comments}
          ></TFAnswer>
        );
        break;
      case "fill-in-the-blank":
        setPreview(
          <></>
          // <FillBlankExercise
          //   statement={exercise.statement}
          //   problem={exercise.problem}
          //   contexto="solve"
          //   name={name}
          // ></FillBlankExercise>
        );
        break;
      case "code":
        setPreview(
          <></>
          // <CodeExercise
          //   statement={statement}
          //   problem={problem}
          //   contexto="solve"
          //   name={name}
          // ></CodeExercise>
        );
        break;
    }
  }, []);

  return (
    <div
      className={` ${
        selectedAnswer ? "max-h-full" : "max-h-[70px]"
      } transition-[max-height] bg-white text-black duration-300 rounded-lg mb-4`}
    >
      <div
        className=" items-center text-md font-medium cursor-pointer p-4"
        onClick={() =>
          selectedAnswer ? setSelectedAnswer(false) : setSelectedAnswer(true)
        }
      >
        {
          /* 
        {studentList[resolution!.studentID].name +
          " <" +
          studentList[resolution!.studentID].email +
          ">"} */
          resolution!.studentID
        }
        <div>email@hello.lo</div>
      </div>
      <div
        className={`${
          selectedAnswer ? "" : "scale-y-0"
        } flex flex-col px-4 rounded-lg ex-1 transition-transform duration-50`}
      >
        {preview}
      </div>
    </div>
  );
}
