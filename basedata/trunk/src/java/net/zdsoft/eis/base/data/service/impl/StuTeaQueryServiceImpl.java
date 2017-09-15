package net.zdsoft.eis.base.data.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.Mcode;
import net.zdsoft.eis.base.common.service.McodeService;
import net.zdsoft.eis.base.data.entity.EisBaseStudent;
import net.zdsoft.eis.base.data.service.BaseClassService;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.data.service.EisBaseStudentService;
import net.zdsoft.eis.base.data.service.StuTeaQueryService;
import net.zdsoft.keel.util.Pagination;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author lufeng
 * @version 创建时间：2016-7-19 下午03:30:50 类说明
 */
public class StuTeaQueryServiceImpl implements StuTeaQueryService {
	// private MangePatternService mangePatternService;
	private EisBaseStudentService eisBaseStudentService;
	private BaseUserService baseUserService;
	private McodeService mcodeService;
	private BaseClassService baseClassService;

	@Override
	public List<User> getStudentInfo(String unitid, String name,
			String username, Pagination page) {
		// TODO Auto-generated method stub

		List<EisBaseStudent> students = eisBaseStudentService
				.getStudentBySchoolIdName(unitid, name);

		List<User> studentList = new ArrayList<User>();
		Map<String, User> userMap = baseUserService.getUserByName(unitid,
				User.STUDENT_LOGIN, username);

		Mcode mcodeSex = mcodeService.getMcode("DM-XB");
		Map<String, BasicClass> classMap = baseClassService.getClassMap(unitid);
		User studentUser = null;
		User user = null;
		for (EisBaseStudent stu : students) {
			user = userMap.get(stu.getId());
			studentUser = new User();
			studentUser.setRealname(stu.getStuname());
			studentUser.setSex(mcodeSex.get(String.valueOf(stu.getSex())));
			BasicClass classInfo = classMap.get(stu.getClassid());
			if (classInfo != null) {
				studentUser.setClassName(classInfo.getGradeName()
						+ classInfo.getClassname());
			}

			if (StringUtils.isNotEmpty(username)) {
				if (user != null) {
					studentUser.setName(user.getName());
					studentList.add(studentUser);
				}

			} else {
				if (user != null) {
					studentUser.setName(user.getName());
				} else {
					studentUser.setName("");
				}
				studentList.add(studentUser);
			}

		}
		List<User> listOutput = new ArrayList<User>();
		if (CollectionUtils.isNotEmpty(studentList) && page != null) {
			// 对page进行 重新 初始化
			page.setMaxRowCount(studentList.size());
			page.initialize();
			if (studentList.size() > 0) {
				int max = 0;
				if (page.getPageIndex() == page.getMaxPageIndex()) {
					max = page.getMaxRowCount();

				} else {
					max = page.getPageIndex() * page.getPageSize();

				}
				// 显示list进行输出设置
				for (int i = (page.getPageIndex() - 1) * page.getPageSize(); i < max; i++) {
					listOutput.add(studentList.get(i));
				}
			}
			return listOutput;
		} else {
			return studentList;
		}

	}

	public void setEisBaseStudentService(
			EisBaseStudentService eisBaseStudentService) {
		this.eisBaseStudentService = eisBaseStudentService;
	}

	public void setBaseUserService(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}

	public void setMcodeService(McodeService mcodeService) {
		this.mcodeService = mcodeService;
	}

	public void setBaseClassService(BaseClassService baseClassService) {
		this.baseClassService = baseClassService;
	}

}
