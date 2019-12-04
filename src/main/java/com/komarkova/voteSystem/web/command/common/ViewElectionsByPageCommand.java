package com.komarkova.voteSystem.web.command.common;

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

public class ViewElectionsByPageCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        Long pages = Long.parseLong(request.getParameter("p"));
        Long t = Long.parseLong(request.getParameter("t"));
        Long numOfPages = Long.parseLong(request.getParameter("numOfPages"));

        List<Election> elections= DBManager.getInstance().findAllElectionsByPage(pages,t);
        request.setAttribute("total", t);
        request.setAttribute("pages", numOfPages);
        request.setAttribute("pa", pages);
        request.setAttribute("elections",elections);
        return Path.PAGE_ALL_ELECTIONS;
    }
}
