# Groovy Web Console

Overview
--------
This application is intended for embedding a Groovy console in a Spring Boot web application
which then could access the application context (for testing purposes).

![Groovy Web Console](https://raw.githubusercontent.com/gabor-bata/groovy-web-console/master/resources/console-screenshot.png)

How to compile
--------------

    mvnw clean package

Usage
-----
Java 8 or later is required to start Zoidberg.

* Start server: `mvnw spring-boot:run` or `java -jar target/groovy-web-console-1.0.0-SNAPSHOT.jar`
* Access the console: `http://localhost:8080/console`
