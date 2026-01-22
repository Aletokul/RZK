**SRB**

# RZK – Spring Cloud Mikroservisni Sistem

Ovaj projekat predstavlja sistem zasnovan na mikroservisima, izgrađen pomoću Spring Boot i Spring Cloud tehnologija.
Demonstrira otkrivanje servisa (service discovery), centralizovano upravljanje konfiguracijom, rutiranje putem API Gateway-a
i pokretanje pomoću Docker-a.


## Arhitektura sistema

Sistem se sastoji od:

- Eureka Server – otkrivanje servisa (service discovery)
- Config Server – centralizovana konfiguracija
- API Gateway – rutiranje i bezbednost
- Eksterni repozitorijum konfiguracije (gitrepo)


## Korišćene tehnologije

- Java 17
- Spring Boot
- Spring Cloud (Eureka, Config Server, Gateway)
- Maven
- Docker & Docker Compose


## Struktura projekta

RZK/ <br>
├── api-gateway <br>
├── config-server <br>
├── eureka-server <br>
├── gitrepo <br>
└── docker-compose.yml


## Kako pokrenuti

1. Proverite da li je Docker instaliran
2. Klonirajte repozitorijum
3. Pokrenite komandu:
   docker-compose up --build

## Kako pokrenuti bez Docker-a

Pokrenite servise sledećim redosledom:
1. Config Server
2. Eureka Server
3. API Gateway


## Status projekta

Ovaj projekat je razvijen u akademske i edukativne svrhe.
Buduća poboljšanja mogu uključivati sigurnosna unapređenja i
dodatne mikroservise.

<br><p align="center"><strong>Prirodno-matematički fakultet</strong><br> Univerzitet u Novom Sadu, 2025 </p><br>

**ENG**

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
Future improvements may include security enhancements and
additional microservices

<br><p align="center"><strong>Faculty of Sciences</strong><br>University of Novi Sad, 2025</p>


