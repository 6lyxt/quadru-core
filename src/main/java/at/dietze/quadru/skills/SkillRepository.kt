package at.dietze.quadru.skills

import at.dietze.quadru.db.DBConnector
import java.sql.SQLException

class SkillRepository {
    fun getById(id: Int): Skill? {
        val sql = "SELECT id, name, description, icon, cost FROM skills WHERE id = ?;"
        try {
            DBConnector.connection.use { connection ->
                connection.prepareStatement(sql).use { ps ->
                    ps.setInt(1, id)
                    ps.executeQuery().use { rs ->
                        if (rs.next()) {
                            return Skill(
                                id = rs.getInt("id"),
                                name = rs.getString("name"),
                                description = rs.getString("description"),
                                icon = rs.getString("icon"),
                                cost = rs.getInt("cost")
                            )
                        }
                    }
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return null
    }

    fun addSkillToPlayer(playerUUID: String, skillId: Int): Boolean {
        val sql = "INSERT INTO player_skills (player_uuid, skill_id) VALUES (?, ?);"
        try {
            DBConnector.connection.use { connection ->
                connection.prepareStatement(sql).use { ps ->
                    ps.setString(1, playerUUID)
                    ps.setInt(2, skillId)
                    val rowsAffected = ps.executeUpdate()
                    return rowsAffected > 0
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return false
    }
}