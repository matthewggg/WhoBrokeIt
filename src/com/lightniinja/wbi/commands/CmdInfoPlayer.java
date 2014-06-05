package com.lightniinja.wbi.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.lightniinja.wbi.Change;
import com.lightniinja.wbi.ChangeQueue;
import com.lightniinja.wbi.Util;
import com.lightniinja.wbi.searchfuncs.SearchPlayer;

public class CmdInfoPlayer implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		SearchPlayer s = new SearchPlayer();
		int page = 0;
		if(args.length > 1) 
			page = Integer.valueOf(args[1]);
		ArrayList<String> res = s.searchAll(page, args[0]);
		if(res.size() <= 0) {
			sender.sendMessage(ChatColor.RED + " No changes found.");
		}
		for(String s2: res) {
			Change c = ChangeQueue.queue.decryptChange(s2);
			switch (c.TYPE) {
				default:
					sender.sendMessage(ChatColor.RED + " Invalid change.");
					break;
				case CHANGE_BREAK:
					String playername = new Util().getName(UUID.fromString(c.UUID));
					String blockname = c.BLOCK.getName();
					String location = "(" + c.POS.getWorld().getName() + "," + c.POS.getX() + "," + c.POS.getY() + "," + c.POS.getZ() + ")";
					sender.sendMessage(ChatColor.GOLD + playername + ChatColor.YELLOW + " broke " + ChatColor.GOLD + blockname + ChatColor.YELLOW + " at " + ChatColor.GOLD + location + ChatColor.YELLOW + " at " + ChatColor.GOLD + c.TIME);
					break;
				case CHANGE_PLACE:
					String playername2 = new Util().getName(UUID.fromString(c.UUID));
					String blockname2 = c.BLOCK.getName();
					String location2 = "(" + c.POS.getWorld().getName() + "," + c.POS.getX() + "," + c.POS.getY() + "," + c.POS.getZ() + ")";
					sender.sendMessage(ChatColor.GOLD + playername2 + ChatColor.YELLOW + " placed " + ChatColor.GOLD + blockname2 + ChatColor.YELLOW + " at " + ChatColor.GOLD + location2 + ChatColor.YELLOW + " at " + ChatColor.GOLD + c.TIME);
					break;
				case CHANGE_TNT:
					String playername3 = new Util().getName(UUID.fromString(c.UUID));
					String blockname3 = c.BLOCK.getName();
					String location3 = "(" + c.POS.getWorld().getName() + "," + c.POS.getX() + "," + c.POS.getY() + "," + c.POS.getZ() + ")";
					sender.sendMessage(ChatColor.GOLD + playername3 + ChatColor.YELLOW + " blew " + ChatColor.GOLD + blockname3 + ChatColor.YELLOW + " up at " + ChatColor.GOLD + location3 + ChatColor.YELLOW + " at " + ChatColor.GOLD + c.TIME);
					break;
				case CHANGE_WORLDEDIT:
					String playername4 = new Util().getName(UUID.fromString(c.UUID));
					String blockname4 = c.BLOCK.getName();
					String nbname = c.NEW.getName();
					String location4 = "(" + c.POS.getWorld().getName() + "," + c.POS.getX() + "," + c.POS.getY() + "," + c.POS.getZ() + ")";
					sender.sendMessage(ChatColor.GOLD + playername4 + ChatColor.YELLOW + " changed " + ChatColor.GOLD + blockname4 + ChatColor.YELLOW + " to " + ChatColor.GOLD + nbname + ChatColor.YELLOW + " at " + ChatColor.GOLD + location4 + ChatColor.YELLOW + " at " + ChatColor.GOLD + c.TIME);
					break;
			
			}
		}
		sender.sendMessage(ChatColor.GREEN + " Listing " + ChatColor.GOLD + res.size() + ChatColor.GREEN + " objects, from " + ChatColor.GOLD + (page * 30) + ChatColor.GREEN + " to " + ChatColor.GOLD + ((page * 30) + 30) + ChatColor.GREEN + " on page " + ChatColor.GOLD + page + ChatColor.GREEN + ".");
		return true;
	}
}
