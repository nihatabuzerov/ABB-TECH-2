package org.example.repository;

import org.example.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarRepositoryImpl implements CarRepository {



    private static List<Car> cars = new ArrayList<>();

    @Override
    public List<Car> getCars() {
        return cars;
    }

    @Override
    public Optional<Car> getCarById(int id) {
        return cars.stream().filter(car -> car.getId() == id).findFirst();
    }

    @Override
    public void saveCar(Car car) {
        car.setId(cars.size() + 1);
        car.setDbCode("Car" + car.getId());
        cars.add(car);

    }

    @Override
    public void deleteCarById(int id) {
        cars.removeIf(car -> car.getId() == id);

    }

    @Override
    public void updateCar(int id, Car carDto) {
        Optional<Car> optional = getCarById(id);
        if (optional.isPresent()) {
            Car car = optional.get();
            car.setColor(carDto.getColor());
            car.setSpeed(carDto.getSpeed());
            car.setDbCode(carDto.getDbCode());
            cars.set(cars.indexOf(car), car);

        }
    }
}

