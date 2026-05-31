# kafka-demo

Spring Boot application demonstrating Kafka **Producer/Consumer** patterns using multiple consumer groups. One producer sends messages to a topic, and three independent consumers each process the same message for different purposes.

---

## How it works

```
REST API
   │
   ▼
MessageProducer ──► Kafka Topic (messages-topic)
                          │
              ┌───────────┼───────────┐
              ▼           ▼           ▼
   PersistenceConsumer  AuditConsumer  MessageConsumer
   (group-persistence)  (group-audit)  (group-processing)
         │                   │               │
      Saves to           Creates an      Looks up user
    MessageEntity         AuditLog       and processes
```

Each consumer belongs to a **different consumer group**, so all three receive every message independently.

---

## Tech Stack

- Java 17
- Spring Boot 3.0.6
- Spring Kafka 3.0.6
- Docker + Docker Compose
- Kafka UI (visual dashboard)

---

## Prerequisites

### 1. Install Java 17

Verify:
```bash
java --version
```

### 2. Install Gradle

Download from [https://gradle.org/releases](https://gradle.org/releases) → `gradle-8.x-bin.zip`

Extract to `C:\Gradle\gradle-8.x` and add to PATH:

- Search **"Environment Variables"** in the Start menu
- Under **System Variables** → `Path` → **Edit** → **New**
- Add `C:\Gradle\gradle-8.x\bin`
- Click OK on everything

Verify in a **new terminal**:
```bash
gradle --version
```

### 3. Install Docker Desktop

Download from [https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop)

Install and make sure Docker Desktop is running before the next steps.

---

## Installation

```bash
# Clone the repository
git clone https://github.com/your-username/kafka-demo.git
cd kafka-demo

# Generate the Gradle wrapper
gradle wrapper
```

---

## Running the project

### Step 1 — Start Kafka

```bash
docker-compose up -d
```

This starts three containers:

| Container | Port | Description |
|---|---|---|
| Zookeeper | 2181 | Kafka coordination |
| Kafka | 9092 | Message broker |
| Kafka UI | 8080 | Visual dashboard |

Wait about 10 seconds for Kafka to fully start before running the app.

### Step 2 — Run the application

```bash
./gradlew bootRun
```

The app starts on port `8081`.

### Step 3 — Send a message

```bash
curl -X POST http://localhost:8081/api/messages \
  -H "Content-Type: application/json" \
  -d '{"title":"Hello Kafka","author":"fabian","payload":"My first message"}'
```

You should see this in the console:

```
[PRODUCER]    Sent: Message[title=Hello Kafka, author=fabian, payload=My first message]
[PERSISTENCE] Saved: MessageEntity[receiveDate=2026-05-30, author=fabian, title=Hello Kafka, payload=My first message]
[AUDIT]       AuditLog[author=fabian, content=Hello Kafka]
[PROCESSOR]   Processing message for user='fabian' | title='Hello Kafka' | payload='My first message'
```

---

## REST API

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/messages` | Send a message to Kafka |
| `GET` | `/api/messages/persisted` | List all persisted messages |
| `GET` | `/api/messages/audit` | List all audit logs |

### Request body for POST

```json
{
  "title": "Hello Kafka",
  "author": "fabian",
  "payload": "My first message"
}
```

---

## Kafka UI

Open [http://localhost:8080](http://localhost:8080) to visually inspect:

- Topics and their messages
- Consumer groups and offsets
- Broker configuration

---

## Project structure

```
kafka-demo/
├── docker-compose.yml
├── build.gradle
├── src/main/java/com/example/
│   ├── KafkaDemoApplication.java
│   ├── config/
│   │   ├── KafkaProducerConfiguration.java   # Serializes Message to JSON
│   │   └── KafkaConsumerConfiguration.java   # Deserializes JSON to Message
│   ├── controller/
│   │   └── MessageController.java            # REST endpoints
│   ├── producer/
│   │   └── MessageProducer.java              # Sends to Kafka topic
│   ├── consumer/
│   │   ├── PersistenceMessageConsumer.java   # Saves MessageEntity
│   │   ├── AuditMessageConsumer.java         # Creates AuditLog
│   │   └── MessageConsumer.java              # Processes with user lookup
│   ├── event/
│   │   └── Message.java                      # Kafka message record
│   ├── model/
│   │   ├── MessageEntity.java
│   │   ├── UserEntity.java
│   │   └── AuditLog.java
│   ├── repository/
│   │   ├── PersistenceRepository.java
│   │   ├── InMemoryPersistenceRepository.java
│   │   ├── UserRepository.java
│   │   └── InMemoryUserRepository.java
│   ├── service/
│   │   ├── AuditService.java
│   │   └── LogAuditService.java
│   ├── processor/
│   │   ├── MessageProcessor.java
│   │   └── DefaultMessageProcessor.java
│   └── util/
│       ├── Clock.java
│       └── SystemClock.java
└── src/main/resources/
    └── application.yml
```

---

## Stop everything

```bash
docker-compose down
```