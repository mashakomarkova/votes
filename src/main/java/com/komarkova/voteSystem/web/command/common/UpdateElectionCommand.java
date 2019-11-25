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

public class UpdateElectionCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        HttpSession session = request.getSession();
        Long electionId = Long.parseLong(request.getParameter("electionId"));
        Long userId = Long.parseLong(session.getAttribute("user").toString());
        String questionText = request.getParameter("questionText");
        String access= request.getParameter("access");
        String[] choicesId = request.getParameterValues("choiceId");
        String[] choices = request.getParameterValues("choices");
        DBManager.getInstance().updateElection(questionText,access, electionId);
        DBManager.getInstance().updateChoices(choices, pseudoOneStepConversion(choicesId));
        request.setAttribute("isUpdatedElection", "true");
        return Path.PAGE_SUCCESS;
    }
    private Long[] pseudoOneStepConversion(String[] numbers) {
        Long[] result = new Long[numbers.length];
        for (int i = 0; i < numbers.length; i++)
            result[i] = Long.parseLong(numbers[i]);
        return result;
    }
}
