package com.komarkova.voteSystem.web.restCommand;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.db.DBManager;
import com.komarkova.voteSystem.db.bean.PollResultBean;
import com.komarkova.voteSystem.exception.AppException;
import com.komarkova.voteSystem.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public class ParticipateInElectionRest extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        Long electionId = Long.parseLong(request.getParameter("elId"));
        Long userId = Long.parseLong(request.getParameter("user"));
        Long choiceId = Long.parseLong(request.getParameter("participationChoice"));
        if (!DBManager.getInstance().hasParticipatedInThisElection(userId, electionId)) {
            DBManager.getInstance().participateInElection(userId, electionId, choiceId);
            response.setHeader("hasAlreadyParticipated", "false");
        } else {
            List<PollResultBean> result = DBManager.getInstance().seeResults(electionId);
            int sum = 0;
            String pollResult = "";
            for (PollResultBean p : result) {
                sum += p.getCounts();
                pollResult += p.getChoice() + ":" + p.getCounts() + "<separator>";
            }

            response.setHeader("pollResult",pollResult);
            response.setHeader("sum", String.valueOf(sum));
            Long numberOfVotes=DBManager.getInstance().numberOfVotes(electionId);
            response.setHeader("numberOfVotes",String.valueOf(numberOfVotes));
            response.setHeader("hasAlreadyParticipated", "true");
        }
        return Path.REST_PAGE;
    }
}
