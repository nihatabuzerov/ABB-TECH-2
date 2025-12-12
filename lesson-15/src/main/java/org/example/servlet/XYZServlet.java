package org.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "XYZServlet", urlPatterns = "/xyz")
public class XYZServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // CarServlet-dən paylaşılan attribute-u oxuyuruq
        String msg = (String) getServletContext().getAttribute("sharedMessage");

        resp.setContentType("text/plain");
        resp.getWriter().println("XYZServlet works. Shared message = " + msg);
    }
}
