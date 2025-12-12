

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.CarController;
import org.example.dto.CarDto;
import org.example.exception.CarNotFoundException;
import org.example.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarControllerTest {

    private CarController carController;
    private CarService carService;
    private ObjectMapper objectMapper = new ObjectMapper();




    @BeforeEach
    void setUp() throws Exception {
        carService = mock(CarService.class);
        carController = new CarController();


        Field field = CarController.class.getDeclaredField("carService");
        field.setAccessible(true);
        field.set(carController, carService);

    }

    @Test
    void testDoGet_WithId_ReturnsCar() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn("1");

        CarDto carDto = CarDto.builder().id(1).color("Red").speed(200).build();
        when(carService.getCarById(1)).thenReturn(carDto);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        carController.doGet(request, response);

        writer.flush();
        String responseBody = stringWriter.toString();
        assertTrue(responseBody.contains("Red"));
        verify(response).setStatus(200);
        verify(response).setContentType("application/json");
    }

    @Test
    void testDoGet_WithoutId_ReturnsAllCars() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn(null);
        when(carService.getCars()).thenReturn(List.of(
                CarDto.builder().id(1).color("Red").speed(200).build(),
                CarDto.builder().id(2).color("Blue").speed(180).build()
        ));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        carController.doGet(request, response);

        writer.flush();
        String responseBody = stringWriter.toString();
        assertTrue(responseBody.contains("Red"));
        assertTrue(responseBody.contains("Blue"));
        verify(response).setStatus(200);
        verify(response).setContentType("application/json");
    }

    @Test
    void testDoGet_CarNotFound() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn("1");
        when(carService.getCarById(1)).thenThrow(new CarNotFoundException("Car not found"));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        carController.doGet(request, response);

        writer.flush();
        assertTrue(stringWriter.toString().contains("Car not found"));
        verify(response).setStatus(404);
    }

    @Test
    void testDoPost_SavesCar() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        CarDto carDto = CarDto.builder().color("Green").speed(150).build();
        String json = objectMapper.writeValueAsString(carDto);

        BufferedReader reader = new BufferedReader(new StringReader(json));
        when(request.getReader()).thenReturn(reader);

        carController.doPost(request, response);

        ArgumentCaptor<CarDto> captor = ArgumentCaptor.forClass(CarDto.class);
        verify(carService).saveCar(captor.capture());

        CarDto savedCar = captor.getValue();
        assertEquals("Green", savedCar.getColor());
        assertEquals(150, savedCar.getSpeed());

        verify(response).setStatus(201);
    }
}
