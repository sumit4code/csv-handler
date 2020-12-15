#CSV-Handler
csv handler is a spring boot application which creates a file listener observer and loads csv and put into relational database.

## Properties

| Property Name  | Sample Value  | Description |
| :------------ |:---------------| :-----|
| server.port     | 8080 | Spins up application on specified port 8080 |
| file.dir.path     | /app/fileloader       |   loads file from the specified path |
| file.pollInterval| 60       |    watch for any file related action in the interval of 60|
| spring.datasource.username| vmatta       |    user name for accessing db|
| spring.datasource.password| vmatta       |    password for the db|
| spring.datasource.url |jdbc:mysql://db:3306/csv-handler?createDatabaseIfNotExist=true&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true       |    db url for connecting to particular db|
| spring.jpa.properties.hibernate.dialect | org.hibernate.dialect.MySQL5Dialect       |    Hibernate configuration for db specific dialect|
| batch-processing.threashold-percentage-for-failure | 10       |    Threshold percentage for failure cases|
| batch-processing.default-minimum-processing-count | 10       |    Starting point for threshold calculation|

## Developer's note
###Pre-requisite

- Java 8
- Docker and Docker-compose must be installed

To start with the project , perform the below steps.

- Clone project
  
  ``git clone git@github.com:sumit4code/csv-handler.git``
- Build the project
  
  ``mvn clean install -DskipTests``
- Create image
  
  ``docker-compose build``
- Run the image

  ``docker-compose up``
- Stop image

  ``docker-compose down``