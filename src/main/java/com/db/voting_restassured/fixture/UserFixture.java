package com.db.voting_restassured.fixture;

import com.db.voting_restassured.modal.User;

public class UserFixture {
    public static User userValid() {
        return User.builder()
            .userType("ADMIN")
            .firstName("Rogério")
            .surname("Senna")
            .cpf("51317577116")
            .build();
    }
}
