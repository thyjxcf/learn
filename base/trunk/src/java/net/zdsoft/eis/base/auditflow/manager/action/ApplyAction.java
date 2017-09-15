/* 
 * @(#)ApplyAction.java    Created on 2012-11-2
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.zdsoft.eis.base.auditflow.manager.entity.ApplyBusiness;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowApply;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.base.auditflow.manager.service.ApplyBusinessService;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.exception.FileUploadFailException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

/**
 * 申请
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-11-2 下午01:26:59 $
 */
public class ApplyAction extends AbstractApplyAuditAction {

	private static final long serialVersionUID = 5325193592710033780L;
	private static final int fileMaxSize=20;//20M
	
	private static List<String[]> statuses = null;
	static {
		statuses = new ArrayList<String[]>();
		statuses.add(new String[] { String.valueOf(FlowApply.STATUS_PREPARING), "未提交" });
		statuses.add(new String[] { String.valueOf(FlowApply.STATUS_IN_AUDIT), "审核中" });
		statuses.add(new String[] { String.valueOf(FlowApply.STATUS_AUDIT_PASS), "通过" });
		statuses.add(new String[] { String.valueOf(FlowApply.STATUS_AUDIT_REJECT), "未通过" });
	}

	private List<FlowAudit> showSteps;

	public List<String[]> getStatuses() {
		return statuses;
	}

	public List<String[]> getOperateTypes() {
		return FlowApply.OPERATE_TYPE_LIST;
	}
    
	/**
	 * 列表信息
	 * 
	 * @return
	 */
	public String list() {
		applys = flowApplyService.getApplys(getApplyBusinessService(), getLoginInfo().getUnitID(),
				businessType, status, null, getPage(),getParamMap());
		return SUCCESS;
	}

	/**
	 * 显示增加页面
	 * 
	 * @return
	 */
	public String add() {
		apply = getApply();
		apply.setStatus(FlowApply.STATUS_PREPARING);
		apply.setBusinessType(businessType);
		return SUCCESS;
	}

	/**
	 * 显示编辑页面
	 * 
	 * @return
	 */
	public String edit() {
		apply = flowApplyService.getFlowApply(getApplyBusinessService(), apply.getId());
		return SUCCESS;
	}

	/**
	 * 保存
	 * 
	 * @return
	 */
	public String save() {//TODO
		if (StringUtils.isNotEmpty(apply.getId())) {
			UploadFile upFile=getUpFile();
			if (upFile!=null&&fileMaxSize>0&&(upFile.getFile().length()/1024)>fileMaxSize*1024) {
				 return back("保存失败    获奖证书不能大于" + fileMaxSize + "M");
			}
			apply.getBusiness().setUpfile(upFile);
			flowApplyService.updateFlowApply(apply);
		} else {
			User user = getLoginInfo().getUser();
			apply.setApplyUserId(user.getId());
			apply.setApplyUsername(user.getRealname());
			apply.setApplyUnitId(user.getUnitid());
			apply.setApplyDate(new Date());
			UploadFile upFile=getUpFile();
			if (upFile!=null&&fileMaxSize>0&&(upFile.getFile().length()/1024>fileMaxSize*1024)) {
				 return back("保存失败    获奖证书不能大于" + fileMaxSize + "M");
			}
			apply.getBusiness().setUpfile(upFile);
			flowApplyService.addFlowApply(apply);
		}
		return back("保存成功");
	}
	//获取附件
    public UploadFile getUpFile()
            throws FileUploadFailException {
        HttpServletRequest request = ServletActionContext.getRequest();
        if (request instanceof MultiPartRequestWrapper) {
            MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper) request;
            Enumeration<String> e = requestWrapper.getFileParameterNames();
            if (e.hasMoreElements()) {
                String fieldName = e.nextElement();
                File uploadedFile = (((File[]) requestWrapper.getFiles(fieldName))[0]);
                String fileName = requestWrapper.getFileNames(fieldName)[0];
                String contentTypes = requestWrapper.getContentTypes(fieldName)[0];
                UploadFile uploadfile = new UploadFile(fileName, uploadedFile, contentTypes,
                        fieldName);
                return uploadfile;
            }
        }
        return null;
    }
	
	public String viewBusiness() throws Exception {
		// 判断是否已有处理于流程中的申请
		FlowApply fa = flowApplyService.getFlowApplyInFlow(apply.getBusinessId());
		if (fa != null && (apply.getId() == null || !apply.getId().equals(fa.getId()))) {
			return responseSuccessJSON("该记录已存在未完成的申请信息，不能再添加新的申请");
		}

		ApplyBusiness business = getApplyBusinessService().getBusiness(apply.getBusinessId());
		jsonMap.put("business", business);
		return responseJSON();
	}

	public String back(String message,boolean bool){
		return back(message,"apply-list.action",bool);
	}
	public String back(String message){
		return back(message,"apply-list.action");
	}
	public String back(){
		return back("操作成功","apply-list.action");
	}
	//显示之公共校验
	public boolean validateShow(){
		if(FlowApply.STATUS_AUDIT_PASS!=status&&getApplys()!=null&&getApplys().size()!=0){
			return true;
		}
		return false;
	}
	//是否显示 删除申请 按钮 
	public boolean getShowDeleteButton(){
		if(validateShow()){
			if(FlowApply.STATUS_PREPARING==status){
				return true;
			}
			if(FlowApply.STATUS_AUDIT_REJECT==status){
				return true;
			}
		}
		return false;
	}
	//是否显示 提交申请 按键
	public boolean getShowSubmitButton(){
		if(validateShow()){
			if(FlowApply.STATUS_PREPARING==status){
				return true;
			}
		}
		return false;
		
	}
	
	//是否显示 撤消申请 按钮
	public boolean getShowCanelApplyButton(){
		if(validateShow()){
			if(FlowApply.STATUS_IN_AUDIT==status){
				return true;
			}
		}
		return false;
	}
	//是否显示 重新申请 按钮
	public boolean getShowReApplyButton(){
		if(validateShow()){
			if(FlowApply.STATUS_AUDIT_REJECT==status){
				return true;
			}
		}
		return false;
	}
	//是否显示 全选  按钮
	public boolean getShowAllSelectButton(){
		if(validateShow()){
			if(FlowApply.STATUS_PREPARING==status){
				return true;
			}
			if(FlowApply.STATUS_IN_AUDIT==status){
				return true;
			}
			if(FlowApply.STATUS_AUDIT_REJECT==status){
				return true;
			}
		}
		return false;
	}
	/**
	 * 删除申请：未提交和审核不通过
	 * 
	 * @return
	 */
	public String delete() {
		flowApplyService.deleteFlowApply(getApplyBusinessService(), ids);
		return back();
	}

	/**
	 * 提交申请：未提交和审核不通过后重新提交
	 * 
	 * @return
	 */
	public String submit() {
		if (apply != null){
			if (StringUtils.isNotEmpty(apply.getId())) {
				UploadFile upFile=getUpFile();
				if (upFile!=null&&fileMaxSize>0&&(upFile.getFile().length()/1024)>fileMaxSize*1024) {
					 return back("保存失败    获奖证书不能大于" + fileMaxSize + "M");
				}
				apply.getBusiness().setUpfile(upFile);
				flowApplyService.updateFlowApply(apply);
			} else {
				User user = getLoginInfo().getUser();
				apply.setApplyUserId(user.getId());
				apply.setApplyUsername(user.getRealname());
				apply.setApplyUnitId(user.getUnitid());
				apply.setApplyDate(new Date());
				UploadFile upFile=getUpFile();
				if (upFile!=null&&fileMaxSize>0&&(upFile.getFile().length()/1024>fileMaxSize*1024)) {
					 return back("保存失败    获奖证书不能大于" + fileMaxSize + "M");
				}
				apply.getBusiness().setUpfile(upFile);
				flowApplyService.addFlowApply(apply);
			}
			ids = new String[]{apply.getId()};
		}
		flowApplyService.saveSubmitFlowApply(ids);
		return back();
	}

	/**
	 * 撤消申请：审核中
	 * 
	 * @return
	 */
	public String cancel() {
		flowApplyService.saveCancelFlowApply(ids);
		return back();
	}

	/**
	 * 审核进度页面
	 * 
	 * @return
	 */
	public String showSteps() {
		showSteps = flowApplyService.getShowSteps(ids[0]);
		return SUCCESS;
	}
	
	/**
	 * 审核进度页面 学校内部审核
	 * 
	 * @return
	 */
	public String showStepsForSch() {
		showSteps = schFlowApplyService.getShowSteps(ids[0]);
		return SUCCESS;
	}

	public List<FlowAudit> getShowSteps() {
		return showSteps;
	}

	@Override
	public ApplyBusinessService<ApplyBusiness> getApplyBusinessService() {
		System.out.println("必须覆盖此方法");
		return null;
	}
	
	public List<String[]> getModels() {
		return models;
	}
	
}
