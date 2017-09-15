package net.zdsoft.eis.base.subsystemcall.service;

import java.util.List;


public interface AchiSubsystemService {
	/**
	 * 取得学生通过成绩 返回studentId+&+subjectId
	 * @param unitId
	 * @param studentIds
	 * @param kind
	 * @return
	 */
	public List<String> getPassAchi(String unitId, String[] studentIds, int kind);
	
	public int getListByOpenAcadyear(String pointId,String specId,String unitId,String openAcadyear,int kind);
}
