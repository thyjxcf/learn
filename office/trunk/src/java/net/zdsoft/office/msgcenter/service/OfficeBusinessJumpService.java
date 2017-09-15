package net.zdsoft.office.msgcenter.service;

import java.util.*;
import net.zdsoft.office.msgcenter.entity.OfficeBusinessJump;
import net.zdsoft.keel.util.Pagination;
/**
 * office_business_jump
 * @author 
 * 
 */
public interface OfficeBusinessJumpService{

	/**
	 * 新增office_business_jump
	 * @param officeBusinessJump
	 * @return
	 */
	public OfficeBusinessJump save(OfficeBusinessJump officeBusinessJump);
	
	public void batchSave(List<OfficeBusinessJump> list);

	/**
	 * 根据ids数组删除office_business_jump数据
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
	 * 根据UnitId获取office_business_jump列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeBusinessJump> getOfficeBusinessJumpByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_business_jump
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeBusinessJump> getOfficeBusinessJumpByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据msgId获取
	 * @param msgId
	 * @return
	 */
	public OfficeBusinessJump getOfficeBusinessJumpByMsgId(String msgId);
	

	/**
	 * 发送消息时绑定跳转相应业务URL(手机端)
	 * @param obj
	 * @param type
	 * @param userIds
	 */
	public void pushMsgUrlToMobile(Object obj, Integer type, String[] userIds, int jumpState);
	
	/**
	 * 发送消息时绑定跳转相应业务URL
	 * 参数包括：消息ID，模块ID，接收者，接收者类别，跳转的URL，选中的TAB页(非必)，需要的权限roleCode(非必)，
	 * 系统参数sysOption(如"GOODS.AUDIT.MODEL_1"，前面表示iniid，后面表示需要的nowvalue，非必)
	 * @param obj
	 * @param type
	 * @param msgId
	 */
	public void pushMsgUrl(Object obj, Integer type, String msgId);

	
	/**
	 * 判断是否需要审核
	 * @param objId
	 * @param objType
	 * @return
	 */
	public boolean checkState(String objId, String objType);
}