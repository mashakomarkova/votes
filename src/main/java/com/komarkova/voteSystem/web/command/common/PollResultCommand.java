package com.komarkova.voteSystem.web.command.common;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.db.DBManager;
import com.komarkova.voteSystem.db.bean.PollResultBean;
import com.komarkova.voteSystem.exception.AppException;
import com.komarkova.voteSystem.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;

public class PollResultCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        Long electionId = Long.parseLong(request.getParameter("elId"));
        List<PollResultBean> result = DBManager.getInstance().seeResults(electionId);
        int sum = 0;
        for (PollResultBean p : result) {
            sum += p.getCounts();
        }
        List<String> colors = new ArrayList<>();
        colors.add("info");
        colors.add("success");
        colors.add("warning");
        colors.add("danger");
        request.setAttribute("colors", colors);
        Random rand = new Random();
        request.setAttribute("rand", rand);
        request.setAttribute("sum", sum);
        request.setAttribute("result", result);
        Long numberOfVotes=DBManager.getInstance().numberOfVotes(electionId);
        request.setAttribute("numberOfVotes",numberOfVotes);
        return Path.POLL_RESULT;
    }
}
