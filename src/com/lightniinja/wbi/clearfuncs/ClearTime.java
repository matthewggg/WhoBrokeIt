package com.lightniinja.wbi.clearfuncs;

import java.sql.SQLException;

import com.lightniinja.wbi.Configurations;
import com.lightniinja.wbi.StorageManager;

public class ClearTime {
	public void clearAll(String srch2) {
		String srch = srch2;
		String query = "DELETE FROM `" + new Configurations().tableprefix + "loggings` WHERE `savestring` LIKE '%" + srch +"%'";
		try {
			new StorageManager().getDatabase().query(query);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
