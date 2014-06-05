package com.lightniinja.wbi.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.lightniinja.wbi.clearfuncs.ClearLocation;

public class CmdClearLocation implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		ClearLocation s = new ClearLocation();
		s.clearAll(args[0]);
		sender.sendMessage(ChatColor.GREEN + " Cleared all recorded changes at " + args[0] + ".");
		return true;
	}
}
