package com.lightniinja.wbi;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.event.extent.EditSessionEvent;
import com.sk89q.worldedit.util.eventbus.Subscribe;

public class WBIWorldEditHandler {
    @Subscribe
    public void wrapForLogging(EditSessionEvent event) {
        BukkitPlayer player = (BukkitPlayer) event.getActor();
        if (player != null) {
        	if(event.getStage() == EditSession.Stage.BEFORE_CHANGE) {
        		event.setExtent(new WBIWorldEditListeners(player, event.getExtent(), event.getWorld()));
        	}
        }
    }
}