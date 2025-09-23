package at.dietze.quadru.commands

import at.dietze.quadru.constants.ICommand
import at.dietze.quadru.constants.IStrings
import at.dietze.quadru.factories.NickRepository
import at.dietze.quadru.factories.PlayerNameFormatter.format
import at.dietze.quadru.factories.PlayerRepository
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class SetNickCommand : ICommand, IStrings, CommandExecutor {
    override val action: String
        get() = "setNick"

    override val description: String
        get() = "Setzt deinen Nicknamen."

    override fun onCommand(commandSender: CommandSender, command: Command, s: String, strings: Array<String>): Boolean {
        val p = commandSender as Player

        if (command.name.equals(this.action, ignoreCase = true)) {
            if (strings.size > 0) {
                var newNick = strings[0]
                val playerRepository = PlayerRepository()
                val islandName = playerRepository.fetchPlayerIsland(p).uppercase(Locale.getDefault())
                val nickRepository = NickRepository()
                nickRepository.upsertNick(p, newNick)

                newNick = format(newNick, islandName)
                p.displayName = newNick
                p.customName = newNick
                p.playerListName = newNick

                p.sendMessage(IStrings.prefix + "§aDein Nickname wurde zu §e" + newNick + " §ageändert. Wir empfehlen, einmal neuzujoinen, damit der Nickname überall übernommen wird.")
            } else {
                p.sendMessage(IStrings.prefix + "§cBitte gib einen Nicknamen an.")
            }
        }

        return true
    }
}
