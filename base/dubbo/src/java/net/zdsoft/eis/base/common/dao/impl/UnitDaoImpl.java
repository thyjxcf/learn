package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.basedata.remote.service.UnitRemoteService;
import net.zdsoft.eis.base.common.dao.UnitDao;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.util.EntityUtils;
import net.zdsoft.eis.base.util.SUtils;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author chendm
 * @version $Revision$, $Date$
 */

public class UnitDaoImpl extends BaseDao<Unit> implements UnitDao {
	
	@Autowired
	private UnitRemoteService unitRemoteService;
	
	private static final String FIND_UNIT_PREFIX = "SELECT * FROM base_unit ";

	// ========================= 返回单个对象的查询=====================================
	private static final String FING_UNIT_BY_ID = FIND_UNIT_PREFIX
			+ " WHERE id=?";
	private static final String FIND_TOP_UNIT = FIND_UNIT_PREFIX
			+ " WHERE parent_id=?  ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789')) ";
	private static final String FIND_UNIT_BY_NAME = FIND_UNIT_PREFIX
			+ " WHERE unit_name =? AND is_deleted=0 ";
	private static final String FIND_UNIT_BY_CODE = FIND_UNIT_PREFIX
			+ " WHERE union_code=? AND is_deleted=0";
	private static final String FIND_UNIT_BY_SERIALNUM = FIND_UNIT_PREFIX
			+ " WHERE serial_number=?";

	// ===========================一般查询=====================================
	private static final String FIND_UNIT_BY_IDS = FIND_UNIT_PREFIX
			+ " WHERE is_deleted=0 AND id in ";
	private static final String FIND_UNIT_INCLUDE_DEL_BY_IDS = FIND_UNIT_PREFIX
			+ " WHERE id in ";
	private static final String FIND_UNITS = FIND_UNIT_PREFIX
			+ " WHERE is_deleted=0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789')) ";
	private static final String FIND_UNITS_BY_STATE = FIND_UNIT_PREFIX
			+ " WHERE unit_state=? AND is_deleted=0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789')) ";
	private static final String FIND_UNITS_BY_STATE_CLASS = FIND_UNIT_PREFIX
			+ " WHERE unit_state=? AND unit_class=? AND is_deleted=0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";
	private static final String FIND_UNITS_BY_STATE_USETYPE = FIND_UNIT_PREFIX
			+ " WHERE unit_state=? AND use_type=? AND is_deleted=0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";

	// ===========================直属单位查询=====================================
	private static final String FIND_UNITS_BY_PARENTID = FIND_UNIT_PREFIX
			+ " WHERE parent_id=? AND is_deleted=0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";
	private static final String FIND_UNITS_BY_PARENTID_STATE = FIND_UNIT_PREFIX
			+ " WHERE parent_id=? AND unit_state=? AND is_deleted=0";
	private static final String FIND_UNITS_BY_PARENTID_STATE_CLASS = FIND_UNIT_PREFIX
			+ " WHERE parent_id=? AND unit_state=? AND unit_class=? "
			+ " and is_deleted=0  ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";
	private static final String FIND_UNITS_BY_PARENTID_STATE_CLASS_TYPE = FIND_UNIT_PREFIX
			+ " WHERE parent_id=? AND unit_state=? AND unit_class=? AND unit_type=?"
			+ " and is_deleted=0  ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";

	// ===========================name模糊查询=====================================
	private static final String FIND_UNITS_BY_NAME_FAINTNESS = FIND_UNIT_PREFIX
			+ " WHERE unit_name like ? AND is_deleted=0  ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789')) ";
	private static final String FIND_UNITS_BY_NAME_FAINTNESS_CLASS = FIND_UNIT_PREFIX
			+ " WHERE unit_name like ? AND unit_class=? AND is_deleted=0  ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789')) ";

	// ===========================region模糊查询=====================================
	private static final String FIND_UNITS_BY_REGION_STATE = FIND_UNIT_PREFIX
			+ " WHERE region_code like ? AND unit_state>=? AND is_deleted=0";
	private static final String FIND_COUNT_BY_REGION_STATE = "SELECT count(*) FROM base_unit WHERE region_code like ? AND unit_state>=? AND is_deleted=0";

	// ===========================union_code模糊查询===============================
	private static final String FIND_UNITS_BY_CODE = FIND_UNIT_PREFIX
			+ " WHERE union_code like ? AND is_deleted=0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";
	private static final String FIND_UNITS_BY_CODE_STATE = FIND_UNIT_PREFIX
			+ " WHERE union_code like ? AND unit_state = ? AND is_deleted=0 ";
	private static final String FIND_UNITS_BY_CODE_STATE_CLASS = FIND_UNIT_PREFIX
			+ " WHERE union_code like ? AND unit_state = ? AND unit_class=? AND is_deleted=0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";
	private static final String FIND_UNITIDS_BY_CODE_STATE_CLASS = "SELECT id FROM base_unit"
			+ " WHERE union_code like ? AND unit_state = ? AND unit_class=? AND is_deleted=0";
	
	private static final String FIND_UNITS_BY_CODE_CLASS_UNITTYPE = FIND_UNIT_PREFIX
			+ " WHERE union_code like ? AND unit_class=? AND unit_type<>? AND is_deleted=0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";

	private static final String FIND_UNITS_BY_CODE_CLASS_UNITTYPE_STATE = FIND_UNIT_PREFIX
			+ " WHERE union_code like ? AND unit_class=? AND unit_state=? AND unit_type=? AND is_deleted=0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";

	private static final String FIND_UNITS_BY_CODE_CLASS_NON_UNITTYPE_STATE = FIND_UNIT_PREFIX
			+ " WHERE union_code like ? AND unit_class=? AND unit_state=? AND unit_type<>? AND is_deleted=0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";

	private static final String FIND_BY_CODE_STATE_CLASS = FIND_UNIT_PREFIX
			+ " WHERE unit_name like ? and union_code like ? AND unit_state = ? AND unit_class=? AND is_deleted=0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";

	private static final String FIND_UNDERLING_UNITS_NAME = "SELECT id,unit_name,union_code,unit_class,creation_time,unit_state,region_level "
			+ " FROM base_unit WHERE union_code like ? AND unit_name like ? AND is_deleted=0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789')) ";
	
	public Unit setField(ResultSet rs) throws SQLException {
		Unit unit = new Unit();
		unit.setId(rs.getString("id"));
		unit.setName(rs.getString("unit_name"));
		unit.setUnitclass(rs.getInt("unit_class"));
		unit.setUnittype(rs.getInt("unit_type"));
		unit.setRegionlevel(rs.getInt("region_level"));
		unit.setRegion(rs.getString("region_code"));
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
		unit.setGuestbookSms(rs.getInt("is_guestbook_sms"));
		unit.setBalance(rs.getLong("balance"));
		unit.setFeeType(rs.getInt("fee_type"));
		unit.setLimitTeacher(rs.getInt("limit_teacher"));
		unit.setSmsFree(rs.getInt("is_sms_free"));
		unit.setCreationTime(rs.getTimestamp("creation_time"));
		unit.setModifyTime(rs.getTimestamp("modify_time"));
		unit.setIsdeleted(rs.getBoolean("is_deleted"));
		unit.setUnionid(rs.getString("union_code"));
		unit.setMark(rs.getInt("unit_state"));
		unit.setUsetype(rs.getInt("use_type"));
		unit.setAuthorized(rs.getInt("authorized"));
		unit.setUnitusetype(rs.getString("unit_use_type"));
		unit.setUnitPartitionNum(rs.getLong("unit_partition_num"));
		unit.setRunschtype(rs.getString("run_school_type"));
		unit.setSchtype(rs.getString("school_type"));
		unit.setUnitEducationType(rs.getString("unit_education_type"));
		unit.setLongitude(rs.getDouble("longitude"));
		unit.setLatitude(rs.getDouble("latitude"));
		unit.setUnitHeader(rs.getString("unit_header"));
		unit.setEtohSchoolId(rs.getString("serial_number"));
		unit.setTeacherEnableSms(rs.getInt("is_teacher_sms"));
		unit.setRegcode(rs.getString("poll_code"));
		return unit;
	}

	// ===============================单个对象==========================================
	public Unit getTopEdu() {
		return Unit.dc(unitRemoteService.findTopUnit());
//		return query(FIND_TOP_UNIT, Unit.TOP_UNIT_GUID, new SingleRow());
	}

	public Unit getUnit(String unitId) {
		return Unit.dc(unitRemoteService.findById(unitId));
//		return query(FING_UNIT_BY_ID, new Object[] { unitId }, new SingleRow());
	}

	public Unit getUnitByName(String unitName) {
		 List<Unit> units =  Unit.dt(unitRemoteService.findByUnitName(unitName));
		 if(CollectionUtils.isNotEmpty(units)){
			 return units.get(0);	 
		 }else{
			 return new Unit();
		 }
		
//		return query(FIND_UNIT_BY_NAME, unitName, new SingleRow());
	}

	public Unit getUnitByUnionId(String unionId) {
		Unit unit =  Unit.dc(unitRemoteService.findByUnionId(unionId == null ? unionId : unionId+"%", -1,-1));
		return unit;
//		return query(FIND_UNIT_BY_CODE, unionId, new SingleRow());
	}

	public Unit getUnitByEtohSchoolId(String etohSchoolId) {
		Unit unit =  Unit.dc(unitRemoteService.findBy(ArrayUtils.toArray("serialNumber", "isDeleted"),ArrayUtils.toArray(etohSchoolId, "0")));
		return unit;
//		return query(FIND_UNIT_BY_SERIALNUM, etohSchoolId, new SingleRow());
	}

	public List<Unit> getUnits() {
		List<Unit> units =  Unit.dt(unitRemoteService.findAll());
		List<Unit> us = new ArrayList<Unit>();
		for(Unit u : units){
			if(!u.getIsdeleted()){
				us.add(u);
			}
		}
		return us;
//		return query(FIND_UNITS, new MultiRow());
	}

	public Map<String, Unit> getUnitMap() {
		List<Unit> units =  Unit.dt(unitRemoteService.findAll());
		Map<String,Unit> us = new HashMap<String,Unit>();
		for(Unit u : units){
			if(!u.getIsdeleted()){
				us.put(u.getId(),u);
			}
		}
		return us;
//		return queryForMap(FIND_UNITS, new MapRow());
	}

	public List<Unit> getUnits(int state) {
		return Unit.dt(unitRemoteService.findByUnionId(null, state, -1));
//		return query(FIND_UNITS_BY_STATE, new Object[] { state },
//				new MultiRow());
	}

	public List<Unit> getUnits(int state, int unitClass) {
		return Unit.dt(unitRemoteService.findByUnionId(null, state, unitClass));
//		return query(FIND_UNITS_BY_STATE_CLASS,
//				new Object[] { state, unitClass }, new MultiRow());
	}

	public Map<String, Unit> getUnitMap(String unionId, int state, int unitClass) {
		return EntityUtils.getMap(Unit.dt(unitRemoteService.findByUnionId(unionId+"%", state, unitClass)),"id");
//		return queryForMap(FIND_UNITS_BY_CODE_STATE_CLASS, new Object[] {
//				unionId + "%", state, unitClass }, new MapRow());
	}

	public List<Unit> getUnderlingUnits(String parentId, String exceptId,
			boolean withRemote) {
		List<Unit> units =  Unit.dt(unitRemoteService.findByParentId(parentId));
		List<Unit> us = new ArrayList<Unit>();
		for(Unit u : units){
			if(!u.getIsdeleted()){
				if(u.getMark().intValue() == Unit.UNIT_MARK_NORAML){
					if (StringUtils.isNotBlank(exceptId) && StringUtils.equals(u.getId(), exceptId)) {
						continue;
					}
					if (!withRemote && u.getUsetype().intValue() != Unit.UNIT_USETYPE_LOCAL) {
						continue;
					}
					us.add(u);
				}
			}
		}
		return us;
		
//		StringBuffer sql = new StringBuffer();
//		sql.append(FIND_UNITS_BY_PARENTID_STATE);
//
//		if (StringUtils.isNotBlank(exceptId)) {
//			sql.append(" AND id<>'");
//			sql.append(exceptId);
//			sql.append("' ");
//		}
//		// 不包含远程注册单位
//		if (!withRemote) {
//			sql.append(" AND use_type =");
//			sql.append(Unit.UNIT_USETYPE_LOCAL);
//		}
//		sql.append("  ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789')) ");
//
//		return query(sql.toString(), new Object[] { parentId,
//				Unit.UNIT_MARK_NORAML }, new MultiRow());
	}

	public List<Unit> getUnderlingUnits(String parentId) {
		List<Unit> units =  Unit.dt(unitRemoteService.findByParentId(parentId));
		return units;
//		return query(FIND_UNITS_BY_PARENTID, new Object[] { parentId },
//				new MultiRow());
	}

	public List<Unit> getUnderlingUnits(String parentId, int state,
			int unitClass) {
		return Unit.dt(unitRemoteService.findByParentIdAndUnitClass(parentId,state,unitClass));
//		return query(FIND_UNITS_BY_PARENTID_STATE_CLASS, new Object[] {
//				parentId, state, unitClass }, new MultiRow());
	}

	public List<Unit> getUnderlingUnits(String parentId, int state,
			int unitClass, int unitType) {
		List<Unit> units =  Unit.dt(unitRemoteService.findByParentIdAndUnitClass(parentId,state,unitClass));
		List<Unit> us = new ArrayList<Unit>();
		for(Unit u : units){
			if(u.getUnittype().intValue() == unitType){
				us.add(u);
			}
		}
		return us;
//		return query(FIND_UNITS_BY_PARENTID_STATE_CLASS_TYPE, new Object[] {
//				parentId, state, unitClass, unitType }, new MultiRow());
	}

	public List<Unit> getUnderlingUnits(String parentId, int unitClass,
			int state, String runSchoolType, String[] unitUseTypes,
			Pagination page) {
		String sql = FIND_UNIT_PREFIX
				+ " WHERE parent_id=? AND unit_state=? AND unit_class=? ";
		if (StringUtils.isNotBlank(runSchoolType)) {
			if ("999".equals(runSchoolType)) {
				sql += " AND run_school_type =? ";
			} else {
				runSchoolType = "999";
				sql += " AND run_school_type <>? ";
			}
			if (unitUseTypes != null) {
				sql += " and is_deleted=0 and unit_use_type IN";
				if (page == null) {
					return queryForInSQL(sql, new Object[] { parentId, state,
							unitClass, runSchoolType }, unitUseTypes,
							new MultiRow());
				} else {
					return queryForInSQL(sql, new Object[] { parentId, state,
							unitClass, runSchoolType }, unitUseTypes,
							new MultiRow(), "", page);
				}
			} else {
				sql += " and is_deleted=0 and (unit_use_type is null or unit_use_type='')";
				if (page == null) {
					return query(sql, new Object[] { parentId, state,
							unitClass, runSchoolType }, new MultiRow());
				} else {
					return query(sql, new Object[] { parentId, state,
							unitClass, runSchoolType }, new MultiRow(), page);
				}
			}

		} else {
			if (unitUseTypes != null) {
				sql += " and is_deleted=0 and unit_use_type IN";
				if (page == null) {
					return queryForInSQL(sql, new Object[] { parentId, state,
							unitClass }, unitUseTypes, new MultiRow());
				} else {
					return queryForInSQL(sql, new Object[] { parentId, state,
							unitClass }, unitUseTypes, new MultiRow(), "", page);
				}
			} else {
				sql += " and is_deleted=0 and (unit_use_type is null or unit_use_type='')";
				if (page == null) {
					return query(sql,
							new Object[] { parentId, state, unitClass },
							new MultiRow());
				} else {
					return query(sql,
							new Object[] { parentId, state, unitClass },
							new MultiRow(), page);
				}
			}
		}
	}

	public List<Unit> getUnits(String[] ids) {
		List<Unit> units = Unit.dt(unitRemoteService.findByIds(ids));
		List<Unit> us = new ArrayList<Unit>();
		for(Unit u : units){
			if(!u.getIsdeleted()){
				us.add(u);
			}
		}
		return us;
//		return queryForInSQL(
//				FIND_UNIT_BY_IDS,
//				null,
//				ids,
//				new MultiRow(),
//				" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789')) ");
	}
	
	
	public List<Unit> getUnitsWithDel(String[] ids) {
		List<Unit> units = Unit.dt(unitRemoteService.findByIds(ids));
		return units;
//		return queryForInSQL(
//				FIND_UNIT_INCLUDE_DEL_BY_IDS,
//				null,
//				ids,
//				new MultiRow(),
//				" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789')) ");
	}
	
	public Map<String, Unit> getUnitsMapWithDel(String[] ids) {
		return EntityUtils.getMap(Unit.dt(unitRemoteService.findByIds(ids)), "id");
//		return queryForInSQL(
//				FIND_UNIT_INCLUDE_DEL_BY_IDS,
//				null,
//				ids,
//				new MapRow());
	}
	
	public List<Unit> getUnitsByRegion(String region, int state) {
		return Unit.dt(unitRemoteService.findByRegion(region, state));
//		return query(FIND_UNITS_BY_REGION_STATE,
//				new Object[] { region, state }, new MultiRow());
	}

	public int getCountByRegion(String region, int state) {
		return Unit.dt(unitRemoteService.findByRegion(region, state)).size();
//		return queryForInt(FIND_COUNT_BY_REGION_STATE, new Object[] { region ,state});
	}

	public List<Unit> getUnitsByFaintness(String unitName) {
		 return Unit.dt(unitRemoteService.findByUnitName(unitName+"%"));
//		return query(FIND_UNITS_BY_NAME_FAINTNESS, new Object[] { unitName
//				+ "%" }, new MultiRow());
	}

	public List<Unit> getUnitsByFaintness(String unitName, int unitClass) {
		List<Unit> units = Unit.dt(unitRemoteService.findByUnitName(unitName+"%"));
		List<Unit> us = new ArrayList<Unit>();
		for(Unit u : units){
			if(u.getUnitclass().intValue() == unitClass){
				us.add(u);
			}
		}
		return us;
//		return query(FIND_UNITS_BY_NAME_FAINTNESS_CLASS, new Object[] {
//				unitName + "%", unitClass }, new MultiRow());
	}

	public List<Unit> getUnitsByUnionCodeUnitType(String unionId,
			int unitClass, int unitType) {
		List<Unit> units = Unit.dt(unitRemoteService.findByUnionId(unionId+"%",-1,unitClass));
		List<Unit> us = new ArrayList<Unit>();
		for(Unit u : units){
			if(u.getUnittype().intValue() != unitType){
				us.add(u);
			}
		}
		return us;
//		return query(FIND_UNITS_BY_CODE_CLASS_UNITTYPE, new Object[] {
//				unionId + "%", unitClass, unitType }, new MultiRow());
	}

	public List<Unit> getUnitsByUnionCodeUnitType(String unionId, int state,
			int unitClass, int unitType) {
		List<Unit> units = Unit.dt(unitRemoteService.findByUnionId(unionId+"%",state,unitClass));
		List<Unit> us = new ArrayList<Unit>();
		for(Unit u : units){
			if(u.getUnittype().intValue() == unitType){
				us.add(u);
			}
		}
		return us;
//		return query(FIND_UNITS_BY_CODE_CLASS_UNITTYPE_STATE, new Object[] {
//				unionId + "%", unitClass, state, unitType }, new MultiRow());
	}

	public List<Unit> getUnitsList(String unionId, int state, int unitClass,
			int unitType) {
		List<Unit> units = Unit.dt(unitRemoteService.findByUnionId(unionId+"%",state,unitClass));
		List<Unit> us = new ArrayList<Unit>();
		for(Unit u : units){
			if(u.getUnittype().intValue() != unitType){
				us.add(u);
			}
		}
		return us;
//		return query(FIND_UNITS_BY_CODE_CLASS_NON_UNITTYPE_STATE, new Object[] {
//				unionId + "%", unitClass, state, unitType }, new MultiRow());
	}

	public List<Unit> getUnitsByUseType(int state, int useType) {
		return Unit.dt(unitRemoteService.findByUseType(state, useType));
//		return query(FIND_UNITS_BY_STATE_USETYPE,
//				new Object[] { state, useType }, new MultiRow());
	}

	public Map<String, Unit> getUnitMap(String[] unitIds) {
		List<Unit> units = Unit.dt(unitRemoteService.findByIds(unitIds));
		Map<String,Unit> umap = new HashMap<String, Unit>();
		for(Unit u : units){
			if(!u.getIsdeleted()){
				umap.put(u.getId(), u);
			}
		}
		return umap;
//		return queryForInSQL(
//				FIND_UNIT_BY_IDS,
//				null,
//				unitIds,
//				new MapRow(),
//				" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789')) ");
	}

	public List<Unit> getUnitsByUnionCode(String unionId) {
		return Unit.dt(unitRemoteService.findByUnionId(unionId+"%", -1, -1));
//		return query(FIND_UNITS_BY_CODE, new Object[] { unionId + "%" },
//				new MultiRow());
	}

	public List<Unit> getUnitsByUnionCode(String unionId, int state,
			String exceptId) {
		List<Unit> units = Unit.dt(unitRemoteService.findByUnionId(unionId+"%",state,-1));
		List<Unit> us = new ArrayList<Unit>();
		for(Unit u : units){
			if(!StringUtils.equals(u.getId(), exceptId)){
				us.add(u);
			}
		}
		return us;
//		String sql = FIND_UNITS_BY_CODE_STATE;
//		if (StringUtils.isNotBlank(exceptId)) {
//			sql += " and id<> '" + exceptId + "' ";
//		}
//		sql += " ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";
//		return query(sql, new Object[] { unionId + "%", state }, new MultiRow());
	}
	@Override
	public Map<String, Integer> getUnderUnitMapByUnitUseType(String unionId,
			int state, String exceptId) {
		
		List<Unit> units = Unit.dt(unitRemoteService.findByUnionId(unionId+"%",state,-1));
		Map<String,Integer> map = new HashMap<String, Integer>();
		for(Unit u : units){
			if(!StringUtils.equals(u.getId(), exceptId)){
				map.put(u.getUsetype()+"",map.get(NumberUtils.toInt(u.getUsetype()+"")) + 1);
			}
		}
		
		return map;
		
		
//		String sql = "select unit_use_type, count(1) countNum from base_unit where union_code like ? AND unit_state = ? AND is_deleted=0 ";
//		if (StringUtils.isNotBlank(exceptId)) {
//			sql += " and id<> '" + exceptId + "' ";
//		}
//		sql += " group by unit_use_type ";
//		return queryForMap(sql, new Object[]{unionId + "%", state}, new MapRowMapper<String, Integer>(){
//			@Override
//			public String mapRowKey(ResultSet rs, int rowNum)
//					throws SQLException {
//				return rs.getString("unit_use_type");
//			}
//			
//			@Override
//			public Integer mapRowValue(ResultSet rs, int rowNum)
//					throws SQLException {
//				return rs.getInt("countNum");
//			}
//		});
	}

	public List<Unit> getUnitsByUnionCode(String unionId, int state,
			int unitClass) {
		return Unit.dt(unitRemoteService.findByUnionId(unionId+"%", state, unitClass));
//		return query(FIND_UNITS_BY_CODE_STATE_CLASS, new Object[] {
//				unionId + "%", state, unitClass }, new MultiRow());
	}
	
	public List<String> getUnitIdsByUnionCode(String unionId, int state, 
			int unitClass){
		List<Unit> units = Unit.dt(unitRemoteService.findByUnionId(unionId+"%",state,unitClass));
		List<String> ids = new ArrayList<String>();
		for(Unit u : units){
			ids.add(u.getId());
		}
		return ids;
//		return query(FIND_UNITIDS_BY_CODE_STATE_CLASS, new Object[]{
//				unionId + "%", state, unitClass	}, new MultiRowMapper<String>(){
//
//			@Override
//			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//				return rs.getString("id");
//			}
//		});
	}

	/**
	 * 获取单位Map key为单位名称
	 * 
	 * @param unitNames
	 * @return
	 */
	public Map<String, Unit> getUnitMapByUnitName(String[] unitNames) {
		return EntityUtils.getMap(Unit.dt(unitRemoteService.findByUnitName(unitNames)), "unitName");
//		String sql = FIND_UNIT_PREFIX + " where unit_name in";
//		return queryForInSQL(sql, null, unitNames,
//				new MapRowMapper<String, Unit>() {
//
//					@Override
//					public String mapRowKey(ResultSet rs, int rowNum)
//							throws SQLException {
//						return rs.getString("unit_name");
//					}
//
//					@Override
//					public Unit mapRowValue(ResultSet rs, int rowNum)
//							throws SQLException {
//						return setField(rs);
//					}
//
//				});
	}

	public List<Unit> getUnitsByNameAndUnionCode(String unitName,
			String unionId, int state, int unitClass) {
		
		return Unit.dt(unitRemoteService.findByNameAndUnionCode("%"+unitName+"%", "%"+unionId+"%", state, unitClass));
		
//		return query(FIND_BY_CODE_STATE_CLASS, new Object[] {
//				"%" + unitName + "%", unionId + "%", state, unitClass },
//				new MultiRow());
	}

	public List<Unit> getUnderlingUnits(String unionid, String unitName,
			Pagination page) {
		if (null == unitName || "".equals(unitName)) {
			unitName = "%";
		} else {
			unitName = "%" + unitName + "%";
		}
		if (null == page) {
			return Unit.dt(unitRemoteService.findByUnderlingUnits(unitName, unionid+"%"));
//			return query(FIND_UNDERLING_UNITS_NAME, new Object[] {
//					unionid + "%", unitName }, new MultiRowMapper<Unit>() {
//				public Unit mapRow(ResultSet rs, int arg1) throws SQLException {
//					Unit unit = new Unit();
//					unit.setId(rs.getString("id"));
//					unit.setUnionid(rs.getString("union_code"));
//					unit.setName(rs.getString("unit_name"));
//					unit.setUnitclass(rs.getInt("unit_class"));
//					unit.setCreationTime(rs.getTimestamp("creation_time"));
//					unit.setMark(rs.getInt("unit_state"));
//					unit.setRegionlevel(rs.getInt("region_level"));
//					return unit;
//				}
//
//			});
		}
		return Unit.dt(unitRemoteService.findByUnderlingUnits(unitName, unionid+"%",SUtils.s(page)),page);
//		return query(FIND_UNDERLING_UNITS_NAME, new Object[] { unionid + "%",
//				unitName }, new MultiRowMapper<Unit>() {
//			public Unit mapRow(ResultSet rs, int arg1) throws SQLException {
//				Unit unit = new Unit();
//				unit.setId(rs.getString("id"));
//				unit.setUnionid(rs.getString("union_code"));
//				unit.setName(rs.getString("unit_name"));
//				unit.setUnitclass(rs.getInt("unit_class"));
//				unit.setCreationTime(rs.getTimestamp("creation_time"));
//				unit.setMark(rs.getInt("unit_state"));
//				return unit;
//			}
//
//		}, page);
	}
	
	
	@Override
	public List<Unit> getUnitsBySearchName(String searchName, Pagination page) {
		searchName = "%"+searchName+"%";
		if(page == null){
			return Unit.dt(unitRemoteService.findByUnitName(searchName));
//			return query(FIND_UNITS_BY_NAME_FAINTNESS, new Object[]{searchName}, new MultiRow());
		}
		return Unit.dt(unitRemoteService.findByUnitName(searchName, SUtils.s(page)),page); 
//		return query(FIND_UNITS_BY_NAME_FAINTNESS, new Object[]{searchName}, new MultiRow(), page);
	}

	@Override
	public List<Unit> getUnderlingUnitsByPage(String parentId, int unitClass,int state,
			Pagination page) {
		
		if(page == null){
//			return query(FIND_UNITS_BY_PARENTID_STATE_CLASS, new Object[] {
//					parentId, state, unitClass }, new MultiRow());
			return Unit.dt(unitRemoteService.findByParentIdAndUnitClass(parentId,state,unitClass));
		}
		return Unit.dt(unitRemoteService.findByParentIdAndUnitClass(parentId,state,unitClass, SUtils.s(page)),page);
//		return query(FIND_UNITS_BY_PARENTID_STATE_CLASS, new Object[]{parentId, state, unitClass}, 
//				new MultiRow(), page);
	}
	public List<Unit> getUnitListBySerialNumber(String sNumber, String eNumber) {
		return Unit.dt(unitRemoteService.findBySerialNumber(sNumber,eNumber));
//		String sql="select * from base_unit where base_unit.is_deleted=0 and  serial_number>= ? and serial_number<=? ";
//		return query(sql, new Object[]{sNumber, eNumber},new MultiRow());
	}
}
