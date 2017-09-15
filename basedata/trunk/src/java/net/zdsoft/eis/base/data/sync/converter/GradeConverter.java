/**
 * 
 */
package net.zdsoft.eis.base.data.sync.converter;

import com.winupon.syncdata.basedata.entity.son.MqGrade;

import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.sync.SyncObjectConvertable;

/**
 * @author yanb
 * 
 */
public class GradeConverter implements SyncObjectConvertable<Grade, MqGrade> {

    @Override
    public void toEntity(MqGrade m, Grade e) {
        e.setGradename(m.getGradeName());
        e.setSchid(m.getSchoolId());
        e.setTeacherId(m.getTeacherId());
        e.setAmLessonCount(m.getAmLessonCount());
        e.setPmLessonCount(m.getPmLessonCount());
        e.setNightLessonCount(m.getNightLessonCount());
        e.setSchoolinglen(m.getSchoolingLength());
        e.setSection(m.getSection());
        e.setIsGraduated(m.isGraduate() ? 1 : 0);
        e.setDisplayOrder(m.getDisplayOrder());
        e.setId(m.getId());
        e.setAcadyear(m.getOpenAcadyear());
    }

    @Override
    public void toMq(Grade e, MqGrade m) {
        m.setSchoolId(e.getSchid());
        m.setAmLessonCount(e.getAmLessonCount());
        m.setPmLessonCount(e.getPmLessonCount());
        m.setNightLessonCount(e.getNightLessonCount());
        m.setSchoolingLength(e.getSchoolinglen());
        m.setGraduate(0 == e.getIsGraduated() ? false : Boolean.parseBoolean(String.valueOf(e
                .getIsGraduated())));
        m.setSection(e.getSection());
        m.setTeacherId(e.getTeacherId());
        m.setDisplayOrder(e.getDisplayOrder());
        m.setId(e.getId());
        m.setOpenAcadyear(e.getAcadyear());
    }

    public static void main(String[] args) {
        net.zdsoft.leadin.tmptool.DtoAssemblerTmp.printSetCode("e", "m", MqGrade.class, true);
    }

}
