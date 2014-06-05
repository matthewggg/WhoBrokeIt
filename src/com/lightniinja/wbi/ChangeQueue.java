package com.lightniinja.wbi;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import lib.PatPeter.SQLibrary.Database;

import org.bukkit.Location;

public class ChangeQueue {
	public ArrayList<String> sqChanges = new ArrayList<String>();
	private ArrayList<String> unsubmit = new ArrayList<String>();
	/*
	 * 
	 *  Block breaks stored like: CHANGE_BREAK;PLAYER_UUID;BLOCK_DATA;LOCATION
	 *  Block places stored like: CHANGE_PLACE;PLAYER_UUID;BLOCK_DATA;LOCATION
	 *  TNT explodes stored like: CHANGE_TNT;TNT#PLAYER_UUID;BLOCK_DATA;LOCATION
	 *  WE Changes stored like: CHANGE_WORLDEDIT;PLAYER_UUID;OLD_BLOCK;NEW_BLOCK;LOCATION
	 * 
	 */
	
	
	private int unSaved = 0;
	public static ChangeQueue queue = new ChangeQueue();
	
	public void addChange(ChangeType c, String uuid, BlockData b, Location l) {
		Calendar now = Calendar.getInstance();
		sqChanges.add(c + ";" + uuid + ";" + b + ";" + new Util().loc2str(l) + ";" + now.get(Calendar.MONTH) + "/" +  now.get(Calendar.DAY_OF_WEEK_IN_MONTH) + "/" + now.get(Calendar.YEAR) + " " + now.get(Calendar.HOUR_OF_DAY));
		unsubmit.add(c + ";" + uuid + ";" + b + ";" + new Util().loc2str(l) + ";" + now.get(Calendar.MONTH) + "/" +  now.get(Calendar.DAY_OF_WEEK_IN_MONTH) + "/" + now.get(Calendar.YEAR) + " " + now.get(Calendar.HOUR_OF_DAY));
		unSaved++;
		doUpdate();
	}
	public void addChange(String uuid, BlockData o, BlockData n, Location pos) {
		Calendar now = Calendar.getInstance();
		sqChanges.add("CHANGE_WORLDEDIT;" + uuid + ";" + o + ";" + n + ";" + new Util().loc2str(pos) + ";" + now.get(Calendar.MONTH) + "/" +  now.get(Calendar.DAY_OF_WEEK_IN_MONTH) + "/" + now.get(Calendar.YEAR) + " " + now.get(Calendar.HOUR_OF_DAY));
		unsubmit.add("CHANGE_WORLDEDIT;" + uuid + ";" + o + ";" + n + ";" + new Util().loc2str(pos) + ";" + now.get(Calendar.MONTH) + "/" +  now.get(Calendar.DAY_OF_WEEK_IN_MONTH) + "/" + now.get(Calendar.YEAR) + " " + now.get(Calendar.HOUR_OF_DAY));
		unSaved++;
		doUpdate();
	}
	public Change decryptChange(String s) {
		String[] s1 = s.split(";");
		Change c = null;
		switch(s1[0]) {
		case "CHANGE_BREAK":
			String uuid = s1[1];
			BlockData data = new BlockData("AIR",0).toData(s1[2]);
			Location pos = new Util().str2loc(s1[3]);
			c = new Change(ChangeType.CHANGE_BREAK, uuid, data, pos, s1[4]);
			break;
		case "CHANGE_PLACE":
			String uuid1 = s1[1];
			BlockData data2 = new BlockData("AIR",0).toData(s1[2]);
			Location pos2 = new Util().str2loc(s1[3]);
			c = new Change(ChangeType.CHANGE_PLACE, uuid1, data2, pos2, s1[4]);
			
			break;
		case "CHANGE_TNT":
			String uuid3 = s1[1];
			BlockData data3 = new BlockData("AIR",0).toData(s1[2]);
			Location pos4 = new Util().str2loc(s1[3]);
			c = new Change(ChangeType.CHANGE_TNT, uuid3, data3, pos4, s1[4]);
			break;
		case "CHANGE_WORLDEDIT":
			String uuid2 = s1[1];
			BlockData old = new BlockData("AIR",0).toData(s1[2]);
			BlockData new_ = new BlockData("AIR",0).toData(s1[3]);
			Location pos3 = new Util().str2loc(s1[4]);
			c = new Change(uuid2, pos3, old, new_, s1[5]);
			break;
		default:
			break;
		}
		return c;
	}
	public void doUpdate() {
		if(unSaved >= 20) {
			ConnectionType connectionType = new Configurations().storage;
			Database sql = new StorageManager().getDatabase();
			if(connectionType == ConnectionType.CONNECTION_MYSQL) {
				// mysql
				for(int i = 0; i < (unSaved); i++) {
					try {
						sql.query("INSERT INTO `" + new Configurations().tableprefix + "loggings` (`id`, `savestring`) VALUES (NULL, '" + unsubmit.get(i) + "');");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				// sqlite
				for(int i = 0; i < (unSaved); i++) {
					try {
						sql.query("INSERT INTO " + new Configurations().tableprefix + "loggings (savestring) VALUES ('" + unsubmit.get(i) + "')");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			unSaved = 0;
			unsubmit.clear();
		}
	}
	public void doUpdate2() {
		ConnectionType connectionType = new Configurations().storage;
		Database sql = new StorageManager().getDatabase();
		if(connectionType == ConnectionType.CONNECTION_MYSQL) {
			// mysql
			for(int i = 0; i < (unSaved); i++) {
				try {
					sql.query("INSERT INTO `" + new Configurations().tableprefix + "loggings` (`id`, `savestring`) VALUES (NULL, '" + unsubmit.get(i) + "');");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			// sqlite
			for(int i = 0; i < (unSaved); i++) {
				try {
					sql.query("INSERT INTO " + new Configurations().tableprefix + "loggings (savestring) VALUES ('" + unsubmit.get(i) + "')");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		unSaved = 0;
		unsubmit.clear();
	}
}
