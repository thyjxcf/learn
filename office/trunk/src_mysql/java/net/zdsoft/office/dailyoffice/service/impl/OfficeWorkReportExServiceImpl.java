package net.zdsoft.office.dailyoffice.service.impl;import java.util.HashSet;import java.util.List;import java.util.Map;import java.util.Set;import org.apache.commons.lang.StringUtils;import net.zdsoft.eis.base.common.entity.User;import net.zdsoft.eis.base.common.service.UserService;import net.zdsoft.eis.base.common.service.UserSetService;import net.zdsoft.keel.util.Pagination;import net.zdsoft.office.dailyoffice.dao.OfficeWorkReportExDao;import net.zdsoft.office.dailyoffice.entity.OfficeWorkReportEx;import net.zdsoft.office.dailyoffice.service.OfficeWorkReportExService;/** * office_work_report_ex  * @author  *  */public class OfficeWorkReportExServiceImpl implements OfficeWorkReportExService{	private OfficeWorkReportExDao officeWorkReportExDao;	private UserService userService;	private UserSetService userSetService;		public void setUserSetService(UserSetService userSetService) {		this.userSetService = userSetService;	}	public void setUserService(UserService userService) {		this.userService = userService;	}	@Override	public OfficeWorkReportEx save(OfficeWorkReportEx officeWorkReportEx){		return officeWorkReportExDao.save(officeWorkReportEx);	}		@Override	public Integer delete(String[] ids){		return officeWorkReportExDao.delete(ids);	}		@Override	public Integer update(OfficeWorkReportEx officeWorkReportEx){		return officeWorkReportExDao.update(officeWorkReportEx);	}		@Override	public OfficeWorkReportEx getOfficeWorkReportExById(String id){		return officeWorkReportExDao.getOfficeWorkReportExById(id);	}		@Override	public Map<String, OfficeWorkReportEx> getOfficeWorkReportExMapByIds(String[] ids){		return officeWorkReportExDao.getOfficeWorkReportExMapByIds(ids);	}		@Override	public List<OfficeWorkReportEx> getOfficeWorkReportExList(){		return officeWorkReportExDao.getOfficeWorkReportExList();	}		@Override	public List<OfficeWorkReportEx> getOfficeWorkReportExPage(Pagination page){		return officeWorkReportExDao.getOfficeWorkReportExPage(page);	}	

	@Override
	public List<OfficeWorkReportEx> getOfficeWorkReportExByUnitIdList(String unitId){
		return officeWorkReportExDao.getOfficeWorkReportExByUnitIdList(unitId);
	}

	@Override
	public List<OfficeWorkReportEx> getOfficeWorkReportExByUnitIdPage(String unitId, Pagination page){
		return officeWorkReportExDao.getOfficeWorkReportExByUnitIdPage(unitId, page);
	}		public void setOfficeWorkReportExDao(OfficeWorkReportExDao officeWorkReportExDao){		this.officeWorkReportExDao = officeWorkReportExDao;	}	@Override	public List<OfficeWorkReportEx> getOfficeWorkReportExByrepotIdList(			String reportId) {		List<OfficeWorkReportEx> workReportExs = officeWorkReportExDao.getOfficeWorkReportExByrepotIdList(reportId);		Set<String> userIds = new HashSet<String>();		for(OfficeWorkReportEx ex:workReportExs){			userIds.add(ex.getUserId());		}		Map<String, User> userMap = userService.getUserWithDelMap(userIds.toArray(new String[0]));		Map<String, String> userPhotoMap = userSetService.getUserPhotoMap(userIds.toArray(new String[0]));		for(OfficeWorkReportEx ex:workReportExs){			if(userMap.containsKey(ex.getUserId())){				User user = userMap.get(ex.getUserId());				String path = userPhotoMap.get(ex.getUserId());				if(StringUtils.isBlank(path)){					path="";				}				if(user != null){					ex.setUserName(user.getRealname()+"=="+path);				}			}		}				return workReportExs;	}}

