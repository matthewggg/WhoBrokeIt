package com.lightniinja.wbi.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdHelp implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		printHelp(sender);
		return true;
	}
	private void printHelp(CommandSender sender) {
		if(sender.hasPermission("wbi.info")) {
			sender.sendMessage(ChatColor.GREEN + " /wbi info all [page]" + ChatColor.GRAY + " | " + ChatColor.RED + "View all blocks broken. [30 per page]");
			if(sender.hasPermission("wbi.info.player")) {
				sender.sendMessage(ChatColor.GREEN + " /wbi info player <player> [page]" + ChatColor.GRAY + " | " + ChatColor.RED + "View all blocks broken by <player>. [30 per page]");
			}
			if(sender.hasPermission("wbi.info.location")) {
				sender.sendMessage(ChatColor.GREEN + " /wbi info location <location> [page]" + ChatColor.GRAY + " | " + ChatColor.RED + "View all blocks broken at location. [30 per page]");
			}
			if(sender.hasPermission("wbi.info.time")) {
				sender.sendMessage(ChatColor.GREEN + " /wbi info time <date> <hour> [page]" + ChatColor.GRAY + " | " + ChatColor.RED + "View all blocks broken between <date> <hour>:00 and <hour>:59. [30 per page]");
			}
		}
		if(sender.hasPermission("wbi.clear")) {
			sender.sendMessage(ChatColor.GREEN + " /wbi clear all" + ChatColor.GRAY + " | " + ChatColor.RED + "Clear all break logs.");
			if(sender.hasPermission("wbi.clear.player")) {
				sender.sendMessage(ChatColor.GREEN + " /wbi clear player <player>" + ChatColor.GRAY + " | " + ChatColor.RED + "Clear all break loggings of <player>.");
			}
			if(sender.hasPermission("wbi.clear.location")) {
				sender.sendMessage(ChatColor.GREEN + " /wbi clear location <location>" + ChatColor.GRAY + " | " + ChatColor.RED + "Clear all break loggings of <location>.");
			}
			if(sender.hasPermission("wbi.clear.time")) {
				sender.sendMessage(ChatColor.GREEN + " /wbi clear time <date> <hour>" + ChatColor.GRAY + " | " + ChatColor.RED + "Clear all break loggings between <date> <hour>:00 and<hour>:59.");
			}
		}
		if(sender.hasPermission("wbi.clear.location") || sender.hasPermission("wbi.info.location")) {
			sender.sendMessage(ChatColor.LIGHT_PURPLE + "        ** Locations are formatted [world],<x>,<y>,<z>");
			sender.sendMessage(ChatColor.LIGHT_PURPLE + "          Example: " + ChatColor.YELLOW + "world" + ChatColor.GRAY + "," + ChatColor.YELLOW + "1553" + ChatColor.GRAY + "," + ChatColor.YELLOW + "64"+ ChatColor.GRAY + "," + ChatColor.YELLOW + "-612");
		}
		if(sender.hasPermission("wbi.tool")) {
			sender.sendMessage(ChatColor.GREEN + " /wbi tool" + ChatColor.GRAY + " | " + ChatColor.RED + "Get a tool for analysing a location.");
		}
	}
}
