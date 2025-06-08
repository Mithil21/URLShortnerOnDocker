URL Shortener Microservice





Table of Contents
About the Project
Features
Technology Stack
System Design Highlights
Prerequisites
Getting Started
Clone the Repository
Build the Spring Boot Application
Run with Docker Compose
API Endpoints
Configuration
Future Enhancements
License
Contact
1. About the Project
   This project implements a highly available and scalable URL shortening microservice, similar to TinyURL. It allows users to convert long, unwieldy URLs into short, memorable ones, and then redirects users from the short URL back to the original.

Built using Spring Boot, PostgreSQL for robust data persistence, and Redis for high-performance caching, the entire application stack is containerized using Docker and orchestrated with Docker Compose, demonstrating a modern approach to distributed system development and deployment.

2. Features
   URL Shortening: Converts a long URL into a unique, short alphanumeric code.
   URL Redirection: Redirects requests from a short URL to its original long URL.
   High Performance Caching: Utilizes Redis to cache frequently accessed URLs, significantly reducing database load and improving response times.
   Scalable Persistence: Employs PostgreSQL for reliable storage of URL mappings.
   Dockerized Environment: All services (Spring Boot App, PostgreSQL, Redis) run in isolated Docker containers, ensuring consistent environments and easy deployment.
   Robust Error Handling: Gracefully handles cases like invalid URLs or non-existent short codes.
3. Technology Stack
   Backend: Spring Boot (Java 17)
   Database: PostgreSQL
   Caching: Redis
   Build Tool: [Maven / Gradle - choose one]
   Containerization: Docker, Docker Compose
   Version Control: Git
4. System Design Highlights
   The architecture is designed with scalability, performance, and reliability in mind:

Microservice Architecture: The URL shortener operates as a self-contained microservice, facilitating independent deployment and scaling.
Database (PostgreSQL): Chosen for its ACID compliance and reliability, ensuring data integrity for URL mappings.
Caching Layer (Redis): Integrated as a distributed cache to store frequently accessed shortUrl -> originalUrl and originalUrl -> shortUrl mappings. This reduces latency for redirection and getShortUrl requests by minimizing direct database hits. Spring's @Cacheable and @CachePut annotations are utilized for effective cache management.
Unique ID Generation: [Briefly describe your chosen ID generation strategy here. E.g., "Uses a counter-based approach with Base62 encoding to generate unique, non-sequential short codes." or "Employs a custom distributed ID generation strategy inspired by Twitter Snowflake for robust unique short code creation."]
Containerization (Docker): Each component (Spring Boot app, PostgreSQL, Redis) runs in its own Docker container, ensuring isolation and consistent environments.
Orchestration (Docker Compose): docker-compose.yml defines and links all services, allowing for single-command deployment and management of the entire application stack. This simplifies development setup and local testing.
5. Prerequisites
   Before you begin, ensure you have the following installed on your machine:

Java Development Kit (JDK) 17 or higher
[Maven / Gradle - choose one] (if building locally outside Docker Compose)
Docker Desktop (includes Docker Engine and Docker Compose)
Download Docker Desktop
6. Getting Started
   Follow these steps to get your URL Shortener service up and running.

Clone the Repository
First, clone the project repository to your local machine:

Bash

git clone [Your Repository URL Here]
cd [your-project-directory-name]
Build the Spring Boot Application (Optional - Docker Compose will build for you)
If you prefer to build the JAR manually before Dockerizing, or for local testing:

Using Maven:
Bash

./mvnw clean install -DskipTests
Using Gradle:
Bash

./gradlew clean build -x test
This will generate the app.jar (or similar named JAR) in your target/ (Maven) or build/libs/ (Gradle) directory.
Run with Docker Compose
The easiest way to run the entire stack is using Docker Compose. Make sure Docker Desktop is running.

Navigate to the root directory of the project where docker-compose.yml and Dockerfile are located.

Bring up all services (Spring Boot app, PostgreSQL, Redis):

Bash

docker-compose up --build
The --build flag ensures that your Spring Boot application's Docker image is rebuilt from the Dockerfile. Use this every time you make changes to your Java code.
This command will pull the necessary postgres and redis images, build your app image, and start all containers.
To run in detached mode (in the background):

Bash

docker-compose up -d --build
To stop and remove the containers (keeps persistent data):

Bash

docker-compose down
To stop and remove containers and all associated volumes (persistent data will be lost):

Bash

docker-compose down --volumes
7. API Endpoints
   Once the application is running, you can interact with it via the following API endpoints:

Base URL: http://localhost:8080 (or your exposed port if changed)

1. Shorten a URL
   Endpoint: /api/v1/url/shorten
   Method: POST
   Request Body (JSON):
   JSON

{
"originalUrl": "https://www.example.com/very/long/url/that/needs/shortening"
}
Success Response (Example):
HTTP/1.1 200 OK
Short URL created: shortcode123
Error Response (Example):
HTTP/1.1 400 BAD REQUEST
Original URL cannot be empty
2. Redirect from Short URL
   Endpoint: /short/{shortUrlKey}
   Method: GET
   Example Usage: Open http://localhost:8080/short/shortcode123 in your browser.
   Behavior:
   If the shortUrlKey exists, you will be redirected (HTTP 302 Found) to the originalUrl.
   If the shortUrlKey does not exist, you will be redirected to an error page (e.g., http://localhost:8080/error/404) with a 404 Not Found status.
8. Configuration
   The application uses Spring Boot's externalized configuration. When running with Docker Compose, the application-docker.properties profile is activated, which configures connections to the postgres and redis services within the Docker network.

src/main/resources/application.properties: Default configuration (e.g., for local non-Docker development).
src/main/resources/application-docker.properties: Configuration specifically for Docker Compose environment.
Key properties configured in docker-compose.yml and application-docker.properties:

PostgreSQL:
spring.datasource.url=jdbc:postgresql://postgres:5432/your_database_name
spring.datasource.username=your_postgres_user
spring.datasource.password=your_postgres_password
Redis:
spring.data.redis.host=redis
spring.data.redis.port=6379
Remember to update your_database_name, your_postgres_user, and your_postgres_password in both docker-compose.yml and application-docker.properties to match your desired setup.

9. Future Enhancements
   Custom Short URLs: Allow users to specify a desired short code (if available).
   URL Expiration: Implement functionality for short URLs to expire after a certain time or number of clicks.
   Click Analytics: Track and store metrics (e.g., total clicks, geographical data) for each short URL.
   Rate Limiting: Implement API rate limiting to prevent abuse.
   Asynchronous Processing: Use a message queue (e.g., Kafka, RabbitMQ) for background tasks like analytics processing or URL validation.
   Malicious URL Detection: Integrate with a third-party service to check for and prevent shortening of known malicious URLs.
   Health Checks: Implement Docker health checks for more robust service orchestration.
   Distributed ID Generation: Refine the short code generation for highly distributed environments (e.g., Twitter Snowflake-like ID generation).
10. License
    This project is licensed under the MIT License - see the LICENSE file for details.

11. Contact
    Mithil Baria
    Email: mithilbaria98@gmail.com
    LinkedIn: https://www.linkedin.com/feed/