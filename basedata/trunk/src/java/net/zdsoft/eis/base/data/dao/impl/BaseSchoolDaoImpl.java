/**
 * 
 */
package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.data.dao.BaseSchoolDao;
import net.zdsoft.eis.base.data.entity.BaseSchool;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.client.BaseDao;

/**
 * @author yanb
 * 
 */
public class BaseSchoolDaoImpl extends BaseDao<BaseSchool> implements
		BaseSchoolDao {

	private static final String SQL_INSERT_SCHOOL = "INSERT INTO base_school(id,school_district_id,school_name,"
			+ "english_name,short_name,school_code,address,region_code,schoolmaster,party_master,"
			+ "build_date,anniversary,governor,run_school_type,school_type,region_type,region_economy,"
			+ "region_nation,grade_year,grade_age,junior_year,junior_age,senior_year,infant_year,infant_age,primary_lang,"
			+ "secondary_lang,recruit_region,postalcode,link_phone,fax,email,homepage,organization_code,introduction,area,"
			+ "serial_number,sections,emphases,is_boorish,demonstration,creation_time,modify_time,is_deleted,event_source,region_property_code) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SQL_DELETE_SCHOOL = "update base_school set is_deleted = 1,event_source=?,modify_time=? where id = ?";

	private static final String SQL_UPDATE_SCHOOL = "UPDATE base_school SET school_district_id=?,school_name=?,"
			+ "english_name=?,short_name=?,school_code=?,address=?,region_code=?,schoolmaster=?,party_master=?,"
			+ "build_date=?,anniversary=?,governor=?,run_school_type=?,school_type=?,region_type=?,region_economy=?,"
			+ "region_nation=?,grade_year=?,grade_age=?,junior_year=?,junior_age=?,senior_year=?,infant_year=?,"
			+ "infant_age=?,primary_lang=?,secondary_lang=?,recruit_region=?,postalcode=?,link_phone=?,fax=?,"
			+ "email=?,homepage=?,organization_code=?,introduction=?,area=?,serial_number=?,sections=?,emphases=?,"
			+ "is_boorish=?,demonstration=?,modify_time=?,is_deleted=?,event_source=?,region_property_code=? WHERE id=?";

	private static final String SQL_FIND_CNT_BY_DISTRICTID = "SELECT COUNT(*) FROM base_school WHERE is_deleted = 0 AND school_district_id=?";
	private static final String SQL_FIND_CODE_EXISTS_BY_ID = "SELECT count(school_code) FROM base_school WHERE id = ? AND is_deleted = 0";

	private static final String SQL_FIND_SCHOOL_BY_ID = "SELECT * FROM base_school WHERE id=?";

	private static final String SQL_FIND_SCHOOL_BY_UNIONID_NAME = "SELECT s.* FROM base_school s,base_unit u "
			+ "where s.id=u.id AND u.union_code like ? AND s.school_name like ? and s.is_deleted=0";

	private static final String SQL_FIND_SCHOOLS_BY_IDS = "SELECT * FROM base_school WHERE is_deleted = 0 and id IN";
	
	public BaseSchool setField(ResultSet rs) throws SQLException {
		BaseSchool school = new BaseSchool();
		school.setId(rs.getString("id"));
		school.setSchdistrictid(rs.getString("school_district_id"));
		school.setName(rs.getString("school_name"));
		school.setEnglishname(rs.getString("english_name"));
		school.setShortName(rs.getString("short_name"));
		school.setCode(rs.getString("school_code"));
		school.setAddress(rs.getString("address"));
		school.setRegion(rs.getString("region_code"));
		school.setShoolmaster(rs.getString("schoolmaster"));
		school.setPartymaster(rs.getString("party_master"));
		school.setDatecreated(rs.getTimestamp("build_date"));
		school.setAnniversary(rs.getString("anniversary"));
		school.setGovernor(rs.getString("governor"));
		school.setRunschtype(rs.getString("run_school_type"));
		school.setType(rs.getString("school_type"));
		school.setRegiontype(String.valueOf(rs.getInt("region_type")));
		school.setRegioneconomy(String.valueOf(rs.getInt("region_economy")));
		school.setRegionnation(String.valueOf(rs.getInt("region_nation")));
		school.setGradeyear(rs.getInt("grade_year"));
		school.setGradeage(rs.getInt("grade_age"));
		school.setJunioryear(rs.getInt("junior_year"));
		school.setJuniorage(rs.getInt("junior_age"));
		school.setSenioryear(rs.getInt("senior_year"));
		school.setInfantyear(rs.getInt("infant_year"));
		school.setInfantage(rs.getInt("infant_age"));
		school.setPrimarylang(rs.getString("primary_lang"));
		school.setSecondarylang(rs.getString("secondary_lang"));
		school.setRecruitregion(rs.getString("recruit_region"));
		school.setPostalcode(rs.getString("postalcode"));
		school.setLinkphone(rs.getString("link_phone"));
		school.setFax(rs.getString("fax"));
		school.setEmail(rs.getString("email"));
		school.setHomepage(rs.getString("homepage"));
		school.setOrganizationcode(rs.getString("organization_code"));
		school.setIntroduction(rs.getString("introduction"));
		school.setArea(rs.getString("area"));
		school.setEtohSchoolId(rs.getString("serial_number"));
		school.setSections(rs.getString("sections"));
		school.setEmphases(String.valueOf(rs.getInt("emphases")));
		school.setBoorish(rs.getInt("is_boorish"));
		school.setDemonstration(String.valueOf(rs.getInt("demonstration")));
		school.setCreationTime(rs.getTimestamp("creation_time"));
		school.setModifyTime(rs.getTimestamp("modify_time"));
		school.setRegionPropertyCode(rs.getString("region_property_code")); 
		return school;
	}

	public void insertSchool(BaseSchool school) {
		school.setCreationTime(new Date());
		school.setModifyTime(new Date());
		school.setIsdeleted(false);
		update(SQL_INSERT_SCHOOL, new Object[] { school.getId(),
				school.getSchdistrictid(), school.getName(),
				school.getEnglishname(), school.getShortName(),
				school.getCode(), school.getAddress(), school.getRegion(),
				school.getShoolmaster(), school.getPartymaster(),
				school.getDatecreated(), school.getAnniversary(),
				school.getGovernor(), school.getRunschtype(), school.getType(),
				school.getRegiontype(), school.getRegioneconomy(),
				school.getRegionnation(), school.getGradeyear(),
				school.getGradeage(), school.getJunioryear(),
				school.getJuniorage(), school.getSenioryear(),
				school.getInfantyear(), school.getInfantage(),
				school.getPrimarylang(), school.getSecondarylang(),
				school.getRecruitregion(), school.getPostalcode(),
				school.getLinkphone(), school.getFax(), school.getEmail(),
				school.getHomepage(), school.getOrganizationcode(),
				school.getIntroduction(), school.getArea(),
				school.getEtohSchoolId(), school.getSections(),
				school.getEmphases(), school.getBoorish(),
				school.getDemonstration(), school.getCreationTime(),
				school.getModifyTime(), school.getIsdeleted() ? 1 : 0,
				school.getEventSourceValue(),school.getRegionPropertyCode() }, new int[] { Types.CHAR,
				Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.VARCHAR,
				Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.VARCHAR,
				Types.INTEGER, Types.CHAR, Types.INTEGER, Types.INTEGER,
				Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER,
				Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER,
				Types.CHAR, Types.CHAR, Types.VARCHAR, Types.CHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER,
				Types.TIMESTAMP, Types.TIMESTAMP, Types.INTEGER, Types.INTEGER,Types.VARCHAR });
	}

	public void deleteSchool(String schoolId, EventSourceType eventSource) {
		update(SQL_DELETE_SCHOOL, new Object[] { eventSource.getValue(),
				new Date(), schoolId });
	}

	public void updateSchool(BaseSchool school) {
		if ("".equals(school.getRegiontype()))
			school.setRegiontype(null);

		if ("".equals(school.getRegioneconomy()))
			school.setRegioneconomy(null);

		if ("".equals(school.getRegionnation()))
			school.setRegionnation(null);

		if ("".equals(school.getEmphases()))
			school.setEmphases(null);
		
		if ("".equals(school.getDemonstration()))
			school.setDemonstration(null);

		school.setModifyTime(new Date());
		school.setIsdeleted(false);

		update(SQL_UPDATE_SCHOOL, new Object[] { school.getSchdistrictid(),
				school.getName(), school.getEnglishname(),
				school.getShortName(), school.getCode(), school.getAddress(),
				school.getRegion(), school.getShoolmaster(),
				school.getPartymaster(), school.getDatecreated(),
				school.getAnniversary(), school.getGovernor(),
				school.getRunschtype(), school.getType(),
				school.getRegiontype(), school.getRegioneconomy(),
				school.getRegionnation(), school.getGradeyear(),
				school.getGradeage(), school.getJunioryear(),
				school.getJuniorage(), school.getSenioryear(),
				school.getInfantyear(), school.getInfantage(),
				school.getPrimarylang(), school.getSecondarylang(),
				school.getRecruitregion(), school.getPostalcode(),
				school.getLinkphone(), school.getFax(), school.getEmail(),
				school.getHomepage(), school.getOrganizationcode(),
				school.getIntroduction(), school.getArea(),
				school.getEtohSchoolId(), school.getSections(),
				school.getEmphases(), school.getBoorish(),
				school.getDemonstration(), school.getModifyTime(),
				school.getIsdeleted() ? 1 : 0, school.getEventSourceValue(),
				school.getRegionPropertyCode(), school.getId() }, new int[] { Types.CHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE,
				Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.CHAR,
				Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER,
				Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER,
				Types.INTEGER, Types.INTEGER, Types.CHAR, Types.CHAR,
				Types.VARCHAR, Types.CHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
				Types.INTEGER, Types.INTEGER, Types.TIMESTAMP, Types.INTEGER,
				Types.INTEGER, Types.VARCHAR, Types.CHAR });
	}

	public boolean isExistSchDistrict(String schdistriId) {
		int rtn = queryForInt(SQL_FIND_CNT_BY_DISTRICTID,
				new String[] { schdistriId });
		if (rtn == 0)
			return false;
		else
			return true;
	}

	public boolean isExistSchoolCode(String schid) {
		int rtn = queryForInt(SQL_FIND_CODE_EXISTS_BY_ID,
				new Object[] { schid });
		if (rtn == 0) {
			return false;
		} else {
			return true;
		}
	}

	public BaseSchool getBaseSchool(String schoolId) {
		return query(SQL_FIND_SCHOOL_BY_ID, schoolId, new SingleRow());
	}

	public List<BaseSchool> getUnderlingSchoolsFaintness(String unionCode,
			String name) {
		return query(SQL_FIND_SCHOOL_BY_UNIONID_NAME, new String[] {
				unionCode + "%", "%" + name + "%" }, new MultiRow());
	}

    public List<BaseSchool> getBaseSchools(String[] schoolIds) {
        return queryForInSQL(SQL_FIND_SCHOOLS_BY_IDS, null, schoolIds, new MultiRow());
    }
}
