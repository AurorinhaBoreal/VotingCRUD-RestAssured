package com.db.voting_restassured.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class User {
    private String userType;
    private String firstName;
    private String surname;
    private String cpf;
}
