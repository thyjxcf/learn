package net.zdsoft.office.dataAnalysis.action;

import net.zdsoft.eis.base.common.entity.School;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keelcnet.util.CollectionUtils;
import net.zdsoft.office.dataAnalysis.entity.OfficeDataReportStreet;
import net.zdsoft.office.dataAnalysis.entity.OfficeDataReportStrmanager;
import net.zdsoft.office.dataAnalysis.service.OfficeDataReportStreetService;
import net.zdsoft.office.dataAnalysis.service.OfficeDataReportStrmanagerService;
import org.apache.axis.utils.ArrayUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OfficeDataAnalysisStreetAction  extends BaseAction {

    private OfficeDataReportStreet officeDataReportStreet = new OfficeDataReportStreet();
    private OfficeDataReportStreetService officeDataReportStreetService;
    private SchoolService schoolService;
    private String streetId;
    private String streetName;
    private List<OfficeDataReportStreet> streetList =null;
    private OfficeDataReportStrmanagerService officeDataReportStrmanagerService;
    public String execute(){

        return "success";
    }
    public String checkStreetName(){
        streetList = officeDataReportStreetService.getOfficeDataReportStreetByUnitIdStreetName(getUnitId(),streetName);
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(streetList)){
            promptMessageDto.setOperateSuccess(false);
            promptMessageDto.setErrorMessage("该单位下已存在该街道名称");
        }else {
            promptMessageDto.setOperateSuccess(true);
            promptMessageDto.setPromptMessage("");
        }

        return "success";
    }

    public String streetSave(){
        try{
            if(StringUtils.isEmpty(officeDataReportStreet.getId())){
                officeDataReportStreetService.save(officeDataReportStreet);
            }else{
                officeDataReportStreetService.update(officeDataReportStreet);
            }
        }catch(Exception e){
            e.printStackTrace();
            promptMessageDto.setOperateSuccess(false);
            promptMessageDto.setErrorMessage("保存失败");
            return "success";
        }

        promptMessageDto.setOperateSuccess(true);
        promptMessageDto.setPromptMessage("保存成功");

        return "success";
    }

    public String streetList(){

        streetList = officeDataReportStreetService.getOfficeDataReportStreetByUnitIdPage(getUnitId(),null);
        Set<String> setSchoolIds = new HashSet<String>();
        for(OfficeDataReportStreet street : streetList){
            String schoolIds = street.getSchoolIds();
            if(StringUtils.isNotEmpty(schoolIds)){
                String[] arrIds = schoolIds.split(",");
                if(ArrayUtils.isNotEmpty(arrIds)){
                    for(String str:arrIds){
                        setSchoolIds.add(str);
                    }
                }
            }
        }
        Map<String,School> schoolMap = schoolService.getSchoolsById(setSchoolIds.toArray(new String[0]));
        for(OfficeDataReportStreet street : streetList){
            String schoolIds = street.getSchoolIds();
            String schooNames = getSchoolNameBySchoolId(schoolIds);

            street.setSchoolNames(schooNames);
        }

        return "success";
    }
    public String streetEditLink(){
        if(StringUtils.isNotEmpty(streetId)){
            officeDataReportStreet = officeDataReportStreetService.getOfficeDataReportStreetById(streetId);
            String schoolIds = officeDataReportStreet.getSchoolIds();
            String schooNames = getSchoolNameBySchoolId(schoolIds);
            officeDataReportStreet.setSchoolNames(schooNames);

        }else{
            officeDataReportStreet.setUnitId(getUnitId());
        }
        return "success";
    }
    private String getSchoolNameBySchoolId(String schoolIds){
        StringBuffer stringBuffer = new StringBuffer();
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(schoolIds)){

            String[] ids = schoolIds.split(",");
            Map<String,School> schoolMap = schoolService.getSchoolsById(ids);
            for(int i=0;i<ids.length;i++){
                School school = schoolMap.get(ids[i]);
                if(school == null){
                    continue;
                }
                if(i == ids.length-1){
                    stringBuffer.append(school.getName());
                }else{
                    stringBuffer.append(school.getName() + ",");
                }
            }
        }
        return stringBuffer.toString();
    }
    public String streetDelete(){
        try{
            List<OfficeDataReportStrmanager> strmanagerList =  officeDataReportStrmanagerService.getOfficeDataReportStrmanagerByUnitIdStreetId(getUnitId(),streetId);
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(strmanagerList)){
                promptMessageDto.setOperateSuccess(false);
                promptMessageDto.setErrorMessage("已经设置街道管理员，不能删除");
                return "success";
            }

            officeDataReportStreetService.delete(new String[]{streetId});
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
    public OfficeDataReportStreet getOfficeDataReportStreet() {
        return officeDataReportStreet;
    }

    public void setOfficeDataReportStreet(OfficeDataReportStreet officeDataReportStreet) {
        this.officeDataReportStreet = officeDataReportStreet;
    }

    public void setOfficeDataReportStreetService(OfficeDataReportStreetService officeDataReportStreetService) {
        this.officeDataReportStreetService = officeDataReportStreetService;
    }

    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }

    public List<OfficeDataReportStreet> getStreetList() {
        return streetList;
    }

    public void setStreetList(List<OfficeDataReportStreet> streetList) {
        this.streetList = streetList;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setOfficeDataReportStrmanagerService(OfficeDataReportStrmanagerService officeDataReportStrmanagerService) {
        this.officeDataReportStrmanagerService = officeDataReportStrmanagerService;
    }
}
