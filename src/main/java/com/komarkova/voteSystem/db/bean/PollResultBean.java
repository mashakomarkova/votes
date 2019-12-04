package com.komarkova.voteSystem.db.bean;

import com.komarkova.voteSystem.db.entity.Entity;

public class PollResultBean extends Entity {
    private String choice;
    private Long counts;

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Long getCounts() {
        return counts;
    }

    public void setCounts(Long counts) {
        this.counts = counts;
    }
}
