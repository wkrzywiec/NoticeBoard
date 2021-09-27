# Noticeboard CRUD application

![Master Branch](https://github.com/wkrzywiec/NoticeBoard/workflows/Master%20Branch/badge.svg) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=wkrzywiec_NoticeBoard&metric=coverage)](https://sonarcloud.io/dashboard?id=wkrzywiec_NoticeBoard) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=wkrzywiec_NoticeBoard&metric=alert_status)](https://sonarcloud.io/dashboard?id=wkrzywiec_NoticeBoard)

This is a simple RESTful CRUD (Create Read Update Delete) application for managing Boards, Notices and Authors saved in PostgreSQL database. It provides basic REST endpoints like fetching all objects of given type, finding them by their id, creating them and so on.

![data-model](https://github.com/wkrzywiec/NoticeBoard/blob/master/pics/data-model.png)

## Usage

An application expose 5 REST endpoints for each entity. For example *Notice* (and any other) they are:

* **GET** `{baseURL}/notices/` - lists all *Notices* (as Json array),
* **GET** `{baseURL}/notices/{id}` - gets single *Notice* (as Json) by its `{id}`,
* **POST** `{baseURL}/notices/` - creates a new *Notice* which is passed in the BODY of the request,
* **PUT** `{baseURL}/notices/{id}` - updates an existing *Notice* (with an `{id}`) with `Notice` passed in the body of the request,
* **DELETE** `{baseURL}/notices/{id}`- deletes an existing *Notice* by its `{id}`.

If you run this application locally the `{baseUrl}` would be `http://localhost:8080`. 

All available endpoint are listed on *Swagger UI* page which can be entered, when application is running, under *http://localhost:8080/swagger-ui.html* URL.

![endpoints](https://github.com/wkrzywiec/NoticeBoard/blob/master/pics/notice-endpoints.png)

## Installation

#### Run

Before running the application make sure that you are running PostgreSQL database on your local machine.

In order to run it use following command:

```shell script
mvn clean spring-boot:run
```

#### Integration tests

In this project there are located several integration tests for REST endpoints during which H2 database is used. To run those tests activate Mavan `-P integration-test` profile:

```shell script
mvn clean verify -P integration-test
```

## License 

The MIT License - 2020 - Wojciech Krzywiec
