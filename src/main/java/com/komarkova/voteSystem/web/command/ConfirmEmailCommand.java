package com.komarkova.voteSystem.web.command;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.db.DBManager;
import com.komarkova.voteSystem.db.entity.User;
import com.komarkova.voteSystem.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class ConfirmEmailCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        String v = request.getParameter("v");
        String ui = request.getParameter("ui");
        User user = DBManager.getInstance().findUserByEmail(Long.parseLong(ui));
        user.setRoleId(Integer.parseInt(v));
        DBManager.getInstance().updateUserRoleId(user);
        request.setAttribute("emailConfirmed", "true");
        return Path.PAGE_SUCCESS;
    }
}
