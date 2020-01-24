package com.komarkova.voteSystem.web.restCommand;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.db.DBManager;
import com.komarkova.voteSystem.db.entity.User;
import com.komarkova.voteSystem.exception.AppException;
import com.komarkova.voteSystem.util.Param;
import com.komarkova.voteSystem.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UpdateUserRestCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {

        DBManager manager = DBManager.getInstance();
        String userEmail = request.getParameter("user");
        User user = manager.findUserByEmail(Long.parseLong(userEmail));
        String first_name = Param.getParamUTF8(request.getParameter("first_name"));
        String last_name = Param.getParamUTF8(request.getParameter("last_name"));
        String login = Param.getParamUTF8(request.getParameter("login"));

        user.setFirstName(first_name);
        user.setLastName(last_name);
        user.setLogin(login);
        manager.updateUser(user);
        response.setHeader("success","success");

        return Path.REST_PAGE;
    }
}
