FROM eclipse-temurin:21-jdk-alpine

# Puerto y usuario no-root
EXPOSE 8080
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Directorio de trabajo
WORKDIR /app

# Copia el JAR y el MANIFEST.MF desde sus ubicaciones exactas
COPY --chown=appuser:appgroup build/libs/franchises.jar .
COPY --chown=appuser:appgroup build/tmp/jar/MANIFEST.MF ./META-INF/

# Variables de entorno
ENV JAVA_OPTS="-XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom"

# Usuario no-root
USER appuser

# Punto de entrada
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar franchises.jar"]