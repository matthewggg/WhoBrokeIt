package com.lightniinja.wbi.clearfuncs;

import java.sql.SQLException;

import com.lightniinja.wbi.Configurations;
import com.lightniinja.wbi.StorageManager;
import com.lightniinja.wbi.Util;

public class ClearPlayer {
	public void clearAll(String srch2) {
		String srch = srch2;
		if(!new Util().isUUID(srch2))
			srch = new Util().getUUID(srch2).toString();
		String query = "DELETE FROM `" + new Configurations().tableprefix + "loggings` WHERE `savestring` LIKE '%" + srch +"%'";
		try {
			new StorageManager().getDatabase().query(query);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
