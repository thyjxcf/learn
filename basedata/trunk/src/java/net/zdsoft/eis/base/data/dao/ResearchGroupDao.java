package net.zdsoft.eis.base.data.dao;

import java.util.List;

import net.zdsoft.eis.base.data.entity.ResearchGroup;

public interface ResearchGroupDao {

	public List<ResearchGroup> getResearchGroups(String unitId);

	public List<ResearchGroup> getTeachGroupNames(String unitId);

	public void save(ResearchGroup researchGroup);

	public void delete(String[] ids);

	public ResearchGroup getResearchGroup(String id);

	public void update(ResearchGroup researchGroup);

	public List<ResearchGroup> getResearchGroupByIds(String[] researchGroupIds);

}
