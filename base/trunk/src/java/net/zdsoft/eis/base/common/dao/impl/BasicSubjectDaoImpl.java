package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.dao.BasicSubjectDao;
import net.zdsoft.keel.dao.BasicDAO;
import net.zdsoft.keel.dao.MapRowMapper;

public class BasicSubjectDaoImpl extends BasicDAO implements BasicSubjectDao {
	
	@Override
	public Map<String, String> getSubjectMap(String unitId, int isUsing, String likeName) {
		String sql="SELECT id,subject_name FROM eduadm_subject WHERE unit_id = ? ";
		if(isUsing>0){
			sql+=" and is_using ="+ isUsing;
		}
		if(StringUtils.isNotBlank(likeName)){
			sql+=" and subject_name like '"+ likeName+"%'";
		}
			sql+=" ORDER BY subject_type,order_id ";
		return this.queryForMap(sql, new Object[]{unitId}, new MapRowMapper<String,String>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("id");
			}

			@Override
			public String mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("subject_name");
			}
			
		});
	}

	@Override
	public Map<String, String> getSubjectMap(String[] subids) {
		String sql="SELECT id,subject_name FROM eduadm_subject WHERE id in ";
		return this.queryForInSQL(sql, null, subids,new MapRowMapper<String,String>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("id");
			}

			@Override
			public String mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("subject_name");
			}
			
		}," ORDER BY subject_type,order_id ");
	}

	@Override
	public Map<String, String> getSubjectMapNo(String[] subids) {
		String sql="SELECT id,subject_code FROM eduadm_subject WHERE id in ";
		return this.queryForInSQL(sql, null, subids,new MapRowMapper<String,String>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("id");
			}

			@Override
			public String mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("subject_code");
			}
			
		}," ORDER BY subject_type,order_id ");
	}



}
