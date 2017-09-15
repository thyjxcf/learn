package net.zdsoft.office.repaire.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.repaire.dao.OfficeRepaireTypeDao;
import net.zdsoft.office.repaire.entity.OfficeRepaireType;
import net.zdsoft.office.repaire.service.OfficeRepaireTypeService;

import org.apache.commons.lang.StringUtils;
/**
 * office_repaire_type
 * @author 
 * 
 */
public class OfficeRepaireTypeServiceImpl implements OfficeRepaireTypeService{
	
	private UserService userService;
	
	private OfficeRepaireTypeDao officeRepaireTypeDao;

	@Override
	public OfficeRepaireType save(OfficeRepaireType officeRepaireType){
		return officeRepaireTypeDao.save(officeRepaireType);
	}

	@Override
	public Integer delete(String id){
		return officeRepaireTypeDao.delete(id);
	}

	@Override
	public Integer update(OfficeRepaireType officeRepaireType){
		return officeRepaireTypeDao.update(officeRepaireType);
	}

	@Override
	public OfficeRepaireType getOfficeRepaireTypeById(String id){
		OfficeRepaireType officeRepaireType = officeRepaireTypeDao.getOfficeRepaireTypeById(id);
		if(officeRepaireType!=null){
			String[] userIds = officeRepaireType.getUserIds().split(",");
			Map<String, User> userMap = userService.getUserWithDelMap(userIds);
			setUserNames(officeRepaireType, userMap);
		}
		return officeRepaireType;
	}
	
	public void setUserNames(OfficeRepaireType officeRepaireType, Map<String, User> userMap){
		if(StringUtils.isNotEmpty(officeRepaireType.getUserIds())){
			String[] userIds = officeRepaireType.getUserIds().split(",");
			StringBuffer sbf = new StringBuffer();
			for(int i=0;i<userIds.length;i++){
				if(i == 0){
					if(userMap.get(userIds[i])!=null){
						sbf.append(userMap.get(userIds[i]).getRealname());
					}
				}else{
					if(userMap.get(userIds[i])!=null){
						sbf.append(",").append(userMap.get(userIds[i]).getRealname());
					}
				}
			}
			officeRepaireType.setUserNames(sbf.toString());
		}
	}

	@Override
	public Map<String, OfficeRepaireType> getOfficeRepaireTypeMapByIds(String[] ids){
		return officeRepaireTypeDao.getOfficeRepaireTypeMapByIds(ids);
	}
	
	@Override
	public Map<String, String> getOfficeRepaireTypeMapByUnitId(String unitId) {
		return officeRepaireTypeDao.getOfficeRepaireTypeMapByUnitId(unitId);
	}

	@Override
	public List<OfficeRepaireType> getOfficeRepaireTypeList(){
		return officeRepaireTypeDao.getOfficeRepaireTypeList();
	}

	@Override
	public List<OfficeRepaireType> getOfficeRepaireTypePage(Pagination page){
		return officeRepaireTypeDao.getOfficeRepaireTypePage(page);
	}

	@Override
	public List<OfficeRepaireType> getOfficeRepaireTypeByUnitIdList(String unitId, String thisId){
		List<OfficeRepaireType> officeRepaireTypes = officeRepaireTypeDao.getOfficeRepaireTypeByUnitIdList(unitId, thisId);
		Set<String> ids = new HashSet<String>();
		for(OfficeRepaireType officeRepaireType:officeRepaireTypes){
			if(StringUtils.isNotEmpty(officeRepaireType.getUserIds())){
				String[] userIds = officeRepaireType.getUserIds().split(",");
				for(int i=0;i<userIds.length;i++){
					ids.add(userIds[i]);
				}
			}
		}
		Map<String, User> userMap = userService.getUserWithDelMap(ids.toArray(new String[]{}));
		for(OfficeRepaireType officeRepaireType:officeRepaireTypes){
			setUserNames(officeRepaireType, userMap);
		}
		return officeRepaireTypes;
	}
	
	@Override
	public List<OfficeRepaireType> getOfficeRepaireTypeByUnitIdList(String unitId){
		List<OfficeRepaireType> officeRepaireTypes = officeRepaireTypeDao.getOfficeRepaireTypeByUnitIdList(unitId);
		return officeRepaireTypes;
	}

	@Override
	public List<OfficeRepaireType> getOfficeRepaireTypeByUnitIdPage(String unitId, Pagination page){
		return officeRepaireTypeDao.getOfficeRepaireTypeByUnitIdPage(unitId, page);
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setOfficeRepaireTypeDao(OfficeRepaireTypeDao officeRepaireTypeDao){
		this.officeRepaireTypeDao = officeRepaireTypeDao;
	}
}	