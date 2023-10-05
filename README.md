# Banking API Microservices

## Description
Banking system developed with the following technologies:

- Java 20
- Spring Boot 3.1.4
- Spring Cloud
- kafka
- Eureka
- MySQL 8

### To run the project on your localhost

- Pull up the docker container
```sh
$ sudo docker-compose up --build
```

# URL:
- Clientes: http://localhost:8080/api/clientes
- Cuentas: http://localhost:8080/api/cuentas
- Movimientos: http://localhost:8080/api/movimientos
- Eureka: http://localhost:8761/

# Test (Unit and implementation tests)
- Microservices client-person