package net.zdsoft.office.dataAnalysis.action;

import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.data.service.BaseTeacherService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.util.RedisUtils;
import net.zdsoft.office.dataAnalysis.entity.OfficeDataReportStreet;
import net.zdsoft.office.dataAnalysis.entity.OfficeDataReportStrmanager;
import net.zdsoft.office.dataAnalysis.service.OfficeDataReportStreetService;
import net.zdsoft.office.dataAnalysis.service.OfficeDataReportStrmanagerService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfficeDataAnalysisStreetManagerAction extends BaseAction{
    private String streetManagerId;
    private OfficeDataReportStrmanager officeDataReportStrmanager = new OfficeDataReportStrmanager();
    private OfficeDataReportStrmanagerService officeDataReportStrmanagerService;
    private List<OfficeDataReportStrmanager> officeDataReportStrmanagerList = new ArrayList<OfficeDataReportStrmanager>();
    private OfficeDataReportStreetService officeDataReportStreetService;
    private TeacherService teacherService;
    private String teacherId;
    private UnitService unitService;
    private BaseTeacherService baseTeacherService;
    private List<Unit> baseUnits = new ArrayList<Unit>();
    public String execute(){

        return "success";
    }

    public String checkStreetManager(){
        List<OfficeDataReportStrmanager> officeDataReportStrmanagerList = officeDataReportStrmanagerService.getOfficeDataReportStrmanagerByUnitIdTeaId(getUnitId(),teacherId);
        if(CollectionUtils.isNotEmpty(officeDataReportStrmanagerList)){
            promptMessageDto.setOperateSuccess(false);
            promptMessageDto.setErrorMessage("该管理员已经在该单位下存在");
        }else {
            promptMessageDto.setOperateSuccess(true);
            promptMessageDto.setPromptMessage("");
        }

        return "success";
    }

    public String streetManagerList(){
        String unitId = getUnitId();
        officeDataReportStrmanagerList = officeDataReportStrmanagerService.getOfficeDataReportStrmanagerByUnitIdPage(unitId,null);

//        Map<String , Teacher> teacherMap = teacherService.getTeacherMap(unitId);
        Map<String ,BaseTeacher> baseTeacherMap = getTeacherMap(unitId);
        for(OfficeDataReportStrmanager manager : officeDataReportStrmanagerList){
            String streetIds = manager.getStreetIds();
            BaseTeacher teacher = baseTeacherMap.get(manager.getTeacherId());
            if(teacher != null){
                manager.setTeacherName(teacher.getName());
            }
            String streetNames = getStreetNames(manager.getStreetIds());
            manager.setStreetNames(streetNames);
        }
        return "success";
    }
//    private Map<String ,BaseTeacher> getTeacherMap(){
//        String unitId = this.getLoginInfo().getUnitID();
//        List<Unit> baseUnits = unitService.getAllUnits(unitId,true);
//        List<String> unitIds = new ArrayList<String>();
//        for(Unit unit : baseUnits){
//            unitIds.add(unit.getId());
//        }
//
//        List<BaseTeacher> teachers = baseTeacherService.getTeachersByUnitIds(unitIds.toArray(new String[0]));
//        Map<String, BaseTeacher> teacherMap = new HashMap<String, BaseTeacher>();
//        for(BaseTeacher teacher : teachers){
//            teacherMap.put(teacher.getId() , teacher);
//        }
//
//        return teacherMap;
//    }
    private String getStreetNames(String streetIds){
        Map<String,OfficeDataReportStreet> streetMap = officeDataReportStreetService.getOfficeDataStreetMapByUnitid(getUnitId());
        StringBuffer sb = new StringBuffer();
        if(StringUtils.isNotEmpty(streetIds)){
            String[] ids = streetIds.split(",");
            int size = ids.length;

            for(int i=0;i<size;i++){
                OfficeDataReportStreet officeDataReportStreet = streetMap.get(ids[i]);
                if(officeDataReportStreet != null){
                    if( i == size -1){
                        sb.append(officeDataReportStreet.getStreetName());
                    }else{
                        sb.append(officeDataReportStreet.getStreetName() + ",");
                    }
                }

            }

        }
        return sb.toString();
    }
    public Map<String, BaseTeacher> getTeacherMap( String unitId){

        String key = unitId + "OfficeDataAnalysisStreetManagerAction_baseUnits";
        baseUnits = RedisUtils.getObject(key);
        if(baseUnits == null){
            baseUnits = new ArrayList<Unit>();
            List<Unit>  units = unitService.getUnderlingUnits(unitId,null);

            Unit unit = unitService.getUnit(unitId);
            baseUnits.add(unit);
            baseUnits.addAll(units);
            RedisUtils.setObject(key , baseUnits ,7200);
        }
        List<String> unitIds = new ArrayList<String>();
        for(Unit u : baseUnits){
            unitIds.add(u.getId());
        }

        List<BaseTeacher> teachers = baseTeacherService.getTeachersByUnitIds(unitIds.toArray(new String[0]));
        String keyMap = unitId + "OfficeDataAnalysisStreetManagerAction_teacherMap";

        Map<String, BaseTeacher> teacherMap = RedisUtils.getObject(keyMap);
        if(teacherMap == null){
            teacherMap = new HashMap<String, BaseTeacher>();
            for(BaseTeacher teacher : teachers){
                teacherMap.put(teacher.getId() , teacher);
            }
            RedisUtils.setObject(keyMap , teacherMap ,7200);
        }

        return teacherMap;

    }
    public String streetManagerEditLink(){
        String unitId = getUnitId();

        Map<String, BaseTeacher> teacherMap  = getTeacherMap(unitId);
        Unit unit = unitService.getUnit(unitId);
        if(StringUtils.isEmpty(streetManagerId)){
            officeDataReportStrmanager = new OfficeDataReportStrmanager();
            officeDataReportStrmanager.setUnitId(getUnitId());
            officeDataReportStrmanager.setUnitName(unit.getName());
        }else{
            officeDataReportStrmanager = officeDataReportStrmanagerService.getOfficeDataReportStrmanagerById(streetManagerId);
           // Map<String,OfficeDataReportStreet> streetMap = officeDataReportStreetService.getOfficeDataStreetMapByUnitid(unitId);
//            Map<String , Teacher> teacherMap = teacherService.getTeacherMap(unitId);
//            Map<String ,BaseTeacher> baseTeacherMap = getTeacherMap();
            BaseTeacher tea = teacherMap.get(officeDataReportStrmanager.getTeacherId());

            if(tea != null){
                Unit teacherUnit = unitService.getUnit(tea.getUnitid());
                if(teacherUnit != null){
                    officeDataReportStrmanager.setUnitName(teacherUnit.getName());
                    officeDataReportStrmanager.setUnitId(teacherUnit.getId());
                }
                officeDataReportStrmanager.setTeacherName(tea.getName());
            }
            String streetNames = getStreetNames(officeDataReportStrmanager.getStreetIds());
            officeDataReportStrmanager.setStreetNames(streetNames);

        }
        return "success";
    }

    public String streetManagerSave(){
        try{
//            if(StringUtils.isNotEmpty(officeDataReportStrmanager.getTeacherId())){
//                Map<String, BaseTeacher> teacherMap = getTeacherMap();
//                BaseTeacher teacher = teacherMap.get(officeDataReportStrmanager.getTeacherId());
//                officeDataReportStrmanager.setUnitId(teacher.getUnitid());
//            }
            officeDataReportStrmanager.setUnitId(getUnitId());
            if(StringUtils.isEmpty(officeDataReportStrmanager.getId())){
                officeDataReportStrmanagerService.save(officeDataReportStrmanager);
            }else{
                officeDataReportStrmanagerService.update(officeDataReportStrmanager);
            }
        }catch (Exception e){
            e.printStackTrace();
            promptMessageDto.setOperateSuccess(false);
            promptMessageDto.setErrorMessage("保存失败");
            return "success";
        }
        promptMessageDto.setOperateSuccess(true);
        promptMessageDto.setPromptMessage("保存成功");



        return "success";
    }
    public String streetManagerDelete(){
        try{

            officeDataReportStrmanagerService.delete(new String[]{streetManagerId});

        }catch (Exception e){
            e.printStackTrace();
            promptMessageDto.setOperateSuccess(false);
            promptMessageDto.setErrorMessage("删除失败");
            return "success";
        }
        promptMessageDto.setOperateSuccess(true);
        promptMessageDto.setPromptMessage("删除成功");



        return "success";
    }
    public void setOfficeDataReportStrmanagerService(OfficeDataReportStrmanagerService officeDataReportStrmanagerService) {
        this.officeDataReportStrmanagerService = officeDataReportStrmanagerService;
    }

    public String getStreetManagerId() {
        return streetManagerId;
    }

    public void setStreetManagerId(String streetManagerId) {
        this.streetManagerId = streetManagerId;
    }

    public OfficeDataReportStrmanager getOfficeDataReportStrmanager() {
        return officeDataReportStrmanager;
    }

    public void setOfficeDataReportStrmanager(OfficeDataReportStrmanager officeDataReportStrmanager) {
        this.officeDataReportStrmanager = officeDataReportStrmanager;
    }

    public void setOfficeDataReportStreetService(OfficeDataReportStreetService officeDataReportStreetService) {
        this.officeDataReportStreetService = officeDataReportStreetService;
    }

    public List<OfficeDataReportStrmanager> getOfficeDataReportStrmanagerList() {
        return officeDataReportStrmanagerList;
    }

    public void setOfficeDataReportStrmanagerList(List<OfficeDataReportStrmanager> officeDataReportStrmanagerList) {
        this.officeDataReportStrmanagerList = officeDataReportStrmanagerList;
    }

    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public void setBaseTeacherService(BaseTeacherService baseTeacherService) {
        this.baseTeacherService = baseTeacherService;
    }

    public void setBaseUnits(List<Unit> baseUnits) {
        this.baseUnits = baseUnits;
    }

    public List<Unit> getBaseUnits() {
        return baseUnits;
    }
}
