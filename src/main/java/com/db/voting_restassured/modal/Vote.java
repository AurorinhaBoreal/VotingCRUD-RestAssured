package com.db.voting_restassured.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Vote {
    private String question;
    private String cpf;
    private String vote;
}
