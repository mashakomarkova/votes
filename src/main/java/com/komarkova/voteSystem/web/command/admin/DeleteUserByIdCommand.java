package com.komarkova.voteSystem.web.command.admin;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.db.DBManager;
import com.komarkova.voteSystem.exception.AppException;
import com.komarkova.voteSystem.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class DeleteUserByIdCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        Long userId = Long.parseLong(request.getParameter("uId"));
        DBManager.getInstance().deleteUserById(userId);

        return Path.VIEW_USERS;
    }
}
