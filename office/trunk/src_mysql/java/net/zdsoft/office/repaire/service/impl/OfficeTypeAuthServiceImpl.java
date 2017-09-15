package net.zdsoft.office.repaire.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.repaire.dao.OfficeTypeAuthDao;
import net.zdsoft.office.repaire.entity.OfficeTeachAreaAuth;
import net.zdsoft.office.repaire.entity.OfficeTypeAuth;
import net.zdsoft.office.repaire.service.OfficeTeachAreaAuthService;
import net.zdsoft.office.repaire.service.OfficeTypeAuthService;
import net.zdsoft.office.util.Constants;
/**
 * office_type_auth
 * @author 
 * 
 */
public class OfficeTypeAuthServiceImpl implements OfficeTypeAuthService{
	private OfficeTypeAuthDao officeTypeAuthDao;
	private OfficeTeachAreaAuthService officeTeachAreaAuthService;
	private UserService userService;
	
	public void setOfficeTeachAreaAuthService(
			OfficeTeachAreaAuthService officeTeachAreaAuthService) {
		this.officeTeachAreaAuthService = officeTeachAreaAuthService;
	}

	@Override
	public OfficeTypeAuth save(OfficeTypeAuth officeTypeAuth){
		return officeTypeAuthDao.save(officeTypeAuth);
	}

	@Override
	public Integer delete(String[] ids){
		return officeTypeAuthDao.delete(ids);
	}

	@Override
	public Integer update(OfficeTypeAuth officeTypeAuth){
		return officeTypeAuthDao.update(officeTypeAuth);
	}

	@Override
	public OfficeTypeAuth getOfficeTypeAuthById(String id){
		return officeTypeAuthDao.getOfficeTypeAuthById(id);
	}

	@Override
	public Map<String, OfficeTypeAuth> getOfficeTypeAuthMapByIds(String[] ids){
		return officeTypeAuthDao.getOfficeTypeAuthMapByIds(ids);
	}

	@Override
	public List<OfficeTypeAuth> getOfficeTypeAuthList(String unitId){
		return officeTypeAuthDao.getOfficeTypeAuthList(unitId);
	}

	@Override
	public List<OfficeTypeAuth> getOfficeTypeAuthPage(String unitId, Pagination page){
		return officeTypeAuthDao.getOfficeTypeAuthPage(unitId, page);
	}

	public void setOfficeTypeAuthDao(OfficeTypeAuthDao officeTypeAuthDao){
		this.officeTypeAuthDao = officeTypeAuthDao;
	}
	@Override
	public List<OfficeTypeAuth> getOfficeTypeAuthUserPage(String unitId,
			String state, Pagination page) {
		List<OfficeTypeAuth> auths = new ArrayList<OfficeTypeAuth>();
		auths = officeTypeAuthDao.getUserPage(unitId,state, page);
		if(auths.size() == 0)
			return auths;
		Set<String> userIds = new HashSet<String>();
		for(OfficeTypeAuth e : auths ){
			userIds.add(e.getUserId());
		}
		Map<String,User> usermap = userService.getUserWithDelMap(userIds.toArray(new String[]{}));
		for(OfficeTypeAuth e : auths ){
			if(usermap.get(e.getUserId())!=null){
				e.setUserName(usermap.get(e.getUserId()).getRealname());
			}
		}
		return auths;
	}
	
	@Override
	public List<OfficeTypeAuth> getOfficeTypeAuthList(String unitId, String state, String typeId){
		return officeTypeAuthDao.getOfficeTypeAuthList(unitId, state, typeId);
	}
	
	@Override
	public List<OfficeTypeAuth> getOfficeTypeAuthList(String state,
			String userId) {
		return officeTypeAuthDao.getOfficeTypeAuthList(state,userId);
	}
	@Override
	public void saveAuth(String userId, String[] types, String[] areaIds) {
	    User user = userService.getUser(userId);
		List<OfficeTypeAuth> typelist = new ArrayList<OfficeTypeAuth>();
		List<OfficeTeachAreaAuth>arealist = new ArrayList<OfficeTeachAreaAuth>();
		for(int i = 0;i<types.length;i++){
			OfficeTypeAuth type = new OfficeTypeAuth();
			type.setCreateTime(new Date());
			type.setState(Constants.REPAIRE_STATE);
			type.setType(types[i]);
			type.setUserId(userId);
			type.setUnitId(user.getUnitid());
			typelist.add(type);
		}
		for(int i = 0 ;i <areaIds.length;i++){
			OfficeTeachAreaAuth area = new OfficeTeachAreaAuth();
			area.setCreateTime(new Date());
			area.setState(Constants.REPAIRE_STATE);
			area.setTeachAreaId(areaIds[i]);
			area.setUserId(userId);
			arealist.add(area);
		}
		officeTypeAuthDao.deleteByUserIds(new String[]{userId});
		officeTeachAreaAuthService.deleteByUserIds(new String[]{userId});
		officeTypeAuthDao.saveBach(typelist);
		officeTeachAreaAuthService.saveBach(arealist);
	}

	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void deleteAuthByUser(String[] userIds) {
		officeTypeAuthDao.deleteByUserIds(userIds);
		officeTeachAreaAuthService.deleteByUserIds(userIds);
	}
}
