package net.zdsoft.office.dailyoffice.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ecs.xhtml.map;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeWorkReportTlDao;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkReport;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkReportTl;
import net.zdsoft.office.dailyoffice.service.OfficeWorkReportTlService;
/**
 * office_work_report_tl
 * @author 
 * 
 */
public class OfficeWorkReportTlServiceImpl implements OfficeWorkReportTlService{
	private OfficeWorkReportTlDao officeWorkReportTlDao;
	private UserService userService;
	private UnitService unitService;

	@Override
	public OfficeWorkReportTl save(OfficeWorkReportTl officeWorkReportTl){
		return officeWorkReportTlDao.save(officeWorkReportTl);
	}

	@Override
	public Integer delete(String[] ids){
		return officeWorkReportTlDao.delete(ids);
	}

	@Override
	public Integer update(OfficeWorkReportTl officeWorkReportTl){
		return officeWorkReportTlDao.update(officeWorkReportTl);
	}

	@Override
	public OfficeWorkReportTl getOfficeWorkReportTlById(String id){
		return officeWorkReportTlDao.getOfficeWorkReportTlById(id);
	}

	@Override
	public Map<String, OfficeWorkReportTl> getOfficeWorkReportTlMapByIds(String[] ids){
		return officeWorkReportTlDao.getOfficeWorkReportTlMapByIds(ids);
	}

	@Override
	public List<OfficeWorkReportTl> getOfficeWorkReportTlList(){
		return officeWorkReportTlDao.getOfficeWorkReportTlList();
	}

	@Override
	public List<OfficeWorkReportTl> getOfficeWorkReportTlPage(Pagination page){
		return officeWorkReportTlDao.getOfficeWorkReportTlPage(page);
	}

	@Override
	public List<OfficeWorkReportTl> getOfficeWorkReportTlByUnitIdList(String unitId){
		return officeWorkReportTlDao.getOfficeWorkReportTlByUnitIdList(unitId);
	}

	@Override
	public List<OfficeWorkReportTl> getOfficeWorkReportTlByUnitIdPage(String unitId, Pagination page){
		return officeWorkReportTlDao.getOfficeWorkReportTlByUnitIdPage(unitId, page);
	}

	public void setOfficeWorkReportTlDao(OfficeWorkReportTlDao officeWorkReportTlDao){
		this.officeWorkReportTlDao = officeWorkReportTlDao;
	}

	@Override
	public String findMaxWeek(String userId,String semester,String unitId,String states) {
		return officeWorkReportTlDao.findMaxWeek(userId,semester,unitId,states);
	}

	@Override
	public List<OfficeWorkReportTl> getOfficeWorkReportByUnitIdPageContent(
			String unit, Pagination page, String userId, String acadyear,
			String semester, String week, String contents, String states) {
		return officeWorkReportTlDao.getOfficeWorkReportByUnitIdPageContent(unit, page, userId, acadyear, semester, week, contents, states);
	}

	@Override
	public List<OfficeWorkReportTl> getOfficeWorkReportTlByUnitIdPageContentCreateUserName(
			Pagination page, String acadyear, String semester, String week,
			String contents, String createUserName) {
		List<OfficeWorkReportTl> list = officeWorkReportTlDao.getOfficeWorkReportTlByUnitIdPageContentCreateUserName(page, acadyear, semester, week, contents, createUserName);
		Set<String> userIds = new HashSet<String>();
		Set<String> unitIds = new HashSet<String>();
		if(CollectionUtils.isNotEmpty(list)){
		for(OfficeWorkReportTl ent : list){
			userIds.add(ent.getCreateUserId());
			unitIds.add(ent.getUnitId());
			}
		}
		Map<String, User> userMap = userService.getUserWithDelMap(userIds.toArray(new String[0]));
		Map<String,Unit> mapUnit=unitService.getUnitMap(unitIds.toArray(new String[0]));
		if(CollectionUtils.isNotEmpty(list)){
		for(OfficeWorkReportTl ent : list){
			if(userMap.containsKey(ent.getCreateUserId())){
				User user = userMap.get(ent.getCreateUserId());
				if(user != null){
					ent.setCreateUserName(user.getRealname());
				}
			}
			if(mapUnit.containsKey(ent.getUnitId())){
					Unit unit=mapUnit.get(ent.getUnitId());
					if(unit!=null){
						ent.setUnitName(unit.getName());
					}else{
						ent.setUnitName("单位已删除");
					}
				}
			}
		}
		return list;
	}

	@Override
	public List<OfficeWorkReportTl> getOfficeWorkReportTlByUnitId(
			String unitId,String userId, String acadyear, String semester, String week) {
		return officeWorkReportTlDao.getOfficeWorkReportTlByUnitId(unitId, userId,acadyear, semester, week);
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	
}
