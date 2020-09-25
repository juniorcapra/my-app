# my-app

API Rest com Spring Boot, Spring Data,Java 8 e Banco de Dados temos
o H2 que é um banco em memória.
Este API tem como finalidade a criação de clientes e a realização
de transações bancárias.

Gerando build para aplicações Spring boot
na pasta raiz do projeto(no mesmo deve conter o arquivo pom.xml )

rodar:

$ mvn clean install

$ cd target/

$ unzip -q my-app-0.0.1-SNAPSHOT.jar

para iniciar o servidor

$ java org.springframework.boot.loader.JarLauncher

Para acessar o banco h2
http://localhost:8100/h2

Para realização de testes já estão sendo incluídas por script alguns dados
para popular a base de dados.
Os testes poderão ser executados via Postman ou pelo próprio Swagger(http://localhost:8100/swagger-ui.html).


