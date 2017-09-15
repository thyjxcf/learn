package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.data.dao.BaseTeacherDutyDao;
import net.zdsoft.eis.base.data.entity.BaseTeacherDuty;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.client.BaseDao;

import org.apache.commons.lang.StringUtils;

public class BaseTeacherDutyDaoImpl extends BaseDao<BaseTeacherDuty> implements BaseTeacherDutyDao {

    private static final String SQL_INSERT_TEACHER_DUTY = "insert into base_teacher_duty(id,teacher_id,duty_code,event_source,is_deleted)"
            + "values (?,?,?,?,?)";
    private static final String SQL_UPDATE_TEACHER_DUTY = "update base_teacher_duty set teacher_id= ?,duty_code= ?,event_source= ?,is_deleted=? where id=?";

    private static final String SQL_DELETE_TEACHER_DUTY_BY_TEACHER_IDS = "update base_teacher_duty set is_deleted=1,event_source= ? where teacher_id IN ";

    private static final String SQL_DELETE_TEACHER_DUTY_BY_IDS = "update base_teacher_duty set is_deleted=1,event_source= ? where id IN ";
    
    private static final String SQL_FIND_TEACHER_DUTY_LIST_BY_TEACHER_ID = "select * from base_teacher_duty where teacher_id =? and is_deleted =0";

    private static final String SQL_FIND_TEACHER_DUTY_LISt_BY_TEACHER_IDS = "select * from base_teacher_duty where is_deleted =0 and teacher_id IN";

    @Override
    public BaseTeacherDuty setField(ResultSet rs) throws SQLException {
        BaseTeacherDuty duty = new BaseTeacherDuty();
        duty.setId(rs.getString("id"));
        duty.setTeacherId(rs.getString("teacher_id"));
        duty.setDutyCode(rs.getString("duty_code"));
        return duty;
    }

    @Override
    public void insertTeacherDuty(BaseTeacherDuty duty) {
        if (StringUtils.isBlank(duty.getId()))
            duty.setId(createId());
        duty.setIsdeleted(false);
        update(SQL_INSERT_TEACHER_DUTY, new Object[] { duty.getId(), duty.getTeacherId(),
                duty.getDutyCode(), duty.getEventSourceValue(), duty.getIsdeleted()?1:0 });
    }

    @Override
    public void updateTeacherDuty(BaseTeacherDuty duty) {
        update(SQL_UPDATE_TEACHER_DUTY, new Object[] { duty.getTeacherId(), duty.getDutyCode(),
                duty.getEventSourceValue(), duty.getIsdeleted()?1:0, duty.getId() }, new int[] {
                Types.CHAR, Types.CHAR, Types.INTEGER, Types.INTEGER, Types.CHAR });
    }

    @Override
    public void insertTeacherDuties(List<BaseTeacherDuty> dutyList) {
        List<Object[]> listOfArgs = new ArrayList<Object[]>();
        for (int i = 0; i < dutyList.size(); i++) {
            BaseTeacherDuty duty = dutyList.get(i);
            if (StringUtils.isBlank(duty.getId()))
                duty.setId(getGUID());
            duty.setIsdeleted(false);
            listOfArgs.add(new Object[] { duty.getId(), duty.getTeacherId(), duty.getDutyCode(),
                    duty.getEventSourceValue(), duty.getIsdeleted()?1:0 });
        }
        int[] argTypes = new int[] { Types.CHAR, Types.CHAR, Types.CHAR, Types.INTEGER,
                Types.INTEGER };
        batchUpdate(SQL_INSERT_TEACHER_DUTY, listOfArgs, argTypes);
    }

    @Override
    public void updateTeacherDuties(List<BaseTeacherDuty> dutyList) {
        List<Object[]> listOfArgs = new ArrayList<Object[]>();
        for (int i = 0; i < dutyList.size(); i++) {
            BaseTeacherDuty duty = dutyList.get(i);
            listOfArgs.add(new Object[] { duty.getTeacherId(), duty.getDutyCode(),
                    duty.getEventSourceValue(), duty.getIsdeleted()?1:0, duty.getId() });
        }
        int[] argTypes = new int[] { Types.CHAR, Types.CHAR, Types.INTEGER, Types.INTEGER,
                Types.CHAR };
        batchUpdate(SQL_UPDATE_TEACHER_DUTY, listOfArgs, argTypes);
    }

    @Override
    public void deleteTeacherDutiesByTeacherIds(String[] teacherIds, EventSourceType eventSource) {
        updateForInSQL(SQL_DELETE_TEACHER_DUTY_BY_TEACHER_IDS, new Object[] { eventSource
                .getValue() }, teacherIds);
    }
    

    public void deleteTeacherDutiesByIds(String[] ids, EventSourceType eventSource) {
        updateForInSQL(SQL_DELETE_TEACHER_DUTY_BY_IDS, new Object[] { eventSource
                .getValue() }, ids);
    }
    
    

    @Override
    public List<BaseTeacherDuty> getTeacherDutyListByTeacherId(String teacherId) {
        return query(SQL_FIND_TEACHER_DUTY_LIST_BY_TEACHER_ID, new Object[] { teacherId },
                new MultiRow());
    }

    @Override
    public List<BaseTeacherDuty> getTeacherDutyListByTeacherIds(String[] teacherIds) {
        return queryForInSQL(SQL_FIND_TEACHER_DUTY_LISt_BY_TEACHER_IDS, new Object[] {},
                teacherIds, new MultiRow());
    }

}
