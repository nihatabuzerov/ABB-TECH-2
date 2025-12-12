package org.example.services;

import org.example.dto.CarDto;

import java.util.List;

public interface CarService {

    List<CarDto> getCars();

    CarDto getCarById(int id);

    void saveCar(CarDto car);

    void deleteCarById(int id);

    void updateCar(int id, CarDto carDto);
}


