package com.komarkova.voteSystem.web.command;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class LogoutCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        HttpSession session = request.getSession(false);

        if (session != null){
            session.invalidate();
        }
        return Path.PAGE_LOGIN;
    }
}
