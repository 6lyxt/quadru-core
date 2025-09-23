package at.dietze.quadru.factories

object PlayerNameFormatter {
    @JvmStatic
    fun format(playerName: String, islandName: String?): String {
        var islandName = islandName
        if (islandName == null || islandName.isEmpty()) {
            islandName = "OBDACHLOS"
        }

        when (islandName) {
            "aloria" -> islandName = "§9ALORIA"
            "pyroka" -> islandName = "§6PYROKA"
        }

        return "§8(§7$islandName§8) §7$playerName§r"
    }
}
