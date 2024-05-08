# Best Match Restaurant

This is a Java REST application built with Spring Boot, Maven, and Java 17. The purpose of this application is to find
the best match restaurant based on given search criteria.

## Features

- It provides a RESTful API endpoint to search for restaurants based on various criteria like name, cuisine, distance,
  rating, and price.
- Retrieve detailed information about a specific restaurant.

## Prerequisites

- Java Development Kit (JDK) 17 or higher installed
- Apache Maven installed or Maven Wrapper
- IDE (e.g., IntelliJ IDEA, Eclipse) for development

## Getting Started

1. Clone the repository:

    ```bash
    git clone https://github.com/osnircunha/restaurant-service.git
    ```

2. Navigate to the project directory:

    ```bash
    cd restaurant-service
    ```

3. Build the project using Maven:

    ```bash
    mvn clean install
    ```
   or
   ```bash
    ./mvnw clean install
    ```

4. Run the application:

    ```bash
    java -jar target/restaurants-service-0.0.1-SNAPSHOT.jar
    ```
   or
    ```bash
    mvn spring-boot:run
    ```

5. Access the API documentation and endpoints via a web browser or API testing tool at:

    ```http
    http://localhost:8080/swagger-ui/index.html
    ```

## Usage

1. Use the provided API endpoints to search for restaurants based on different criteria.
2. Make requests to the API using tools like cURL, Postman, or your preferred REST client.
3. Explore and analyze the response to find the best match restaurant based on the given search criteria.
