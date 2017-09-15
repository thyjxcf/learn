/**
 * 
 */
package net.zdsoft.eis.base.data.sync.converter;

import com.winupon.syncdata.basedata.entity.son.MqSemester;

import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.sync.SyncObjectConvertable;


/**
 * @author yanb
 *
 */
public class SemesterConverter implements SyncObjectConvertable<Semester, MqSemester> {

    @Override
    public void toEntity(MqSemester m, Semester e) {
        e.setAcadyear(m.getAcadyear());
        e.setSemester(String.valueOf(m.getSemester()));
        e.setWorkBegin(m.getWorkBegin());
        e.setWorkEnd(m.getWorkEnd());
        e.setSemesterBegin(m.getSemesterBegin());
        e.setSemesterEnd(m.getSemesterEnd());
        e.setEduDays((short) m.getEduDays());
        e.setAmPeriods((short) m.getAmPeriods());
        e.setPmPeriods((short) m.getPmPeriods());
        e.setNightPeriods((short) m.getNightPeriods());
//        e.setRegisterdate(null);
//        e.setClasshour((short) 45);
        e.setId(m.getId());
    }

    @Override
    public void toMq(Semester e, MqSemester m) {
        m.setAcadyear(e.getAcadyear());
        m.setSemester(Integer.parseInt(e.getSemester()));
        m.setWorkBegin(e.getWorkBegin());
        m.setWorkEnd(e.getWorkEnd());
        m.setSemesterBegin(e.getSemesterBegin());
        m.setSemesterEnd(e.getSemesterEnd());
        m.setEduDays(e.getEduDays());
        m.setAmPeriods(e.getAmPeriods());
        m.setPmPeriods(e.getPmPeriods());
        m.setNightPeriods(e.getNightPeriods());
        m.setId(e.getId());
    }

    public static void main(String[] args) {
        net.zdsoft.leadin.tmptool.DtoAssemblerTmp.printSetCode("e", "m", MqSemester.class, true);
    }

}
