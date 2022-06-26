FROM openjdk:11

ARG JAR_FILE=*.jar

COPY ${JAR_FILE} kakaopay.jar

ENTRYPOINT ["java", "-jar", "/kakaopay.jar"]