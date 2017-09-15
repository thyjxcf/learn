package net.zdsoft.office.goodmanage.service.impl;

import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.goodmanage.entity.OfficeGoodsTypeAuth;
import net.zdsoft.office.goodmanage.service.OfficeGoodsTypeAuthService;
import net.zdsoft.office.goodmanage.dao.OfficeGoodsTypeAuthDao;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.Pagination;
/**
 * office_goods_type_auth
 * @author 
 * 
 */
public class OfficeGoodsTypeAuthServiceImpl implements OfficeGoodsTypeAuthService{
	private OfficeGoodsTypeAuthDao officeGoodsTypeAuthDao;
	private UserService userService;

	@Override
	public OfficeGoodsTypeAuth save(OfficeGoodsTypeAuth officeGoodsTypeAuth){
		return officeGoodsTypeAuthDao.save(officeGoodsTypeAuth);
	}

	@Override
	public Integer delete(String[] ids){
		return officeGoodsTypeAuthDao.delete(ids);
	}

	@Override
	public Integer update(OfficeGoodsTypeAuth officeGoodsTypeAuth){
		return officeGoodsTypeAuthDao.update(officeGoodsTypeAuth);
	}

	@Override
	public OfficeGoodsTypeAuth getOfficeGoodsTypeAuthById(String id){
		OfficeGoodsTypeAuth goodsAuth = officeGoodsTypeAuthDao.getOfficeGoodsTypeAuthById(id);
		User user = userService.getUser(goodsAuth.getUserId());
		if(user !=null){
			goodsAuth.setUserName(user.getName());
			goodsAuth.setRealName(user.getRealname());
		}
		return goodsAuth;
	}

	@Override
	public Map<String, OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthMapByIds(String[] ids){
		return officeGoodsTypeAuthDao.getOfficeGoodsTypeAuthMapByIds(ids);
	}

	@Override
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthList(){
		return officeGoodsTypeAuthDao.getOfficeGoodsTypeAuthList();
	}

	@Override
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthPage(Pagination page){
		return officeGoodsTypeAuthDao.getOfficeGoodsTypeAuthPage(page);
	}

	@Override
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthByUnitIdList(String unitId){
		return officeGoodsTypeAuthDao.getOfficeGoodsTypeAuthByUnitIdList(unitId);
	}

	@Override
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthByUnitIdPage(String unitId, Pagination page){
		return officeGoodsTypeAuthDao.getOfficeGoodsTypeAuthByUnitIdPage(unitId, page);
	}
	
	@Override
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthByConditions(String unitId, String searchName, Pagination page){
		List<OfficeGoodsTypeAuth> authlist = officeGoodsTypeAuthDao.getOfficeGoodsTypeAuthByConditions(unitId, searchName, page);
		Map<String, User> userMap = userService.getUserWithDelMap(unitId);
		for(OfficeGoodsTypeAuth auth : authlist){
			User user = userMap.get(auth.getUserId());
			if(user != null){
				auth.setUserName(user.getName());
				auth.setRealName(user.getRealname());
			}
		}
		return authlist;
	}
	
	@Override
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthByUserId(String unitId, String userId){
		return officeGoodsTypeAuthDao.getOfficeGoodsTypeAuthByUserId(unitId, userId);
	}
	
	@Override
	public void updateByDelTypeIds(String unitId, String[] delTypeIds){
		List<OfficeGoodsTypeAuth> authlist = this.getOfficeGoodsTypeAuthByUnitIdList(unitId);
		List<String> delIds = new ArrayList<String>();
		List<OfficeGoodsTypeAuth> updatelist = new ArrayList<OfficeGoodsTypeAuth>();
		String newTypeIdsStr = "";
		boolean flag = false;//判断是否要更新
		for(OfficeGoodsTypeAuth auth : authlist){
			newTypeIdsStr = "";
			flag = false;
			String[] typeIds = auth.getTypeId().split(",");
			for(String oldTypeId : typeIds){
				if(!isExist(delTypeIds, oldTypeId)){//如果原有的类型不在删除类型中
					if(StringUtils.isBlank(newTypeIdsStr)){
						newTypeIdsStr = oldTypeId;
					}else{
						newTypeIdsStr += "," + oldTypeId;
					}
				}else{
					flag = true;
				}
			}
			if(StringUtils.isBlank(newTypeIdsStr)){//如果类型全被删除，则删除此信息
				delIds.add(auth.getId());
			}else{
				if(flag){
					auth.setTypeId(newTypeIdsStr);
					updatelist.add(auth);
				}
			}
		}
		if(delIds.size() > 0){
			this.delete(delIds.toArray(new String[0]));
		}
		if(updatelist.size() > 0){
			this.batchUpdate(updatelist);
		}
	}
	
	public boolean isExist(String[] strs, String str){
		for(int i = 0; i < strs.length; i++){
			if(StringUtils.equals(strs[i], str)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void batchUpdate(List<OfficeGoodsTypeAuth> authlist){
		officeGoodsTypeAuthDao.batchUpdate(authlist);
	}

	public void setOfficeGoodsTypeAuthDao(OfficeGoodsTypeAuthDao officeGoodsTypeAuthDao){
		this.officeGoodsTypeAuthDao = officeGoodsTypeAuthDao;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Map<String, List<OfficeGoodsTypeAuth>> getOfficeGoodsTypeAuthMapByUserIds(String[] userIds) {
		Map<String, List<OfficeGoodsTypeAuth>> map = new HashMap<String, List<OfficeGoodsTypeAuth>>();
		List<OfficeGoodsTypeAuth> officeGoodsTypeAuthList = officeGoodsTypeAuthDao.getOfficeGoodsTypeAuthList(userIds);
		for (OfficeGoodsTypeAuth item : officeGoodsTypeAuthList) {
			List<OfficeGoodsTypeAuth> list = map.get(item.getUserId());
			if(list == null){
				list = new ArrayList<OfficeGoodsTypeAuth>();
				map.put(item.getUserId(), list);
			}
			list.add(item);
		}
		return map;
	}
	
}
	