###########Builder Image############

FROM maven:3.8.3-openjdk-17 AS builder

LABEL maintainer="Mohammed abdu <eng.mo.abdu@gmail.com> <+201020013620 / +201110076777>"

ARG TARGETARCH

# image layer
WORKDIR /app

ADD pom.xml /app

RUN mvn dependency:go-offline --fail-never

# Image layer: with the application
COPY . /app

RUN mvn clean package -DskipTests

###########Final Image############

FROM openjdk:17-alpine

LABEL maintainer="Mohammed abdu <eng.mo.abdu@gmail.com> <+201020013620 / +201110076777>"

COPY --from=builder /app/target/*.jar /opt/bookstore.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/opt/bookstore.jar"]
