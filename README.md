# Bem vindo a Esse sistema

# Stack:
- Front End: Angular (Ng v19[css, standalone, router], PrimeNG v19, Tailwind)
- Back End: Spring Boot (Java 21, Spring Security, JWT, Lombok, Mapstruct, H2 DataBase, JPA/Hibernate)

# Objetivos/ Caracteristicas:
Usuário deve se logar ou cadastrar(/login, /signgup) e será movido para tela principal (path '/').
- A autenticacao é feita no momento do login ou cadastro (signup) setando no localstorage dados do profile do usuario e o token de acesso que dura 24 horas.
- Ao acessar o sistema ele irá pegar a data de 'agora' e setar no sistema (como um 'signal'). Essa data será usada pela lista de tarefas para buscar as tarefas daquele dia.
- O usuário na navbar superior pode selecionar uma nova data para o sistema que irá alterar a data da lista consequentimente. 
- O usuário pode adicionar uma tarefa nova (Add Task) que será disponibilizada para a lista da data setada no sistema. 
- O usuário ao clicar em uma tarefa pode efetuar algumas açoes como: alterar o status da tarefa (pending, in progress e completed) ou deletar a tarefa.
    
# Pendencias:
- Botao de filtro: ainda nao implementado 19/05/2025
- Botão de Ediçao (dentro do detalhe da tarefa): ainda nao implementado 19/05/2025
- Bug: Possivel bug na sincronia das datas pela diferenca de fuso horario entre servidor e front, observado as 21:00 (pois em iso 21:00 brt troca a data para o proximo dia)

# Dados do Sistema:
Default Login Account:
email: john@example.com
password: pass123

# Rodar o sistema:
Na pasta root do sistema efetue o comando no powershell ou bash, 
`$ docker-compose up`

Ao startar as imagens docker pode mos acessar o sistema nos endereços:
Front:
- Página: http://localhost:8000
- Rotas: /, /login, /signup

Back:
- API: http://localhost:8080/api/*
- H2 Console: http://localhost:8080/h2-console 
- Swagger: http://localhost:8080/swagger-ui/index.html