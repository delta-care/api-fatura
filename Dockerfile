FROM openjdk:11.0.9.1-jre
ADD /target/api-fatura-*.jar api-fatura.jar
ENTRYPOINT ["java", "-jar", "api-fatura.jar"]
