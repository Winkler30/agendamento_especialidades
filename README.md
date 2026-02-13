# 🩺 Agendamento de Especialidades

Microsserviço responsável pelo gerenciamento de agendamentos de especialidades médicas para o SUS (Sistema Único de Saúde).

Este projeto faz parte de uma arquitetura baseada em microsserviços, permitindo integração com outros sistemas por meio de API REST e mensageria (Kafka).

---

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Spring Kafka
- Lombok
- Maven
- Docker & Docker Compose

---

## 📁 Estrutura do Projeto

```
agendamento_especialidades/
│
├── docker-compose.yml
├── pom.xml
└── src
    └── main
        ├── java/
        └── resources/
            └── application.yml
```

---

## 🧠 Funcionalidades

✔️ Cadastro de agendamentos  
✔️ Consulta de agendamentos por ID  
✔️ Listagem de agendamentos  
✔️ Atualização de agendamento  
✔️ Remoção de agendamento  
✔️ Integração com mensageria via Kafka  
✔️ Persistência em banco PostgreSQL

---

## 📦 Pré-requisitos

Antes de executar o projeto, é necessário ter instalado:

- Java 17+
- Maven 3.6+
- Docker
- Docker Compose

---

## 🚀 Como Executar o Projeto

### 🔹 1. Clonar o Repositório

```bash
git clone https://github.com/Winkler30/agendamento_especialidades.git
cd agendamento_especialidades
```

---

### 🔹 2. Subir Infraestrutura com Docker

```bash
docker compose up -d
```

Isso irá iniciar:

- PostgreSQL
- Kafka
- Zookeeper
- Aplicação Spring Boot (se configurado no compose)

---

### 🔹 3. Rodar Localmente (sem Docker)

Compilar o projeto:

```bash
mvn clean install
```

Executar a aplicação:

```bash
mvn spring-boot:run
```

A aplicação iniciará normalmente na porta padrão:

```
http://localhost:8080
```

---

## 📌 Endpoints da API

| Método | Endpoint             | Descrição                   |
|--------|----------------------|----------------------------|
| GET    | `/agendamentos`      | Lista todos os agendamentos |
| GET    | `/agendamentos/{id}` | Busca agendamento por ID    |
| POST   | `/agendamentos`      | Cria novo agendamento       |
| PUT    | `/agendamentos/{id}` | Atualiza um agendamento     |
| DELETE | `/agendamentos/{id}` | Remove um agendamento       |

---

## 📄 Exemplo de Payload (POST)

```json
{
  "pacienteId": 1,
  "especialidade": "Cardiologia",
  "data": "2026-02-20",
  "hora": "14:30"
}
```

---

## ⚙️ Variáveis de Ambiente (Exemplo)

Caso execute fora do Docker, configure:

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/agendamentos
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
SPRING_KAFKA_BOOTSTRAP_SERVERS=localhost:9092
```

Ou configure diretamente no `application.yml`.

---

## 🧪 Testes

Para executar os testes:

```bash
mvn test
```

---

## 🏗️ Arquitetura

Este microsserviço segue o padrão REST e pode ser integrado a outros serviços da arquitetura por meio de:

- APIs HTTP
- Publicação/consumo de eventos via Kafka

---
