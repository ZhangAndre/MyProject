package com.muke.service.impl;

import com.muke.dao.IAdminDao;
import com.muke.dao.impl.AdminDaoimpl;
import com.muke.pojo.Admin;
import com.muke.service.IAdminService;

/**
 * 
 * @Description: 管理員用戶操作服务类
 * @author:Zhao Jiaqiang
 * @time:2017年7月19日 下午1:43:36
 *
 */
public class AdminServiceImpl implements IAdminService {
	IAdminDao iad = new AdminDaoimpl();

	@Override
	public Admin login(String name, String pwd) {
		Admin ad=iad.login(name, pwd);
		return ad;
	}

	@Override
	public int update(String name,String  pwd) {
		int temp = iad.update(name,pwd);
		return temp;
	}

}
