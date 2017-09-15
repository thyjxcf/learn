package net.zdsoft.eis.base.subsystemcall.service;

import java.util.Map;
public interface StuworkSubsystemService {
	
	public Map<String,Integer> getPunishMap(String[] stuIds);
	
	public Map<String,Integer> getDiachiMap(String[] stuIds,String unitId,String classId);
	
	/**
	 * 获取学生德育评语(评语为学期数据，拼接)
	 * @param stuId
	 * @return
	 */
	public String[] getMoralContentByStudentId(String studentId,String acadyear); 
	/**
	 * 获取学生德育校级处分信息(评语为拼接数据)
	 * @param stuId
	 */
	public String getSchoolPunishByStudentId(String studentId,String unitId); 
}
