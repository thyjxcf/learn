package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import net.zdsoft.eis.base.common.entity.SchoolDistrict;
import net.zdsoft.eis.base.data.dao.BaseSchoolDistrictDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;

/**
 * @author yanb
 * 
 */
public class BaseSchoolDistrictDaoImpl extends BaseDao<SchoolDistrict>
		implements BaseSchoolDistrictDao {

	private static final String SQL_INSERT_JWSCHDISTRICT = "INSERT INTO jw_basic_school_district(schdistrictid,"
			+ "schdistri_code,schdistri_name,eduid,schdistri_region,remark,isdeleted,updatestamp) VALUES(?,?,?,?,?,?,?,?)";

	private static final String SQL_DELETE_JWSCHDISTRICT_BY_IDS = "UPDATE jw_basic_school_district SET isdeleted = '1',updatestamp=? WHERE schdistrictid IN ";

	private static final String SQL_UPDATE_JWSCHDISTRICT = "UPDATE jw_basic_school_district SET schdistri_code=?,"
			+ "schdistri_name=?,eduid=?,schdistri_region=?,remark=?,isdeleted=?,updatestamp=? WHERE schdistrictid=?";

	private static final String SQL_EXISTS_JWSCHDISTRICT_CODE_BY_EDUID_NAME = "SELECT COUNT(*) FROM jw_basic_school_district "
			+ "WHERE eduid=? AND schdistri_name=? AND isdeleted='0'";

	private static final String SQL_FIND_JWSCHDISTRICTS_BY_IDS = "SELECT * FROM jw_basic_school_district WHERE isdeleted='0' AND schdistrictid IN";

	private static final String SQL_FIND_JWSCHDISTRICT_CODE_BY_EDUID = "SELECT schdistri_code FROM jw_basic_school_district "
			+ "WHERE eduid=? AND isdeleted='0'";

	@Override
	public SchoolDistrict setField(ResultSet rs) throws SQLException {
		SchoolDistrict schoolDistrict = new SchoolDistrict();
		schoolDistrict.setId(rs.getString("schdistrictid"));
		schoolDistrict.setCode(rs.getString("schdistri_code"));
		schoolDistrict.setName(rs.getString("schdistri_name"));
		schoolDistrict.setEduid(rs.getString("eduid"));
		schoolDistrict.setRegion(rs.getString("schdistri_region"));
		schoolDistrict.setRemark(rs.getString("remark"));
		schoolDistrict.setIsdeleted(rs.getBoolean("isdeleted"));
		schoolDistrict.setUpdatestamp(rs.getLong("updatestamp"));
		return schoolDistrict;
	}

	public void insertSchoolDistrict(SchoolDistrict schoolDistrict) {
		schoolDistrict.setId(getGUID());
		schoolDistrict.setUpdatestamp(System.currentTimeMillis());
		schoolDistrict.setIsdeleted(false);
		update(SQL_INSERT_JWSCHDISTRICT, new Object[] { schoolDistrict.getId(),
				schoolDistrict.getCode(), schoolDistrict.getName(),
				schoolDistrict.getEduid(), schoolDistrict.getRegion(),
				schoolDistrict.getRemark(), schoolDistrict.getIsdeleted(),
				schoolDistrict.getUpdatestamp() }, new int[] { Types.CHAR,
				Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.VARCHAR,
				Types.VARCHAR, Types.CHAR, Types.INTEGER });
	}

	public void deleteSchoolDistrict(String[] districtIds) {
		updateForInSQL(SQL_DELETE_JWSCHDISTRICT_BY_IDS, new Object[] { System
				.currentTimeMillis() }, districtIds);
	}

	public void updateSchoolDistrict(SchoolDistrict schoolDistrict) {
		schoolDistrict.setIsdeleted(false);
		schoolDistrict.setUpdatestamp(System.currentTimeMillis());
		update(SQL_UPDATE_JWSCHDISTRICT, new Object[] {
				schoolDistrict.getCode(), schoolDistrict.getName(),
				schoolDistrict.getEduid(), schoolDistrict.getRegion(),
				schoolDistrict.getRemark(), schoolDistrict.getIsdeleted(),
				schoolDistrict.getUpdatestamp(), schoolDistrict.getId() },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.CHAR,
						Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
						Types.BIGINT, Types.CHAR });
	}

	public boolean isExistsName(String eduId, String name, String id) {
		String sql = SQL_EXISTS_JWSCHDISTRICT_CODE_BY_EDUID_NAME;
		Object[] objs = null;
		if (null == id || "".equals(id)) {
			objs = new Object[] { eduId, name };
		} else {
			sql += " AND schdistrictid <> ? ";
			objs = new Object[] { eduId, name, id };
		}
		return queryForInt(sql, objs) > 0 ? true : false;
	}

	public List<String> getSchoolDistrictCode(String eduId) {
		return query(SQL_FIND_JWSCHDISTRICT_CODE_BY_EDUID,
				new Object[] { eduId }, new MultiRowMapper<String>() {
					public String mapRow(ResultSet rs, int arg1)
							throws SQLException {
						return rs.getString("schdistri_code");
					}

				});
	}

	public List<SchoolDistrict> getSchoolDistricts(String[] districtIds) {
		return (List<SchoolDistrict>) queryForInSQL(
				SQL_FIND_JWSCHDISTRICTS_BY_IDS, new Object[0], districtIds,
				new MultiRow());
	}
}
