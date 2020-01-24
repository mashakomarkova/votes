package com.komarkova.voteSystem.web.restCommand;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.db.DBManager;
import com.komarkova.voteSystem.exception.AppException;
import com.komarkova.voteSystem.util.Param;
import com.komarkova.voteSystem.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class CreateElectionRestCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        String questionText = Param.getParamUTF8(request.getParameter("questionText"));
        String access = request.getParameter("access");
        String city = Param.getParamUTF8(request.getParameter("city"));
        String country = Param.getParamUTF8(request.getParameter("country"));
        String topic = Param.getParamUTF8(request.getParameter("topic"));
        String[] choices = Param.getParamUTF8Arr(request.getParameterValues("choices"));
        String forward = Path.PAGE_ERROR_PAGE;
        String user = request.getParameter("user");

        Long countOfElections = DBManager.getInstance().countElectionsUser(Long.parseLong(user));
        if (countOfElections < 5) {
            if (Param.isUnique(choices)) {
                Long electionId = DBManager.getInstance().createElection(questionText, access, Long.parseLong(user), city, country);
                DBManager.getInstance().addTopic(topic, electionId);
                DBManager.getInstance().createChoice(choices, electionId);
                response.setHeader("electionCreated", "true");
                forward = Path.PAGE_SUCCESS;
            } else {
                response.setHeader("mustBeUnique", "true");
            }

        } else {
            if (Param.isUnique(choices)) {
                request.setAttribute("electionsAreOver", "true");

                Long electionId = DBManager.getInstance().createElection(questionText, "", Long.parseLong(user), city, country);
                request.setAttribute("electionId", electionId);
            } else {
                response.setHeader("mustBeUnique", "true");
            }
        }
        return forward;
    }
}
