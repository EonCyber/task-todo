# === Build Angular App ===
FROM node:22.15.1-slim AS build
WORKDIR /app
COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build --prod --no-progress --verbose --force

# === Serve via NGINX ===
FROM nginx:alpine
#RUN rm -rf /usr/share/nginx/html/*
COPY --from=build /app/dist/ng-serasa-tasks/browser /usr/share/nginx/html
COPY /nginx.conf  /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
