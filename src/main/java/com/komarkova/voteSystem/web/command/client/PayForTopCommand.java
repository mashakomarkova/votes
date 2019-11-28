package com.komarkova.voteSystem.web.command.client;

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

public class PayForTopCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        Long electionId = Long.parseLong(request.getParameter("electionId"));
        int days = Integer.parseInt(request.getParameter("days"));
        DBManager.getInstance().addTransaction(electionId,days);
        request.setAttribute("isPaidForTop","true");
        return Path.PAGE_SUCCESS;
    }
}
