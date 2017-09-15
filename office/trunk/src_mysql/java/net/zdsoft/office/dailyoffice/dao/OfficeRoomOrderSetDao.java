package net.zdsoft.office.dailyoffice.dao;

import java.util.*;
import net.zdsoft.office.dailyoffice.entity.OfficeRoomOrderSet;
import net.zdsoft.keel.util.Pagination;
/**
 * office_room_order_set
 * @author 
 * 
 */
public interface OfficeRoomOrderSetDao{

	/**
	 * 新增office_room_order_set
	 * @param officeRoomOrderSet
	 * @return
	 */
	public OfficeRoomOrderSet save(OfficeRoomOrderSet officeRoomOrderSet);

	/**
	 * 根据ids数组删除office_room_order_set
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_room_order_set
	 * @param officeRoomOrderSet
	 * @return
	 */
	public Integer update(OfficeRoomOrderSet officeRoomOrderSet);

	/**
	 * 根据id获取office_room_order_set
	 * @param id
	 * @return
	 */
	public OfficeRoomOrderSet getOfficeRoomOrderSetById(String id);

	/**
	 * 根据ids数组查询office_room_order_setmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeRoomOrderSet> getOfficeRoomOrderSetMapByIds(String[] ids);

	/**
	 * 获取office_room_order_set列表
	 * @return
	 */
	public List<OfficeRoomOrderSet> getOfficeRoomOrderSetList();

	/**
	 * 分页获取office_room_order_set列表
	 * @param page
	 * @return
	 */
	public List<OfficeRoomOrderSet> getOfficeRoomOrderSetPage(Pagination page);

	/**
	 * 根据unitId获取office_room_order_set列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeRoomOrderSet> getOfficeRoomOrderSetByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_room_order_set获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeRoomOrderSet> getOfficeRoomOrderSetByUnitIdPage(String unitId, Pagination page);

	/**
	 * 
	 * @param thisId
	 * @param unitId
	 * @return
	 */
	public OfficeRoomOrderSet getOfficeRoomOrderSetByThisId(String thisId,String unitId);
	/**
	 * 获取默认选中
	 * @param thisId
	 * @param unitId
	 * @return
	 */
	public OfficeRoomOrderSet getOfficeRoomOrderSetBySelect(String unitId);
	
	public List<OfficeRoomOrderSet> getOfficeRoomOrderSetByType(String unitId, String type);
	
}