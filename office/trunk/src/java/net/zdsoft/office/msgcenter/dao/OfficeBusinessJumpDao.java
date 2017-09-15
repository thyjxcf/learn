package net.zdsoft.office.msgcenter.dao;

import java.util.*;

import net.zdsoft.office.msgcenter.entity.OfficeBusinessJump;
import net.zdsoft.keel.util.Pagination;
/**
 * office_business_jump
 * @author 
 * 
 */
public interface OfficeBusinessJumpDao{

	/**
	 * 新增office_business_jump
	 * @param officeBusinessJump
	 * @return
	 */
	public OfficeBusinessJump save(OfficeBusinessJump officeBusinessJump);

	public void batchSave(List<OfficeBusinessJump> list);
	
	/**
	 * 根据ids数组删除office_business_jump
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_business_jump
	 * @param officeBusinessJump
	 * @return
	 */
	public Integer update(OfficeBusinessJump officeBusinessJump);

	/**
	 * 根据id获取office_business_jump
	 * @param id
	 * @return
	 */
	public OfficeBusinessJump getOfficeBusinessJumpById(String id);

	/**
	 * 根据ids数组查询office_business_jumpmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeBusinessJump> getOfficeBusinessJumpMapByIds(String[] ids);

	/**
	 * 获取office_business_jump列表
	 * @return
	 */
	public List<OfficeBusinessJump> getOfficeBusinessJumpList();

	/**
	 * 分页获取office_business_jump列表
	 * @param page
	 * @return
	 */
	public List<OfficeBusinessJump> getOfficeBusinessJumpPage(Pagination page);

	/**
	 * 根据unitId获取office_business_jump列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeBusinessJump> getOfficeBusinessJumpByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_business_jump获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeBusinessJump> getOfficeBusinessJumpByUnitIdPage(String unitId, Pagination page);
	
	public List<OfficeBusinessJump> getOfficeBusinessJumpByMsgId(String msgId);
}