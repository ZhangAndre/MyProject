package com.muke.dao.impl;

import com.muke.dao.IAdminDao;
import com.muke.pojo.Admin;
import com.muke.util.DBUtil;
/**
 * 
 * @Description:管理员用户数据访问接口
 * @author:Zhao JiaQiang
 * @time:2017年7月18日 下午5:21:41
 *
 */
public class AdminDaoimpl implements IAdminDao {
	DBUtil dbutil = new DBUtil();

	@Override
	public Admin login(String name, String pwd) {
		String sql = "SELECT * FROM admin where name=? and pwd=?";
		Admin ad = null;
		try {
			ad = (Admin) dbutil.getObject(Admin.class, sql, new Object[] { name, pwd });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ad;
	}

	public int update(String name,String pwd) {
		String sql = "";
		sql = "update admin set name=?, pwd=? where name=?";
		int temp = 0;
		Admin u1 = this.getAdminByName(name);
		if (u1 != null) {
			try {
				temp = dbutil.execute(sql, new Object[] { name,pwd,name });
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			temp = -1;
		}
		return temp;
	}

	public Admin getAdminByName(String name) {
		Admin ad = new Admin();
		String sql = "SELECT * FROM admin t where name=?";
		try {
			ad = (Admin) dbutil.getObject(Admin.class, sql, new Object[] { name });
			;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return ad;
	}
}
