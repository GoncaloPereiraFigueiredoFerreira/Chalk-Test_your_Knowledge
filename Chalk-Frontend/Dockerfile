FROM node:21
WORKDIR /chalk-fe
COPY ./package.json .
RUN npm install --silent 
CMD npm start -- --host 0.0.0.0
