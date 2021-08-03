# taskList Service
##Overview
Create a task List for create, update and delete task for a user. Each task have a finish date field to set a corresponding date value when the task is finish.

### Built With

### Tecnologías

- [Spring Boot](https://spring.io/projects/spring-boot) - To create standalone applications that run on their own.
- [Gradle](https://gradle.org/)   - To automate builds, testings and deployment.
- [H2](http://www.h2database.com/html/features.html) - Inmemory database.
- [Swagger](https://swagger.io/solutions/api-documentation/) - For implementing the OpenApi specification.
- [JUnit5](https://junit.org/junit5/) -
- [Lombok](https://projectlombok.org/) - Library to reduce Java code to auto-generate all the boilerplate code during compiling time.
- [JPA](https://spring.io/projects/spring-data-jpa) - Java specification for accessing, persisting and managing data between java objects/classes and the database.

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/pbonastre/taskList.git
   ```
2. Install NPM packages
   ```sh
   ./gradlew clean build
   ```
3. Change or check password for H2 database as you like in application. properties.


###How to run?
Run spring boot application with command: 
```bash
./gradlew bootRun
```

Table TASK will be automatically generated in H2 Database.

###Once the application is running

To access to swagger page: h**ttp://localhost:8080/swagger-ui.html**

![ScreenShot](https://github.com/pbonastre/taskList/blob/develop/src/main/resources/jpg/ApiUrl.jpg)

You could check H2 database by accessing: **http://localhost:8080/h2-ui**

![ScreenShot](https://github.com/pbonastre/taskList/blob/develop/src/main/resources/jpg/H2console.jpg)

There is a **mimacomTaskList.postman_collection.json** file collection on the project root to be load in _Postman_ for a testing purpose. 
####How to Test?
```bash
./gradlew test - to run all JUNIT tests
```
