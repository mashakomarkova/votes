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
import java.util.List;

public class ViewMyElectionsRestCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {

        Long userId = Long.parseLong(request.getParameter("user"));
        List<Election> myEl = DBManager.getInstance().findMyElections(userId);

        String myElections = "";
        for (Election e : myEl) {
            myElections += e.getQuestionText() + "<separator>";
        }

        String elId = "";
        for (Election e : myEl) {
            elId += e.getId() + "<separator>";
        }
        response.setHeader("myElections",myElections);
        response.setHeader("electionsId",elId);
        return Path.REST_PAGE;
    }
}
