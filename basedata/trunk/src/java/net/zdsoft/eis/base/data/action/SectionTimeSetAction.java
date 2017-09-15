/** 
 
 * @author chens
 * @since 1.0
 * @version $Id: SectionTimeSetAction.java, v 1.0 2010-01-22 下午02:22:06 chens Exp $
 */
package net.zdsoft.eis.base.data.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.data.entity.StusysSectionTimeSet;
import net.zdsoft.eis.base.data.service.StusysSectionTimeSetService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.dto.PromptMessageDto;

public class SectionTimeSetAction extends BaseAction {
    private static final long serialVersionUID = -7642010989231220634L;
    
    private SemesterService semesterService;
    private StusysSectionTimeSetService stusysSectionTimeSetService;

    private int totalSection;//总共几节课
    private String[] beginTime;//开始时间
    private String[] endTime;//结束时间
    
    private List<StusysSectionTimeSet> stusysSectionTimeSetList;

    public String showPage() {
        String unitId = this.getLoginInfo().getUnitID();
        CurrentSemester  currentSemester =  semesterService.getCurrentSemester();
        if (currentSemester == null ) {
        	promptMessageDto = new PromptMessageDto();
        	promptMessageDto.setErrorMessage("还没有设置当前学期, 请先设置当前学期再做此操作");
        	return PROMPTMSG;
        }
        String acadyear = currentSemester.getAcadyear();
        String semesterStr = currentSemester.getSemester();
        Semester semester = semesterService.getSemester(acadyear, semesterStr);
        totalSection = semester.getAmPeriods()+semester.getPmPeriods()+semester.getNightPeriods();
        stusysSectionTimeSetList = stusysSectionTimeSetService.getStusysSectionTimeSetByUnitIdList(unitId,acadyear,semesterStr);
        return SUCCESS;
    }
    
    public String saveSection(){
    	if(stusysSectionTimeSetList == null){
    		stusysSectionTimeSetList = new ArrayList<StusysSectionTimeSet>();
    	}
    	CurrentSemester  currentSemester =  semesterService.getCurrentSemester();
        String acadyear = currentSemester.getAcadyear();
        String semesterStr = currentSemester.getSemester();
        String unitId = getLoginInfo().getUnitID();
        String userId = getLoginInfo().getUser().getId();
    	for(int i=0;i<beginTime.length;i++){
    		StusysSectionTimeSet ssts = new StusysSectionTimeSet();
    		ssts.setUnitId(unitId);
    		ssts.setUserId(userId);
    		ssts.setAcadyear(acadyear);
    		ssts.setSemester(Integer.parseInt(semesterStr));
    		ssts.setSectionNumber(i+1);
    		ssts.setBeginTime(beginTime[i]);
    		ssts.setEndTime(endTime[i]);
    		stusysSectionTimeSetList.add(ssts);
    	}
    	//先清空原先的记录
    	try{
    		stusysSectionTimeSetService.deleteByUnitId(unitId);
    		stusysSectionTimeSetService.batchSave(stusysSectionTimeSetList);
    	}catch(Exception e){
    		log.error(e.getMessage());
    		promptMessageDto.setErrorMessage("保存失败");
    		return SUCCESS;
    	}
    	promptMessageDto.setPromptMessage("保存成功！");
		promptMessageDto.setOperateSuccess(true);
    	return SUCCESS;
    }

    public void setSemesterService(SemesterService semesterService) {
        this.semesterService=semesterService;
    }

	public int getTotalSection() {
		return totalSection;
	}

	public void setTotalSection(int totalSection) {
		this.totalSection = totalSection;
	}

	public String[] getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String[] beginTime) {
		this.beginTime = beginTime;
	}

	public String[] getEndTime() {
		return endTime;
	}

	public void setEndTime(String[] endTime) {
		this.endTime = endTime;
	}

	public void setStusysSectionTimeSetService(
			StusysSectionTimeSetService stusysSectionTimeSetService) {
		this.stusysSectionTimeSetService = stusysSectionTimeSetService;
	}

	public List<StusysSectionTimeSet> getStusysSectionTimeSetList() {
		return stusysSectionTimeSetList;
	}

	public void setStusysSectionTimeSetList(
			List<StusysSectionTimeSet> stusysSectionTimeSetList) {
		this.stusysSectionTimeSetList = stusysSectionTimeSetList;
	}

}
