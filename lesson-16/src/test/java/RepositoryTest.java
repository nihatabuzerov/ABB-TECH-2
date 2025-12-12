import org.example.model.Car;
import org.example.repository.CarRepository;
import org.example.repository.CarRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryTest {



    @Test
    void testSaveCarAndGetCars() {
        CarRepositoryImpl repo = new CarRepositoryImpl();

        List<Car> cars = repo.getCars();
        assertTrue(cars.isEmpty());

        Car car1 = new Car("Red", 120);
        repo.saveCar(car1);

        cars = repo.getCars();
        assertEquals(1, cars.size());
        assertEquals("Red", cars.get(0).getColor());
        assertEquals(120, cars.get(0).getSpeed());
        assertEquals(1, cars.get(0).getId());
        assertEquals("Car1", cars.get(0).getDbCode());

        Car car2 = new Car("Black", 150);
        repo.saveCar(car2);

        cars = repo.getCars();
        assertEquals(2, cars.size());
        assertEquals("Black", cars.get(1).getColor());
        assertEquals(150, cars.get(1).getSpeed());
        assertEquals(2, cars.get(1).getId());
        assertEquals("Car2", cars.get(1).getDbCode());
    }

    @Test
    void testGetCarById() {
        CarRepositoryImpl repo = new CarRepositoryImpl();

        Car car1 = new Car("Red", 120);
        repo.saveCar(car1);
        Car car2 = new Car("Black", 150);
        repo.saveCar(car2);

        Optional<Car> foundCar1 = repo.getCarById(1);
        assertTrue(foundCar1.isPresent());
        assertEquals("Red", foundCar1.get().getColor());

        Optional<Car> notFoundCar = repo.getCarById(3);
        assertFalse(notFoundCar.isPresent());
    }

    @Test
    void testDeleteCarById() {
        CarRepositoryImpl repo = new CarRepositoryImpl();

        Car car1 = new Car("Red", 120);
        Car car2 = new Car("Black", 150);
        repo.saveCar(car1);
        repo.saveCar(car2);

        repo.deleteCarById(1);

        List<Car> cars = repo.getCars();
        assertEquals(1, cars.size());
        assertEquals(2, cars.get(0).getId());  // yalnız ikinci car qalır
    }

    @Test
    void testUpdateCar() {
        CarRepositoryImpl repo = new CarRepositoryImpl();

        Car car1 = new Car("Red", 120);
        repo.saveCar(car1);

        Car updateDto = new Car("Blue", 180);
        updateDto.setDbCode("UpdatedCar");
        repo.updateCar(1, updateDto);

        Optional<Car> updatedCar = repo.getCarById(1);
        assertTrue(updatedCar.isPresent());
        assertEquals("Blue", updatedCar.get().getColor());
        assertEquals(180, updatedCar.get().getSpeed());
        assertEquals("UpdatedCar", updatedCar.get().getDbCode());
    }



}
