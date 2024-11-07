# Etapa de construcción
FROM maven:3.8.4-openjdk-17 AS build

# Establecer un directorio de trabajo
WORKDIR /app

# Copiar todos los archivos y construir el proyecto
COPY . .
RUN mvn clean package -DskipTests

# Etapa final
FROM openjdk:17-jdk-slim

# Exponer el puerto en el que correrá la aplicación
EXPOSE 8080

# Copiar el archivo JAR generado en la etapa de construcción
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /app/demo-0.0.1-SNAPSHOT.jar

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/demo-0.0.1-SNAPSHOT.jar"]
