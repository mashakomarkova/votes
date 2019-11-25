package com.komarkova.voteSystem.db.entity;

public class Choice extends Entity {
    private String choice;
    private Long electionId;

    public String getChoice() {
        return choice;
        
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Long getElectionId() {
        return electionId;
    }

    public void setElectionId(Long electionId) {
        this.electionId = electionId;
    }
}
