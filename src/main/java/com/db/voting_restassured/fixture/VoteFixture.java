package com.db.voting_restassured.fixture;

import com.db.voting_restassured.modal.Vote;

public class VoteFixture {
    public static Vote validVote() {
        return Vote.builder()
            .question("Do you like Futboll?")
            .vote("YES")
            .cpf("51317577116")
            .build();
    }
}
