import org.example.Car;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class CarTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private Car car = new Car();
    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        System.out.println("test bashladi");
    }
    @AfterEach
    void restore() {
        System.setOut(originalOut);
        System.out.println("test bitti");
    }

    @Test
    void testFirstGear() {
        Car car = new Car();
        car.changeGear(1);

        Assertions.assertEquals("Car is in first gear\n", outContent.toString());
    }

    @Test
    void testSecondGear() {
        Car car = new Car();
        car.changeGear(2);

        Assertions.assertEquals("Car is in second gear\n", outContent.toString());
    }

    @Test
    void testHighGear() {
        Car car = new Car();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            car.changeGear(3);
        });
    }



    static Stream<Arguments> gearProvider() {
        return Stream.of(
                Arguments.of(1, "Car is in first gear\n", false),
                Arguments.of(2, "Car is in second gear\n", false),
                Arguments.of(3, null, true),
                Arguments.of(4, null, true),
                Arguments.of(5, null, true)
        );
    }


    public static Arguments[] testManipulationThrows() {
        return new Arguments[]{
                Arguments.of(3, null, true),
                Arguments.of(4, null, true),
                Arguments.of(5, null, true)

        };
    }



    @ParameterizedTest
    @MethodSource("gearProvider")
    void testGears(int gear, String expectedMessage, boolean expectException) {

        if (expectException) {
            Assertions.assertThrows(IllegalArgumentException.class, () -> car.changeGear(gear));
        } else {
            car.changeGear(gear);
            Assertions.assertEquals(expectedMessage, outContent.toString());
            outContent.reset();
        }
    }

//    @Test
//    void testWithGearNumber(){
//        Car car = new Car();
//        for (int gear = 1; gear <= 5; gear++) {
//            if (gear == 1) {
//                car.changeGear(gear);
//                Assertions.assertEquals("Car is in first gear\n", outContent.toString());
//                outContent.reset();
//            } else if (gear == 2) {
//                car.changeGear(gear);
//                Assertions.assertEquals("Car is in second gear\n", outContent.toString());
//                outContent.reset();
//            } else {
//                final int testGear = gear;
//                Assertions.assertThrows(IllegalArgumentException.class, () -> {
//                    car.changeGear(testGear);
//                });
//            }
//        }
//    }

}


