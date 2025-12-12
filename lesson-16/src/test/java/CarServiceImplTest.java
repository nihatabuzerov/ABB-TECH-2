
import org.example.dto.CarDto;
import org.example.exception.CarNotFoundException;
import org.example.model.Car;
import org.example.repository.CarRepository;
import org.example.services.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceImplTest {

    private CarRepository carRepository;
    private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        carRepository = mock(CarRepository.class);
        carService = new CarServiceImpl(carRepository);
    }

    @Test
    void testGetCars() {
        when(carRepository.getCars()).thenReturn(List.of(
                new Car("Red", 200),
                new Car("Blue", 180)
        ));

        List<CarDto> cars = carService.getCars();

        assertEquals(2, cars.size());
        assertEquals("Red", cars.get(0).getColor());
        assertEquals("Blue", cars.get(1).getColor());
        verify(carRepository).getCars();
    }

    @Test
    void testGetCarById_Success() {
        Car car = new Car("Green", 150);
        car.setId(1);
        when(carRepository.getCarById(1)).thenReturn(Optional.of(car));

        CarDto carDto = carService.getCarById(1);

        assertEquals(1, carDto.getId());
        assertEquals("Green", carDto.getColor());
        assertEquals(150, carDto.getSpeed());
        verify(carRepository).getCarById(1);
    }

    @Test
    void testGetCarById_NotFound() {
        when(carRepository.getCarById(1)).thenReturn(Optional.empty());

        assertThrows(CarNotFoundException.class, () -> carService.getCarById(1));
        verify(carRepository).getCarById(1);
    }

    @Test
    void testSaveCar() {
        CarDto carDto = CarDto.builder().color("Yellow").speed(220).build();

        carService.saveCar(carDto);

        ArgumentCaptor<Car> captor = ArgumentCaptor.forClass(Car.class);
        verify(carRepository).saveCar(captor.capture());

        Car savedCar = captor.getValue();
        assertEquals("Yellow", savedCar.getColor());
        assertEquals(220, savedCar.getSpeed());
    }

    @Test
    void testDeleteCarById() {
        carService.deleteCarById(1);
        verify(carRepository).deleteCarById(1);
    }

    @Test
    void testUpdateCar() {
        CarDto carDto = CarDto.builder().color("Black").speed(190).build();

        carService.updateCar(1, carDto);

        ArgumentCaptor<Car> captor = ArgumentCaptor.forClass(Car.class);
        verify(carRepository).updateCar(eq(1), captor.capture());

        Car updatedCar = captor.getValue();
        assertEquals("Black", updatedCar.getColor());
        assertEquals(190, updatedCar.getSpeed());
    }
}
