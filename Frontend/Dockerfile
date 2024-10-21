# Frontend Dockerfile

FROM node:18-alpine AS build

WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install

COPY . .

RUN npm run build
RUN mkdir -p /usr/share/nginx/html && cp -r dist/* /usr/share/nginx/html/