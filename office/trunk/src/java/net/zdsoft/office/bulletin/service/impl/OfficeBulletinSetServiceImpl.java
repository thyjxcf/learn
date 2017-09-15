package net.zdsoft.office.bulletin.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.dao.OfficeBulletinSetDao;
import net.zdsoft.office.bulletin.entity.OfficeBulletinSet;
import net.zdsoft.office.bulletin.service.OfficeBulletinSetService;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeave;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeaveUser;
/**
 * office_bulletin_set
 * @author 
 * 
 */
public class OfficeBulletinSetServiceImpl implements OfficeBulletinSetService{
	private OfficeBulletinSetDao officeBulletinSetDao;
	private UserService userService;

	@Override
	public OfficeBulletinSet save(OfficeBulletinSet officeBulletinSet){
		return officeBulletinSetDao.save(officeBulletinSet);
	}

	@Override
	public Integer delete(String[] ids){
		return officeBulletinSetDao.delete(ids);
	}

	@Override
	public Integer update(OfficeBulletinSet officeBulletinSet){
		return officeBulletinSetDao.update(officeBulletinSet);
	}

	@Override
	public OfficeBulletinSet getOfficeBulletinSetById(String id){
		return officeBulletinSetDao.getOfficeBulletinSetById(id);
	}

	@Override
	public Map<String, OfficeBulletinSet> getOfficeBulletinSetMapByIds(String[] ids){
		return officeBulletinSetDao.getOfficeBulletinSetMapByIds(ids);
	}

	@Override
	public List<OfficeBulletinSet> getOfficeBulletinSetList(){
		return officeBulletinSetDao.getOfficeBulletinSetList();
	}

	@Override
	public List<OfficeBulletinSet> getOfficeBulletinSetPage(Pagination page){
		return officeBulletinSetDao.getOfficeBulletinSetPage(page);
	}

	@Override
	public List<OfficeBulletinSet> getOfficeBulletinSetByUnitIdList(String unitId,String roleCode){
		List<OfficeBulletinSet> officeBulletinSetList= officeBulletinSetDao.getOfficeBulletinSetByUnitIdList(unitId,roleCode);
		if(CollectionUtils.isNotEmpty(officeBulletinSetList)){
		for (OfficeBulletinSet officeBulletinSet : officeBulletinSetList) {
			Map<String, User> userMap = userService.getUserMap(unitId);
			StringBuffer sbfNames = new StringBuffer();
			int i = 0;
			String[] strs=StringUtils.split(officeBulletinSet.getAuditId(),",");
			if(strs!=null){
			for(String str: strs){
				if(userMap.containsKey(str)){
					if(i == 0){
						sbfNames.append(userMap.get(str).getRealname());
					}else{
						sbfNames.append(",").append(userMap.get(str).getRealname());
					}
					i++;
				}
			}
			}
			officeBulletinSet.setAuditNames(sbfNames.toString());
			}
		}
		return officeBulletinSetList;
	}

	@Override
	public List<OfficeBulletinSet> getOfficeBulletinSetByUnitIdPage(String unitId, Pagination page){
		return officeBulletinSetDao.getOfficeBulletinSetByUnitIdPage(unitId, page);
	}

	@Override
	public List<OfficeBulletinSet> getOfficeBulletinSetByUnitIdUserIdList(
			String unitId, String userId,String roleCode) {
		return officeBulletinSetDao.getOfficeBulletinSetByUnitIdUserIdList(unitId, userId,roleCode);
	}

	public void setOfficeBulletinSetDao(OfficeBulletinSetDao officeBulletinSetDao){
		this.officeBulletinSetDao = officeBulletinSetDao;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
