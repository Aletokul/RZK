# RZK – Spring Cloud Microservices System

This project represents a microservices-based system built with Spring Boot and Spring Cloud.
It demonstrates service discovery, centralized configuration management, API Gateway routing,
and Docker-based deployment.


## System Architecture

The system consists of:

- Eureka Server – service discovery
- Config Server – centralized configuration
- API Gateway – routing and security
- External configuration repository (gitrepo)


## Technologies Used

- Java 17
- Spring Boot
- Spring Cloud (Eureka, Config Server, Gateway)
- Maven
- Docker & Docker Compose


## Project Structure

RZK/ <br>
├── api-gateway <br>
├── config-server <br>
├── eureka-server <br>
├── gitrepo <br>
└── docker-compose.yml


## How to Run

1. Make sure Docker is installed
2. Clone the repository
3. Run:
   docker-compose up --build

## How to run without Docker

Run services in the following order:
1. Config Server
2. Eureka Server
3. API Gateway


## Project Status

This project was developed for academic and learning purposes.
Future improvements may include security enhancements,
additional microservices, and database integration.




