# Read Me First
The following was discovered as part of building this project:

* The JVM level was changed from '11' to '17', review the [JDK Version Range](https://github.com/spring-projects/spring-framework/wiki/Spring-Framework-Versions#jdk-version-range) on the wiki for more details.
* The original package name 'maroune.semantic-search.simentic-search' is invalid and this project uses 'maroune.semanticsearch.simenticsearch' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.0/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#web)
* [Spring Data Elasticsearch (Access+Driver)](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#data.nosql.elasticsearch)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#using.devtools)
* [Vaadin](https://vaadin.com/docs)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Creating CRUD UI with Vaadin](https://spring.io/guides/gs/crud-with-vaadin/)

### TODO
- [x] init project
- [x] add docker container for elasticsearch
- [x] add CRUD for post
- [x] add import bulk for post
- [x] get embedding from openAi for post
- [x] implement semantic search 

### Installation

rename env file

```bash
mv env.properties.exemple env.properties
```
add your openAi key to env.properties

```bash
openai.token=YOUR_KEY
```
start the app

```bash
docker-compose up -d
mvnw spring-boot:run
```
visit [localhost:8080](http://localhost:8080)


