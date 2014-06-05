package com.lightniinja.wbi;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.extent.logging.AbstractLoggingExtent;
import com.sk89q.worldedit.world.World;

public class WBIWorldEditListeners extends AbstractLoggingExtent {
	private final BukkitPlayer player;
	private final World world;
	public WBIWorldEditListeners(BukkitPlayer player, Extent extent, World world) {
		super(extent);
		this.player = player;
		this.world = world;
	}
	@Override
    protected void onBlockChange(Vector position, BaseBlock newBlock) {
       BaseBlock oldBlock = getBlock(position);
       Location l = new Location(Bukkit.getWorld(world.getName()), position.getX(), position.getY(), position.getZ());
        ChangeQueue.queue.addChange(new Util().getUUID(player.getName()).toString(), new BlockData(oldBlock.toString(), oldBlock.getData()), new BlockData(newBlock.toString(), newBlock.getData()), l);
        ChangeQueue.queue.doUpdate();
    }
}