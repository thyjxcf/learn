/** 
 
 * @author wangsn
 * @since 1.0
 * @version $Id: HolidayAction.java, v 1.0 2011-5-31 下午02:22:06 wangsn Exp $
 */
package net.zdsoft.eis.base.data.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.entity.DateInfo;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.data.dto.DateInfoDto;
import net.zdsoft.eis.base.data.service.BaseDateInfoService;
import net.zdsoft.eis.frame.action.BaseSemesterAction;
import net.zdsoft.eis.frame.dto.PromptMessageDto;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class HolidayAction extends BaseSemesterAction{
    private static final long serialVersionUID = -7642010989231220634L;
    
    private BaseDateInfoService baseDateInfoService;
    private SemesterService semesterService;
    private String listString;
	private List<DateInfo> dateInfoList = new ArrayList<DateInfo>();
	private DateInfo dateInfo;
	private List<String> acadyearList;
	private int week;
	private List<DateInfoDto> dtolist;
	
	public String monthSet(){
		acadyearList = semesterService.getAcadyears();
		if (CollectionUtils.isEmpty(acadyearList)) {
			promptMessageDto.setErrorMessage("学校还没有维护过学年学期信息！");
			return PROMPTMSG;
		}
	     CurrentSemester  currentSemester =  semesterService.getCurrentSemester();
	     if(StringUtils.isBlank(acadyear))
	       acadyear = currentSemester.getAcadyear();
	     if(StringUtils.isBlank(semester))
	        semester = currentSemester.getSemester();
		List<DateInfo> datelist = baseDateInfoService.getDateList(getUnitId(), acadyear, semester);
		dtolist = createDto(datelist);
		return SUCCESS;
	}
	
	private List<DateInfoDto> createDto(List<DateInfo> datelist) {
		List<DateInfoDto> dts = new ArrayList<DateInfoDto>();
		Map<Integer,DateInfoDto> dtomap = new HashMap<Integer, DateInfoDto>();
		Calendar cal = Calendar.getInstance();
		int m = 0;
		DateInfoDto dt;
		week = 0;
		int order = 1;
		int actMon = 0;// 自然月份
		int minMon = 0;// 最小设置月份
		int minActMon = 0;// 最小自然月份
		Map<Integer,Integer> ordermap =new  HashMap<Integer, Integer>();	
		for(DateInfo e : datelist){
			if(e.getWeek() > week){
				week = e.getWeek();
			}
			cal.setTime(e.getDate());
			actMon = cal.get(Calendar.MONTH) + 1;
			if(minActMon == 0){
				minActMon = actMon;
			}
			if(e.getMonth() <= 0){
				m = actMon;
			}else{
				m = e.getMonth();
			}
			if(minMon == 0){
				minMon = m;
			}
			if(!ordermap.containsKey(cal.get(Calendar.MONTH) + 1)){
				ordermap.put(cal.get(Calendar.MONTH) + 1, order);
				order++;
			}
			if(dtomap.containsKey(m)){
				dt =dtomap.get(m); 
				if(e.getWeek() < dt.getBeginWeek()){
					dt.setBeginWeek(e.getWeek());
				}
				if(e.getWeek() > dt.getEndWeek()){
					dt.setEndWeek(e.getWeek());
				}
				dtomap.put(m, dt);
			}else{
				dt = new DateInfoDto();
				dt.setMonth(m);
				dt.setBeginWeek(e.getWeek());
				dt.setEndWeek(e.getWeek());
				dtomap.put(m, dt);
			}
		}
		// 最小自然月份比最小设置月份小，如果设置中不包括该自然月份 则此处添加上，方便用户设置
		if(minActMon < minMon && !dtomap.containsKey(minActMon)){
			dt = new DateInfoDto();
			dt.setMonth(minActMon);
			dtomap.put(minActMon, dt);
		}
		// 最大自然月份处理同上
		if(actMon > m && !dtomap.containsKey(actMon)){
			dt = new DateInfoDto();
			dt.setMonth(actMon);
			dtomap.put(actMon, dt);
		}
		for(DateInfoDto d : dtomap.values()){
			d.setOrder(ordermap.get(d.getMonth()));
			dts.add(d);
		}
		Collections.sort(dts, new Comparator<DateInfoDto>() {

			@Override
			public int compare(DateInfoDto o1, DateInfoDto o2) {
				if(o1.getOrder() > o2.getOrder()){
					return 1;
				}else{
					return -1;
				}
			}
		});
		return dts;
	}
	public String save(){
		try {
			if(CollectionUtils.isNotEmpty(dtolist)){
				if(!weekpd(dtolist)){
					jsonError = "周数不能有重复或者交叉！";
					return SUCCESS;
				}
				List<DateInfo> datelist = baseDateInfoService.getDateList(getUnitId(), acadyear, semester);
				upMonth(datelist,dtolist);
				baseDateInfoService.updateDateList1(datelist);
			}else{
				jsonError = "没有数据！";
				return SUCCESS;
			}
		} catch (Exception e) {
			log.debug(e.getMessage(),e);
			jsonError = "保存失败！";
			return SUCCESS;
		}
	//	jsonError="月周关系保存成功！";
		return SUCCESS;
	}

	private boolean weekpd(List<DateInfoDto> dtolist2) {
		boolean fag = true;
		for(DateInfoDto dt : dtolist2){
			if(dt.getEndWeek() < dt.getBeginWeek()){
				fag = false;
				break;
			}
		}
		if(fag){
			for(int i=0;i<dtolist2.size() && fag;i++){
				DateInfoDto d1 = dtolist2.get(i);
				if(d1.getBeginWeek() == 0 && d1.getEndWeek() == 0){
					continue;
				}
				for(int j = i+1;j<dtolist2.size();j++){
					DateInfoDto d2 = dtolist2.get(j);
					if(d2.getBeginWeek() == 0 && d2.getEndWeek() == 0){
						continue;
					}
					if(d1.getEndWeek() >= d2.getEndWeek()){
						if(d1.getBeginWeek()<=d2.getEndWeek()){
							fag = false;
							break;
						}
					}else{
						if(d2.getBeginWeek()<=d1.getEndWeek()){
							fag = false;
							break;
						}
					}
				}
			}
		}
		return fag;
	}

	private void upMonth(List<DateInfo> datelist, List<DateInfoDto> dtolist2) {
		boolean fag = false;
		for(DateInfo info : datelist){
			fag = false;
			for(DateInfoDto dto : dtolist2){
				if(dto.getBeginWeek() == 0 && dto.getEndWeek() == 0){
					continue;
				}
				if(info.getWeek() <= dto.getEndWeek() && info.getWeek() >= dto.getBeginWeek()){
					info.setMonth(dto.getMonth());
					fag =true;
					break;
				}
			}
			if(!fag){
				info.setMonth(-1);
			}
		}
	}

	public String showPage() {
        String schid = this.getLoginInfo().getUnitID();
        if(Unit.UNIT_CLASS_EDU==getLoginInfo().getUnitClass()){
        	//教育局
        	dateInfoList = baseDateInfoService.getEduDateList(schid);
        }else{
        	//学校
        	 CurrentSemester  currentSemester =  semesterService.getCurrentSemester();
             if (currentSemester == null ) {
             	promptMessageDto = new PromptMessageDto();
             	promptMessageDto.setErrorMessage("还没有设置当前学期, 请先设置当前学期再做此操作");
             	return PROMPTMSG;
             }
             String acadyear = currentSemester.getAcadyear();
             String semester = currentSemester.getSemester();
             dateInfoList = baseDateInfoService.getDateList(schid, acadyear,semester);
        }
       
        return SUCCESS;
    }

    public String updateDateInfo() {
		try {
			JSONArray obj = JSONArray.fromObject(listString);
			@SuppressWarnings("unchecked")
			Collection<DateInfo> list = JSONArray.toCollection(obj, DateInfo.class);
			dateInfoList.addAll(list);
			if(CollectionUtils.isNotEmpty(dateInfoList)){
				baseDateInfoService.updateDateList(dateInfoList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setErrorMessage(e.getMessage());
			return SUCCESS;
		}
		//reply.addActionMessage("节假日信息保存成功!");
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.setPromptMessage("节假日信息保存成功!");
		return SUCCESS;
	}
    
    public boolean isEduUnit(){
    	if(Unit.UNIT_CLASS_EDU==getLoginInfo().getUnitClass()){
    		return true;
    	}
    	return false;
    }

	
	public void setBaseDateInfoService(BaseDateInfoService baseDateInfoService) {
		this.baseDateInfoService = baseDateInfoService;
	}

    public void setSemesterService(SemesterService semesterService) {
        this.semesterService=semesterService;
    }

    public void setDateInfoList(List<DateInfo> dateInfoList) {
        this.dateInfoList=dateInfoList;
    }
    public List<DateInfo> getDateInfoList() {
        return this.dateInfoList;
    }
    
    public String getListString() {
		return listString;
	}
	public void setListString(String listString) {
		this.listString = listString;
	}
	
	 public DateInfo getDateInfo() {
			return dateInfo;
	}

	public void setDateInfo(DateInfo dateInfo) {
		this.dateInfo = dateInfo;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public List<DateInfoDto> getDtolist() {
		return dtolist;
	}

	public void setDtolist(List<DateInfoDto> dtolist) {
		this.dtolist = dtolist;
	}

	public List<String> getAcadyearList() {
		return acadyearList;
	}
}
