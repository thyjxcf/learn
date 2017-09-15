package net.zdsoft.eis.base.data.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.data.entity.SchtypeSection;
import net.zdsoft.eis.base.sync.EventSourceType;

/**
 * @author yanb
 * 
 */
public interface BaseSchtypeSectionDao {
    /**
     * 增加schtypeSection
     * @param schtypeSection
     */
    public void insertSchtypeSection(SchtypeSection schtypeSection);

    /**
     * 删除schtypeSection
     * @param schtypeSectionIds
     * @param eventSource 
     */
    public void deleteSchtypeSection(String[] schtypeSectionIds, EventSourceType eventSource);

    /**
     *  更新schtypeSection 
     * @param schtypeSection
     */
    public void updateSchtypeSection(SchtypeSection schtypeSection);

    /**
     * 根据id获得SchtypeSection
     * @param schtypeSectionId
     * @return
     */
    public SchtypeSection getSchtypeSection(String schtypeSectionId);

    /**
     * 获得SchtypeSection列表
     * @return
     */
    public List<SchtypeSection> getSchtypeSections();

    /**
     * 获得SchtypeSection Map
     * @param schtypeSectionIds
     * @return
     */
    public Map<String, SchtypeSection> getSchtypeSections(String[] schtypeSectionIds);

}
