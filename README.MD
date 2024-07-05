# Best Match Restaurant

![Coverage](.github/badges/jacoco.svg) ![Branches](.github/badges/branches.svg)

This is a Java REST application built with Spring Boot, Maven, and Java 17. The purpose of this application is to find
the best match restaurant based on given search criteria.

## Tech stack

* Java 17
* Springboot 3.2.5
* OpenApi (API first generating controller from swagger.yml spec)
* Jacoco (Code coverage report available on Github pipeline)
* Github Actions (Continuous Integration)

## Features

- It provides a RESTful API endpoint to search for restaurants based on various criteria like name, cuisine, distance,
  rating, and price.
- Retrieve detailed information about a specific restaurant.

## Prerequisites

- Java Development Kit (JDK) 17 or higher installed
- Apache Maven installed or Maven Wrapper
- IDE (e.g., IntelliJ IDEA, Eclipse) for development

## Getting Started

### From Git repo

1. Clone the repository:

    ```bash
    git clone https://github.com/osnircunha/restaurant-service.git
    ```
2. Navigate to the project directory:

    ```bash
    cd restaurant-service
    ```
   Then you can move to step 3

### From zip file package

1. Unzip the zip file you received:
   ```bash
   unzip restaurant-service-master.zip
   ```
2. Navigate to the project directory:

    ```bash
    cd restaurant-service-master
    ```
   Then you can move to step 3

### With repo cloned or zip extracted you can proceed as below:

3. Build the project using Maven:

    ```bash
    mvn clean package -DskipTests
    ```
   or
   ```bash
    ./mvnw clean package -DskipTests
    ```

4. Run unit tests using Maven:

    ```bash
    mvn clean verify
    ```
   or
   ```bash
    ./mvnw clean verify
    ```


5. Run the application:

    ```bash
    java -jar target/restaurants-service-0.0.1-SNAPSHOT.jar
    ```
   or
    ```bash
    mvn spring-boot:run
    ```

6. Access the API documentation and endpoints via a web browser.

   ```http
   http://localhost:8080/swagger-ui/index.html
   ```
   It can also be used to test the api by expanding the "GET /restaurants" and select "Try it out" button.
   By doing this, it will unlock the parameters form that you can fill based on usage criteria and then click "Execute"
   to submit a request.

## Usage

1. Use the provided API endpoint to search for restaurants based on the five criteria below:
    * Restaurant Name (Contains the text provided ignoring case)
    * Customer Rating (1 star ~ 5 stars)
    * Distance (1 mile ~ 10 miles)
    * Price (how much one person will spend on average, $10 ~ $50)
    * Cuisine (Chinese, American, Thai, etc.)
2. Make requests to the API using tools like cURL, Postman, or your preferred REST client.
3. Explore and analyze the response to find the best match restaurant based on the given search criteria.

## Assumptions

* It was not clear the rule for text search, the example provided seemed to be a "starts with" rule.
  The rule applied is a verification of "contains ignoring case" for the parameters provided on restaurant name and
  cuisine. However it can be easily adjusted if needed by change the interface FilterDefinition.java as below:

**Starts with ignoring case:**

```java
    default boolean contains(String current,String searchParam){
        return isNull(searchParam)||StringUtils.startsWithIgnoreCase(current,searchParam);
        }
```

**Starts with considering case:**


```java
    default boolean contains(String current,String searchParam){
        return isNull(searchParam)||StringUtils.startsWith(current,searchParam);
        }
```
