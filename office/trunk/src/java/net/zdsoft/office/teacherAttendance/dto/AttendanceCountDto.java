package net.zdsoft.office.teacherAttendance.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.office.teacherAttendance.constant.AttendanceConstants;

public class AttendanceCountDto {
	
	/**迟到*/
	private List<AttendanceDto> list1;
	
	/**早退*/
	private List<AttendanceDto> list2;
	
	/**外勤*/
	private List<AttendanceDto> list3;
	
	/**缺卡*/
	private List<AttendanceDto> list4;
	
	/**旷工*/
	private List<AttendanceDto> list5;
	
	/**
	 * key1-8 分别对应 "出勤天数","正常打卡","外勤打卡","早退","缺卡","迟到","旷工","休息"
	 */
	private Map<String, AttendanceDetailCountDto> detailMap;
	
	

	public Map<String, AttendanceDetailCountDto> getDetailMap() {
		if(detailMap == null){
			detailMap = new LinkedHashMap<String, AttendanceDetailCountDto>();
			AttendanceDetailCountDto d1 = new AttendanceDetailCountDto();
			d1.setType(AttendanceConstants.ATTENDANCE_COUNT_TYPE_1);
			d1.setName("出勤天数");
			detailMap.put(AttendanceConstants.ATTENDANCE_COUNT_TYPE_1, d1);
			AttendanceDetailCountDto d2 = new AttendanceDetailCountDto();
			d2.setType(AttendanceConstants.ATTENDANCE_COUNT_TYPE_2);
			d2.setName("正常打卡");
			detailMap.put(AttendanceConstants.ATTENDANCE_COUNT_TYPE_2, d2);
			AttendanceDetailCountDto d3 = new AttendanceDetailCountDto();
			d3.setType(AttendanceConstants.ATTENDANCE_COUNT_TYPE_3);
			d3.setName("外勤打卡");
			detailMap.put(AttendanceConstants.ATTENDANCE_COUNT_TYPE_3, d3);
			AttendanceDetailCountDto d4 = new AttendanceDetailCountDto();
			d4.setType(AttendanceConstants.ATTENDANCE_COUNT_TYPE_4);
			d4.setName("早退");
			detailMap.put(AttendanceConstants.ATTENDANCE_COUNT_TYPE_4, d4);
			AttendanceDetailCountDto d5 = new AttendanceDetailCountDto();
			d5.setType(AttendanceConstants.ATTENDANCE_COUNT_TYPE_5);
			d5.setName("缺卡");
			detailMap.put(AttendanceConstants.ATTENDANCE_COUNT_TYPE_5, d5);
			AttendanceDetailCountDto d6 = new AttendanceDetailCountDto();
			d6.setType(AttendanceConstants.ATTENDANCE_COUNT_TYPE_6);
			d6.setName("迟到");
			detailMap.put(AttendanceConstants.ATTENDANCE_COUNT_TYPE_6, d6);
			AttendanceDetailCountDto d7 = new AttendanceDetailCountDto();
			d7.setType(AttendanceConstants.ATTENDANCE_COUNT_TYPE_7);
			d7.setName("旷工");
			detailMap.put(AttendanceConstants.ATTENDANCE_COUNT_TYPE_7, d7);
			AttendanceDetailCountDto d8 = new AttendanceDetailCountDto();
			d8.setType(AttendanceConstants.ATTENDANCE_COUNT_TYPE_8);
			d8.setName("休息");
			detailMap.put(AttendanceConstants.ATTENDANCE_COUNT_TYPE_8, d8);
		}
		return detailMap;
	}

	public void setDetailMap(Map<String, AttendanceDetailCountDto> detailMap) {
		this.detailMap = detailMap;
	}

	public List<AttendanceDto> getList1() {
		if(list1 == null){
			list1 = new ArrayList<AttendanceDto>();
		}
		return list1;
	}

	public void setList1(List<AttendanceDto> list1) {
		this.list1 = list1;
	}

	public List<AttendanceDto> getList2() {
		if(list2 == null){
			list2 = new ArrayList<AttendanceDto>();
		}
		return list2;
	}

	public void setList2(List<AttendanceDto> list2) {
		this.list2 = list2;
	}

	public List<AttendanceDto> getList3() {
		if(list3 == null){
			list3 = new ArrayList<AttendanceDto>();
		}
		return list3;
	}

	public void setList3(List<AttendanceDto> list3) {
		this.list3 = list3;
	}

	public List<AttendanceDto> getList4() {
		if(list4 == null){
			list4 = new ArrayList<AttendanceDto>();
		}
		return list4;
	}

	public void setList4(List<AttendanceDto> list4) {
		this.list4 = list4;
	}

	public List<AttendanceDto> getList5() {
		if(list5 == null){
			list5 = new ArrayList<AttendanceDto>();
		}
		return list5;
	}

	public void setList5(List<AttendanceDto> list5) {
		this.list5 = list5;
	}
	
}
