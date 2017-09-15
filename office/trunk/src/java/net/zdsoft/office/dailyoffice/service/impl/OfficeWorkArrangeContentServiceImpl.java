package net.zdsoft.office.dailyoffice.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeWorkArrangeContentDao;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkArrangeContent;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkArrangeOutline;
import net.zdsoft.office.dailyoffice.service.OfficeWorkArrangeContentService;
import net.zdsoft.office.dailyoffice.service.OfficeWorkArrangeOutlineService;
/**
 * office_work_arrange_content
 * @author 
 * 
 */
public class OfficeWorkArrangeContentServiceImpl implements OfficeWorkArrangeContentService{
	
	private DeptService deptService;
	private OfficeWorkArrangeOutlineService officeWorkArrangeOutlineService;
	
	private OfficeWorkArrangeContentDao officeWorkArrangeContentDao;

	@Override
	public void batchSave(List<OfficeWorkArrangeContent> owacList) {
		officeWorkArrangeContentDao.batchSave(owacList);
	}
	
	@Override
	public Integer delete(String[] ids){
		return officeWorkArrangeContentDao.delete(ids);
	}
	
	@Override
	public void deleteByDetailId(String detailId) {
		officeWorkArrangeContentDao.deleteByDetailId(detailId);
	}

	@Override
	public Integer update(OfficeWorkArrangeContent officeWorkArrangeContent){
		return officeWorkArrangeContentDao.update(officeWorkArrangeContent);
	}
	
	@Override
	public void updateStateByOutLineId(String outLineId, String state) {
		officeWorkArrangeContentDao.updateStateByOutLineId(outLineId, state);
	}

	@Override
	public OfficeWorkArrangeContent getOfficeWorkArrangeContentById(String id){
		OfficeWorkArrangeContent officeWorkArrangeContent = officeWorkArrangeContentDao.getOfficeWorkArrangeContentById(id);
		StringBuffer deptNames = new StringBuffer();
		if(StringUtils.isNotBlank(officeWorkArrangeContent.getDeptIds())){
			String[] deptIds = officeWorkArrangeContent.getDeptIds().split(",");
			Map<String, Dept> deptMap = deptService.getDeptMap(deptIds);
			for(int i = 0; i < deptIds.length;i++){
				if(deptMap.containsKey(deptIds[i])){
					if(i==0){
						deptNames.append(deptMap.get(deptIds[i]).getDeptname());
					}else{
						deptNames.append(",").append(deptMap.get(deptIds[i]).getDeptname());
					}
				}
			}
			officeWorkArrangeContent.setDeptNames(deptNames.toString());
		}
		return officeWorkArrangeContent;
	}

	@Override
	public Map<String, OfficeWorkArrangeContent> getOfficeWorkArrangeContentMapByIds(String[] ids){
		return officeWorkArrangeContentDao.getOfficeWorkArrangeContentMapByIds(ids);
	}

	@Override
	public List<OfficeWorkArrangeContent> getOfficeWorkArrangeContentListByDetailId(String detailId, String unitId){
		List<OfficeWorkArrangeContent> officeWorkArrangeContents = officeWorkArrangeContentDao.getOfficeWorkArrangeContentListByDetailId(detailId);
		Map<String, Dept> deptMap = deptService.getDeptMap(unitId);
		for(OfficeWorkArrangeContent officeWorkArrangeContent:officeWorkArrangeContents){
			StringBuffer deptNames = new StringBuffer();
			if(StringUtils.isNotBlank(officeWorkArrangeContent.getDeptIds())){
				String[] deptIds = officeWorkArrangeContent.getDeptIds().split(",");
				for(int i = 0; i < deptIds.length;i++){
					if(deptMap.containsKey(deptIds[i])){
						if(i==0){
							deptNames.append(deptMap.get(deptIds[i]).getDeptname());
						}else{
							deptNames.append(",").append(deptMap.get(deptIds[i]).getDeptname());
						}
					}
				}
				officeWorkArrangeContent.setDeptNames(deptNames.toString());
			}
		}
		return officeWorkArrangeContents;
	}

	@Override
	public List<OfficeWorkArrangeContent> getOfficeWorkArrangeContentPage(Pagination page){
		return officeWorkArrangeContentDao.getOfficeWorkArrangeContentPage(page);
	}
	
	@Override
	public Map<Date, List<OfficeWorkArrangeContent>> getOfficeWorkArrangeContentsMapByOutLineId(
			String outLineId) {
		OfficeWorkArrangeOutline officeWorkArrangeOutline = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlineById(outLineId);
		List<OfficeWorkArrangeContent> officeWorkArrangeContents = officeWorkArrangeContentDao.getOfficeWorkArrangeContentListByOutLineId(outLineId, officeWorkArrangeOutline.getState());
		Map<Date, List<OfficeWorkArrangeContent>> owacMap = new LinkedHashMap<Date, List<OfficeWorkArrangeContent>>();
		Map<String, Dept> deptMap = deptService.getDeptMap(officeWorkArrangeOutline.getUnitId());
		Date startTime = officeWorkArrangeOutline.getStartTime();
		Date endTime =  officeWorkArrangeOutline.getEndTime();
		
		Set<Date> dateSet=new TreeSet<Date>();
		for (OfficeWorkArrangeContent officeWorkArrangeContent : officeWorkArrangeContents) {
			dateSet.add(officeWorkArrangeContent.getWorkDate());
		}
		if(CollectionUtils.isNotEmpty(dateSet)){
			for (Date date : dateSet) {
				List<OfficeWorkArrangeContent> owacs = new ArrayList<OfficeWorkArrangeContent>();
				for (OfficeWorkArrangeContent officeWorkArrangeContent : officeWorkArrangeContents) {
					if(officeWorkArrangeContent.getWorkDate().compareTo(date)==0){
						StringBuffer deptNames=new StringBuffer();
						if(org.apache.commons.lang3.StringUtils.isNotBlank(officeWorkArrangeContent.getDeptIds())){
							String[] deptIds=officeWorkArrangeContent.getDeptIds().split(",");
							for (int i=0; i<deptIds.length; i++) {
								if(deptMap.containsKey(deptIds[i])){
									Dept dept=deptMap.get(deptIds[i]);
									if(dept!=null){
										if(i==0){
											deptNames.append(dept.getDeptname());
										}else{
											deptNames.append(",").append(dept.getDeptname());
										}
									}
								}
							}
							officeWorkArrangeContent.setDeptNames(deptNames.toString());
						}
						owacs.add(officeWorkArrangeContent);
					}
				}
				if(owacs.size() > 0){
					Collections.sort(owacs, new Comparator<OfficeWorkArrangeContent>() {
						@Override
						public int compare(OfficeWorkArrangeContent o1, OfficeWorkArrangeContent o2) {
							if(o1.getWorkDate().compareTo(o2.getWorkDate()) == 0){
								String t1 = StringUtils.isEmpty(o1.getWorkStartTime())?"0":o1.getWorkStartTime();
								String t2 = StringUtils.isEmpty(o2.getWorkStartTime())?"0":o2.getWorkStartTime();
								return t1.compareTo(t2);
							}
							return o1.getWorkDate().compareTo(o2.getWorkDate());
						}
					});
					owacMap.put(date, owacs);
				}
			}
		}
		/**while (startTime.compareTo(endTime) <= 0) {
			List<OfficeWorkArrangeContent> owacs = new ArrayList<OfficeWorkArrangeContent>();
			for(OfficeWorkArrangeContent officeWorkArrangeContent:officeWorkArrangeContents){
				if(officeWorkArrangeContent.getWorkDate().compareTo(startTime) == 0){
					StringBuffer deptNames = new StringBuffer();
					if(StringUtils.isNotBlank(officeWorkArrangeContent.getDeptIds())){
						String[] deptIds = officeWorkArrangeContent.getDeptIds().split(",");
						for(int i = 0; i < deptIds.length;i++){
							if(deptMap.containsKey(deptIds[i])){
								if(i==0){
									deptNames.append(deptMap.get(deptIds[i]).getDeptname());
								}else{
									deptNames.append(",").append(deptMap.get(deptIds[i]).getDeptname());
								}
							}
						}
						officeWorkArrangeContent.setDeptNames(deptNames.toString());
					}
					owacs.add(officeWorkArrangeContent);
				}
			}
			if(owacs.size() > 0){
				Collections.sort(owacs, new Comparator<OfficeWorkArrangeContent>() {
					@Override
					public int compare(OfficeWorkArrangeContent o1, OfficeWorkArrangeContent o2) {
						if(o1.getWorkDate().compareTo(o2.getWorkDate()) == 0){
							String t1 = StringUtils.isEmpty(o1.getWorkStartTime())?"0":o1.getWorkStartTime();
							String t2 = StringUtils.isEmpty(o2.getWorkStartTime())?"0":o2.getWorkStartTime();
							return t1.compareTo(t2);
						}
						return o1.getWorkDate().compareTo(o2.getWorkDate());
					}
				});
				owacMap.put(startTime, owacs);
			}
			startTime = DateUtils.addDay(startTime, 1);
		}**/
		return owacMap;
	}
	
	@Override
	public Map<Date, List<OfficeWorkArrangeContent>> getOfficeWorkArrangeContentsMapByOutLineId(
			String outLineId, String deptId) {
		OfficeWorkArrangeOutline officeWorkArrangeOutline = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlineById(outLineId);
		List<OfficeWorkArrangeContent> officeWorkArrangeContents = officeWorkArrangeContentDao.getOfficeWorkArrangeContentListByOutLineId(outLineId, deptId, officeWorkArrangeOutline.getState());
		Map<Date, List<OfficeWorkArrangeContent>> owacMap = new LinkedHashMap<Date, List<OfficeWorkArrangeContent>>();
		Map<String, Dept> deptMap = deptService.getDeptMap(officeWorkArrangeOutline.getUnitId());
		Date startTime = officeWorkArrangeOutline.getStartTime();
		Date endTime =  officeWorkArrangeOutline.getEndTime();
		while (startTime.compareTo(endTime) <= 0) {
			List<OfficeWorkArrangeContent> owacs = new ArrayList<OfficeWorkArrangeContent>();
			for(OfficeWorkArrangeContent officeWorkArrangeContent:officeWorkArrangeContents){
				if(officeWorkArrangeContent.getWorkDate().compareTo(startTime) == 0){
					StringBuffer deptNames = new StringBuffer();
					if(StringUtils.isNotBlank(officeWorkArrangeContent.getDeptIds())){
						String[] deptIds = officeWorkArrangeContent.getDeptIds().split(",");
						for(int i = 0; i < deptIds.length;i++){
							if(deptMap.containsKey(deptIds[i])){
								if(i==0){
									deptNames.append(deptMap.get(deptIds[i]).getDeptname());
								}else{
									deptNames.append(",").append(deptMap.get(deptIds[i]).getDeptname());
								}
							}
						}
						officeWorkArrangeContent.setDeptNames(deptNames.toString());
					}
					owacs.add(officeWorkArrangeContent);
				}
			}
			if(owacs.size() > 0){
				Collections.sort(owacs, new Comparator<OfficeWorkArrangeContent>() {
					@Override
					public int compare(OfficeWorkArrangeContent o1, OfficeWorkArrangeContent o2) {
						if(o1.getWorkDate().compareTo(o2.getWorkDate()) == 0){
							String t1 = StringUtils.isEmpty(o1.getWorkStartTime())?"0":o1.getWorkStartTime();
							String t2 = StringUtils.isEmpty(o2.getWorkStartTime())?"0":o2.getWorkStartTime();
							return t1.compareTo(t2);
						}
						return o1.getWorkDate().compareTo(o2.getWorkDate());
					}
				});
				owacMap.put(startTime, owacs);
			}
			startTime = DateUtils.addDay(startTime, 1);
		}
		return owacMap;
	}
	
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setOfficeWorkArrangeOutlineService(
			OfficeWorkArrangeOutlineService officeWorkArrangeOutlineService) {
		this.officeWorkArrangeOutlineService = officeWorkArrangeOutlineService;
	}

	public void setOfficeWorkArrangeContentDao(OfficeWorkArrangeContentDao officeWorkArrangeContentDao){
		this.officeWorkArrangeContentDao = officeWorkArrangeContentDao;
	}
}