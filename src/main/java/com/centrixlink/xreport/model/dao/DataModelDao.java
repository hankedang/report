package com.centrixlink.xreport.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.centrixlink.xreport.domain.model.Column;
import com.centrixlink.xreport.domain.model.DataModel;

/**
 * 数据模型DAO
 * 
 */
@Repository
public class DataModelDao {

	@Autowired
	private JdbcTemplate modelTemplate;

	/**
	 * 根据Dash Board Id 获取 Data Model
	 */
	public DataModel getModel(String dashId) {

		String sql = "SELECT dm.id,col.name,board.id,board.name"
				+ " FROM xv_dashboard_rel dmr,xv_datamodel dm,xv_metadata_column col,xv_dashboard board"
				+ " WHERE dmr.dashboard_id=? AND dm.id=dmr.data_model_id AND col.id=dm.ord_id AND dm.dashboard_id=board.id";

		DataModel dataModel = null;
		try {
			dataModel = modelTemplate.queryForObject(sql, new Object[] { dashId }, new RowMapper<DataModel>() {

				@Override
				public DataModel mapRow(ResultSet rs, int idx) throws SQLException {

					DataModel dataModel = new DataModel();
					dataModel.setId(rs.getString(1));
					dataModel.setOrderName(rs.getString(2));

					return dataModel;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Get Dim Column And Indexes Column
		if (dataModel != null) {
			List<Column> idxList = getColumnList(dataModel.getId());
			for (Column column : idxList) {
				dataModel.addColumn(column);
			}
		}
		return dataModel;
	}

	/**
	 * Get Title Of Data Model
	 */
	public List<Column> getColumnList(String dashboardId) {

		String sql = "SELECT rel.flag,rel.level,rel.code,col.name,col.label,col.dim,col.datatype,col.func,col.paras"
				+ " FROM xv_dashboard_column rel,xv_metadata_column col"
				+ " WHERE rel.theme_id=? AND rel.col_id=col.id";

		System.out.println("Dim sql:[" + sql + "]");

		List<Column> idxList = null;
		try {
			idxList = modelTemplate.query(sql, new Object[] { dashboardId }, new RowMapper<Column>() {

				@Override
				public Column mapRow(ResultSet rs, int idx) throws SQLException {
					Column column = new Column();
					// display or not
					int flag = rs.getInt(1);
					if (flag == 1) {
						column.setFlag(true);
					}
					column.setLevel(rs.getInt(2));
					column.setCode(rs.getInt(3));
					String name = rs.getString(4);
					column.setName(name);
					column.setTitle(rs.getString(5));
					column.setDim(rs.getInt(6));
					column.setDateType(rs.getString(7));
					column.setFormula(rs.getString(8));
					column.setParas(rs.getString(9));
					return column;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idxList;
	}
}