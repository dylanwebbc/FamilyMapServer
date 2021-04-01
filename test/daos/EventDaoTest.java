package daos;

import dbModels.Event;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

class EventDaoTest {

    private Database database;
    private Event testEvent;
    private Event testEvent2;
    private EventDao eventDao;

    @BeforeEach
    void setUp() throws SQLException {
        testEvent = new Event("01", "dyl", "01",
                21.5, 9.4, "USA", "SLC", "Death", 2080);
        testEvent2 = new Event("02", "ray", "02",
                -30.2, 6.6, "Peru", "Trujillo", "Mission", 2017);
        database = new Database();
        Connection connection = database.getConnection();
        database.clearTables();
        eventDao = new EventDao(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        database.closeConnection(false);
    }

    @Test
    void insertPass() throws SQLException {
        eventDao.insert(testEvent);
        Event foundEvent = eventDao.find(testEvent.getEventID());
        assertNotNull(foundEvent);
        eventDao.insert(testEvent2);
        Event foundEvent2 = eventDao.find(testEvent2.getEventID());
        assertNotNull(foundEvent2);
        assertEquals(testEvent, foundEvent);
        assertEquals(testEvent2, foundEvent2);
        assertNotEquals(foundEvent, foundEvent2);
    }

    @Test
    void insertFail() throws SQLException {
        eventDao.insert(testEvent);
        assertThrows(SQLException.class, ()-> eventDao.insert(testEvent));
    }

    @Test
    void findPass() throws SQLException {
        eventDao.insert(testEvent);
        Event foundEvent = eventDao.find(testEvent.getEventID());
        assertNotNull(foundEvent);
    }

    @Test
    void findFail() throws SQLException {
        eventDao.clear();
        assertNull(eventDao.find(testEvent.getEventID()));
        eventDao.insert(testEvent2);
        assertNull(eventDao.find(testEvent.getEventID()));
    }

    @Test
    void clearTest() throws SQLException {
        eventDao.insert(testEvent);
        eventDao.insert(testEvent2);
        eventDao.clear();
        Event foundEvent = eventDao.find(testEvent.getEventID());
        assertNull(foundEvent);
        Event foundEvent2 = eventDao.find(testEvent2.getEventID());
        assertNull(foundEvent2);
    }

    @Test
    void clearUsernameTest() throws SQLException {
        eventDao.insert(testEvent);
        eventDao.insert(testEvent2);
        eventDao.clear(testEvent.getAssociatedUsername());
        Event foundEvent = eventDao.find(testEvent.getEventID());
        assertNull(foundEvent);
        Event foundEvent2 = eventDao.find(testEvent2.getEventID());
        assertNotNull(foundEvent2);
    }

}