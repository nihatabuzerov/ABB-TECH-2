package org.example.services;

import org.example.repository.CarRepository;
import org.example.dto.CarDto;
import org.example.model.Car;
import org.example.exception.CarNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<CarDto> getCars() {
        return carRepository.getCars()
                .stream()
                .map(car -> CarDto.builder()
                        .color(car.getColor())
                        .speed(car.getSpeed())
                        .id(car.getId())
                        .build())
                .collect(Collectors.toList());

    }

    @Override
    public CarDto getCarById(int id) {
        Car car = carRepository.getCarById(id).
                orElseThrow(() -> new CarNotFoundException("Car not found"));
        return CarDto.builder()
                .color(car.getColor())
                .speed(car.getSpeed())
                .id(car.getId())
                .build();
    }

    @Override
    public void saveCar(CarDto car) {
        carRepository.saveCar(new Car(car.getColor(), car.getSpeed()));
    }

    @Override
    public void deleteCarById(int id) {
        carRepository.deleteCarById(id);
    }

    @Override
    public void updateCar(int id, CarDto carDto) {
        carRepository.updateCar(id, new Car(carDto.getColor(), carDto.getSpeed()));
    }
}


