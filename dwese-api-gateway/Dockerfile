# Usar OPENJDK 21 como base
FROM eclipse-temurin:21-jdk-alpine

# Crear directorio de trabajo dentro del contenedor
WORKDIR /app

# Instalar Maven dentro del contenedor
RUN apk add --no-cache maven

# Copiar el código fuente
COPY . .

# Compilar la aplicación con Maven
RUN mvn clean package -DskipTests

# Mover el JAR a la raíz del contenedor
RUN mv target/*.jar app.jar

# Exponer el puerto donde se ejecuta el API Gateway
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]