package net.zdsoft.office.dailyoffice.service;

import java.util.*;
import net.zdsoft.office.dailyoffice.entity.OfficeMeetingAudit;
import net.zdsoft.keel.util.Pagination;
/**
 * office_meeting_audit
 * @author 
 * 
 */
public interface OfficeMeetingAuditService{

	/**
	 * 新增office_meeting_audit
	 * @param officeMeetingAudit
	 * @return
	 */
	public OfficeMeetingAudit save(OfficeMeetingAudit officeMeetingAudit);

	/**
	 * 根据ids数组删除office_meeting_audit数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_meeting_audit
	 * @param officeMeetingAudit
	 * @return
	 */
	public Integer update(OfficeMeetingAudit officeMeetingAudit);

	/**
	 * 根据id获取office_meeting_audit
	 * @param id
	 * @return
	 */
	public OfficeMeetingAudit getOfficeMeetingAuditById(String id);

	/**
	 * 根据ids数组查询office_meeting_auditmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeMeetingAudit> getOfficeMeetingAuditMapByIds(String[] ids);

	/**
	 * 获取office_meeting_audit列表
	 * @return
	 */
	public List<OfficeMeetingAudit> getOfficeMeetingAuditList();

	/**
	 * 分页获取office_meeting_audit列表
	 * @param page
	 * @return
	 */
	public List<OfficeMeetingAudit> getOfficeMeetingAuditPage(Pagination page);

	/**
	 * 根据UnitId获取office_meeting_audit列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeMeetingAudit> getOfficeMeetingAuditByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_meeting_audit
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeMeetingAudit> getOfficeMeetingAuditByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据applyId获取office_meeting_audit
	 * @param applyId
	 * @return
	 */
	public OfficeMeetingAudit getOfficeMeetingAuditByApplyId(String applyId);
	
	public Map<String, OfficeMeetingAudit> getOfficeMeetingAuditMapByApplyIds(String[] applyIds);
}