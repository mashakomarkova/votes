package com.komarkova.voteSystem.web.command;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.db.DBManager;
import com.komarkova.voteSystem.exception.AppException;
import com.komarkova.voteSystem.util.Param;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class RegisterCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        DBManager manager = DBManager.getInstance();
        String username = Param.getParamUTF8(request.getParameter("username"));
        String email = Param.getParamUTF8(request.getParameter("email"));
        String password = Param.getParamUTF8(request.getParameter("password"));
        String first_name = Param.getParamUTF8(request.getParameter("first_name"));
        String last_name = Param.getParamUTF8(request.getParameter("last_name"));
        manager.createNewUser(username, email, password, first_name, last_name);
        return Path.MAIN_PAGE;
    }
}
