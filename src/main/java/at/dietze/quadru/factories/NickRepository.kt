package at.dietze.quadru.factories

import at.dietze.quadru.db.DBConnector
import org.bukkit.entity.Player
import java.sql.SQLException

class NickRepository {
    /**
     * @param p    Player
     * @param nick Nickname
     */
    fun upsertNick(p: Player, nick: String?) {
        val sql = "INSERT INTO players (uuid, nick) VALUES (?, ?) ON DUPLICATE KEY UPDATE nick = VALUES(nick);"
        try {
            DBConnector.connection.use { connection ->
                connection.prepareStatement(sql).use { ps ->
                    ps.setString(1, p.uniqueId.toString())
                    ps.setString(2, nick)
                    ps.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    /**
     * @param p Player
     * @return Nickname
     */
    fun fetchNick(p: Player): String? {
        val sql = "SELECT nick FROM players WHERE uuid = ?;"
        try {
            DBConnector.connection.use { connection ->
                connection.prepareStatement(sql).use { ps ->
                    ps.setString(1, p.uniqueId.toString())
                    ps.executeQuery().use { rs ->
                        if (rs.next()) {
                            return rs.getString("nick")
                        }
                    }
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return null
    }
}
