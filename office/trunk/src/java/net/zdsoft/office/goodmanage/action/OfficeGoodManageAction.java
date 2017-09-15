package net.zdsoft.office.goodmanage.action;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;

import com.opensymphony.xwork2.ModelDriven;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.goodmanage.constant.OfficeGoodsConstants;
import net.zdsoft.office.goodmanage.entity.OfficeGoods;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsChangeLog;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsDistribute;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsReq;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsType;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsTypeAuth;
import net.zdsoft.office.goodmanage.service.OfficeGoodsChangeLogService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsDistributeService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsReqService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsTypeAuthService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsTypeService;

public class OfficeGoodManageAction extends PageAction implements ModelDriven<OfficeGoods> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -472805006205792454L;
	
	private OfficeGoods officeGoods = new OfficeGoods();
	private OfficeGoodsChangeLog goodsChangeLog = new OfficeGoodsChangeLog();
	private OfficeGoodsReq officeGoodsReq = new OfficeGoodsReq();
	private OfficeGoodsType officeGoodsType = new OfficeGoodsType();
	private OfficeGoodsTypeAuth officeGoodsTypeAuth = new OfficeGoodsTypeAuth();
	private OfficeGoodsDistribute officeGoodsDistribute = new OfficeGoodsDistribute();
	
	private String searchType;
	private String searchName;
	private String applyType;
	private Date startTime;
	private Date endTime;
	private String searchUserName;
	
	private String[] checkid;
	
	private boolean goodsManageAuth;//物品管理权限
	private boolean goodsAuditAuth;//物品审核权限
	private boolean hasAuditModel;//是否开启审核模式
	
	private List<OfficeGoods> goodsList = new ArrayList<OfficeGoods>();
	private List<OfficeGoodsChangeLog> goodsChangeLogList = new ArrayList<OfficeGoodsChangeLog>();
	private List<OfficeGoodsReq> goodsRequestList = new ArrayList<OfficeGoodsReq>();
	private List<OfficeGoodsType> goodsTypeList = new ArrayList<OfficeGoodsType>();
	private List<OfficeGoodsTypeAuth> goodsTypeAuthList = new ArrayList<OfficeGoodsTypeAuth>();
	private List<OfficeGoodsDistribute> goodsDistributeList = new ArrayList<OfficeGoodsDistribute>();
	
	private OfficeGoodsService officeGoodsService;
	private OfficeGoodsChangeLogService officeGoodsChangeLogService;
	private OfficeGoodsReqService officeGoodsReqService;
	private OfficeGoodsTypeService officeGoodsTypeService;
	private OfficeGoodsTypeAuthService officeGoodsTypeAuthService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private OfficeGoodsDistributeService officeGoodsDistributeService;
	private UserService userService;
	private DeptService deptService;

	@Override
	public OfficeGoods getModel() {
		return officeGoods;
	}

	/**
	 * 判断当前用户是否指定角色
	 * @param roleCode
	 */
	public boolean isCustomRole(String roleCode){
		CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), roleCode);
		boolean flag;
		if(role == null){
			flag = false;
			return flag;
		}
		List<CustomRoleUser> roleUs = customRoleUserService.getCustomRoleUserListByUserId(getLoginUser().getUserId());
		if(CollectionUtils.isNotEmpty(roleUs)){
			for(CustomRoleUser ru : roleUs){
				if(StringUtils.equals(ru.getRoleId(), role.getId())){
					flag = true;
					return flag;
				}
			}
		}
		flag = false;
		return flag;
	}
	
	public String execute() {//TODO
		if(isHasAuditModel())
			return SUCCESS;
		else
			return "success2";
	}
	
	/**
	 * 物品管理
	 * @return
	 */
	public String goodsManage() {
		goodsTypeList = officeGoodsTypeService.getOfficeGoodsTypeByUnitIdList(getUnitId());
		return SUCCESS;
	}
	
	public String goodsManageList() {
		goodsList = officeGoodsService.getOfficeGoodsByConditions(getUnitId(), StringUtils.isBlank(searchType)?null:new String[]{searchType}, searchName,false, getPage());
		return SUCCESS;
	}
	
	public String goodsManageAdd() {
		goodsTypeList = officeGoodsTypeService.getOfficeGoodsTypeByUnitIdList(getUnitId());
		officeGoods = new OfficeGoods();
		officeGoods.setPrice(0f);
		return SUCCESS;
	}
	
	public String goodsManageEdit() {
		goodsTypeList = officeGoodsTypeService.getOfficeGoodsTypeByUnitIdList(getUnitId());
		officeGoods = officeGoodsService.getOfficeGoodsById(officeGoods.getId());
		if(officeGoods.getIsReturned())
			officeGoods.setIsReturnedStr("1");
		else
			officeGoods.setIsReturnedStr("0");
		return SUCCESS;
	}
	
	public String goodsManageSave() {
		try{
			if("0".equals(officeGoods.getIsReturnedStr())){
				officeGoods.setIsReturned(false);
			}else{
				officeGoods.setIsReturned(true);
			}
			if(StringUtils.isBlank(officeGoods.getId())){
				officeGoods.setUnitId(getUnitId());
				officeGoods.setAddUserId(getLoginUser().getUserId());
				officeGoods.setCreationTime(new Date());
				officeGoods.setReqTag(OfficeGoodsConstants.GOODS_HAS_NOT_REQ);
				
				officeGoodsService.save(officeGoods);
				
				//变更记录
				goodsChangeLog = new OfficeGoodsChangeLog();
				goodsChangeLog.setAddUserId(getLoginUser().getUserId());
				goodsChangeLog.setGoodsId(officeGoods.getId());
				goodsChangeLog.setReason(OfficeGoodsConstants.GOODS_REGISTER);
				goodsChangeLog.setAmount(officeGoods.getAmount());
				goodsChangeLog.setRemark(OfficeGoodsConstants.GOODS_REGISTER + ",登记人：" + getLoginInfo().getUser().getRealname());
				goodsChangeLog.setCreationTime(new Date());
				
				officeGoodsChangeLogService.save(goodsChangeLog);
			}
			else{
				OfficeGoods goods = officeGoodsService.getOfficeGoodsById(officeGoods.getId());
				officeGoods.setUnitId(goods.getUnitId());
				officeGoods.setAddUserId(goods.getAddUserId());
				officeGoods.setCreationTime(new Date());
				officeGoods.setReqTag(goods.getReqTag());
				
				officeGoodsService.update(officeGoods);
				
				//变更记录
				if(officeGoods.getAmount() - goods.getAmount() != 0){
					goodsChangeLog = new OfficeGoodsChangeLog();
					goodsChangeLog.setAddUserId(getLoginUser().getUserId());
					goodsChangeLog.setGoodsId(officeGoods.getId());
					goodsChangeLog.setReason(OfficeGoodsConstants.GOODS_MANAGE);
					goodsChangeLog.setAmount(officeGoods.getAmount() - goods.getAmount());
					goodsChangeLog.setRemark(OfficeGoodsConstants.GOODS_MANAGE + ",管理人：" + getLoginInfo().getUser().getRealname());
					goodsChangeLog.setCreationTime(new Date());
					
					officeGoodsChangeLogService.save(goodsChangeLog);
				}
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("物品登记成功！");
		} catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("物品登记失败！");
		}
		return SUCCESS;
	}
	
	public String goodsDelete() {
		try{
			officeGoodsService.delete(checkid);
			officeGoodsChangeLogService.deleteByGoodsIds(checkid);
			officeGoodsReqService.deleteByGoodsIds(checkid);
			
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功！");
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败！");
		}
		return SUCCESS;
	}
	
	public String goodsChangeLog(){
		officeGoods = officeGoodsService.getOfficeGoodsById(officeGoods.getId());
		goodsChangeLogList = officeGoodsChangeLogService.getOfficeGoodsChangeLogByGoodsId(officeGoods.getId(), getPage());
		return SUCCESS;
	}
	
	/**
	 * 我的物品
	 * @return
	 */
	public String myGoods() {
		goodsTypeList = officeGoodsTypeService.getOfficeGoodsTypeByUnitIdList(getUnitId());
		return SUCCESS;
	}
	
	public String myGoodsList() {//TODO
		goodsRequestList = officeGoodsReqService.getOfficeGoodsReqByConditions(getUnitId(), StringUtils.isBlank(searchType)?null:new String[]{searchType}, searchName, null, getLoginInfo().getUser().getId(), null, null, getPage());
		return SUCCESS;
	}
	
	public String myGoodsReturn() {
		officeGoodsReq = officeGoodsReqService.getOfficeGoodsReqById(officeGoodsReq.getId());
		officeGoods = officeGoodsService.getOfficeGoodsById(officeGoodsReq.getGoodsId());
		
		OfficeGoodsType goodsType = officeGoodsTypeService.getOfficeGoodsTypeByTypeId(getUnitId(), officeGoods.getType());
		officeGoods.setTypeName(goodsType==null?"不存在":goodsType.getTypeName());
		
		return SUCCESS;
	}
	
	public String myGoodsSave() {
		try{
			Date returnTime = officeGoodsReq.getPassTime();
			officeGoodsReq = officeGoodsReqService.getOfficeGoodsReqById(officeGoodsReq.getId());
			officeGoodsReq.setPassTime(returnTime);
			officeGoodsReq.setState(OfficeGoodsConstants.GOODS_HAS_RETURNED);

			officeGoods = officeGoodsService.getOfficeGoodsById(officeGoods.getId());
			officeGoods.setAmount(officeGoods.getAmount() + officeGoodsReq.getAmount());
			
			//变更记录
			goodsChangeLog = new OfficeGoodsChangeLog();
			goodsChangeLog.setAddUserId(getLoginUser().getUserId());
			goodsChangeLog.setGoodsId(officeGoods.getId());
			goodsChangeLog.setReason(OfficeGoodsConstants.GOODS_RETURN);
			goodsChangeLog.setAmount(officeGoodsReq.getAmount());
			goodsChangeLog.setRemark(OfficeGoodsConstants.GOODS_RETURN + ",归还人：" + getLoginInfo().getUser().getRealname());
			goodsChangeLog.setCreationTime(officeGoodsReq.getPassTime());
			
			officeGoodsService.update(officeGoods);
			officeGoodsReqService.update(officeGoodsReq);
			officeGoodsChangeLogService.save(goodsChangeLog);
			
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("归还成功！");
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("归还失败！");
		}
		return SUCCESS;
	}
	
	public String myGoodsApply() {
		try{
			officeGoodsReq = officeGoodsReqService.getOfficeGoodsReqById(officeGoodsReq.getId());
			officeGoodsReq.setState(OfficeGoodsConstants.GOODS_NOT_AUDIT);
			
			officeGoods = officeGoodsService.getOfficeGoodsById(officeGoodsReq.getGoodsId());
			if(officeGoods.getAmount() < officeGoodsReq.getAmount()){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("物品库存数量为："+officeGoods.getAmount()+"，无法重新申请！");
				return SUCCESS;
			}
			officeGoods.setReqTag(OfficeGoodsConstants.GOODS_HAS_REQ);
			
			
			officeGoodsReqService.update(officeGoodsReq);
			officeGoodsService.update(officeGoods);
			
			Unit crUnit=new Unit();
			crUnit.setId(getLoginInfo().getUnitID());
			crUnit.setName(getLoginInfo().getUnitName());
			officeGoodsService.sendMsg(getLoginInfo().getUser(), crUnit, officeGoods, null, null, officeGoodsReq.getId());
			
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("申请成功！");
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("申请失败！");
		}
		return SUCCESS;
	}
	
	/**
	 * 领用申请
	 * @return
	 */
	public String goodsApply() {
		goodsTypeList = officeGoodsTypeService.getOfficeGoodsTypeByUnitIdList(getUnitId());
		return SUCCESS;
	}
	
	public String goodsApplyList(){
		officeGoods.setCreationTime(new Date());
		goodsList = officeGoodsService.getOfficeGoodsByConditions(getUnitId(), StringUtils.isBlank(searchType)?null:new String[]{searchType}, searchName, true, getPage());
		return SUCCESS;
	}
	
	public String goodsApplyAdd(){
		goodsTypeList = officeGoodsTypeService.getOfficeGoodsTypeByUnitIdList(getUnitId());
		officeGoods = officeGoodsService.getOfficeGoodsById(officeGoods.getId());
		
		OfficeGoodsType goodsType = officeGoodsTypeService.getOfficeGoodsTypeByTypeId(getUnitId(), officeGoods.getType());
		officeGoods.setTypeName(goodsType==null?"不存在":goodsType.getTypeName());
		
		officeGoodsReq = new OfficeGoodsReq();
		return SUCCESS;
	}
	
	public String goodsApplySave(){//TODO
		try{
			officeGoods = officeGoodsService.getOfficeGoodsById(officeGoods.getId());
			officeGoods.setReqTag(OfficeGoodsConstants.GOODS_HAS_REQ);
			
			if(StringUtils.isBlank(officeGoodsReq.getId())){
				officeGoodsReq.setGoodsId(officeGoods.getId());
				officeGoodsReq.setUnitId(getLoginInfo().getUnitID());
				officeGoodsReq.setCreationTime(new Date());
				officeGoodsReq.setGetUserid(getLoginInfo().getUser().getId());
			}else{
				officeGoodsReq = officeGoodsReqService.getOfficeGoodsReqById(officeGoodsReq.getId());
			}
			officeGoodsReq.setReqDeptId(getLoginInfo().getUser().getDeptid());
			officeGoodsReq.setReqUserId(getLoginInfo().getUser().getId());
			officeGoodsReq.setState(OfficeGoodsConstants.GOODS_NOT_AUDIT);
			
			officeGoodsService.update(officeGoods);
			if(StringUtils.isBlank(officeGoodsReq.getId())){
				officeGoodsReqService.submitSave(officeGoodsReq);
			}else{
				officeGoodsReqService.update(officeGoodsReq);
			}
			
			Unit crUnit=new Unit();
			crUnit.setId(getLoginInfo().getUnitID());
			crUnit.setName(getLoginInfo().getUnitName());
			officeGoodsService.sendMsg(getLoginInfo().getUser(), crUnit, officeGoods, null, null, officeGoodsReq.getId());
			
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("申请成功！");
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("申请失败！");
		}
		return SUCCESS;
	}
	
	/**
	 * 领用审核
	 * @return
	 */
	public String goodsAudit() {
		List<String> typelist = new ArrayList<String>();
		List<OfficeGoodsTypeAuth> authlist = officeGoodsTypeAuthService.getOfficeGoodsTypeAuthByUserId(getUnitId(), getLoginInfo().getUser().getId());
		if(authlist.size() > 0){
			typelist = Arrays.asList(authlist.get(0).getTypeId().split(","));
		}
		goodsTypeList = officeGoodsTypeService.getOfficeGoodsTypeByTypeId(getUnitId(), typelist.toArray(new String[0]));
		return SUCCESS;
	}
	
	public String goodsAuditList() {
		if(StringUtils.isBlank(applyType)){
			applyType = OfficeGoodsConstants.GOODS_AUDIT_ALL + "";
		}
		goodsRequestList = this.getGoodsReqList();
		return SUCCESS;
	}
	
	public String goodsAuditEdit(){
		officeGoodsReq = officeGoodsReqService.getOfficeGoodsReqById(officeGoodsReq.getId());
		return SUCCESS;
	}
	
	public String goodsAuditSave(){
		try{
			OfficeGoodsReq goodsReq = officeGoodsReqService.getOfficeGoodsReqById(officeGoodsReq.getId());
			goodsReq.setState(officeGoodsReq.getState());
			if(StringUtils.isNotBlank(officeGoodsReq.getAdvice())){
				goodsReq.setAdvice(officeGoodsReq.getAdvice());
			}
			goodsReq.setPassTime(new Date());
			
			officeGoods = officeGoodsService.getOfficeGoodsById(goodsReq.getGoodsId());
			officeGoods.setReqTag(OfficeGoodsConstants.GOODS_HAS_NOT_REQ);
			
			if(OfficeGoodsConstants.GOODS_AUDIT_PASS == goodsReq.getState()){//审核通过
				if(officeGoods.getAmount() < goodsReq.getAmount()){
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("物品库存数量不足，无法通过申请！");
					return SUCCESS;
				}
				
				officeGoods.setAmount(officeGoods.getAmount() - goodsReq.getAmount());
				
				//变更记录
				goodsChangeLog = new OfficeGoodsChangeLog();
				goodsChangeLog.setAddUserId(getLoginUser().getUserId());
				goodsChangeLog.setGoodsId(officeGoods.getId());
				goodsChangeLog.setReason(OfficeGoodsConstants.GOODS_GET);
				goodsChangeLog.setAmount(-goodsReq.getAmount());
				goodsChangeLog.setRemark(OfficeGoodsConstants.GOODS_GET + ",领用人：" + goodsReq.getReqUserName());
				goodsChangeLog.setCreationTime(new Date());
				officeGoodsChangeLogService.save(goodsChangeLog);
			}
			officeGoodsService.update(officeGoods);
//			officeGoodsReqService.update(goodsReq);
			officeGoodsReqService.auditSave(goodsReq);
			
			Unit crUnit=new Unit();
			crUnit.setId(getLoginInfo().getUnitID());
			crUnit.setName(getLoginInfo().getUnitName());
			officeGoodsService.sendMsg(getLoginInfo().getUser(), crUnit, officeGoods, goodsReq.getState(), new String[]{goodsReq.getGetUserid()}, goodsReq.getId());
			
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("审核成功！");
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("审核失败！");
		}
		return SUCCESS;
	}
	
	public String doExport() {
		applyType = OfficeGoodsConstants.GOODS_AUDIT_PASS + "";
		goodsRequestList = this.getGoodsReqList();
		
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER|ZdStyle.ALIGN_CENTER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT);
		List<ZdCell>zdlist=new ArrayList<ZdCell>();
		
		zdlist.add(new ZdCell("物品名称",1,style2));
		zdlist.add(new ZdCell("规格型号",1,style2));
		zdlist.add(new ZdCell("单位",1,style2));
		zdlist.add(new ZdCell("单价",1,style2));
		zdlist.add(new ZdCell("物品类别",1,style2));
		zdlist.add(new ZdCell("是否需归还",1,style2));
		zdlist.add(new ZdCell("申请数量",1,style2));
		zdlist.add(new ZdCell("申请人",1,style2));
		zdlist.add(new ZdCell("申请部门",1,style2));
		zdlist.add(new ZdCell("申请说明",1,style2));
		zdlist.add(new ZdCell("申请日期",1,style2));
		
		zdExcel.add(new ZdCell("物品审核列表", zdlist.size(), 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		
		for(OfficeGoodsReq item : goodsRequestList){
			int index = 0;
			ZdCell[] cells = new ZdCell[zdlist.size()];
			cells[index++] = new ZdCell(item.getReqGoods().getName(), 1, style3);
			cells[index++] = new ZdCell(item.getReqGoods().getModel(), 1, style3);
			cells[index++] = new ZdCell(item.getReqGoods().getGoodsUnit(), 1, style3);
			
			BigDecimal price = new BigDecimal(item.getReqGoods().getPrice());
			DecimalFormat df = new DecimalFormat("0.00");
			cells[index++] = new ZdCell(df.format(price), 1, style3);
			
			cells[index++] = new ZdCell(item.getReqGoods().getTypeName(), 1, style3);
			cells[index++] = new ZdCell(item.getReqGoods().getIsReturned()?"不需归还":"需归还", 1, style3);
			cells[index++] = new ZdCell(item.getAmount()+"", 1, style3);
			cells[index++] = new ZdCell(item.getReqUserName(), 1, style3);
			cells[index++] = new ZdCell(item.getReqDeptName(), 1, style3);
			cells[index++] = new ZdCell(item.getRemark(), 1, style3);
			
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			cells[index++] = new ZdCell(sdf.format(item.getCreationTime()), 1, style3);
			
			zdExcel.add(cells);
		}
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<3;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("goods_audit_list");
		return NONE;
	}
	
	public List<OfficeGoodsReq> getGoodsReqList(){
		List<String> typelist = new ArrayList<String>();
		if(StringUtils.isBlank(searchType)){
			List<OfficeGoodsTypeAuth> authlist = officeGoodsTypeAuthService.getOfficeGoodsTypeAuthByUserId(getUnitId(), getLoginInfo().getUser().getId());
			if(authlist.size() > 0){
				String[] types = authlist.get(0).getTypeId().split(",");
				typelist = Arrays.asList(types);
			}
		}else{
			typelist.add(searchType);
		}
		return officeGoodsReqService.getOfficeGoodsReqByConditions(getUnitId(), typelist.toArray(new String[0]), searchName, applyType, null, startTime, endTime, getPage());
	}
	
	/**
	 * 物品类别
	 * @return
	 */
	public String goodsType() {//TODO
		goodsTypeList = officeGoodsTypeService.getOfficeGoodsTypeByUnitIdList(getUnitId());
		return SUCCESS;
	}

	public String goodsTypeAdd() {
		try{
			int num = 1;
			goodsTypeList = officeGoodsTypeService.getOfficeGoodsTypeByUnitIdList(getUnitId());
			for(OfficeGoodsType type : goodsTypeList){
				if(StringUtils.equals(officeGoodsType.getTypeName(), type.getTypeName())){
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("该物品类别已存在！");
					return SUCCESS;
				}
				if(num <= Integer.parseInt(type.getTypeId())){
					num = Integer.parseInt(type.getTypeId()) + 1;
				}
			}
			officeGoodsType.setUnitId(getUnitId());
			officeGoodsType.setTypeId(num + "");
			officeGoodsTypeService.save(officeGoodsType);
			
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("物品类别添加成功！");
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("物品类别添加失败！");
		}
		return SUCCESS;
	}
	
	public String goodsTypeEdit() {
		try{
			String typeName = officeGoodsType.getTypeName();
			officeGoodsType = officeGoodsTypeService.getOfficeGoodsTypeById(officeGoodsType.getId());
			goodsTypeList = officeGoodsTypeService.getOfficeGoodsTypeByUnitIdList(getUnitId());
			for(OfficeGoodsType type : goodsTypeList){
				if(!StringUtils.equals(officeGoodsType.getId(), type.getId())
						&& StringUtils.equals(typeName, type.getTypeName())){
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("该物品类别已存在！");
					return SUCCESS;
				}
			}
			officeGoodsType.setTypeName(typeName);
			officeGoodsTypeService.update(officeGoodsType);
			
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("物品类别修改成功！");
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("物品类别修改失败！");
		}
		return SUCCESS;
	}
	
	public String goodsTypeDelete() {
		try{
			Map<String, OfficeGoodsType> typesMap = officeGoodsTypeService.getOfficeGoodsTypeMapByIds(checkid);
			List<String> typeIds = new ArrayList<String>();
			for(Map.Entry<String, OfficeGoodsType> entry : typesMap.entrySet()){
				OfficeGoodsType type = entry.getValue();
				typeIds.add(type.getTypeId());
			}
			int num;
			if(isHasAuditModel()){
				List<OfficeGoods> list = officeGoodsService.getGoodsByType(getUnitId(), typeIds.toArray(new String[0]));
				num = list.size();
			}else{
				List<OfficeGoodsDistribute> list = officeGoodsDistributeService.getGoodsDistributeByType(getUnitId(), typeIds.toArray(new String[0]));
				num = list.size();
			}
			if(num > 0){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("删除失败，要删除的类别下存在物品！请先删除后再执行本操作。");
				return SUCCESS;
			}
			officeGoodsTypeService.delete(checkid);
			officeGoodsTypeAuthService.updateByDelTypeIds(getUnitId(), typeIds.toArray(new String[0]));
			
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("物品类别删除成功！");
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("物品类别删除失败！");
		}
		return SUCCESS;
	}
	
	/**
	 * 权限设置
	 * @return
	 */
	public String goodsAuth() {
		return SUCCESS;
	}
	
	public String goodsAuthList() {//TODO
		goodsTypeAuthList = officeGoodsTypeAuthService.getOfficeGoodsTypeAuthByConditions(getUnitId(), searchName, getPage());
		return SUCCESS;
	}
	
	public String goodsAuthEdit() {
		goodsTypeList = officeGoodsTypeService.getOfficeGoodsTypeByUnitIdList(getUnitId());
		if(StringUtils.isNotBlank(officeGoodsTypeAuth.getId())){
			officeGoodsTypeAuth = officeGoodsTypeAuthService.getOfficeGoodsTypeAuthById(officeGoodsTypeAuth.getId());
			String[] types = officeGoodsTypeAuth.getTypeId().split(",");
			for(OfficeGoodsType type : goodsTypeList){
				if(Arrays.asList(types).contains(type.getTypeId())){
					type.setIsExist(true);
				}else{
					type.setIsExist(false);
				}
			}
		}
		else{
			officeGoodsTypeAuth = new OfficeGoodsTypeAuth();
			for(OfficeGoodsType type : goodsTypeList){
				type.setIsExist(false);
			}
		}
		return SUCCESS;
	}
	
	public String goodsAuthSave(){
		try{
			if(StringUtils.isNotBlank(officeGoodsTypeAuth.getId())){
				officeGoodsTypeAuthService.update(officeGoodsTypeAuth);
			}
			else{
				List<OfficeGoodsTypeAuth> list = officeGoodsTypeAuthService
						.getOfficeGoodsTypeAuthByUserId(getUnitId(), officeGoodsTypeAuth.getUserId());
				if(list.size() > 0){
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("已为该用户设置了权限，无法再次添加！");
					return SUCCESS;
				}
				officeGoodsTypeAuth.setUnitId(getUnitId());
				officeGoodsTypeAuth.setCreationTime(new Date());
				officeGoodsTypeAuthService.save(officeGoodsTypeAuth);
			}

			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("保存成功！");
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("保存失败！");
		}
		return SUCCESS;
	}
	
	public String goodsAuthDelete() {
		try{
			officeGoodsTypeAuthService.delete(checkid);

			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功！");
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败！");
		}
		return SUCCESS;
	}
	
	
	/**
	 * 不走审核的我的物品页面，只有查看功能
	 * @return
	 */
	public String myGoods2() {
		goodsTypeList = officeGoodsTypeService.getOfficeGoodsTypeByUnitIdList(getUnitId());
		return SUCCESS;
	}
	
	public String myGoodsList2() {//TODO
		goodsDistributeList = officeGoodsDistributeService.getOfficeGoodsDistributeListByConditions(getUnitId(), StringUtils.isBlank(searchType)?null:new String[]{searchType}, searchName, new String[]{getLoginInfo().getUser().getId()}, getPage());
		return SUCCESS;
	}
	
	/**
	 * 不走审核模式的物品管理，
	 * @return
	 */
	public String goodsManage2() {
		goodsTypeList = officeGoodsTypeService.getOfficeGoodsTypeByUnitIdList(getUnitId());
		return SUCCESS;
	}
	
	public String goodsManageList2() {
		String[] userIds = null;
		if(StringUtils.isNotBlank(searchUserName)){
			Map<String, User> userMap = userService.getUserWithDelMap(getUnitId());
			Set<String> setIds = new HashSet<String>();
			for(Map.Entry<String, User> entry : userMap.entrySet()){
				User user = entry.getValue();
				if(user.getRealname().indexOf(searchUserName) != -1){
					setIds.add(user.getId());
				}
			}
			userIds = setIds.toArray(new String[0]);
		}
		goodsDistributeList = officeGoodsDistributeService.getOfficeGoodsDistributeListByConditions(getUnitId(), StringUtils.isBlank(searchType)?null:new String[]{searchType}, searchName, userIds, getPage());
		return SUCCESS;
	}
	
	public String goodsManageAdd2() {
		goodsTypeList = officeGoodsTypeService.getOfficeGoodsTypeByUnitIdList(getUnitId());
		return SUCCESS;
	}
	
	public String goodsManageEdit2() {
		goodsTypeList = officeGoodsTypeService.getOfficeGoodsTypeByUnitIdList(getUnitId());
		officeGoodsDistribute = officeGoodsDistributeService.getOfficeGoodsDistributeById(officeGoodsDistribute.getId());
		if(StringUtils.isNotBlank(officeGoodsDistribute.getReceiverId())){
			String receiverName = "";
			User user = userService.getUser(officeGoodsDistribute.getReceiverId());
			if(user != null && StringUtils.isNotBlank(user.getRealname())){
				receiverName = user.getRealname();
			}
			Dept dept = deptService.getDept(user.getDeptid());
			if(dept != null && StringUtils.isNotBlank(dept.getDeptname())){
				receiverName += "(" + dept.getDeptname() + ")";
			}
			officeGoodsDistribute.setReceiverName(receiverName);
		}
		return SUCCESS;
	}
	
	public String goodsManageDistribute() {
		officeGoodsDistribute = officeGoodsDistributeService.getOfficeGoodsDistributeById(officeGoodsDistribute.getId());
		OfficeGoodsType type = officeGoodsTypeService.getOfficeGoodsTypeByTypeId(getUnitId(), officeGoodsDistribute.getType());
		if(type != null){
			officeGoodsDistribute.setTypeName(type.getTypeName());
		}
		if(StringUtils.isNotBlank(officeGoodsDistribute.getReceiverId())){
			String receiverName = "";
			User user = userService.getUser(officeGoodsDistribute.getReceiverId());
			if(user != null && StringUtils.isNotBlank(user.getRealname())){
				receiverName = user.getRealname();
			}
			Dept dept = deptService.getDept(user.getDeptid());
			if(dept != null && StringUtils.isNotBlank(dept.getDeptname())){
				receiverName += "(" + dept.getDeptname() + ")";
			}
			officeGoodsDistribute.setReceiverName(receiverName);
		}
		if(officeGoodsDistribute.getDistributeTime()==null){
			officeGoodsDistribute.setDistributeTime(new Date());
		}
		return SUCCESS;
	}
	
	public String goodsManageSave2() {
		try{
			Integer num = officeGoodsDistribute.getAmount();
			if(StringUtils.isBlank(officeGoodsDistribute.getId())){
				List<OfficeGoodsDistribute> list = new ArrayList<OfficeGoodsDistribute>();
				for(int i = 0; i < num; i++){
					OfficeGoodsDistribute item = new OfficeGoodsDistribute();
					item.setUnitId(getUnitId());
					item.setAddUserId(getLoginUser().getUserId());
					item.setName(officeGoodsDistribute.getName());
					item.setModel(officeGoodsDistribute.getModel());
					item.setPrice(officeGoodsDistribute.getPrice());
					item.setGoodsUnit(officeGoodsDistribute.getGoodsUnit());
					item.setType(officeGoodsDistribute.getType());
					item.setPurchaseTime(officeGoodsDistribute.getPurchaseTime());
					item.setGoodsRemark(officeGoodsDistribute.getGoodsRemark());
					item.setAmount(1);
					list.add(item);
				}
				officeGoodsDistributeService.batchInsertGoodsDistribute(list);
			}
			else{
				officeGoodsDistributeService.update(officeGoodsDistribute);
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("物品登记成功！");
		} catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("物品登记失败！");
		}
		return SUCCESS;
	}
	
	public String goodsDelete2() {
		try{
			officeGoodsDistributeService.delete(checkid);
			
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功！");
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败！");
		}
		return SUCCESS;
	}
	
	public OfficeGoods getOfficeGoods() {
		return officeGoods;
	}

	public void setOfficeGoods(OfficeGoods officeGoods) {
		this.officeGoods = officeGoods;
	}

	public List<OfficeGoods> getGoodsList() {
		return goodsList;
	}

	public List<OfficeGoodsChangeLog> getGoodsChangeLogList() {
		return goodsChangeLogList;
	}

	public List<OfficeGoodsReq> getGoodsRequestList() {
		return goodsRequestList;
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

	public void setOfficeGoodsReqService(OfficeGoodsReqService officeGoodsReqService) {
		this.officeGoodsReqService = officeGoodsReqService;
	}

	public void setOfficeGoodsTypeService(
			OfficeGoodsTypeService officeGoodsTypeService) {
		this.officeGoodsTypeService = officeGoodsTypeService;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String[] getCheckid() {
		return checkid;
	}

	public void setCheckid(String[] checkid) {
		this.checkid = checkid;
	}

	public OfficeGoodsChangeLog getGoodsChangeLog() {
		return goodsChangeLog;
	}

	public void setGoodsChangeLog(OfficeGoodsChangeLog goodsChangeLog) {
		this.goodsChangeLog = goodsChangeLog;
	}

	public OfficeGoodsReq getOfficeGoodsReq() {
		return officeGoodsReq;
	}

	public void setOfficeGoodsReq(OfficeGoodsReq officeGoodsReq) {
		this.officeGoodsReq = officeGoodsReq;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public OfficeGoodsType getOfficeGoodsType() {
		return officeGoodsType;
	}

	public void setOfficeGoodsType(OfficeGoodsType officeGoodsType) {
		this.officeGoodsType = officeGoodsType;
	}

	public boolean isGoodsManageAuth() {
		return goodsManageAuth = isCustomRole(OfficeGoodsConstants.OFFICE_GOODS_MANAGE);
	}

	public void setGoodsManageAuth(boolean goodsManageAuth) {
		this.goodsManageAuth = goodsManageAuth;
	}

	public boolean isGoodsAuditAuth() {
		return goodsManageAuth = isCustomRole(OfficeGoodsConstants.OFFICE_GOODS_AUDIT);
	}

	public void setGoodsAuditAuth(boolean goodsAuditAuth) {
		this.goodsAuditAuth = goodsAuditAuth;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public OfficeGoodsTypeAuth getOfficeGoodsTypeAuth() {
		return officeGoodsTypeAuth;
	}

	public void setOfficeGoodsTypeAuth(OfficeGoodsTypeAuth officeGoodsTypeAuth) {
		this.officeGoodsTypeAuth = officeGoodsTypeAuth;
	}

	public List<OfficeGoodsTypeAuth> getGoodsTypeAuthList() {
		return goodsTypeAuthList;
	}

	public void setOfficeGoodsTypeAuthService(
			OfficeGoodsTypeAuthService officeGoodsTypeAuthService) {
		this.officeGoodsTypeAuthService = officeGoodsTypeAuthService;
	}

	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

	public boolean isHasAuditModel() {
		SystemIni systemIni = systemIniService.getSystemIni(OfficeGoodsConstants.GOODS_AUDIT_MODEL);
		if(systemIni != null && "0".equals(systemIni.getNowValue()))//不走审核模式
			hasAuditModel = false;
		else
			hasAuditModel = true;
		return hasAuditModel;
	}

	public void setHasAuditModel(boolean hasAuditModel) {
		this.hasAuditModel = hasAuditModel;
	}

	public OfficeGoodsDistribute getOfficeGoodsDistribute() {
		return officeGoodsDistribute;
	}

	public void setOfficeGoodsDistribute(OfficeGoodsDistribute officeGoodsDistribute) {
		this.officeGoodsDistribute = officeGoodsDistribute;
	}

	public List<OfficeGoodsDistribute> getGoodsDistributeList() {
		return goodsDistributeList;
	}

	public void setOfficeGoodsDistributeService(
			OfficeGoodsDistributeService officeGoodsDistributeService) {
		this.officeGoodsDistributeService = officeGoodsDistributeService;
	}

	public String getSearchUserName() {
		return searchUserName;
	}

	public void setSearchUserName(String searchUserName) {
		this.searchUserName = searchUserName;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

}
