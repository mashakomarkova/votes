package com.komarkova.voteSystem.db;

import com.komarkova.voteSystem.db.bean.PollResultBean;
import com.komarkova.voteSystem.db.bean.UserElectionBean;
import com.komarkova.voteSystem.db.entity.Choice;
import com.komarkova.voteSystem.db.entity.Election;
import com.komarkova.voteSystem.db.entity.Transaction;
import com.komarkova.voteSystem.db.entity.User;
import com.komarkova.voteSystem.exception.DBException;
import com.komarkova.voteSystem.exception.Messages;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private static DBManager instance;
    private DataSource ds;

    //INSERTS

    private final static String SQL_INSERT_USER = "INSERT INTO users(username, email, password," +
            "first_name, last_name) VALUES(?, ?, MD5(?), ?, ?);";

    private static final String SQL_CREATE_ELECTION = "INSERT INTO elections(question_text, access, status, user_id, date_of_register, city, country) VALUES(?,?,'plain',?, CURDATE(),?,? );";

    private static final String SQL_CREATE_CHOICES = "INSERT INTO choices(choice, election_id) VALUES(?,?)";

    private static final String SQL_VOTE_ELECTION = "INSERT INTO votes(user_id, election_id, choice_id) VALUES(?,?,?);";

    private static final String SQL_ADD_TOPIC = "INSERT INTO topics(topic,election_id) VALUES(?,?)";

    private static final String SQL_ADD_TRANSACTION = "INSERT INTO transactions(first_date, last_date, sum, type, election_id) VALUES(CURDATE(),DATE_ADD(CURDATE(), INTERVAL ? DAY),2*?,'top', ?);";


    //SELECTS

    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM users where role_id=1";


    private static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email=? AND password=MD5(?)";

    private static final String SQL_PAGES_TOTAL = "SELECT count(*) as 'totalEl', count(*)/5 as 'pages' FROM elections where access='public';";

    private static final String SQL_FIND_ELECTION_ID = "SELECT LAST_INSERT_ID() as 'id';";

    private static final String SQL_FIND_ALL_ELECTIONS_REST = "SELECT * FROM elections WHERE access='public'";

    private static final String SQL_FIND_ALL_ELECTIONS = "SELECT * FROM elections WHERE access='public' limit 5";

    private static final String SQL_FIND_TOP_ELECTIONS = "SELECT * FROM elections where access='public' and status='top' " +
            "ORDER BY rand(), date_of_register desc LIMIT 5;";

    private static final String SQL_FIND_USER_PARTICIPATED = "SELECT votes.user_id FROM votes, elections, users where votes.user_id=users.user_id " +
            "and votes.election_id=elections.election_id and votes.user_id=? and votes.election_id=?;";


    private static final String SQL_SEARCH_ELECTIONS = "SELECT distinct elections.election_id, elections.question_text, elections.access," +
            "elections.status, elections.user_id, elections.date_of_register, elections.city, elections.country" +
            "  FROM elections, topics WHERE elections.access='public' and topics.election_id=elections.election_id and " +
            "elections.question_text like CONCAT('%',?,'%') OR elections.country like ? or elections.city like ? or" +
            " topics.topic=?";

    private static final String SQL_FIND_MY_ELECTIONS = "SELECT elections.election_id, elections.question_text, elections.access, elections.status," +
            " elections.user_id, elections.date_of_register, elections.city, elections.country FROM elections, users WHERE users.user_id=elections.user_id AND elections.user_id=? and status<>''";

    private static final String SQL_FIND_ELECTION_BY_ID = "SELECT * FROM elections WHERE election_id=?";

    private static final String SQL_FIND_CHOICES_BY_ELECTION_ID = "SELECT choices.choice_id, choices.choice, choices.election_id FROM " +
            "choices, elections WHERE elections.election_id=choices.election_id AND choices.election_id=?;";

    private static final String SQL_FIND_USER_BY_EMAIL_ONLY = "SELECT * FROM users WHERE user_id=?";

//    private static final String SQL_SEE_RESULTS = "select distinct choice, count(*) as 'counts' from choices, votes where " +
//            "votes.choice_id=choices.choice_id and votes.election_id=(select election_id from " +
//            "elections where election_id=?) group by votes.choice_id;";

    private static final String SQL_SEE_RESULTS = "select t2.choice, t1.counts from (select choice, count(*) as 'counts' from choices, votes where " +
            "votes.choice_id=choices.choice_id and votes.election_id=(select election_id from " +
            "elections where election_id=?) group by votes.choice_id) as t1 right outer join " +
            "(select choice from choices where election_id=?) as t2 on (t1.choice=t2.choice);";

    private static final String SQL_FIND_TRANSACTIONS = "SELECT transactions.transaction_id, transactions.first_date, transactions.last_date, transactions.type, transactions.sum, " +
            "transactions.election_id FROM transactions, elections, users " +
            "WHERE transactions.election_id=elections.election_id AND elections.user_id=users.user_id " +
            "AND elections.user_id=?;";

    private static final String SQL_SORT_ELECTIONS = "SELECT * FROM elections ORDER BY ?;";

    private static final String SQL_SORT_BY_POPULARITY = "SELECT elections.election_id,elections.question_text, elections.access, elections.status, " +
            "elections.user_id, elections.date_of_register, elections.city, elections.country " +
            "FROM elections, votes where votes.election_id=elections.election_id " +
            "group by votes.election_id having count(*);";

    private static final String SQL_COUNT_OF_ELECTIONS_USER = "SELECT count(*) as 'counts' FROM elections, users " +
            "where elections.user_id=users.user_id " +
            "and elections.user_id=? and elections.election_id not in(select transactions.election_id from transactions, elections " +
            "where transactions.election_id=elections.election_id and " +
            "transactions.type='election' and transactions.last_date > curdate()) and elections.access='public' " +
            "group by elections.user_id;";

    private static final String SQL_FIND_ELECTION_BY_PAGE = "SELECT * FROM elections WHERE access='public' LIMIT ?,5";

    private static final String SQL_ALL_VOTES_FOR_ELECTION = "SELECT count(*) as 'counts' FROM votes where election_id=?;";

    private static final String SQL_USER_ELECTION = "SELECT elections.election_id, users.username, users.first_name, users.last_name, " +
            "elections.question_text, elections.date_of_register, elections.city, " +
            "elections.country from users, elections " +
            "where elections.user_id=users.user_id and elections.access='public';";

    //UPDATES
    private static final String SQL_UPDATE_USER_EMAIL = "UPDATE users SET email=? WHERE user_id=?;";

    private static final String SQL_UPDATE_ELECTION_STATUS = "update elections set status='top' where election_id=?";

    private static final String SQL_UPDATE_USER_ROLE = "UPDATE users SET role_id=? WHERE user_id=?;";

    private static final String SQL_UPDATE_USER_PASS = "UPDATE users SET password=MD5(?) WHERE user_id=?;";

    private static final String SQL_UPDATE_ELECTION = "UPDATE elections SET question_text=?, access=? WHERE election_id=?;";

    private static final String SQL_UPDATE_CHOICES = "UPDATE choices SET choice=? WHERE choice_id=?;";

    private static final String SQL_UPDATE_USER = "UPDATE users SET first_name=?, last_name=?, user_picture=? WHERE email=?;";

    private static final String SQL_UPDATE_ELECTION_TO_PLAIN="update elections set status='plain' where election_id in (" +
            "select election_id from transactions where last_date <= curdate());";

    //DELETES
    private static final String SQL_DELETE_ELECTION_BY_ID = "DELETE FROM elections WHERE election_id=?";

    private static final String SQL_DELETE_ELECTIONS = "DELETE FROM elections WHERE CURDATE()-DATE(date_of_register)>=30";

    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE user_id=?";


    public static synchronized DBManager getInstance() throws DBException {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() throws DBException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/ST4DB");

        } catch (NamingException ex) {
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
        }
    }

    /**
     * Returns a DB connection from the Pool Connections.
     *
     * @return DB connection.
     */
    public Connection getConnection() throws DBException {
        Connection con = null;
        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
        }
        return con;
    }

    public void createNewUser(String username, String email, String password, String first_name, String last_name) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_INSERT_USER);
            int k = 1;
            pstmt.setString(k++, username);
            pstmt.setString(k++, email);
            pstmt.setString(k++, password);
            pstmt.setString(k++, first_name);
            pstmt.setString(k++, last_name);
            pstmt.executeUpdate();
            con.commit();

        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(con);
        }
    }


    /**
     * Finds user by email and password.
     *
     * @param email    Email.
     * @param password Password
     * @return User object
     */
    public User findUserByEmailAndPassword(String email, String password) throws DBException {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_EMAIL);
            int k = 1;
            pstmt.setString(k++, email);
            pstmt.setString(k++, password);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return user;
    }

    /**
     * Extracts a user entity from the result set.
     *
     * @param rs Result set from which a user entity will be extracted.
     * @return User object
     */
    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(Fields.ENTITY_ID));
        user.setEmail(rs.getString(Fields.USER_EMAIL));
        user.setLogin(rs.getString(Fields.USER_LOGIN));
        user.setPassword(rs.getString(Fields.USER_PASSWORD));
        user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
        user.setLastName(rs.getString(Fields.USER_LAST_NAME));
        user.setUserPicture(rs.getBlob(Fields.USER_PICTURE));
        user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
        return user;
    }


    private UserElectionBean extractUserElection(ResultSet rs) throws SQLException {
        UserElectionBean userElectionBean = new UserElectionBean();
        userElectionBean.setId(rs.getLong(Fields.ELECTION_ID));
        userElectionBean.setUsername(rs.getString(Fields.USER_LOGIN));
        userElectionBean.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
        userElectionBean.setLastName(rs.getString(Fields.USER_LAST_NAME));
        userElectionBean.setQuestionText(rs.getString(Fields.ELECTION_QUESTION_TEXT));
        userElectionBean.setDateOfRegister(rs.getString(Fields.ELECTION_DATE_OF_REGISTER));
        userElectionBean.setCity(rs.getString(Fields.ELECTION_CITY));
        userElectionBean.setCountry(rs.getString(Fields.ELECTION_COUNTRY));
        return userElectionBean;
    }


    private void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    private void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Closes resources.
     */
    private void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }

    private void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Finds user by id
     *
     * @param id User id.
     * @return User object
     */
    public User findUserByEmail(Long id) throws DBException {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_EMAIL_ONLY);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return user;
    }

    /**
     * Updates user's email
     *
     * @param user User object.
     */
    public void updateUserEmail(User user) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(SQL_UPDATE_USER_EMAIL);
            int k = 1;
            preparedStatement.setString(k++, user.getEmail());
            preparedStatement.setLong(k++, user.getId());

            preparedStatement.executeUpdate();
            con.commit();

        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(con);
        }
    }

    public void updateElectionToPlain() {
        Connection con = null;
        Statement stmt = null;
        try {
            con = getConnection();

            stmt = con.createStatement();
            stmt.executeUpdate(SQL_UPDATE_ELECTION_TO_PLAIN);
            con.commit();

        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(stmt);
            close(con);
        }
    }

    public void updateUserRoleId(User user) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(SQL_UPDATE_USER_ROLE);
            int k = 1;
            preparedStatement.setInt(k++, user.getRoleId());
            preparedStatement.setLong(k++, user.getId());

            preparedStatement.executeUpdate();
            con.commit();

        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(con);
        }
    }

    /**
     * Updates user's password
     *
     * @param user User object.
     */
    public void updateUserPass(User user) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(SQL_UPDATE_USER_PASS);
            int k = 1;
            preparedStatement.setString(k++, user.getPassword());
            preparedStatement.setLong(k++, user.getId());

            preparedStatement.executeUpdate();
            con.commit();

        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(con);
        }
    }

    /**
     * Updates user
     *
     * @param user User object.
     */
    public void updateUser(User user) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(SQL_UPDATE_USER);
            int k = 1;
            preparedStatement.setString(k++, user.getFirstName());
            preparedStatement.setString(k++, user.getLastName());
            preparedStatement.setBlob(k++, user.getUserPicture());
            preparedStatement.setString(k++, user.getEmail());
            preparedStatement.executeUpdate();
            con.commit();

        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(con);
        }
    }

    public void addTopic(String topic, Long electionId) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(SQL_ADD_TOPIC);

            int k = 1;
            preparedStatement.setString(k++, topic);
            preparedStatement.setLong(k++, electionId);
            preparedStatement.executeUpdate();
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(con);
        }
    }

    public Long createElection(String questionText, String access, Long userId, String city, String country) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        Statement stmt = null;
        ResultSet rs = null;
        Long res = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(SQL_CREATE_ELECTION);

            int k = 1;
            preparedStatement.setString(k++, questionText);
            preparedStatement.setString(k++, access);
            preparedStatement.setLong(k++, userId);
            preparedStatement.setString(k++, city);
            preparedStatement.setString(k++, country);
            preparedStatement.executeUpdate();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ELECTION_ID);
            while (rs.next()) {
                res = rs.getLong("id");
            }
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(con);
        }
        return res;
    }

    public void addTransaction(Long electionId, int days) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(SQL_ADD_TRANSACTION);
            preparedStatement2 = con.prepareStatement(SQL_UPDATE_ELECTION_STATUS);
            preparedStatement2.setLong(1, electionId);
            int k = 1;
            preparedStatement.setInt(k++, days);
            preparedStatement.setInt(k++, days);
            preparedStatement.setLong(k++, electionId);

            preparedStatement.executeUpdate();
            preparedStatement2.executeUpdate();
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(preparedStatement2);
            close(con);
        }
    }

    public void createChoice(String[] choices, Long electionId) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = getConnection();
            for (String choice : choices) {
                preparedStatement = con.prepareStatement(SQL_CREATE_CHOICES);
                int k = 1;
                preparedStatement.setString(k++, choice);
                preparedStatement.setLong(k++, electionId);
                preparedStatement.executeUpdate();
            }
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(con);
        }
    }

    public List<User> findAllUsers() {
        List<User> allUsers = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_USERS);
            while (rs.next()) {
                allUsers.add(extractUser(rs));

            }
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(con, stmt, rs);
        }
        return allUsers;
    }

    public List<Election> findTopElections() {
        List<Election> elections = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_TOP_ELECTIONS);
            while (rs.next()) {
                elections.add(extractAllElections(rs));

            }
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(con, stmt, rs);
        }
        return elections;
    }

    public List<String> numberOfPages() {
        List<String> elections = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_PAGES_TOTAL);
            while (rs.next()) {
                elections.add(rs.getString("totalEl"));
                elections.add(rs.getString("pages"));
            }
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(con, stmt, rs);
        }
        return elections;
    }

    public List<Election> findAllElectionsByPage(Long pages, Long t) {
        List<Election> elections = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        double p = Math.round((double)(t / pages));

        long res = (long)(t - p);
        try {
            con = getConnection();
            int k = 1;
            pstmt = con.prepareStatement(SQL_FIND_ELECTION_BY_PAGE);
            pstmt.setLong(k++, res);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                elections.add(extractAllElections(rs));
            }
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(con, pstmt, rs);
        }
        return elections;
    }
    public List<Election> findAllElectionsRest() {
        List<Election> elections = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_ELECTIONS_REST);
            while (rs.next()) {
                elections.add(extractAllElections(rs));

            }
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(con, stmt, rs);
        }
        return elections;
    }
    public List<Election> findAllElections() {
        List<Election> elections = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_ELECTIONS);
            while (rs.next()) {
                elections.add(extractAllElections(rs));

            }
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(con, stmt, rs);
        }
        return elections;
    }

    public List<Election> searchElections(String keys, String country, String city, String topic) {
        List<Election> elections = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            int k = 1;
            pstmt = con.prepareStatement(SQL_SEARCH_ELECTIONS);
            pstmt.setString(k++, keys);
            pstmt.setString(k++, country);
            pstmt.setString(k++, city);
            pstmt.setString(k++, topic);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                elections.add(extractAllElections(rs));
            }
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(con, pstmt, rs);
        }
        return elections;
    }

    public List<Election> findMyElections(Long userId) {
        List<Election> elections = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_MY_ELECTIONS);
            pstmt.setLong(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                elections.add(extractAllElections(rs));
            }
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(con, pstmt, rs);
        }
        return elections;
    }

    public Election findElectionById(Long electionId) throws DBException {
        Election election = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_ELECTION_BY_ID);
            int k = 1;
            pstmt.setLong(k++, electionId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                election = extractAllElections(rs);
            }
            con.commit();
        } catch (SQLException | DBException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return election;
    }

    public List<Choice> findChoices(Long electionId) {
        List<Choice> choices = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_CHOICES_BY_ELECTION_ID);
            pstmt.setLong(1, electionId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                choices.add(extractChoice(rs));
            }
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(con, pstmt, rs);
        }
        return choices;
    }

    public List<Transaction> findMyTransactions(Long userId) {
        List<Transaction> myTransactions = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_TRANSACTIONS);
            pstmt.setLong(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                myTransactions.add(extractTransaction(rs));
            }
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(con, pstmt, rs);
        }
        return myTransactions;
    }

    public List<UserElectionBean> findUsersElections() {
        List<UserElectionBean> result = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_USER_ELECTION);
            while (rs.next()) {
                result.add(extractUserElection(rs));
            }
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(con, stmt, rs);
        }
        return result;
    }

    public List<PollResultBean> seeResults(Long electionId) {
        List<PollResultBean> result = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            int k = 1;
            pstmt = con.prepareStatement(SQL_SEE_RESULTS);
            pstmt.setLong(k++, electionId);
            pstmt.setLong(k++, electionId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(extractResultBean(rs));
            }
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(con, pstmt, rs);
        }
        return result;
    }

    private PollResultBean extractResultBean(ResultSet rs) throws SQLException {
        PollResultBean resultBean = new PollResultBean();
        resultBean.setChoice(rs.getString(Fields.CHOICE));
        resultBean.setCounts(rs.getLong(Fields.COUNTS));
        return resultBean;
    }

    private Transaction extractTransaction(ResultSet rs) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(rs.getLong(Fields.TRANSACTION_ID));
        transaction.setFirstDate(rs.getString(Fields.TRANSACTION_FIRST_DATE));
        transaction.setLastDate(rs.getString(Fields.TRANSACTION_LAST_DATE));
        transaction.setSum(rs.getString(Fields.TRANSACTION_SUM));
        transaction.setType(rs.getString(Fields.TRANSACTION_TYPE));
        transaction.setElectionId(rs.getLong(Fields.ELECTION_ID));
        return transaction;
    }

    private Choice extractChoice(ResultSet rs) throws SQLException {
        Choice choice = new Choice();
        choice.setId(rs.getLong(Fields.CHOICE_ID));
        choice.setChoice(rs.getString(Fields.CHOICE));
        choice.setElectionId(rs.getLong(Fields.ELECTION_ID));
        return choice;
    }


    private Election extractAllElections(ResultSet rs) throws SQLException {
        Election election = new Election();
        election.setId(rs.getLong(Fields.ELECTION_ID));
        election.setQuestionText(rs.getString(Fields.ELECTION_QUESTION_TEXT));
        election.setAccess(rs.getString(Fields.ELECTION_ACCESS));
        election.setStatus(rs.getString(Fields.ELECTION_STATUS));
        election.setUserId(rs.getLong(Fields.ELECTION_USER_ID));
        election.setDateOfRegister(rs.getString(Fields.ELECTION_DATE_OF_REGISTER));
        election.setCity(rs.getString(Fields.ELECTION_CITY));
        election.setCountry(rs.getString(Fields.ELECTION_COUNTRY));
        return election;
    }

    public void participateInElection(Long userId, Long electionId, Long choiceId) {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(SQL_VOTE_ELECTION);
            int k = 1;
            preparedStatement.setLong(k++, userId);
            preparedStatement.setLong(k++, electionId);
            preparedStatement.setLong(k++, choiceId);
            preparedStatement.executeUpdate();
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(con);
        }
    }

    public Long countElectionsUser(long userId) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        long res = 0;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(SQL_COUNT_OF_ELECTIONS_USER);
            int k = 1;
            preparedStatement.setLong(k++, userId);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                res = rs.getLong("counts");
            }
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(con);
        }
        return res;
    }

    public void deleteUserById(Long userId) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(SQL_DELETE_USER_BY_ID);
            preparedStatement.setLong(1, userId);
            preparedStatement.executeUpdate();
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(con);
        }
    }

    public void deleteElectionById(Long electionId) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(SQL_DELETE_ELECTION_BY_ID);
            preparedStatement.setLong(1, electionId);
            preparedStatement.executeUpdate();
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(con);
        }
    }

    public void deleteElection() {
        Connection con = null;
        Statement stmt = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(SQL_DELETE_ELECTIONS);
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(stmt);
            close(con);
        }
    }

    public void updateElection(String questionText, String access, Long electionId) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(SQL_UPDATE_ELECTION);
            int k = 1;
            preparedStatement.setString(k++, questionText);
            preparedStatement.setString(k++, access);
            preparedStatement.setLong(k++, electionId);
            preparedStatement.executeUpdate();
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(con);
        }
    }

    public void updateChoices(String[] choices, Long[] chociceIds) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = getConnection();
            for (int i = 0; i < choices.length; i++) {
                preparedStatement = con.prepareStatement(SQL_UPDATE_CHOICES);
                int k = 1;
                preparedStatement.setString(k++, choices[i]);
                preparedStatement.setLong(k++, chociceIds[i]);
                preparedStatement.executeUpdate();
            }
            con.commit();
        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(preparedStatement);
            close(con);
        }
    }


    public List<Election> sortAllElections(String option) {
        List<Election> elections = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            if ("date_of_register".equals(option)) {
                pstmt = con.prepareStatement(SQL_SORT_ELECTIONS);
                int k = 1;
                pstmt.setString(k++, option);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    elections.add(extractAllElections(rs));
                }
                con.commit();

            } else if ("popular".equals(option)) {
                pstmt = con.prepareStatement(SQL_SORT_BY_POPULARITY);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    elections.add(extractAllElections(rs));
                }
                con.commit();
            }

        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(con, pstmt, rs);
        }
        return elections;
    }


    public boolean hasParticipatedInThisElection(Long userId, Long electionId) {
        List<String> userParticipated = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_USER_PARTICIPATED);
            int k = 1;
            pstmt.setLong(k++, userId);
            pstmt.setLong(k++, electionId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                userParticipated.add(rs.getString(Fields.ENTITY_ID));
            }
            con.commit();

        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(con, pstmt, rs);
        }
        return userParticipated.size() >= 1;
    }

    public Long numberOfVotes(Long electionId) {
        long res = 0L;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_ALL_VOTES_FOR_ELECTION);
            int k = 1;
            pstmt.setLong(k++, electionId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                res = rs.getLong(Fields.COUNTS);
            }
            con.commit();

        } catch (DBException | SQLException e) {
            rollback(con);
            e.printStackTrace();
        } finally {
            close(con, pstmt, rs);
        }
        return res;
    }



}

