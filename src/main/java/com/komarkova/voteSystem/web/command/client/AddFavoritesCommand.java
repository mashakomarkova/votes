package com.komarkova.voteSystem.web.command.client;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.db.DBManager;
import com.komarkova.voteSystem.db.entity.Election;
import com.komarkova.voteSystem.exception.AppException;
import com.komarkova.voteSystem.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddFavoritesCommand extends Command {
    private List<Election> favoriteElections = new ArrayList<>();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        HttpSession session = request.getSession();
        String electionRemove = request.getParameter("electionRemove");
        String electionId = request.getParameter("election");
        if (electionRemove==null|| "".equals(electionRemove)) {
            Election election = DBManager.getInstance().findElectionById(Long.parseLong(electionId));
            favoriteElections.add(election);
        }
        else if(favoriteElections.size()>0 && (electionId==null || "".equals(electionId))){
            Election election = DBManager.getInstance().findElectionById(Long.parseLong(electionRemove));
            favoriteElections.remove(election);
        }
        session.setAttribute("favoriteElections", favoriteElections);
        return Path.COMMAND_ALL_ELECTIONS;
    }
}
