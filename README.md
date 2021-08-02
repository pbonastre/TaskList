# taskList Service
##Overview
Create a task List for create, update and delete task for a user. Each task have a finish date field to set a corresponding date value when the task is finish.

### Built With

### Tecnologías

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Gradle](https://gradle.org/) / [Maven](https://maven.apache.org/)
- [H2](http://www.h2database.com/html/features.html)
- [Swagger](https://swagger.io/solutions/api-documentation/)

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

![image](src\main\resources\jpg\ApiUrl.jpg) 

You could check H2 database by accessing: **http://localhost:8080/h2-ui**

![image](src\main\resources\jpg\H2console.jpg)

There is a **mimacomTaskList.postman_collection.json** file collection on the project root to be load in _Postman_ for a testing purpose. 
####How to Test?
```bash
./gradlew test - to run all JUNIT tests
```