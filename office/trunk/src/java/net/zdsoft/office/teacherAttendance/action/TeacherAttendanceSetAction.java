package net.zdsoft.office.teacherAttendance.action;

import java.util.Date;
import java.util.List;

import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceSet;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceSetService;

import org.apache.commons.lang.StringUtils;
/**
 * 考勤制度设置action
 * @author 
 *    
 */
public class TeacherAttendanceSetAction extends PageAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private OfficeAttendanceSet officeAttendanceSet=new OfficeAttendanceSet();
	private OfficeAttendanceSetService officeAttendanceSetService;
	
	private List<OfficeAttendanceSet> officeAttendanceSetList;
	
	private String id;
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	//获取列表
	public String getAttendanceList(){
		
		officeAttendanceSetList=officeAttendanceSetService.getOfficeAttendanceSetByUnitId(getUnitId());
		return SUCCESS;
	}
	
	//跳转新增、编辑页面
	public String editAttendance(){
		if(StringUtils.isNotBlank(id)){
			officeAttendanceSet=officeAttendanceSetService.getOfficeAttendanceSetById(id);
		}
		return SUCCESS;
	}
	//保存
	public String saveAttendance(){
		try {
			if(!officeAttendanceSet.getIsElastic()){//非弹性
				officeAttendanceSet.setElasticRange(null);
				officeAttendanceSet.setEndElasticRange(null);
			}
			if(StringUtils.isNotBlank(officeAttendanceSet.getId())){
				officeAttendanceSet.setModifyTime(new Date());
				officeAttendanceSetService.update(officeAttendanceSet);
			}else{
				officeAttendanceSet.setUnitId(getUnitId());
				officeAttendanceSet.setCreationTime(new Date());
				officeAttendanceSetService.save(officeAttendanceSet);
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("保存失败!");
		}
		return SUCCESS;
	}
	//删除
	public String deleteAttendance(){
		try {
			if(StringUtils.isNotBlank(id)){
				officeAttendanceSetService.delete(new String[]{id});
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败!");
		}
		return SUCCESS;
	}
	public OfficeAttendanceSet getOfficeAttendanceSet() {
		return officeAttendanceSet;
	}

	public void setOfficeAttendanceSet(OfficeAttendanceSet officeAttendanceSet) {
		this.officeAttendanceSet = officeAttendanceSet;
	}

	public List<OfficeAttendanceSet> getOfficeAttendanceSetList() {
		return officeAttendanceSetList;
	}

	public void setOfficeAttendanceSetList(
			List<OfficeAttendanceSet> officeAttendanceSetList) {
		this.officeAttendanceSetList = officeAttendanceSetList;
	}

	public void setOfficeAttendanceSetService(
			OfficeAttendanceSetService officeAttendanceSetService) {
		this.officeAttendanceSetService = officeAttendanceSetService;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
