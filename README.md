<h1 align="left">Vireya - Hydrocore Api SQL</h1>

###

<p align="left">Esse projeto foi desenvolvido por alunos do Instituto J&F, do curso Germinare Tech. Vireya é um projeto  que tem a proposta de simplificar e ajudar no gerenciamento de ETAs (Empresas de Tratamento de Água) e diminuir erros humanos. Trazendo soluções a problemas reais dentro de ETAs desde superdosagem ou subdosagem até o gerenciamento de estoque e geração de relatórios exigidos por lei e periódicos.</p>

###

<h2 align="left">📂 Estrutura do projeto</h2>

###

<p align="left">📦 hydrocore-api<br> ┣ 📂 src<br> ┃ ┣ 📂 main<br> ┃ ┣ 📂 test<br> ┣ 📂 .github<br> ┣ 📄 README.md<br> ┣ 📄 Dockerfile<br> ┣ 📄 pom.xml<br> ┣ 📄 application.yml<br> ┣ 📄 .env</p>

###

<h2 align="left">⚙️ Como configurar o projeto</h2>

###

<h4 align="left">Configurações necessárias para inicializar o projeto</h4>

###

<p align="left">- Java 17<br>- Git<br>- Docker</p>

###

<h4 align="left">Para inicializar o projeto</h4>

###

```bash
# Clone o projeto
$ git clone https://github.com/Vireya-Hydrocore/hydrocore-api.git

# Limpe e instale as dependências
$ mvn clean install

# Builde o container PostgreSQL com docker
$ docker-compose up --build

```

## Ao iniciar, ele rodará em
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)


###

<h2 align="left"> 📃 Documentação das apis de QA e PRD</h2>

| Documentação       | Links                                                                             |
| -----------------  | --------------------------------------------------------------------------------- |
| Hydrocore-Api-QA   | [Swagger QA](https://hydrocore-api-qa.onrender.com/swagger-ui/index.html#/)       |
| Hydrocore-Api-PRD  | [Swagger PROD](https://hydrocore-api-prod.onrender.com/swagger-ui/index.html#/)   |

###

<h2 align="left">✏️ Linguagens utilizadas</h2>

###

<div align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="40" alt="java logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" height="40" alt="spring logo"  />
  <img width="12" />
  <img src="https://cdn.simpleicons.org/docker/2496ED" height="40" alt="docker logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg" height="40" alt="postgresql logo"  />
</div>

### 

<h2 align="left"> 👤 Responsável por este repositório </h2>

###

- [@Leonardo Lins](https://github.com/leonardolinsz)

###

<p align="center">Este projeto está sob a licença <a href="https://opensource.org/licenses/MIT">MIT</a> – veja o arquivo LICENSE para detalhes.</p>
