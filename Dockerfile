FROM openjdk:17-oracle
COPY target/Medical-0.0.1-SNAPSHOT.jar Medical-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/Medical-0.0.1-SNAPSHOT.jar"]