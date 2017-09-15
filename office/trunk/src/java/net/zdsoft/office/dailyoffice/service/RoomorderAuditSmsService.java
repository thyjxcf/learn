package net.zdsoft.office.dailyoffice.service;

import java.util.*;
import net.zdsoft.office.dailyoffice.entity.RoomorderAuditSms;
import net.zdsoft.keel.util.Pagination;
/**
 * roomorder_audit_sms
 * @author 
 * 
 */
public interface RoomorderAuditSmsService{

	/**
	 * 新增roomorder_audit_sms
	 * @param roomorderAuditSms
	 * @return
	 */
	public RoomorderAuditSms save(RoomorderAuditSms roomorderAuditSms);

	/**
	 * 根据ids数组删除roomorder_audit_sms数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	public Integer deleteByUserId(String unitId, String userId);

	/**
	 * 更新roomorder_audit_sms
	 * @param roomorderAuditSms
	 * @return
	 */
	public Integer update(RoomorderAuditSms roomorderAuditSms);

	/**
	 * 根据id获取roomorder_audit_sms
	 * @param id
	 * @return
	 */
	public RoomorderAuditSms getRoomorderAuditSmsById(String id);

	/**
	 * 根据ids数组查询roomorder_audit_smsmap
	 * @param ids
	 * @return
	 */
	public Map<String, RoomorderAuditSms> getRoomorderAuditSmsMapByIds(String[] ids);

	/**
	 * 获取roomorder_audit_sms列表
	 * @return
	 */
	public List<RoomorderAuditSms> getRoomorderAuditSmsList();

	/**
	 * 分页获取roomorder_audit_sms列表
	 * @param page
	 * @return
	 */
	public List<RoomorderAuditSms> getRoomorderAuditSmsPage(Pagination page);

	/**
	 * 根据UnitId获取roomorder_audit_sms列表
	 * @param unitId
	 * @return
	 */
	public List<RoomorderAuditSms> getRoomorderAuditSmsByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取roomorder_audit_sms
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<RoomorderAuditSms> getRoomorderAuditSmsByUnitIdPage(String unitId, Pagination page);
	
	public RoomorderAuditSms getRoomorderAuditSmsByUserId(String unitId, String userId);
	
	/**
	 * 获取单位内接收短信的人员
	 * @param unitId
	 * @return
	 */
	public List<RoomorderAuditSms> getRoomorderAuditSmsList(String unitId);
}