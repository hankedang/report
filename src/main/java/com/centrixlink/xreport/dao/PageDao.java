package com.centrixlink.xreport.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.centrixlink.sso.bean.User;
import com.centrixlink.xreport.domain.user.EntryPage;

@Repository
public class PageDao {

	@Autowired
	private JdbcTemplate modelTemplate;

	public EntryPage getEntrypage(User user) {

		EntryPage page = null;

		try {
			String sql = "SELECT title,url FROM xv_user_page WHERE uid='" + user.getUserId() + "'";
			page = modelTemplate.queryForObject(sql, new RowMapper<EntryPage>() {
				@Override
				public EntryPage mapRow(ResultSet rs, int arg1) throws SQLException {
					EntryPage page = new EntryPage();

					page.setTitle(rs.getString(1));
					page.setUrl(rs.getString(2));

					return page;
				}
			});
		} catch (Exception e) {
			page = new EntryPage("welcome", "/frame/hello.jsp");
		}

		return page;
	}
}
