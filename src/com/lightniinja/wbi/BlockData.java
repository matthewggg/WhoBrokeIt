package com.lightniinja.wbi;

import org.bukkit.Material;

public class BlockData {
	public String BLOCK_NAME = "AIR";
	public int BLOCK_DATA = 0;
	public BlockData(String name, int data) {
		BLOCK_NAME = name;
		BLOCK_DATA = data;
	}
	public String toString() {
		return "(" + BLOCK_NAME + "," + BLOCK_DATA + ")";
	}
	public BlockData toData(String s) {
		String s2 = s.replaceAll("\\(", "");
		s2 = s2.replaceAll("\\)", "");
		String[] s1 = s2.split(",");
		return new BlockData(s1[0], Integer.valueOf(s1[1]));
	}
	public String getName() {
		return Material.getMaterial(BLOCK_NAME) + "(" + BLOCK_DATA + ")";
	}
}
