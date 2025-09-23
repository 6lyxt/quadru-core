package at.dietze.quadru.factories

import at.dietze.quadru.db.DBConnector
import org.bukkit.entity.Player
import java.sql.SQLException
import java.util.*

class PlayerRepository {
    /**
     * @param p player
     */
    fun upsertPlayer(p: Player) {
        val sql = "INSERT IGNORE INTO players (uuid) VALUES (?);"
        try {
            DBConnector.getConnection().use { connection ->
                connection.prepareStatement(sql).use { ps ->
                    ps.setString(1, p.uniqueId.toString())
                    ps.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    /**
     * @param p player
     * @return dbplayer
     */
    fun playerExists(p: Player): Boolean {
        val sql = "SELECT * FROM players WHERE uuid = ?;"
        try {
            DBConnector.getConnection().use { connection ->
                connection.prepareStatement(sql).use { ps ->
                    ps.setString(1, p.uniqueId.toString())
                    try {
                        ps.executeQuery().use { rs ->
                            if (rs.next()) {
                                return true
                            }
                        }
                    } catch (e: SQLException) {
                        throw RuntimeException(e)
                    }
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return false
    }

    fun setPlayerIsland(p: Player, islandName: String) {
        val sql = "UPDATE players SET island = ? WHERE uuid = ?;"
        try {
            DBConnector.getConnection().use { connection ->
                connection.prepareStatement(sql).use { ps ->
                    ps.setString(1, islandName.lowercase(Locale.getDefault()))
                    ps.setString(2, p.uniqueId.toString())
                    println("Setting island of player " + p.uniqueId + " to " + islandName.lowercase(Locale.getDefault()))
                    ps.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun fetchPlayerIsland(p: Player): String {
        val sql = "SELECT island FROM players WHERE uuid = ?;"
        try {
            DBConnector.getConnection().use { connection ->
                connection.prepareStatement(sql).use { ps ->
                    ps.setString(1, p.uniqueId.toString())
                    ps.executeQuery().use { rs ->
                        if (rs.next()) {
                            return if (rs.getString("island") != null) {
                                rs.getString("island")
                            } else {
                                "OBDACHLOS"
                            }
                        }
                    }
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return "OBDACHLOS"
    }
}
