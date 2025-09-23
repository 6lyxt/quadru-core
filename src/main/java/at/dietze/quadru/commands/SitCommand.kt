package at.dietze.quadru.commands

import at.dietze.quadru.QuadruCore
import at.dietze.quadru.constants.ICommand
import at.dietze.quadru.constants.IStrings
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

class SitCommand : ICommand, CommandExecutor, IStrings {
    init {
        QuadruCore.addToDescriptions(this.action, this.description)
    }

    override val action: String
        get() = "sit"

    override val description: String
        get() = "sitz wie hund wuff wuff"

    override fun onCommand(commandSender: CommandSender, command: Command, s: String, strings: Array<String>): Boolean {
        val p = commandSender as Player

        if (command.name.equals(this.action, ignoreCase = true)) {
            this.spawnHelperEntity(p)
        }

        return true
    }

    /**
     * @param p Player
     */
    private fun spawnHelperEntity(p: Player) {
        if (p.isInsideVehicle) {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(IStrings.prefix + "§cDu sitzt bereits!"))
            return
        }

        if (!p.isOnGround) {
            p.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                TextComponent(IStrings.prefix + "§cDu musst stehen, um zu sitzen!")
            )
            return
        }

        val arrow = p.world.spawnEntity(p.location.subtract(0.0, 0.4, 0.0), EntityType.ARROW)
        arrow.setGravity(false)
        arrow.isPersistent = true
        arrow.isSilent = true
        arrow.addPassenger(p)
        p.spigot()
            .sendMessage(ChatMessageType.ACTION_BAR, TextComponent(IStrings.prefix + "§aUm aufzustehen, drücke SHIFT."))
    }
}
