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

Caso encontre problemas para subir o docker compose, tente recriar os containers

````bash
docker compose up -d --force-recreate
````

Se isso não funcionar, execute o comando abaixo para remover a imagem e em seguida execute o comando acima para fazer o pull novamente.

````bash
docker rmi smartmes-manufacturing
````

## Como testar a aplicação

Chamando o endpoint /hello para validar que a aplicação está UP e conectada ao banco de dados

````bash
curl http://localhost:8080/smartmes/hello
````