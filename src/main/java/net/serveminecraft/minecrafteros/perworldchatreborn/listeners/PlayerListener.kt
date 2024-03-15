package net.serveminecraft.minecrafteros.perworldchatreborn.listeners

import me.clip.placeholderapi.PlaceholderAPI
import net.serveminecraft.minecrafteros.perworldchatreborn.PerWorldChatReborn
import org.bukkit.ChatColor
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class PlayerListener : Listener {

    private val plugin: PerWorldChatReborn = PerWorldChatReborn.getInstance()

    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        val config: FileConfiguration = plugin.config
        var format: String? = config.getString("chat-format", "")

        if (format == null || format == "")
            return

        val player: Player = event.player
        var message: String = ChatColor.translateAlternateColorCodes('&', event.message)

        if (!player.hasPermission("perworldchat.chatcolor") && !player.isOp) {
            player.sendMessage("You don't have permission")
            message = ChatColor.stripColor(message)!!
        }

        event.isCancelled = true

        for (worldPlayer: Player in player.world.players) {
            if (format != null) {
                format = format.replace("%message%", message)
                worldPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, format)))
            }
        }
    }

}