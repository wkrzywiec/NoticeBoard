package com.wkrzywiec.medium.noticeboard.controller;

import com.google.gson.JsonObject;
import com.wkrzywiec.medium.noticeboard.config.TestDataProvider;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@DisplayName("Integration Tests of the Board CRUD REST endpoints")
public class BoardControllerITCase extends CrudControllerITCase {

    @Test
    @DisplayName("GET a list with 5 Boards")
    public void whenGETfindAll_thenGetListOf5Boards() {
        //when
        ValidatableResponse response = given()
                .when()
                .get( baseURL + "/boards/")

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.OK.value())
                .contentType("application/json")
                .body("size()", greaterThanOrEqualTo(4));
    }

    @Test
    @DisplayName("GET a Board by Id")
    public void givenBoardId_thenGetSingleBoard() {
        //given
        Long boardId = 2L;

        //when
        ValidatableResponse response = given()
                .when()
                .get(baseURL + "/boards/" + boardId)

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.OK.value())
                .contentType("application/json")
                .body("id", equalTo(2))
                .body("title", equalTo("Board 2 title"))
                .body("noticeList[0].id", equalTo(4))
                .body("noticeList[0].author.firstName", equalTo("John"))
                .body("noticeList[0].author.lastName", equalTo("Doe"));
    }

    @Test
    @DisplayName("POST a Board to create it")
    public void givenBoard_whenPOSTSave_thenGetSavedBoard(){
        //given
        JsonObject boardJson = TestDataProvider.getBoardJson();

        //when
        ValidatableResponse response = given()
                .contentType("application/json")
                .body(boardJson.toString())

                .when()
                .post(baseURL + "/boards/")

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("title", equalTo("Board TEST title"))
                .body("noticeList[0].id",  notNullValue())
                .body("noticeList[0].title", equalTo("Notice TEST title"))
                .body("noticeList[0].description", equalTo("Notice TEST description"));
    }

    @Test
    @DisplayName("DELETE a Board by Id")
    public void givenBoardId_whenDELETEbyId_thenBoardIsDeleted() {
        //given
        Long boardId = 3L;

        //when
        ValidatableResponse response = given()
                .contentType("application/json")

                .when()
                .delete(baseURL + "/boards/" + boardId)

                .prettyPeek()
                .then();

        response.statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("PUT a Board by Id to update it")
    public void givenIdAndUpdatedBoard_whenPUTUpdate_thenBoardIsUpdated() {
        //given
        Long boardId = 4L;
        JsonObject boardJson = TestDataProvider.getBoardJson();

        //when
        ValidatableResponse response = given()
                .contentType("application/json")
                .body(boardJson.toString())

                .when()
                .put(baseURL + "/boards/" + boardId)

                .prettyPeek()
                .then();

        response.statusCode(HttpStatus.OK.value());
    }
}
