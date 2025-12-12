package org.example.repository;

import org.example.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {

    List<Car> getCars();

    Optional<Car> getCarById(int id);

    void saveCar(Car car);

    void deleteCarById(int id);

    void updateCar(int id, Car carDto);

}

