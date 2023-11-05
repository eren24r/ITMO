package main.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import main.model.User;
import main.model.UserDatas;

import java.io.IOException;

@WebServlet(name = "adduser", value = "/adduser")
public class AddUserServlet extends HttpServlet {
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        String usr = request.getParameter("username");
        String pass = request.getParameter("password");

        final HttpSession currentSession = request.getSession();

        if(usr != null && pass != null && !usr.isEmpty() && !pass.isEmpty()){
            UserDatas datas = (UserDatas) currentSession.getAttribute("userList");

            if(datas == null){
                datas = new UserDatas();
            }

            User tmpUsr = new User(usr, pass);

            for(User s: datas.getUserList()){
                if(s.getUsername().equals(tmpUsr.getUsername())){
                    response.sendRedirect("login.jsp");
                    return;
                }
            }

            datas.addUser(tmpUsr);
            currentSession.setAttribute("userList", datas);

            response.sendRedirect("login.jsp");
        }

        response.sendRedirect("addUser.jsp");
    }
}
