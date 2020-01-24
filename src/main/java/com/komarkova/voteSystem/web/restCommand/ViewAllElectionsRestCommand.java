package com.komarkova.voteSystem.web.restCommand;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.db.DBManager;
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

public class ViewAllElectionsRestCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
//        HttpSession session = request.getSession();
//        Long userId = Long.parseLong(session.getAttribute("user").toString());
        List<Election> elections = DBManager.getInstance().findAllElectionsRest();
        //    List<Election> participatedElections = new ArrayList<>();
//        for (Election e : elections) {
//            if (DBManager.getInstance().hasParticipatedInThisElection(userId, e.getId())) {
//                participatedElections.add(e);
//            }
//        }
//        session.setAttribute("participatedElections",participatedElections);
        String el = "";
        for (Election e : elections) {
            el += e.getQuestionText() + "<separator>";
        }
        String elId = "";
        for (Election e : elections) {
            elId += e.getId() + "<separator>";
        }
        response.setHeader("elections", el);
        response.setHeader("electionsId", elId);

        return Path.REST_PAGE;
    }

}
