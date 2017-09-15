package net.zdsoft.office.asset.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.office.asset.entity.OfficeAssetPurchaseOpinion;
import net.zdsoft.office.asset.service.OfficeAssetPurchaseOpinionService;

import com.opensymphony.xwork2.ModelDriven;

public class OfficeAssetOpinionAction extends BaseAction implements ModelDriven<OfficeAssetPurchaseOpinion>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6190772362673035479L;
	
	private OfficeAssetPurchaseOpinion opinion = new OfficeAssetPurchaseOpinion();
	private List<OfficeAssetPurchaseOpinion> opinionlist = new ArrayList<OfficeAssetPurchaseOpinion>();
	private OfficeAssetPurchaseOpinionService officeAssetPurchaseOpinionService;
	
	public String execute() throws Exception{
		String unitId = getUnitId();
		opinionlist = officeAssetPurchaseOpinionService.getOfficeAssetPurchaseOpinionByUnitIdList(unitId);
		return SUCCESS;
	}
	
	public String add(){
		opinion.setUnitId(getUnitId());
		return SUCCESS;
	}
	
	public String edit(){
		opinion = officeAssetPurchaseOpinionService.getOfficeAssetPurchaseOpinionById(opinion.getId());
		return SUCCESS;
	}
	
	public String save(){
		try{
			if(StringUtils.isNotBlank(opinion.getId())){
				officeAssetPurchaseOpinionService.update(opinion);
			}else{
				List<OfficeAssetPurchaseOpinion> oldOpinionList = officeAssetPurchaseOpinionService
						.getOfficeAssetPurchaseOpinionList(opinion.getUnitId(), opinion.getType(), opinion.getContent());
				if(oldOpinionList.size() > 0){
					promptMessageDto.setErrorMessage("已存在相同的意见内容，请重新维护！");
					return SUCCESS;
				}else{
					officeAssetPurchaseOpinionService.save(opinion);
				}
			}
			promptMessageDto.setOperateSuccess(true);
		}catch (Exception e) {
			promptMessageDto.setErrorMessage("操作异常："+e.getMessage());
		}
		return SUCCESS;
	}
	
	public String delete(){
		try {
			officeAssetPurchaseOpinionService.delete(new String[]{opinion.getId()});
			promptMessageDto.setOperateSuccess(true);
		} catch (Exception e) {
			promptMessageDto.setErrorMessage("操作异常："+e.getMessage());
		}
		return SUCCESS;
	}
	
	public OfficeAssetPurchaseOpinion getModel() {
		return opinion;
	}

	public OfficeAssetPurchaseOpinion getOpinion() {
		return opinion;
	}

	public void setOpinion(OfficeAssetPurchaseOpinion opinion) {
		this.opinion = opinion;
	}

	public List<OfficeAssetPurchaseOpinion> getOpinionlist() {
		return opinionlist;
	}

	public void setOfficeAssetPurchaseOpinionService(
			OfficeAssetPurchaseOpinionService officeAssetPurchaseOpinionService) {
		this.officeAssetPurchaseOpinionService = officeAssetPurchaseOpinionService;
	}

}
