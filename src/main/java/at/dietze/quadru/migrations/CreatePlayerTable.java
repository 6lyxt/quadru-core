package at.dietze.quadru.migrations;

import at.dietze.quadru.db.DBConnector;

public class CreatePlayerTable {

    public void run() {

        DBConnector connector = DBConnector.getInstance();

        String sql = "CREATE TABLE IF NOT EXISTS players (" +
                "id SERIAL PRIMARY KEY," +
                "uuid VARCHAR(36) NOT NULL UNIQUE," +
                "nick VARCHAR(16) NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "island VARCHAR(36) DEFAULT NULL," +
                ");";
        connector.raw(sql);
    }
}
