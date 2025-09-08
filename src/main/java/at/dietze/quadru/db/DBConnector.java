package at.dietze.quadru.db;

import at.dietze.quadru.QuadruCore;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class DBConnector {
    private static final DBConnector INSTANCE = new DBConnector();
    private final String dbUrl;
    private final String dbUser;
    private final String dbPass;

    private DBConnector() {

        this.dbUrl = QuadruCore.getPlugin().getConfig().getString("DB_URL");
        this.dbUser = QuadruCore.getPlugin().getConfig().getString("DB_USER");
        this.dbPass = QuadruCore.getPlugin().getConfig().getString("DB_PASSWORD");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
            throw new RuntimeException("Failed to load JDBC driver.", e);
        }
    }

    public static DBConnector getInstance() {
        return INSTANCE;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPass() {
        return dbPass;
    }

    /**
     * establishes a new database connection
     *
     * @return Connection
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getInstance().getDbUrl(), getInstance().getDbUser(), getInstance().getDbPass());
    }

    /**
     * executes a raw SQL query
     *
     * @param query raw sql query
     */
    public void raw(String query) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            System.err.println("Raw query failed: " + e.getMessage());
        }
    }
}
