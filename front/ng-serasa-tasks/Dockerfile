# Build the Angular app
FROM node:22.15.1-slim AS build
WORKDIR /app
COPY front/ng-serasa-tasks/package*.json ./

RUN npm install

COPY front/ng-serasa-tasks ./

RUN npm run build --configuration=production

# Serve via NGINX
FROM nginx:alpine

COPY --from=build /app/dist/ng-serasa-tasks /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]