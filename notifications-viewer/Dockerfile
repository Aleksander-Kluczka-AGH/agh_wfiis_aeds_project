FROM node:21.6-alpine

RUN mkdir -p /home/node/app/node_modules
RUN chown -R node:node /home/node/app

WORKDIR /home/node/app
USER node

COPY --chmod=777 --chown=node:node package*.json ./
RUN npm install

COPY --chown=node:node . .

EXPOSE 8080

CMD ["node", "index.js"]
