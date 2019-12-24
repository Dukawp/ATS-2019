package Model;

import Exceptions.InvalidUserException;
import Exceptions.UserExistsException;
import Utils.Point;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UsersTest {

    Point p = new Point(1.0,2.1);
    Users userBase = new Users();
    Client u = new Client(p,"d@gmail.com","pass", "d", "rua",99999999);


    @Test
    void addUserTest() throws UserExistsException{
        userBase.addUser(u);
        assertEquals(1, userBase.());
    }

    @Test
    void getCLientsIdsTest() {
        assertEquals(1, userBase.getClientIDS().size());
    }

    @Test
    void getUser() throws UserExistsException, InvalidUserException {
        userBase.addUser(u);
        assertNotNull(userBase.getUser("d@gmail.com"));
    }
}