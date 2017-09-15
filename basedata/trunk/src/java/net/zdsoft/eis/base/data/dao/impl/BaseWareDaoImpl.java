/* 
 * @(#)BaseWareDaoImpl.java    Created on Dec 20, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.Ware;
import net.zdsoft.eis.base.data.dao.BaseWareDao;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 20, 2010 4:09:51 PM $
 */
public class BaseWareDaoImpl extends BaseDao<Ware> implements BaseWareDao {

    private static final String SQL_INSERT_WARE = "INSERT INTO base_ware(id,code,name,"
            + "ware_fee,state,server_id,server_type_id,subscriber_type,nums,order_type,"
            + "unit_class,role,experience_month,is_fee,teacher_rule,student_rule,family_rule,"
            + "admin_rule,server_code,event_source,is_deleted) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SQL_DELETE_WARE_BY_IDS = "UPDATE base_ware SET is_deleted = 1,event_source=? WHERE id IN ";

    private static final String SQL_UPDATE_WARE = "UPDATE base_ware SET code=?,name=?,"
            + "ware_fee=?,state=?,server_id=?,server_type_id=?,subscriber_type=?,nums=?,order_type=?,"
            + "unit_class=?,role=?,experience_month=?,is_fee=?,teacher_rule=?,student_rule=?,family_rule=?,"
            + "admin_rule=?,server_code=?,event_source=?,is_deleted=? WHERE id=?";

    private static final String SQL_FIND_SERVERIDS = "SELECT distinct server_id FROM base_ware WHERE unit_class like ? AND state=? AND is_deleted=0 ";

    @Override
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

    public void insertWare(Ware ware) {
        if (StringUtils.isEmpty(ware.getId())) {
            ware.setId(createId());
        }
        ware.setCreationTime(new Date());
        update(SQL_INSERT_WARE, new Object[] { ware.getId(), ware.getCode(), ware.getName(),
                ware.getWareFee(), ware.getState(), ware.getServerId(), ware.getServerTypeId(),
                ware.getSubscriberType(), ware.getNums(), ware.getOrderType(), ware.getUnitClass(),
                ware.getRole(), ware.getExperienceMonth(), ware.getIsFee(), ware.getTeacherRule(),
                ware.getStudentRule(), ware.getFamilyRule(), ware.getAdminRule(),
                ware.getServerCode(), ware.getEventSourceValue(), ware.getIsdeleted() ? 1 : 0 },
                new int[] { Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER,
                        Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER,
                        Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER,
                        Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.INTEGER,
                        Types.INTEGER });
    }

    public void deleteWare(String[] wareIds, EventSourceType eventSource) {
        updateForInSQL(SQL_DELETE_WARE_BY_IDS, new Object[] { eventSource.getValue() }, wareIds);
    }

    public void updateWare(Ware ware) {
        update(SQL_UPDATE_WARE, new Object[] { ware.getCode(), ware.getName(), ware.getWareFee(),
                ware.getState(), ware.getServerId(), ware.getServerTypeId(),
                ware.getSubscriberType(), ware.getNums(), ware.getOrderType(), ware.getUnitClass(),
                ware.getRole(), ware.getExperienceMonth(), ware.getIsFee(), ware.getTeacherRule(),
                ware.getStudentRule(), ware.getFamilyRule(), ware.getAdminRule(),
                ware.getServerCode(), ware.getEventSourceValue(), ware.getIsdeleted() ? 1 : 0,
                ware.getId() }, new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
                Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER,
                Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER,
                Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR,
                Types.INTEGER, Types.INTEGER, Types.CHAR });
    }

    public Set<String> getServerIds(int unitClass, int roleType, int state, int rule) {
        String ruleField = "teacher_rule";
//        int role = roleType;
        switch (roleType) {
        case Ware.ROLE_TYPE_STUDENT:
            ruleField = "student_rule";
            break;
        case Ware.ROLE_TYPE_TEACHER:
            ruleField = "teacher_rule";

            // 如果是教育局，则role为教育局职工
            if (Unit.UNIT_CLASS_EDU == unitClass) {
//                role = Ware.ROLE_TYPE_EMPLOYEE;
            }
            break;
        case Ware.ROLE_TYPE_FAMILY:
            ruleField = "family_rule";
            break;
        case Ware.ROLE_TYPE_ADMIN:
            ruleField = "admin_rule";
            break;
        default:
            break;
        }

        String cond = "AND " + ruleField + "=" + String.valueOf(rule);
        List<String> list = query(SQL_FIND_SERVERIDS + cond, new Object[] {
                "%" + String.valueOf(unitClass) + "%", Integer.valueOf(state) }, new int[] {
                Types.VARCHAR, Types.INTEGER }, new MultiRowMapper<String>() {

            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("server_id");
            }
        });
        Set<String> serverIds = new HashSet<String>();
        serverIds.addAll(list);
        return serverIds;
    }
}
