package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.dao.StudentFamilyDao;
import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;

public class StudentFamilyDaoImpl extends BaseDao<Family> implements StudentFamilyDao {

    private static final String SQL_FIND_FAMILY_BY_ID = "SELECT * FROM base_family WHERE id=?";
    private static final String SQL_FIND_FAMILYS_BY_IDS = "SELECT * FROM base_family WHERE is_deleted = 0 AND id IN";

    private static final String SQL_FIND_FAMILY_BY_STUDENTID = "SELECT * FROM base_family WHERE student_id = ? AND is_deleted = 0";
    private static final String SQL_FIND_FAMILY_BY_STUDENTIDS = "SELECT * FROM base_family WHERE is_deleted=0 AND student_id in ";

    private static final String SQL_FIND_FAMILY_BY_STUDENTID_PHONE = "SELECT * FROM base_family "
            + "WHERE student_id = ? AND mobile_phone = ? AND is_deleted = 0";
    
    private static final String SQL_FIND_FAMILY_BY_STUDENTID_RELATIONS = "SELECT * FROM base_family WHERE student_id=? and is_deleted=0 AND relation in ";

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
        family.setSex(rs.getString("sex"));
        family.setRegionCode(rs.getString("region_code"));
        family.setCreationTime(rs.getTimestamp("creation_time"));
        family.setModifyTime(rs.getTimestamp("modify_time"));
        family.setChargeNumber(rs.getString("charge_number"));
        family.setChargeNumberType(rs.getInt("charge_number_type"));
        family.setLeaveSchool(rs.getInt("is_leave_school"));
        //add by zhaojt 2014-10-20
        family.setReceiveInfomation(rs.getString("receive_infomation"));
        family.setSmartMobilePhone(rs.getString("smart_mobile_phone"));
        
        family.setIdentitycardType(rs.getString("identitycard_type"));
        family.setHealth(rs.getString("health"));
        family.setRelationRemark(rs.getString("RELATION_REMARK"));
        family.setRegisterPlace(rs.getString("REGISTER_PLACE"));
        
        family.setIsbuySocsec(rs.getString("isbuy_socsec"));
        family.setIsResidence(rs.getString("is_residence"));
        
        return family;
    }

    public Family getFamily(String familyId) {
        return query(SQL_FIND_FAMILY_BY_ID, familyId, new SingleRow());
    }

    public List<Family> getFamiliesByStudentId(String studentId) {
        return query(SQL_FIND_FAMILY_BY_STUDENTID, studentId, new MultiRow());
    }
    
    public List<Family> getFamiliesByStudentId(String studentId,String[] relations){
    	return queryForInSQL(SQL_FIND_FAMILY_BY_STUDENTID_RELATIONS, new Object[]{studentId},relations, new MultiRow(),"order by relation");
    }

    public List<Family> getFamilies(String[] familyIds) {
        return queryForInSQL(SQL_FIND_FAMILYS_BY_IDS, null, familyIds, new MultiRow());
    }

    public List<Family> getFamiliesByStudentId(String[] studentIds) {
        return queryForInSQL(SQL_FIND_FAMILY_BY_STUDENTIDS, null, studentIds, new MultiRow(),
                " order by student_id,relation");
    }

    public List<Family> getFamilies(String studentId, String phoneNum) {
        return query(SQL_FIND_FAMILY_BY_STUDENTID_PHONE, new String[] { studentId, phoneNum },
                new MultiRow());
    }

	@Override
	public List<Family> getFamiliesByUnit(String unitId) {
		String sql ="SELECT * FROM base_family WHERE is_deleted = 0 AND school_id=?";
		return query(sql, unitId, new MultiRow());
	}
}
