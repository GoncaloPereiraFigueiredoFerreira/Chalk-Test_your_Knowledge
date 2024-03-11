![](./report_imgs/chalk-tyk.png)

Final Grade: 18/20  
Metainfo: Project developed by Software Engineering Students at Universidade do Minho, as part of the course "Projeto de Informática", which required students to develop a startup project. The code here available was part of this project, which also involved the creation of a complex business case, a thorough finacial evaluation, and multiple pitches and presentations.


## Pitch

Chalk - Test your Knowledge presents itself as a platform for sharing educational exercises, which facilitates test creation and self-assessment of knowledge, for both teachers and students alike. At the core of this platform will be a focus on innovation, through the integration of artificial intelligence, to assist in generating exercises and their corrections, and the inclusion of new question types.


## Main Features

- Creation and distribution of tests
- Creation of multiple types of tradicional exercises: open-anwser, multiple-choice, true-and-false
- __*New*__ Chat-based questions (students interact with an AI assistant in their tests, simmilar to an oral exam)
- Test correction and scoring
- Automatic Corrections for multiple-choice questions
- AI assisted correction for subjective questions
- Course creation and administration
- Question bank administration


## Technologies and Architecture

![](./report_imgs/Arquitetura.png)

The main technologies used behind this project were:
- __React__ (frontend library)
- __NGINX__ (as a reverse proxy)
- __Spring__ (backend code)
- __Python__ (AI api usage)
- __PostGres__ (backend DB)
- __MongoDB__ (authentication DB)
- __JWT__ (authentication tokens)
- __Docker__ (assembling of all the components and easier deployment of the service)


## Demonstration  

We'll now present some printscreens from our application running (note: these prints are in our native language, portuguese)

### Landing Page  
![](./report_imgs/main.png)
![](./report_imgs/team.png)

### Dashboard and Exercise Bank  
![](./report_imgs/dashboard.png)
![](./report_imgs/darkmode.png)
![](./report_imgs/ExBankCreate.png)

### Test related pages
![](./report_imgs/Test.png)
![](./report_imgs/TestBank.png)
![](./report_imgs/TestRes.png)
![](./report_imgs/TestCorr.png)
![](./report_imgs/GroupTests2.png)


## How to run ?

Simply make sure you have docker installed and run, at the root directory  

```
docker compose up
```
  
The application will run on:  http://localhost:7777/




