# TaskFlow API

Uma API REST para gerenciamento de tarefas construída com Spring Boot, que oferece funcionalidades de autenticação JWT e operações CRUD completas para tasks.

## 🚀 Sobre o Projeto

TaskFlow é uma API REST moderna para gerenciamento de tarefas que permite:

- ✅ Cadastro e autenticação de usuários
- ✅ Criação, edição, listagem e exclusão de tarefas
- ✅ Sistema de autenticação JWT
- ✅ Paginação de resultados
- ✅ Estatísticas de tarefas por usuário
- ✅ Validação de dados
- ✅ Segurança com Spring Security

## 🛠 Tecnologias

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Autenticação e autorização
- **Spring Validation** - Validação de dados
- **PostgreSQL** - Banco de dados
- **Flyway** - Migração de banco de dados
- **JWT (java-jwt)** - Tokens de autenticação
- **Lombok** - Redução de código boilerplate
- **SpringDoc OpenAPI** - Documentação da API
- **Maven** - Gerenciador de dependências

## 📋 Pré-requisitos

Antes de executar o projeto, certifique-se de ter:

- [Java JDK 21](https://openjdk.org/projects/jdk/21/) ou superior
- [PostgreSQL](https://www.postgresql.org/) instalado e rodando
- [Maven 3.8+](https://maven.apache.org/) (ou use o wrapper incluído)
- Git

## 🔧 Instalação

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/TaskFlow_api.git
   cd TaskFlow_api
   ```

2. **Configure o banco de dados PostgreSQL:**
   - Certifique-se que o postgreSQL esteja rodando na porta 5432
   - Crie uma database postgres o flyway cuidará do resto

3. **Instale as dependências:**
   ```bash
   ./mvnw clean install
   ```
   
   Ou no Windows:
   ```cmd
   mvnw.cmd clean install
   ```

## ⚙️ Configuração

O arquivo de configuração está localizado em `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: postgres
  jpa:
    show-sql: true
    properties:
      hibernate.default_schema: taskflow_db
  flyway:
    enabled: true
    schemas: taskflow_db
    table: flyway_schema_history

api:
  security:
    token:
      secret: ${JWT_SECRET:MY_SECRET_KEY}
```

### Variáveis de Ambiente

| Variável | Descrição | Valor Padrão |
|----------|-----------|-------------|
| `JWT_SECRET` | Chave secreta para assinatura dos tokens JWT | `MY_SECRET_KEY` |

## 🚀 Como Executar

1. **Execute a aplicação:**
   ```bash
   ./mvnw spring-boot:run
   ```
   
   Ou no Windows:
   ```cmd
   mvnw.cmd spring-boot:run
   ```

2. **A API estará disponível em:** `http://localhost:8080`

3. **Documentação Swagger:** `http://localhost:8080/swagger-ui.html`

## 📚 API Endpoints

### Autenticação

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|-------------|
| POST | `/auth/register` | Cadastrar novo usuário | ❌ |
| POST | `/auth/login` | Fazer login | ❌ |

### Tarefas

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|-------------|
| POST | `/task` | Criar nova tarefa | ✅ |
| GET | `/task/list` | Listar tarefas do usuário | ✅ |
| GET | `/task/{id}` | Buscar tarefa por ID | ✅ |
| PUT | `/task` | Atualizar tarefa | ✅ |
| DELETE | `/task/{id}` | Excluir tarefa | ✅ |
| GET | `/task/stats` | Estatísticas das tarefas | ✅ |

### Parâmetros de Query

- **GET /task/list**:
  - `completed` (opcional): `true` para tarefas concluídas, `false` para pendentes
  - `page` (opcional): Número da página (padrão: 0)
  - `size` (opcional): Tamanho da página (padrão: 10)
  - `sort` (opcional): Campo para ordenação (padrão: id)

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/sousaarthur/TaskFlow/
│   │   ├── Application.java                 # Classe principal
│   │   ├── controller/
│   │   │   ├── AuthenticationController.java # Endpoints de autenticação
│   │   │   └── TaskController.java          # Endpoints de tarefas
│   │   ├── domain/
│   │   │   ├── task/
│   │   │   │   ├── Task.java                # Entidade Task
│   │   │   │   └── TaskRepository.java      # Repository JPA
│   │   │   └── user/
│   │   │       ├── User.java                # Entidade User
│   │   │       ├── UserRepository.java     # Repository JPA
│   │   │       └── UserRole.java           # Enum de roles
│   │   ├── dto/
│   │   │   ├── AuthenticationDTO.java       # DTO de login
│   │   │   ├── LoginResponseDTO.java        # DTO de resposta do login
│   │   │   ├── RegisterDTO.java             # DTO de registro
│   │   │   ├── TaskDTO.java                 # DTO de tarefa
│   │   │   └── TaskStatsDTO.java            # DTO de estatísticas
│   │   ├── infra/
│   │   │   ├── CorsConfig.java              # Configuração CORS
│   │   │   └── security/
│   │   │       ├── SecurityConfigurations.java # Configuração do Spring Security
│   │   │       ├── SecurityFilter.java      # Filtro JWT
│   │   │       └── TokenService.java        # Serviço de tokens JWT
│   │   └── service/
│   │       └── AuthorizationService.java    # Serviço de autorização
│   └── resources/
│       └── application.yml                  # Configurações da aplicação
└── test/
    └── java/com/sousaarthur/TaskFlow/
        └── TaskFlowApplicationTests.java    # Testes da aplicação
```

## 🗄️ Banco de Dados

### Modelo de Dados

#### Tabela: users
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | VARCHAR (UUID) | Identificador único |
| login | VARCHAR | Nome de usuário |
| password | VARCHAR | Senha criptografada |
| role | VARCHAR | Papel do usuário (USER/ADMIN) |

#### Tabela: task
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | BIGINT | Identificador único |
| title | VARCHAR | Título da tarefa |
| description | TEXT | Descrição da tarefa |
| completed | BOOLEAN | Status de conclusão |
| created_at | TIMESTAMP | Data de criação |
| user_id | VARCHAR | ID do usuário (FK) |

### Migração com Flyway

As migrações do banco são gerenciadas pelo Flyway. Os scripts estão localizados em `src/main/resources/db/migration/`.

## 🔐 Autenticação

A API utiliza **JWT (JSON Web Token)** para autenticação:

1. **Registro**: Crie uma conta via `POST /auth/register`
2. **Login**: Faça login via `POST /auth/login` e receba um token JWT
3. **Autorização**: Inclua o token no header: `Authorization: Bearer <token>`

### Exemplo de Token JWT

```json
{
  "sub": "usuario123",
  "iat": 1640995200,
  "exp": 1641081600
}
```

## 💡 Exemplos de Uso

### 1. Registrar um usuário

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "login": "usuario",
    "password": "senha123"
  }'
```

### 2. Fazer login

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "login": "usuario",
    "password": "senha123"
  }'
```

### 3. Criar uma tarefa

```bash
curl -X POST http://localhost:8080/task \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN" \
  -d '{
    "title": "Estudar Spring Boot",
    "description": "Aprender sobre controllers e repositories",
    "completed": false
  }'
```

### 4. Listar tarefas

```bash
curl -X GET "http://localhost:8080/task/list?page=0&size=10" \
  -H "Authorization: Bearer SEU_TOKEN"
```

### 5. Obter estatísticas

```bash
curl -X GET http://localhost:8080/task/stats \
  -H "Authorization: Bearer SEU_TOKEN"
```

## 🔗 Frontend

Esta API foi desenvolvida para trabalhar com um frontend Angular rodando em `http://localhost:4200`. As configurações de CORS estão configuradas para permitir requisições desta origem.

## 📝 Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

## 👨‍💻 Autor

Desenvolvido por **Arthur Sousa** (@sousaarthur)

---

⭐ Se este projeto foi útil para você, considere dar uma estrela no repositório!
