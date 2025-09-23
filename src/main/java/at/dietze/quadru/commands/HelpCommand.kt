package at.dietze.quadru.commands

import at.dietze.quadru.QuadruCore
import at.dietze.quadru.constants.ICommand
import at.dietze.quadru.constants.IStrings
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.function.Consumer

class HelpCommand : ICommand, IStrings, CommandExecutor {
    init {
        QuadruCore.addToDescriptions(this.action, this.description)
    }

    override val action: String
        get() = "help"

    override val description: String
        get() = "Zeigt diesen Help-Command an."

    override fun onCommand(commandSender: CommandSender, command: Command, s: String, strings: Array<String>): Boolean {
        val p = commandSender as Player

        if (command.name.equals(this.action, ignoreCase = true)) {
            p.sendMessage(IStrings.prefix + "§6§lHilfe Menü")
            QuadruCore.descriptions.forEach(Consumer { s: String? ->
                p.sendMessage(
                    s!!
                )
            })
        }

        return true
    }
}
