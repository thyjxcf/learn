package net.zdsoft.office.remote;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.StudentFamilyService;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.frame.action.BaseH5Action;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.office.health.constant.HealthConstants;
import net.zdsoft.office.health.entity.OfficeHealthCount;
import net.zdsoft.office.health.service.OfficeHealthCountService;
import net.zdsoft.remote.utils.WeiKeyUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;


public class RemoteHealthAction extends BaseH5Action{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OfficeHealthCountService officeHealthCountService;
	private StudentFamilyService studentFamilyService;
	private StudentService studentService;
	private UserService userService;
	
	private String studentId;
	private String dateType;//日 周 月
	private String queryDateStr;//查询的日期
	private String ownerId;
	private Integer addOrLess;
	// 微课自动加上的参数
	private String token;
	private String appId;
	
	public String getIndexFtl(){
		if (StringUtils.isBlank(token)) {
			promptMessageDto.setErrorMessage("token为空！");
			return NONE;
		}
		try {
			String familyId = WeiKeyUtils.decodeByDes(token);// base_user.owner_id
			Family family=studentFamilyService.getFamily(familyId);
			if (family == null) {
				promptMessageDto.setErrorMessage("找不到相关用户信息，请联系管理员！");
				return NONE;
			}
			ownerId = familyId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public void getDetail(){
		JSONObject json = new JSONObject();
		
		if(StringUtils.isBlank(studentId) && StringUtils.isBlank(ownerId)){
			returnErrorMsg("参数错误");
			return;
		}	
		//第一次进来的时候 是通过ownerId获取该用户下的所有学生
		if(StringUtils.isNotBlank(ownerId)){
			Set<String> owernIds=getAllOIdsByOwnerId(ownerId);
			List<Family> familyList=studentFamilyService.getFamilies(owernIds.toArray(new String[0]));
			if(CollectionUtils.isEmpty(familyList)){
				returnErrorMsg("没有家庭信息");
				return;
			}
			Set<String> studentIds=new HashSet<String>();
			for(Family family:familyList){
				studentIds.add(family.getStudentId());
			}
			List<Student> studentList=studentService.getStudentsByIds(studentIds.toArray(new String[0]));
			if(CollectionUtils.isEmpty(studentList)){
				returnErrorMsg("没有学生信息");
				return;
			}
			if(StringUtils.isBlank(studentId)){
				studentId=studentList.get(0).getId();
			}
			
			JSONArray jsonArrayStu=new JSONArray();
			for(Student student:studentList){
				JSONObject jsonStu=new JSONObject();
				jsonStu.put("studentId", student.getId());
				jsonStu.put("studentName", student.getStuname());
				jsonArrayStu.add(jsonStu);
			}
			json.put("studentList",jsonArrayStu);
		}
		json.put("studentId", studentId);
		Student student=studentService.getStudent(studentId);
		if(student==null){
			returnErrorMsg("没有学生信息");
			return;
		}
		//处理数据
		if(StringUtils.isBlank(dateType)){
			dateType=HealthConstants.HEALTH_DATE_DAY;
		}
		Date queryDate=null;
		if(dateType.equals(HealthConstants.HEALTH_DATE_DAY) && StringUtils.isNotBlank(queryDateStr)){
			queryDate=DateUtils.addDay(DateUtils.string2Date(queryDateStr, "yyyy-MM-dd"), addOrLess);
		}
		
		List<OfficeHealthCount> healthList=officeHealthCountService.getOfficeHealthCountByStudentId(studentId, dateType,queryDate);
		String nowDayStr=DateUtils.date2String(new Date(),"yyyy-MM-dd");
		if(StringUtils.isBlank(queryDateStr) || nowDayStr.equals(DateUtils.date2String(queryDate,"yyyy-MM-dd"))){
			json.put("queryDateStr", nowDayStr);
			json.put("showDate", "今天");
		}else{
			json.put("queryDateStr", DateUtils.date2String(queryDate,"yyyy-MM-dd"));
			json.put("showDate", DateUtils.date2String(queryDate,"MM-dd"));
		}
		//获取排名
		int stuRank=officeHealthCountService.getRankByStudentId(student,queryDate);
		json.put("stuRank", stuRank);
		
		if(CollectionUtils.isNotEmpty(healthList)){
			//当天 日
			if(dateType.equals(HealthConstants.HEALTH_DATE_DAY)){
				//取出当天 一天的信息
				Iterator<OfficeHealthCount> it=healthList.iterator();
				while(it.hasNext()){
					OfficeHealthCount health=it.next();
					if(health.getType().equals(HealthConstants.HEALTH_TYPE_DAY)){
						health.setDistance(formatDouble2(health.getDistance()/1000,1));
						health.setCalorie(formatDouble2(health.getCalorie()/1000,0));
						json.put("health", health);
						it.remove();
					}
				}
			}//周或月
			else{//已升序排序的list 
				OfficeHealthCount health=new OfficeHealthCount();
				json.put("beforeDate",DateUtils.date2String(healthList.get(0).getDate(),"MM/dd"));
				json.put("nowDate",DateUtils.date2String(healthList.get(healthList.size()-1).getDate(),"MM/dd"));
				
				int step=0;
				Double distance=0.0;
				Double calorie=0.0;
				//计算总的数据
				for(OfficeHealthCount officeHealthCount:healthList){
					step+=officeHealthCount.getStep();
					distance+=officeHealthCount.getDistance();
					calorie+=officeHealthCount.getCalorie();
				}
				health.setStep(step);
				health.setDistance(formatDouble2(distance/1000,1));
				health.setCalorie(formatDouble2(calorie/1000,0));
				json.put("health", health);
			}
			JSONArray jsonArrayHea=new JSONArray();
			for(OfficeHealthCount health:healthList){
				JSONObject jsonHealth=new JSONObject();
				jsonHealth.put("dateStr", health.getDateStr());
				jsonHealth.put("hour", health.getHour());
				jsonHealth.put("step", health.getStep());
				jsonHealth.put("distance",health.getDistance());
				jsonHealth.put("calorie", health.getCalorie());
				jsonArrayHea.add(jsonHealth);
			}
			json.put("healthList", jsonArrayHea);
		}
		setDetailObject(json);
		returnSuccessMsg("ok");
		return;
	}
	
	public void getClassRank(){
		if(StringUtils.isBlank(studentId)){
			returnErrorMsg("参数错误");
			return;
		}
		JSONObject json = new JSONObject();
		
		Student student=studentService.getStudent(studentId);
		//获取该班级下的所有学生ids
		List<Student> studentList=studentService.getStudents(student!=null?student.getClassid():"");
		Set<String> studentIds=new HashSet<String>();
		
		//获取该班级下的学生 key-studentId  value-Student
		Map<String,Student> allStuMap=new HashMap<String, Student>();
		if(CollectionUtils.isEmpty(studentList)){
			returnErrorMsg("学生信息有误");
			return;
		}else{
			for(Student stu:studentList){
				studentIds.add(stu.getId());
				allStuMap.put(stu.getId(), stu);
			}
		}
		//所有学生的数据
		List<OfficeHealthCount> healthList=officeHealthCountService.getStudentsByStudentId(studentIds.toArray(new String[0]), queryDateStr);
		if(CollectionUtils.isNotEmpty(healthList)){
			
			JSONArray jsonArrayMy=new JSONArray();
			JSONArray jsonArrayAll=new JSONArray();
			for(OfficeHealthCount health:healthList){
				//取自己孩子的信息
				if(health.getStudentId().equals(studentId)){
					JSONObject  myStuJson=new JSONObject();
					myStuJson.put("stuRank",health.getStuRank());
					myStuJson.put("studentName", student.getStuname());
					myStuJson.put("step", health.getStep());
					myStuJson.put("distance", formatDouble2(health.getDistance()/1000,1));
					myStuJson.put("photoUrl", health.getPhotoUrl());
					jsonArrayMy.add(myStuJson);
				}
				//班级下所有学生
				Student allStu=allStuMap.get(health.getStudentId());
				if(allStu!=null){
					JSONObject  allStuJson=new JSONObject();
					allStuJson.put("stuRank",health.getStuRank());
					allStuJson.put("studentName", allStu.getStuname());
					allStuJson.put("step", health.getStep());
					allStuJson.put("distance", formatDouble2(health.getDistance()/1000,1));
					allStuJson.put("photoUrl", health.getPhotoUrl());
					jsonArrayAll.add(allStuJson);
				}
			}
			json.put("myStuHealthList", jsonArrayMy);
			json.put("allStuHealthList", jsonArrayAll);
		}
		setDetailObject(json);
		returnSuccessMsg("ok");
		return;
	}
	/**
	 * 获取相同手机号的家长
	 * @param  ownerId
	 * @return
	 */
	public Set<String> getAllOIdsByOwnerId(String ownerId){
		Set<String> owernIds=new HashSet<String>();
		
		List<User> myUserList=userService.getUsersByOwner(ownerId);
		if(CollectionUtils.isNotEmpty(myUserList)){
			User user=myUserList.get(0);
			List<User> us = userService.getUserByMobilePhone(
					user.getMobilePhone(),3);
			if(CollectionUtils.isNotEmpty(us)){
				String upwd = user.findClearPassword();
				if (StringUtils.isBlank(upwd)) {
					upwd = "";
				}
				for (User u1 : us) {
					String password = null;
					/** password城域库中密码, pwd为用户输入密码 * */
					password = u1.findClearPassword();
					if ("".equals(password)) {
						password = null;
					}
					if ((password == null && (StringUtils.isBlank(user.getPassword())))
							|| upwd.equals(password)) {
						owernIds.add(u1.getTeacherid());
					}
				}
			}else{
				owernIds.add(user.getTeacherid());
			}
		}
		return owernIds;
	}
	/**
	 * 四舍五入
	 * @param d
	 * @param scale 保留几位小数
	 * @return
	 */
	public double formatDouble2(double d, int scale) {
        // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
        BigDecimal bg = new BigDecimal(d).setScale(scale, BigDecimal.ROUND_HALF_UP);
        return bg.doubleValue();
    }

	public void setOfficeHealthCountService(
			OfficeHealthCountService officeHealthCountService) {
		this.officeHealthCountService = officeHealthCountService;
	}
	public void setStudentFamilyService(StudentFamilyService studentFamilyService) {
		this.studentFamilyService = studentFamilyService;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public String getQueryDateStr() {
		return queryDateStr;
	}

	public void setQueryDateStr(String queryDateStr) {
		this.queryDateStr = queryDateStr;
	}


	public Integer getAddOrLess() {
		return addOrLess;
	}

	public void setAddOrLess(Integer addOrLess) {
		this.addOrLess = addOrLess;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
