package com.komarkova.voteSystem.db;

public class Fields {

    static final String ENTITY_ID = "user_id";

    static final String USER_EMAIL = "email";
    static final String USER_LOGIN = "username";
    static final String USER_PASSWORD = "password";
    static final String USER_FIRST_NAME = "first_name";
    static final String USER_LAST_NAME = "last_name";
    static final String USER_PICTURE = "user_picture";
    static final String USER_ROLE_ID = "role_id";

    static final String ELECTION_ID = "election_id";
    static final String ELECTION_QUESTION_TEXT = "question_text";
    static final String ELECTION_ACCESS = "access";
    static final String ELECTION_STATUS = "status";
    static final String ELECTION_USER_ID = "user_id";
    static final String ELECTION_DATE_OF_REGISTER = "date_of_register";
    static final String ELECTION_CITY = "city";
    static final String ELECTION_COUNTRY = "country";

    static final String CHOICE_ID = "choice_id";
    static final String CHOICE = "choice";
    static final String COUNTS="counts";

    static final String TRANSACTION_ID="transaction_id";
    static final String TRANSACTION_FIRST_DATE="first_date";
    static final String TRANSACTION_LAST_DATE="last_date";
    static final String TRANSACTION_SUM = "sum";
    static final String TRANSACTION_TYPE = "type";


}
