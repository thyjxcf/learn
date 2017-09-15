package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.base.common.dao.SchoolDistrictDao;
import net.zdsoft.eis.base.common.entity.SchoolDistrict;
import net.zdsoft.eis.frame.client.BaseDao;

/**
 * <p>
 * 项目：学籍二期(stusys)
 * 
 * 学区基本信息--数据库访问实现类
 * 
 * @author Kobe Su,2007-5-17
 */
public class SchoolDistrictDaoImpl extends BaseDao<SchoolDistrict> implements SchoolDistrictDao {

    private static final String SQL_FIND_JWSCHDISTRICT_BY_ID = "SELECT * FROM jw_basic_school_district WHERE schdistrictid=?";

    private static final String SQL_FIND_JWSCHDISTRICT_BY_EDUID = "SELECT * FROM jw_basic_school_district WHERE"
            + " eduid=? AND isdeleted='0' ORDER BY schdistri_code";

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

    public SchoolDistrict getSchoolDistrict(String districtId) {
        return query(SQL_FIND_JWSCHDISTRICT_BY_ID, districtId, new SingleRow());
    }

    public List<SchoolDistrict> getSchoolDistricts(String eduId) {
        return query(SQL_FIND_JWSCHDISTRICT_BY_EDUID, new Object[] { eduId }, new MultiRow());
    }

}
