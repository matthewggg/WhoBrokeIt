package com.lightniinja.wbi.commands;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.lightniinja.wbi.Util;
import com.lightniinja.wbi.clearfuncs.ClearPlayer;;

public class CmdClearPlayer implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		ClearPlayer s = new ClearPlayer();
		s.clearAll(args[0]);
		String srch = args[0];
		if(new Util().isUUID(srch))
			srch = new Util().getName(UUID.fromString(srch));
		sender.sendMessage(ChatColor.GREEN + " Cleared all recorded changes by " + srch + ".");
		return true;
	}
}
