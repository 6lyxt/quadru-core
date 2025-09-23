package at.dietze.quadru.factories;

public class PlayerNameFormatter {

    public static String format(String playerName, String islandName) {
        if (islandName == null || islandName.isEmpty()) {
            islandName = "OBDACHLOS";
        }

        switch (islandName) {
            case "aloria":
                islandName = "§9ALORIA";
                break;
            case "pyroka":
                islandName = "§6PYROKA";
                break;
        }

        return "§8(§7" + islandName + "§8)" + " §7" + playerName + "§r";
    }
}
