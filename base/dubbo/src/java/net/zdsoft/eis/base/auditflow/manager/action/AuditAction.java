/* 
 * @(#)AuditAction.java    Created on 2012-11-2
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.base.auditflow.manager.service.FlowAuditService;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.form.Field;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.keelcnet.config.BootstrapManager;

import org.apache.commons.lang.StringUtils;

/**
 * 审核
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-11-2 下午01:27:58 $
 */
public abstract class AuditAction extends AbstractApplyAuditAction {
	private static final long serialVersionUID = -6981205622701176751L;

	private static List<String[]> statuses = null;
	static {
		statuses = new ArrayList<String[]>();
		statuses.add(new String[] { String.valueOf(FlowAudit.STATUS_CHECKING), "待审核" });
		statuses.add(new String[] { String.valueOf(FlowAudit.STATUS_AUDIT_PASS), "通过" });
		statuses.add(new String[] { String.valueOf(FlowAudit.STATUS_AUDIT_REJECT), "不通过" });
	}

	private FlowAuditService flowAuditService;
	


	private FlowAudit audit = new FlowAudit();
	
    private String awardFilePath;//获奖证书  路径
    
    private String awardFileName;//获奖证书 文件名
    
	public String applyOrAudit="audit";//判断是申请页面还是审核页面
    
	public List<String[]> getStatuses() {
		return statuses;
	}

	/**
	 * 列表信息
	 * 
	 * @return
	 */
	public String list() {
		if (status == 0) {
			status = FlowAudit.STATUS_CHECKING;
		}
		
		applys = flowApplyService.getAuditApplys(getApplyBusinessService(), getLoginInfo()
				.getUnitID(), businessType, status, operateType, applyUnitId, getPage(), getParamMap());
		return SUCCESS;
	}

	/**
	 * 显示编辑页面
	 * 
	 * @return
	 */
	public String edit() {
		String auditId = audit.getId();
		audit = flowAuditService.getFlowAudit(auditId);
		apply = flowApplyService.getFlowApply(getApplyBusinessService(), audit.getApplyId());
		List<Field> fieldValues =apply.getBusiness().getFieldValues();
		if (fieldValues != null) {
			for (Field field : fieldValues) {
				if (field.getName().equals("awardFile")) {
					field.setWrappedValue(apply.getBusiness().getFileName());
					field.setValue(apply.getBusiness().getFilePath());
				}
			}
		}
		return SUCCESS;
	}

	/**
	 * 审核：单个审核、批量审核
	 * 
	 * @return
	 */
	public String save() {
		// 单个审核
		if (StringUtils.isNotBlank(audit.getId())) {
			ids = new String[1];
			ids[0] = audit.getId();
		}

		audit.setAuditDate(new Date());
		audit.setAuditUserId(getLoginInfo().getUser().getId());
		audit.setAuditUsername(getLoginInfo().getUser().getRealname());

		flowApplyService.saveAudits(getApplyBusinessService(), audit, ids);

		return back();
	}
	
	public String back(String message){
		return back(message,"audit-list.action");
	}
	public String back(){
		return back("审核成功","audit-list.action");
	}

	/**
	 * 获取申请单位信息
	 * @return
	 */
	public List<Unit> getApplyUnitList(){
		return flowAuditService.getApplyUnits(getLoginInfo().getUnitID(), businessType, status, operateType);
	}
	
	public void dynGetApplyUnitList(){
		//动态获取单位列表 AJAX
		List<Unit> unitList = getApplyUnitList();
		JSONObject jsonObject = new JSONObject();
		if(unitList!=null){
			for(Unit u:unitList){
				jsonObject.put(u.getId(), u.getName());
			}
		}
		try {
			ServletUtils.print(getResponse(), jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    //下载附件
	public void downloadFile() throws ServletException,IOException{
		if (StringUtils.isNotEmpty(awardFilePath)) {
			File file = new File(BootstrapManager.getStoreHome() + File.separator
					+ awardFilePath);
			ServletUtils.download(file, getRequest(), getResponse(), awardFileName);
		}
	}

	public FlowAudit getAudit() {
		return audit;
	}

	public void setAudit(FlowAudit audit) {
		this.audit = audit;
	}

	public void setFlowAuditService(FlowAuditService flowAuditService) {
		this.flowAuditService = flowAuditService;
	}

	public List<String[]> getModels() {
		return models;
	}

	public String getAwardFilePath() {
		return awardFilePath;
	}

	public void setAwardFilePath(String awardFilePath) {
		this.awardFilePath = awardFilePath;
	}

	public String getAwardFileName() {
		return awardFileName;
	}

	public void setAwardFileName(String awardFileName) {
		this.awardFileName = awardFileName;
	}

	public String getApplyOrAudit() {
		return applyOrAudit;
	}

	public void setApplyOrAudit(String applyOrAudit) {
		this.applyOrAudit = applyOrAudit;
	}
	
}
