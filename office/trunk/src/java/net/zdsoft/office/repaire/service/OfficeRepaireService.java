package net.zdsoft.office.repaire.service;

import java.util.*;

import net.zdsoft.office.repaire.entity.OfficeRepaire;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
/**
 * office_repaire
 * @author 
 * 
 */
public interface OfficeRepaireService{

	/**
	 * 新增office_repaire
	 * @param officeRepaire
	 * @return
	 */
	public OfficeRepaire save(OfficeRepaire officeRepaire);
	public void add(OfficeRepaire officeRepaire,UploadFile file);
	public void updateState(String[] ids,String state,String userId);

	/**
	 * 根据ids数组删除office_repaire数据
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
	public void update(OfficeRepaire officeRepaire,UploadFile file);
	
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
	 * 根据UnitId,userId获取office_repaire列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeRepaire> getOfficeRepaireByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_repaire
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeRepaire> getOfficeRepaireByUnitIdPage(String unitId, Pagination page);

	public List<OfficeRepaire> getOfficeRepaireList(String userId,
			String unitID, String areaId, String type, String state,
			Date startTime, Date endTime, String searchContent, Pagination page);

	/**
	 * 
	 * @param userId
	 * @param unitID
	 * @param areaId
	 * @param type
	 * @param state
	 * @param startTime
	 * @param endTime
	 * @param searchContent 根据设备名称或者设备地点
	 * @param page
	 * @return
	 */
	public List<OfficeRepaire> getOfficeRepaireMangeList(String userId,
			String unitID, String areaId, String type, String state,
			Date startTime, Date endTime, String searchContent, Pagination page);
	
	/**
	 * 
	 * @param userId
	 * @param unitID
	 * @param areaId
	 * @param type
	 * @param state
	 * @param startTime
	 * @param endTime
	 * @param searchContent 根据设备名称或者设备地点
	 * @param page
	 * @return
	 */
	public List<OfficeRepaire> getOfficeRepaireMangeListH5(String userId,
			String unitID, String areaId, String type, String[] state,
			Date startTime, Date endTime, String searchContent, Pagination page);

	public List<OfficeRepaire> getOfficeRepaireByUnitIdList(String unitId,
			String id);
	public int getOfficeRepaireMangeListH5Count(String userId, String unitId, String areaId, String type,
			String[] state, Date startTime, Date endTime, String searchContent);
	/**
	 * 根据User_id和最后创建时间获取office_repaire
	 * @param id
	 * @return
	 */
	public OfficeRepaire getOfficeRepaireByUserIdLastTime(String userId);
	
	public Map<String,String> getOfficeRepaireByUnitIdAndYearsMap(String unitId,String years,String change);
	public Map<String,String> getOfficeRepaireByUnitIdAndYearsTalMap(String unitId,String years,String change);
	public Map<String,String> getOfficeRepaireByUnitIdAndYearsCountMap(String unitId,String years);
}