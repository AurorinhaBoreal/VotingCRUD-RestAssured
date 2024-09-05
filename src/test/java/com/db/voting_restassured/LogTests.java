package com.db.voting_restassured;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.db.voting_restassured.fixture.UserFixture;
import com.db.voting_restassured.modal.User;

import io.restassured.http.ContentType;

public class LogTests {
    
    @BeforeEach
    public void setUp() {
        baseURI = "https://votacaocrud.onrender.com";
    }

    @Test
    public void allowGetLogs() {
        User userValid = UserFixture.userValid();
        String userCpf = userValid.getCpf();

        given()
            .when()
            .delete("/user/"+userCpf)
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());

        given()
            .contentType(ContentType.JSON)
            .body(userValid)
            .when()
            .post("/user")
            .then()
            .statusCode(HttpStatus.CREATED.value());

        given()
            .when()
            .get("/log")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("[0].objectInfo", notNullValue());

        given()
            .when()
            .delete("/user/"+userCpf)
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }
}