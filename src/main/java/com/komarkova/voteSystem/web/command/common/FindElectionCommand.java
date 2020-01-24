package com.komarkova.voteSystem.web.command.common;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.db.DBManager;
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
import java.util.List;

public class FindElectionCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        Long electionId= Long.parseLong(request.getParameter("elId"));
        Election election = DBManager.getInstance().findElectionById(electionId);
        List<Choice> choices = DBManager.getInstance().findChoices(electionId);
        request.setAttribute("choices",choices);
        request.setAttribute("election", election);
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
        return Path.ELECTION_PARTICIPATION;
    }
}
