package net.zdsoft.eis.base.auditflow.manager.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.auditflow.manager.entity.ApplyBusiness;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.base.auditflow.template.entity.FlowInitiator;
import net.zdsoft.eis.base.auditflow.template.entity.FlowType;
import net.zdsoft.keel.util.Pagination;

public interface SchFlowAuditService {
	public List<FlowAudit> getFlowAudits(String arrangeId, String roleId,
			int businessType, int status, int operateType, String applyUnitId,
			Pagination page);

	public void deleteFlowAudits(String... applyIds);

	public FlowAudit getFlowAudit(String flowAuditId);

	public List<FlowAudit> getFlowAuditList(String[] ids);

	public Map<String, List<FlowAudit>> getFlowAuditsMap(String[] applyIds,
			int state);

	/**
	 * 根据申请id获得审核结果
	 * 
	 * @param applyId
	 * @return
	 */
	public List<FlowAudit> getFlowAudits(String applyId);

	/**
	 * 根据申请ids获得审核结果List
	 * 
	 * @param applyId
	 * @return
	 */
	public Map<String, List<FlowAudit>> getFlowAuditsMap(String[] applyIds);

	/**
	 * 创建流程
	 * 
	 * @param initiator
	 * @param flowType
	 * @return
	 */
	public int saveCreateSteps(
			ApplyBusinessService<ApplyBusiness> applyBusinessService,
			FlowInitiator initiator, FlowType flowType);

	/**
	 * 启动流程 <br>
	 * 方法通过给定的业务<tt>id</tt>,把其对应的第一个步骤的状态更新成审核中(CHECKING)状态,<br>
	 * 同时把这个业务流程中的其他步骤状态更新成未提交(PREPARING)状态.
	 * 
	 * @param applyId
	 */
	public void saveStartFlow(String applyId);

	/**
	 * 取消一个指定业务的流程.<br>
	 * 
	 * 方法通过给定的业务<tt>id</tt>,把其对应的所有步骤的状态更新成未提交(PREPARING)状态.
	 * 
	 * @param applyId
	 *            给定的业务<tt>id</tt>
	 */
	public void saveCancelFlow(String applyId);

	/**
	 * <p>
	 * 根据提供的步骤信息更新指定业务步骤的内容,同时返回所更新步骤在流程中的序号.<br>
	 * 方法会根据提供的步骤<tt>id</tt>来更新对应的信息,这些信息包括:
	 * <ul>
	 * <li>state(审核结果)</li>
	 * <li>审核意见</li>
	 * <li>审核时间</li>
	 * <li>操作人</li>
	 * </ul>
	 * </p>
	 * <p>
	 * 在更新的同时,该方法会根据所更新的state状态来进行不同的操作:
	 * <ul>
	 * <li>如果更新后的状态是审核通过状态(PASSED),<br>
	 * 则会更新该业务流程中下一个步骤的state为审核中状态(CHECKING).</li>
	 * <li>如果更新后的状态是未提交(PREPARING)或审核中(CHECKING)状态,<br>
	 * 则把该业务流程中在当前步骤后面所有步骤的state更新为未提交(PREPARING)状态.</li>
	 * <li>其他情况则只更新当前步骤的state.</li>
	 * </ul>
	 * </p>
	 * <p>
	 * 另外,方法会把该步骤的其他内容回填到<code>FlowAudit</code>中,包括以下内容:
	 * <ul>
	 * <li>业务<tt>id</tt></li>
	 * <li>业务类型</li>
	 * <li>申请单位<tt>id</tt></li>
	 * </ul>
	 * </p>
	 * 
	 * @param step
	 *            要更新的步骤信息对象
	 * @return 当前所更新的步骤在业务流程中的序号,序号从0开始,-1表示最后一步.
	 */
	public int saveUpOneStep(
			ApplyBusinessService<ApplyBusiness> applyBusinessService,
			FlowAudit step);
}
