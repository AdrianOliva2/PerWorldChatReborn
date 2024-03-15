package net.serveminecraft.minecrafteros.perworldchatreborn

import net.serveminecraft.minecrafteros.perworldchatreborn.commands.PerWorldChatCommand
import net.serveminecraft.minecrafteros.perworldchatreborn.listeners.PlayerListener
import net.serveminecraft.minecrafteros.perworldchatreborn.tabcompleters.PerWorldChatCompleter
import org.bukkit.ChatColor
import org.bukkit.command.PluginCommand
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class PerWorldChatReborn : JavaPlugin() {

    private val pluginDescriptionFile: PluginDescriptionFile = this.description
    private val pluginName: String = pluginDescriptionFile.name
    private val pluginVersion: String = pluginDescriptionFile.version
    var prefix: String = ""

    companion object {
        fun getInstance(): PerWorldChatReborn {
            return getPlugin(PerWorldChatReborn::class.java)
        }
    }

    override fun onEnable() {
        registerConfig()
        prefix = ChatColor.translateAlternateColorCodes('&', config.getString("messages.prefix", "")!!)
        registerListeners()
        registerCommands()
        if (server.pluginManager.getPlugin("PlaceholderAPI") == null) {
            server.consoleSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&b$pluginName&8] &cPlugin &lPlaceholderAPI &4not found, disabling plugin"))
            server.pluginManager.disablePlugin(this)
            return
        } else {
            server.consoleSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&b$pluginName&8] &aPlugin &lPlaceholderAPI &afound, enabling plugin"))
        }
        if (server.pluginManager.isPluginEnabled("PlaceholderAPI"))

            this.server.consoleSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&b$pluginName&8] &aHas been enabled on version &8[&b$pluginVersion&8]"))
    }

    private fun registerConfig() {
        config.options().copyDefaults()
        saveDefaultConfig()
    }

    private fun registerListeners() {
        val pluginManager = this.server.pluginManager
        pluginManager.registerEvents(PlayerListener(), this)
    }

    private fun registerCommands() {
        val perWorldChatCommand: PluginCommand? = getCommand("perworldchat")
        if (perWorldChatCommand != null) {
            perWorldChatCommand.setExecutor(PerWorldChatCommand())
            perWorldChatCommand.tabCompleter = PerWorldChatCompleter()
        }
    }

    override fun reloadConfig() {
        val file = File(dataFolder, "config.yml")
        if (!file.exists())
            saveDefaultConfig()
        else
            super.reloadConfig()
        this.saveConfig()
        prefix = ChatColor.translateAlternateColorCodes('&', config.getString("messages.prefix", "")!!)
    }

    override fun onDisable() {
        this.server.consoleSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&b$pluginName&8] &cHas been disabled on version &8[&b$pluginVersion&8]"))
    }
}
