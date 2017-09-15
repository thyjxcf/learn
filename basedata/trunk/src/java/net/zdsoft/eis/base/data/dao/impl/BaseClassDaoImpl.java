package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.data.dao.BaseClassDao;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.SingleRowMapper;

/**
 * @author yanb
 * 
 */
public class BaseClassDaoImpl extends BaseDao<BasicClass> implements BaseClassDao {

    private static final String SQL_INSERT_BASECLASS = "INSERT INTO base_class(id,school_id,class_code,"
            + "class_name,section,acadyear,honor,build_date,class_type,art_science_type,"
            + "is_graduate,graduate_date,schooling_length,teacher_id,student_id,campus_id,grade_id,"
            + "vice_teacher_id,display_order,creation_time,modify_time,is_deleted,event_source, teach_place_id) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SQL_DELETE_BASECLASS_BY_ID = "UPDATE base_class SET is_deleted = 1,modify_time = ?,event_source=? WHERE id =? ";

    private static final String SQL_UPDATE_BASECLASS = "UPDATE base_class SET school_id=?,class_code=?,"
            + "class_name=?,section=?,acadyear=?,honor=?,build_date=?,class_type=?,art_science_type=?,"
            + "is_graduate=?,graduate_date=?,schooling_length=?,teacher_id=?,student_id=?,campus_id=?,grade_id=?,"
            + "vice_teacher_id=?,display_order=?,modify_time=?,is_deleted=?,event_source=?, teach_place_id=? WHERE id=?";

    private static final String SQL_UPDATE_GRADEID_BY_SCHLEN = "UPDATE base_class SET grade_id = ?,modify_time = ?,event_source=? "
            + "WHERE school_id = ? AND acadyear = ? AND section = ? AND schooling_length = ?";

    private static final String SQL_UPDATE_GRADEID = "UPDATE base_class SET grade_id = ?,modify_time = ?,event_source=? WHERE school_id = ? "
            + "AND acadyear = ? AND section =? ";

    private static final String SQL_IS_EXIST_BASECLASSS_BY_CODE = "SELECT * FROM base_class "
            + "WHERE school_id=? AND class_code=? AND is_deleted = 0";

    private static final String SQL_IS_EXIST_BASECLASSS_BY_NAME = "SELECT * FROM base_class "
            + "WHERE school_id=? AND section=? AND acadyear=? AND class_name=? AND is_deleted = 0";

    private static final String SQL_FIND_MAX_ORDER = "SELECT max(display_order) FROM base_class "
            + "WHERE school_id=? AND acadyear=? AND section=? AND schooling_length=? AND is_deleted = 0 ";

    private static final String SQL_FIND_MAX_CLSCODE_BY_SCHID = "SELECT * FROM base_class "
            + "WHERE school_id=? AND section=? AND acadyear=? AND is_deleted = 0 ORDER BY class_code DESC";

    private static final String SQL_EXISTS_CLASS_BY_SCHOOLID_ENROLLYEAR = "SELECT count(*) FROM base_class "
        + "WHERE school_id=? AND acadyear = ? and is_deleted = 0";

    private static final String SQL_FIND_EXISTS_CLASS_BY_SCHID = "SELECT count(*) FROM base_class "
            + "WHERE school_id=? AND is_deleted = 0 AND is_graduate=0 ";
    
    private static final String SQL_FIND_EXISTS_CLASS_BY_SCHID_SECTION = "SELECT count(*) FROM base_class "
            + "WHERE school_id=? AND section=? AND is_deleted = 0 AND is_graduate=0 ";
    
    private static final String SQL_FIND_MAX_CLSCODE = "SELECT max(class_code) FROM base_class "
            + "WHERE school_id = ? AND is_deleted = 0 AND class_code like ? AND length(class_code) = ? AND regexp_like(class_code,'^[0-9]+[0-9]$')";


    @Override
    public BasicClass setField(ResultSet rs) throws SQLException {
        BasicClass baseClass = new BasicClass();
        baseClass.setId(rs.getString("id"));
        baseClass.setSchid(rs.getString("school_id"));
        baseClass.setClasscode(rs.getString("class_code"));
        baseClass.setClassname(rs.getString("class_name"));
        baseClass.setSection(rs.getInt("section"));
        baseClass.setAcadyear(rs.getString("acadyear"));
        baseClass.setHonor(rs.getString("honor"));
        baseClass.setDatecreated(rs.getTimestamp("build_date"));
        baseClass.setClasstype(rs.getString("class_type"));
        baseClass.setArtsciencetype(rs.getInt("art_science_type"));
        baseClass.setGraduatesign(rs.getInt("is_graduate"));
        baseClass.setGraduatedate(rs.getTimestamp("graduate_date"));
        baseClass.setSchoolinglen(rs.getInt("schooling_length"));
        baseClass.setTeacherid(rs.getString("teacher_id"));
        baseClass.setStuid(rs.getString("student_id"));
        baseClass.setSubschoolid(rs.getString("campus_id"));
        baseClass.setGradeId(rs.getString("grade_id"));
        baseClass.setViceTeacherId(rs.getString("vice_teacher_id"));
        baseClass.setDisplayOrder(rs.getInt("display_order"));
        baseClass.setCreationTime(rs.getTimestamp("creation_time"));
        baseClass.setModifyTime(rs.getTimestamp("modify_time"));
        baseClass.setTeachPlaceId(rs.getString("teach_place_id"));
        return baseClass;
    }

    public void insertClass(BasicClass baseClass) {
        if (StringUtils.isBlank(baseClass.getId()))
            baseClass.setId(createId());
        baseClass.setCreationTime(new Date());
        baseClass.setModifyTime(new Date());
        baseClass.setIsdeleted(false);
        update(SQL_INSERT_BASECLASS, new Object[] { baseClass.getId(), baseClass.getSchid(),
                baseClass.getClasscode(), baseClass.getClassname(), baseClass.getSection(),
                baseClass.getAcadyear(), baseClass.getHonor(), baseClass.getDatecreated(),
                baseClass.getClasstype(), baseClass.getArtsciencetype(),
                baseClass.getGraduatesign(), baseClass.getGraduatedate(),
                baseClass.getSchoolinglen(), baseClass.getTeacherid(), baseClass.getStuid(),
                baseClass.getSubschoolid(), baseClass.getGradeId(), baseClass.getViceTeacherId(),
                baseClass.getDisplayOrder(), baseClass.getCreationTime(),
                baseClass.getModifyTime(), baseClass.getIsdeleted(),
                baseClass.getEventSourceValue(), baseClass.getTeachPlaceId() }, new int[] { Types.CHAR, Types.CHAR,
                Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.CHAR, Types.VARCHAR, Types.DATE,
                Types.CHAR, Types.INTEGER, Types.INTEGER, Types.DATE, Types.INTEGER, Types.CHAR,
                Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR, Types.INTEGER, Types.TIMESTAMP,
                Types.TIMESTAMP, Types.INTEGER, Types.INTEGER, Types.CHAR });
    }

    public void updateClass(BasicClass baseClass) {
        baseClass.setModifyTime(new Date());
        baseClass.setIsdeleted(false);
        update(SQL_UPDATE_BASECLASS, new Object[] { baseClass.getSchid(), baseClass.getClasscode(),
                baseClass.getClassname(), baseClass.getSection(), baseClass.getAcadyear(),
                baseClass.getHonor(), baseClass.getDatecreated(), baseClass.getClasstype(),
                baseClass.getArtsciencetype(), baseClass.getGraduatesign(),
                baseClass.getGraduatedate(), baseClass.getSchoolinglen(), baseClass.getTeacherid(),
                baseClass.getStuid(), baseClass.getSubschoolid(), baseClass.getGradeId(),
                baseClass.getViceTeacherId(), baseClass.getDisplayOrder(),
                baseClass.getModifyTime(), baseClass.getIsdeleted(),
                baseClass.getEventSourceValue(), baseClass.getTeachPlaceId(), baseClass.getId() }, new int[] { Types.CHAR,
                Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.CHAR, Types.VARCHAR, Types.DATE,
                Types.CHAR, Types.INTEGER, Types.INTEGER, Types.DATE, Types.INTEGER, Types.CHAR,
                Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR, Types.INTEGER, Types.TIMESTAMP,
                Types.INTEGER, Types.INTEGER, Types.CHAR, Types.CHAR });
    }

    public void deleteClass(String classId, EventSourceType eventSource) {
        update(SQL_DELETE_BASECLASS_BY_ID, new Object[] { new Date(), eventSource.getValue(), classId });
    }

    public void updateGradeId(String schoolId, String gradeId, String acadyear, int section,
            String schoolingLength) {
        if (schoolingLength != null) {
            update(SQL_UPDATE_GRADEID_BY_SCHLEN, new Object[] { gradeId, new Date(),
                    EventSourceType.LOCAL.getValue(), schoolId, acadyear, Integer.valueOf(section),
                    Integer.valueOf(schoolingLength) });
        }
        else {
            update(SQL_UPDATE_GRADEID, new Object[] { gradeId, new Date(),
                    EventSourceType.LOCAL.getValue(), schoolId, acadyear, section });
        }
    }

    public boolean isExistsClassCode(String schoolId, String classCode) {
        boolean flag = false;
        List<BasicClass> list = query(SQL_IS_EXIST_BASECLASSS_BY_CODE, new Object[] { schoolId,
                classCode }, new MultiRow());
        if (list != null && list.size() > 0) {
            flag = true;
        }

        return flag;
    }

    public boolean isExistsClassName(String schoolId, int section, String acadyear, String className) {
        boolean flag = false;
        List<BasicClass> list = query(SQL_IS_EXIST_BASECLASSS_BY_NAME, new Object[] { schoolId,
                section, acadyear, className }, new MultiRow());
        if (list != null && list.size() > 0) {
            flag = true;
        }

        return flag;
    }

    public int getMaxOrder(String schoolId, String acadyear, int section, int schoolingLength) {
        int num = queryForInt(SQL_FIND_MAX_ORDER, new Object[] { schoolId, acadyear, section,
                schoolingLength });
        return num;
    }

    public String getMaxClassCode(String schid, int section, String acadyear) {
        List<BasicClass> bcList = query(SQL_FIND_MAX_CLSCODE_BY_SCHID, new Object[] { schid,
                section, acadyear }, new MultiRow());

        if ((bcList != null) && (bcList.size() > 0)) {
            BasicClass BaseClass = (BasicClass) bcList.get(0);

            return BaseClass.getClasscode();
        } else {
            return null;
        }
    }    
    
    public boolean isExistsClass(String schoolId, String enrollyear) {
        return queryForInt(SQL_EXISTS_CLASS_BY_SCHOOLID_ENROLLYEAR,
                new String[] { schoolId, enrollyear }) > 0 ? true : false;
    }

    public boolean isExistsClass(String schoolId) {
        return queryForInt(SQL_FIND_EXISTS_CLASS_BY_SCHID,
                new Object[] { schoolId }) > 0 ? true : false;
    }

    public boolean isExistsClass(String schoolId, int section) {
        return queryForInt(SQL_FIND_EXISTS_CLASS_BY_SCHID_SECTION,
                new Object[] { schoolId, section }) > 0 ? true : false;
    }
    
    @Override
    public String getMaxClassCodeByPrefix(String schoolId, String prefix, int length) {
        return query(SQL_FIND_MAX_CLSCODE, new Object[] {schoolId, prefix + "%", length}, new SingleRowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs) throws SQLException {
                return rs.getString(1);
            }
        });
    }
}
