package com.komarkova.voteSystem.web.command.common;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.db.DBManager;
import com.komarkova.voteSystem.exception.AppException;
import com.komarkova.voteSystem.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class ParticipateCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        HttpSession session = request.getSession();
        Long electionId = Long.parseLong(request.getParameter("elId"));
        Long userId = Long.parseLong(session.getAttribute("user").toString());
        Long choiceId = Long.parseLong(request.getParameter("participationChoice"));
        String forward = Path.PAGE_ERROR_PAGE;
        if (!DBManager.getInstance().hasParticipatedInThisElection(userId, electionId)) {
            DBManager.getInstance().participateInElection(userId, electionId, choiceId);
            request.setAttribute("isParticipated", "true");
            forward = Path.PAGE_SUCCESS;
        } else {
            request.setAttribute("hasAlreadyParticipated", "true");
        }
        return forward;
    }
}
