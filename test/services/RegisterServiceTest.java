package services;

import daos.*;
import dbModels.User;
import org.junit.jupiter.api.*;
import requests.LoginRequest;
import requests.RegisterRequest;
import results.LoginResult;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {

    private Database database;
    private User testUser;
    private UserDao userDao;
    private PersonDao personDao;

    @BeforeEach
    void setUp() throws SQLException {
        testUser = new User("dyl", "1234", "dyl@gmail.com",
                "Dylan", "Webb", "m", "01");
        database = new Database();
        Connection connection = database.getConnection();
        userDao = new UserDao(connection);
        personDao = new PersonDao(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        database.clearTables();
        database.closeConnection(true);
    }

    @Test
    void registerPass() throws SQLException {
        RegisterRequest registerRequest = new RegisterRequest(testUser.getUsername(), testUser.getPassword(),
                testUser.getEmail(), testUser.getFirstName(), testUser.getLastName(), testUser.getGender());
        LoginResult registerResult = RegisterService.register(registerRequest);

        assertTrue(registerResult.isSuccess());
        User foundUser = userDao.find(registerResult.getUsername());
        assertNotNull(foundUser);
        assertNotNull(personDao.find(foundUser.getPersonID()));
        assertEquals(31, userDao.getFamily(registerResult.getUsername()).size());
        assertEquals(1, personDao.getEvents(foundUser.getPersonID()).size());
    }

    @Test
    void registerFail() {
        RegisterRequest registerRequest = new RegisterRequest(testUser.getUsername(), testUser.getPassword(),
                testUser.getEmail(), testUser.getFirstName(), testUser.getLastName(), testUser.getGender() + ".");
        LoginResult registerResult = RegisterService.register(registerRequest);
        assertFalse(registerResult.isSuccess());
        assertThrows(NullPointerException.class, ()-> RegisterService.register(null));
    }

}