package com.lightniinja.wbi.searchfuncs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lightniinja.wbi.ChangeQueue;
import com.lightniinja.wbi.Configurations;
import com.lightniinja.wbi.StorageManager;

public class SearchTime {
	public ArrayList<String> searchAll(int page, String srch2) {
		String srch = srch2;
		ChangeQueue.queue.doUpdate2();
		ArrayList<String> temp = new ArrayList<String>();
		String query = "SELECT * FROM `" + new Configurations().tableprefix + "loggings` WHERE `savestring` LIKE '%" + srch +"%' "; //LIMIT 0, 30"; default
		
		int start = page * 30;
		int finish = 30;
		query += "LIMIT " + start + ", " + finish;
		System.out.print(query);
		ResultSet s;
		try {
			s = new StorageManager().getDatabase().query(query);
			while(s.next()) {
				temp.add(s.getString("savestring"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}
}
