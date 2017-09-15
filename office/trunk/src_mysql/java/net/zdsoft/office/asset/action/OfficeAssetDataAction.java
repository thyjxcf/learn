package net.zdsoft.office.asset.action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.asset.entity.OfficeAssetApply;
import net.zdsoft.office.asset.entity.OfficeAssetCategory;
import net.zdsoft.office.asset.service.OfficeAssetApplyService;
import net.zdsoft.office.asset.service.OfficeAssetCategoryService;
import net.zdsoft.office.goodmanage.constant.OfficeGoodsConstants;
import net.zdsoft.office.goodmanage.entity.OfficeGoods;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsChangeLog;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsType;
import net.zdsoft.office.goodmanage.service.OfficeGoodsChangeLogService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsTypeService;

import com.opensymphony.xwork2.ModelDriven;

public class OfficeAssetDataAction extends OfficeAssetCommonAction implements ModelDriven<OfficeAssetApply>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3035151557755475669L;
	
	private OfficeAssetApply asset = new OfficeAssetApply();
	private List<OfficeAssetApply> assetList = new ArrayList<OfficeAssetApply>();
	private OfficeAssetApplyService officeAssetApplyService;
	private OfficeAssetCategoryService officeAssetCategoryService;
	private String queryCategoryId;
	private Date queryBeginDate;
	private Date queryEndDate;
	
	private OfficeGoods officeGoods = new OfficeGoods();
	private List<OfficeGoodsType> goodsTypeList = new ArrayList<OfficeGoodsType>();
	private OfficeGoodsService officeGoodsService;
	private OfficeGoodsChangeLogService officeGoodsChangeLogService;
	private OfficeGoodsTypeService officeGoodsTypeService;
	private UserService userService;
	
	public String execute(){
		return SUCCESS;
	}
	
	public String list(){
		asset.setUnitId(getUnitId());
		asset.setPurchaseState("2");//采购通过的
		if(StringUtils.isNotBlank(queryCategoryId)){
			asset.setCategoryId(queryCategoryId);
		}
		
		assetList = officeAssetApplyService.getAssetData(asset, queryBeginDate, queryEndDate, getPage());
		for(OfficeAssetApply it : assetList){
			DecimalFormat df = new DecimalFormat("0.00");
			it.setPriceStr(df.format(it.getPurchasePrice()));
			it.setTotalPriceStr(df.format(it.getPurchaseTotalPrice()));
		}
		return SUCCESS;
	}

	public String assetExport(){
		asset.setUnitId(getUnitId());
		asset.setPurchaseState("2");//采购通过的
		if(StringUtils.isNotBlank(queryCategoryId)){
			asset.setCategoryId(queryCategoryId);
		}
		
		assetList = officeAssetApplyService.getAssetData(asset, queryBeginDate, queryEndDate, getPage());
		
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER);
		zdExcel.add(new ZdCell("资产汇总", 8, 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		
		List<ZdCell>zdlist=new ArrayList<ZdCell>();
		zdlist.add(new ZdCell("类别",1,style2));
		zdlist.add(new ZdCell("物品名称",1,style2));
		zdlist.add(new ZdCell("单位",1,style2));
		zdlist.add(new ZdCell("数量",1,style2));
		zdlist.add(new ZdCell("单价",1,style2));
		zdlist.add(new ZdCell("总价",1,style2));
		zdlist.add(new ZdCell("申请人",1,style2));
		zdlist.add(new ZdCell("采购时间",1,style2));
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		
		for(OfficeAssetApply item : assetList){
			DecimalFormat df = new DecimalFormat("0.00");
			item.setPriceStr(df.format(item.getPurchasePrice()));
			item.setTotalPriceStr(df.format(item.getPurchaseTotalPrice()));
			
			ZdCell[] cells = new ZdCell[8];
			cells[0] = new ZdCell(item.getCategoryName(), 1, style3);
			cells[1] = new ZdCell(item.getAssetName(), 1, style3);
			cells[2] = new ZdCell(item.getAssetUnit(), 1, style3);
			cells[3] = new ZdCell(item.getAssetNumber()+"", 1, style3);
			cells[4] = new ZdCell(item.getPriceStr(), 1, style3);
			cells[5] = new ZdCell(item.getTotalPriceStr(), 1, style3);
			cells[6] = new ZdCell(item.getApplyUserName(), 1, style3);
			cells[7] = new ZdCell(DateUtils.date2StringByDay(item.getPurchaseDate()), 1, style3);
			zdExcel.add(cells);
		}
		
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<8;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("office_asset_data");
		return NONE;
	}
	
	public String doAssetToGoods() {//TODO
		asset = officeAssetApplyService.getOfficeAssetApplyById(asset.getId());
		goodsTypeList = officeGoodsTypeService.getOfficeGoodsTypeByUnitIdList(getUnitId());
		
		officeGoods = new OfficeGoods();
		officeGoods.setUnitId(asset.getUnitId());
		officeGoods.setName(asset.getAssetName());
		officeGoods.setModel(asset.getAssetFormat());
		officeGoods.setAmount(asset.getAssetNumber());
		officeGoods.setGoodsUnit(asset.getAssetUnit());
		officeGoods.setPrice(Float.parseFloat(asset.getPurchasePrice().toString()));
		officeGoods.setAddUserId(asset.getApplyUserId());
		officeGoods.setPurchaseDate(asset.getPurchaseDate());
		return SUCCESS;
	}
	
	public String save() {
		try{
			if("0".equals(officeGoods.getIsReturnedStr())){
				officeGoods.setIsReturned(false);
			}else{
				officeGoods.setIsReturned(true);
			}
			if(StringUtils.isBlank(officeGoods.getUnitId())){
				officeGoods.setUnitId(getUnitId());
			}
			if(StringUtils.isBlank(officeGoods.getAddUserId())){
				officeGoods.setAddUserId(getLoginUser().getUserId());
			}
			officeGoods.setCreationTime(new Date());
			officeGoods.setReqTag(OfficeGoodsConstants.GOODS_HAS_NOT_REQ);
			
			officeGoodsService.save(officeGoods);
			
			//变更记录
			OfficeGoodsChangeLog goodsChangeLog = new OfficeGoodsChangeLog();
			goodsChangeLog.setAddUserId(officeGoods.getAddUserId());
			goodsChangeLog.setGoodsId(officeGoods.getId());
			goodsChangeLog.setReason(OfficeGoodsConstants.GOODS_REGISTER);
			goodsChangeLog.setAmount(officeGoods.getAmount());
			User user = userService.getUser(officeGoods.getAddUserId());
			goodsChangeLog.setRemark(OfficeGoodsConstants.GOODS_REGISTER + ",登记人：" + user.getRealname());
			goodsChangeLog.setCreationTime(new Date());
			
			officeGoodsChangeLogService.save(goodsChangeLog);
			
			asset = officeAssetApplyService.getOfficeAssetApplyById(asset.getId());
			asset.setIsSyncToGoods(true);
			officeAssetApplyService.update(asset);
			
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("物品登记成功！");
		} catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("物品登记失败！");
		}
		return SUCCESS;
	}
	
	public OfficeAssetApply getModel() {
		return asset;
	}

	public OfficeAssetApply getAsset() {
		return asset;
	}

	public void setAsset(OfficeAssetApply asset) {
		this.asset = asset;
	}

	public List<OfficeAssetApply> getAssetList() {
		return assetList;
	}

	public void setOfficeAssetApplyService(
			OfficeAssetApplyService officeAssetApplyService) {
		this.officeAssetApplyService = officeAssetApplyService;
	}
	
	public void setOfficeAssetCategoryService(
			OfficeAssetCategoryService officeAssetCategoryService) {
		this.officeAssetCategoryService = officeAssetCategoryService;
	}

	public List<OfficeAssetCategory> getAssetCategoryList(){
		return officeAssetCategoryService.getOfficeAssetCategoryList(getUnitId());
	}

	public String getQueryCategoryId() {
		return queryCategoryId;
	}

	public void setQueryCategoryId(String queryCategoryId) {
		this.queryCategoryId = queryCategoryId;
	}

	public Date getQueryBeginDate() {
		return queryBeginDate;
	}

	public void setQueryBeginDate(Date queryBeginDate) {
		this.queryBeginDate = queryBeginDate;
	}

	public Date getQueryEndDate() {
		return queryEndDate;
	}

	public void setQueryEndDate(Date queryEndDate) {
		this.queryEndDate = queryEndDate;
	}

	public OfficeGoods getOfficeGoods() {
		return officeGoods;
	}

	public void setOfficeGoods(OfficeGoods officeGoods) {
		this.officeGoods = officeGoods;
	}

	public List<OfficeGoodsType> getGoodsTypeList() {
		return goodsTypeList;
	}

	public void setOfficeGoodsService(OfficeGoodsService officeGoodsService) {
		this.officeGoodsService = officeGoodsService;
	}

	public void setOfficeGoodsChangeLogService(
			OfficeGoodsChangeLogService officeGoodsChangeLogService) {
		this.officeGoodsChangeLogService = officeGoodsChangeLogService;
	}

	public void setOfficeGoodsTypeService(
			OfficeGoodsTypeService officeGoodsTypeService) {
		this.officeGoodsTypeService = officeGoodsTypeService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
