package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.SchoolDao;
import net.zdsoft.eis.base.common.entity.School;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.dao.SingleRowMapper;
import net.zdsoft.keel.util.Pagination;

public class SchoolDaoImpl extends BaseDao<School> implements SchoolDao {

	private static final String FIND_SCH_PREFIX = "SELECT * FROM base_school ";

	private static final String SQL_FIND_SCHOOL_BY_ID = FIND_SCH_PREFIX
			+ " WHERE id=?";
	private static final String SQL_FIND_SCHOOL_BY_CODE = FIND_SCH_PREFIX
			+ " WHERE school_code= ? and is_deleted = 0";

	private static final String SQL_FIND_SCHOOLS_BY_IDS = FIND_SCH_PREFIX
			+ " WHERE is_deleted = 0 and id IN";
	private static final String SQL_FIND_SCHOOL_BY_NAMES = FIND_SCH_PREFIX
			+ " where is_deleted = 0 and school_name in ";

	private static final String SQL_FIND_SCHOOL = FIND_SCH_PREFIX
			+ " WHERE is_deleted = 0";
	private static final String SQL_FIND_SCHOOL_TYPE = "SELECT id,school_type FROM base_school WHERE is_deleted = 0";

	private static final String SQL_FIND_SCHOOL_BY_DISTRICTID = FIND_SCH_PREFIX
			+ " WHERE is_deleted = 0 AND school_district_id=?";

	private static final String SQL_FIND_SCHOOL_BY_PARENTID_TYPE_NAME_SECTION = "SELECT s.id "
			+ "FROM base_school s,base_unit u "
			+ "WHERE s.id = u.id AND u.parent_id = ? AND u.is_deleted = 0 AND NVL(s.run_school_type,'') like ?"
			+ " AND s.school_name like ? AND sections like ? AND s.is_deleted = 0 ";

	private static final String SQL_FIND_NAME_BY_PARENTID_SECTION_NAME = "SELECT s.id,s.school_name FROM base_school s,base_unit u"
			+ " WHERE s.id = u.id AND u.parent_id=? AND u.is_deleted = 0 AND nvl(s.sections,'') like ? "
			+ " AND s.school_name like ? AND s.is_deleted = 0 ORDER BY u.display_order";

	private static final String SQL_FIND_NAME_BY_UNIONID_SECTION_NAME = "SELECT s.id,s.school_name FROM base_school s,base_unit u "
			+ "WHERE s.id = u.id AND u.union_code like ? AND u.is_deleted = 0 AND NVL(s.sections,'') like ? "
			+ " AND s.school_name like ? AND s.is_deleted = 0 ORDER BY u.display_order";

	private static final String SQL_FIND_SECTION_BY_SCHOOLTYPE = "select section from base_schtype_section where school_type=?";

	// private static final String SQL_FIND_SCHOOLS_BY_REGIONTYPE_SECTIONS =
	// "SELECT * FROM base_school WHERE sections like ? AND is_deleted = 0 AND region_type in";
	private static final String SQL_FIND_SCHOOLS_BY_REGIONTYPE_SECTIONS = "select s.* from base_unit u, base_school s "
			+ "where u.id = s.id and u.is_deleted = 0 and s.is_deleted = 0  "
			+ "and u.parent_id=? and region_type =? and NVL(s.sections,'') like ? ORDER BY u.display_order";

	public School setField(ResultSet rs) throws SQLException {
		School school = new School();
		school.setId(rs.getString("id"));
		school.setName(rs.getString("school_name"));
		school.setEnglishname(rs.getString("english_name"));
		school.setShortName(rs.getString("short_name"));
		school.setCode(rs.getString("school_code"));
		school.setAddress(rs.getString("address"));
		school.setRegion(rs.getString("region_code"));
		school.setShoolmaster(rs.getString("schoolmaster"));
		school.setPartymaster(rs.getString("party_master"));
		school.setAnniversary(rs.getString("anniversary"));
		school.setRunschtype(rs.getString("run_school_type"));
		school.setType(rs.getString("school_type"));
		school.setRegiontype(rs.getString("region_type"));
		school.setRegioneconomy(rs.getString("region_economy"));
		school.setRegionnation(rs.getString("region_nation"));
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
		school.setOrganizationcode(rs.getString("organization_code"));
		school.setIntroduction(rs.getString("introduction"));
		school.setArea(rs.getString("area"));
		school.setDatecreated(rs.getTimestamp("build_date"));
		school.setEtohSchoolId(rs.getString("serial_number"));
		school.setSections(rs.getString("sections"));
		school.setCreationTime(rs.getTimestamp("creation_time"));
		school.setModifyTime(rs.getTimestamp("modify_time"));
		school.setIsdeleted(rs.getBoolean("is_deleted"));
		school.setSchdistrictid(rs.getString("school_district_id"));
		school.setGovernor(rs.getString("governor"));
		school.setPostalcode(rs.getString("postalcode"));
		school.setLinkphone(rs.getString("link_phone"));
		school.setFax(rs.getString("fax"));
		school.setEmail(rs.getString("email"));
		school.setHomepage(rs.getString("homepage"));
		school.setEmphases(rs.getString("emphases"));
		school.setBoorish(rs.getInt("is_boorish"));
		school.setDemonstration(rs.getString("demonstration"));
		school.setFeature(rs.getString("feature"));
		school.setPublicAssist(rs.getInt("public_assist"));
		school.setSchoolPropStat(rs.getString("school_prop_stat"));
		school.setSchoolTypeGroup(rs.getString("school_type_group"));
		school.setLedgerSchoolType(rs.getString("ledger_school_type"));
		school.setRegionManageDept(rs.getString("region_manage_dept"));
		school.setRegionManageDeptCode(rs.getString("region_manage_dept_code"));
		school.setAddressCode(rs.getString("address_code"));
		school.setLegaRegistrationNumber(rs
				.getString("lega_registration_number"));
		school.setLandCertificateNo(rs.getString("land_certificate_no"));
		school.setLongitude(rs.getDouble("longitude"));
		school.setLatitude(rs.getDouble("latitude"));
		school.setFillEmail(rs.getString("fill_email"));
		school.setStatStuffName(rs.getString("stat_stuff_name"));
		school.setStatStuffContact(rs.getString("stat_stuff_contact"));
		school.setSchDistrictChief(rs.getBoolean("is_sch_district_chief"));
		school.setStatRegionCode(rs.getString("stat_region_code"));
		school.setKgLevel(rs.getString("kg_level"));
		school.setMobilePhone(rs.getString("mobile_phone"));
		school.setRegionPropertyCode(rs.getString("region_property_code"));
		school.setNatureType(rs.getString("nature_type"));
		school.setMinoritySchool(rs.getBoolean("is_minority_school"));
		school.setSchoolSetupName(rs.getString("school_setup_name"));
		school.setSpecialRegionCode(rs.getString("special_region_code"));
		school.setSchoolType(rs.getString("school_type"));
		return school;
	}

	public School getSchool(String schoolId) {
		return query(SQL_FIND_SCHOOL_BY_ID, schoolId, new SingleRow());
	}

	public School getSchoolByCode(String code) {
		return query(SQL_FIND_SCHOOL_BY_CODE, new Object[] { code },
				new SingleRow());
	}

	public int getSchoolingLen(String schoolId, int section) {
		if (section == 0)
			return 0;
		// 特殊处理（杭外）
		if (section == 9)
			section = 3;
		String[] sections = { "", "grade_year", "junior_year", "senior_year" };
		String sql = "SELECT " + sections[section]
				+ " FROM base_school WHERE id = ? AND is_deleted=0";
		return queryForInt(sql, new Object[] { schoolId });
	}

	public List<School> getSchools(String[] schoolIds) {
		return queryForInSQL(SQL_FIND_SCHOOLS_BY_IDS, null, schoolIds,
				new MultiRow());
	}

	public List<String> getSchoolIds(String parentId, String runschtype,
			String schoolName, int section) {
		return query(SQL_FIND_SCHOOL_BY_PARENTID_TYPE_NAME_SECTION,
				new String[] { parentId, runschtype, schoolName,
						"%" + section + "%" }, new MultiRowMapper<String>() {

					public String mapRow(ResultSet rs, int numRow)
							throws SQLException {
						return rs.getString("id");
					}
				});
	}

	public List<School> getUnderlingSchools(String districtId) {
		return query(SQL_FIND_SCHOOL_BY_DISTRICTID,
				new String[] { districtId }, new MultiRow());
	}

	public List<School> getUnderlingSchoolsFaintness(String parentId,
			int section, String schoolName) {
		return query(SQL_FIND_NAME_BY_PARENTID_SECTION_NAME, new Object[] {
				parentId, "%" + section + "%", "%" + schoolName + "%" },
				new MultiRowMapper<School>() {

					public School mapRow(ResultSet rs, int numRow)
							throws SQLException {
						School school = new School();
						school.setId(rs.getString("id"));
						school.setName(rs.getString("school_name"));
						return school;
					}
				});
	}

	public List<School> getAllSchoolsFaintness(String unionCode, int section,
			String schoolName) {
		return query(SQL_FIND_NAME_BY_UNIONID_SECTION_NAME, new Object[] {
				unionCode + "%", "%" + section + "%", "%" + schoolName + "%" },
				new MultiRowMapper<School>() {

					public School mapRow(ResultSet rs, int numRow)
							throws SQLException {
						School school = new School();
						school.setId(rs.getString("id"));
						school.setName(rs.getString("school_name"));
						return school;
					}
				});
	}

	public Map<String, String> getSchoolTypeMap() {
		return queryForMap(SQL_FIND_SCHOOL_TYPE,
				new MapRowMapper<String, String>() {
					public String mapRowKey(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getString("id");
					}

					public String mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getString("school_type");
					}
				});
	}

	public Map<String, School> getSchoolMap() {
		return queryForMap(SQL_FIND_SCHOOL, new MapRowMapper<String, School>() {
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("id");
			}

			public School mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}

	@Override
	public Map<String, School> getSchoolsById(String[] schoolIds) {
		String sql = "SELECT * FROM base_school WHERE is_deleted = 0 and id IN";
		return queryForInSQL(sql, null, schoolIds,
				new MapRowMapper<String, School>() {

					@Override
					public String mapRowKey(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getString("id");
					}

					@Override
					public School mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						return setField(rs);
					}
				});
	}

	public Map<String, School> getSchoolMapByNames(String[] names) {
		return queryForInSQL(SQL_FIND_SCHOOL_BY_NAMES, null, names,
				new MapRowMapper<String, School>() {
					public String mapRowKey(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getString("school_name");
					}

					public School mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						return setField(rs);
					}
				});
	}

	public String getSections(String schoolType) {
		return query(SQL_FIND_SECTION_BY_SCHOOLTYPE, schoolType,
				new SingleRowMapper<String>() {

					public String mapRow(ResultSet rs) throws SQLException {
						return rs.getString("section");
					}
				});
	}

	public List<School> getBaseSchoolsByRegiontypeSections(String parentid,
			String regiontype, String section, Pagination page) {
		// return queryForInSQL(SQL_FIND_SCHOOLS_BY_REGIONTYPE_SECTIONS, new
		// String[]{parentid,"%"+section+"%"}, regiontype, new MultiRow()
		// ,null, page);
		return query(SQL_FIND_SCHOOLS_BY_REGIONTYPE_SECTIONS, new Object[] {
				parentid, regiontype, "%" + section + "%" }, new MultiRow(),
				page);
	}

	@Override
	public School getSchoolBy10Code(String code) {
		String sql = FIND_SCH_PREFIX
				+ " WHERE substr(school_code,0,10)= ? and is_deleted = 0";
		return query(sql, new Object[] { code }, new SingleRow());
	}
}
