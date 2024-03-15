package net.serveminecraft.minecrafteros.perworldchatreborn.commands

import net.serveminecraft.minecrafteros.perworldchatreborn.PerWorldChatReborn
import net.serveminecraft.minecrafteros.perworldchatreborn.utils.MessagesUtil
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class PerWorldChatCommand : CommandExecutor {

    private val plugin: PerWorldChatReborn = PerWorldChatReborn.getInstance()
    private val replaces: HashMap<String, String> = HashMap()

    init {
        replaces["%prefix%"] = plugin.prefix
    }

    private fun help(sender: CommandSender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m----------------------------------------" +
                "\n&b&lPerWorldChat &8- &7Help" +
                "\n" +
                "\n&7- &e/pwc help &8- &7Shows this help" +
                "\n&7- &e/pwc reload &8- &7Reloads the plugin" +
                "\n&8&m----------------------------------------"
        ))
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player || command.permission == null || sender.hasPermission(command.permission!!) || !sender.isOp) {
            when (args?.size) {
                0 -> {
                    help(sender)
                    return true
                }

                1 -> {
                    when (args[0].lowercase(Locale.getDefault())) {
                        "help" -> {
                            help(sender)
                            return true
                        }

                        "reload" -> {
                            plugin.reloadConfig()
                            replaces["%prefix%"] = plugin.prefix
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MessagesUtil.getFullStringFromConfig(plugin.config, "messages.plugin-reloaded", replaces)))
                            return true
                        }

                        else -> {
                            help(sender)
                            return true
                        }
                    }
                }

                else -> {
                    help(sender)
                    return true
                }
            }
        } else if (!sender.isOp && command.permission != null && !sender.hasPermission(command.permission!!)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MessagesUtil.getFullStringFromConfig(plugin.config, "messages.no-permission", replaces)))
        }
        return false
    }
}