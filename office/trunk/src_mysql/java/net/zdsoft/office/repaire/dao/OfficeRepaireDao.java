package net.zdsoft.office.repaire.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.repaire.entity.OfficeRepaire;
/**
 * office_repaire
 * @author 
 * 
 */
public interface OfficeRepaireDao{

	/**
	 * 新增office_repaire
	 * @param officeRepaire
	 * @return
	 */
	public OfficeRepaire save(OfficeRepaire officeRepaire);

	/**
	 * 根据ids数组删除office_repaire
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_repaire
	 * @param officeRepaire
	 * @return
	 */
	public Integer update(OfficeRepaire officeRepaire);
	
	/**
	 * 更新维修状态
	 * @param officeRepaire
	 */
	public void updateState(OfficeRepaire officeRepaire);
	
	/**
	 * 更新反馈信息
	 * @param officeRepaire
	 */
	public void updateFeedBack(OfficeRepaire officeRepaire);

	/**
	 * 根据id获取office_repaire
	 * @param id
	 * @return
	 */
	public OfficeRepaire getOfficeRepaireById(String id);

	/**
	 * 根据ids数组查询office_repairemap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeRepaire> getOfficeRepaireMapByIds(String[] ids);

	/**
	 * 获取office_repaire列表
	 * @return
	 */
	public List<OfficeRepaire> getOfficeRepaireList();

	/**
	 * 分页获取office_repaire列表
	 * @param page
	 * @return
	 */
	public List<OfficeRepaire> getOfficeRepairePage(Pagination page);

	/**
	 * 根据unitId获取office_repaire列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeRepaire> getOfficeRepaireByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_repaire获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeRepaire> getOfficeRepaireByUnitIdPage(String unitId, Pagination page);

	public List<OfficeRepaire> getOfficeRepaireList(String userId,
			String unitID, String areaId, String type, String state,
			Date startTime, Date endTime, String searchContent, Pagination page);

	public List<OfficeRepaire> getOfficeRepaireList(String unitID,
			String[] areaIds, String[] types, String state, Date startTime,
			Date endTime, String searchContent, Pagination page);
	
	public List<OfficeRepaire> getOfficeRepaireListH5(String unitID,
			String[] areaIds, String[] types, String[] state, Date startTime,
			Date endTime, String searchContent, Pagination page);

	public List<OfficeRepaire> getOfficeRepaireByUnitIdList(String unitId,
			String typeId);

	public int getOfficeRepaireListH5Count(String unitId, String[] areaIds, String[] types, String[] state,
			Date startTime, Date endTime, String searchContent);
	/**
	 * 根据User_id和最后创建时间获取office_repaire
	 * @param id
	 * @return
	 */
	public OfficeRepaire getOfficeRepaireByUserIdLastTime(String userId);

}