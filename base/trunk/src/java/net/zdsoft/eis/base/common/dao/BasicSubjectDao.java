package net.zdsoft.eis.base.common.dao;

import java.util.Map;

public interface BasicSubjectDao {
	/**
	 * 
	 * @param unitId
	 * @param isUsing -1 不过滤这个字段
	 * @param likeName TODO
	 * @return
	 */
	public Map<String,String> getSubjectMap(String unitId,int isUsing, String likeName);
	
	public Map<String, String> getSubjectMap(String[] subids);
	
	public Map<String, String> getSubjectMapNo(String[] subids);
}
