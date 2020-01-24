package com.komarkova.voteSystem.web.command.common;

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

public class ViewAllElectionsCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        HttpSession session = request.getSession();
        Long userId = Long.parseLong(session.getAttribute("user").toString());
        List<Election> elections = DBManager.getInstance().findAllElections();
        List<Election> participatedElections = new ArrayList<>();
        for (Election e : elections) {
            if (DBManager.getInstance().hasParticipatedInThisElection(userId, e.getId())) {
                participatedElections.add(e);
            }
        }
        session.setAttribute("participatedElections",participatedElections);
        request.setAttribute("elections", elections);
        List<Election> topElections = DBManager.getInstance().findTopElections();
        List<String> listPages = DBManager.getInstance().numberOfPages();
        Integer pages = (int) (Math.ceil(Double.parseDouble(listPages.get(1))));
        Integer total = Integer.parseInt(listPages.get(0));
        request.setAttribute("total", total);
        request.setAttribute("pages", pages);
        request.setAttribute("topElections", topElections);

        return Path.PAGE_ALL_ELECTIONS;
    }
}
