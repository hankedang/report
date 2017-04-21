package com.centrixlink.xreport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centrixlink.sso.bean.User;
import com.centrixlink.xreport.dao.PageDao;
import com.centrixlink.xreport.domain.user.EntryPage;

@Service
public class PageService {

	@Autowired
	private PageDao pageDao;

	public EntryPage getEntrypage(User user) {
		return pageDao.getEntrypage(user);
	}
	
}
