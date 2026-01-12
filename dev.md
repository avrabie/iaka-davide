# Development Guide

### Liquibase
1. In spring-boot 4, you have modules, and you need the starters. So you need these:
`implementation("org.springframework.boot:spring-boot-liquibase:4.0.1")`
`implementation("org.springframework.boot:spring-boot-starter-liquibase:4.0.1")`
2. What I also missed, and chatGPT failed to tell me:
   `developmentOnly("org.springframework.boot:spring-boot-docker-compose")`
