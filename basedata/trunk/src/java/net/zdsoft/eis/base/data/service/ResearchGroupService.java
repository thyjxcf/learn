package net.zdsoft.eis.base.data.service;

import java.util.List;

import net.zdsoft.eis.base.data.dto.ResearchGroupDto;
import net.zdsoft.eis.base.data.entity.ResearchGroup;
import net.zdsoft.eis.base.data.entity.ResearchGroupEx;

public interface ResearchGroupService {

	/**
	 * 获取学科教研组的信息列表
	 * @param unitId
	 * @return
	 */
	public List<ResearchGroupDto> getResearchGroups(String unitId);

	/**
	 * 保存新的教研组信息
	 * @param researchGroupDto
	 */
	public void save(ResearchGroupDto researchGroupDto);

	/**
	 * 获取教研组名称list
	 * @param unitId
	 * @return
	 */
	public List<ResearchGroup> getTeachGroupNames(String unitId);

	/**
	 * 软删选中的教研组信息
	 * @param ids
	 */
	public void delete(String[] ids);

	/**
	 * 查找一条教研组信息
	 * @param id
	 * @return
	 */
	public ResearchGroup getResearchGroup(String id);

	/**
	 * 获取教研组信息中相应的负责人和成员信息
	 * @param id
	 * @return
	 */
	public List<ResearchGroupEx> getResearchGroupExs(String id);

	/**
	 * 修改教研组信息
	 * @param researchGroupDto
	 */
	public void update(ResearchGroupDto researchGroupDto);

	/**
	 * 通过老师Id来找老师参与的教研组
	 * @param id
	 * @return
	 */
	public List<ResearchGroupEx> getresearchGroupExList(String id);

	/**
	 * 通过ids查找相应的教研组
	 * @param researchGroupIds
	 * @return
	 */
	public List<ResearchGroup> getResearchGroupByIds(String[] researchGroupIds);

	/**
	 * 添加某一个成员
	 * @param researchGroupId
	 * @param id
	 * @param i
	 */
	public void saveOne(String researchGroupId, String id, int i);

	/**
	 * 通过UnitId找所有未删除的教研组
	 * @param unitId
	 * @return
	 */
	public List<ResearchGroup> getResearchGroupsByUnitId(String unitId);

	/**
	 * 获得所有在用的教研组
	 * @param unitid
	 * @return
	 */
	public List<ResearchGroup> getUseResearchGroups(String unitid);

	/**
	 * 删除所有有关教师的信息
	 * @param id
	 */
	public void deleteByid(String id);
	
}
