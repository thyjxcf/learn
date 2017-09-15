package net.zdsoft.office.convertflow.test.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.zip.DataFormatException;

import net.zdsoft.office.convertflow.test.entity.Person;
import net.zdsoft.office.convertflow.test.entity.Plan;
import net.zdsoft.office.convertflow.test.entity.Project;

public class ProjectModel {

	public static void main(String[] args) {
		ProjectModel p = new ProjectModel();
		p.getDateList();
	}
	//排班
	public void initData(){
		List<Person> persons = getPersonList();
		List<Project> projects = getProjectList();
		List<String> dates = getDateList();
		List<Plan> planlist = new ArrayList<Plan>();
		
	}
	
	//工作计划安排
	private List<Plan> getPlanList(List<Person> persons, List<Project> projects, List<String> dates, List<Plan> planlist){
		//每天需要的人次
//		int dayNeedPersons = 0;
//		for(Project p : projects){
//			dayNeedPersons += p.getNumber();
//		}
//		
//		//每个月需要的人次
//		int monthNeedPersons = 0;
//		monthNeedPersons = 31 * dayNeedPersons;
		
		for(String date : dates){
			int m = new Random().nextInt(persons.size());
		}
		
		return planlist;
	}
	
	//获取某月的所有日期
	private List<String> getDateList(){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DATE, 1);//当月的第一天
		
		List<String> list = new ArrayList<String>();
		int month = cal.get(Calendar.MONTH);
		int maxNum = cal.getActualMaximum(Calendar.DAY_OF_MONTH);//最大天数
		while (cal.get(Calendar.MONTH) == month) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
			list.add(df.format(cal.getTime()));
		}
		
		return list;
	}
	
	private List<Person> getPersonList(){
		List<Person> list = new ArrayList<Person>();
		
		for(int i = 0; i < 20; i++){
			Person p = new Person();
			p.setName("员工"+(i+1));
			list.add(p);
		}
		
		return list;
	}
	
	private List<Project> getProjectList(){
		List<Project> list = new ArrayList<Project>();
		
		Project p = new Project();
		p.setType(1);
		p.setTypeName("早班");
		p.setNumber(5);
		
		Project p1 = new Project();
		p1.setType(2);
		p1.setTypeName("晚班");
		p1.setNumber(10);
		
		Project p2 = new Project();
		p2.setType(3);
		p2.setTypeName("休息");
		p2.setNumber(5);
		
		list.add(p);
		list.add(p1);
			
		return list;
	}
	
}
