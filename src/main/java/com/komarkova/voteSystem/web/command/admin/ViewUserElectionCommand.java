package com.komarkova.voteSystem.web.command.admin;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.db.DBManager;
import com.komarkova.voteSystem.db.bean.UserElectionBean;
import com.komarkova.voteSystem.exception.AppException;
import com.komarkova.voteSystem.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public class ViewUserElectionCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        List<UserElectionBean> userElectionBean = DBManager.getInstance().findUsersElections();
        request.setAttribute("userElectionBean",userElectionBean);

        return Path.USERS_ELECTIONS;
    }
}
