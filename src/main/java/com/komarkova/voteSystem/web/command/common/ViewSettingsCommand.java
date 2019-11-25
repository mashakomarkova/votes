package com.komarkova.voteSystem.web.command.common;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.db.DBManager;
import com.komarkova.voteSystem.db.entity.User;
import com.komarkova.voteSystem.exception.AppException;
import com.komarkova.voteSystem.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import sun.misc.BASE64Encoder;


public class ViewSettingsCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        String email = request.getParameter("email");
        User userCurrent = DBManager.getInstance().findUserByEmail(Long.parseLong(email));
        try{
            byte[] imageData = userCurrent.getUserPicture().getBytes(1, (int) userCurrent.getUserPicture().length());
            BASE64Encoder encoder = new BASE64Encoder();

            String userImage = encoder.encode(imageData);
            request.setAttribute("userImage", userImage);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        HttpSession session = request.getSession();
        session.setAttribute("userCurrent",userCurrent);
        return Path.PAGE_PROFILE;
    }
}
