package net.zdsoft.office.boardroom.dao;

import java.util.*;

import net.zdsoft.office.boardroom.entity.OfficeApplyExXj;
import net.zdsoft.keel.util.Pagination;
/**
 * office_apply_ex_xj
 * @author 
 * 
 */
public interface OfficeApplyExXjDao{

	/**
	 * 新增office_apply_ex_xj
	 * @param officeApplyExXj
	 * @return
	 */
	public OfficeApplyExXj save(OfficeApplyExXj officeApplyExXj);

	/**
	 * 根据ids数组删除office_apply_ex_xj
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_apply_ex_xj
	 * @param officeApplyExXj
	 * @return
	 */
	public Integer update(OfficeApplyExXj officeApplyExXj);

	/**
	 * 根据id获取office_apply_ex_xj
	 * @param id
	 * @return
	 */
	public OfficeApplyExXj getOfficeApplyExXjById(String id);

	/**
	 * 根据ids数组查询office_apply_ex_xjmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeApplyExXj> getOfficeApplyExXjMapByIds(String[] ids);

	/**
	 * 获取office_apply_ex_xj列表
	 * @return
	 */
	public List<OfficeApplyExXj> getOfficeApplyExXjList();

	/**
	 * 分页获取office_apply_ex_xj列表
	 * @param page
	 * @return
	 */
	public List<OfficeApplyExXj> getOfficeApplyExXjPage(Pagination page);

	/**
	 * 根据unitId获取office_apply_ex_xj列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeApplyExXj> getOfficeApplyExXjByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_apply_ex_xj获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeApplyExXj> getOfficeApplyExXjByUnitIdPage(String unitId, Pagination page);
	public void addOfficeApplyExXjs(List<OfficeApplyExXj> officeApplyExXjs);
	
	/**
	 * 根据申请id查询详细表Id
	 */
	public List<OfficeApplyExXj> getOfficeApplyExXjByApplyId(String unitId,String officeBoardroomApplyId);
	
	/**
	 * 通过申请表id获取详细表id
	 */
	public String[] getOfficeApplyDetailsXjByIds(String[] applyRoomIds) ;
}