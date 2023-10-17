package main.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "controllerServlet", value = "/controller")
public class ControllerServlet extends HttpServlet {
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        String forwardPath = getServletContext().getContextPath();

        if (request.getParameter("clear") != null) {
            final HttpSession session = request.getSession();
            session.invalidate();
        }

        if (request.getParameter("x") != null
            && request.getParameter("y") != null
            && request.getParameter("r") != null)
        {
            System.out.println("posting to AreaChecker...");
            forwardPath = this.getServletContext().getContextPath() + "/area-check?x=" + request.getParameter("x")
            + "&y=" + request.getParameter("y") + "&r=" + request.getParameter("r");
        }

        System.out.println("Posting...");

        response.sendRedirect(forwardPath);
    }
}
