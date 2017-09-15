package net.zdsoft.eis.base.data.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.zdsoft.eis.base.common.entity.SchoolSemester;
import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.impl.SchoolSemesterServiceImpl;
import net.zdsoft.eis.base.data.dao.BaseSchoolSemesterDao;
import net.zdsoft.eis.base.data.service.BaseClassService;
import net.zdsoft.eis.base.data.service.BaseDateInfoService;
import net.zdsoft.eis.base.data.service.BaseGradeService;
import net.zdsoft.eis.base.data.service.BaseSchoolSemesterService;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.keel.util.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yanb
 * 
 */
public class BaseSchoolSemesterServiceImpl extends SchoolSemesterServiceImpl
		implements BaseSchoolSemesterService {
	private final static Logger log = LoggerFactory.getLogger(BaseSchoolSemesterServiceImpl.class);

	private SemesterUtil semesterUtil;
	private BaseDateInfoService baseDateInfoService;
	private BaseSchoolSemesterDao baseSchoolSemesterDao;
	private BaseGradeService baseGradeService;
	private BaseClassService baseClassService;
	private SystemIniService systemIniService;
	
	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setBaseDateInfoService(BaseDateInfoService baseDateInfoService) {
		this.baseDateInfoService = baseDateInfoService;
	}

	public void setSemesterUtil(SemesterUtil semesterUtil) {
		this.semesterUtil = semesterUtil;
	}

	public void setBaseGradeService(BaseGradeService baseGradeService) {
		this.baseGradeService = baseGradeService;
	}

	public void setBaseClassService(BaseClassService baseClassService) {
        this.baseClassService = baseClassService;
    }

    public void setBaseSchoolSemesterDao(
			BaseSchoolSemesterDao baseSchoolSemesterDao) {
		this.baseSchoolSemesterDao = baseSchoolSemesterDao;
	}

	// ========================以上是set==============================

	public void insertSemester(SchoolSemester schoolSemester) {
		baseSchoolSemesterDao.insertSemester(schoolSemester);
	}

	public PromptMessageDto saveSemester(SchoolSemester dto) {
		PromptMessageDto msgDto = new PromptMessageDto();
		try {
			// 根据新增还是修改生成entity
			if (dto.getId() == null || "".equals(dto.getId())) {
				// 新增
				// 先验证是否存在相同记录
				boolean isSame = getSemester(dto.getSchid(), dto.getAcadyear(),
						dto.getSemester()) == null ? false : true;
				if (isSame) {
					msgDto.setOperateSuccess(false);
					msgDto.setPromptMessage("保存学年<u>" + dto.getAcadyear()
							+ "</u>的第<u>" + dto.getSemester()
							+ "</u>学期信息失败！原因：已存在相同的学年、学期记录！");
					log.warn("保存学年" + dto.getAcadyear() + "的第"
							+ dto.getSemester() + "学期信息失败！原因：已存在相同的学年、学期记录！");

					return msgDto;
				}

				// 再验证日期是否合法
				if (!checkSemesterDate(dto, msgDto)) {
					return msgDto;
				}

				baseSchoolSemesterDao.insertSemester(dto);
			} else {
				// 更新
				// 验证日期是否合法
				if (!checkSemesterDate(dto, msgDto)) {
					return msgDto;
				}

				baseSchoolSemesterDao.updateSemester(dto);
			}

			// 只有修改的就当前学年学期，才会进行修改课时数
			SchoolSemester schoolSemester = getCurrentAcadyear(dto.getSchid());
			if (schoolSemester != null) {
				if (schoolSemester.getAcadyear().equals(dto.getAcadyear())
						&& schoolSemester.getSemester().equals(
								dto.getSemester())) {
					// 修改课时数的时候，同时修改年级表中的课时数
					baseGradeService.updateGrade(dto.getSchid(), dto
							.getAmperiods(), dto.getPmperiods(), dto
							.getNightperiods());
				}
			}

			// 更新日期信息表
			SystemIni systemIni = systemIniService.getSystemIni("SUNDAY_WEEK_DAY");
			if(systemIni != null && "1".equals(systemIni.getNowValue())){
				baseDateInfoService.saveDateInfo(dto.getSchid(), dto.getAcadyear(),
						dto.getSemester(), dto.getSemesterbegin(), dto
								.getSemesterend(), true);
			}else{
				baseDateInfoService.saveDateInfo(dto.getSchid(), dto.getAcadyear(),
						dto.getSemester(), dto.getSemesterbegin(), dto
								.getSemesterend(), false);
			}
			

			msgDto.setOperateSuccess(true);
			msgDto.setPromptMessage("保存<u>" + dto.getAcadyear() + "</u>的第<u>"
					+ dto.getSemester() + "</u>学期信息成功！");
			log.info("保存" + dto.getAcadyear() + "的第" + dto.getSemester()
					+ "学期信息成功！");

		} catch (Exception ex) {
			msgDto.setOperateSuccess(false);
			msgDto.setPromptMessage("保存<u>" + dto.getAcadyear() + "</u>的第<u>"
					+ dto.getSemester() + "</u>学期信息失败！原因：系统错误！");

			log.error("保存" + dto.getAcadyear() + "的第" + dto.getSemester()
					+ "学期信息失败！原因：系统错误！", ex);
		}

		return msgDto;

	}

	// 检查日期是否合法
	private boolean checkSemesterDate(SchoolSemester dto,
			PromptMessageDto msgDto) {
		// 判断日期是否与学年名称相符
		List<String> notAccord = new ArrayList<String>();
		String dataStr = "";
		String acadyear = dto.getAcadyear();

		dataStr = DateUtils.date2StringByDay(dto.getWorkbegin());
		if (acadyear.indexOf(dataStr.substring(0, 4)) < 0) {
			notAccord.add("工作开始日期");
		}
		dataStr = DateUtils.date2StringByDay(dto.getSemesterbegin());
		if (acadyear.indexOf(dataStr.substring(0, 4)) < 0) {
			notAccord.add("学习开始日期");
		}
		dataStr = DateUtils.date2StringByDay(dto.getRegisterdate());
		if (acadyear.indexOf(dataStr.substring(0, 4)) < 0) {
			notAccord.add("注册日期");
		}
		dataStr = DateUtils.date2StringByDay(dto.getSemesterend());
		if (acadyear.indexOf(dataStr.substring(0, 4)) < 0) {
			notAccord.add("学习结束日期");
		}
		dataStr = DateUtils.date2StringByDay(dto.getWorkend());
		if (acadyear.indexOf(dataStr.substring(0, 4)) < 0) {
			notAccord.add("工作结束日期");
		}
		if (notAccord.size() > 0) {
			StringBuffer buf = new StringBuffer();
			String msg = "保存学年<u>" + dto.getAcadyear() + "</u>的第<u>"
					+ dto.getSemester() + "</u>学期信息失败！<br>原因，下面项的日期不在本学年内：<br>";
			buf.append(msg);
			for (Iterator<String> it = notAccord.iterator(); it.hasNext();) {
				buf.append(it.next() + "<br>");
			}
			buf.append("<br>请更正！");
			msgDto.setOperateSuccess(false);
			msgDto.setPromptMessage(buf.toString());

			return false;
		}

		// 判断日期是否有交叉
		boolean isCross = baseSchoolSemesterDao.isCrossDate(dto.getSchid(), dto
				.getAcadyear(), dto.getSemester(), dto.getWorkbegin(), dto
				.getWorkend(), dto.getSemesterbegin(), dto.getSemesterend());
		if (isCross) {
			String msg = "保存学年<u>" + dto.getAcadyear() + "</u>的第<u>"
					+ dto.getSemester()
					+ "</u>学期信息失败！原因：可能存在日期的交叉！<br>注意：学年学期中的日期开设不能与其他学年学期交错";

			msgDto.setOperateSuccess(false);
			msgDto.setPromptMessage(msg);

			return false;
		}

		// 判断开始日期是否小于结束日期
		boolean isLogic = semesterUtil.isLogicDate(dto.getWorkbegin(), dto
				.getWorkend(), dto.getSemesterbegin(), dto.getSemesterend());
		if (!isLogic) {
			String msg = "保存学年<u>"
					+ dto.getAcadyear()
					+ "</u>的第<u>"
					+ dto.getSemester()
					+ "</u>学期信息失败！原因：可能开始日期不合逻辑！<br>注意：<br>（1）学习开始日期要大于工作开始日期；<br>"
					+ "（2）学习结束日期要大于学习开始日期；<br>（3）工作结束日期要大于学习结束日期；<br>（4）工作结束日期要大于工作开始日期。";

			msgDto.setOperateSuccess(false);
			msgDto.setPromptMessage(msg);

			return false;
		}

		return true;
	}

	public PromptMessageDto deleteSemester(String[] semesterIds) {
		boolean flag = true;
		StringBuffer buf = new StringBuffer("");
		PromptMessageDto msgDto = new PromptMessageDto();

		for (int i = 0; i < semesterIds.length; i++) {
			SchoolSemester schoolSemester = getSemester(semesterIds[i]);
			if (schoolSemester != null) {

				if (baseClassService.isExistsClass(schoolSemester.getSchid(),
						schoolSemester.getAcadyear())) {
					flag = false;
					buf.append("<br><font color='#FF0000'>"
							+ schoolSemester.getAcadyear()
							+ "</font> 学年已经存在班级信息。<br>");
				}
			}
			if (flag) {
				// 软删除
				baseSchoolSemesterDao.deleteSemester(semesterIds[i]);
				// 再删除日期信息
				baseDateInfoService.deleteDateInfo(schoolSemester.getSchid(),
						schoolSemester.getAcadyear(), schoolSemester
								.getSemester());
			}
		}

		if (flag) {
			msgDto.setPromptMessage("删除提示：" + buf.toString() + "成功删除！");
		} else {
			msgDto.setPromptMessage("删除提示：" + buf.toString()
					+ "<br>以上学年学期未被删除！");
		}
		msgDto.setOperateSuccess(flag);

		return msgDto;
	}

	public SchoolSemester getMaxSemester(String schoolId) {
		return baseSchoolSemesterDao.getMaxSemester(schoolId);
	}

	public SchoolSemester getDefaultSemester(String schid) {
		// 先得到要添加的下一条记录的学年和学期
		SchoolSemester dto = semesterUtil.getNextSemester(schid);
		dto.setSchid(schid);
		String acadyear = dto.getAcadyear();
		String semester = dto.getSemester();
		//        Calendar c = new GregorianCalendar();// 新建日期对象
		//        int currentYear = c.get(Calendar.YEAR);// 获取年份
		// if (!StringUtils.contains(acadyear, String.valueOf(currentYear)))
		// return null;
		return getNewSemester(schid, acadyear, semester);
	}

	@SuppressWarnings("unchecked")
	public SchoolSemester getNewSemester(String schid, String acadyear,
			String semester) {
		SchoolSemester dto = semesterUtil.getNextSemester(schid);
		dto.setAcadyear(acadyear);
		dto.setSemester(semester);

		// 1、先取配置文件
		//		Map map = semesterUtil.getValueFromXml(acadyear, semester);
		//		if (map != null && map.size() > 0) {
		//			dto.setWorkbegin((Date) map.get("workbegin"));
		//			dto.setWorkend((Date) map.get("workend"));
		//			dto.setSemesterbegin((Date) map.get("semesterbegin"));
		//			dto.setSemesterend((Date) map.get("semesterend"));
		//			dto.setRegisterdate((Date) map.get("registerdate"));
		//
		//			dto.setEdudays(Short.parseShort(map.get("edudays").toString()));
		//			dto.setClasshour(Short.parseShort(map.get("classhour").toString()));
		//			dto.setAmperiods(Short.parseShort(map.get("amperiods").toString()));
		//			dto.setPmperiods(Short.parseShort(map.get("pmperiods").toString()));
		//			dto.setNightperiods(Short.parseShort(map.get("nightperiods")
		//					.toString()));
		//		}

		// 2、若上学期有，则再取上学期的
		//		String proAcadyear = semesterUtil.getPreAcadyear(acadyear);
		//		SchoolSemester proSem = getSemester(schid, proAcadyear, semester);
		//		if (proSem != null) {
		//			dto.setWorkbegin(semesterUtil
		//					.getNextYearDate(proSem.getWorkbegin()));
		//			dto.setWorkend(semesterUtil.getNextYearDate(proSem.getWorkend()));
		//			dto.setSemesterbegin(semesterUtil.getNextYearDate(proSem
		//					.getSemesterbegin()));
		//			dto.setSemesterend(semesterUtil.getNextYearDate(proSem
		//					.getSemesterend()));
		//			dto.setRegisterdate(semesterUtil.getNextYearDate(proSem
		//					.getRegisterdate()));
		//			dto.setEdudays(proSem.getEdudays());
		//			dto.setClasshour(proSem.getClasshour());
		//			dto.setAmperiods(proSem.getPmperiods());
		//			dto.setPmperiods(proSem.getPmperiods());
		//			dto.setNightperiods(proSem.getNightperiods());
		//		}

		//如果教育局有设置相应学年学期，则使用相应的设置，否则返回null
		Semester jwsem = semesterService.getSemester(dto.getAcadyear(), dto
				.getSemester());
		if (null != jwsem) {
			dto.setWorkbegin(jwsem.getWorkBegin());
			dto.setWorkend(jwsem.getWorkEnd());
			dto.setSemesterbegin(jwsem.getSemesterBegin());
			dto.setSemesterend(jwsem.getSemesterEnd());
			dto.setAmperiods(jwsem.getAmPeriods());
			dto.setPmperiods(jwsem.getPmPeriods());
			dto.setNightperiods(jwsem.getNightPeriods());
			dto.setEdudays(jwsem.getEduDays());
			dto.setRegisterdate(jwsem.getRegisterdate());
			dto.setClasshour(jwsem.getClasshour());
			return dto;
		} else {
			return null;
		}

	}

	public SchoolSemester getSemester(String semesterId) {
		return baseSchoolSemesterDao.getSemester(semesterId);
	}
}
