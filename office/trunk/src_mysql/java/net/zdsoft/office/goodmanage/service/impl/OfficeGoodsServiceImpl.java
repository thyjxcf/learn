package net.zdsoft.office.goodmanage.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.subsystemcall.service.OfficeSubsystemService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.dataimport.exception.DataValidException;
import net.zdsoft.office.goodmanage.constant.OfficeGoodsConstants;
import net.zdsoft.office.goodmanage.dao.OfficeGoodsDao;
import net.zdsoft.office.goodmanage.entity.OfficeGoods;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsType;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsTypeAuth;
import net.zdsoft.office.goodmanage.service.OfficeGoodsService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsTypeAuthService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsTypeService;
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
	public void sendMsg(User user, Unit unit, OfficeGoods officeGoods, Integer state, String[] userIds) {
		try{
			StringBuffer title = new StringBuffer();
			StringBuffer content = new StringBuffer();
			if(state == OfficeGoodsConstants.GOODS_AUDIT_PASS){
				title.append("物品管理申请通过信息提醒");
				content.append("您好，【"+officeGoods.getName()+"】物品，领用申请通过。");
			}else if(state == OfficeGoodsConstants.GOODS_AUDIT_NOT_PASS){
				title.append("物品管理申请未通过信息提醒");
				content.append("您好，【"+officeGoods.getName()+"】物品，领用申请未通过。");
			}else{
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
				officeSubsystemService.sendMsgDetail(user, unit, title.toString(), content.toString(), content.toString(),
						false, BaseConstant.MSG_TYPE_GOODS, userIds,null);
			}else{
				throw new DataValidException("推送信息失败！原因：信息未找到！");
			}
		}catch(Exception e){
			System.out.println("物品管理：发送消息失败=============");
			e.printStackTrace();
		}
	}

}
	