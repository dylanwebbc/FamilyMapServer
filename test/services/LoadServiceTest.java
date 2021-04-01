package services;

import daos.*;
import dbModels.Event;
import dbModels.Person;
import dbModels.User;
import org.junit.jupiter.api.*;
import requests.LoadRequest;
import results.Result;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LoadServiceTest {

    private Database database;
    private User testUser;
    private User testUser2;
    private Person testPerson;
    private Person testPerson2;
    private Person testPerson3;
    private Event testEvent;
    private Event testEvent2;
    private Event testEvent3;
    UserDao userDao;
    PersonDao personDao;
    EventDao eventDao;
    private ArrayList<User> users;
    private ArrayList<Person> people;
    private ArrayList<Event> events;

    @BeforeEach
    void setUp() throws SQLException {
        testUser = new User("dyl", "1234", "dyl@gmail.com",
                "Dylan", "Webb", "m", "01");
        testUser2 = new User("dad", "5678", "cooldad@gmail.com",
                "Father", "Webb", "m", "02");
        testPerson = new Person("01", "dyl", "Dylan", "Webb",
                "m", null, null, null);
        testPerson2 = new Person("02", "dyl", "Father", "Webb",
                "m", null, null, "03");
        testPerson3 = new Person("03", "dyl", "Mother", "Webb",
                "f", null, null, "02");
        testEvent = new Event("01", "dyl", "01",
                24.0, 8.9, "USA", "Provo", "College", 2019);
        testEvent2 = new Event("02", "dyl", "01",
                21.5, 9.4, "USA", "SLC", "Death", 2080);
        testEvent3 = new Event("03", "dad", "02",
                -30.2, 6.6, "Italy", "Rome", "Mission", 1980);
        users = new ArrayList<>();
        people = new ArrayList<>();
        events = new ArrayList<>();
        users.add(testUser);
        users.add(testUser2);
        people.add(testPerson);
        people.add(testPerson2);
        people.add(testPerson3);
        events.add(testEvent);
        events.add(testEvent2);
        events.add(testEvent3);
        database = new Database();
        Connection connection = database.getConnection();
        userDao = new UserDao(connection);
        personDao = new PersonDao(connection);
        eventDao = new EventDao(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        database.openConnection();
        database.clearTables();
        database.closeConnection(true);
    }

    @Test
    void loadPass() throws SQLException {
        LoadRequest loadRequest = new LoadRequest(users, people, events);
        LoadService.load(loadRequest);
        assertEquals(users.get(0), userDao.find(testUser.getUsername()));
        assertEquals(users.get(1), userDao.find(testUser2.getUsername()));
        assertEquals(people.get(0), personDao.find(testPerson.getPersonID()));
        assertEquals(people.get(1), personDao.find(testPerson2.getPersonID()));
        assertEquals(people.get(2), personDao.find(testPerson3.getPersonID()));
        assertEquals(events.get(0), eventDao.find(testEvent.getEventID()));
        assertEquals(events.get(1), eventDao.find(testEvent2.getEventID()));
        assertEquals(events.get(2), eventDao.find(testEvent3.getEventID()));

        database.closeConnection(true);
        Connection connection = database.getConnection();
        userDao = new UserDao(connection);
        personDao = new PersonDao(connection);
        eventDao = new EventDao(connection);

        LoadRequest loadRequest2 = new LoadRequest(users, new ArrayList<>(), new ArrayList<>());
        LoadService.load(loadRequest2);
        assertEquals(users.get(0), userDao.find(testUser.getUsername()));
        assertEquals(users.get(1), userDao.find(testUser2.getUsername()));
        assertNull(personDao.find(testPerson.getPersonID()));
        assertNull(personDao.find(testPerson2.getPersonID()));
        assertNull(personDao.find(testPerson3.getPersonID()));
        assertNull(eventDao.find(testEvent.getEventID()));
        assertNull(eventDao.find(testEvent2.getEventID()));
        assertNull(eventDao.find(testEvent3.getEventID()));

        database.closeConnection(false);
    }

    @Test
    void loadFail() {
        LoadRequest loadRequest = new LoadRequest(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Result result = LoadService.load(loadRequest);
        assertTrue(result.getMessage().contains("0 users"));
        assertTrue(result.getMessage().contains("0 persons"));
        assertTrue(result.getMessage().contains("0 events"));
        assertThrows(NullPointerException.class, ()-> LoadService.load(null));
    }
}