FROM openjdk:11
COPY ./build/libs/swedbank-hm-0.0.1-SNAPSHOT.jar swedbank-hm-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/swedbank-hm-0.0.1-SNAPSHOT.jar"]