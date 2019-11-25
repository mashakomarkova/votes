package com.komarkova.voteSystem.db;

import com.komarkova.voteSystem.db.bean.PollResultBean;
import com.komarkova.voteSystem.db.entity.Choice;
import com.komarkova.voteSystem.db.entity.Election;
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

    private static final String SQL_CREATE_ELECTION = "INSERT INTO elections(question_text, access, user_id) VALUES(?,?,?);";

    private static final String SQL_CREATE_CHOICES = "INSERT INTO choices(choice, election_id) VALUES(?,?)";

    private static final String SQL_VOTE_ELECTION = "INSERT INTO votes(user_id, election_id, choice_id) VALUES(?,?,?);";

    //SELECTS
    private static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email=? AND password=MD5(?)";

    private static final String SQL_FIND_ELECTION_ID = "SELECT LAST_INSERT_ID() as 'id';";

    private static final String SQL_FIND_ALL_ELECTIONS = "SELECT * FROM elections WHERE access='public'";

    private static final String SQL_FIND_MY_ELECTIONS = "SELECT elections.election_id, elections.question_text, elections.access, elections.status," +
            " elections.user_id FROM elections, users WHERE users.user_id=elections.user_id AND elections.user_id=?";

    private static final String SQL_FIND_ELECTION_BY_ID = "SELECT * FROM elections WHERE election_id=?";

    private static final String SQL_FIND_CHOICES_BY_ELECTION_ID = "SELECT choices.choice_id, choices.choice, choices.election_id FROM " +
            "choices, elections WHERE elections.election_id=choices.election_id AND choices.election_id=?;";

    private static final String SQL_FIND_USER_BY_EMAIL_ONLY = "SELECT * FROM users WHERE user_id=?";

    private static final String SQL_SEE_RESULTS="select distinct choice, count(*) as 'counts' from choices, votes where " +
            "votes.choice_id=choices.choice_id and votes.election_id=(select election_id from " +
            "elections where election_id=?) group by votes.choice_id;";


    //UPDATES
    private static final String SQL_UPDATE_USER_EMAIL = "UPDATE users SET email=? WHERE user_id=?;";

    private static final String SQL_UPDATE_USER_PASS = "UPDATE users SET password=MD5(?) WHERE user_id=?;";

    private static final String SQL_UPDATE_ELECTION = "UPDATE elections SET question_text=?, access=? WHERE election_id=?;";

    private static final String SQL_UPDATE_CHOICES = "UPDATE choices SET choice=? WHERE choice_id=?;";

    private static final String SQL_UPDATE_USER = "UPDATE users SET first_name=?, last_name=?, user_picture=? WHERE email=?;";

    //DELETES
    private static final String SQL_DELETE_ELECTION_BY_ID = "DELETE FROM elections WHERE election_id=?";


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

    public Long createElection(String questionText, String access, Long userId) {
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

    public List<PollResultBean> seeResults(Long electionId) {
        List<PollResultBean> result = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_SEE_RESULTS);
            pstmt.setLong(1, electionId);
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
        }
        finally {
            close(preparedStatement);
            close(con);
        }
    }
}

