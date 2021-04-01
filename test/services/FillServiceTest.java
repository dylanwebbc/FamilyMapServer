package services;

import daos.*;
import dbModels.AuthToken;
import dbModels.User;
import org.junit.jupiter.api.*;
import requests.FillRequest;
import results.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class FillServiceTest {

    private Database database;
    private User testUser;
    private AuthToken testAuthToken;

    @BeforeEach
    void setUp() throws SQLException {
        testUser = new User("dyl", "1234", "dyl@gmail.com",
                "Dylan", "Webb", "m", "01");
        testAuthToken = new AuthToken("111", "01", new Timestamp(0));
        database = new Database();
        Connection connection = database.getConnection();
        database.clearTables();
        UserDao userDao = new UserDao(connection);
        AuthTokenDao authTokenDao = new AuthTokenDao(connection);
        userDao.insert(testUser);
        authTokenDao.insert(testAuthToken);
        database.closeConnection(true);
    }

    @AfterEach
    void tearDown() throws SQLException {
        database.openConnection();
        database.clearTables();
        database.closeConnection(true);
    }

    @Test
    void fillPass() {
        FillRequest fillRequest = new FillRequest(testUser.getUsername());
        Result result = FillService.fill(fillRequest);
        assertTrue(result.isSuccess());
        EventsResult eventsResult = EventService.getEvents(testAuthToken.getAuthtoken());
        assertEquals(91, eventsResult.getData().size());
        PeopleResult peopleResult = PersonService.getPeople(testAuthToken.getAuthtoken());
        assertEquals(31, peopleResult.getData().size());

        FillRequest fillRequest2 = new FillRequest(testUser.getUsername(), 1);
        Result result2 = FillService.fill(fillRequest2);
        assertTrue(result2.isSuccess());
        EventsResult eventsResult2 = EventService.getEvents(testAuthToken.getAuthtoken());
        assertEquals(7, eventsResult2.getData().size());
        PeopleResult peopleResult2 = PersonService.getPeople(testAuthToken.getAuthtoken());
        assertEquals(3, peopleResult2.getData().size());
    }

    @Test
    void fillFail() {
        FillRequest fillRequest = new FillRequest("");
        Result result = FillService.fill(fillRequest);
        assertFalse(result.isSuccess());
        FillRequest fillRequest2 = new FillRequest(testUser.getUsername(), 0);
        Result result2 = FillService.fill(fillRequest2);
        assertFalse(result2.isSuccess());
        assertThrows(NullPointerException.class, ()-> FillService.fill(null));
    }

}