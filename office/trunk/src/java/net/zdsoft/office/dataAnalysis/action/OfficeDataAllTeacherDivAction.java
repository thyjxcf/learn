package net.zdsoft.office.dataAnalysis.action;

import net.zdsoft.eis.base.common.action.ObjectDivAction;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.service.BaseTeacherService;
import net.zdsoft.eis.base.data.service.BaseUnitService;
import net.zdsoft.eis.base.simple.entity.SimpleObject;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class OfficeDataAllTeacherDivAction extends ObjectDivAction<Teacher> {

    private TeacherService teacherService;
    private List<Teacher> teacherList = new ArrayList<Teacher>(); // 教师列表
    private UnitService unitService;
    private BaseTeacherService baseTeacherService;
    private String unitId;
    @Override
    public List<Teacher> getDatasByUnitId() {
        if(StringUtils.isEmpty(unitId)){
           unitId = this.getLoginInfo().getUnitID();
        }

//        List<Unit> baseUnits = unitService.getAllUnits(unitId,true);
//        List<String> unitIds = new ArrayList<String>();
//        for(Unit unit : baseUnits){
//            unitIds.add(unit.getId());
//        }

        List<Teacher> teachers = teacherService.getTeachers(unitId);
        return teachers;
    }

    protected void toObject(Teacher teacher, SimpleObject object) {
        if (teacher == null) {
            return;
        }
        if (object == null) {
            return;
        }

        object.setId(teacher.getId());
        object.setObjectCode(teacher.getTchId());
        object.setObjectName(teacher.getName());
        object.setUnitiveCode("");
        object.setUnitId(teacher.getUnitid());
    }

    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    public void setBaseTeacherService(BaseTeacherService baseTeacherService) {
        this.baseTeacherService = baseTeacherService;
    }

    @Override
    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    @Override
    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
}
