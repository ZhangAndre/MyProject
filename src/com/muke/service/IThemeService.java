package com.muke.service;

import java.util.List;

import com.muke.pojo.Theme;
import com.muke.util.Page;

public interface IThemeService {
	int add(Theme theme);
	int delete(int theid);
	int update(Theme theme);
	List getAll();
	Page query(String key, Page page);
}
