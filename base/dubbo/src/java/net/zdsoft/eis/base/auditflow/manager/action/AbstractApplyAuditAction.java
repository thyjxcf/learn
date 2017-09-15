/* 
 * @(#)AbstractApplyAuditAction.java    Created on 2012-11-5
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.zdsoft.eis.base.auditflow.manager.entity.ApplyBusiness;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowApply;
import net.zdsoft.eis.base.auditflow.manager.service.ApplyBusinessService;
import net.zdsoft.eis.base.auditflow.manager.service.FlowApplyService;
import net.zdsoft.eis.base.auditflow.manager.service.SchFlowApplyService;
import net.zdsoft.eis.base.form.Field;
import net.zdsoft.eis.base.form.FieldService;
import net.zdsoft.eis.frame.action.PageSemesterAction;
import net.zdsoft.keel.util.ServletUtils;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-11-5 上午10:58:24 $
 */
public abstract class AbstractApplyAuditAction extends PageSemesterAction {

	private static final long serialVersionUID = 6981490500481574330L;

	public static final int JSON_SUCCESS = 1;
	public static final int JSON_FAILURE = 0;

	protected Map<String, Object> jsonMap = new HashMap<String, Object>();

	protected String responseJSON() {
		return responseJSON(jsonMap);
	}

	protected String responseSuccessJSON() {
		jsonMap.put("result", JSON_SUCCESS);
		return responseJSON();
	}

	protected String responseSuccessJSON(String msg) {
		jsonMap.put("msg", msg);
		return responseSuccessJSON();
	}

	protected String responseJSON(Map<String, Object> jsonMap) {
		JSONObject jsonObject = new JSONObject();
		try{
			JsonConfig jsonConfig = new JsonConfig(); 
			jsonConfig.setExcludes(new String[]{"file","contentsAsStream"}); 
//			for (String key : jsonMap.keySet()) {
//				jsonObject.put(key, jsonMap.get(key));
//			}
			jsonObject.putAll(jsonMap, jsonConfig);
		}catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ServletUtils.print(getResponse(), jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}

	protected FlowApplyService flowApplyService;
	protected SchFlowApplyService schFlowApplyService;
	private FieldService fieldService;

	protected FlowApply apply;
	protected List<FlowApply> applys;
	protected int operateType;// 操作类型
	protected int status;// 状态
	protected int businessType;// 类型
	protected String modId;		//模块
	protected String[] ids;// 申请或审核id
	protected List<String[]> models;
	protected String applyUnitId;
	public Map<String,String> paramMap = new HashMap<String, String>();

	/**
	 * 取状态列表
	 * 
	 * @return
	 */
	public abstract List<String[]> getStatuses();

	/**
	 * 操作列表
	 * 
	 * @return
	 */
	public List<String[]> getOperateTypes() {
		return FlowApply.OPERATE_TYPE_LIST;
	}

	/**
	 * 业务列表字段
	 * 
	 * @return
	 */
	public List<Field> getListFieldHeads() {
		return fieldService.getListFieldHeads(businessType);
	}

	/**
	 * 字段列表
	 * 
	 * @return
	 */
	public List<Field> getFields() {
		List<Field>  fields = fieldService.getFiledList(getBusinessType(),getApplyBusinessService());
		return fields;
	}
	
	/**
	 * 拥有者列表字段
	 * 
	 * @return
	 */
	public List<String> getOwnerListFieldHeads() {
		return getApplyBusinessService().getBusinessOwnerService().getFieldHeads();
	}

	public List<FlowApply> getApplys() {
		return applys;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public FlowApply getApply() {
		if (null == apply) {
			apply = new FlowApply(getApplyBusinessService());
		}
		return apply;
	}

	public void setApply(FlowApply apply) {
		this.apply = apply;
	}

	public void setFlowApplyService(FlowApplyService flowApplyService) {
		this.flowApplyService = flowApplyService;
	}
	
	public void setSchFlowApplyService(SchFlowApplyService schFlowApplyService) {
		this.schFlowApplyService = schFlowApplyService;
	}

	public void setFieldService(FieldService fieldService) {
		this.fieldService = fieldService;
	}

	public abstract ApplyBusinessService<ApplyBusiness> getApplyBusinessService();
	public Map<String,String> getParamMap(){
		return paramMap;
	}

	public String getBusinessMacroPage() {
		return getApplyBusinessService().getMacroPage();
	}

	public String getOwnerMacroPage() {
		return getApplyBusinessService().getBusinessOwnerService().getMacroPage();
	}
	
	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public List<String[]> getModels() {
		return models;
	}

	public String getApplyUnitId() {
		return applyUnitId;
	}

	public void setApplyUnitId(String applyUnitId) {
		this.applyUnitId = applyUnitId;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}
	
	public String back(String message,String action,boolean bool) {
		if(StringUtils.isBlank(message)){
			message="操作成功";
		}
		promptMessageDto.setOperateSuccess(bool);
		promptMessageDto.setPromptMessage(message);
		promptMessageDto
				.addHiddenText(new String[] { "businessType", String.valueOf(businessType) });
		promptMessageDto.addHiddenText(new String[] { "status", String.valueOf(status) });
		promptMessageDto.addHiddenText(new String[]{ "modId", modId });
		for(Map.Entry<String, String> entry:paramMap.entrySet()){
			promptMessageDto.addHiddenText(new String[]{ entry.getKey(),entry.getValue() });
		}
		promptMessageDto.addOperation(new String[] { "返回", action });
		return PROMPTMSG;
	}
	
	public String back(String message,String action) {
		return back(message,action,true);
	}
	

}
