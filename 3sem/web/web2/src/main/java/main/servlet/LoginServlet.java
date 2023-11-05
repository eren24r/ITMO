package main.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import main.model.User;
import main.model.UserDatas;

import java.io.IOException;

@WebServlet(name = "log", value = "/log")
public class LoginServlet extends HttpServlet {
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        String usr = request.getParameter("username");
        String pass = request.getParameter("password");

        final HttpSession currentSession = request.getSession();

        System.out.println(usr);
        System.out.println(pass);

        if(usr != null && pass != null && !usr.isEmpty() && !pass.isEmpty()){
            UserDatas userList = (UserDatas) currentSession.getAttribute("userList");
            if(userList != null){
                for(User tmp: userList.getUserList()){
                    if(tmp.getUsername().equals(usr) && tmp.getPassword().equals(pass)){
                        System.out.println("user");

                        Cookie userCookie = new Cookie("username", usr);
                        Cookie userpassCookie = new Cookie("password", pass);

                        userCookie.setMaxAge(30 * 24 * 60 * 60);

                        response.addCookie(userCookie);
                        response.addCookie(userpassCookie);

                        response.sendRedirect(request.getContextPath().replace("/login.jsp", "/"));
                        return;
                    }
                }
            }
        }

        response.sendRedirect("login.jsp");
    }
}
