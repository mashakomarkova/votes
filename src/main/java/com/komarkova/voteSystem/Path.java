package com.komarkova.voteSystem;

public class Path {

    public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
    public static final String PAGE_PROFILE = "/WEB-INF/jsp/profile.jsp";
    public static final String PAGE_SUCCESS = "/WEB-INF/jsp/success.jsp";
    public static final String PAGE_LOGIN = "login.jsp";
    public static final String MAIN_PAGE = "index.jsp";

    public static final String PAGE_ELECTION = "/WEB-INF/jsp/client/create_election.jsp";
    public static final String PAGE_ALL_ELECTIONS = "/WEB-INF/jsp/all_elections.jsp";
    public static final String PAGE_MY_ELECTIONS = "/WEB-INF/jsp/my_elections.jsp";
    public static final String PAGE_MANAGE_ELECTION = "/WEB-INF/jsp/manage_my_election.jsp";
    public static final String ELECTION_PARTICIPATION = "/WEB-INF/jsp/election_participation.jsp";
    public static final String COMMAND_MY_ELECTIONS= "controller?command=myElections";
    public static final String COMMAND_ALL_ELECTIONS= "controller?command=viewAllElections";
    public static final String POLL_RESULT = "/WEB-INF/jsp/poll_result.jsp";
    public static final String MY_TRANSACTIONS = "/WEB-INF/jsp/client/my_transactions.jsp";
    public static final String FAVORITE_ELECTIONS = "/WEB-INF/jsp/client/favorite_elections.jsp";
    public static final String MANAGEMENT_USERS = "/WEB-INF/jsp/admin/manage_users.jsp";
    public static final String REST_PAGE = "/WEB-INF/jsp/restPage.jsp";
    public static final String USERS_ELECTIONS = "/WEB-INF/jsp/admin/users_elections.jsp";
    public static final String VIEW_USERS ="controller?command=manageUsers" ;
    public static final String COMMAND_ADMIN_ELECTIONS = "controller?command=userElections";
}
