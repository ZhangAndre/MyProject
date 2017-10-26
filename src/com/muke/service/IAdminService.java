package com.muke.service;

import com.muke.pojo.Admin;

public interface IAdminService {
	Admin login(String name,String pwd) ;


	int update(String name,String pwd);
}
