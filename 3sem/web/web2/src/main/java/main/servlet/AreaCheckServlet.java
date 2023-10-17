package main.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import main.model.AreaData;
import main.model.UserAreaDatas;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.LinkedList;

@WebServlet(name = "areaCheckServlet", value = "/area-check")
public class AreaCheckServlet extends HttpServlet {
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
        final long startExec = System.nanoTime();

        final String ctx = this.getServletContext().getContextPath();

        final String x = request.getParameter("x");
        final String y = request.getParameter("y");
        final String r = request.getParameter("r");

        final double dx;
        final double dy;
        final double dr;

        try {
            dx = Double.parseDouble(x);
            dy = Double.parseDouble(y);
            dr = Double.parseDouble(r);
        } catch (NumberFormatException | NullPointerException e) {
            response.sendError(400);
            return;
        }

        final boolean result = checkArea(dx, dy, dr);

        final HttpSession currentSession = request.getSession();
        UserAreaDatas datas = (UserAreaDatas) currentSession.getAttribute("data");
        if (datas == null) {
            datas = new UserAreaDatas();
            currentSession.setAttribute("data", datas);
        }
        if (datas.getAreaDataList() == null)
            datas.setAreaDataList(new LinkedList<>());

        final long endExec = System.nanoTime();
        final long executionTime = endExec - startExec;
        final LocalDateTime executedAt = LocalDateTime.now();

        final AreaData data = new AreaData();
        data.setX(dx);
        data.setY(dy);
        data.setR(dr);
        data.setResult(result);
        data.setCalculationTime(executionTime);
        data.setCalculatedAt(executedAt);

        datas.getAreaDataList().addLast(data);
        currentSession.setAttribute("data", datas);

        System.out.println("AreaUpdated!");
        response.setContentType("text/html;charset=UTF-8");
        final PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("    <meta http-equiv=\"Content-Type\" content=\"text/html\" charset=\"UTF-8\" />");
        out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("    <link rel=\"stylesheet\" href=\"" + ctx + "/styles/style.css\">");
        out.println("    <title>Calculation result</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div id=\"header\" class=\"blured-container round-container margin\">");
        out.println("        <h1>Calculation results</h1>");
        out.println("    </div>");
        out.println("    <div id=\"result-table-container\" class=\"blured-container margin\">");
        out.println("        <table>");
        out.println("            <tr>");
        out.println("                <th>Parameter</th>");
        out.println("                <th>Received Value</th>");
        out.println("            </tr>");
        out.println("            <tr>");
        out.println("                <td>X</td>");
        out.println("                <td>" + dx + "</td>");
        out.println("            </tr>");
        out.println("            <tr>");
        out.println("                <td>Y</td>");
        out.println("                <td>" + dy + "</td>");
        out.println("            </tr>");
        out.println("            <tr>");
        out.println("                <td>R</td>");
        out.println("                <td>" + dr + "</td>");
        out.println("            </tr>");
        out.println("        </table>");
        out.println("    </div>");
        out.println("    <div class=\"blured-container round-container fit-content-container margin\">");
        out.println("        <p>Result: " + (result ? "hit" : "miss") + "</p>");
        out.println("    </div>");
        out.println("    <div class=\"blured-container round-container fit-content-container margin\">"); // Здесь подставьте результат вычислений
        out.println("        <p><a href=\"" + ctx + "\">Return to homepage</a></p>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");

        System.out.println("Updated!");

        out.close();
        /*RequestDispatcher dispatcher = request.getRequestDispatcher("table.jsp");
        dispatcher.forward(request, response);*/

        /*response.sendRedirect("table.jsp");*/

    }

    private boolean checkArea(final double x, final double y, final double r) {
        if (x >= 0 && y >= 0) {
            return (x * x) + (y * y) <= ((r / 2) * (r / 2));
        } else if (x <= 0 && y <= 0) {
            double newX = -r - y;
            return x >= newX;
        } else if (x >= 0 && y <= 0) {
            return x <= r && y >= (-r / 2);
        }
        return false;
    }
}
