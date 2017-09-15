package net.zdsoft.office.goodmanage.service.impl;

import java.util.*;

import net.zdsoft.office.convertflow.constant.ConvertFlowConstants;
import net.zdsoft.office.convertflow.service.OfficeConvertFlowService;
import net.zdsoft.office.goodmanage.entity.OfficeGoods;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsReq;
import net.zdsoft.office.goodmanage.service.OfficeGoodsReqService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsService;
import net.zdsoft.office.goodmanage.constant.OfficeGoodsConstants;
import net.zdsoft.office.goodmanage.dao.OfficeGoodsReqDao;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.Pagination;
/**
 * office_goods_req
 * @author 
 * 
 */
public class OfficeGoodsReqServiceImpl implements OfficeGoodsReqService{
	private OfficeGoodsReqDao officeGoodsReqDao;
	private OfficeGoodsService officeGoodsService;
	private UserService userService;
	private DeptService deptService;
	private OfficeConvertFlowService officeConvertFlowService;

	public void setOfficeConvertFlowService(
			OfficeConvertFlowService officeConvertFlowService) {
		this.officeConvertFlowService = officeConvertFlowService;
	}
	@Override
	public OfficeGoodsReq save(OfficeGoodsReq officeGoodsReq){
		return officeGoodsReqDao.save(officeGoodsReq);
	}

	@Override
	public Integer delete(String[] ids){
		return officeGoodsReqDao.delete(ids);
	}

	@Override
	public Integer deleteByGoodsIds(String[] goodsIds){
		return officeGoodsReqDao.deleteByGoodsIds(goodsIds);
	}
	
	@Override
	public Integer update(OfficeGoodsReq officeGoodsReq){
		return officeGoodsReqDao.update(officeGoodsReq);
	}

	public OfficeGoodsReq submitSave(OfficeGoodsReq officeGoodsReq){
		OfficeGoodsReq ent = officeGoodsReqDao.save(officeGoodsReq);
		//同步到审核流程中间表，给微办公手机端使用
		officeConvertFlowService.startFlow(ent, ConvertFlowConstants.OFFICE_GOODS);
		return ent;
	}
	
	public Integer auditSave(OfficeGoodsReq officeGoodsReq){
		Integer m = officeGoodsReqDao.update(officeGoodsReq);
		//同步到审核流程中间表，给微办公手机端使用
		boolean isPass = false;
		if(OfficeGoodsConstants.GOODS_AUDIT_PASS == officeGoodsReq.getState()){
			isPass = true;
		}

		officeConvertFlowService.completeAudit(isPass, officeGoodsReq.getId());
		return m;
	}
	
	@Override
	public OfficeGoodsReq getOfficeGoodsReqById(String id){
		OfficeGoodsReq goodsReq = officeGoodsReqDao.getOfficeGoodsReqById(id);
		User user = userService.getUser(goodsReq.getReqUserId());
		if(user != null){
			goodsReq.setReqUserName(user.getRealname());
		}
		Dept dept = deptService.getDept(goodsReq.getReqDeptId());
		if(dept != null){
			goodsReq.setReqDeptName(dept.getDeptname());
		}
		return goodsReq;
	}

	@Override
	public Map<String, OfficeGoodsReq> getOfficeGoodsReqMapByIds(String[] ids){
		return officeGoodsReqDao.getOfficeGoodsReqMapByIds(ids);
	}

	@Override
	public List<OfficeGoodsReq> getOfficeGoodsReqList(){
		return officeGoodsReqDao.getOfficeGoodsReqList();
	}

	@Override
	public List<OfficeGoodsReq> getOfficeGoodsReqPage(Pagination page){
		return officeGoodsReqDao.getOfficeGoodsReqPage(page);
	}

	@Override
	public List<OfficeGoodsReq> getOfficeGoodsReqByUnitIdList(String unitId){
		return officeGoodsReqDao.getOfficeGoodsReqByUnitIdList(unitId);
	}

	@Override
	public List<OfficeGoodsReq> getOfficeGoodsReqByUnitIdPage(String unitId, Pagination page){
		return officeGoodsReqDao.getOfficeGoodsReqByUnitIdPage(unitId, page);
	}
	
	@Override
	public List<OfficeGoodsReq> getOfficeGoodsReqByConditions(String unitId, String[] goodsTypes, String goodsName,
			String applyType, String applyUserId, Date beginTime, Date endTime, Pagination page){
		List<OfficeGoods> goodslist = officeGoodsService.getOfficeGoodsByConditions(unitId, goodsTypes, goodsName,false, null);
		
		Set<String> goodsIds = new HashSet<String>();
		Map<String, OfficeGoods> goodsMap = new HashMap<String, OfficeGoods>();
		Map<String, User> userMap = userService.getUserWithDelMap(unitId);
		Map<String, Dept> deptMap = deptService.getDeptMap(unitId);
		
		for(OfficeGoods goods : goodslist){
			goodsIds.add(goods.getId());
			goodsMap.put(goods.getId(), goods);
		}
		
		List<OfficeGoodsReq> goodsReqlist = officeGoodsReqDao.getOfficeGoodsReqByConditions(unitId, goodsIds.toArray(new String[0]), applyType, applyUserId, beginTime, endTime, page);
		for(OfficeGoodsReq goodsReq : goodsReqlist){
			User user = userMap.get(goodsReq.getReqUserId());
			if(user != null){
				goodsReq.setReqUserName(user.getRealname());
			}
			
			Dept dept = deptMap.get(goodsReq.getReqDeptId());
			if(dept != null){
				goodsReq.setReqDeptName(dept.getDeptname());
			}
			
			if(goodsReq.getState() == OfficeGoodsConstants.GOODS_NOT_AUDIT){
				goodsReq.setStateStr("待审核");
			}
			else if(goodsReq.getState() == OfficeGoodsConstants.GOODS_AUDIT_PASS){
				goodsReq.setStateStr("已通过");
			}
			else if(goodsReq.getState() == OfficeGoodsConstants.GOODS_AUDIT_NOT_PASS){
				goodsReq.setStateStr("未通过"+"("+goodsReq.getAdvice()+")");
			}
			else{
				goodsReq.setStateStr("已归还");
			}
			
			OfficeGoods goods = goodsMap.get(goodsReq.getGoodsId());
			if(goods != null){
				goodsReq.setReqGoods(goods);
			}else{
				goodsReq.setReqGoods(new OfficeGoods());
			}
		}
		return goodsReqlist;
	}

	public void setOfficeGoodsReqDao(OfficeGoodsReqDao officeGoodsReqDao){
		this.officeGoodsReqDao = officeGoodsReqDao;
	}

	public void setOfficeGoodsService(OfficeGoodsService officeGoodsService) {
		this.officeGoodsService = officeGoodsService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
}
