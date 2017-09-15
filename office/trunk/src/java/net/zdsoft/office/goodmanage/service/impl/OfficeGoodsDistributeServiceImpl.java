package net.zdsoft.office.goodmanage.service.impl;

import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.goodmanage.entity.OfficeGoodsDistribute;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsType;
import net.zdsoft.office.goodmanage.service.OfficeGoodsDistributeService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsTypeService;
import net.zdsoft.office.goodmanage.dao.OfficeGoodsDistributeDao;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.Pagination;
/**
 * office_goods_distribute
 * @author 
 * 
 */
public class OfficeGoodsDistributeServiceImpl implements OfficeGoodsDistributeService{
	private OfficeGoodsDistributeDao officeGoodsDistributeDao;
	private OfficeGoodsTypeService officeGoodsTypeService;
	private UserService userService;
	private DeptService deptService;

	@Override
	public OfficeGoodsDistribute save(OfficeGoodsDistribute officeGoodsDistribute){
		return officeGoodsDistributeDao.save(officeGoodsDistribute);
	}

	@Override
	public void batchInsertGoodsDistribute(List<OfficeGoodsDistribute> goodsDistributeList){
		officeGoodsDistributeDao.batchInsertGoodsDistribute(goodsDistributeList);
	}
	
	@Override
	public Integer delete(String[] ids){
		return officeGoodsDistributeDao.delete(ids);
	}

	@Override
	public Integer update(OfficeGoodsDistribute officeGoodsDistribute){
		return officeGoodsDistributeDao.update(officeGoodsDistribute);
	}

	@Override
	public OfficeGoodsDistribute getOfficeGoodsDistributeById(String id){
		return officeGoodsDistributeDao.getOfficeGoodsDistributeById(id);
	}

	@Override
	public Map<String, OfficeGoodsDistribute> getOfficeGoodsDistributeMapByIds(String[] ids){
		return officeGoodsDistributeDao.getOfficeGoodsDistributeMapByIds(ids);
	}

	@Override
	public List<OfficeGoodsDistribute> getOfficeGoodsDistributeList(){
		return officeGoodsDistributeDao.getOfficeGoodsDistributeList();
	}

	@Override
	public List<OfficeGoodsDistribute> getOfficeGoodsDistributePage(Pagination page){
		return officeGoodsDistributeDao.getOfficeGoodsDistributePage(page);
	}

	@Override
	public List<OfficeGoodsDistribute> getOfficeGoodsDistributeByUnitIdList(String unitId){
		return officeGoodsDistributeDao.getOfficeGoodsDistributeByUnitIdList(unitId);
	}

	@Override
	public List<OfficeGoodsDistribute> getOfficeGoodsDistributeByUnitIdPage(String unitId, Pagination page){
		return officeGoodsDistributeDao.getOfficeGoodsDistributeByUnitIdPage(unitId, page);
	}

	@Override
	public List<OfficeGoodsDistribute> getOfficeGoodsDistributeListByConditions(String unitId, String[] goodsTypes, 
			String goodsName, String[] receiverIds, Pagination page){
		List<OfficeGoodsDistribute> list = officeGoodsDistributeDao.getOfficeGoodsDistributeListByConditions(unitId, goodsTypes, goodsName, receiverIds, page);
		Map<String, OfficeGoodsType> typeMap = officeGoodsTypeService.getOfficeGoodsTypeMapByUnitId(unitId);
		Map<String, User> userMap = userService.getUserWithDelMap(unitId);
		Map<String, Dept> deptMap = deptService.getDeptMap(unitId);
		for(OfficeGoodsDistribute item : list){
			item.setTypeName(typeMap.get(item.getType())==null?"不存在":typeMap.get(item.getType()).getTypeName());
			User user = userMap.get(item.getReceiverId());
			if(user != null){
				item.setReceiverName(user.getRealname());
				Dept dept = deptMap.get(user.getDeptid());
				if(dept != null){
					item.setReceiverDeptName(dept.getDeptname());
				}else{
					item.setReceiverDeptName("");
				}
			}
			else{
				item.setReceiverName("");
				item.setReceiverDeptName("");
			}
		}
		return list;
	}
	
	@Override
	public List<OfficeGoodsDistribute> getGoodsDistributeByType(String unitId, String[] typeIds){
		return officeGoodsDistributeDao.getGoodsDistributeByType(unitId, typeIds);
	}
	
	public void setOfficeGoodsDistributeDao(OfficeGoodsDistributeDao officeGoodsDistributeDao){
		this.officeGoodsDistributeDao = officeGoodsDistributeDao;
	}

	public void setOfficeGoodsTypeService(
			OfficeGoodsTypeService officeGoodsTypeService) {
		this.officeGoodsTypeService = officeGoodsTypeService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
}

	