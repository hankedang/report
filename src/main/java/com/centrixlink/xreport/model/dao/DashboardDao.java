package com.centrixlink.xreport.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.centrixlink.xreport.domain.menu.DrillMenu;
import com.centrixlink.xreport.domain.model.Dashboard;

/**
 * 分析模型DAO
 */
@Repository
public class DashboardDao {

	@Autowired
	private JdbcTemplate modelTemplate;

	/**
	 * Get Dash Board By ID
	 */
	public Dashboard getBoard(final String boardId) {
		Dashboard dashboard = null;
		try {
			String sql = "SELECT dash.name,dash.board,dash.datespan,dash.ex_value,meta.name, dash.cycle"
					+ " FROM xv_dashboard dash,xv_metadata_column meta"
					+ " WHERE dash.id=? AND meta.id=dash.dim_id";
			
			dashboard = modelTemplate.queryForObject(sql, new Object[] { boardId }, new RowMapper<Dashboard>() {
				@Override
				public Dashboard mapRow(ResultSet rs, int idx) throws SQLException {
					Dashboard dashboard = new Dashboard(boardId);
					dashboard.setName(rs.getString(1));
					dashboard.setModelURL(rs.getString(2));
					dashboard.setDateType(rs.getString(3));
					dashboard.setExValue(rs.getString(4));
					dashboard.setDimName(rs.getString(5));
					dashboard.setTableType(rs.getInt(6));
					return dashboard;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (dashboard == null) {
			dashboard = new Dashboard(boardId);
		}
		
		List<DrillMenu> drillMenus = getDrillMenus(boardId);
		dashboard.setDrillMenus(drillMenus);
		
		return dashboard;
	}

	public List<DrillMenu> getDrillMenus(String boardId) {
		
		List<DrillMenu> menus = null;
		
		try {
			String sql = "SELECT drill_id,drill_name,drill_title FROM xv_dashboard_drill WHERE board_id=?";
			
			menus = modelTemplate.query(sql, new Object[]{boardId}, new RowMapper<DrillMenu>(){
				@Override
				public DrillMenu mapRow(ResultSet rs, int idx) throws SQLException {
					DrillMenu menu = new DrillMenu();
					menu.setUrl(rs.getString(1));
					menu.setName(rs.getString(2));
					menu.setTitle(rs.getString(3));
					return menu;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null == menus) {
			menus = new ArrayList<>(0);
		}
		return menus;
	}
}
