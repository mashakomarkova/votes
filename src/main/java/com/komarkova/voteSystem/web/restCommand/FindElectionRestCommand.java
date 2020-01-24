package com.komarkova.voteSystem.web.restCommand;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.db.DBManager;
import com.komarkova.voteSystem.db.bean.PollResultBean;
import com.komarkova.voteSystem.db.entity.Choice;
import com.komarkova.voteSystem.db.entity.Election;
import com.komarkova.voteSystem.exception.AppException;
import com.komarkova.voteSystem.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FindElectionRestCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        Long electionId= Long.parseLong(request.getParameter("elId"));
        String user = request.getParameter("user");

        Election election = DBManager.getInstance().findElectionById(electionId);
        List<Choice> choices = DBManager.getInstance().findChoices(electionId);
        if (DBManager.getInstance().hasParticipatedInThisElection(Long.parseLong(user),electionId)){
            response.setHeader("hasAlreadyParticipated", "true");
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
        }
        else{
            response.setHeader("hasAlreadyParticipated", "false");
            String choicesText = "";
            String choicesId = "";
            for (int i = 0; i < choices.size(); i++){
                choicesText+=choices.get(i).getChoice() + "<separator>";
            }
            for (int i = 0; i < choices.size(); i++){
                choicesId+=choices.get(i).getId() + "<separator>";
            }
            response.setHeader("electionText",election.getQuestionText());
            response.setHeader("choicesText",choicesText);
            response.setHeader("choicesId",choicesId);
        }

        return Path.REST_PAGE;
    }
}
