package com.lightniinja.wbi.searchfuncs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lightniinja.wbi.ChangeQueue;
import com.lightniinja.wbi.Configurations;
import com.lightniinja.wbi.StorageManager;

public class SearchAll {
	public ArrayList<String> searchAll(int page) {
		ChangeQueue.queue.doUpdate2();
		ArrayList<String> temp = new ArrayList<String>();
		String query = "SELECT * FROM `" + new Configurations().tableprefix + "loggings` "; //LIMIT 0, 30"; default
		int start = page * 30;
		int finish = 30;
		query += "LIMIT " + start + ", " + finish;
		ResultSet s;
		try {
			s = new StorageManager().getDatabase().query(query);
			while(s.next()) {
				if(s.getString("savestring")!= null)
					temp.add(s.getString("savestring"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}
}
