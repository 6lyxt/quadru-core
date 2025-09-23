package at.dietze.quadru.db

import at.dietze.quadru.QuadruCore
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DBConnector private constructor() {
    val dbUrl: String? = QuadruCore.plugin.config.getString("DB_URL")
    val dbUser: String? = QuadruCore.plugin.config.getString("DB_USER")
    val dbPass: String? = QuadruCore.plugin.config.getString("DB_PASSWORD")

    init {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
        } catch (e: ClassNotFoundException) {
            System.err.println("JDBC Driver not found: " + e.message)
            throw RuntimeException("Failed to load JDBC driver.", e)
        }
    }

    /**
     * executes a raw SQL query
     *
     * @param query raw sql query
     */
    fun raw(query: String?) {
        try {
            connection.use { connection ->
                connection.createStatement().use { statement ->
                    statement.execute(query)
                }
            }
        } catch (e: SQLException) {
            System.err.println("Raw query failed: " + e.message)
        }
    }

    companion object {
        val instance: DBConnector = DBConnector()

        @get:Throws(SQLException::class)
        val connection: Connection
            /**
             * establishes a new database connection
             *
             * @return Connection
             * @throws SQLException if connection fails
             */
            get() = DriverManager.getConnection(
                instance.dbUrl,
                instance.dbUser,
                instance.dbPass
            )
    }
}
