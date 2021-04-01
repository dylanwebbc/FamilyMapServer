package daos;

import dbModels.AuthToken;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

class AuthTokenDaoTest {

    private Database database;
    private AuthToken testToken;
    private AuthToken testToken2;
    private AuthTokenDao authTokenDao;

    @BeforeEach
    void setUp() throws SQLException {
        testToken = new AuthToken("111", "dyl", new Timestamp(10));
        testToken2 = new AuthToken( "122", "unknown", new Timestamp(12));
        database = new Database();
        Connection connection = database.getConnection();
        database.clearTables();
        authTokenDao = new AuthTokenDao(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        database.closeConnection(false);
    }

    @Test
    void insertPass() throws SQLException {
        authTokenDao.insert(testToken);
        AuthToken foundToken = authTokenDao.find(testToken.getAuthtoken());
        assertNotNull(foundToken);
        authTokenDao.insert(testToken2);
        AuthToken foundToken2 = authTokenDao.find(testToken2.getAuthtoken());
        assertNotNull(foundToken2);
        assertEquals(testToken, foundToken);
        assertEquals(testToken2, foundToken2);
        assertNotEquals(foundToken, foundToken2);
    }

    @Test
    void insertFail() throws SQLException {
        authTokenDao.insert(testToken);
        assertThrows(SQLException.class, ()-> authTokenDao.insert(testToken));
    }

    @Test
    void findPass() throws SQLException {
        authTokenDao.insert(testToken);
        AuthToken foundToken = authTokenDao.find(testToken.getAuthtoken());
        assertNotNull(foundToken);
    }

    @Test
    void findFail() throws SQLException {
        authTokenDao.clear();
        assertNull(authTokenDao.find(testToken.getAuthtoken()));
        authTokenDao.insert(testToken2);
        assertNull(authTokenDao.find(testToken.getAuthtoken()));
    }

    @Test
    void clearTest() throws SQLException {
        authTokenDao.insert(testToken);
        authTokenDao.clear();
        AuthToken foundToken = authTokenDao.find(testToken.getAuthtoken());
        assertNull(foundToken);
    }

}