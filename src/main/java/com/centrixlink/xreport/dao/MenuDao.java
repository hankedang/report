package com.centrixlink.xreport.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.centrixlink.sso.bean.User;
import com.centrixlink.xreport.domain.menu.Menu;
/**
 * 菜单DAO
 */
@Repository
public class MenuDao {
	
	@Autowired
	private JdbcTemplate modelTemplate;
	
	/**
	 * 根据用户id查询该用户对应的菜单项
	 */
	public List<Menu> getMenuList(User user) {
		
		String sql = "select name,url,subject from xv_menu where valid=1 order by subject,level,code";
		
		System.out.println("Menu SQL:[" + sql + "]");
		
		final List<Menu> menuList = new ArrayList<Menu>();
		
		modelTemplate.query(sql, new RowCallbackHandler() {
			Menu parentMenu = null;
			int parentSign = -1;
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				
				int currSign = rs.getInt("subject");
				
				Menu menu = new Menu();
				menu.setName(rs.getString("name"));
				menu.setUrl(rs.getString("url"));
				
				if (currSign != parentSign) { // New Parent Menu
					menuList.add(menu);
					parentMenu = menu;
					parentSign = currSign;
				} else {
					parentMenu.addSubMenu(menu);
				}
			}
		});
		return menuList;
	}
}
