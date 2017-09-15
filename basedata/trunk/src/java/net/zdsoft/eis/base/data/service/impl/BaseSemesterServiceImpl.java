/**
 * 
 */
package net.zdsoft.eis.base.data.service.impl;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import net.zdsoft.eis.base.affair.AbstractTransact;
import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.impl.SemesterServiceImpl;
import net.zdsoft.eis.base.data.dao.BaseSemesterDao;
import net.zdsoft.eis.base.data.service.BaseDateInfoService;
import net.zdsoft.eis.base.data.service.BaseSemesterService;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.leadin.exception.BusinessErrorException;

import org.apache.commons.lang.StringUtils;

/**
 * @author yanb
 * 
 */
public class BaseSemesterServiceImpl extends SemesterServiceImpl implements
		BaseSemesterService  {
	private BaseSemesterDao baseSemesterDao;
	private JwSemesterUtil jwSemesterUtil;
	private GregorianCalendar gc;	
	private BaseDateInfoService baseDateInfoService;
    private UnitService unitService;
    private SystemIniService systemIniService;
	
	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}
    public BaseSemesterServiceImpl() {
		gc = new GregorianCalendar();
	}

	public void setBaseSemesterDao(BaseSemesterDao baseSemesterDao) {
		this.baseSemesterDao = baseSemesterDao;
	}

	public void setJwSemesterUtil(JwSemesterUtil jwSemesterUtil) {
		this.jwSemesterUtil = jwSemesterUtil;
	}

	public void saveSemester(Semester semester) {
		// 取学年学期信息
		Semester existsSem = getSemester(semester.getAcadyear(), semester
				.getSemester());
		// 判断日期是否有交叉
		boolean isCross = isCrossDate(semester.getAcadyear(), semester
				.getSemester(), semester.getWorkBegin(), semester.getWorkEnd());
		if (isCross)
			throw new BusinessErrorException("保存学年<u>" + semester.getAcadyear()
					+ "</u>的第<u>" + semester.getSemester()
					+ "</u>学期信息失败！原因：可能存在日期的交叉！");

		// 判断开始日期是否小于结束日期
		boolean isLogic = jwSemesterUtil.isLogicDate(semester.getWorkBegin(),
				semester.getWorkEnd());
		if (!isLogic)
			throw new BusinessErrorException("保存学年<u>" + semester.getAcadyear()
					+ "</u>的第<u>" + semester.getSemester()
					+ "</u>学期信息失败！原因：可能开始日期大于结束日期！");

		// 根据新增还是修改生成entity
		if (StringUtils.isBlank(semester.getId())) {
			// 新增
			// 先验证是否存在相同记录
			if (null != existsSem) {
				throw new BusinessErrorException("保存学年<u>"
						+ semester.getAcadyear() + "</u>的第<u>"
						+ semester.getSemester()
						+ "</u>学期信息失败！原因：已存在相同的学年、学期记录！");
			}
			baseSemesterDao.insertSemester(semester);
		} else {
			baseSemesterDao.updateSemester(semester);
		}
        //新增教育的节假日信息(全局)
        Unit topUnit = unitService.getTopEdu();
        SystemIni systemIni = systemIniService.getSystemIni("SUNDAY_WEEK_DAY");
        if(systemIni != null && "1".equals(systemIni.getNowValue())){
        	baseDateInfoService.saveDateInfo(topUnit.getId(), semester.getAcadyear(),
					semester.getSemester(), semester.getSemesterBegin(), semester.getSemesterEnd(), true);
        }else{
        	baseDateInfoService.saveDateInfo(topUnit.getId(), semester.getAcadyear(),
					semester.getSemester(), semester.getSemesterBegin(), semester.getSemesterEnd(), false);
        }
        
		saveSemesterEx();
	}

	public void addSemester(Semester semester) {
		baseSemesterDao.insertSemester(semester);
		saveSemesterEx();
	}

	public void updateSemester(Semester semester) {
		baseSemesterDao.updateSemester(semester);
		saveSemesterEx();
	}

	private void saveSemesterEx() {
		clearCache();

		semesterAffair.updateAffairCompleteSign();
	}

	/**
	 * 判断新增加的学年学期与本教育局中已存在的学年学期的日期是否存在交叉情况
	 * 
	 * @param acadyear 学年
	 * @param semester 学期
	 * @param workbegin 工作开始日期
	 * @param workend 工作结束日期
	 * @return boolean true?存在：不存在
	 */
	private boolean isCrossDate(String acadyear, String semester,
			Date workbegin, Date workend) {

		int intxn = Integer.parseInt(acadyear.substring(0, 4));
		String lastAcadyear = String.valueOf(intxn - 1) + "-"
				+ String.valueOf(intxn);
		String nextAcadyear = String.valueOf(intxn + 1) + "-"
				+ String.valueOf(intxn + 2);
		String lastSemester = "2";
		String nextSemester = "1";

		if (semester.equals("2")) {
			lastAcadyear = acadyear;
			lastSemester = "1";
		} else {
			nextAcadyear = acadyear;
			nextSemester = "2";
		}

		// 转换日期为字符串
		String workBegin = DateUtils.date2StringByDay(workbegin);
		String workEnd = DateUtils.date2StringByDay(workend);

		return baseSemesterDao.isSemesterCross(lastAcadyear, lastSemester,
				nextAcadyear, nextSemester, workBegin, workEnd);
	}

	public void deleteSemester(String[] delArray, EventSourceType eventSource) {
		// 软删
		baseSemesterDao.deleteSemester(delArray, eventSource);
		clearCache();
	}

	public Semester getSemester(String semesterid) {
		return baseSemesterDao.getSemester(semesterid);
	}

	public Semester getMaxSemester() {
		return baseSemesterDao.getMaxSemester();
	}

	@SuppressWarnings("unchecked")
	public Semester getDefaultSemester() {
		// 先得到要添加的下一条记录的学年和学期
		Semester sem = new Semester();

		Semester maxSemester = getMaxSemester();
		// 判断数据库中是否已有学年学期记录，若没有则默认生成一个
		if (null == maxSemester) {
			gc.setTime(new Date());
			int endYear = gc.get(GregorianCalendar.YEAR);
			int startYear = endYear - 1;
			String acadYear = String.valueOf(startYear) + "-"
					+ String.valueOf(endYear);

			sem.setAcadyear(acadYear); // 学年格式为:2005-2006
			sem.setSemester("1"); // 学期微代码表示:1第一学期，2第二学期
		} else {
			if ("1".equals(maxSemester.getSemester())) {
				sem.setAcadyear(maxSemester.getAcadyear());
				sem.setSemester("2");
			} else {
				String acadYear2 = jwSemesterUtil.getNextAcadyear(maxSemester
						.getAcadyear());

				sem.setAcadyear(acadYear2);
				sem.setSemester("1");
			}
		}

		String acadyear = sem.getAcadyear();
		String semester = sem.getSemester();

		// 1、先取配置文件
		Map map = jwSemesterUtil.getValueFromXml(acadyear, semester);
		if (map != null && map.size() > 0) {
			sem.setWorkBegin((Date) map.get("workbegin"));
			sem.setWorkEnd((Date) map.get("workend"));
			sem.setSemesterBegin((Date) map.get("semesterbegin"));
			sem.setSemesterEnd((Date) map.get("semesterend"));

			sem.setEduDays(Short.parseShort(map.get("edudays").toString()));
			sem.setAmPeriods(Short.parseShort(map.get("amperiods").toString()));
			sem.setPmPeriods(Short.parseShort(map.get("pmperiods").toString()));
			sem.setNightPeriods(Short.parseShort(map.get("nightperiods")
					.toString()));
			sem.setRegisterdate((Date) map.get("registerdate"));
			sem.setClasshour(Short.parseShort(map.get("classhour").toString()));
		}

		// 2、若上学期有，则再取上学期的
		String proAcadyear = jwSemesterUtil.getPreAcadyear(acadyear);
		Semester proJwSem = getSemester(proAcadyear, semester);
		if (proJwSem != null) {
			sem.setWorkBegin(jwSemesterUtil.getNextYearDate(proJwSem
					.getWorkBegin()));
			sem.setWorkEnd(jwSemesterUtil
					.getNextYearDate(proJwSem.getWorkEnd()));
			sem.setSemesterBegin(jwSemesterUtil.getNextYearDate(proJwSem
					.getSemesterBegin()));
			sem.setSemesterEnd(jwSemesterUtil.getNextYearDate(proJwSem
					.getSemesterEnd()));
			sem.setRegisterdate(jwSemesterUtil.getNextYearDate(proJwSem
					.getRegisterdate()));
			sem.setEduDays(proJwSem.getEduDays());
			sem.setAmPeriods(proJwSem.getPmPeriods());
			sem.setPmPeriods(proJwSem.getPmPeriods());
			sem.setNightPeriods(proJwSem.getNightPeriods());
			sem.setClasshour(proJwSem.getClasshour());
		}

		return sem;
	}

    private SemesterAffair semesterAffair;

    public void setSemesterAffair(SemesterAffair semesterAffair) {
        this.semesterAffair = semesterAffair;
    }

    public static class SemesterAffair extends AbstractTransact {
        private SemesterService semesterService;
        
        public void setSemesterService(SemesterService semesterService) {
            this.semesterService = semesterService;
        }

        public SemesterAffair() {
            super();
        }

        public String getIdentifier() {
            return "BASE_SEMESTER_CURRENT";
        }

        public boolean isComplete() {
            if (null == semesterService.getCurrentSemester()) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public String getReceiverId() {
            return getTopUnitId();
        }
    }

    public void setUnitService(UnitService unitService) {
        this.unitService=unitService;
    }
    public UnitService getUnitService() {
        return this.unitService;
    }

	public void setBaseDateInfoService(BaseDateInfoService baseDateInfoService) {
		this.baseDateInfoService = baseDateInfoService;
	}

	public BaseDateInfoService getBaseDateInfoService() {
		return baseDateInfoService;
	}

}
