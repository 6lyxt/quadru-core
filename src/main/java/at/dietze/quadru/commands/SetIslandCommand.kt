package at.dietze.quadru.commands

import at.dietze.quadru.constants.ICommand
import at.dietze.quadru.constants.IStrings
import at.dietze.quadru.factories.PlayerRepository
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetIslandCommand : ICommand, CommandExecutor, IStrings {
    override val action: String
        get() = "setIsland"

    override val description: String
        get() = "Setzt die Insel eines Spielers."

    override fun onCommand(commandSender: CommandSender, command: Command, s: String, strings: Array<String>): Boolean {
        val p = commandSender as Player

        if (command.name.equals(this.action, ignoreCase = true)) {
            if (p.hasPermission("core.admin")) {
                if (strings.size > 1) {
                    val targetPlayerName = strings[0]
                    val islandName = strings[1]
                    if (islandName.equals("Pyroka", ignoreCase = true) || islandName.equals(
                            "Aloria",
                            ignoreCase = true
                        )
                    ) {
                        val toBeNicked = Bukkit.getPlayer(targetPlayerName)

                        if (toBeNicked == null) {
                            p.sendMessage(IStrings.prefix + "§cDer Spieler §e" + targetPlayerName + " §cwurde nicht gefunden.")
                            return false
                        }

                        val playerRepository = PlayerRepository()
                        playerRepository.setPlayerIsland(toBeNicked, islandName)

                        p.sendMessage(IStrings.prefix + "§aDie Insel von §e" + targetPlayerName + " §awurde zu §e" + islandName + " §ageändert.")
                    } else {
                        p.sendMessage(IStrings.prefix + "§cDie Insel muss entweder 'Pyroka' oder 'Aloria' sein.")
                        return false
                    }
                } else {
                    p.sendMessage(IStrings.prefix + "§cCommandstruktur: /setIsland <Spielername> <Pyroka|Aloria>")
                }
            } else {
                p.sendMessage(IStrings.prefix + "§cDu hast keine Rechte, um diesen Befehl auszuführen.")
            }
        }

        return true
    }
}
