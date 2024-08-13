# How-to

## Configurations

In order to run the temperature sensor job, there are 3 properties that needs to be adjusted in **src/res/application.properties** file: `spring.datasource.url`, `spring.datasource.username` and `spring.datasource.password`. Assign the values that you configured. For example:

```properties
spring.datasource.url= jdbc:oracle:thin:@//localhost:1521/XEPDB1
spring.datasource.username = system
spring.datasource.password = hanif
```

## Build

From the root of the project (folder **first**), run `mvn clean package`

## Run temperature sensor job

**Please build the code before running it (!)**

In case you have **uuidgen** installed, use the following command to (re-)run th job:
```shell
java -jar target/spring-batch-demo-0.0.1-SNAPSHOT.jar com.spring.batch.demo.config.BatchConfig temperatureSensorJob id=$(uuidgen)
```

If you do not have it, you need to place unique number in the last (id) parameter specified, and **do it every time you run**. Otherwise, Spring Batch will not re-run the job. Example:
```shell
java -jar target/spring-batch-demo-0.0.1-SNAPSHOT.jar com.spring.batch.demo.config.BatchConfig temperatureSensorJob id=1
