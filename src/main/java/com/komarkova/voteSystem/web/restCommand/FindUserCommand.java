package com.komarkova.voteSystem.web.restCommand;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.db.DBManager;
import com.komarkova.voteSystem.db.Role;
import com.komarkova.voteSystem.db.entity.User;
import com.komarkova.voteSystem.exception.AppException;
import com.komarkova.voteSystem.util.Param;
import com.komarkova.voteSystem.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class FindUserCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        HttpSession session = request.getSession();

        DBManager manager = DBManager.getInstance();
        String email = Param.getParamUTF8(request.getParameter("email"));
        String password = Param.getParamUTF8(request.getParameter("password"));
        String messageLogin = "There is no user with such credentials";

        if (email.equals("") || password.equals("") || email.isEmpty() || password.isEmpty()) {
            messageLogin = "Login/password cannot be empty";
            request.setAttribute("messageLogin", messageLogin);
            request.setAttribute("emptyLogin", "true");
            throw new AppException("Login/password cannot be empty");
        }
        User user = manager.findUserByEmailAndPassword(email, password);
        if (user == null) {
            request.setAttribute("messageLogin", messageLogin);
            request.setAttribute("incorrectUser","true");
            throw new AppException("Cannot find user with such login/password");
        }

        Role userRole = Role.getRole(user);
        session.setAttribute("user", user);
        response.setHeader("firstName",user.getFirstName());
        response.setHeader("lastName",user.getLastName());
        response.setHeader("login",user.getLogin());
        response.setHeader("user",user.toString());

        return Path.REST_PAGE;
    }
}
