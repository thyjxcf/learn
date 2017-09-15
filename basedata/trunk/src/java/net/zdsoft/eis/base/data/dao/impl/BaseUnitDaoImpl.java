/* 
 * @(#)BaseUnitDaoImpl.java    Created on Nov 18, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.data.dao.BaseUnitDao;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 18, 2009 3:33:03 PM $
 */
public class BaseUnitDaoImpl extends BaseDao<BaseUnit> implements
		BaseUnitDao {
	// ===================================== 维护==========================
	private static final String SQL_INSERT_UNIT = "INSERT INTO base_unit(id,creation_time,union_code,unit_name,"
			+ "poll_code,unit_class,unit_type,unit_state,region_level,use_type,authorized,"
			+ "unit_use_type,region_code,serial_number,is_teacher_sms,is_guestbook_sms,"
			+ "balance,fee_type,unit_partition_num,second_level_domain,parent_id,display_order,postalcode,email,fax,link_man,link_phone,"
			+ "mobile_phone,homepage,address,modify_time,is_deleted,limit_teacher,is_sms_free,event_source) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SQL_UPDATE_UNIT = "UPDATE base_unit SET union_code=?,unit_name=?,"
			+ "poll_code=?,unit_class=?,unit_type=?,unit_state=?,region_level=?,use_type=?,authorized=?,"
			+ "unit_use_type=?,region_code=?,serial_number=?,is_teacher_sms=?,is_guestbook_sms=?,"
			+ "balance=?,fee_type=?,unit_partition_num=?,second_level_domain=?,parent_id=?,display_order=?,"
			+ "postalcode=?,email=?,fax=?,link_man=?,link_phone=?,mobile_phone=?,homepage=?,"
			+ "address=?,modify_time=?,is_deleted=?,limit_teacher=?,is_sms_free=?,event_source=? WHERE id=?";

	private static final String UPDATE_BLANCE = "UPDATE base_unit SET balance=?,event_source=?,modify_time=? WHERE id=?";

	private static final String UPDATE_STATE = "UPDATE base_unit SET unit_state=?,event_source =?,modify_time=? WHERE id IN ";

	private static final String DELETE_UNIT = " DELETE FROM base_unit WHERE id IN ";

	// ===========================查询=====================================
	private static final String MAX_UNION_CODE_CLASS = "SELECT Max(union_code) FROM base_unit WHERE parent_id=? AND unit_class=? AND length(union_code)=? AND is_deleted=0";
	
		private static final String MAX_UNION_CODE_CLASS2 = "SELECT Max(substr(union_code,?)) FROM base_unit WHERE parent_id=? AND unit_class=? AND length(union_code)=? AND is_deleted=0";

	private static final String MAX_UNION_CODE_EXCEPT_TYPE = "SELECT Max(union_code) FROM base_unit WHERE parent_id=? AND unit_class=? AND unit_type<>? AND is_deleted=0";

	private static final String COUNT_ALL_UNIT_BY_AUTHORIZED = "SELECT COUNT(name) FROM base_unit WHERE authorized =? AND is_deleted=0";

	private static final String COUNT_ALL_UNIT= "SELECT COUNT(unit_name) FROM base_unit WHERE is_deleted=0";

	private static final String COUNT_UNION_CODE = "SELECT COUNT(union_code) FROM base_unit WHERE union_code =? AND is_deleted=0";

	private static final String COUNT_BY_NAME = "SELECT COUNT(unit_name) FROM base_unit WHERE unit_name=? AND is_deleted=0 ";

	private static final String COUNT_UNIT_CODES = "SELECT union_code,COUNT(union_code) as cnt FROM base_unit WHERE is_deleted=0 AND union_code IN ";

	private static final String FIND_UNDERLING_UNITS_NAME = "SELECT id,unit_name,union_code,unit_class,creation_time,unit_state "
			+ " FROM base_unit WHERE union_code like ? AND unit_name like ? AND is_deleted=0 ORDER BY display_order ";

	private static final String FING_UNIT_BY_ID = "SELECT * FROM base_unit WHERE id=?";
	private static final String FIND_UNIT_BY_CODE = "SELECT * FROM base_unit WHERE union_code=? AND is_deleted=0";
	private static final String FIND_UNIT_BY_IDS = "SELECT * FROM base_unit WHERE is_deleted=0 AND id in ";
	private static final String FIND_UNITS = "SELECT * FROM base_unit WHERE is_deleted=0 ORDER BY display_order ";

	private static final String FIND_UNITS_BY_CODE_CLASS_UNITTYPE = "SELECT * FROM base_unit WHERE union_code like ? AND unit_class=? AND unit_type<>? AND is_deleted=0 ORDER BY display_order";

	private static final String FIND_UNITS_BY_CODE = "SELECT * FROM base_unit WHERE union_code like ? AND is_deleted=0 ORDER BY display_order";


	/*取顶级单位的union_code,serial_number*/
	private static final String FIND_MIN_UNION_CODE="select min(to_number(union_code)) from base_unit where unit_type=1";
	
	private static final String FIND_MAX_SERIAL_NUMBER="select max(to_number(serial_number)) from base_unit";
	
	
	public BaseUnit setField(ResultSet rs) throws SQLException {
		BaseUnit unit = new BaseUnit();
		unit.setId(rs.getString("id"));
		unit.setUnionid(rs.getString("union_code"));
		unit.setName(rs.getString("unit_name"));
		unit.setRegcode(rs.getString("poll_code"));
		unit.setUnitclass(rs.getInt("unit_class"));
		unit.setUnittype(rs.getInt("unit_type"));
		unit.setMark(rs.getInt("unit_state"));
		unit.setRegionlevel(rs.getInt("region_level"));
		unit.setUsetype(rs.getInt("use_type"));
		unit.setAuthorized(rs.getInt("authorized"));
		unit.setUnitusetype(rs.getString("unit_use_type"));
		unit.setRegion(rs.getString("region_code"));
		unit.setEtohSchoolId(rs.getString("serial_number"));
		unit.setTeacherEnableSms(rs.getInt("is_teacher_sms"));
		unit.setGuestbookSms(rs.getInt("is_guestbook_sms"));
		unit.setBalance(rs.getLong("balance"));
		unit.setFeeType(rs.getInt("fee_type"));
		unit.setUnitPartitionNum(rs.getLong("unit_partition_num"));
		unit.setSld(rs.getString("second_level_domain"));
		unit.setParentid(rs.getString("parent_id"));
		unit.setOrderid(rs.getLong("display_order"));
		unit.setPostalcode(rs.getString("postalcode"));
		unit.setEmail(rs.getString("email"));
		unit.setFax(rs.getString("fax"));
		unit.setLinkMan(rs.getString("link_man"));
		unit.setLinkPhone(rs.getString("link_phone"));
		unit.setMobilePhone(rs.getString("mobile_phone"));
		unit.setHomepage(rs.getString("homepage"));
		unit.setAddress(rs.getString("address"));
		unit.setCreationTime(rs.getTimestamp("creation_time"));
		unit.setModifyTime(rs.getTimestamp("modify_time"));
		unit.setLimitTeacher(rs.getInt("limit_teacher"));
		unit.setSmsFree(rs.getInt("is_sms_free"));
		return unit;
	}

	/**
	 * @param unit
	 * @param type
	 *            true表示新增的赋值
	 * @return
	 */
	private Object[] getParams(BaseUnit unit, boolean type) {
		List<Object> parms = new ArrayList<Object>();
		if (type) {
			parms.add(unit.getId());
			parms.add(unit.getCreationTime());
		}
		parms.add(unit.getUnionid());
		parms.add(unit.getName());
		parms.add(unit.getRegcode());
		parms.add(unit.getUnitclass());
		parms.add(unit.getUnittype());
		parms.add(unit.getMark());
		parms.add(unit.getRegionlevel());
		parms.add(unit.getUsetype());
		parms.add(unit.getAuthorized());
		parms.add(unit.getUnitusetype());
		parms.add(unit.getRegion());
		parms.add(unit.getEtohSchoolId());
		parms.add(unit.getTeacherEnableSms());
		parms.add(unit.getGuestbookSms());
		parms.add(unit.getBalance());
		parms.add(unit.getFeeType());
		parms.add(unit.getUnitPartitionNum());
		parms.add(unit.getSld());
		parms.add(unit.getParentid());
		parms.add(unit.getOrderid());
		parms.add(unit.getPostalcode());
		parms.add(unit.getEmail());
		parms.add(unit.getFax());
		parms.add(unit.getLinkMan());
		parms.add(unit.getLinkPhone());
		parms.add(unit.getMobilePhone());
		parms.add(unit.getHomepage());
		parms.add(unit.getAddress());
		parms.add(unit.getModifyTime());
		parms.add(unit.getIsdeleted());
		parms.add(unit.getLimitTeacher());
		parms.add(unit.getSmsFree());
		parms.add(unit.getEventSourceValue());
		if (!type) {
			parms.add(unit.getId());
		}
		return parms.toArray(new Object[0]);
	}

	public void insertUnit(BaseUnit unit) {
		if (StringUtils.isBlank(unit.getId()))
			unit.setId(getGUID());
		unit.setCreationTime(new Date());
		unit.setModifyTime(new Date());
		unit.setIsdeleted(false);
		unit.setMark(BaseUnit.UNIT_MARK_NORAML);
		unit.setUnitPartitionNum(getIncrementerKey());
		if(StringUtils.isEmpty(unit.getEtohSchoolId()))
			unit.setEtohSchoolId(genSerialNumber(unit.getRegion()));
		update(SQL_INSERT_UNIT, getParams(unit, true));
	}

	
	private String genSerialNumber(String unionCode){
		String max_serial_number,min_union_code,new_serial_number;
		
		max_serial_number=this.queryForString(FIND_MAX_SERIAL_NUMBER, null);
		if(StringUtils.isNotEmpty(max_serial_number)){
			new_serial_number="0000000000"+String.valueOf(Long.valueOf(max_serial_number)+1);
			new_serial_number=new_serial_number.substring(new_serial_number.length()-10);
		}else{
			min_union_code=this.queryForString(FIND_MIN_UNION_CODE, null);
			if(StringUtils.isEmpty(min_union_code))
				min_union_code=unionCode;
			new_serial_number=min_union_code+"0000000001".substring(min_union_code.length());
		}
		return new_serial_number;
	}
		
	public void updateUnit(BaseUnit unit) {
		unit.setModifyTime(new Date());
		unit.setIsdeleted(false);
		update(SQL_UPDATE_UNIT, getParams(unit, false));
	}

	public void updateUnitBalance(String unitId, int balance) {
		update(UPDATE_BLANCE, new Object[] { Integer.valueOf(balance),EventSourceType.LOCAL.getValue(),
				new Date(), unitId });
	}

	public void updateUnitMark(String[] unitIds, Integer mark) {
		updateForInSQL(UPDATE_STATE, new Object[] { mark,EventSourceType.LOCAL.getValue(), new Date() }, unitIds);
	}

	public void deleteUnit(String[] arrayIds, EventSourceType eventSource) {
		updateForInSQL(DELETE_UNIT, null, arrayIds);
	}

	public void excuteSql(String sqlStr, Object[] params) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sqlStr);
			for (int i = 0; i < params.length; i++) {
				Object param = params[i];
				ps.setObject(i + 1, param);
			}
			ps.execute();

		} catch (SQLException e) {
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
			} catch (Exception e) {
			}
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception en) {
			}

		}
	}

	public String getAvaUnionId(String parentid, int unitclass, int length) {
		return queryForString(MAX_UNION_CODE_CLASS, new Object[] { parentid,
				unitclass, Long.valueOf(length) });
	}
	
	public String getAvaUnionId(String parentid, String parentUnionId, int unitclass, int length) {
		return queryForString(MAX_UNION_CODE_CLASS2, new Object[] { StringUtils.length(parentUnionId) + 1, parentid,
				unitclass, Long.valueOf(length) });
	}

	public String getAvaUnionIdExceptType(String parentid, int unitclass,
			int unittype) {
		return queryForString(MAX_UNION_CODE_EXCEPT_TYPE, new Object[] {
				parentid, unitclass, unittype });
	}

	public Integer getCountAllUnitByAuthorized(Integer authorized) {
		return queryForInt(COUNT_ALL_UNIT_BY_AUTHORIZED,
				new Object[] { authorized });
	}

    public int getAllUnitCount() {
        return queryForInt(COUNT_ALL_UNIT);
    }

	public Integer getCountUnionId(String unionId) {
		return queryForInt(COUNT_UNION_CODE, new Object[] { unionId });
	}

	public Integer getCountUnitByName(String unitName) {
		return queryForInt(COUNT_BY_NAME, new Object[] { unitName });
	}

	public Map<String, Integer> getCountsByUnionIds(String[] unionIds) {
		return queryForInSQL(COUNT_UNIT_CODES, null, unionIds,
				new MapRowMapper<String, Integer>() {
					public String mapRowKey(ResultSet rs, int arg1)
							throws SQLException {
						return rs.getString("union_code");
					}

					public Integer mapRowValue(ResultSet rs, int arg1)
							throws SQLException {
						return rs.getInt("cnt");
					}
				}, "GROUP BY union_code ");

	}

	public List<BaseUnit> getUnderlingUnits(String unionid, String unitName,
			Pagination page) {
		Assert.notNull(page, "page参数不能为null");
		if (null == unitName || "".equals(unitName)) {
			unitName = "%";
		} else {
			unitName = "%" + unitName + "%";
		}
		if (null == page) {

		}
		return query(FIND_UNDERLING_UNITS_NAME, new Object[] { unionid + "%",
				unitName }, new MultiRowMapper<BaseUnit>() {
			public BaseUnit mapRow(ResultSet rs, int arg1) throws SQLException {
				BaseUnit unit = new BaseUnit();
				unit.setId(rs.getString("id"));
				unit.setUnionid(rs.getString("union_code"));
				unit.setName(rs.getString("unit_name"));
				unit.setUnitclass(rs.getInt("unit_class"));
				unit.setCreationTime(rs.getTimestamp("creation_time"));
				unit.setMark(rs.getInt("unit_state"));
				return unit;
			}

		}, page);
	}

	public BaseUnit getBaseUnit(String unitId) {
		return query(FING_UNIT_BY_ID, new Object[] { unitId }, new SingleRow());
	}

	public BaseUnit getBaseUnitByUnionId(String unionId) {
		return query(FIND_UNIT_BY_CODE, unionId, new SingleRow());
	}

	public Map<String, BaseUnit> getBaseUnitMap(String[] unitIds) {
		return queryForInSQL(FIND_UNIT_BY_IDS, null, unitIds, new MapRow());
	}

	public List<BaseUnit> getBaseUnitsByUnionCodeUnitType(String unionId,
			int unitClass, int unitType) {
		return query(FIND_UNITS_BY_CODE_CLASS_UNITTYPE, new Object[] {
				unionId + "%", unitClass, unitType }, new MultiRow());
	}

	public List<BaseUnit> getUnitsByUnionCode(String unionId) {
		return query(FIND_UNITS_BY_CODE, new Object[] { unionId + "%" },
				new MultiRow());
	}

	public List<BaseUnit> getBaseUnits(String[] unitIds) {
		return queryForInSQL(FIND_UNIT_BY_IDS, null, unitIds, new MultiRow());
	}

	public List<BaseUnit> getBaseUnits() {
		return query(FIND_UNITS, new MultiRow());
	}
	
	/**
	 * 获取单位Map key为单位名称
	 * @param unitNames
	 * @return
	 */
	public Map<String, BaseUnit> getUnitMapByUnitName(String[] unitNames){
		String sql = "select * from base_unit where unit_name in";
		return queryForInSQL(sql, null, unitNames, new MapRowMapper<String, BaseUnit>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("unit_name");
			}

			@Override
			public BaseUnit mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
			
		});
	}
	
	public void updateRunSchType(String unitId, String runSchType){
		String sql = "UPDATE base_unit SET run_school_type=? WHERE id=?";
		update(sql, new Object[] { runSchType, unitId });
	}
}
