package com.wkrzywiec.medium.noticeboard.controller;

import com.wkrzywiec.medium.noticeboard.config.IntegrationTestConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(IntegrationTestConfig.class)
@DisplayName("Integration tests of the NoticeController")
public class NoticeControllerITCase {

    @LocalServerPort
    private int port;

    @Autowired
    private EntityManager entityManager;

    private String baseURL;

    @BeforeEach
    public void init(){
       this.baseURL = "http://localhost:" + port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void givenSingleNotice_whenGETAllNotices_thenGetSingleNoticeList() {
        //when
        given()
                .when()
                .get( baseURL + "/notices/")
                    .prettyPeek()
                .then()
                .statusCode(200)
                .body("size()", is(4));
    }
}
