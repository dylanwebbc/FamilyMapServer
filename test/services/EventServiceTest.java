package services;

import daos.*;
import dbModels.*;
import org.junit.jupiter.api.*;
import results.EventResult;
import results.EventsResult;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest {

    private Database database;
    private Event testEvent;
    private Event testEvent2;
    private Event testEvent3;
    private AuthToken testAuthToken;

    @BeforeEach
    void setUp() throws SQLException {
        User testUser = new User("dyl", "1234", "dyl@gmail.com",
                "Dylan", "Webb", "m", "01");
        User testUser2 = new User("dad", "5678", "cooldad@gmail.com",
                "Father", "Webb", "m", "02");
        Person testPerson = new Person("01", "dyl", "Dylan", "Webb",
                "m", null, null, null);
        Person testPerson2 = new Person("02", "dad", "Father", "Webb",
                "m", null, null, "03");
        Person testPerson3 = new Person("03", "mom", "Mother", "Webb",
                "f", null, null, "02");
        testEvent = new Event("01", "dyl", "01",
                24.0, 8.9, "USA", "Provo", "College", 2019);
        testEvent2 = new Event("02", "dyl", "01",
                21.5, 9.4, "USA", "SLC", "Death", 2080);
        testEvent3 = new Event("03", "dad", "02",
                -30.2, 6.6, "Italy", "Rome", "Mission", 1980);
        testAuthToken = new AuthToken("111", "01", new Timestamp(0));
        database = new Database();
        Connection connection = database.getConnection();
        database.clearTables();
        UserDao userDao = new UserDao(connection);
        PersonDao personDao = new PersonDao(connection);
        EventDao eventDao = new EventDao(connection);
        AuthTokenDao authTokenDao = new AuthTokenDao(connection);
        userDao.insert(testUser);
        userDao.insert(testUser2);
        personDao.insert(testPerson);
        personDao.insert(testPerson2);
        personDao.insert(testPerson3);
        eventDao.insert(testEvent);
        eventDao.insert(testEvent2);
        eventDao.insert(testEvent3);
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
    void getEventPass() {
        EventResult eventResult = EventService.getEvent(testEvent.getEventID(), testAuthToken.getAuthtoken());
        assertEquals(testEvent.getEventID(), eventResult.getEventID());
        EventResult eventResult2 = EventService.getEvent(testEvent2.getEventID(), testAuthToken.getAuthtoken());
        assertEquals(testEvent2.getEventID(), eventResult2.getEventID());
    }

    @Test
    void getEventFail() {
        EventResult eventResult = EventService.getEvent(testEvent3.getEventID(), testAuthToken.getAuthtoken());
        assertFalse(eventResult.isSuccess());
        EventResult eventResult2 = EventService.getEvent(testEvent3.getEventID(), "");
        assertFalse(eventResult2.isSuccess());
        EventResult eventResult3 = EventService.getEvent("", testAuthToken.getAuthtoken());
        assertFalse(eventResult3.isSuccess());
    }

    @Test
    void getEventsPass() {
        EventsResult eventsResult = EventService.getEvents(testAuthToken.getAuthtoken());
        assertEquals(testEvent, eventsResult.getData().get(0));
        assertEquals(testEvent2, eventsResult.getData().get(1));
        assertThrows(Exception.class, ()-> eventsResult.getData().get(2));
    }

    @Test
    void getEventsFail() {
        EventsResult eventsResult = EventService.getEvents("");
        assertFalse(eventsResult.isSuccess());
        assertNull(eventsResult.getData());
        EventsResult eventsResult2 = EventService.getEvents(testAuthToken.getAuthtoken());
        assertFalse(eventsResult2.getData().contains(testEvent3));
    }

}