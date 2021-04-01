package daos;

import dbModels.Event;
import dbModels.Person;
import dbModels.User;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;
import java.util.ArrayList;

class UserDaoTest {

    private Database database;
    private User testUser;
    private User testUser2;
    private UserDao userDao;
    private PersonDao personDao;

    @BeforeEach
    void setUp() throws SQLException {
        testUser = new User("dyl", "1234", "dyl@gmail.com",
                "Dylan", "Webb", "m", "01");
        testUser2 = new User("dad", "5678", "cooldad@gmail.com",
                "Father", "Webb", "m", "02");
        database = new Database();
        Connection connection = database.getConnection();
        database.clearTables();
        userDao = new UserDao(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        database.closeConnection(false);
    }

    @Test
    void insertPass() throws SQLException {
        userDao.insert(testUser);
        User foundUser = userDao.find(testUser.getUsername());
        assertNotNull(foundUser);
        userDao.insert(testUser2);
        User foundUser2 = userDao.find(testUser2.getUsername());
        assertNotNull(foundUser2);
        assertEquals(testUser, foundUser);
        assertEquals(testUser2, foundUser2);
        assertNotEquals(foundUser, foundUser2);
    }

    @Test
    void insertFail() throws SQLException {
        userDao.insert(testUser);
        assertThrows(SQLException.class, ()-> userDao.insert(testUser));
    }

    @Test
    void findPass() throws SQLException {
        userDao.insert(testUser);
        User foundUser = userDao.find(testUser.getUsername());
        assertNotNull(foundUser);
    }

    @Test
    void findFail() throws SQLException {
        userDao.clear();
        assertNull(userDao.find(testUser.getUsername()));
        userDao.insert(testUser2);
        assertNull(userDao.find(testUser.getUsername()));
    }

    @Test
    void clearTest() throws SQLException {
        userDao.insert(testUser);
        userDao.clear();
        User foundUser = userDao.find(testUser.getUsername());
        assertNull(foundUser);
    }

    @Test
    void getFamilyPass() throws SQLException {
        ArrayList<Person> family = setupFamily();
        userDao.insert(testUser);
        ArrayList<Person> people = userDao.getFamily(testUser.getUsername());
        assertEquals(family, people);
    }

    @Test
    void getFamilyFail() throws SQLException {
        ArrayList<Person> family = setupFamily();
        userDao.insert(testUser);
        userDao.insert(testUser2);
        assertThrows(SQLException.class, ()-> userDao.getFamily(testUser2.getUsername()));
        assertThrows(SQLException.class, ()-> userDao.getFamily("mom"));

        personDao.updateParents(null, null, testUser.getPersonID());
        ArrayList<Person> people = userDao.getFamily(testUser.getUsername());
        assertNotEquals(family, people);
    }

    //for testing the getFamily function
    private ArrayList<Person> setupFamily() throws SQLException {
        ArrayList<Person> family = new ArrayList<>();
        Person testPerson = new Person("01", "dyl", "Dylan", "Webb",
                "m", "02", "03", null);
        Person testPerson2 = new Person("02", "dyl", "Father", "Webb",
                "m", null, null, "03");
        Person testPerson3 = new Person("03", "dyl", "Mother", "Webb",
                "f", null, null,"02");
        personDao = new PersonDao(database.getConnection());
        personDao.insert(testPerson);
        personDao.insert(testPerson2);
        personDao.insert(testPerson3);
        family.add(testPerson);
        family.add(testPerson2);
        family.add(testPerson3);
        return family;
    }
}