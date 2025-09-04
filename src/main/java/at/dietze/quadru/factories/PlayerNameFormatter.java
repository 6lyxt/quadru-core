package at.dietze.quadru.factories;

public class PlayerNameFormatter {

    public static String format(String playerName, String islandName) {
        if (islandName == null || islandName.isEmpty()) {
            islandName = "OBDACHLOS";
        }

        return "§8(§7" + islandName + "§8)" + " §r" + playerName + "§r";
    }
}
