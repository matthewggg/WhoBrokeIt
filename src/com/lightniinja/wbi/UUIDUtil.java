package com.lightniinja.wbi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;

import lib.PatPeter.SQLibrary.Database;

public class UUIDUtil {
	private UUID uuid;
	private String player;
	public UUIDUtil(String player) {
		this.player = player;
	}
	public UUIDUtil(UUID uuid) {
		this.uuid = uuid;
	}
	public String getPlayername() {
		String playername = null;
		if(Bukkit.getOfflinePlayer(uuid).getName() == null) {
			Database db = new StorageManager().getDatabase();
			try {
				ResultSet r = db.query("SELECT `playername` FROM `" + new Configurations().tableprefix + "users` WHERE `uuid`='" + uuid + "';");
				int count = 0;
				while(r.next()) {
					playername = r.getString("playername");
					count++;
				}
				if(count == 0) {
					return null;
				}
				return playername;
			} catch (SQLException e) {
				return null;
			}
		} else {
			return Bukkit.getOfflinePlayer(uuid).getName();
		}
	}
	@SuppressWarnings("deprecation")
	public UUID getUUID() {
		UUID uuid = null;
		if(Bukkit.getOfflinePlayer(player) == null) {
			Database db = new StorageManager().getDatabase();
			try {
				ResultSet r = db.query("SELECT `uuid` FROM `" + new Configurations().tableprefix + "users` WHERE `playername`='" + player + "';");
				int count = 0;
				while(r.next()) {
					uuid = UUID.fromString(r.getString("uuid"));
					count++;
				}
				if(count == 0) {
					return null;
				}
			} catch (SQLException e) {
				return null;
			}
			return uuid;
		} else {
			return Bukkit.getOfflinePlayer(player).getUniqueId();
		}
	}
}
