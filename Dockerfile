# Utiliza la imagen base de Java 17
FROM openjdk:17-jdk-alpine

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR de la aplicación al contenedor
COPY target/gestion-pedidos.jar app.jar

# Expone el puerto en el que Spring Boot corre
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
