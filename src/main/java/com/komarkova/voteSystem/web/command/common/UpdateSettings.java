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
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.SQLException;

public class UpdateSettings extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        HttpSession session = request.getSession();
        DBManager manager = DBManager.getInstance();
        String userEmail = session.getAttribute("user").toString();
        User user = manager.findUserByEmail(Long.parseLong(userEmail));
        String first_name = Param.getParamUTF8(request.getParameter("first_name"));
        String last_name = Param.getParamUTF8(request.getParameter("last_name"));

        Part filePart = request.getPart("file");
        InputStream input = filePart.getInputStream();
        byte[] image = Param.toByteArray(input); // Apache commons IO.
        Blob blob = new SerialBlob(image);
        if (blob.length() != 0) {
            user.setUserPicture(blob);
        }
        user.setFirstName(first_name);
        user.setLastName(last_name);
        manager.updateUser(user);

        String forward = Path.PAGE_SUCCESS;
        return forward;
    }
}
