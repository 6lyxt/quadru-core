package at.dietze.quadru.factories;

import at.dietze.quadru.db.DBConnector;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NickRepository {

    /**
     * @param p    Player
     * @param nick Nickname
     */
    public void upsertNick(Player p, String nick) {
        String sql = "INSERT INTO players (uuid, nick) VALUES (?, ?) ON DUPLICATE KEY UPDATE nick = VALUES(nick);";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getUniqueId().toString());
            ps.setString(2, nick);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param p Player
     * @return Nickname
     */
    public String fetchNick(Player p) {
        String sql = "SELECT nick FROM players WHERE uuid = ?;";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getUniqueId().toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nick");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
