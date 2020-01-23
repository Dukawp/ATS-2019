package Model;

import Utils.Point;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static Model.Car.CarType.gas;
import static org.junit.jupiter.api.Assertions.*;

class RentalTest {

    Point p = new Point(1.0,2.1);
    Point p1 = new Point(5.0,1.1);
    Client o = new Client( p,"d@gmail.com", "as","D", "rua", 99999999);
    Owner s = new Owner("d@gmail.com", "D", "rua", 99999999, "pass");
    Car a = new Car("00-AA-00", s, gas , 100.0, 1.50, 9.6, 120, p, "Aston");

    Rental n = new Rental(a,o,p1);

    @Test
    void getDate() {
        assertEquals(LocalDateTime.now().getHour(),n.getDate().getHour());
    }

    @Test
    void getDistance() {
        assertEquals(Math.sqrt(Math.pow(5 - 1, 2) + Math.pow(1.1 - 2.1, 2)),n.getDistance());
    }

    @Test
    void getPrice() {
        assertEquals(0,n.getPrice());
    }

    @Test
    void getCarID() {
        assertEquals("00-AA-00",n.getCarID());
    }

    @Test
    void getClientID() {
        assertEquals("d@gmail.com",n.getClientID());
    }

    @Test
    void getOwnerID() {
        assertEquals("d@gmail.com",n.getOwnerID());
    }


}