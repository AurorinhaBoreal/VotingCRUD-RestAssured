package com.db.voting_restassured;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.db.voting_restassured.fixture.UserFixture;
import com.db.voting_restassured.modal.User;

import io.restassured.http.ContentType;

public class UserTests {
    
    @BeforeEach
    public void setUp() {
        baseURI = "https://votacaocrud.onrender.com";
    }

    @Test
    public void allowUserCreationValidationAndDeletion() {
        User userValid = UserFixture.userValid();
        String userCpf = userValid.getCpf();

        given()
            .contentType(ContentType.JSON)
            .body(userValid)
            .when()
            .post("/user")
            .then()
            .statusCode(HttpStatus.CREATED.value());
        
        given()
            .contentType(ContentType.JSON)
            .body(userCpf)
            .when()
            .post("/user/validate")
            .then()
            .statusCode(HttpStatus.ACCEPTED.value());

        given()
            .when()
            .delete("/user/"+userCpf)
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void notAllowUserCreation() {
        User userValid = UserFixture.userValid();

        given()
            .contentType(ContentType.JSON)
            .body(userValid)
            .when()
            .post("/user")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void allowListUserSpecific() {
        User userValid = UserFixture.userValid();
        String userCpf = userValid.getCpf();

        given()
            .contentType(ContentType.JSON)
            .body(userValid)
            .when()
            .post("/user")
            .then()
            .statusCode(HttpStatus.CREATED.value());
        
        given()
            .body(userCpf)
            .when()
            .get("/user/specific")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("cpf", equalTo(userCpf));

        given()
            .when()
            .delete("/user/"+userCpf)
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void allowListUsers() {
        User userValid = UserFixture.userValid();
        String userCpf = userValid.getCpf();

        given()
            .contentType(ContentType.JSON)
            .body(userValid)
            .when()
            .post("/user")
            .then()
            .statusCode(HttpStatus.CREATED.value());
        
        given()
            .body(userCpf)
            .when()
            .get("/user")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("[0].cpf", notNullValue());

        given()
            .when()
            .delete("/user/"+userCpf)
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
