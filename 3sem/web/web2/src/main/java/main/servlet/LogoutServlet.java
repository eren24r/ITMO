package main.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import main.model.User;
import main.model.UserDatas;

import java.io.IOException;

@WebServlet(name = "logout", value = "/logout")
public class LogoutServlet extends HttpServlet {
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {

        Cookie userCookie = new Cookie("username", null);
        Cookie userpassCookie = new Cookie("password", null);

        response.addCookie(userCookie);
        response.addCookie(userpassCookie);

        response.sendRedirect("login.jsp");
    }
}
