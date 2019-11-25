package com.komarkova.voteSystem.web.command.common;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.db.DBManager;
import com.komarkova.voteSystem.db.entity.User;
import com.komarkova.voteSystem.exception.AppException;
import com.komarkova.voteSystem.util.Param;
import com.komarkova.voteSystem.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UpdatePasswordCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        HttpSession session = request.getSession();
        DBManager manager = DBManager.getInstance();
        String oldPassword = Param.getParamUTF8(request.getParameter("old_password"));
        String userEmail = session.getAttribute("user").toString();
        User user = manager.findUserByEmail(Long.parseLong(userEmail));
        String forward = Path.PAGE_ERROR_PAGE;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(oldPassword.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toLowerCase();
        if (myHash.equals(user.getPassword())) {
            String password = Param.getParamUTF8(request.getParameter("password"));
            user.setPassword(password);
            manager.updateUserPass(user);
            forward = Path.PAGE_SUCCESS;
        }
        return forward;
    }
}
