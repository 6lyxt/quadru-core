package at.dietze.quadru.commands

import at.dietze.quadru.QuadruCore
import at.dietze.quadru.constants.ICommand
import at.dietze.quadru.constants.IStrings
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class InvseeCommand : ICommand, CommandExecutor, IStrings {
    init {
        QuadruCore.addToDescriptions(this.action, this.description)
    }

    override val action: String
        get() = "invsee"

    override val description: String
        get() = "Öffnet das Inventar eines anderen Spielers."

    override fun onCommand(commandSender: CommandSender, command: Command, s: String, strings: Array<String>): Boolean {
        val p = commandSender as Player

        if (command.name.equals(this.action, ignoreCase = true)) {
            if (p.hasPermission("core.admin")) {
                if (strings.size > 0) {
                    val invPlayer = Bukkit.getPlayer(strings[0])
                    if (invPlayer != null) {
                        val inv: Inventory = invPlayer.inventory
                        p.openInventory(inv)
                    } else {
                        p.sendMessage(IStrings.prefix + "§cDieser Spieler wurde leider nicht gefunden.")
                    }
                }
            } else {
                p.sendMessage(IStrings.prefix + "§cDu hast keine Rechte, um diesen Befehl auszuführen.")
            }
        }

        return true
    }
}
