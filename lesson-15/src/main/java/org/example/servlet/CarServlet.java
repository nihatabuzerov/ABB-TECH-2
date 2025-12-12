package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.Car;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        name = "CarServlet",
        urlPatterns = {"/cars", "/mashinlar"},
        initParams = {
                @WebInitParam(name = "version", value = "1.0"),
                @WebInitParam(name = "author", value = "Vusal")
        }
)
public class CarServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        log("CarServlet initialized.");

        // Context attribute paylaşılır
        getServletContext().setAttribute("sharedMessage", "Hello from CarServlet");

        // Init param oxumaq
        log("Init param version = " + getInitParameter("version"));
    }

    @Override
    public void destroy() {
        log("CarServlet destroyed.");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Car car = new Car("White", 100);

        resp.setStatus(200);
        resp.setContentType("text/html");

        PrintWriter pw = resp.getWriter();
        pw.println(car);
    }
}
