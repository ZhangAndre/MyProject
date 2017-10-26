package com.muke.dao.impl;

import java.util.List;

import com.muke.dao.IThemeDao;
import com.muke.pojo.Theme;
import com.muke.util.DBUtil;
import com.muke.util.Page;

public class ThemeDaoImpl implements IThemeDao {

	DBUtil dbutil = new DBUtil();
	
	@Override
	public int add(Theme theme) {
		String sql = "INSERT INTO theme (thename) VALUES (?)";
		Object[] params = {theme.getThename()};
		
		int res = 0;
		
		try {
			res = dbutil.execute(sql, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public int delete(int theid) {
		String sql = "DELETE FROM theme WHERE theid = ?";
		Object[] params = {theid};
		
		int res = 0;
		
		try {
			res = dbutil.execute(sql, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public int update(Theme theme) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List getAll() {
		String sql = "SELECT * FROM theme";
		List list = null;
		
		try {
			list = dbutil.getQueryList(Theme.class, sql, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public Page query(String key, Page page) {
		String sql = "SELECT * FROM theme WHERE thename like ?";
		Object[] params = {"%"+key+"%"};
		
		Page resPage = null;
		
		resPage = dbutil.getQueryPage(Theme.class, sql, params, page);
		
		return resPage;
	}
}
