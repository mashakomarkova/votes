package com.komarkova.voteSystem.web.restCommand;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.db.DBManager;
import com.komarkova.voteSystem.db.entity.Transaction;
import com.komarkova.voteSystem.exception.AppException;
import com.komarkova.voteSystem.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public class ViewMyTransactionsRestCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        List<Transaction> myTransactions = DBManager.getInstance().findMyTransactions(
                Long.parseLong(request.getParameter("userId")));
        String res = "";

        for (int i = 0; i < myTransactions.size(); i++){
            res += myTransactions.get(i).getFirstDate() + " - " +  myTransactions.get(i).getLastDate() + "; " +
                    myTransactions.get(i).getSum() + "<separator>";
        }
        response.setHeader("myTransactions", res);
        return Path.REST_PAGE;
    }
}
