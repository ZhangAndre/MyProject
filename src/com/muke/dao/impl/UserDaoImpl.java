package com.muke.dao.impl;

import com.muke.dao.IUserDao;
import com.muke.pojo.User;
import com.muke.util.DBUtil;
import com.muke.util.Page;

public class UserDaoImpl implements IUserDao {
	DBUtil dbutil = new DBUtil();
	
	@Override
	public User query(String username, String pw) {
		String sql = "SELECT * FROM user WHERE username = ? and password = ?";
		Object[] params = {username, pw};
		
		User user = null;

		try {
			user = (User) dbutil.getObject(User.class, sql, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}
	@Override
	public User query(String username) {
		String sql = "SELECT * FROM user WHERE username = ?";
		Object[] params = {username};
		
		User user = null;

		try {
			user = (User) dbutil.getObject(User.class, sql, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}

	@Override
	public int add(User user) {
		String sql = "INSERT INTO user (username, password, realname, sex, hobbys, birthday, city, email, qq) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		Object[] params = {user.getUsername(), user.getPassword(), user.getRealname(), user.getSex(),
				user.getHobbys(), user.getBirthday(), user.getCity(), user.getEmail(), user.getQq()};
		
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
	public int update(User user) {
		String sql = "UPDATE user SET "
				+ "password = ?, realname = ?, sex = ?, "
				+ "hobbys = ?, birthday = ?, city = ?, email = ?, qq = ? , photo=?"
				+ "WHERE userid = ?";
		Object[] params = {user.getPassword(), user.getRealname(), user.getSex(),
				user.getHobbys(), user.getBirthday(), user.getCity(), user.getEmail(), user.getQq(), user.getPhoto(),
				user.getUserid()};
		
		int res = 0;
		
		try {
			res = dbutil.execute(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public Page queryByName(String username, Page page) {
		String sql = "SELECT * FROM user WHERE username like ? ORDER BY createtime DESC";
		Object[] params = {"%"+username+"%"};
		
		Page resPage = null;
		
		try {
			resPage = dbutil.getQueryPage(User.class, sql, params, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resPage;
	}

	@Override
	public int updateState(int userid, int state) {
		String sql = "UPDATE user SET "
				+ "state = ? "
				+ "WHERE userid = ?";
		Object[] params = {state, userid};
		
		int res = 0;
		
		try {
			res = dbutil.execute(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}
