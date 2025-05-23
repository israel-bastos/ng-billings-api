## 🏡 Sobre o Projeto

**Desafio Técnico NG Billings**

Crie um sistema de gestão bancária por meio de uma API, composta por dois endpoints: "/conta" e "/transacao". O endpoint "/conta" deve criar e fornecer informações sobre o número da conta. O endpoint "/transacao" será responsável por realizar diversas operações financeiras.

---

## 🌎 URL da Aplicação

- Endereço: [http://localhost:8080](http://localhost:8080)
- Documentação Swagger: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)


- **`/conta`** para criação e consulta de conta
- **`/transacao`** para realizar operações financeiras com taxas

---

## ⚡ Tecnologias Utilizadas

[![Java](https://img.shields.io/badge/Java%2017-cc0000?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6db33f?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![H2 Database](https://img.shields.io/badge/H2%20Database-003366?style=for-the-badge&logo=h2&logoColor=white)](https://www.h2database.com/)
[![Swagger](https://img.shields.io/badge/Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)](https://swagger.io/)
[![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=java&logoColor=white)](https://junit.org/junit5/)
[![Docker](https://img.shields.io/badge/Docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![JaCoCo](https://img.shields.io/badge/JaCoCo-003366?style=for-the-badge&logo=java&logoColor=white)](https://www.eclemma.org/jacoco/)

---

## 💻 Requisitos

Para utilizar a aplicação em ambiente de desenvolvimento, é necessário ter as seguintes ferramentas instaladas:

[![Java](https://img.shields.io/badge/Java%2017-cc0000?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com)
[![Git](https://img.shields.io/badge/Git-E44C30?style=for-the-badge&logo=git&logoColor=white)](https://git-scm.com)
[![Docker](https://img.shields.io/badge/Docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)

---

## 🔧 Clonando e Executando

1. Clone o projeto:
   ```bash
   git clone https://github.com/israel-bastos/ng-billings-api.git
   cd ng-billings-api
   
   # 2. Rodar a aplicação
   mvn spring-boot:run

A aplicação iniciará em `http://localhost:8080`.

## 📘 Endpoints da API

### ➕ Criar Conta

`POST /v1/accounts`

```json
{
  "accountNumber": "123",
  "balance": 100.00
}
```

📘 Resposta:
```json
{
  "accountNumber": "123",
  "balance": 100.00,
  "transactions": []
}
```

---

### 🔍 Buscar Conta

`GET /v1/accounts/{accountNumber}`

📘 Resposta:
```json
{
  "accountNumber": "acc-001",
  "balance": 80.00,
  "transactions": [
    {
      "amount": 20.00,
      "type": "D"
    }
  ]
}
```

---

### ➖ Criar Transação

`POST /v1/transactions`

```json
{
  "accountNumber": "acc-001",
  "amount": 20.00,
  "type": "D"
}
```

📘 Resposta:
```json
{
  "accountNumber": "acc-001",
  "balance": 80.00
}
```

---

## 📏 Regras de Negócio

- Saldo **nunca pode ser negativo**
- Transações aplicam taxa de acordo com o tipo:
   - Pix → 0%
   - Débito → 3%
   - Crédito → 5%
- Transações com saldo insuficiente são recusadas (404)
- Conta duplicada retorna erro 400

---

## 💡 Comandos Úteis

```bash
# 1. Iniciar a aplicação
mvn spring-boot:run

# 2. Executar os testes
mvn test

# Executar testes com cobertura Jacoco
mvn clean verify

# Relatório de cobertura:
open target/site/jacoco/index.html

# 4. Gerar build do projeto
mvn clean install
```

## 🐳 Rodando com Docker

Você pode rodar esta aplicação em um container local:

### 🧱 1. Construir a imagem Docker

```bash
docker build -t ng-billings-api .
```

### 🚀 2. Rodar o container

```bash
docker run -p 8080:8080 ng-billings-api
```

### 🚀 3. Rodar o compose

```bash
docker-compose up --build
```
## 🧹 Limpando Containers e Imagens Docker (para rebuild completo)

Sempre que você fizer alterações no código-fonte ou nas dependências e quiser garantir que o Docker reflita essas mudanças, siga os passos abaixo para remover containers e imagens antigos:

```bash
# Parar e remover containers, volumes e redes do docker-compose
docker-compose down -v --remove-orphans

# Remover a imagem antiga da aplicação (substitua pelo nome se necessário)
docker rmi ng-billings-api

# Verificar se não sobrou container antigo
docker ps -a

# Opcional: remover todos containers parados
docker container prune
```

Após isso, você pode executar:

```bash
# Recompilar o projeto
mvn clean package

# Recriar a imagem Docker
docker build -t ng-billings-api .

# Subir a aplicação com Docker Compose
docker-compose up --build
```

---

## 📂 Estrutura do Projeto

- `adapters.controller` → Controllers REST da aplicação
- `adapters.controller.dto` → DTOs para entrada e saída das APIs
- `adapters.psersistence.entity` → Entidades JPA do domínio persistente
- `adapters.psersistence.repository` → Interfaces de acesso ao banco (Spring Data)
- `application.service` → Implementações dos casos de uso (orquestração)
- `application.usecase` → Contratos/portas dos casos de uso
- `domain.enums` → Tipos fixos (como PaymentType)
- `domain.exception` → Exceções de negócio
- `domain.messaging` → Interface de publicação de eventos
- `domain.strategy` → Estratégias de taxa de transação
- `domain.vo` → Objetos de valor (como TransactionVO)
- `infra.messaging` → Implementações simuladas (ex: RabbitMQ fake)
- `config` → Configurações da aplicação (Swagger, beans, profiles)

---

## 🧠 Arquitetura e Boas Práticas

Este projeto segue princípios de arquitetura limpa com forte separação de responsabilidades:

- ✅ **Arquitetura Hexagonal (Ports & Adapters)** (sem usar a nomenclatura literal)
- ✅ **Clean Code**: coesão, nomes claros, classes curtas
- ✅ **DDD**: com entidades, VOs, estratégia de taxa e camada de domínio isolada
- ✅ **SOLID** aplicado nos serviços e interfaces
- ✅ **Strategy Pattern** para taxas financeiras
- ✅ **Simulação de Gateway de Pagamento**
- ✅ **Simulação de fila com RabbitMQ fake**

## ✅ Testes

- Testes unitários com JUnit + Mockito
- Testes de integração com MockMvc
- Cobertura com Jacoco
---

## 🧪 Simulações Técnicas

- 💳 **Gateway Fictício de Cartão**: verifica se a transação de crédito seria aprovada
- 🧠 **Strategy de Taxas**: usada para aplicar taxa com base no tipo de pagamento
- 📩 **Fila Simulada (RabbitMQ)**: eventos de transação são publicados para uma fila fake
- 

### 🌐 Acessos

- H2 Console: http://localhost:8080/h2-console
   - JDBC URL: `jdbc:h2:mem:ngbillings-db`
   - Usuário: `sa`
   - Senha: (vazio)


