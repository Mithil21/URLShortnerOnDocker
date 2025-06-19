# URL Shortener Microservice

## Table of Contents

- [About the Project](#about-the-project)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [System Design Highlights](#system-design-highlights)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Configuration](#configuration)
- [Security Insight: JWT Token Handling via Caching](#security-insight-jwt-token-handling-via-caching)
- [Future Enhancements](#future-enhancements)
- [License](#license)
- [Contact](#contact)

---

## About the Project

This project implements a highly available and scalable URL shortening microservice, similar to TinyURL. It allows users to convert long, unwieldy URLs into short, memorable ones, and then redirects users from the short URL back to the original.

Built using **Spring Boot**, **PostgreSQL** for robust data persistence, and **Redis** for high-performance caching, the application stack is fully containerized using Docker and orchestrated with Docker Compose, following modern microservice best practices.

---

## Features

- 🔗 **URL Shortening** – Converts a long URL into a unique short alphanumeric code.
- 🔁 **URL Redirection** – Redirects requests from a short URL to its original long URL.
- ⚡ **High Performance Caching** – Uses Redis to cache frequently accessed URLs.
- 💾 **Scalable Persistence** – Employs PostgreSQL for durable URL mappings.
- 🐳 **Dockerized Environment** – Consistent deployment using Docker and Docker Compose.
- 🚫 **Robust Error Handling** – Gracefully manages invalid URLs or non-existent codes.

---

## Technology Stack

- **Backend**: Spring Boot (Java 17)
- **Database**: PostgreSQL
- **Caching**: Redis
- **Build Tool**: Maven or Gradle
- **Containerization**: Docker, Docker Compose
- **Version Control**: Git

---

## System Design Highlights

- **Microservice Architecture**: Enables independent deployment and scaling.
- **PostgreSQL**: Chosen for its ACID compliance and reliability.
- **Redis Cache**: Speeds up lookups using `@Cacheable` and `@CachePut` annotations.
- **Unique ID Generation**: *[Describe your strategy here. E.g., Base62 encoding or custom Snowflake-inspired ID generator.]*  
- **Containerization**: Isolated services for consistency and easy local dev/testing.
- **Docker Compose**: Manages orchestration, networks, and volumes effortlessly.

---

## Prerequisites

- Java Development Kit (JDK) 17+
- Maven or Gradle (if building locally)
- Docker & Docker Compose

---

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Mithil21/URLShortnerOnDocker.git
cd [your-project-directory-name]
# Using Maven
./mvnw clean install -DskipTests

# Or using Gradle
./gradlew clean build -x test

docker-compose up --build       # foreground
docker-compose up -d --build    # background

docker-compose down             # keeps data
docker-compose down --volumes  # removes all volumes

API Endpoints
Base URL: http://localhost:8080

1. Shorten a URL
Endpoint: /api/v1/url/shorten

Method: POST

Request Body:
{
  "originalUrl": "https://www.example.com/very/long/url"
}

Response (200 OK): shortcode123

Response (400 Bad Request): "Original URL cannot be empty"

2. Redirect from Short URL
Endpoint: /short/{shortUrlKey}

Method: GET

Behavior:

If valid, redirects to original URL (302 Found)

If not, redirects to error page with 404 Not Found

Configuration
Spring Boot uses externalized configuration:

application.properties: Default for local dev

application-docker.properties: Used when run via Docker Compose

Key Docker Settings:

# PostgreSQL
spring.datasource.url=jdbc:postgresql://postgres:5432/your_database_name
spring.datasource.username=your_postgres_user
spring.datasource.password=your_postgres_password

# Redis
spring.data.redis.host=redis
spring.data.redis.port=6379
Update values in both docker-compose.yml and config files as per your setup.

Security Insight: JWT Token Handling via Caching
This URL shortening design pattern can be extended for secure management of JWT access tokens, which are often exposed in browser developer tools due to frontend misconfigurations.

Problem:
Applications often fail to properly obscure JWTs from the network tab.

If intercepted, tokens can be reused or tampered with (e.g., changing authorities).

Proposed Solution:
Shorten JWT tokens using a unique Base64 hash.

Store them in Redis cache as active tokens.

When a token is used:

Check its existence in the cache.

If already used or expired → invalidate it (blacklist).

This makes it impossible to reuse an intercepted token.

Adds a stateless, cache-driven layer of security for API authentication.

This method mitigates risks of insider attacks and elevates token management strategies in distributed systems.

Future Enhancements
🧩 Custom Short URLs

⏱️ URL Expiration Support

📊 Click Analytics (IP/Geo-based)

🧵 Asynchronous Processing (Kafka/RabbitMQ)

🚦 Rate Limiting per API Key/IP

🛡️ Malicious URL Detection via 3rd Party API

💥 Distributed ID Generation for global scaling

❤️ Health Checks in Docker for orchestration monitoring

License
This project is licensed under the MIT License.

Contact
Mithil Baria
📧 Email: mithilbaria98@gmail.com
🔗 LinkedIn: linkedin.com/in/mithilbaria
