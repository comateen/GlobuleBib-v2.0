package db;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by jof on 01/04/2016.
 */
public class Database {
    /*private String url= "jdbc:mysql://localhost:3306/GlobuleBib";
    private String user ="root";
    private String pass = null;
    private static Connection con = null;

    private Database(){
        try {
            con= DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getInstance(){
        if(con == null) {
            new Database();
        }
        return con;
    }*/

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
