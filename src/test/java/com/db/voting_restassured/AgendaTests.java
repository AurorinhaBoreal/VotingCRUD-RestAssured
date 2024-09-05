package com.db.voting_restassured;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.db.voting_restassured.fixture.AgendaFixture;
import com.db.voting_restassured.fixture.UserFixture;
import com.db.voting_restassured.fixture.VoteFixture;
import com.db.voting_restassured.modal.Agenda;
import com.db.voting_restassured.modal.User;
import com.db.voting_restassured.modal.Vote;

import io.restassured.http.ContentType;

public class AgendaTests {

    @BeforeEach
    public void setUp() {
        baseURI = "https://votacaocrud.onrender.com";
    }
  
    @Test
    public void allowAgendaCreationEndingAndDeletion() {
        User userValid = UserFixture.userValid();
        String userCpf = userValid.getCpf();
        Agenda agendaValid = AgendaFixture.agendaValid();
        String agendaQuestion = agendaValid.getQuestion();
        agendaQuestion = agendaQuestion.substring(0, agendaQuestion.length() - 1);

        given()
            .contentType(ContentType.JSON)
            .body(userValid)
            .when()
            .post("/user")
            .then()
            .statusCode(HttpStatus.CREATED.value());

        given()
            .contentType(ContentType.JSON)
            .body(agendaValid)
            .when()
            .post("/agenda")
            .then()
            .statusCode(HttpStatus.CREATED.value());

        given()
            .when()
            .put("/agenda/end/"+agendaQuestion)
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());

        given()
            .when()
            .delete("/agenda/"+agendaQuestion)
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());

        given()
            .when()
            .delete("/user/"+userCpf)
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void allowVoteOnAgenda() {
        User userValid = UserFixture.userValid();
        String userCpf = userValid.getCpf();
        Agenda agendaValid = AgendaFixture.agendaValid();
        String agendaQuestion = agendaValid.getQuestion();
        agendaQuestion = agendaQuestion.substring(0, agendaQuestion.length() - 1);
        Vote voteValid = VoteFixture.validVote();
                
        given()
            .contentType(ContentType.JSON)
            .body(userValid)
            .when()
            .post("/user")
            .then()
            .statusCode(HttpStatus.CREATED.value());

        given()
            .contentType(ContentType.JSON)
            .body(agendaValid)
            .when()
            .post("/agenda")
            .then()
            .statusCode(HttpStatus.CREATED.value());

        given()
            .contentType(ContentType.JSON)
            .body(voteValid)
            .then()
            .statusCode(HttpStatus.OK.value());

        given()
            .when()
            .put("/agenda/end/"+agendaQuestion)
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());

        given()
            .when()
            .delete("/agenda/"+agendaQuestion)
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());

        given()
            .when()
            .delete("/user/"+userCpf)
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void notAllowAgendaCreation() {
        User userValid = UserFixture.userValid();
        String userCpf = userValid.getCpf();
        Agenda agendaInvalid = AgendaFixture.agendaInvalid();

        given()
            .contentType(ContentType.JSON)
            .body(userValid)
            .when()
            .post("/user")
            .then()
            .statusCode(HttpStatus.CREATED.value());

        given()
            .contentType(ContentType.JSON)
            .body(agendaInvalid)
            .when()
            .post("/agenda")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());

        given()
            .when()
            .delete("/user/"+userCpf)
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void allowListUnactiveAgendas() {
        given()
            .when()
            .get("/agenda")
            .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void allowListActiveAgendas() {
        given()
            .when()
            .get("/agenda/active")
            .then()
            .statusCode(HttpStatus.OK.value());
    }
}
