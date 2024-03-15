package net.serveminecraft.minecrafteros.perworldchatreborn.tabcompleters

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class PerWorldChatCompleter : TabCompleter {

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>?): MutableList<String> {
        if (args?.size == 1) {
            val subCommands: MutableList<String> = mutableListOf("help", "reload")
            return subCommands
        }
        return mutableListOf()
    }

}