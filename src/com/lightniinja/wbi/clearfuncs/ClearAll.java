package com.lightniinja.wbi.clearfuncs;

import java.sql.SQLException;

import com.lightniinja.wbi.Configurations;
import com.lightniinja.wbi.StorageManager;

public class ClearAll {
	public void clearAll() {
		String query = "TRUNCATE TABLE `" + new Configurations().tableprefix + "loggings`";
		try {
			new StorageManager().getDatabase().query(query);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
