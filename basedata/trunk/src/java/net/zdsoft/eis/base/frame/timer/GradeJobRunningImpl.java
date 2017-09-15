package net.zdsoft.eis.base.frame.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.common.dao.UnitDao;
import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.subsystemcall.service.BaseDataSubsystemService;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.leadin.exception.BusinessErrorException;
import net.zdsoft.leadin.exception.OperationNotAllowedException;

public class GradeJobRunningImpl implements GradeJobRunning {

    private static final String STUSYS_AUTOMATIC_UPGRADE = "STUSYS.AUTOMATIC.UPGRADE";//自动升级
    private static final String STUSYS_AUTOMATIC_UPGRADE_START_TIME = "STUSYS.AUTOMATIC.UPGRADE.START.TIME";
    
    private UnitDao unitDao;
	private BaseDataSubsystemService baseDataSubsystemService;
    private SemesterService semesterService;
    private SystemIniService systemIniService;
	private String sysStartDate;
	private String nowDate;
	PromptMessageDto promptMessageDto = new PromptMessageDto();
	private Semester jwsemester = new Semester();
	
	
	@Override
	public void saveInitClass() throws Exception {
		System.out.println("-----------------提示：进入年级升级方法------------------");
		if (baseDataSubsystemService == null){
			return;
		}
		SystemIni systemini= systemIniService.getSystemIni(STUSYS_AUTOMATIC_UPGRADE);
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		sysStartDate = year+"-"+systemIniService.getValue(STUSYS_AUTOMATIC_UPGRADE_START_TIME);
		nowDate = ft.format(new Date());
		System.out.println("nowvalue==="+systemini.getNowValue()+";nowdate====="+nowDate+";sysStartDate===="+ft.format(ft.parse(sysStartDate)));
		if(!("Y".equals(systemini.getNowValue()) && nowDate.equals(ft.format(ft.parse(sysStartDate))))){
			System.out.println("-----------------提示：还未到年级升级时间------------------");
			return;
		}
		System.out.println("-----------------提示：定时年级升级开启------------------");
		CurrentSemester cursemester = semesterService.getRealCurrentSemester();
		if(cursemester == null){
			System.out.println("------------提示：教育局没有设置当前学年学期，为其创建两条学年学期！----------");
			//新增教育局当前学年学期
			jwsemester = baseDataSubsystemService.getDefaultSemester();
			if("1".equals(jwsemester.getSemester())){
				try {
					baseDataSubsystemService.saveSemester(jwsemester);
					System.out.println("------------提示：教育局新增第一学期！----------");
				} catch (BusinessErrorException e) {
					System.out.println("------------提示：教育局新增第一学期信息失败！原因：可能存在日期的交叉！----------");
					System.out.println("------------提示：定时年级升级结束----------");
					return;
				}
				try {
					//第二学期
					jwsemester = baseDataSubsystemService.getDefaultSemester();
					baseDataSubsystemService.saveSemester(jwsemester);
					System.out.println("------------提示：教育局新增第二学期！----------");
				} catch (BusinessErrorException e) {
					System.out.println("------------提示：教育局新增第二学期信息失败！原因：可能存在日期的交叉！----------");
					System.out.println("------------提示：定时年级升级结束----------");
					return;
				}
			}
			
			cursemester = semesterService.getRealCurrentSemester();
		}
		
		if(cursemester==null){
			System.out.println("------------提示：教育局没有当前学年学期----------");
			System.out.println("------------提示：定时年级升级结束----------");
			return;
		}else if(!cursemester.getSemester().equals("1")){
			System.out.println("------------提示：年级升级只能在第一学期操作----------");
			System.out.println("------------提示：定时年级升级结束----------");
			return;
		}
		//获取所有学校列表（包含幼儿园）
		String unionid = unitDao.getTopEdu().getUnionid();
//		unionid = "610722000042";
		List<Unit> unitList = unitDao.getUnitsList(unionid, 1, 2, 0);
		for(Unit unit : unitList){
			Thread.sleep(50);	
			//年级升级
			try {
				//添加参数控制年级升级的时候是否按照新的学年学期判断默认为false
				baseDataSubsystemService.initGrades(unit.getId(), cursemester.getAcadyear());
			} catch (OperationNotAllowedException e) {
				System.out.println("------------"+unit.getName()+" 学校信息基本信息不完整，初始化年级信息失败---------");
				continue;
			} catch (Exception e){ 
				e.printStackTrace();
				continue;
			}
			//班级升级
			//baseDataSubsystemService.saveClassWithJob(unit.getId(), cursemester.getAcadyear());
			
		}
		System.out.println("------------提示：定时年级升级结束----------");
	}


	public void setUnitDao(UnitDao unitDao) {
		this.unitDao = unitDao;
	}
	public String getSysStartDate() {
		return sysStartDate;
	}
	public void setSysStartDate(String sysStartDate) {
		this.sysStartDate = sysStartDate;
	}
	public String getNowDate() {
		return nowDate;
	}
	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}
	public PromptMessageDto getPromptMessageDto() {
		return promptMessageDto;
	}
	public void setPromptMessageDto(PromptMessageDto promptMessageDto) {
		this.promptMessageDto = promptMessageDto;
	}
	public Semester getJwsemester() {
		return jwsemester;
	}
	public void setJwsemester(Semester jwsemester) {
		this.jwsemester = jwsemester;
	}
	public void setBaseDataSubsystemService(
			BaseDataSubsystemService baseDataSubsystemService) {
		this.baseDataSubsystemService = baseDataSubsystemService;
	}
	public void setSemesterService(SemesterService semesterService) {
		this.semesterService = semesterService;
	}
	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}
	
	

}
