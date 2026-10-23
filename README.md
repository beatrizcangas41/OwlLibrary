# OwlLibrary

<div align="center">

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.x-6DB33F?style=for-the-badge&logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?style=for-the-badge&logo=mysql)
![Maven](https://img.shields.io/badge/Build-Maven-C71A36?style=for-the-badge&logo=apachemaven)

</div>

<p align="center">
  <strong>Online library and bookstore application</strong>
</p>

OwlLibrary is a Java-based web application designed to manage a digital library and bookstore experience. It combines a Spring Boot backend with database-backed book management, user-facing functionality, and a structured application flow for online reading and catalog browsing.

## Live Preview

This project is a local application and is intended to be run in a development environment.

## Project Pulse

<div align="center">

![Backend](https://img.shields.io/badge/Backend-Spring%20Boot-6DB33F?style=flat-square)
![Database](https://img.shields.io/badge/Database-MySQL%20%2F%20H2-4479A1?style=flat-square)
![Build Tool](https://img.shields.io/badge/Tool-Maven-C71A36?style=flat-square)

</div>

## Overview

This project was built as a practical library application with the goal of handling catalog data, user interactions, and online bookstore functionality in a structured web application. It includes database integration, server-side business logic, and support for a full Java web stack using Spring Boot.

The application is designed to support a digital library workflow, including:

- book catalog management
- bookstore-style data handling
- user-facing web interfaces
- application logic layered with Spring MVC and JPA
- MySQL database support with H2 fallback for local development

## Why it matters

Many library and bookstore projects are limited to static content or front-end demos. OwlLibrary aims to provide a more complete full-stack structure with real application patterns, backend services, and persistence support.

## Features

### 📚 Library catalog structure
Organized library data model for storing and retrieving book information.

### 🧩 Spring Boot architecture
Built on a Java Spring Boot foundation for web, security, and persistence layers.

### 🗄️ Database integration
Supports MySQL and includes H2 for local runtime compatibility.

### 🔐 Authentication and security
Includes Spring Security configuration for application-level protection.

### 🧪 Ready for development
Maven-based setup with project configuration included for local development.

## Tech Stack

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- MySQL
- H2 Database
- Maven
- Thymeleaf
- Bootstrap

## Project Structure

```text
OwlLibrary/
├── src/                              # application source code
├── lib/                              # dependency output directory
├── pom.xml                           # Maven configuration
├── owllibrary.sql                    # database schema/data import
├── Instructions.txt                  # setup instructions
├── .idea/                            # IDE configuration
├── OwlLibrary.iml                    # IntelliJ project metadata
├── _config.yml                       # Jekyll configuration
└── README.md                         # project documentation
```

## How to Use

1. Clone the repository.
2. Import the SQL file into MySQL or a compatible database.
3. Configure database credentials in the Spring Boot application settings.
4. Run the project as a Maven project.
5. Start the application and access the web interface in the browser.

## Local Development

### Prerequisites

- Java 8+
- Maven
- MySQL or compatible database
- WAMP/XAMPP or equivalent local database environment

### Setup

```bash
git clone https://github.com/beatrizcangas41/OwlLibrary.git
cd OwlLibrary
mvn clean install
mvn spring-boot:run
```

If you are using a local database tool, import `owllibrary.sql` first.

## Notes

This project is a full-stack Java application intended for local development and learning. The repository includes database seed data and project configuration to help accelerate setup.

## License

This project is intended for personal and educational use.

---

Crafted for practical digital systems, structured learning, and scalable web application design.
