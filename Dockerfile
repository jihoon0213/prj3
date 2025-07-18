FROM openjdk:21
COPY backend/build/libs/backend-0.0.1-SNAPSHOT.jar prj.jar

ENTRYPOINT ["java", "-jar", "prj.jar"]