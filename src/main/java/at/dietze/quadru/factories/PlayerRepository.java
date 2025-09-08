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
        String sql = "INSERT IGNORE INTO players (uuid) VALUES (?);";
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
    public boolean playerExists(Player p) {
        String sql = "SELECT * FROM players WHERE uuid = ?;";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getUniqueId().toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setPlayerIsland(Player p, String islandName) {
        UUID playerUUID = p.getUniqueId();
        String sql = "UPDATE players SET island = ? WHERE uuid = ?;";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, islandName.toLowerCase());
            ps.setString(2, playerUUID.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String fetchPlayerIsland(Player p) {
        String sql = "SELECT island FROM players WHERE uuid = ?;";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getUniqueId().toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("island");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "OBDACHLOS";
    }
}
