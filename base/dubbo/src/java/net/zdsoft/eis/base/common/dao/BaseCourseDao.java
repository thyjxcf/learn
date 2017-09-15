package net.zdsoft.eis.base.common.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.BaseCourse;

public interface BaseCourseDao {
	/**
	 * 获取学科列表
	 * @param unitid
	 * @return
	 */
	public List<BaseCourse> getBaseCoureList(String unitid);
	
	/**
	 * 通过id获取学科信息
	 * @param code
	 * @return
	 */
	public BaseCourse getBaseCourseByCode(String code);
}
