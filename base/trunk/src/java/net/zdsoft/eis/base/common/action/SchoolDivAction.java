package net.zdsoft.eis.base.common.action;

import net.zdsoft.eis.base.common.entity.School;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.simple.entity.SimpleObject;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SchoolDivAction extends ObjectDivAction<School> {

    private Integer unitType;//学段
    private UnitService unitService;
    private SchoolService schoolService;
    @Override
    protected void toObject(School school, SimpleObject object) {
        if(school == null){
            return;
        }
        if(object == null){
            return;
        }

        object.setId(school.getId());
        object.setObjectName(school.getName());
        object.setObjectCode(school.getCode());

    }

    @Override
    protected List<School> getDatasByUnitId() {
        String unitId = this.getLoginInfo().getUnitID();

        List<Unit> unitList = unitService.getUnderlingSchools(unitId);
//        unitList = unitService.getAllUnits(unitId,false);
        List<String> unitIds = new ArrayList<String>();

        for(Unit unit :unitList){
           if(3 == unit.getUnittype()){
                unitIds.add(unit.getId());
           }

        }
        List<School> schoolList = schoolService.getSchools(unitIds.toArray(new String[0]));
        School school = schoolService.getSchool("00EA65CEFDD3DE8DE050A8C09B006DCE");
        schoolList = new ArrayList<School>();
        schoolList.add(school);
        School school1 = schoolService.getSchool("00EA65CEFDD5DE8DE050A8C09B006DCE");
        schoolList.add(school1);
        School school2 = schoolService.getSchool("00EA65CEFE36DE8DE050A8C09B006DCE");
        schoolList.add(school2);
        if(schoolList == null) schoolList = new ArrayList<School>();
        // dddd
        return schoolList;
    }

    public Integer getUnitType() {
        return unitType;
    }

    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }

    public UnitService getUnitService() {
        return unitService;
    }

    @Override
    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public SchoolService getSchoolService() {
        return schoolService;
    }

    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }
}
