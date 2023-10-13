## Spring Boot project.
The project was implemented using Java 11, PostgreSQL 14, Spring Boot 3.1.4 and Apache Maven 3.6.3.

## EndPoints
Endpoint documentation can be found at: [/api/swagger-ui](http://localhost:8080/api/swagger-ui) or the api-docs page at [/api/api-docs](http://localhost:8080/api/api-docs).

### Security

There are two users created in the application, one of each type (one with role USER representing the operator, and one with role ADMIN). The username and password are listed below.

* angel (password)
* admin (admin-password)

To make any rest requests, a Bearer authentication token must be added in the header, which is obtained from the end point [/api/auth/login](http://localhost:8080/api/auth/login).

### Useful files
The following files that may be of interest are added to the project archive.
* warehouse.postman_collection.json (is the request collection that was used in Postman to check the progress of the activities.)
* salva_dump.sql (a database save in PostgreSQL)