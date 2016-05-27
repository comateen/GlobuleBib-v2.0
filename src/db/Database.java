package db;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by jof on 01/04/2016.
 */
public class Database {
    private MysqlDataSource dataSource = new MysqlDataSource();

    private Database() {
        dataSource.setDatabaseName("GlobuleBib");
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setUser("root");
        dataSource.setPassword("");
    }

    public static Database getInstance() {
        return DbSingleton.instance;
    }

    /* throws car c'est pas la DB qui va gérér l'erreur */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private static class DbSingleton {
        private static final Database instance = new Database();
    }
}
