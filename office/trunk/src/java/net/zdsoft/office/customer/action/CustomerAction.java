package net.zdsoft.office.customer.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.auditflow.manager.entity.FlowApply;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.Region;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.Mcode;
import net.zdsoft.eis.base.common.service.McodeService;
import net.zdsoft.eis.base.common.service.RegionService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.customer.constant.OfficeCustomerConstants;
import net.zdsoft.office.customer.entity.OfficeCustomerApply;
import net.zdsoft.office.customer.entity.OfficeCustomerFollowRecord;
import net.zdsoft.office.customer.entity.OfficeCustomerInfo;
import net.zdsoft.office.customer.entity.SearchCustomer;
import net.zdsoft.office.customer.service.OfficeCustomerApplyService;
import net.zdsoft.office.customer.service.OfficeCustomerDeptLeaderApplyService;
import net.zdsoft.office.customer.service.OfficeCustomerFollowRecordService;
import net.zdsoft.office.customer.service.OfficeCustomerInfoService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Sheet;


/**
 * 客户管理
 */
public class CustomerAction extends CustomerCommonAction{

	private static final long serialVersionUID = 1L;
	private RegionService regionService;
	private McodeService mcodeService;
	private DeptService deptService;
	private OfficeCustomerApplyService officeCustomerApplyService;
	private OfficeCustomerApply officeCustomerApply=new OfficeCustomerApply(); 
	
	private OfficeCustomerInfo officeCustomerInfo=new OfficeCustomerInfo();
	private OfficeCustomerInfoService officeCustomerInfoService;
	private OfficeCustomerDeptLeaderApplyService officeCustomerDeptLeaderApplyService;
	private OfficeCustomerFollowRecordService officeCustomerFollowRecordService;
	private OfficeCustomerFollowRecord officeRecord=new OfficeCustomerFollowRecord();
	private SearchCustomer searchCustomer=new SearchCustomer();
	private List<OfficeCustomerApply> customerApplyList;
	private List<OfficeCustomerInfo> customerInfoList;
	private List<OfficeCustomerFollowRecord> recordList;
	private boolean regionLeader;
	private boolean deptLeader;
	private boolean clientManager;
	
	private List<Dept> deptList;
	private List<User> userList;
	private List<Mcodedetail> mcodedetailList;
	private String applyId;
	private String transType;//是否转发
	private String placeType;
	private String libraryApply;//资源库中的调转
	private final String mcodeAttend="DM-JZZT";
	public void getCusRole(){
		regionLeader=isCustomRole(OfficeCustomerConstants.OFFICE_REGION_LEADER);
		deptLeader=officeCustomerApplyService.isDeptLeader(getLoginUser().getUnitId(), getLoginUser().getUserId());
		clientManager=isCustomRole(OfficeCustomerConstants.OFFICE_CLIENT_MANAGER);
	}
	@Override
	public String execute(){
		getCusRole();
		//将审核通过 但延期超过3天的入库
		officeCustomerApplyService.deletePutOffCustomer(getUnitId());
		//将资源库申请的初审不通过 3天未再次申请的数据入库
		
		return SUCCESS;
	} 
	//客户申请显示列表
	public String customerApplyList(){
		customerApplyList=officeCustomerApplyService.
				getOfficeCustomerApplyByUnitIdPage(getUnitId(), getLoginUser().getUserId(),new SearchCustomer(),getPage());
		return SUCCESS;
	}  
	//删除
	public String delete(){
		String erroMessage="删除失败";
		String successMessage="删除成功";
		try{
			if(StringUtils.isNotBlank(officeCustomerApply.getId())){
				officeCustomerApply=officeCustomerApplyService.getOfficeCustomerApplyById(officeCustomerApply.getId());
				officeCustomerApplyService.deleteAll(officeCustomerApply,applyId);
			}
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage(erroMessage);
			return SUCCESS;
		}
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.setPromptMessage(successMessage);
		return SUCCESS;
	}
	//放弃申请
	public String giveUp(){
		getCusRole();
		String erroMessage="提交失败";
		String successMessage="提交成功";
		try{
			String officeApplyId=officeCustomerApply.getId();
			if(StringUtils.isNotBlank(officeApplyId)){
				officeCustomerApply=officeCustomerApplyService.getOfficeCustomerApplyById(officeApplyId);
				officeCustomerInfoService.updateState(officeCustomerApply.getCustomerId(), OfficeCustomerConstants.INFO_LIBARAY, null);
				officeCustomerApplyService.updateIsDelete(true,officeApplyId);
			}
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage(erroMessage);
			return SUCCESS;
		}
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.setPromptMessage(successMessage);
		return SUCCESS;
	}
	//客户列表提交
	public String goApply(){
		getCusRole();
		String erroMessage="提交失败";
		String successMessage="提交成功";
		try{
		if(StringUtils.isNotBlank(officeCustomerApply.getId())){
			officeCustomerApply.setApplyId(applyId);
			if(deptLeader){
				officeCustomerDeptLeaderApplyService.update(officeCustomerApply);
			}else{
				officeCustomerApplyService.update(officeCustomerApply);
			}
		}
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage(erroMessage);
			return SUCCESS;
		}
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.setPromptMessage(successMessage);
		return SUCCESS;
	}
	//客户新增或编辑页面
	public String customerAdd(){
		getCusRole();
		Mcode mcode=mcodeService.getMcode(mcodeAttend);
		mcodedetailList=mcode.getMcodeDetailList();
		if(StringUtils.isNotBlank(officeCustomerApply.getId())){
			officeCustomerApply=officeCustomerApplyService.getOfficeCustomerApplyById(officeCustomerApply.getId());
			officeCustomerInfo=officeCustomerInfoService.getOfficeCustomerInfoById(officeCustomerApply.getCustomerId());
			if(StringUtils.isNotBlank(officeCustomerInfo.getProgressState())){
				officeCustomerInfo.setProgressStateInt(Integer.parseInt(officeCustomerInfo.getProgressState()));
			}
		}else{
			officeCustomerInfo.setUnitId(getUnitId());
			officeCustomerApply.setUnitId(getUnitId());
		}
		return SUCCESS;
	}
	//再次申请
	public String applyAgain(){
		getCusRole();
		String erroMessage="申请失败:";
		String successMessage="申请成功";
		try{
			officeCustomerApply.setApplyId(applyId);
			if(deptLeader){
				officeCustomerDeptLeaderApplyService.update(officeCustomerApply);
			}else{
				officeCustomerApplyService.update(officeCustomerApply);
			}
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage(erroMessage);
			return SUCCESS;
		}
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.setPromptMessage(successMessage);
		return SUCCESS;
	}
	//客户新增/编辑
	public String save(){
		getCusRole();
		String erroMessage="保存失败:";
		String successMessage="保存成功";
		String sameName="";
		try{
			int auditSate=officeCustomerInfo.getAuditState();
			if(FlowApply.STATUS_IN_AUDIT==auditSate){
				successMessage="提交成功";
				erroMessage="提交失败:";
			}
			if(officeCustomerInfoService.getSameCustomerName(officeCustomerInfo)!=0){
				sameName=" 有相同客户名称！";
				throw new Exception();
			}
			if(StringUtils.isNotBlank(officeCustomerInfo.getId())){
				//新增的编辑
				officeCustomerInfoService.update(officeCustomerInfo);
				if(FlowApply.STATUS_IN_AUDIT==auditSate){
					officeCustomerApply.setApplyId(applyId);
					if(deptLeader){
						officeCustomerDeptLeaderApplyService.update(officeCustomerApply);
					}else{
						officeCustomerApplyService.update(officeCustomerApply);
					}
				}
			}else{
				User user = getLoginInfo().getUser();
				officeCustomerApply.setApplyUserId(user.getId()); 
				officeCustomerApply.setApplyUserName(user.getRealname());
				//新增申请 只有一种新增客户申请类型。跟进人都是自己  
				officeCustomerApply.setApplyType(OfficeCustomerConstants.APPLY_NEW);
				officeCustomerApply.setFollowerId(getLoginUser().getUserId());
				officeCustomerApply.setOfficeCustomerInfo(officeCustomerInfo);
				officeCustomerApply.setIsdeleted(false);
				//如果是事业部负责人新增调用对应的service
				if(deptLeader){
					officeCustomerDeptLeaderApplyService.save(officeCustomerApply);
				}else{
					officeCustomerApplyService.save(officeCustomerApply);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage(erroMessage+sameName);
			return SUCCESS;
		}
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.setPromptMessage(successMessage);
		return SUCCESS;
	}
	//查看信息
	public String goDetail(){
		getCusRole();
		//客户申请查看
		if(StringUtils.isNotEmpty(officeCustomerApply.getId())){
			officeCustomerApply=officeCustomerApplyService.getOfficeCustomerApplyById(officeCustomerApply.getId());
			officeCustomerInfo=officeCustomerInfoService.getOfficeCustomerInfoById(officeCustomerApply.getCustomerId());
		}
		//资源库申请跳转
		if(StringUtils.isNotBlank(officeCustomerInfo.getId())){
			Mcode mcode=mcodeService.getMcode(mcodeAttend);
			mcodedetailList=mcode.getMcodeDetailList();
			officeCustomerInfo=officeCustomerInfoService.getOfficeCustomerInfoById(officeCustomerInfo.getId());
			if(StringUtils.isNotBlank(officeCustomerInfo.getProgressState())){
				officeCustomerInfo.setProgressStateInt(Integer.parseInt(officeCustomerInfo.getProgressState()));
			}
		}
		return SUCCESS;
	}
	//带跟进记录的查看信息
	public String goDetailRecord(){
		getCusRole();
		if(StringUtils.isNotEmpty(officeCustomerApply.getId())){
			officeCustomerApply=officeCustomerApplyService.getOfficeCustomerApplyById(officeCustomerApply.getId());
			officeCustomerInfo=officeCustomerInfoService.getOfficeCustomerInfoById(officeCustomerApply.getCustomerId());
			recordList=officeCustomerFollowRecordService.getOfficeCustomerFollowRecordList(officeCustomerApply.getId());
		}
		return SUCCESS;
	}
	
	//我的客户显示列表
	public String myCustomerList(){
		getCusRole();
		customerApplyList=officeCustomerApplyService.getMyCustomerByUnitIdPage(getUnitId(), getLoginUser().getUserId(), searchCustomer, getPage());
		return SUCCESS;
	}
	//添加跟进记录跳转
	public String addRecord(){
		getCusRole();
		//userList=userService.getUsersByDeptId(userService.getDeptIdByUserId(getLoginUser().getUserId()));
		if(StringUtils.isNotBlank(officeCustomerApply.getId())){
			recordList=officeCustomerFollowRecordService.getOfficeCustomerFollowRecordList(officeCustomerApply.getId());
		}
		return SUCCESS;
	}
	//跟进记录入库
	public String addRecordToSQL(){
		String erroMessage="提交失败";
		String successMessage="提交成功";
		try{
			officeRecord.setApplyId(officeCustomerApply.getId());
			officeRecord.setCreateTime(new Date());
			officeCustomerFollowRecordService.save(officeRecord);

			officeCustomerApply=officeCustomerApplyService.getOfficeCustomerApplyById(officeCustomerApply.getId());
			officeCustomerInfo=officeCustomerInfoService.getOfficeCustomerInfoById(officeCustomerApply.getCustomerId());
			officeCustomerInfo.setProgressState(officeRecord.getProgressState());
			officeCustomerInfoService.update(officeCustomerInfo);
			if((officeCustomerInfo.getProgressState().equals("04") ||officeCustomerInfo.getProgressState().equals("05") ||officeCustomerInfo.getProgressState().equals("09")) 
							&&StringUtils.isNotBlank(officeCustomerApply.getId())){
					officeCustomerApply.setOfficeCustomerInfo(officeCustomerInfo);
					officeCustomerApply.setFollowerId(null);//不需要修改跟进人
					officeCustomerApplyService.updateFollowerId(officeCustomerApply);
			}
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage(erroMessage);
			return SUCCESS;
		}
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.setPromptMessage(successMessage);
		return SUCCESS;
	}
	//我的客户编辑跳转
	public String editMyCustomer(){
		Mcode mcode=mcodeService.getMcode(mcodeAttend);
		mcodedetailList=mcode.getMcodeDetailList();
		if(StringUtils.isNotBlank(officeCustomerApply.getId())){
			officeCustomerApply=officeCustomerApplyService.getOfficeCustomerApplyById(officeCustomerApply.getId());
			officeCustomerInfo=officeCustomerInfoService.getOfficeCustomerInfoById(officeCustomerApply.getCustomerId());
			if(StringUtils.isNotBlank(officeCustomerInfo.getProgressState())){
				officeCustomerInfo.setProgressStateInt(Integer.parseInt(officeCustomerInfo.getProgressState()));
			}
		}
		return SUCCESS;
	}
	//我的客户编辑转发入库
	public String editMyCustomerToSQL(){
		String erroMessage="提交失败";
		String successMessage="提交成功";
		try{
			if(StringUtils.isNotBlank(officeCustomerInfo.getId())){
				officeCustomerInfoService.update(officeCustomerInfo);
				if((StringUtils.isNotBlank(officeCustomerApply.getFollowerId()) ||officeCustomerInfo.getProgressState().equals("04")
											||officeCustomerInfo.getProgressState().equals("05") ||officeCustomerInfo.getProgressState().equals("09")) 
													&&StringUtils.isNotBlank(officeCustomerApply.getId())){
					officeCustomerApply.setOfficeCustomerInfo(officeCustomerInfo);
					officeCustomerApplyService.updateFollowerId(officeCustomerApply);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage(erroMessage);
			return SUCCESS;
		}
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.setPromptMessage(successMessage);
		return SUCCESS;
	}
	//我的客户延期申请入库
	public String putOffApply(){
		getCusRole();
		String erroMessage="提交失败";
		String successMessage="提交成功";
		try{
			if(StringUtils.isNotBlank(officeCustomerInfo.getId())){
				User user = getLoginInfo().getUser();
				officeCustomerApply.setApplyUserId(user.getId()); 
				officeCustomerApply.setApplyUserName(user.getRealname());
				
				officeCustomerInfo.setState(OfficeCustomerConstants.INFO_APPLY);
				officeCustomerInfoService.update(officeCustomerInfo);
				if(deptLeader){
					officeCustomerDeptLeaderApplyService.putOffApply(officeCustomerApply);
				}else{
					officeCustomerApplyService.putOffApply(officeCustomerApply);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage(erroMessage);
			return SUCCESS;
		}
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.setPromptMessage(successMessage);
		return SUCCESS;
	}
	//地区 本部 所有客户显示
	public String  getAllList(){
		getCusRole();
		List<Dept> deptListAll=deptService.getDepts(getUnitId());
		if(CollectionUtils.isNotEmpty(deptListAll)){
			deptList=new ArrayList<Dept>();
			for(Dept dept:deptListAll){
				if(dept.getMark()==9){
					deptList.add(dept);
				}
			}
		}
		customerApplyList=officeCustomerApplyService.getAllList(searchCustomer,getUnitId(),getLoginUser().getUserId(),clientManager,deptLeader,regionLeader,getPage());
		return SUCCESS;
	}
	//导出
	public void doExport(){
		getCusRole();
		Mcode typeMcode=mcodeService.getMcode("DM-OAKHLB");//客户类别
		Mcode productMcode=mcodeService.getMcode("DM-OAYXHZCP");//意向合作产品
		Mcode stateMcode=mcodeService.getMcode("DM-JZZT");//进展状态
		customerApplyList=officeCustomerApplyService.getAllList(searchCustomer,getUnitId(),getLoginUser().getUserId(),clientManager,deptLeader,regionLeader,null);
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER|ZdStyle.ALIGN_CENTER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT);
		List<ZdCell> zdlist=new ArrayList<ZdCell>();
		
		zdlist.add(new ZdCell("客户官方名称",2,style2));
		zdlist.add(new ZdCell("所在区域",2,style2));
		zdlist.add(new ZdCell("客户类别",2,style2));
		zdlist.add(new ZdCell("客户来源",2,style2));
		zdlist.add(new ZdCell("客户背景",2,style2));
		zdlist.add(new ZdCell("意向合作产品",2,style2));
		zdlist.add(new ZdCell("联系人",2,style2));
		zdlist.add(new ZdCell("联系电话",2,style2));
		zdlist.add(new ZdCell("申请日期",2,style2));
		zdlist.add(new ZdCell("跟进部门",2,style2));
		zdlist.add(new ZdCell("跟进人",2,style2));
		zdlist.add(new ZdCell("进展状态",2,style2));
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		
		for(OfficeCustomerApply apply:customerApplyList){
			String typeStr="";
			String productStr="";
			String stateStr="";
			if(StringUtils.isNotBlank(apply.getOfficeCustomerInfo().getType())){
				typeStr=typeMcode.get(apply.getOfficeCustomerInfo().getType());
			}
			if(StringUtils.isNotBlank(apply.getOfficeCustomerInfo().getProduct())){
				String[] products=apply.getOfficeCustomerInfo().getProduct().split(",");
				for(String product:products){
					productStr+=productMcode.get(product.trim())+" ";
				}
//				productStr=productMcode.get(apply.getOfficeCustomerInfo().getProduct());
			}
			if(StringUtils.isNotBlank(apply.getOfficeCustomerInfo().getProgressState())){
				stateStr=stateMcode.get(apply.getOfficeCustomerInfo().getProgressState());
			}
			ZdCell[] zdCell=new ZdCell[12];
			zdCell[0]=new ZdCell(apply.getOfficeCustomerInfo().getName(),2, style3);
			zdCell[1]=new ZdCell(apply.getOfficeCustomerInfo().getRegionName(),2, style3);
			zdCell[2]=new ZdCell(typeStr,2, style3);
			zdCell[3]=new ZdCell(apply.getOfficeCustomerInfo().getInfoSource(),2, style3);
			zdCell[4]=new ZdCell(apply.getOfficeCustomerInfo().getBackgroundInfo(),2, style3);
			zdCell[5]=new ZdCell(productStr,2, style3);
			zdCell[6]=new ZdCell(apply.getOfficeCustomerInfo().getContact(),2, style3);
			zdCell[7]=new ZdCell(apply.getOfficeCustomerInfo().getPhone(),2, style3);
			zdCell[8]=new ZdCell(DateUtils.date2String(apply.getApplyDate(),"yyyy-MM-dd"),2, style3);
			zdCell[9]=new ZdCell(apply.getDeptName(),2, style3);
			zdCell[10]=new ZdCell(apply.getFollowerName(),2, style3);
			zdCell[11]=new ZdCell(stateStr,2, style3);
			zdExcel.add(zdCell);
		}
		Sheet sheet = zdExcel.createSheet("sheet1");
		sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
		for(int i=0;i<12;i++){
			zdExcel.setCellWidth(sheet, i, 1);
		}
		zdExcel.export("客户信息");
	}
	//客户资源库显示列表
	public String customerLibraryList(){
		getCusRole();
		customerInfoList=officeCustomerInfoService.getCustomerLibraryByUnitIdPage(searchCustomer,getUnitId(), getPage());
		return SUCCESS;
	}
	//资源库申请
	public String customerLibraryApply(){
		getCusRole();
		String erroMessage="申请失败";
		String successMessage="申请成功";
		try{
			if(StringUtils.isNotBlank(officeCustomerInfo.getId())){
				int auditState=officeCustomerInfo.getAuditState();
				String progressState=officeCustomerInfo.getProgressState();
				officeCustomerInfo=officeCustomerInfoService.getOfficeCustomerInfoById(officeCustomerInfo.getId());
				officeCustomerInfo.setProgressState(progressState);
				officeCustomerInfo.setAuditState(auditState);
				String applyType=null;
				//判断是否有跟进人。
				if(StringUtils.isEmpty(officeCustomerApply.getFollowerId())){
					applyType=OfficeCustomerConstants.APPLY_OLD;
					officeCustomerApply.setFollowerId(getLoginUser().getUserId());
				}else{
					applyType=OfficeCustomerConstants.APPLY_TRANS;
				}
				User user = getLoginInfo().getUser();
				officeCustomerApply.setApplyType(applyType);
				officeCustomerApply.setApplyUserId(user.getId()); 
				officeCustomerApply.setApplyUserName(user.getRealname());
				officeCustomerApply.setOfficeCustomerInfo(officeCustomerInfo);
				officeCustomerApply.setUnitId(getUnitId());
				if(deptLeader){
					officeCustomerDeptLeaderApplyService.save(officeCustomerApply);
				}else{
					officeCustomerApplyService.save(officeCustomerApply);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage(erroMessage);
			return SUCCESS;
		}
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.setPromptMessage(successMessage);
		return SUCCESS;
	}
	//运营人员添加转发客户
	public String addCustomer(){
		String erroMessage="添加失败";
		String successMessage="添加成功";
		String sameName="";
		try{
			if(StringUtils.isNotBlank(officeCustomerInfo.getId())){//转发
				OfficeCustomerInfo customerInfo=officeCustomerInfoService.getOfficeCustomerInfoById(officeCustomerInfo.getId());//获取客户全部信息
				if(!officeCustomerInfo.getProgressState().equals("05") &&!officeCustomerInfo.getProgressState().equals("09")){
					customerInfo.setState(OfficeCustomerConstants.INFO_FLOWING);//将客户状态设成跟进中
				}else{
					officeCustomerApply.setIsdeleted(true);
				}
				customerInfo.setProgressState(officeCustomerInfo.getProgressState());//修改对应的进展情况
				officeCustomerInfoService.update(customerInfo);//修改客户状态
				
				officeCustomerApply.setOfficeCustomerInfo(customerInfo);
				officeCustomerApply.setCustomerId(customerInfo.getId());
				officeCustomerApply.setUnitId(getUnitId());
				officeCustomerApplyService.addCustomer(officeCustomerApply);//添加跟进人 申请信息
			}else{//新增
				if(officeCustomerInfoService.getSameCustomerName(officeCustomerInfo)!=0){
					sameName=" 有相同客户名称！";
					throw new Exception();
				}
				officeCustomerInfo.setAddTime(new Date());
				if(StringUtils.isNotBlank(officeCustomerApply.getFollowerId())){
					//选择跟进人
					if(!officeCustomerInfo.getProgressState().equals("05") &&!officeCustomerInfo.getProgressState().equals("09")){
						//将客户状态设成跟进中
						officeCustomerInfo.setState(OfficeCustomerConstants.INFO_FLOWING);
					}else{
						officeCustomerInfo.setState(OfficeCustomerConstants.INFO_LIBARAY);
						officeCustomerApply.setIsdeleted(true);
					}
					officeCustomerInfo=officeCustomerInfoService.save(officeCustomerInfo);
					
					officeCustomerApply.setOfficeCustomerInfo(officeCustomerInfo);
					officeCustomerApply.setCustomerId(officeCustomerInfo.getId());
					officeCustomerApplyService.addCustomer(officeCustomerApply);
				}else{
					officeCustomerInfo.setState(OfficeCustomerConstants.INFO_LIBARAY);
					officeCustomerInfoService.save(officeCustomerInfo);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage(erroMessage+sameName);
			return SUCCESS;
		}
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.setPromptMessage(successMessage);
		return SUCCESS;
	}
	/*public String  findRegionList(){
		List<Region> regionList = regionService.getSubRegions(regionId);
		Map<String,String> regionMap = new HashMap <String,String>();
		if(CollectionUtils.isNotEmpty(regionList)){
			for(Region  region:regionList){
				regionMap.put(region.getRegionName(),region.getRegionCode());
			}
			jsonMessageDto.put("regionMap", regionMap);
			jsonMessageDto.setSuccess(true);
		}else {
			jsonMessageDto.setSuccess(false);
		}
		return SUCCESS;
	}*/
	
	public List<Region> getProvinceList() {
		return regionService.getSubRegions("");
	}
	public List<OfficeCustomerApply> getCustomerApplyList() {
		return customerApplyList;
	}
	public void setCustomerApplyList(List<OfficeCustomerApply> customerApplyList) {
		this.customerApplyList = customerApplyList;
	}
	public OfficeCustomerApply getOfficeCustomerApply() {
		return officeCustomerApply;
	}
	public void setOfficeCustomerApply(OfficeCustomerApply officeCustomerApply) {
		this.officeCustomerApply = officeCustomerApply;
	}
	public void setOfficeCustomerApplyService(
			OfficeCustomerApplyService officeCustomerApplyService) {
		this.officeCustomerApplyService = officeCustomerApplyService;
	}
	public void setOfficeCustomerInfo(OfficeCustomerInfo officeCustomerInfo) {
		this.officeCustomerInfo = officeCustomerInfo;
	}
	public void setOfficeCustomerInfoService(
			OfficeCustomerInfoService officeCustomerInfoService) {
		this.officeCustomerInfoService = officeCustomerInfoService;
	}
	public OfficeCustomerInfo getOfficeCustomerInfo() {
		return officeCustomerInfo;
	}
	public void setMcodeService(McodeService mcodeService) {
		this.mcodeService = mcodeService;
	}
	public List<Mcodedetail> getMcodedetailList() {
		return mcodedetailList;
	}
	public void setMcodedetailList(List<Mcodedetail> mcodedetailList) {
		this.mcodedetailList = mcodedetailList;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public SearchCustomer getSearchCustomer() {
		return searchCustomer;
	}
	public void setSearchCustomer(SearchCustomer searchCustomer) {
		this.searchCustomer = searchCustomer;
	}
	public List<OfficeCustomerInfo> getCustomerInfoList() {
		return customerInfoList;
	}
	public void setCustomerInfoList(List<OfficeCustomerInfo> customerInfoList) {
		this.customerInfoList = customerInfoList;
	}
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	public boolean isRegionLeader() {
		return regionLeader;
	}
	public void setRegionLeader(boolean regionLeader) {
		this.regionLeader = regionLeader;
	}
	public boolean isDeptLeader() {
		return deptLeader;
	}
	public void setDeptLeader(boolean deptLeader) {
		this.deptLeader = deptLeader;
	}
	public boolean isClientManager() {
		return clientManager;
	}
	public void setClientManager(boolean clientManager) {
		this.clientManager = clientManager;
	}
	public void setOfficeCustomerDeptLeaderApplyService(
			OfficeCustomerDeptLeaderApplyService officeCustomerDeptLeaderApplyService) {
		this.officeCustomerDeptLeaderApplyService = officeCustomerDeptLeaderApplyService;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public List<OfficeCustomerFollowRecord> getRecordList() {
		return recordList;
	}
	public void setRecordList(List<OfficeCustomerFollowRecord> recordList) {
		this.recordList = recordList;
	}
	public void setOfficeCustomerFollowRecordService(
			OfficeCustomerFollowRecordService officeCustomerFollowRecordService) {
		this.officeCustomerFollowRecordService = officeCustomerFollowRecordService;
	}
	public OfficeCustomerFollowRecord getOfficeRecord() {
		return officeRecord;
	}
	public void setOfficeRecord(OfficeCustomerFollowRecord officeRecord) {
		this.officeRecord = officeRecord;
	}
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public String getPlaceType() {
		return placeType;
	}
	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}
	public List<Dept> getDeptList() {
		return deptList;
	}
	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}
	public String getLibraryApply() {
		return libraryApply;
	}
	public void setLibraryApply(String libraryApply) {
		this.libraryApply = libraryApply;
	}
	
}
