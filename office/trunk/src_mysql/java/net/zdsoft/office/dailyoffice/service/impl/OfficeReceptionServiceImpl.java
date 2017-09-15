package net.zdsoft.office.dailyoffice.service.impl;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeReceptionDao;
import net.zdsoft.office.dailyoffice.entity.OfficeReception;
import net.zdsoft.office.dailyoffice.service.OfficeReceptionService;
/**
 * office_reception
 * @author 
 * 
 */
public class OfficeReceptionServiceImpl implements OfficeReceptionService{
	
	private UserService userService;
	
	private OfficeReceptionDao officeReceptionDao;

	@Override
	public OfficeReception save(OfficeReception officeReception){
		return officeReceptionDao.save(officeReception);
	}

	@Override
	public Integer delete(String[] ids){
		return officeReceptionDao.delete(ids);
	}

	@Override
	public Integer update(OfficeReception officeReception){
		return officeReceptionDao.update(officeReception);
	}

	@Override
	public OfficeReception getOfficeReceptionById(String id){
		OfficeReception officeReception = officeReceptionDao.getOfficeReceptionById(id);
		Set<String> userIdSet = new HashSet<String>();
		userIdSet.add(officeReception.getStartWorkUserId());
		userIdSet.add(officeReception.getAccompanyPerson());
		userIdSet.add(officeReception.getCamemaPerson());
		userIdSet.add(officeReception.getReceptionUserId());
		Map<String, User> userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
		
		if(StringUtils.isNotBlank(officeReception.getStartWorkUserId())){
			if (userMap.containsKey(officeReception.getStartWorkUserId())) {
				User tt = userMap.get(officeReception.getStartWorkUserId());
				if (tt != null) {
					officeReception.setStartWorkUserName(tt.getRealname());
				}
			}else{
				officeReception.setStartWorkUserName("用户已删除");
			}
		}
		if(StringUtils.isNotBlank(officeReception.getAccompanyPerson())){
			if (userMap.containsKey(officeReception.getAccompanyPerson())) {
				User tt = userMap.get(officeReception.getAccompanyPerson());
				if (tt != null) {
					officeReception.setAccompanyPersonName(tt.getRealname());
				}
			}else{
				officeReception.setAccompanyPersonName("用户已删除");
			}
		}
		
		if(StringUtils.isNotBlank(officeReception.getCamemaPerson())){
			if (userMap.containsKey(officeReception.getCamemaPerson())) {
				User tt = userMap.get(officeReception.getCamemaPerson());
				if (tt != null) {
					officeReception.setCamemaPersonName(tt.getRealname());
				}
			}else{
				officeReception.setCamemaPersonName("用户已删除");
			}
		}
		if(StringUtils.isNotBlank(officeReception.getReceptionUserId())){
			if (userMap.containsKey(officeReception.getReceptionUserId())) {
				User tt = userMap.get(officeReception.getReceptionUserId());
				if (tt != null) {
					officeReception.setReceptionUserName(tt.getRealname());
				}
			}else{
				officeReception.setReceptionUserName("用户已删除");
			}
		}
		
		return officeReception;
	}

	@Override
	public List<OfficeReception> getOfficeReceptionByUnitId(String unitId) {
		return officeReceptionDao.getOfficeReceptionByUnitId(unitId);
	}

	@Override
	public List<OfficeReception> getOfficeReceptionByUnitIdWithPage(
			 Date startTime, Date endTime,
			String unitId, Pagination page) {
		
		List<OfficeReception> officeReceptions = officeReceptionDao.getOfficeReceptionByUnitIdWithPage(startTime,endTime,unitId,page);
		Set<String> userIdSet = new HashSet<String>();
		for (OfficeReception re : officeReceptions) {
			userIdSet.add(re.getStartWorkUserId());
			userIdSet.add(re.getAccompanyPerson());
			userIdSet.add(re.getCamemaPerson());
			userIdSet.add(re.getReceptionUserId());
		}
		Map<String, User> userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
		
		for (OfficeReception officeReception : officeReceptions) {
			if(StringUtils.isNotBlank(officeReception.getStartWorkUserId())){
				if (userMap.containsKey(officeReception.getStartWorkUserId())) {
					User tt = userMap.get(officeReception.getStartWorkUserId());
					if (tt != null) {
						officeReception.setStartWorkUserName(tt.getRealname());
					}
				}else{
					officeReception.setStartWorkUserName("用户已删除");
				}
			}
			if(StringUtils.isNotBlank(officeReception.getAccompanyPerson())){
				if (userMap.containsKey(officeReception.getAccompanyPerson())) {
					User tt = userMap.get(officeReception.getAccompanyPerson());
					if (tt != null) {
						officeReception.setAccompanyPersonName(tt.getRealname());
					}
				}else{
					officeReception.setAccompanyPersonName("用户已删除");
				}
			}
			
			if(StringUtils.isNotBlank(officeReception.getCamemaPerson())){
				if (userMap.containsKey(officeReception.getCamemaPerson())) {
					User tt = userMap.get(officeReception.getCamemaPerson());
					if (tt != null) {
						officeReception.setCamemaPersonName(tt.getRealname());
					}
				}else{
					officeReception.setCamemaPersonName("用户已删除");
				}
			}
			if(StringUtils.isNotBlank(officeReception.getReceptionUserId())){
				if (userMap.containsKey(officeReception.getReceptionUserId())) {
					User tt = userMap.get(officeReception.getReceptionUserId());
					if (tt != null) {
						officeReception.setReceptionUserName(tt.getRealname());
					}
				}else{
					officeReception.setReceptionUserName("用户已删除");
				}
			}
		}
		return officeReceptions;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setOfficeReceptionDao(OfficeReceptionDao officeReceptionDao){
		this.officeReceptionDao = officeReceptionDao;
	}
}