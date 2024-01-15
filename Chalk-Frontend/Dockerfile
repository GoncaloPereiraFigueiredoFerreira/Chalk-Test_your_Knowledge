FROM node:21
WORKDIR /chalk-fe
COPY ./package.json .
COPY .eslintrc.cjs .
COPY index.html .
COPY postcss.config.js .
COPY public ./public
COPY src ./src
COPY tailwind.config.js .
COPY tsconfig.json .
COPY vite.config.ts .
COPY tsconfig.node.json .
RUN npm install --silent 
CMD npm start -- --host 0.0.0.0
