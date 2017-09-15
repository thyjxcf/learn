package net.zdsoft.eis.base.data.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.data.service.StuTeaQueryService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.eis.frame.dto.PromptMessageDto;

/** 
 * @author  lufeng 
 * @version 创建时间：2016-7-19 下午01:39:44 
 * 类说明 
 */
public class StuTeaQueryAction extends PageAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 601004283844220612L;
	private String unitid;
	private UnitService unitService;
	private String queryTchName; // 姓名
	private String userType;
	private String queryTchUserName; // 用户名
    private List<User> userList = new ArrayList<User>();
    private StuTeaQueryService stuTeaQueryService;
    private PromptMessageDto promptMessageDto = new PromptMessageDto();
	public String execute(){

	  //  userList = stuTeaQueryService.getStudentInfo(unitid,  queryTchName, queryTchUserName, getPage());
		return "success";
	}
	public String queryList(){
		Unit unit = unitService.getUnit(unitid)	;
		if(unit != null ){
			if(Unit.UNIT_CLASS_SCHOOL == unit.getUnitclass()){
				userList = stuTeaQueryService.getStudentInfo(unitid,  queryTchName, queryTchUserName, getPage());
			}
		}
		return "success";
	}
	public String queryJudge(){
		Unit unit = unitService.getUnit(unitid)	;
		if(unit != null ){
			if(Unit.UNIT_CLASS_SCHOOL == unit.getUnitclass()){
				promptMessageDto.setOperateSuccess(true);
			}
		}
		return "success";
	}
	
	public String getUnitid() {
		return unitid;
	}
	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}
	public String getQueryTchName() {
		return queryTchName;
	}
	public void setQueryTchName(String queryTchName) {
		this.queryTchName = queryTchName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getQueryTchUserName() {
		return queryTchUserName;
	}
	public void setQueryTchUserName(String queryTchUserName) {
		this.queryTchUserName = queryTchUserName;
	}
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public PromptMessageDto getPromptMessageDto() {
		return promptMessageDto;
	}
	public void setPromptMessageDto(PromptMessageDto promptMessageDto) {
		this.promptMessageDto = promptMessageDto;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	public void setStuTeaQueryService(StuTeaQueryService stuTeaQueryService) {
		this.stuTeaQueryService = stuTeaQueryService;
	}
	
	
	
}
