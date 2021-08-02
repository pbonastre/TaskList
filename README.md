# taskList Service
##Overview
Create a task List for create, update and delete task for a user. Each task have a finish date field to set a corresponding date value when the task is finish.

####How to run?
Run spring boot application with command: 
```bash
./gradlew bootRun
```

Table TASK will be automatically generated in H2 Database.

#####Once the application is running

To access to swagger page:http://localhost:8080/swagger-ui.html

![image](src\main\resources\jpg\ApiUrl.jpg) 

You could check H2 database by accessing:http://localhost:8080/h2-ui
![image](src\main\resources\jpg\H2console.jpg)

####How to Test?
```bash
./gradlew test - to run all JUNIT tests
```