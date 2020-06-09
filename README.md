## Requirements
For building and running the application you need:
JDK 1.8
Maven 3

## Running the application locally
There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in the com.assecor.application.AssecorApplication class from your IDE.
Alternatively you can use below command [more information see](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins.html#build-tool-plugins-maven-plugin) .

```bash
mvn spring-boot:run
```

## Usage

```python
Spring Security will ask to to enter UserName & Password
User Name: admin
Password: password

To see Data in browser go to http://localhost:8085/h2-console

API document is written in swagger 
please go to http://localhost:8085/swagger-ui.html#/

How to Dockerize Spring Boot Application
Go to docker console inside project target directory run the following command 

Build Docker Image
$ docker build -t assecor.jar .

Check Docker Image
$ docker image ls

Run Docker Image
$ docker run -p 9090:8085 assecor.jar

In the run command, we have specified that the port 8085 on the container should be mapped to the port 9090 on the Host OS.
```

## Packaging & run  
```
mvn clean install

Default packaging is jar and save it in /target directory 
Copy the jar file. Put any directory go to that directory and run the command:  java -jar assecor-0.0.1-SNAPSHOT.jar
Open the browser http://localhost:8085//api/v1/persons
It will ask UserName: admin  & Password: password  then click Singin

```

## License
[Public](https://test.com/licenses/)