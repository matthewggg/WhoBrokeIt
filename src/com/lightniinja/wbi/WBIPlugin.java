package com.lightniinja.wbi;

import org.bukkit.plugin.java.JavaPlugin;

import com.lightniinja.wbi.commands.CmdHelp;
import com.lightniinja.wbi.commands.CmdTool;
import com.lightniinja.wbi.commands.CommandSubClear;
import com.lightniinja.wbi.commands.CommandSubInfo;
import com.sk89q.worldedit.WorldEdit;

public class WBIPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		this.getServer().getPluginManager().registerEvents(new WBIBlockListeners(this), this);
		if(getServer().getPluginManager().isPluginEnabled("WorldEdit")) {
			WorldEdit.getInstance().getEventBus().register(new WBIWorldEditHandler());
		}
		new Configurations(this);
		CommandSub com = new CommandSub(this);
		com.registerCommand("help", new CmdHelp());
		com.registerCommand("tool", new CmdTool());
		com.registerCommand("info", new CommandSubInfo());
		com.registerCommand("clear",  new CommandSubClear());
		getCommand("wbi").setExecutor(com);
		new StorageManager(true, new Configurations().storage);
	}
	
}
