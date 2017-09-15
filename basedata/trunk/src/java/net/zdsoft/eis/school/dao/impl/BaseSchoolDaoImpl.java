package net.zdsoft.eis.school.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.school.dao.BaseSchoolDao;
import net.zdsoft.eis.school.entity.BaseSchool;
import net.zdsoft.keel.dao.BasicDAO;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.dao.SingleRowMapper;

import org.apache.commons.lang.StringUtils;

public class BaseSchoolDaoImpl extends BasicDAO implements BaseSchoolDao {
	private static final String SQL_INSERT_SCHOOL = "INSERT INTO base_school(id,school_district_id,school_name,"
			+ "english_name,short_name,school_code,address,region_code,schoolmaster,party_master,"
			+ "build_date,anniversary,governor,run_school_type,school_type,region_type,region_economy,"
			+ "region_nation,grade_age,junior_age,infant_year,infant_age,primary_lang,"
			+ "secondary_lang,recruit_region,postalcode,link_phone,fax,email,homepage,organization_code,introduction,area,"
			+ "serial_number,sections,emphases,is_boorish,demonstration,creation_time,modify_time,is_deleted,event_source,feature,public_assist,builtup_area,green_area,sports_area,industry,legal_person,remark) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SQL_DELETE_SCHOOL = "update base_school set is_deleted = 1,event_source=?,modify_time=? where id = ?";

	private static final String SQL_UPDATE_SCHOOL = "UPDATE base_school SET school_district_id=?,school_name=?,"
			+ "english_name=?,short_name=?,school_code=?,address=?,region_code=?,schoolmaster=?,party_master=?,"
			+ "build_date=?,anniversary=?,governor=?,run_school_type=?,school_type=?,region_type=?,region_economy=?,"
			+ "region_nation=?,grade_age=?,junior_age=?,infant_year=?,"
			+ "infant_age=?,primary_lang=?,secondary_lang=?,recruit_region=?,postalcode=?,link_phone=?,fax=?,"
			+ "email=?,homepage=?,organization_code=?,introduction=?,area=?,serial_number=?,sections=?,emphases=?,"
			+ "is_boorish=?,demonstration=?,modify_time=?,is_deleted=?,event_source=?,feature=?, public_assist=?, "
			+" builtup_area=?,green_area=?,sports_area=?,industry=?,legal_person=?,remark=? WHERE id=?";

	private static final String SQL_FIND_CODE_EXISTS_BY_CODE = "SELECT count(school_code) FROM base_school WHERE school_code = ? AND is_deleted = 0";

	private static final String SQL_FIND_SCHOOL_BY_ID = "SELECT * FROM base_school WHERE id=?";

	private static final String SQL_FIND_SCHOOL_BY_UNIONID_NAME = "SELECT s.* FROM base_school s,base_unit u "
			+ "where s.id=u.id AND u.union_code like ? AND s.school_name like ? AND s.is_deleted=0 AND u.unit_class = 2";

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
		school.setGradeage(rs.getInt("grade_age"));
		school.setJuniorage(rs.getInt("junior_age"));
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
		school.setFeature(rs.getString("feature"));
		school.setPublicAssist(rs.getInt("public_assist"));
//		school.setBuiltupArea(rs.getString("builtup_area"));
//		school.setGreenArea(rs.getString("green_area"));
//		school.setSportsAreal(rs.getString("sports_area"));
//		school.setIndustry(rs.getString("industry"));
//		school.setLegalPerson(rs.getString("legal_person"));
//		school.setRemark(rs.getString("remark"));
		return school;
	}

	private SingleRowMapper<BaseSchool> singleRowMapper = new SingleRowMapper<BaseSchool>() {
		public BaseSchool mapRow(ResultSet rs) throws SQLException {
			return setField(rs);
		}
	};

	private MultiRowMapper<BaseSchool> multiRowMapper = new MultiRowMapper<BaseSchool>() {
		public BaseSchool mapRow(ResultSet rs, int rowNum) throws SQLException {
			return setField(rs);
		}
	};

	public void deleteSchool(String schoolId, EventSourceType eventSource) {
		update(SQL_DELETE_SCHOOL, new Object[] { eventSource.getValue(),
				new Date(), schoolId });
	}

	public BaseSchool getBaseSchool(String schoolId) {
		return query(SQL_FIND_SCHOOL_BY_ID, schoolId, singleRowMapper);
	}

	public List<BaseSchool> getUnderlingSchoolsFaintness(String unionCode,
			String name) {
		return query(SQL_FIND_SCHOOL_BY_UNIONID_NAME, new String[] {
				unionCode + "%", "%" + name + "%" }, multiRowMapper);
	}

	public void insertSchool(BaseSchool school) {
		if (StringUtils.isBlank(school.getId())) {
			school.setId(createId());
		}
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
				school.getRegionnation(), school.getGradeage(),
				school.getJuniorage(), school.getInfantyear(),
				school.getInfantage(), school.getPrimarylang(),
				school.getSecondarylang(), school.getRecruitregion(),
				school.getPostalcode(), school.getLinkphone(), school.getFax(),
				school.getEmail(), school.getHomepage(),
				school.getOrganizationcode(), school.getIntroduction(),
				school.getArea(), school.getEtohSchoolId(),
				school.getSections(), school.getEmphases(),
				school.getBoorish(), school.getDemonstration(),
				school.getCreationTime(), school.getModifyTime(),
				school.getIsdeleted() ? 1 : 0, school.getEventSourceValue(),
				school.getFeature(), school.getPublicAssist(),
				school.getBuiltupArea(),school.getGreenArea(),
				school.getSportsAreal(),school.getIndustry(),
				school.getLegalPerson(),school.getRemark()
				}, new int[] {
				Types.CHAR, Types.CHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.CHAR,
				Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.VARCHAR,
				Types.VARCHAR, Types.INTEGER, Types.CHAR, Types.INTEGER,
				Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER,
				Types.INTEGER, Types.INTEGER, Types.CHAR, Types.CHAR,
				Types.VARCHAR, Types.CHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
				Types.INTEGER, Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP,
				Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.INTEGER ,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR
			});
	}

	public boolean isExistSchoolCode(String schid, String schCode) {
		StringBuilder sql = new StringBuilder(SQL_FIND_CODE_EXISTS_BY_CODE);
		Object[] params;
		if (StringUtils.isNotBlank(schid)) {
			sql.append("AND id <> ? ");
			params = new Object[] { schCode, schid };
		} else {
			params = new Object[] { schCode };
		}
		int rtn = queryForInt(sql.toString(), params);
		if (rtn == 0) {
			return false;
		}
		return true;
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
				school.getRegionnation(), school.getGradeage(),
				school.getJuniorage(), school.getInfantyear(),
				school.getInfantage(), school.getPrimarylang(),
				school.getSecondarylang(), school.getRecruitregion(),
				school.getPostalcode(), school.getLinkphone(), school.getFax(),
				school.getEmail(), school.getHomepage(),
				school.getOrganizationcode(), school.getIntroduction(),
				school.getArea(), school.getEtohSchoolId(),
				school.getSections(), school.getEmphases(),
				school.getBoorish(), school.getDemonstration(),
				school.getModifyTime(), school.getIsdeleted() ? 1 : 0,
				school.getEventSourceValue(), school.getFeature(),
				school.getPublicAssist(),
				school.getBuiltupArea(),school.getGreenArea(),
				school.getSportsAreal(),school.getIndustry(),
				school.getLegalPerson(),school.getRemark(),
				 school.getId()
				}, new int[] {
				Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.VARCHAR,
				Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.VARCHAR,
				Types.INTEGER, Types.CHAR, Types.INTEGER, Types.INTEGER,
				Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER,
				Types.INTEGER, Types.CHAR, Types.CHAR, Types.VARCHAR,
				Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.CHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER,
				Types.INTEGER, Types.TIMESTAMP, Types.INTEGER, Types.INTEGER,
				Types.VARCHAR, Types.INTEGER ,Types.VARCHAR,Types.VARCHAR,
				Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
				 Types.CHAR
				});
	}

}
