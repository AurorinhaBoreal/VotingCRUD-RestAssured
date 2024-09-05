package com.db.voting_restassured.fixture;

import com.db.voting_restassured.modal.Agenda;

public class AgendaFixture {
    
    public static Agenda agendaValid() {
        return Agenda.builder()
            .category("SPORTS")
            .question("Do you like Futboll?")
            .cpf("51317577116")
            .duration(10)
            .build();
    }
}
