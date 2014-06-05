package com.lightniinja.wbi.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CmdTool implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			ItemStack is = new ItemStack(Material.STICK, 1);
			List<String> lore = new ArrayList<String>();
			lore.add("" + ChatColor.WHITE);
			lore.add("Right/left click to investigate a location.");
			ItemMeta im = is.getItemMeta();
			im.setLore(lore);
			im.setDisplayName(ChatColor.RED + "Investigation Tool");
			is.setItemMeta(im);
			ItemStack is1 = new ItemStack(Material.SPONGE, 1);
			List<String> lore1 = new ArrayList<String>();
			lore1.add("" + ChatColor.WHITE);
			lore1.add("Right/left click to investigate a location.");
			ItemMeta im1 = is1.getItemMeta();
			im1.setLore(lore1);
			im1.setDisplayName(ChatColor.RED + "Investigation Tool");
			is1.setItemMeta(im1);
			((Player) sender).getInventory().addItem(is);
			((Player) sender).getInventory().addItem(is1);
			sender.sendMessage(ChatColor.GREEN + " Here's your tool!");
		} else {
			sender.sendMessage(ChatColor.RED + " You must be a player to get the tool.");
		}
		return true;
	}
}
