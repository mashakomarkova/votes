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

public class UpdatePictureCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException, SQLException, NoSuchAlgorithmException {
        HttpSession session = request.getSession();
        DBManager manager = DBManager.getInstance();
        String userEmail = session.getAttribute("user").toString();
        User user = manager.findUserByEmail(Long.parseLong(userEmail));
        Part filePart = request.getPart("file");
        String forward = Path.PAGE_ERROR_PAGE;
        if (filePart.getContentType().equals("image/png") || filePart.getContentType().equals("image/jpeg") ||
                filePart.getContentType().equals("image/jpg")) {
            InputStream input = filePart.getInputStream();
            byte[] image = Param.toByteArray(input); // Apache commons IO.
            Blob blob = new SerialBlob(image);
            if (blob.length() != 0) {
                user.setUserPicture(blob);
            }
            manager.updateUser(user);

            forward = Path.PAGE_SUCCESS;
        }
        request.setAttribute("incorrectPic","true");
        return forward;
    }
}
