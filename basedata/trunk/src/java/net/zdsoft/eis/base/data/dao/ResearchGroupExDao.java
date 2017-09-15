package net.zdsoft.eis.base.data.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.data.entity.ResearchGroupEx;

public interface ResearchGroupExDao {

	/**
	 * 通过id查找相关的成员和负责人信息
	 * @param id
	 * @return
	 */
	public List<ResearchGroupEx> getResearchGroupExs(String id);

	/**
	 * 保存新的相关成员和负责人信息
	 * @param researchGroupEx
	 */
	public void save(ResearchGroupEx researchGroupEx);

	/**
	 * 删除相关成员和负责人信息
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 通过老师id得到老师参与的教研组
	 * @param id
	 * @return
	 */
	public List<ResearchGroupEx> getresearchGroupExList(String id);

	/**
	 * 通过id删除
	 * @param id
	 */
	public void deleteByid(String id);



}
