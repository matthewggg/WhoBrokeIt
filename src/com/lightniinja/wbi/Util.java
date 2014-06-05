package com.lightniinja.wbi;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Util {
	public Location str2loc(String s) {
		s = s.replaceAll("\\(", "");
		s = s.replaceAll("\\)", "");
		String[] s1 = s.split(",");
		return new Location(Bukkit.getWorld(s1[0]), Double.valueOf(s1[1]), Double.valueOf(s1[2]), Double.valueOf(s1[3]));
	}
	public String loc2str(Location s) {
		return "(" + s.getWorld().getName() + "," + s.getX() + "," + s.getY() + "," + s.getZ() + ")";
	}
	public String getUUID(String playername) {
		return new UUIDUtil(playername).getUUID().toString();
			
	}
	public String getName(UUID uuid) {
		return new UUIDUtil(uuid).getPlayername();
	}
	public boolean isDouble(Object check) {
		try {
			Double.valueOf(String.valueOf(check));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public boolean isUUID(String uuid){
        if( uuid == null) return false;
        try {
            UUID fromStringUUID = UUID.fromString(uuid);
            String toStringUUID = fromStringUUID.toString();
            return toStringUUID.equals(uuid);
        } catch(IllegalArgumentException e) {
            return false;
        }
    }
	
}
