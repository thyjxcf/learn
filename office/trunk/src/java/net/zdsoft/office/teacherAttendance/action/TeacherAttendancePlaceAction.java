package net.zdsoft.office.teacherAttendance.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroup;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendancePlace;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceGroupService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceGroupUserService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendancePlaceService;

public class TeacherAttendancePlaceAction extends PageAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OfficeAttendancePlaceService officeAttendancePlaceService;
	private OfficeAttendanceGroupService officeAttendanceGroupService;
	
	private List<OfficeAttendancePlace> placeList = new ArrayList<OfficeAttendancePlace>();
	private OfficeAttendancePlace place;
	
	private String id;
	
	@Override
	public String execute() throws Exception {
		placeList = officeAttendancePlaceService.listOfficeAttendancePlaceByUnitId(getLoginInfo().getUnitID());
		return SUCCESS;
	}
	
	
	public String edit(){
		if(StringUtils.isNotBlank(id)){
			place = officeAttendancePlaceService.getOfficeAttendancePlaceById(id);
		}else{
			place = new OfficeAttendancePlace();
		}
		return SUCCESS;
	}
	
	public String save(){
		try {
			
			List<OfficeAttendancePlace> list = officeAttendancePlaceService.getListByName(getLoginInfo().getUnitID(), place.getName(), id);
			if(CollectionUtils.isNotEmpty(list)){
				promptMessageDto.setErrorMessage("已存在相同名称的办公地点，请重新维护。");
				return SUCCESS;
			}
			if(StringUtils.isNotBlank(id)){
				OfficeAttendancePlace ent = officeAttendancePlaceService.getOfficeAttendancePlaceById(id);
				ent.setName(place.getName());
				ent.setMapName(place.getMapName());
				ent.setAddress(place.getAddress());
				ent.setLatitude(place.getLatitude());
				ent.setLongitude(place.getLongitude());
				ent.setRange(place.getRange());
				ent.setModifyTime(new Date());
				
				officeAttendancePlaceService.update(ent);
			}else{
				place.setUnitId(getLoginInfo().getUnitID());
				officeAttendancePlaceService.save(place);
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			promptMessageDto.setErrorMessage("操作失败,请重新提交。");
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String delete(){
		try {
			List<OfficeAttendanceGroup> grouplist = officeAttendanceGroupService.getListByPlaceId(id);
			if(CollectionUtils.isNotEmpty(grouplist)){
				promptMessageDto.setErrorMessage("操作失败,该办公地点有关联考勤组，不能删除。");
				return SUCCESS;
			}
			officeAttendancePlaceService.delete(new String[]{id});
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			promptMessageDto.setErrorMessage("操作失败,请重新删除。");
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String map(){
		return SUCCESS;
	}
	
	public String selMap(){
		return SUCCESS;
	}
	
	
	public void setOfficeAttendancePlaceService(
			OfficeAttendancePlaceService officeAttendancePlaceService) {
		this.officeAttendancePlaceService = officeAttendancePlaceService;
	}

	public OfficeAttendancePlace getPlace() {
		return place;
	}

	public void setPlace(OfficeAttendancePlace place) {
		this.place = place;
	}

	public List<OfficeAttendancePlace> getPlaceList() {
		return placeList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOfficeAttendanceGroupService(
			OfficeAttendanceGroupService officeAttendanceGroupService) {
		this.officeAttendanceGroupService = officeAttendanceGroupService;
	}
}
