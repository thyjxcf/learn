package net.zdsoft.office.dailyoffice.service;

import java.util.*;
import net.zdsoft.office.dailyoffice.entity.OfficeRoomOrderSet;
import net.zdsoft.keel.util.Pagination;
/**
 * office_room_order_set
 * @author 
 * 
 */
public interface OfficeRoomOrderSetService{

	/**
	 * 新增office_room_order_set
	 * @param officeRoomOrderSet
	 * @return
	 */
	public OfficeRoomOrderSet save(OfficeRoomOrderSet officeRoomOrderSet);

	/**
	 * 根据ids数组删除office_room_order_set数据
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
	 * 根据UnitId获取office_room_order_set列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeRoomOrderSet> getOfficeRoomOrderSetByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_room_order_set
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeRoomOrderSet> getOfficeRoomOrderSetByUnitIdPage(String unitId, Pagination page);

	/**
	 * 根据thisId和unitId查询
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
	/**
	 * 通过unitId、thisId获取
	 * @param unitId
	 * @param type
	 * @return
	 */
	public OfficeRoomOrderSet getOfficeRoomOrderSetByType(String unitId, String type);
	
}