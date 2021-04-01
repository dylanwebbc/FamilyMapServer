package services;

import daos.AuthTokenDao;
import daos.Database;
import daos.UserDao;
import dbModels.AuthToken;
import dbModels.User;
import org.junit.jupiter.api.*;
import requests.LoginRequest;
import results.LoginResult;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

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
    void loginPass() {
        LoginRequest loginRequest = new LoginRequest(testUser.getUsername(), testUser.getPassword());
        LoginResult loginResult = LoginService.login(loginRequest);
        assertTrue(loginResult.isSuccess());
        assertNotEquals(loginResult.getAuthtoken(), testAuthToken.getAuthtoken());
    }

    @Test
    void loginFail() {
        LoginRequest loginRequest = new LoginRequest(testUser.getUsername(), "");
        LoginResult loginResult = LoginService.login(loginRequest);
        assertFalse(loginResult.isSuccess());

        LoginRequest loginRequest2 = new LoginRequest("", testUser.getPassword());
        LoginResult loginResult2 = LoginService.login(loginRequest2);
        assertFalse(loginResult2.isSuccess());

        LoginRequest loginRequest3 = new LoginRequest(null, null);
        LoginResult loginResult3 = LoginService.login(loginRequest3);
        assertFalse(loginResult3.isSuccess());

        assertThrows(NullPointerException.class, ()-> LoginService.login(null));
    }

}