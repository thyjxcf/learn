/**
 * 
 */
package net.zdsoft.eis.base.data.sync.converter;

import com.winupon.syncdata.basedata.entity.son.MqClass;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.sync.SyncObjectConvertable;

/**
 * @author yanb
 *
 */
public class ClassConverter implements SyncObjectConvertable<BasicClass, MqClass> {

    @Override
    public void toEntity(MqClass m, BasicClass e) {
        e.setSchid(m.getSchoolId());
        e.setClasscode(m.getClassCode());
        e.setClassname(m.getClassName());
        e.setAcadyear(m.getAcadyear());
        e.setSection(m.getSection());
        e.setHonor(m.getHonor());
        e.setDatecreated(m.getBuildDate());
        e.setClasstype(m.getClassType());
        e.setArtsciencetype(m.getArtScienceType());
        e.setGraduatesign(m.isGraduate() ? 1 : 0);
        e.setGraduatedate(m.getGraduateDate());
        e.setSchoolinglen(m.getSchoolingLength());
        e.setTeacherid(m.getTeacherId());
        e.setStuid(m.getStudentId());
//        e.setSubschoolid("");
        e.setGradeId(m.getGradeId());
        e.setViceTeacherId(m.getViceTeacherId());
        e.setDisplayOrder(m.getDisplayOrder());
        e.setId(m.getId());
    }

    @Override
    public void toMq(BasicClass e, MqClass m) {
        m.setSchoolId(e.getSchid());
        m.setClassCode(e.getClasscode());
        m.setClassName(e.getClassname());
        m.setAcadyear(e.getAcadyear());
        m.setGraduate(0 == e.getGraduatesign() ? false : Boolean.parseBoolean(String.valueOf(e
                .getGraduatesign())));
        m.setSchoolingLength(e.getSchoolinglen());
        m.setGradeId(e.getGradeId());
        m.setSection(e.getSection());
        m.setHonor(e.getHonor());
        m.setClassType(e.getClasstype());
        m.setArtScienceType(e.getArtsciencetype());
        m.setGraduateDate(e.getGraduatedate());
        m.setTeacherId(e.getTeacherid());
        m.setStudentId(e.getStuid());
        m.setViceTeacherId(e.getViceTeacherId());
        m.setDisplayOrder(e.getDisplayOrder());
        m.setId(e.getId());
    }

    public static void main(String[] args) {
        net.zdsoft.leadin.tmptool.DtoAssemblerTmp.printSetCode("e", "m", MqClass.class, true);
    }

}
