package Model;

import Exceptions.CarExistsException;
import Exceptions.InvalidCarException;
import Exceptions.InvalidUserException;
import Exceptions.NoCarAvaliableException;
import Utils.Point;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static Model.Car.CarType.electric;
import static Model.Car.CarType.gas;
import static org.junit.jupiter.api.Assertions.*;

class CarsTest {

    UMCarroJa m = new UMCarroJa();
    Cars carBase = new Cars();
    Point p = new Point(1.0,2.1);
    Point p1 = new Point(5.0,1.1);
    Owner o = new Owner("d@gmail.com", "D", "rua", 99999999, "pass");

    @Test
    void addCarTest() throws CarExistsException, InvalidUserException, InvalidCarException {

        Car a = new Car("00-AA-00", o, gas , 100.0, 1.50, 9.6, 120, p, "Aston");
        carBase.addCar(a);
        assertEquals("d@gmail.com", carBase.searchCar("00-AA-00").getOwnerID());
    }

    @Test
    void addCarTest2() throws CarExistsException, InvalidUserException, InvalidCarException {

        Car a = new Car("00-AA-00", o, gas , 100.0, 1.50, 9.6, 120, p, "Aston");
        carBase.addCar(a);
        assertEquals("b@gmail.com", carBase.searchCar("00-AA-00").getOwnerID());
    }


    @Test
    void searchCarTest() throws CarExistsException, InvalidCarException {
        Car a = new Car("00-AA-00", o, gas , 100.0, 1.50, 9.6, 120, p, "Aston");
        carBase.addCar(a);
        assertSame(a, carBase.searchCar("00-AA-00"));
    }

    @Test
    void searchCarTest2() throws CarExistsException, InvalidCarException {
        Car a = new Car("00-AA-00", o, gas , 100.0, 1.50, 9.6, 120, p, "Aston");
        Car b = new Car("99-AA-00", o, electric , 100.0, 1.60, 5, 50, p, "Tesla");
        carBase.addCar(b);
        //assertNotNull(carBase.searchCar("00-AA-00"));
        assertSame(a, carBase.searchCar("99-AA-00"));
    }

    @Test
    void listOfCarTypeTest() throws CarExistsException {
        Car a = new Car("00-AA-00", o, gas , 100.0, 1.50, 9.6, 120, p, "Aston");
        Car b = new Car("99-AA-00", o, electric , 100.0, 1.60, 5, 50, p, "Tesla");
        carBase.addCar(a);
        ArrayList<Car> carsType = new ArrayList<>();
        carsType.add(a);
        assertSame(carsType, carBase.listOfCarType(gas));
    }

    //ESTE TESTE DEVIA PASSAR !!! O METODO DELES ESTA MAL!!!
    @Test
    void getCarTest() throws CarExistsException, NoCarAvaliableException {
        Car a = new Car("00-AA-00", o, gas , 100.0, 1.50, 9.6, 120, p, "Aston");
        Car b = new Car("99-AA-00", o, electric , 100.0, 1.60, 5, 50, p1, "Tesla");
        Car c = new Car("00-AA-01", o, gas , 100.0, 1.50, 9.6, 120, p1, "BMW");
        carBase.addCar(a);
        carBase.addCar(c);
        assertSame(a, carBase.getCar(p1, p,120,gas));
    }

    @Test
    void testGetCar() throws NoCarAvaliableException, CarExistsException {
        Car a = new Car("00-AA-00", o, gas , 100.0, 1.50, 9.6, 120, p, "Aston");
        Car b = new Car("99-AA-00", o, electric , 100.0, 1.60, 5, 50, p1, "Tesla");
        Car c = new Car("00-AA-01", o, gas , 100.0, 1.50, 9.6, 120, p1, "BMW");
        carBase.addCar(a);
        carBase.addCar(c);
        assertSame(c, carBase.getCar(p1,120,gas));
    }
}