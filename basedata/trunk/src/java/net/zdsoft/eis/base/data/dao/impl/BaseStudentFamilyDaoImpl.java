/**
 * 
 */
package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.data.dao.BaseStudentFamilyDao;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.client.BaseDao;

/**
 * @author yanb
 * 
 */
public class BaseStudentFamilyDaoImpl extends BaseDao<Family> implements
		BaseStudentFamilyDao {
	private static final String SQL_INSERT_FAMILY = "INSERT INTO base_family(id,student_id,school_id,"
			+ "relation,real_name,company,duty,link_phone,work_code,profession_code,"
			+ "duty_level,political_status,marital_status,emigration_place,birthday,culture,identity_card,"
			+ "nation,is_guardian,homepage,remark,postalcode,link_address,email,mobile_phone,office_tel,sex,"
			+ "region_code,creation_time,modify_time,is_deleted,charge_number,charge_number_type,is_leave_school,event_source, identitycard_type, health,relation_remark,register_place,is_residence , isbuy_socsec) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SQL_DELETE_FAMILY_BY_IDS = "UPDATE base_family SET is_deleted = 1,modify_time = ?,event_source=?  WHERE id IN ";

	private static final String SQL_UPDATE_FAMILY = "UPDATE base_family SET school_id=?,"
			+ "relation=?,real_name=?,company=?,duty=?,link_phone=?,work_code=?,profession_code=?,"
			+ "duty_level=?,political_status=?,marital_status=?,emigration_place=?,birthday=?,culture=?,identity_card=?,"
			+ "nation=?,is_guardian=?,homepage=?,remark=?,postalcode=?,link_address=?,email=?,mobile_phone=?,office_tel=?,sex=?,"
			+ "region_code=?,modify_time=?,is_deleted=?,charge_number=?,charge_number_type=?,is_leave_school=?,event_source=?," +
			" identitycard_type=?, health=?,relation_remark=?,register_place=? ,is_residence =?, isbuy_socsec=?  WHERE id=?";

	private static final String SQL_UPDATE_FAMILY_LEAVESCHOOL_BY_STUDENTID = "UPDATE base_family SET school_id=?,is_leave_school = ?,modify_time = ?,event_source=? WHERE student_id=? ";

	private static final String SQL_DELETE_FAMILY_BY_STUDENTIDS = "UPDATE base_family SET is_deleted = 1, modify_time = ?,event_source=? WHERE student_id in ";

	private static final String SQL_UPDATE_FAMILY_MOBILE_PHONE="update base_family set mobile_phone=?,modify_time = ? where id=?";
	
	@Override
	public Family setField(ResultSet rs) throws SQLException {
		Family family = new Family();
		family.setId(rs.getString("id"));
		family.setStudentId(rs.getString("student_id"));
		family.setSchoolId(rs.getString("school_id"));
		family.setRelation(rs.getString("relation"));
		family.setName(rs.getString("real_name"));
		family.setCompany(rs.getString("company"));
		family.setDuty(rs.getString("duty"));
		family.setLinkPhone(rs.getString("link_phone"));
		family.setWorkCode(rs.getString("work_code"));
		family.setProfessionCode(rs.getString("profession_code"));
		family.setDutyLevel(rs.getString("duty_level"));
		family.setPoliticalStatus(rs.getString("political_status"));
		family.setMaritalStatus(rs.getString("marital_status"));
		family.setEmigrationPlace(rs.getString("emigration_place"));
		family.setBirthday(rs.getTimestamp("birthday"));
		family.setCulture(rs.getString("culture"));
		family.setIdentityCard(rs.getString("identity_card"));
		family.setNation(rs.getString("nation"));
		family.setGuardian(rs.getBoolean("is_guardian"));
		family.setHomepage(rs.getString("homepage"));
		family.setRemark(rs.getString("remark"));
		family.setPostalcode(rs.getString("postalcode"));
		family.setLinkAddress(rs.getString("link_address"));
		family.setEmail(rs.getString("email"));
		family.setMobilePhone(rs.getString("mobile_phone"));
		family.setOfficeTel(rs.getString("office_tel"));
		family.setSex(String.valueOf(rs.getInt("sex")));
		family.setRegionCode(rs.getString("region_code"));
		family.setCreationTime(rs.getTimestamp("creation_time"));
		family.setModifyTime(rs.getTimestamp("modify_time"));
		family.setChargeNumber(rs.getString("charge_number"));
		family.setChargeNumberType(rs.getInt("charge_number_type"));
		family.setLeaveSchool(rs.getInt("is_leave_school"));
		
		family.setIdentitycardType(rs.getString("identitycard_type"));
		family.setHealth(rs.getString("health"));
		
		 family.setRelationRemark(rs.getString("RELATION_REMARK"));
	     family.setRegisterPlace(rs.getString("REGISTER_PLACE"));
		return family;
	}

	public void insertFamily(Family family) {
		if (StringUtils.isBlank(family.getId()))
			family.setId(createId());
		family.setCreationTime(new Date());
		family.setModifyTime(new Date());
		family.setIsdeleted(false);
		if ("".equals(family.getSex()))
			family.setSex(null);
		try {

			update(SQL_INSERT_FAMILY, new Object[] { family.getId(),
					family.getStudentId(), family.getSchoolId(),
					family.getRelation(), family.getName(),
					family.getCompany(), family.getDuty(),
					family.getLinkPhone(), family.getWorkCode(),
					family.getProfessionCode(), family.getDutyLevel(),
					family.getPoliticalStatus(), family.getMaritalStatus(),
					family.getEmigrationPlace(), family.getBirthday(),
					family.getCulture(), family.getIdentityCard(),
					family.getNation(), family.isGuardian() ? 1 : 0,
					family.getHomepage(), family.getRemark(),
					family.getPostalcode(), family.getLinkAddress(),
					family.getEmail(), family.getMobilePhone(),
					family.getOfficeTel(), family.getSex(),
					family.getRegionCode(), family.getCreationTime(),
					family.getModifyTime(), family.getIsdeleted() ? 1 : 0,
					family.getChargeNumber(), family.getChargeNumberType(),
					family.getLeaveSchool(), family.getEventSourceValue(),
					family.getIdentitycardType(), family.getHealth() ,
					family.getRelationRemark(),family.getRegisterPlace(),
					family.getIsResidence(),family.getIsbuySocsec()},
					new int[] { Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR,
							Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
							Types.VARCHAR, Types.CHAR, Types.CHAR, Types.CHAR,
							Types.CHAR, Types.CHAR, Types.CHAR, Types.DATE,
							Types.CHAR, Types.VARCHAR, Types.CHAR,
							Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
							Types.CHAR, Types.VARCHAR, Types.VARCHAR,
							Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
							Types.CHAR, Types.TIMESTAMP, Types.TIMESTAMP, Types.INTEGER,
							Types.VARCHAR, Types.INTEGER, Types.INTEGER,
							Types.INTEGER, Types.CHAR, Types.CHAR, Types.VARCHAR, Types.CHAR,Types.CHAR, Types.CHAR});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void insertFamilies(List<Family> famList) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (int i = 0; i < famList.size(); i++) {
			Family family = famList.get(i);
			if (StringUtils.isBlank(family.getId()))
				family.setId(createId());
			family.setCreationTime(new Date());
			family.setModifyTime(new Date());
			family.setIsdeleted(false);
			if ("".equals(family.getSex())) {
				family.setSex(null);
			}
			Object[] objs = new Object[] { family.getId(),
					family.getStudentId(), family.getSchoolId(),
					family.getRelation(), family.getName(),
					family.getCompany(), family.getDuty(),
					family.getLinkPhone(), family.getWorkCode(),
					family.getProfessionCode(), family.getDutyLevel(),
					family.getPoliticalStatus(), family.getMaritalStatus(),
					family.getEmigrationPlace(), family.getBirthday(),
					family.getCulture(), family.getIdentityCard(),
					family.getNation(), family.isGuardian() ? 1 : 0,
					family.getHomepage(), family.getRemark(),
					family.getPostalcode(), family.getLinkAddress(),
					family.getEmail(), family.getMobilePhone(),
					family.getOfficeTel(), family.getSex(),
					family.getRegionCode(), family.getCreationTime(),
					family.getModifyTime(), family.getIsdeleted() ? 1 : 0,
					family.getChargeNumber(), family.getChargeNumberType(),
					family.getLeaveSchool(), family.getEventSourceValue(), family.getIdentitycardType(), family.getHealth(),family.getRelationRemark(),family.getRegisterPlace(),
					family.getIsResidence(),family.getIsbuySocsec()};
			listOfArgs.add(objs);
		}

		int[] argTypes = new int[] { Types.CHAR, Types.CHAR, Types.CHAR,
				Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR,
				Types.CHAR, Types.CHAR, Types.DATE, Types.CHAR, Types.VARCHAR,
				Types.CHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
				Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.TIMESTAMP,
				Types.TIMESTAMP, Types.INTEGER, Types.VARCHAR, Types.INTEGER,
				Types.INTEGER, Types.INTEGER, Types.CHAR, Types.CHAR, Types.VARCHAR, Types.CHAR,Types.CHAR, Types.CHAR};
		batchUpdate(SQL_INSERT_FAMILY, listOfArgs, argTypes);
	}

	public void updateFamily(Family family) {
		family.setModifyTime(new Date());
		family.setIsdeleted(false);
		update(SQL_UPDATE_FAMILY, new Object[] {
				family.getSchoolId(), family.getRelation(), family.getName(),
				family.getCompany(), family.getDuty(), family.getLinkPhone(),
				family.getWorkCode(), family.getProfessionCode(),
				family.getDutyLevel(), family.getPoliticalStatus(),
				family.getMaritalStatus(), family.getEmigrationPlace(),
				family.getBirthday(), family.getCulture(),
				family.getIdentityCard(), family.getNation(),
				family.isGuardian() ? 1 : 0, family.getHomepage(),
				family.getRemark(), family.getPostalcode(),
				family.getLinkAddress(), family.getEmail(),
				family.getMobilePhone(), family.getOfficeTel(),
				family.getSex(), family.getRegionCode(),
				family.getModifyTime(), family.getIsdeleted() ? 1 : 0,
				family.getChargeNumber(), family.getChargeNumberType(),
				family.getLeaveSchool(), family.getEventSourceValue(),family.getIdentitycardType(), family.getHealth(),family.getRelationRemark(),family.getRegisterPlace(),
				
				family.getIsResidence(),family.getIsbuySocsec(),
				
				family.getId() }, 
				new int[] {  Types.CHAR,
				Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR,
				Types.CHAR, Types.CHAR, Types.DATE, Types.CHAR, Types.VARCHAR,
				Types.CHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
				Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.TIMESTAMP,
				Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.INTEGER,
				Types.INTEGER, Types.CHAR, Types.CHAR, Types.VARCHAR, Types.CHAR,Types.CHAR,Types.CHAR, Types.CHAR });
	}

	public void updateFamilies(List<Family> famList) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (int i = 0; i < famList.size(); i++) {
			Family family = famList.get(i);
			family.setModifyTime(new Date());
			family.setIsdeleted(false);
			Object[] objs = new Object[] {
					family.getSchoolId(), family.getRelation(),
					family.getName(), family.getCompany(), family.getDuty(),
					family.getLinkPhone(), family.getWorkCode(),
					family.getProfessionCode(), family.getDutyLevel(),
					family.getPoliticalStatus(), family.getMaritalStatus(),
					family.getEmigrationPlace(), family.getBirthday(),
					family.getCulture(), family.getIdentityCard(),
					family.getNation(), family.isGuardian() ? 1 : 0,
					family.getHomepage(), family.getRemark(),
					family.getPostalcode(), family.getLinkAddress(),
					family.getEmail(), family.getMobilePhone(),
					family.getOfficeTel(), family.getSex(),
					family.getRegionCode(), family.getModifyTime(),
					family.getIsdeleted() ? 1 : 0, family.getChargeNumber(),
					family.getChargeNumberType(), family.getLeaveSchool(),
					family.getEventSourceValue(), family.getIdentitycardType(), family.getHealth(),family.getRelationRemark(),family.getRegisterPlace(),
					
					family.getIsResidence(),family.getIsbuySocsec(),
					family.getId() };
			listOfArgs.add(objs);
		}
		int[] argTypes = new int[] {  Types.CHAR, Types.CHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR,
				Types.CHAR, Types.DATE, Types.CHAR, Types.VARCHAR, Types.CHAR,
				Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.CHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.CHAR, Types.TIMESTAMP, Types.INTEGER,
				Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER,
				Types.CHAR, Types.CHAR, Types.VARCHAR, Types.CHAR, Types.CHAR,Types.CHAR, Types.CHAR };
		batchUpdate(SQL_UPDATE_FAMILY, listOfArgs, argTypes);
	}

	public void updateFamilyByStudent(List<Family> familyList) {
        List<Object[]> listOfArgs = new ArrayList<Object[]>();
        for (int i = 0; i < familyList.size(); i++) {
            Family family = familyList.get(i);
            family.setModifyTime(new Date());
            Object[] objs = new Object[] { family.getSchoolId(), family.getLeaveSchool(),
                    family.getModifyTime(), family.getEventSourceValue(), family.getStudentId() };
            listOfArgs.add(objs);
        }
        int[] argTypes = new int[] { Types.CHAR, Types.INTEGER, Types.TIMESTAMP, Types.INTEGER,
                Types.CHAR };
        batchUpdate(SQL_UPDATE_FAMILY_LEAVESCHOOL_BY_STUDENTID, listOfArgs, argTypes);
    }

	public void deleteFamiliesByFamilyIds(String[] familyIds,
			EventSourceType eventSource) {
		updateForInSQL(SQL_DELETE_FAMILY_BY_IDS, new Object[] { new Date(),
				eventSource.getValue() }, familyIds);
	}

	public void deleteFamilyByStudentIds(String[] stuids) {
        updateForInSQL(SQL_DELETE_FAMILY_BY_STUDENTIDS, new Object[] { new Date(),
                EventSourceType.LOCAL.getValue() }, stuids);
    }

    
	/**
	 * 家庭成员信息是否存在
	 * 
	 */
	public boolean isFamilyExist(String unitId, String stuId, String realName, String relation) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from base_family where school_id = '").
		append(unitId).append("' and student_id = '").append(stuId).append("' and real_name = '").
		append(realName).append("' and is_deleted = 0 and relation = '").append(relation).append("'");
		
		int result = this.getJdbcTemplate().queryForInt(sb.toString());
		return (result != 0);
	}

	@Override
	public void updateFamily(String familyMobPho, String id) {
		update(SQL_UPDATE_FAMILY_MOBILE_PHONE, new Object[]{familyMobPho,new Date(),id});
	}

}
