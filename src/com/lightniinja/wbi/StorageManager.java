package com.lightniinja.wbi;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Logger;

import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.MySQL;
import lib.PatPeter.SQLibrary.SQLite;

public class StorageManager {
	private static Database sql;
	private ConnectionType connType;
	public StorageManager(boolean isFirstRun, ConnectionType con) {
		connType = con;
		if(con == ConnectionType.CONNECTION_MYSQL) {
			sql = new MySQL(Logger.getLogger("Minecraft"), "[WhoBrokeIt]",
					new Configurations().host,
					3306,
					new Configurations().dbname,
					new Configurations().user,
					new Configurations().pass);
		} else {
			sql = new SQLite(Logger.getLogger("Minecraft"), "[WhoBrokeIt]",
					new Configurations().getPL().getDataFolder().getAbsolutePath(),
					new File(new Configurations().dbfile).getName().split("\\.(?=[^\\.]+$)")[0],
					"." + new File(new Configurations().dbfile).getName().split("\\.(?=[^\\.]+$)")[1]);
		}
		sql.open();
		if(isFirstRun) {
			if(con == ConnectionType.CONNECTION_MYSQL) {
				try {
					sql.query("CREATE TABLE IF NOT EXISTS `" + new Configurations().tableprefix + "loggings` ("
							+ "`id` int(11) NOT NULL AUTO_INCREMENT," 
							+ "`savestring` text NOT NULL,"
							+ "PRIMARY KEY (`id`)"
							+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;");
					sql.query("CREATE TABLE IF NOT EXISTS `" + new Configurations().tableprefix  + "users` ( "
							+ "`id` int(11) NOT NULL AUTO_INCREMENT,"
							+ "`playername` varchar(64) NOT NULL,"
							+ "`uuid` varchar(64) NOT NULL,"
							+ "PRIMARY KEY (`id`)"
							+ ") engine=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=2;");
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				try {
					sql.query("CREATE TABLE IF NOT EXISTS " + new Configurations().tableprefix + "loggings ( "
							+ "savestring TEXT NOT NULL);");
					sql.query("CREATE TABLE IF NOT EXISTS wbi_" + new Configurations().tableprefix + " ("
							+ "playername VARCHAR( 64 ) NOT NULL,"
							+ "uuid VARCHAR( 64 ) NOT NULL"
							+ ");");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public StorageManager(ConnectionType con) {
		this(false, con);
	}
	public StorageManager() {
		String really_you_are_going_to_make_me_have_a_blank_constructor = "that is not cool";
		really_you_are_going_to_make_me_have_a_blank_constructor.contains("why are you making me do this");
	}
	public Database getDatabase() {
		return StorageManager.sql;
	}
	public ConnectionType getConn() {
		return this.connType;
	}
	
}
