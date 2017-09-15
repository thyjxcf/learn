package net.zdsoft.office.convertflow.dao;


import java.util.*;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.convertflow.entity.OfficeConvertFlow;
/**
 * office_convert_flow
 * @author 
 * 
 */
public interface OfficeConvertFlowDao{

	/**
	 * 新增office_convert_flow
	 * @param ent
	 * @return TODO
	 */
	public OfficeConvertFlow save(OfficeConvertFlow ent);

	/**
	 * 更新office_convert_flow
	 * @param officeConvertFlow
	 * @return
	 */
	public void update(Integer status, String businessId);
	
	/**
	 * 通过businessId获取对象
	 * @param businessId
	 * @return
	 */
	public OfficeConvertFlow getObjByBusinessId(String businessId);
	
	/**
	 * 根据参数获取审核list
	 * @param unitId
	 * @param applyUserIds TODO
	 * @param userId 审核人或者其他审核参数 如物品id
	 * @param otherAuditParm TODO
	 * @param dataType 审核状态
	 * @param type 业务类型 如请假、物品等
	 * @param page
	 * @return
	 */
	public List<OfficeConvertFlow> getAuditList(String unitId, String[] applyUserIds, String userId, String otherAuditParm, String dataType, Integer type, Pagination page);
	
	/**
	 * 已处理数据获取
	 * @param unitId
	 * @param applyUserIds TODO
	 * @param userId 审核人或者其他审核参数 如物品id
	 * @param otherAuditParm 物品id等
	 * @param dataType 审核状态
	 * @param type 业务类型 如请假、物品等
	 * @param page
	 * @return
	 */
	public List<OfficeConvertFlow> getHaveDoAuditList(String unitId, String[] applyUserIds, String userId, String otherAuditParm, String dataType, Integer type, Pagination page);
	
	/**
	 * 根据参数获取申请list
	 * @param applyUserId申请人
	 * @param type 业务类型 如请假、物品等 
	 * @param page
	 * @return
	 */
	public List<OfficeConvertFlow> getApplyList(String applyUserId, Integer type, Pagination page);
	/**
	 * 通过businessId删除
	 * @param businessId
	 * @return
	 * 2016年9月11日
	 */
	public int deleteByBusinessId(String businessId);
}