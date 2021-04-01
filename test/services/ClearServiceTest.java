package services;

import daos.Database;

import daos.UserDao;
import dbModels.User;
import org.junit.jupiter.api.*;
import results.Result;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

class ClearServiceTest {

    @Test
    void clearPass() throws SQLException {
        Result result = ClearService.clear();
        assertNotNull(result);
        assertTrue(result.isSuccess());

        Database database = new Database();
        Connection connection = database.getConnection();
        database.clearTables();
        UserDao userDao = new UserDao(connection);
        userDao.insert(new User("dyl", "1234", "dyl@gmail.com",
                "Dylan", "Webb", "m", "01"));
        database.closeConnection(true);

        Result result2 = ClearService.clear();
        assertNotNull(result2);
        assertTrue(result2.isSuccess());
    }

    @Test
    void clearFail() throws SQLException {
        Database database = new Database();
        database.getConnection();
        database.clearTables();
        Result result = ClearService.clear();
        assertNotNull(result);
        assertFalse(result.isSuccess());
        database.closeConnection(true);
    }
}