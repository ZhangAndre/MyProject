package com.muke.service.impl;

import java.util.List;

import com.muke.dao.IThemeDao;
import com.muke.dao.impl.ThemeDaoImpl;
import com.muke.pojo.Theme;
import com.muke.service.IThemeService;
import com.muke.util.Page;

public class ThemeServiceImpl implements IThemeService {

	IThemeDao iThemeDao = new ThemeDaoImpl();
	
	@Override
	public int add(Theme theme) {
		// TODO Auto-generated method stub
		return iThemeDao.add(theme);
	}

	@Override
	public int delete(int theid) {
		// TODO Auto-generated method stub
		return iThemeDao.delete(theid);
	}

	@Override
	public int update(Theme theme) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List getAll() {
		return iThemeDao.getAll();
	}

	@Override
	public Page query(String key, Page page) {
		// TODO Auto-generated method stub
		return iThemeDao.query(key, page);
	}

}
