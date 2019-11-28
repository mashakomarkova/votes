package com.komarkova.voteSystem.web.command;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.db.DBManager;
import com.komarkova.voteSystem.db.entity.User;
import com.komarkova.voteSystem.exception.AppException;
import com.komarkova.voteSystem.util.Param;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Properties;

public class RegisterCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        DBManager manager = DBManager.getInstance();
        String username = Param.getParamUTF8(request.getParameter("username"));
        String email = Param.getParamUTF8(request.getParameter("email"));
        String password = Param.getParamUTF8(request.getParameter("password"));
        String first_name = Param.getParamUTF8(request.getParameter("first_name"));
        String last_name = Param.getParamUTF8(request.getParameter("last_name"));
        manager.createNewUser(username, email, password, first_name, last_name);
        User user = DBManager.getInstance().findUserByEmailAndPassword(email,password);
        Properties p = new Properties();
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.port", "587");

        Session s = Session.getInstance(p,
                new javax.mail.Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication("electronic.record1@gmail.com", "resumeconstructor");
                    }
                }
        );

        Message message = new MimeMessage(s);
        try {
            message.setFrom(new InternetAddress("electronic.record1@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Confirm your email");
            URL myURL = new URL("http://localhost:8080/vote_war_exploded/controller?command=confirmEmail&v=1&ui="+user.getId());

            message.setText("Thanks for registration. To confirm your email follow the link:" +myURL);

            Transport.send(message);


        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return Path.PAGE_SUCCESS;
    }
}
