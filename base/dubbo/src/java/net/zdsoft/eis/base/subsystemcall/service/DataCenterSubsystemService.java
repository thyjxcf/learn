package net.zdsoft.eis.base.subsystemcall.service;

import java.util.List;
/**
 * 数据中心
 * @author huy
 */
public interface DataCenterSubsystemService {
	/**
	 * 判断学校是否有上年度数据结转数据
	 * @param clsList
	 * @author huy
	 * @date 2015-8-10下午06:34:59
	 */
    public boolean isHaveLastYearData(String schoolId);
    /**
     * 判断是否是幼儿园学校
     * @param schoolId
     * @return
     * @author huy
     * @date 2015-9-10下午06:23:09
     */
    public boolean isPreEdu(String schoolId);

}
