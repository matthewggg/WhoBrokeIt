package com.lightniinja.wbi.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.lightniinja.wbi.clearfuncs.ClearTime;

public class CmdClearTime implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length < 2) {
			sender.sendMessage(ChatColor.RED + " Invalid time string!");
			return true;
		}
		ClearTime s = new ClearTime();
		s.clearAll(args[0] + " " + args[1]);
		String srch = args[0] + " " + args[1];
		sender.sendMessage(ChatColor.GREEN + " Cleared all recorded changes by " + srch + ".");
		return true;
	}
}
