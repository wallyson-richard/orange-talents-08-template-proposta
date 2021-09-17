FROM openjdk:11
COPY ./target/proposta.jar proposta.jar
ENTRYPOINT ["java","-jar","/proposta.jar"]