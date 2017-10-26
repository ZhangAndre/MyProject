package com.muke.dao.impl;

import com.muke.dao.ICountDao;
import com.muke.util.DBUtil;

public class CountDaoImpl implements ICountDao {

	DBUtil dbutil = new DBUtil();
	
	@Override
	public int updateAccessCount(int msgid) {
		String sql = "update count set accessCount=accessCount+1 where msgid=?";
		Object[] params = {msgid};
		
		int res = -1;
		try {
			res = dbutil.execute(sql, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
}
