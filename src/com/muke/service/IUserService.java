package com.muke.service;

import com.muke.pojo.User;
import com.muke.util.Page;

public interface IUserService {
	
	int userRegister(User user);
	
	User userLogin(String username, String pw);
	
	int update(User user);
	
	Page searchByName(String username, Page page);

	int deleteUser(int userid);
	
	int restoreUser(int userid);
}
