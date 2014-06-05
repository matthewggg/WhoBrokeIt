package com.lightniinja.wbi;

public class Configurations {
	private static WBIPlugin pl;
	public ConnectionType storage;
	public String dbfile;
	public String tableprefix;
	public String host;
	public String user;
	public String pass;
	public String dbname;
	public Configurations(WBIPlugin pl) {
		Configurations.pl = pl;
	}
	public Configurations() {
		
		if(pl.getConfig().getString("storage").equalsIgnoreCase("mysql")) {
			storage = ConnectionType.CONNECTION_MYSQL;
		} else {
			storage = ConnectionType.CONNECTION_SQLITE;
		}
		dbfile = pl.getConfig().getString("dbfile");
		tableprefix = pl.getConfig().getString("tableprefix");
		host = pl.getConfig().getString("host");
		user = pl.getConfig().getString("user");
		pass = pl.getConfig().getString("pass");
		dbname = pl.getConfig().getString("dbname");
	}
	public void reload() {
		pl.reloadConfig();
	}
	public WBIPlugin getPL() {
		return pl;
	}
}
