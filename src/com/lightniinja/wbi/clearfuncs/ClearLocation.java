package com.lightniinja.wbi.clearfuncs;

import java.sql.SQLException;

import com.lightniinja.wbi.Configurations;
import com.lightniinja.wbi.StorageManager;

public class ClearLocation {
	public void clearAll(String srch2) {
		String[] srch = srch2.split(",");
		String res = "(";
		if(srch.length != 3 ) {
			if(srch.length != 4)
				return;
			res += srch[0];
			res += "," + Double.valueOf(srch[1]);
			res += "," + Double.valueOf(srch[2]);
			res += "," + Double.valueOf(srch[3]) + ")";
		} else {
			res += "world";
			res += "," + Double.valueOf(srch[0]);
			res += "," + Double.valueOf(srch[1]);
			res += "," + Double.valueOf(srch[2]) + ")";
		}
		String query = "DELETE FROM `" + new Configurations().tableprefix + "loggings` WHERE `savestring` LIKE '%" + res +"%'";
		try {
			new StorageManager().getDatabase().query(query);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
