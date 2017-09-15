package net.zdsoft.office.asset.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.office.asset.entity.OfficeAssetApply;
import net.zdsoft.office.asset.entity.OfficeAssetCategory;
import net.zdsoft.office.asset.service.OfficeAssetApplyService;
import net.zdsoft.office.asset.service.OfficeAssetCategoryService;
import net.zdsoft.office.asset.service.OfficeAssetPurchaseOpinionService;

import com.opensymphony.xwork2.ModelDriven;

public class OfficeAssetPurchaseAction extends OfficeAssetCommonAction implements ModelDriven<OfficeAssetApply>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OfficeAssetApplyService officeAssetApplyService;
	
	private OfficeAssetCategoryService officeAssetCategoryService;
	
	private OfficeAssetPurchaseOpinionService officeAssetPurchaseOpinionService;
	
	private List<OfficeAssetApply> purchaseList;
	
	private OfficeAssetApply purchase = new OfficeAssetApply();
	
	private String stateQuery;
	
	private List<String> passOpinion;
	
	private List<String> faileOpinion;
	
	public String execute(){
		return SUCCESS;
	}
	
	public String purchaseList(){
		if(isAssetPurAuth()){
			purchaseList = officeAssetApplyService.getOfficeAssetApplyPurchaseList(getUnitId(), null, stateQuery, false, getPage());
		}else{
			purchaseList = officeAssetApplyService.getOfficeAssetApplyPurchaseList(getUnitId(), getLoginInfo().getUser().getId(), stateQuery, false, getPage());
		}
		return SUCCESS;
	}

	public String purchaseEdit(){
		purchase = officeAssetApplyService.getOfficeAssetApplyById(purchase.getId());
		Map<String, OfficeAssetCategory> cateMap = officeAssetCategoryService.getOfficeAssetCategoryMap(getUnitId());
		if(cateMap.containsKey(purchase.getCategoryId())){
			purchase.setCategoryName(cateMap.get(purchase.getCategoryId()).getAssetName());
		}else{
			purchase.setCategoryName("类别已删除");
		}
		return SUCCESS;
	}
	
	public String savePurchase(){
		OfficeAssetApply ent = officeAssetApplyService.getOfficeAssetApplyById(purchase.getId());
		ent.setPurchasePrice(purchase.getPurchasePrice());
		ent.setPurchaseTotalPrice(purchase.getPurchaseTotalPrice());
		ent.setPurchaseDate(purchase.getPurchaseDate());
		ent.setPurchaseUserid1(getLoginInfo().getUser().getId());
		ent.setCreationTime(new Date());
		try {
			String returnMsg = officeAssetApplyService.savePurchaseInfo(ent);
			if(StringUtils.isNotBlank(returnMsg)){
				returnMsg = "保存成功："+returnMsg;
			}else{
				returnMsg = "保存成功";
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage(returnMsg);
		} catch (Exception e) {
			promptMessageDto.setErrorMessage("操作异常："+e.getMessage());
		}
		return SUCCESS;
	}
	
	public String purchaseAudit(){
		purchaseList = officeAssetApplyService.getOfficeAssetApplyPurchaseList(getUnitId(), null, stateQuery, true, getPage());
		return SUCCESS;
	}
	
	public String purchaseAuditEdit(){
		purchase = officeAssetApplyService.getOfficeAssetApplyById(purchase.getId());
		Map<String, OfficeAssetCategory> cateMap = officeAssetCategoryService.getOfficeAssetCategoryMap(getUnitId());
		if(cateMap.containsKey(purchase.getCategoryId())){
			purchase.setCategoryName(cateMap.get(purchase.getCategoryId()).getAssetName());
		}else{
			purchase.setCategoryName("类别已删除");
		}
		passOpinion = officeAssetPurchaseOpinionService.getOpinionByType(getUnitId(), "1");
		faileOpinion = officeAssetPurchaseOpinionService.getOpinionByType(getUnitId(), "2");
		return SUCCESS;
	}
	
	public String savePurchaseAudit(){
		try {
			OfficeAssetApply udpateObj = officeAssetApplyService.getOfficeAssetApplyById(purchase.getId());
			udpateObj.setPurchaseState(purchase.getPurchaseState());
			udpateObj.setPurchaseOpinion(purchase.getPurchaseOpinion());
			officeAssetApplyService.update(udpateObj);
			promptMessageDto.setOperateSuccess(true);
		} catch (Exception e) {
			promptMessageDto.setErrorMessage("操作异常："+e.getMessage());
		}
		return SUCCESS;
	}

	public OfficeAssetApply getModel() {
		return purchase;
	}


	public OfficeAssetApply getPurchase() {
		return purchase;
	}


	public void setPurchase(OfficeAssetApply purchase) {
		this.purchase = purchase;
	}


	public String getStateQuery() {
		return stateQuery;
	}


	public void setStateQuery(String stateQuery) {
		this.stateQuery = stateQuery;
	}


	public List<OfficeAssetApply> getPurchaseList() {
		return purchaseList;
	}

	public void setOfficeAssetApplyService(
			OfficeAssetApplyService officeAssetApplyService) {
		this.officeAssetApplyService = officeAssetApplyService;
	}

	public void setOfficeAssetCategoryService(
			OfficeAssetCategoryService officeAssetCategoryService) {
		this.officeAssetCategoryService = officeAssetCategoryService;
	}

	public void setOfficeAssetPurchaseOpinionService(
			OfficeAssetPurchaseOpinionService officeAssetPurchaseOpinionService) {
		this.officeAssetPurchaseOpinionService = officeAssetPurchaseOpinionService;
	}

	public List<String> getPassOpinion() {
		return passOpinion;
	}

	public List<String> getFaileOpinion() {
		return faileOpinion;
	}

}
