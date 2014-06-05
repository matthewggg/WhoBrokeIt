package com.lightniinja.wbi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import lib.PatPeter.SQLibrary.Database;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import com.lightniinja.wbi.searchfuncs.SearchLocation;



public class WBIBlockListeners implements Listener {
	private WBIPlugin pl;
	public WBIBlockListeners(WBIPlugin pl) {
		this.pl = pl;
	}
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent e) {
		ChangeQueue.queue.addChange(ChangeType.CHANGE_BREAK, e.getPlayer().getUniqueId().toString(), new BlockData(e.getBlock().getType().name(), e.getBlock().getData()), e.getBlock().getLocation());
		ChangeQueue.queue.doUpdate();
	}
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent e) {
		if(e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getType() != Material.AIR) {
			if(e.getPlayer().getItemInHand().getItemMeta().getLore() != null) {
				if(e.getPlayer().getItemInHand().getItemMeta().getLore().get(0).equalsIgnoreCase("" + ChatColor.WHITE)) {
					if(e.getBlock() != null) {
						if(e.getPlayer().hasPermission("wbi.tool")) {
							e.getPlayer().sendMessage(ChatColor.GREEN + "WhoBrokeIt " + this.pl.getDescription().getVersion() + " by" + ChatColor.DARK_RED + " lightniinja");
							SearchLocation s = new SearchLocation();
							int page = 0;
							String clb = e.getBlock().getLocation().getWorld().getName() + "," + e.getBlock().getLocation().getX() + "," + e.getBlock().getLocation().getY() + "," + e.getBlock().getLocation().getZ();
							ArrayList<String> res = s.searchAll(page, clb);
							if(res.size() <= 0) {
								e.getPlayer().sendMessage(ChatColor.RED + " No changes found.");
							}
							for(String s2: res) {
								Change c = ChangeQueue.queue.decryptChange(s2);
								switch (c.TYPE) {
									default:
										e.getPlayer().sendMessage(ChatColor.RED + " Invalid change.");
										break;
									case CHANGE_BREAK:
										String playername = new Util().getName(UUID.fromString(c.UUID));
										String blockname = c.BLOCK.getName();
										String location = "(" + c.POS.getWorld().getName() + "," + c.POS.getX() + "," + c.POS.getY() + "," + c.POS.getZ() + ")";
										e.getPlayer().sendMessage(ChatColor.GOLD + playername + ChatColor.YELLOW + " broke " + ChatColor.GOLD + blockname + ChatColor.YELLOW + " at " + ChatColor.GOLD + location + ChatColor.YELLOW + " at " + ChatColor.GOLD + c.TIME);
										break;
									case CHANGE_PLACE:
										String playername2 = new Util().getName(UUID.fromString(c.UUID));
										String blockname2 = c.BLOCK.getName();
										String location2 = "(" + c.POS.getWorld().getName() + "," + c.POS.getX() + "," + c.POS.getY() + "," + c.POS.getZ() + ")";
										e.getPlayer().sendMessage(ChatColor.GOLD + playername2 + ChatColor.YELLOW + " placed " + ChatColor.GOLD + blockname2 + ChatColor.YELLOW + " at " + ChatColor.GOLD + location2 + ChatColor.YELLOW + " at " + ChatColor.GOLD + c.TIME);
										break;
									case CHANGE_TNT:
										String playername3 = new Util().getName(UUID.fromString(c.UUID));
										String blockname3 = c.BLOCK.getName();
										String location3 = "(" + c.POS.getWorld().getName() + "," + c.POS.getX() + "," + c.POS.getY() + "," + c.POS.getZ() + ")";
										e.getPlayer().sendMessage(ChatColor.GOLD + playername3 + ChatColor.YELLOW + " blew " + ChatColor.GOLD + blockname3 + ChatColor.YELLOW + " up at " + ChatColor.GOLD + location3 + ChatColor.YELLOW + " at " + ChatColor.GOLD + c.TIME);
										break;
									case CHANGE_WORLDEDIT:
										String playername4 = new Util().getName(UUID.fromString(c.UUID));
										String blockname4 = c.BLOCK.getName();
										String nbname = c.NEW.getName();
										String location4 = "(" + c.POS.getWorld().getName() + "," + c.POS.getX() + "," + c.POS.getY() + "," + c.POS.getZ() + ")";
										e.getPlayer().sendMessage(ChatColor.GOLD + playername4 + ChatColor.YELLOW + " changed " + ChatColor.GOLD + blockname4 + ChatColor.YELLOW + " to " + ChatColor.GOLD + nbname + ChatColor.YELLOW + " at " + ChatColor.GOLD + location4 + ChatColor.YELLOW + " at " + ChatColor.GOLD + c.TIME);
										break;
								
								}
							}
							e.getPlayer().sendMessage(ChatColor.GREEN + " Listing " + ChatColor.GOLD + res.size() + ChatColor.GREEN + " objects, from " + ChatColor.GOLD + (page * 30) + ChatColor.GREEN + " to " + ChatColor.GOLD + ((page * 30) + 30) + ChatColor.GREEN + " on page " + ChatColor.GOLD + page + ChatColor.GREEN + ".");
							e.getPlayer().sendMessage(ChatColor.GREEN + " To view other pages, type /wbi info location " + e.getBlock().getLocation().getWorld().getName() + "," + (int) e.getBlock().getLocation().getX() + "," + (int) e.getBlock().getLocation().getY() + "," + (int) e.getBlock().getLocation().getZ() + " [1,2...]");
							e.setCancelled(true);
						}
					}
				}
			}
		}
		ChangeQueue.queue.addChange(ChangeType.CHANGE_PLACE, e.getPlayer().getUniqueId().toString(), new BlockData(e.getBlock().getType().name(), e.getBlock().getData()), e.getBlock().getLocation());
		ChangeQueue.queue.doUpdate();
	}
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.MONITOR)
	public void onEntityExplode(EntityExplodeEvent e) {
		if(e.getEntityType() == EntityType.PRIMED_TNT) {
			TNTPrimed tnt = (TNTPrimed) e.getEntity();
			if(tnt.getSource().getType() == EntityType.PLAYER) {
				String uuidmod = tnt.getSource().getUniqueId().toString();
				for(int i = 0; i < e.blockList().size(); i++) {
					ChangeQueue.queue.addChange(ChangeType.CHANGE_TNT, uuidmod, new BlockData(e.blockList().get(i).getType().name(), e.blockList().get(i).getData()), e.blockList().get(i).getLocation());
				}
			}
		}
	}
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onInteract(PlayerInteractEvent e) {
		//Util.Points d = new Util().new Points();
		if(e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getType() != Material.AIR) {
			if(e.getPlayer().getItemInHand().getItemMeta().getLore() != null) {
				if(e.getPlayer().getItemInHand().getItemMeta().getLore().get(0).equalsIgnoreCase("" + ChatColor.WHITE)) {
					if(e.getClickedBlock() != null) {
						if(e.getPlayer().hasPermission("wbi.tool")) {
							e.getPlayer().sendMessage(ChatColor.GREEN + "WhoBrokeIt " + this.pl.getDescription().getVersion() + " by" + ChatColor.DARK_RED + " lightniinja");
							SearchLocation s = new SearchLocation();
							int page = 0;
							String clb = e.getClickedBlock().getLocation().getWorld().getName() + "," + e.getClickedBlock().getLocation().getX() + "," + e.getClickedBlock().getLocation().getY() + "," + e.getClickedBlock().getLocation().getZ();
							ArrayList<String> res = s.searchAll(page, clb);
							if(res.size() <= 0) {
								e.getPlayer().sendMessage(ChatColor.RED + " No changes found.");
							}
							for(String s2: res) {
								Change c = ChangeQueue.queue.decryptChange(s2);
								switch (c.TYPE) {
									default:
										e.getPlayer().sendMessage(ChatColor.RED + " Invalid change.");
										break;
									case CHANGE_BREAK:
										String playername = new Util().getName(UUID.fromString(c.UUID));
										String blockname = c.BLOCK.getName();
										String location = "(" + c.POS.getWorld().getName() + "," + c.POS.getX() + "," + c.POS.getY() + "," + c.POS.getZ() + ")";
										e.getPlayer().sendMessage(ChatColor.GOLD + playername + ChatColor.YELLOW + " broke " + ChatColor.GOLD + blockname + ChatColor.YELLOW + " at " + ChatColor.GOLD + location + ChatColor.YELLOW + " at " + ChatColor.GOLD + c.TIME);
										break;
									case CHANGE_PLACE:
										String playername2 = new Util().getName(UUID.fromString(c.UUID));
										String blockname2 = c.BLOCK.getName();
										String location2 = "(" + c.POS.getWorld().getName() + "," + c.POS.getX() + "," + c.POS.getY() + "," + c.POS.getZ() + ")";
										e.getPlayer().sendMessage(ChatColor.GOLD + playername2 + ChatColor.YELLOW + " placed " + ChatColor.GOLD + blockname2 + ChatColor.YELLOW + " at " + ChatColor.GOLD + location2 + ChatColor.YELLOW + " at " + ChatColor.GOLD + c.TIME);
										break;
									case CHANGE_TNT:
										String playername3 = new Util().getName(UUID.fromString(c.UUID));
										String blockname3 = c.BLOCK.getName();
										String location3 = "(" + c.POS.getWorld().getName() + "," + c.POS.getX() + "," + c.POS.getY() + "," + c.POS.getZ() + ")";
										e.getPlayer().sendMessage(ChatColor.GOLD + playername3 + ChatColor.YELLOW + " blew " + ChatColor.GOLD + blockname3 + ChatColor.YELLOW + " up at " + ChatColor.GOLD + location3 + ChatColor.YELLOW + " at " + ChatColor.GOLD + c.TIME);
										break;
									case CHANGE_WORLDEDIT:
										String playername4 = new Util().getName(UUID.fromString(c.UUID));
										String blockname4 = c.BLOCK.getName();
										String nbname = c.NEW.getName();
										String location4 = "(" + c.POS.getWorld().getName() + "," + c.POS.getX() + "," + c.POS.getY() + "," + c.POS.getZ() + ")";
										e.getPlayer().sendMessage(ChatColor.GOLD + playername4 + ChatColor.YELLOW + " changed " + ChatColor.GOLD + blockname4 + ChatColor.YELLOW + " to " + ChatColor.GOLD + nbname + ChatColor.YELLOW + " at " + ChatColor.GOLD + location4 + ChatColor.YELLOW + " at " + ChatColor.GOLD + c.TIME);
										break;
								
								}
							}
							e.getPlayer().sendMessage(ChatColor.GREEN + " Listing " + ChatColor.GOLD + res.size() + ChatColor.GREEN + " objects, from " + ChatColor.GOLD + (page * 30) + ChatColor.GREEN + " to " + ChatColor.GOLD + ((page * 30) + 30) + ChatColor.GREEN + " on page " + ChatColor.GOLD + page + ChatColor.GREEN + ".");
							e.getPlayer().sendMessage(ChatColor.GREEN + " To view other pages, type /wbi info location " + e.getClickedBlock().getLocation().getWorld().getName() + "," + (int) e.getClickedBlock().getLocation().getX() + "," + (int) e.getClickedBlock().getLocation().getY() + "," + (int) e.getClickedBlock().getLocation().getZ() + " [1,2...]");
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		try {
			Database db = new StorageManager().getDatabase();
			String query = "SELECT * FROM `" + new Configurations().tableprefix + "users` WHERE `uuid`='" + e.getPlayer().getUniqueId().toString() + "';";
			ResultSet r = db.query(query);
			int c = 0;
			while(r.next()) {
				c++;
			}
			if(c == 0) {
				String qew = "INSERT INTO `" + new Configurations().tableprefix + "users` ("
						+ "`id`,"
						+ "`playername`,"
						+ "`uuid`"
						+ ") "
						+ "VALUES ("
						+ "NULL,"
						+ "'" + e.getPlayer().getName() +"',"
						+ "'" + e.getPlayer().getUniqueId().toString() + "'"
						+ ");" ;
				db.query(qew);
			} else {
				// check if username is right
				r.first();
				if(!(r.getString("playername").equalsIgnoreCase(e.getPlayer().getName()))) {
					String qew = "UPDATE `" + new Configurations().tableprefix  + "users` SET `playername`='" + e.getPlayer().getName()+ "' WHERE `uuid`='" + e.getPlayer().getUniqueId().toString() +"'";
					db.query(qew);
				}
			}
		} catch (SQLException ex) {
			e.setKickMessage("Failed to register UUID.");
			e.setResult(Result.KICK_OTHER);
			ex.printStackTrace();
		}
	}
	
}
