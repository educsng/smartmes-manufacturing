# SmartMES :: Manufacturing :: API

API responsável por registrar ordens de produção

## Como rodar a aplicação

### Pré-requisitos

- docker

### Docker-compose

Para rodar a aplicação, basta executar o comando abaixo para subir um docker compose com a aplicação buildada juntamente com um banco de dados MySQL

````bash
docker compose up -d
````

## Como testar a aplicação

Chamando o endpoint /hello para validar que a aplicação está de pé e conectada ao banco de dados

````curl
curl http://localhost:8080/manufacturing/hello
````