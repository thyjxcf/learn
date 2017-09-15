package net.zdsoft.office.goodmanage.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.constant.WeikeAppConstant;
import net.zdsoft.eis.base.subsystemcall.service.OfficeSubsystemService;
import net.zdsoft.eis.component.push.client.WeikePushClient;
import net.zdsoft.eis.component.push.entity.WKPushParm;
import net.zdsoft.eis.frame.util.RedisUtils;
import net.zdsoft.eis.remote.enums.WeikeAppEnum;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.dataimport.exception.DataValidException;
import net.zdsoft.office.convertflow.constant.ConvertFlowConstants;
import net.zdsoft.office.enums.WeikeAppUrlEnum;
import net.zdsoft.office.goodmanage.constant.OfficeGoodsConstants;
import net.zdsoft.office.goodmanage.dao.OfficeGoodsDao;
import net.zdsoft.office.goodmanage.entity.OfficeGoods;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsReq;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsType;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsTypeAuth;
import net.zdsoft.office.goodmanage.service.OfficeGoodsReqService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsTypeAuthService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsTypeService;
import net.zdsoft.office.msgcenter.entity.OfficeBusinessJump;
import net.zdsoft.office.msgcenter.service.OfficeBusinessJumpService;
/**
 * office_goods
 * @author 
 * 
 */
public class OfficeGoodsServiceImpl implements OfficeGoodsService{
	private OfficeGoodsDao officeGoodsDao;
	private OfficeGoodsTypeService officeGoodsTypeService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private OfficeSubsystemService officeSubsystemService;
	private OfficeGoodsTypeAuthService officeGoodsTypeAuthService;
	private UnitService unitService;
	private ModuleService moduleService;
	private OfficeGoodsReqService officeGoodsReqService;
	private OfficeBusinessJumpService officeBusinessJumpService;

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

	public void setOfficeGoodsTypeAuthService(OfficeGoodsTypeAuthService officeGoodsTypeAuthService) {
		this.officeGoodsTypeAuthService = officeGoodsTypeAuthService;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setOfficeSubsystemService(OfficeSubsystemService officeSubsystemService) {
		this.officeSubsystemService = officeSubsystemService;
	}

	public void setOfficeGoodsReqService(OfficeGoodsReqService officeGoodsReqService) {
		this.officeGoodsReqService = officeGoodsReqService;
	}
	public void setOfficeBusinessJumpService(
			OfficeBusinessJumpService officeBusinessJumpService) {
		this.officeBusinessJumpService = officeBusinessJumpService;
	}
	@Override
	public OfficeGoods save(OfficeGoods officeGoods){
		return officeGoodsDao.save(officeGoods);
	}

	@Override
	public Integer delete(String[] ids){
		return officeGoodsDao.delete(ids);
	}

	@Override
	public Integer update(OfficeGoods officeGoods){
		return officeGoodsDao.update(officeGoods);
	}

	@Override
	public OfficeGoods getOfficeGoodsById(String id){
		return officeGoodsDao.getOfficeGoodsById(id);
	}

	@Override
	public Map<String, OfficeGoods> getOfficeGoodsMapByIds(String[] ids){
		return officeGoodsDao.getOfficeGoodsMapByIds(ids);
	}

	@Override
	public List<OfficeGoods> getOfficeGoodsList(){
		return officeGoodsDao.getOfficeGoodsList();
	}

	@Override
	public List<OfficeGoods> getOfficeGoodsPage(Pagination page){
		return officeGoodsDao.getOfficeGoodsPage(page);
	}

	@Override
	public List<OfficeGoods> getOfficeGoodsByUnitIdList(String unitId){
		return officeGoodsDao.getOfficeGoodsByUnitIdList(unitId);
	}

	@Override
	public List<OfficeGoods> getOfficeGoodsByUnitIdPage(String unitId, Pagination page){
		return officeGoodsDao.getOfficeGoodsByUnitIdPage(unitId, page);
	}
	
	@Override
	public List<OfficeGoods> getOfficeGoodsByConditions(String unitId, String[] goodsTypes, String goodsName, Boolean isApply, Pagination page){
		List<OfficeGoods> goodslist = officeGoodsDao.getOfficeGoodsByConditions(unitId, goodsTypes, goodsName, isApply, page);
		Map<String, OfficeGoodsType> typeMap = officeGoodsTypeService.getOfficeGoodsTypeMapByUnitId(unitId);
		for(OfficeGoods goods : goodslist){
			goods.setTypeName(typeMap.get(goods.getType())==null?"不存在":typeMap.get(goods.getType()).getTypeName());
		}
		return goodslist;
	}
	
	@Override
	public List<OfficeGoods> getGoodsByType(String unitId, String[] typeIds){
		return officeGoodsDao.getGoodsByType(unitId, typeIds);
	}
	
	@Override
	public List<OfficeGoods> getGoodsByGoodsUnit(String unitId, String[] goodsUnitIds){
		return officeGoodsDao.getGoodsByGoodsUnit(unitId, goodsUnitIds);
	}

	public void setOfficeGoodsDao(OfficeGoodsDao officeGoodsDao){
		this.officeGoodsDao = officeGoodsDao;
	}

	public void setOfficeGoodsTypeService(
			OfficeGoodsTypeService officeGoodsTypeService) {
		this.officeGoodsTypeService = officeGoodsTypeService;
	}

	@Override
	public void sendMsg(User user, Unit unit, OfficeGoods officeGoods, Integer state, String[] userIds, String goodsReqId) {
		try{
			StringBuffer title = new StringBuffer();
			StringBuffer content = new StringBuffer();
			String wkTitle ="";
			String wkAuditString ="";
			int jumpSate = WeikeAppConstant.DETAILE_URL;
			if(state == OfficeGoodsConstants.GOODS_AUDIT_PASS){
				title.append("物品管理申请通过信息提醒");
				content.append("您好，【"+officeGoods.getName()+"】物品，领用申请通过。");
				
				wkTitle = "物品领用审批";
				wkAuditString = "审批结果：审核通过";
			}else if(state == OfficeGoodsConstants.GOODS_AUDIT_NOT_PASS){
				title.append("物品管理申请未通过信息提醒");
				content.append("您好，【"+officeGoods.getName()+"】物品，领用申请未通过。");
				
				wkTitle = "物品领用审批";
				wkAuditString = "审批结果：审核未通过";
			}else{
				jumpSate = WeikeAppConstant.AUDIT_URL;
				wkTitle = "物品领用申请";
				title.append("物品管理申请信息提醒");
				content.append("您好，【"+officeGoods.getName()+"】物品，有新的领用申请需要处理，请尽快处理。申请人："+user.getRealname());
				CustomRole role = customRoleService.getCustomRoleByRoleCode(unit.getId(), OfficeGoodsConstants.OFFICE_GOODS_AUDIT);
				Set<String> roleUserIds = new HashSet<String>();
				if(role!=null){
					List<CustomRoleUser> customRoleUserList = customRoleUserService.getCustomRoleUserList(role.getId());
					for (CustomRoleUser item : customRoleUserList) {
						roleUserIds.add(item.getUserId());
					}
					Map<String, List<OfficeGoodsTypeAuth>> officeGoodsTypeAuthMapByUserIds = officeGoodsTypeAuthService.getOfficeGoodsTypeAuthMapByUserIds(roleUserIds.toArray(new String[0]));
					roleUserIds = new HashSet<String>();
					for (Map.Entry<String, List<OfficeGoodsTypeAuth>> entry : officeGoodsTypeAuthMapByUserIds.entrySet()) {
						for (String string : entry.getValue().get(0).getTypeId().split(",")) {
							if(string.equals(officeGoods.getType())){
								roleUserIds.add(entry.getKey());
								break;
							}
						}
						
					}
				}
				userIds = roleUserIds.toArray(new String[0]);
			}
			if(officeSubsystemService != null){
				String msgId = officeSubsystemService.sendMsgDetail(user, unit, title.toString(), content.toString(), content.toString(),
						false, BaseConstant.MSG_TYPE_GOODS, userIds,null);
				
				//申请成功，发送消息同时设置消息跳转信息
				OfficeGoodsReq goodsReq = officeGoodsReqService.getOfficeGoodsReqById(goodsReqId);
				if(goodsReq != null && goodsReq.getState() == OfficeGoodsConstants.GOODS_NOT_AUDIT){
					officeBusinessJumpService.pushMsgUrl(goodsReq, OfficeBusinessJump.OFFICE_GOODS, msgId);
				}
			}else{
				throw new DataValidException("推送信息失败！原因：信息未找到！");
			}
			
			//微课推送消息
			WKPushParm parm = new WKPushParm();
			parm.setMsgTitle(wkTitle);
			parm.setHeadContent(user.getRealname());
			parm.setBodyTitle(wkTitle);
			List<String> rows = new ArrayList<String>();
			//申请成功，发送消息同时设置消息跳转信息
			OfficeGoodsReq goodsReq = officeGoodsReqService.getOfficeGoodsReqById(goodsReqId);
			
			rows.add("物品名称：" + officeGoods.getName());
			if(goodsReq != null)
				rows.add("申请数量：" + goodsReq.getAmount());
			if(StringUtils.isNotBlank(wkAuditString)){
				rows.add(wkAuditString);
			}
			
			String domain = RedisUtils.get("EIS.BASE.PATH.V6");
			if(StringUtils.isNotBlank(domain)){
				parm.setJumpType(WeikeAppConstant.JUMP_TYPE_0);
				String url = WeikeAppUrlEnum.getWeikeFlowUrl(ConvertFlowConstants.OFFICE_GOODS+"", jumpSate, goodsReq.getId(), null);
				parm.setUrl(domain + url);
				parm.setFootContent("详情");
			}
			parm.setRowsContent(rows.toArray(new String[0]));
			WeikePushClient.getInstance().pushMessage("", userIds, parm);
		}catch(Exception e){
			System.out.println("物品管理：发送消息失败=============");
			e.printStackTrace();
		}
	}

}
	