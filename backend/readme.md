[JAVA_BADGE]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[SPRING_BOOT_BADGE]: https://img.shields.io/badge/Spring%20Boot-6DB33F.svg?style=for-the-badge&logo=Spring-Boot&logoColor=white
[SPRING_SECURITY_BADGE]: https://img.shields.io/badge/Spring%20Security-6DB33F.svg?style=for-the-badge&logo=Spring-Security&logoColor=white
[SQLSERVER_BADGE]: https://img.shields.io/badge/Microsoft%20SQL%20Server-CC2927?style=for-the-badge&logo=microsoft%20sql%20server&logoColor=white
[SWAGGER_BADGE]: https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white
[DOCKER_BADGE]: https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white


<h1 align="center" style="font-weight: bold;">DSCommerce 🏬💻</h1>


<div style="text-align: center;">

![java][JAVA_BADGE]
![spring boot][SPRING_BOOT_BADGE]
![spring security][SPRING_SECURITY_BADGE]
![Docker][DOCKER_BADGE]
![Swagger][SWAGGER_BADGE]
![MicrosoftSQL Server][SQLSERVER_BADGE]

</div>


<p align="center">
 <a href="#tech">Tecnologias</a> • 
 <a href="#started">Getting Started</a> • 
  <a href="#routes">Endpoints da API</a> •
 <a href="#colab">Colaboradores</a> •
</p>

<p align="center">
    <b>Projeto criado durante o curso Spring Specialist da instituição DevSuperior do professor Nélio Alves.</b>
</p>

<h2 id="tech">💻 Tecnologias</h2>

Este projeto utiliza as seguintes tecnologias e frameworks:

- **Java 21**: Linguagem de programação para o desenvolvimento backend.
- **Spring Framework**: Framework para criação de aplicações Java robustas e escaláveis.
- **Spring Security**: Módulo do Spring para segurança e controle de acesso.
- **Spring Data JPA**: Abstração de persistência de dados baseada no JPA.
- **Flyway**: Ferramenta de versionamento e migração de banco de dados.
- **Microsoft SQL Server**: Banco de dados relacional utilizado na aplicação.
- **Docker**: Ferramenta para criação e gerenciamento de containers.
- **Swagger**: Ferramenta para documentação e teste de APIs.

<h2 id="started">🚀 Getting started</h2>

<h3>Pré-requisitos</h3>

Antes de começar, você precisará ter os seguintes softwares instalados em sua máquina:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Java 21](https://www.oracle.com/br/java/technologies/downloads/#java21)
- [Git](https://git-scm.com/)

<h3>Clone</h3>

Para clonar o repositório do projeto, execute o seguinte comando no terminal:

```bash
git clone https://github.com/RafaelJaber/DsCommerce.git
```

<h3>Iniciando o Projeto</h3>

Navegue até o diretório do projeto e suba os containers Docker:

```bash
cd dscommerce
docker-compose up -d
```

<h3>Carregando o Banco de Dados</h3>

Localize o arquivo de seed na pasta: 'src/main/resources/db/seed.sql'.
Execute os scripts de insert no banco de dados para popular as tabelas iniciais.

<h3>Rodando a Aplicação</h3>

Para iniciar a aplicação, você pode utilizar uma IDE ou seguir os passos abaixo para compilar e rodar via Maven:

```bash
./mvnw deploy
```

```bash
java -jar target/dscommerce-0.0.1-SNAPSHOT.jar
```

<h2 id="routes">📍 Endpoints da API</h2>

| Rota                                 | Descrição                                                      |
|--------------------------------------|----------------------------------------------------------------|
| <kbd>GET /users/me</kbd>             | Buscar informações do usuário logado                           |
| <kbd>POST /oauth2/token</kbd>        | Realizar o login na aplicação                                  |
| <kbd>GET /products</kbd>             | Buscar produtos - Sem Autenticação                             |
| <kbd>GET /products/{productId}</kbd> | Buscar produto por id - Sem Autenticação                       |
| <kbd>POST /products</kbd>            | Cadastrar produto - Necessário permissão admin                 |
| <kbd>PUT /products/{productId}</kbd> | Atualizar produto informando o id - Necessário permissão admin |
| <kbd>DEL /products/{productId}</kbd> | Deletar produto informando o id - Necessário permissão admin   |
| <kbd>GET /orders/{orderId}</kbd>     | Buscar pedidos - Admin ou o seu pedido                         |
| <kbd>POST /orders</kbd>              | Cadastrar pedidos - Necessário permissão client                |
| <kbd>GET /users/me</kbd>             | Busca seus dados de perfil - Necessário estar autenticado      |
| <kbd>GET /categories</kbd>           | Buscar categorias - Sem Autenticação                           |


<h2 id="colab">🤝 Colaboradores</h2>

<table>
  <tr>
    <td align="center">
      <a href="#">
        <img src="https://github.com/rafaeljaber.png" width="100px;" alt="Fernanda Kipper Profile Picture"/><br>
        <sub>
          <b>Rafael Jáber</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="#">
        <img src="https://github.com/acenelio.png" width="100px;" alt="Foto Nélio Alves"/><br>
        <sub>
          <b>Nélio Alves</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="#">
        <img src="https://github.com/devsuperior.png" width="100px;" alt="Foto DevSuperior"/><br>
        <sub>
          <b>DevSuperior</b>
        </sub>
      </a>
    </td>
  </tr>
</table>