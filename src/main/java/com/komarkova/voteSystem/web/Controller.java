package com.komarkova.voteSystem.web;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.exception.AppException;
import com.komarkova.voteSystem.web.command.Command;
import com.komarkova.voteSystem.web.command.CommandContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * Main servlet controller.
 */
@WebServlet("/controller")
@MultipartConfig
public class Controller extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    /**
     * Main method of this controller.
     */
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter("command");

        Command command = CommandContainer.get(commandName);
        String forward = Path.PAGE_ERROR_PAGE;
        try {
            forward = command.execute(request, response);
        } catch (AppException | IOException | ServletException | SQLException | NoSuchAlgorithmException e) {
            request.setAttribute("errorMessage", e.getMessage());
        }

        request.getRequestDispatcher(forward).forward(request, response);
    }
}
