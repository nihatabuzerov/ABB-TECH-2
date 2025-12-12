package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.CarDto;
import org.example.exception.CarNotFoundException;
import org.example.repository.CarRepositoryImpl;
import org.example.services.CarService;
import org.example.services.CarServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;


@WebServlet(name = "CarController", urlPatterns = "/cars")
public class CarController extends HttpServlet {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private CarService carService;

    @Override
    public void init() {
        carService = new CarServiceImpl(new CarRepositoryImpl());
    }

    @Override
    public void doGet(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        var params = req.getParameterMap();
        try {
            if (id != null) {
                CarDto car = carService.getCarById(Integer.parseInt(id));
                resp.getWriter().println(OBJECT_MAPPER.writeValueAsString(car));
            } else {
                var cars = carService.getCars();
                resp.getWriter().println(OBJECT_MAPPER.writeValueAsString(cars));
            }
            resp.setStatus(200);
            resp.setContentType("application/json");
        } catch (Exception exception) {
            if (exception instanceof CarNotFoundException) {
                resp.setStatus(404);
                resp.getWriter().println("Car not found");
            } else {
                resp.setStatus(500);
                resp.getWriter().println("Internal Server Error");
            }

        }

    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer sb = new StringBuffer();
        try (BufferedReader bufferedReader = req.getReader()) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        }
        CarDto CarDto = OBJECT_MAPPER.readValue(sb.toString(), CarDto.class);
        carService.saveCar(CarDto);
        resp.setStatus(201);
    }





}
