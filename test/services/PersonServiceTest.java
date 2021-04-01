package services;

import daos.*;
import dbModels.*;
import org.junit.jupiter.api.*;
import results.PeopleResult;
import results.PersonResult;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {

    private Database database;
    private Person testPerson;
    private Person testPerson2;
    private Person testPerson3;
    private AuthToken testAuthToken;
    private AuthToken testAuthToken2;

    @BeforeEach
    void setUp() throws SQLException {
        User testUser = new User("dyl", "1234", "dyl@gmail.com",
                "Dylan", "Webb", "m", "01");
        User testUser2 = new User("dad", "5678", "cooldad@gmail.com",
                "Father", "Webb", "m", "02");
        testPerson = new Person("01", "dyl", "Dylan", "Webb",
                "m", null, null, null);
        testPerson2 = new Person("02", "dyl", "Father", "Webb",
                "m", null, null, "03");
        testPerson3 = new Person("03", "dyl", "Mother", "Webb",
                "f", null, null, "02");
        testAuthToken = new AuthToken("111", "01", new Timestamp(0));
        testAuthToken2 = new AuthToken("112", "02", new Timestamp(0));
        database = new Database();
        Connection connection = database.getConnection();
        database.clearTables();
        UserDao userDao = new UserDao(connection);
        PersonDao personDao = new PersonDao(connection);
        AuthTokenDao authTokenDao = new AuthTokenDao(connection);
        userDao.insert(testUser);
        userDao.insert(testUser2);
        personDao.insert(testPerson);
        personDao.insert(testPerson2);
        personDao.insert(testPerson3);
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
    void getPersonPass() {
        PersonResult personResult = PersonService.getPerson(testPerson.getPersonID(), testAuthToken.getAuthtoken());
        assertEquals(testPerson.getPersonID(), personResult.getPersonID());
        PersonResult personResult2 = PersonService.getPerson(testPerson2.getPersonID(), testAuthToken.getAuthtoken());
        assertEquals(testPerson2.getPersonID(), personResult2.getPersonID());
        PersonResult personResult3 = PersonService.getPerson(testPerson3.getPersonID(), testAuthToken.getAuthtoken());
        assertEquals(testPerson3.getPersonID(), personResult3.getPersonID());
    }

    @Test
    void getPersonFail() {
        PersonResult personResult = PersonService.getPerson(testPerson2.getPersonID(), testAuthToken2.getAuthtoken());
        assertFalse(personResult.isSuccess());
        PersonResult personResult2 = PersonService.getPerson(testPerson2.getPersonID(), "");
        assertFalse(personResult2.isSuccess());
        PersonResult personResult3 = PersonService.getPerson("", testAuthToken2.getAuthtoken());
        assertFalse(personResult3.isSuccess());
    }

    @Test
    void getPeoplePass() {
        PeopleResult peopleResult = PersonService.getPeople(testAuthToken.getAuthtoken());
        assertEquals(testPerson, peopleResult.getData().get(0));
        assertEquals(testPerson2, peopleResult.getData().get(1));
        assertEquals(testPerson3, peopleResult.getData().get(2));
        assertThrows(Exception.class, ()-> peopleResult.getData().get(3));
    }

    @Test
    void getPeopleFail() {
        PeopleResult peopleResult = PersonService.getPeople("");
        assertFalse(peopleResult.isSuccess());
        assertNull(peopleResult.getData());
        PeopleResult peopleResult2 = PersonService.getPeople(testAuthToken2.getAuthtoken());
        assertNull(peopleResult2.getData());
    }

}