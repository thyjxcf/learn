package net.zdsoft.office.remote.service.impl;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.office.remote.service.RemoteTacherAttendanceService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	private static final String[] names = new String[]{"出勤天数","正常打卡","外勤打卡","早退","缺卡","迟到","旷工","休息"};
	private static final String[] nums = new String[]{"5天","12次","3次","0次","1次","1次","5天","4天"};
	private static final double[] points = new double[]{0.88,0.90,0.22,0.22,0.22,0.5,0.00,1.00};
	
	private static final String[] names1 = new String[]{"请假","外出","出差","集体外出"};
	private static final String[] nums1 = new String[]{"5天","12小时","3天","2天"};
	
	public static JSONArray getCountTest(){
		JSONArray a = new JSONArray();
		for(int i = 0; i < names.length; i++){
			JSONObject j = new JSONObject();
			j.put("type", (i+1));
			j.put("name", names[i]);
			j.put("numStr", (i+1));
			j.put("percentage", points[i]);//两位小数
			a.add(j);
		}
		return a;
	}
	
	public static JSONArray getCountTest1(){
		JSONArray a = new JSONArray();
		for(int i = 0; i < names1.length; i++){
			JSONObject j = new JSONObject();
			j.put("type", (i+1));
			j.put("name", names1[i]);
			j.put("numStr", nums1[i]);
			j.put("flag", 1);//0:没有请假记录，1：有请假记录
			a.add(j);
		}
		return a;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

//	static ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:conf/spring/*.xml");
	
	public static void main(String[] args) {
//		RemoteTacherAttendanceService service = (RemoteTacherAttendanceService) context.getBean("remoteTacherAttendanceService");
//		
//		service.attendanceDetail("");
		
		Long a = 1492682914727l;
		String s = DateUtils.date2String(new Date(), "yyyy-MM-dd HH:mm:ss");
		System.out.println(s);
	}
	
}
