package at.dietze.quadru.factories;

import at.dietze.quadru.db.DBConnector;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerRepository {

    /**
     * @param p player
     */
    public void upsertPlayer(Player p) {
        String sql = "INSERT INTO players (uuid) VALUES (?) ON CONFLICT (uuid) DO NOTHING;";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getUniqueId().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param p player
     * @return dbplayer
     */
    public DBPlayer fetchPlayer(Player p) {
        String sql = "SELECT * FROM players WHERE uuid = ?;";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getUniqueId().toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PlayerDB(
                            UUID.fromString(rs.getString("uuid")),
                            rs.getString("nick")
                    );
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
