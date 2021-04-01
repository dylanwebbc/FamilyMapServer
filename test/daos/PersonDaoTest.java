package daos;

import dbModels.Event;
import dbModels.Person;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonDaoTest {

    private Database database;
    private Person testPerson;
    private Person testPerson2;
    private Person testPerson3;
    private PersonDao personDao;
    private Event testEvent;
    private Event testEvent2;
    private Event testEvent3;
    private EventDao eventDao;

    @BeforeEach
    void setUp() throws SQLException {
        testPerson = new Person("01", "dyl", "Dylan", "Webb",
                "m", null, null, null);
        testPerson2 = new Person("02", "dad", "Father", "Webb",
                "m", null, null, "03");
        testPerson3 = new Person("03", "mom", "Mother", "Webb",
                "f", null, null,"02");
        testEvent = new Event("01", "dyl", "01",
                24.0, 8.9, "USA", "Provo", "College", 2019);
        testEvent2 = new Event("02", "dyl", "01",
                21.5, 9.4, "USA", "SLC", "Death", 2080);
        testEvent3 = new Event("03", "dad", "02",
                -30.2, 6.6, "Italy", "Rome", "Mission", 1980);
        database = new Database();
        Connection connection = database.getConnection();
        database.clearTables();
        personDao = new PersonDao(connection);
        eventDao = new EventDao(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        database.closeConnection(false);
    }

    @Test
    void insertPass() throws SQLException {
        personDao.insert(testPerson);
        Person foundPerson = personDao.find(testPerson.getPersonID());
        assertNotNull(foundPerson);
        personDao.insert(testPerson2);
        Person foundPerson2 = personDao.find(testPerson2.getPersonID());
        assertNotNull(foundPerson2);
        assertEquals(testPerson, foundPerson);
        assertEquals(testPerson2, foundPerson2);
        assertNotEquals(foundPerson, foundPerson2);
    }

    @Test
    void insertFail() throws SQLException {
        personDao.insert(testPerson);
        assertThrows(SQLException.class, ()-> personDao.insert(testPerson));
    }

    @Test
    void findPass() throws SQLException {
        personDao.insert(testPerson);
        Person foundPerson = personDao.find(testPerson.getPersonID());
        assertNotNull(foundPerson);
    }

    @Test
    void findFail() throws SQLException {
        personDao.clear();
        assertNull(personDao.find(testPerson.getPersonID()));
        personDao.insert(testPerson2);
        assertNull(personDao.find(testPerson.getPersonID()));
    }

    @Test
    void clearTest() throws SQLException {
        personDao.insert(testPerson);
        personDao.clear();
        Person foundPerson = personDao.find(testPerson.getPersonID());
        assertNull(foundPerson);
    }

    @Test
    void getEventsPass() throws SQLException {
        personDao.insert(testPerson);
        personDao.insert(testPerson2);
        eventDao.insert(testEvent);
        eventDao.insert(testEvent2);
        eventDao.insert(testEvent3);

        ArrayList<Event> events = personDao.getEvents(testPerson.getPersonID());
        assertEquals(events.get(0), testEvent);
        assertEquals(events.get(1), testEvent2);
        ArrayList<Event> events2 = personDao.getEvents(testPerson2.getPersonID());
        assertEquals(events2.get(0), testEvent3);
    }

    @Test
    void getEventsFail() throws SQLException {
        personDao.insert(testPerson);
        personDao.insert(testPerson2);
        assertThrows(SQLException.class, ()-> personDao.getEvents(testPerson.getPersonID()));

        eventDao.insert(testEvent);
        eventDao.insert(testEvent2);
        eventDao.insert(testEvent3);
        ArrayList<Event> events1 = personDao.getEvents(testPerson.getPersonID());
        assertThrows(Exception.class, ()-> events1.get(2));
        ArrayList<Event> events2 = personDao.getEvents(testPerson2.getPersonID());
        assertThrows(Exception.class, ()-> events2.get(1));
    }

    @Test
    void updateParentsPass() throws SQLException {
        personDao.insert(testPerson);
        personDao.insert(testPerson2);
        personDao.insert(testPerson3);
        assertNotEquals(testPerson.getFatherID(), testPerson2.getPersonID());
        assertNotEquals(testPerson.getMotherID(), testPerson3.getPersonID());

        personDao.updateParents(testPerson3.getPersonID(), testPerson2.getPersonID(), testPerson.getPersonID());
        Person updatedPerson = personDao.find(testPerson.getPersonID());
        assertEquals(updatedPerson.getFatherID(), testPerson2.getPersonID());
        assertEquals(updatedPerson.getMotherID(), testPerson3.getPersonID());
    }

    @Test
    void updateParentsFail() throws SQLException {
        personDao.insert(testPerson);
        personDao.insert(testPerson2);
        personDao.insert(testPerson3);
        personDao.updateParents(testPerson3.getPersonID(), testPerson2.getPersonID(), testPerson.getPersonID());
        personDao.updateParents(null, null, null);

        Person updatedPerson = personDao.find(testPerson.getPersonID());
        assertNotNull(updatedPerson.getMotherID());
        assertNotEquals(updatedPerson.getFatherID(), testPerson3.getPersonID());
        assertNotEquals(updatedPerson.getMotherID(), testPerson2.getPersonID());
    }

}