package com.lightniinja.wbi;

import java.lang.reflect.Array;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.Java15Compat;


public class CommandSub implements CommandExecutor {

    private WBIPlugin plugin;
    private HashMap<String, CommandExecutor> commandMap = new HashMap<String, CommandExecutor>();
    
    public CommandSub(WBIPlugin plugin) {
        if (plugin == null) throw new IllegalArgumentException("plugin cannot be null");
        this.plugin = plugin;
    }
    
    public void registerCommand(String command, CommandExecutor commandExecutor) {
        if (commandExecutor == null || command == null || command.isEmpty()) {
            throw new IllegalArgumentException("invalid command paramters specified");
        }
        this.commandMap.put(command.toLowerCase(), commandExecutor);
    }
    
    public void unregisterCommand(String command) {
        this.commandMap.remove(command.toLowerCase());
    }
    
    public CommandExecutor getCommandExecutor(String command) {
        return this.commandMap.get(command.toLowerCase());
    }
    
    public boolean hasCommand(String command) {
        return this.commandMap.containsKey(command.toLowerCase());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    		sender.sendMessage(ChatColor.GREEN + "WhoBrokeIt " + plugin.getDescription().getVersion() + " by" + ChatColor.DARK_RED + " lightniinja");
            if (args.length > 1 && hasCommand(args[0])) {
                if(sender.hasPermission("wbi." + args[0])) {
                	CommandExecutor executor = this.getCommandExecutor(args[0]);
                    return executor.onCommand(sender, cmd, label, popArray(args));
                } else {
                	sender.sendMessage(ChatColor.RED + " No permission.");
                	return true;
                }
            } else if(args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("tool")) ) {
                if(sender.hasPermission("wbi.info." + args[0])) {
                	CommandExecutor executor = this.getCommandExecutor(args[0]);
                    return executor.onCommand(sender, cmd, label, new String[0]);
                } else {
                	sender.sendMessage(ChatColor.RED + " No permission.");
                	return true;
                }
            } else {
                sender.sendMessage(ChatColor.GREEN + "Do " + ChatColor.AQUA + "/wbi help " + ChatColor.GREEN + "to see help for the plugin.");
                return true;
            }
    }
	@SuppressWarnings("unchecked")
	private static final <T> T[] popArray(T[] args) {
        	return (args.length >= 2) ? Java15Compat.Arrays_copyOfRange(args, 1, args.length) : 
            	(T[]) Array.newInstance(args.getClass().getComponentType(), 0);
    }
}