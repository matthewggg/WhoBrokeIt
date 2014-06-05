package com.lightniinja.wbi;

import org.bukkit.Location;

public class Change {
	public String UUID;
	public Location POS;
	public BlockData BLOCK;
	public ChangeType TYPE;
	public BlockData NEW;
	public String TIME;
	public String CHANGESTRING;
	public Change(ChangeType changeBreak, String uuid2, BlockData block, Location pos2, String time) {
		UUID = uuid2;
		POS = pos2;
		BLOCK = block;
		TYPE = changeBreak;
		TIME = time;
		CHANGESTRING = changeBreak + ";" + UUID + ";" + block + ";" + new Util().loc2str(POS) + ";" + TIME;
	}
	public Change(String uuid, Location pos, BlockData old, BlockData new_, String time) {
		UUID = uuid;
		POS = pos;
		BLOCK = old;
		NEW = new_;
		TYPE = ChangeType.CHANGE_WORLDEDIT;
		TIME = time;
		CHANGESTRING = ChangeType.CHANGE_WORLDEDIT + ";" + UUID + ";" + old + ";" + new_ + ";" + new Util().loc2str(POS) + ";" + TIME;
	}
}
