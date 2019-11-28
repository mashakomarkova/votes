package com.komarkova.voteSystem.db.entity;

public class Election extends Entity {
    private String questionText;
    private String access;
    private String status;
    private Long userId;
    private String dateOfRegister;
    private String city;
    private String country;

    public String getDateOfRegister() {
        return dateOfRegister;
    }

    public void setDateOfRegister(String dateOfRegister) {
        this.dateOfRegister = dateOfRegister;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Election{" +
                "questionText='" + questionText + '\'' +
                ", access='" + access + '\'' +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                ", dateOfRegister='" + dateOfRegister + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Election election = (Election)obj;
        return questionText.equals(election.questionText) && access.equals(election.access)
                && status.equals(election.status) && userId.equals(election.userId) &&
                dateOfRegister.equals(election.dateOfRegister) && city.equals(election.city) &&
                country.equals(election.country);
    }
}
