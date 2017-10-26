package com.muke.dao;

import com.muke.pojo.User;
import com.muke.util.Page;

public interface IUserDao {
	int add(User user);
	int update(User user);
	User query(String username, String pw);
	User query(String username);
	Page queryByName(String username, Page page);
	int updateState(int userid, int state);
}
