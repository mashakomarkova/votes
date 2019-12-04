package com.komarkova.voteSystem.web.command.client;

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

public class SearchCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        String keys = request.getParameter("keys");
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String topic = request.getParameter("topic");
        List<Election> elections = DBManager.getInstance().searchElections(keys, country, city, topic);
        List<Election> topElections = DBManager.getInstance().searchElections(keys, country, city, topic);
        request.setAttribute("elections", elections);
        request.setAttribute("topElections", topElections);
        return Path.PAGE_ALL_ELECTIONS; }
}
