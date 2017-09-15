package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.WareDao;
import net.zdsoft.eis.base.common.entity.Ware;
import net.zdsoft.eis.frame.client.BaseDao;

public class WareDaoImpl extends BaseDao<Ware> implements WareDao {

    private static final String SQL_FIND_SUWARES_BY_IDS = "SELECT * FROM base_ware WHERE is_deleted=0 AND id IN";

    public Ware setField(ResultSet rs) throws SQLException {
        Ware ware = new Ware();
        ware.setId(rs.getString("id"));
        ware.setCode(rs.getString("code"));
        ware.setName(rs.getString("name"));
        ware.setWareFee(rs.getInt("ware_fee"));
        ware.setState(rs.getInt("state"));
        ware.setServerId(rs.getInt("server_id"));
        ware.setServerTypeId(rs.getInt("server_type_id"));
        ware.setSubscriberType(rs.getInt("subscriber_type"));
        ware.setNums(rs.getInt("nums"));
        ware.setOrderType(rs.getInt("order_type"));
        ware.setUnitClass(rs.getString("unit_class"));
        ware.setRole(rs.getString("role"));
        ware.setExperienceMonth(rs.getInt("experience_month"));
        ware.setIsFee(rs.getInt("is_fee"));
        ware.setTeacherRule(rs.getInt("teacher_rule"));
        ware.setStudentRule(rs.getInt("student_rule"));
        ware.setFamilyRule(rs.getInt("family_rule"));
        ware.setAdminRule(rs.getInt("admin_rule"));
        ware.setServerCode(rs.getString("server_code"));
        return ware;
    }

    public Map<String, Ware> getWares(String[] wareIds) {
        return queryForInSQL(SQL_FIND_SUWARES_BY_IDS, null, wareIds, new MapRow());
    }

}
