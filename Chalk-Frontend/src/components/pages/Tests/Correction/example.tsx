import {
  ExerciseJustificationKind,
  ExerciseType,
  ResolutionStatus,
} from "../../../objects/Exercise/Exercise";
import { ImgPos } from "../../../objects/Exercise/Header/ExHeader";

export const students = [
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

export const userExercises = [
  {
    id: "11",
    title: "Multiple Choice Exercise",
    specialistId: "specialist1",
    visibility: "public",
    type: ExerciseType.MULTIPLE_CHOICE,
    statement: {
      text: "Select the correct option:",
    },
    justifyKind: ExerciseJustificationKind.JUSTIFY_MARKED,
    items: {
      option1: { text: "Option A", type: "text" },
      option2: { text: "Option B", type: "text" },
      option3: { text: "Option C", type: "text" },
    },
    solution: {
      id: "11",
      cotation: 5,
      studentID: "student1",
      status: ResolutionStatus.PENDING,
      data: {
        items: {
          option1: {
            text: "Option A",
            justification: "Chosen option A",
            value: true,
          },
        },
      },
    },
    resolutions: [
      {
        id: "11",
        cotation: 5,
        studentID: "student16",
        status: ResolutionStatus.PENDING,
        data: {
          items: {
            option1: {
              text: "Option A",
              justification: "Chosen option A",
              value: true,
            },
          },
        },
      },
    ],
    comments: ["Hello", "konichiwa"],
  },

  {
    id: "21",
    title: "Open Answer Exercise",
    specialistId: "specialist1",
    visibility: "public",
    type: ExerciseType.OPEN_ANSWER,
    statement: {
      text: "Provide a detailed answer",
    },
    solution: {
      id: "21",
      cotation: 10,
      studentID: "student1",
      status: ResolutionStatus.PENDING,
      data: { text: "This is the correct answer." },
    },
    resolutions: [
      {
        id: "21",
        cotation: 10,
        studentID: "student2",
        status: ResolutionStatus.PENDING,
        data: { text: "This is the student answer." },
      },
    ],
    comments: ["Hello", "konichiwa"],
  },

  {
    id: "31",
    title: "True or False Exercise",
    specialistId: "specialist1",
    visibility: "public",
    type: ExerciseType.TRUE_OR_FALSE,
    statement: {
      text: "Is this statement true or false?",
    },
    items: {
      optionA: { text: "Existem 9 canetas roxas ou vermelhas", type: "string" },
      optionB: {
        text: "Existem tantas canetas pretas ou roxas, quanto vermelhas",
        type: "string",
      },
      optionC: { text: "Existem 8 canetas pretas", type: "string" },
      optionD: {
        text: "Existem mais canetas castanhas que amarelas",
        type: "string",
      },
    },
    justifyKind: ExerciseJustificationKind.JUSTIFY_TRUE,
    solution: {
      id: "31",
      cotation: 5,
      studentID: "student1",
      status: ResolutionStatus.PENDING,
      data: {
        items: {
          optionA: {
            text: "True",
            justification: "Statement is true",
            value: true,
          },
          optionB: {
            text: "True",
            justification: "Statement is true",
            value: true,
          },
          optionC: {
            text: "False",
            justification: "Statement is false",
            value: false,
          },
        },
      },
    },
    resolutions: [
      {
        id: "31",
        cotation: 5,
        studentID: "student3",
        status: ResolutionStatus.PENDING,
        data: {
          items: {
            optionA: {
              text: "True",
              justification: "Statement is true",
              value: true,
            },
            optionB: {
              text: "True",
              justification: "Statement is true",
              value: true,
            },
            optionC: {
              text: "True",
              justification: "Statement is true",
              value: true,
            },
          },
        },
      },
    ],
    comments: ["Hello", "konichiwa"],
  },

  {
    id: "1",
    title: "Quantas canetas",
    visibility: "public",
    type: ExerciseType.OPEN_ANSWER,
    specialistId: "333",
    statement: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      imagePath:
        "https://static.vecteezy.com/ti/vetor-gratis/p3/8344304-aluno-no-quadro-negro-na-sala-de-aula-explica-a-solucao-do-problema-de-volta-a-escola-educacao-para-criancas-cartoon-ilustracao-vetor.jpg",
      imagePosition: ImgPos.RIGHT,
    },
    justifyKind: ExerciseJustificationKind.JUSTIFY_UNMARKED,
    solution: {
      id: "21",
      cotation: 10,
      studentID: "student1",
      status: ResolutionStatus.PENDING,
      data: { text: "This is the correct answer." },
    },
    resolutions: [
      {
        id: "21",
        cotation: 10,
        studentID: "student4",
        status: ResolutionStatus.PENDING,
        data: { text: "This is the student answer." },
      },
      {
        id: "213",
        cotation: 10,
        studentID: "student213",
        status: ResolutionStatus.PENDING,
        data: { text: "This is the student answer." },
      },
    ],
    comments: ["Hello", "konichiwa"],
  },

  {
    id: "2",
    title: "Quantas canetas",
    visibility: "public",
    type: ExerciseType.TRUE_OR_FALSE,
    specialistId: "333",
    statement: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      imagePath:
        "https://static.vecteezy.com/ti/vetor-gratis/p3/8344304-aluno-no-quadro-negro-na-sala-de-aula-explica-a-solucao-do-problema-de-volta-a-escola-educacao-para-criancas-cartoon-ilustracao-vetor.jpg",
      imagePosition: ImgPos.RIGHT,
    },
    justifyKind: ExerciseJustificationKind.JUSTIFY_ALL,
    items: {
      optionA: { text: "Existem 9 canetas roxas ou vermelhas", type: "string" },
      optionB: {
        text: "Existem tantas canetas pretas ou roxas, quanto vermelhas",
        type: "string",
      },
      optionC: { text: "Existem 8 canetas pretas", type: "string" },
      optionD: {
        text: "Existem mais canetas castanhas que amarelas",
        type: "string",
      },
    },
    solution: {
      id: "31",
      cotation: 5,
      studentID: "student1",
      status: ResolutionStatus.PENDING,
      data: {
        items: {
          optionA: {
            text: "True",
            justification: "Statement is true",
            value: true,
          },
          optionB: {
            text: "True",
            justification: "Statement is true",
            value: true,
          },
          optionC: {
            text: "False",
            justification: "Statement is false",
            value: false,
          },
        },
      },
    },
    resolutions: [
      {
        id: "31",
        cotation: 5,
        studentID: "student5",
        status: ResolutionStatus.PENDING,
        data: {
          items: {
            optionA: {
              text: "True",
              justification: "Statement is true",
              value: true,
            },
            optionB: {
              text: "True",
              justification: "Statement is true",
              value: true,
            },
            optionC: {
              text: "True",
              justification: "Statement is true",
              value: true,
            },
          },
        },
      },
    ],
    comments: ["Hello", "konichiwa"],
  },

  {
    id: "6",
    title: "Quantas canetas",
    visibility: "private",
    type: ExerciseType.TRUE_OR_FALSE,
    specialistId: "333",
    statement: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      imagePath:
        "https://static.vecteezy.com/ti/vetor-gratis/p3/8344304-aluno-no-quadro-negro-na-sala-de-aula-explica-a-solucao-do-problema-de-volta-a-escola-educacao-para-criancas-cartoon-ilustracao-vetor.jpg",
      imagePosition: ImgPos.RIGHT,
    },
    justifyKind: ExerciseJustificationKind.JUSTIFY_TRUE,
    items: {
      optionA: { text: "Existem 9 canetas roxas ou vermelhas", type: "string" },
      optionB: {
        text: "Existem tantas canetas pretas ou roxas, quanto vermelhas",
        type: "string",
      },
      optionC: { text: "Existem 8 canetas pretas", type: "string" },
      optionD: {
        text: "Existem mais canetas castanhas que amarelas",
        type: "string",
      },
    },
    solution: {
      id: "31",
      cotation: 5,
      studentID: "student1",
      status: ResolutionStatus.PENDING,
      data: {
        items: {
          optionA: {
            text: "True",
            justification: "Statement is true",
            value: true,
          },
          optionB: {
            text: "True",
            justification: "Statement is true",
            value: true,
          },
          optionC: {
            text: "False",
            justification: "Statement is false",
            value: false,
          },
        },
      },
    },
    resolutions: [
      {
        id: "31",
        cotation: 5,
        studentID: "student6",
        status: ResolutionStatus.PENDING,
        data: {
          items: {
            optionA: {
              text: "True",
              justification: "Statement is true",
              value: true,
            },
            optionB: {
              text: "True",
              justification: "Statement is true",
              value: true,
            },
            optionC: {
              text: "True",
              justification: "Statement is true",
              value: true,
            },
          },
        },
      },
    ],
    comments: ["Hello", "konichiwa"],
  },

  {
    id: "3",
    title: "Quantas canetas",
    visibility: "public",
    type: ExerciseType.TRUE_OR_FALSE,
    specialistId: "333",
    statement: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      imagePath:
        "https://static.vecteezy.com/ti/vetor-gratis/p3/8344304-aluno-no-quadro-negro-na-sala-de-aula-explica-a-solucao-do-problema-de-volta-a-escola-educacao-para-criancas-cartoon-ilustracao-vetor.jpg",
      imagePosition: ImgPos.BOT,
    },
    justifyKind: ExerciseJustificationKind.JUSTIFY_FALSE,
    items: {
      optionA: {
        text: "Existem 9 canetas roxas ou vermelhas",
        type: "string",
      },
      optionB: {
        text: "Existem tantas canetas pretas ou roxas, quanto vermelhas",
        type: "string",
      },
      optionC: {
        text: "Existem 8 canetas pretas",
        type: "string",
      },
      optionD: {
        text: "Existem mais canetas castanhas que amarelas",
        type: "string",
      },
    },
    solution: {
      id: "31",
      cotation: 5,
      studentID: "student11",
      status: ResolutionStatus.PENDING,
      data: {
        items: {
          optionA: {
            text: "True",
            justification: "Statement is true",
            value: true,
          },
          optionB: {
            text: "True",
            justification: "Statement is true",
            value: true,
          },
          optionC: {
            text: "False",
            justification: "Statement is false",
            value: false,
          },
        },
      },
    },
    resolutions: [
      {
        id: "51",
        cotation: 5,
        studentID: "student12",
        status: ResolutionStatus.PENDING,
        data: {
          items: {
            optionA: {
              text: "True",
              justification: "Statement is true",
              value: true,
            },
            optionB: {
              text: "True",
              justification: "Statement is true",
              value: true,
            },
            optionC: {
              text: "True",
              justification: "Statement is true",
              value: true,
            },
          },
        },
      },
    ],
    comments: ["Hello", "konichiwa"],
  },

  {
    id: "4",
    title: "Quantas canetas",
    visibility: "not-listed",
    type: ExerciseType.MULTIPLE_CHOICE,
    specialistId: "333",
    statement: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
    },
    justifyKind: ExerciseJustificationKind.NO_JUSTIFICATION,
    items: {
      optionA: { text: "Existem 9 canetas roxas ou vermelhas", type: "string" },
      optionB: {
        text: "Existem tantas canetas pretas ou roxas, quanto vermelhas",
        type: "string",
      },
      optionC: { text: "Existem 8 canetas pretas", type: "string" },
      optionD: {
        text: "Existem mais canetas castanhas que amarelas",
        type: "string",
      },
    },
    solution: {
      id: "41",
      cotation: 5,
      studentID: "student1",
      status: ResolutionStatus.PENDING,
      data: {
        items: {
          "1": {
            text: "True",
            justification: "Statement is true",
            value: true,
          },
        },
      },
    },
    resolutions: [
      {
        id: "41",
        cotation: 5,
        studentID: "student13",
        status: ResolutionStatus.PENDING,
        data: {
          items: {
            "1": {
              text: "True",
              justification: "Statement is true",
              value: false,
            },
            "2": {
              text: "True",
              justification: "Statement is true",
              value: true,
            },
            "3": {
              text: "True",
              justification: "Statement is true",
              value: false,
            },
          },
        },
      },
    ],
    comments: ["Hello", "konichiwa"],
  },

  {
    id: "7",
    title: "Quantas canetas",
    visibility: "institutional",
    type: ExerciseType.TRUE_OR_FALSE,
    specialistId: "333",
    statement: {
      text: "O Joao pegou em 29 canetas de 5 cores diferentes. Sabe-se que o numero de canetas amarelas é igual ao numero de canetas pretas, o numero de canetas roxas é metade do numero de canetas amarelas e que existem tres vezes mais canetas vermelhas do que roxas. Sabe-se ainda que existem 5 canetas castanhas",
      imagePath:
        "https://static.vecteezy.com/ti/vetor-gratis/p3/8344304-aluno-no-quadro-negro-na-sala-de-aula-explica-a-solucao-do-problema-de-volta-a-escola-educacao-para-criancas-cartoon-ilustracao-vetor.jpg",
      imagePosition: ImgPos.TOP,
    },
    justifyKind: ExerciseJustificationKind.NO_JUSTIFICATION,

    items: {
      optionA: {
        text: "Existem 9 canetas roxas ou vermelhas",
        type: "string",
      },
      optionB: {
        text: "Existem tantas canetas pretas ou roxas, quanto vermelhas",
        type: "string",
      },
      optionC: { text: "Existem 8 canetas pretas", type: "string" },
      optionD: {
        text: "Existem mais canetas castanhas que amarelas",
        type: "string",
      },
    },

    solution: {
      id: "31",
      cotation: 5,
      studentID: "student1",
      status: ResolutionStatus.PENDING,
      data: {
        items: {
          optionA: {
            text: "True",
            justification: "Statement is true",
            value: true,
          },
          optionB: {
            text: "True",
            justification: "Statement is true",
            value: true,
          },
          optionC: {
            text: "False",
            justification: "Statement is false",
            value: false,
          },
        },
      },
    },
    resolutions: [
      {
        id: "31",
        cotation: 5,
        studentID: "student15",
        status: ResolutionStatus.PENDING,
        data: {
          items: {
            optionA: {
              text: "True",
              justification: "Statement is true",
              value: true,
            },
            optionB: {
              text: "True",
              justification: "Statement is true",
              value: true,
            },
            optionC: {
              text: "True",
              justification: "Statement is true",
              value: true,
            },
          },
        },
      },
    ],
    comments: ["Hello", "konichiwa"],
  },
];
