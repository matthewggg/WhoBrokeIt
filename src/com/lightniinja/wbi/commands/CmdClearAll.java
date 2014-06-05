package com.lightniinja.wbi.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.lightniinja.wbi.clearfuncs.ClearAll;

public class CmdClearAll implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		ClearAll s = new ClearAll();
		s.clearAll();
		sender.sendMessage(ChatColor.GREEN + " Cleared all recorded changes.");
		return true;
	}
}
