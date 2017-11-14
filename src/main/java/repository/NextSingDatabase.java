package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NextSingDatabase {
    public static Connection getConn() {
        try {
            Class.forName("org.postgresql.Driver");

            final String HOST = System.getenv("HOST");
            final String DATABASE = System.getenv("DATABASE");
            final String USER = System.getenv("USER");
            final String PASS = System.getenv("PASS");

            final String sslString = "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

            String dbUrl = String.format("jdbc:postgresql://%s/%s%s", HOST, DATABASE, sslString);

            return DriverManager.getConnection(dbUrl, USER, PASS);
        } catch (SQLException se) {
            throw new RuntimeException("Could not connect", se);
        } catch (ClassNotFoundException cnfe) {
            throw new RuntimeException("Package issue", cnfe);
        }
    }
}
