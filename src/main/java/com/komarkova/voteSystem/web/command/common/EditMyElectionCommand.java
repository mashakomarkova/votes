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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public class EditMyElectionCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        Long electionId= Long.parseLong(request.getParameter("elId"));
        Election election = DBManager.getInstance().findElectionById(electionId);
        List<Choice> choices = DBManager.getInstance().findChoices(electionId);
        URL myURL = new URL("http://localhost:8080/vote_war_exploded/");
        URL electionURL = new URL(myURL, "controller?command=findElectionById&elId="+electionId);
        request.setAttribute("electionURL",electionURL);
        request.setAttribute("myChoices",choices);
        request.setAttribute("myElection", election);
        return Path.PAGE_MANAGE_ELECTION;
    }
}
