package net.zdsoft.eis.base.common.entity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.simple.entity.SimpleClass;

import net.zdsoft.eis.base.util.SUtils;

import net.zdsoft.eis.base.util.SUtils;
import net.zdsoft.keel.util.Pagination;

public class BasicClass extends SimpleClass implements Comparable<BasicClass> {

	public static List<BasicClass> dt(String data) {
		List<BasicClass> ts = SUtils.dt(data,
				new TypeReference<List<BasicClass>>() {
				});
		if (ts == null)
			ts = new ArrayList<BasicClass>();
		return ts;

	}

	public static List<BasicClass> dt(String data, Pagination page) {
		JSONObject json = JSONObject.parseObject(data);
		List<BasicClass> ts = SUtils.dt(json.getString("data"),
				new TypeReference<List<BasicClass>>() {
				});
		if (ts == null)
			ts = new ArrayList<BasicClass>();
		if (json.containsKey("count"))
			page.setMaxRowCount(json.getInteger("count"));
		return ts;

	}

	public static BasicClass dc(String data) {
		return SUtils.dc(data, BasicClass.class);
	}

	private static final long serialVersionUID = -279135028967144707L;

	/**
	 * 班级荣誉称号
	 */
	private String honor;
	/**
	 * 班级类型
	 */
	private String classtype;
	/**
	 * 文理类型
	 */
	private int artsciencetype;

	// =======================台账属性上提 by zhangkc
	// 2015-05-06============================
	/**
	 * 是否复式班
	 */
	private boolean isDuplexClass;

	// =====================辅助字段==========================
	private String name; // 学校名称

	/**
	 * 初始化数据学生的班级为空串 32个0组成的字符串
	 */
	public static final String ZERO_CLASSID_GUID = BaseConstant.ZERO_GUID;

	/**
	 * 默认生成的班级名称是否使用括号SYSTEM.GRADENAME
	 */
	public static final String CLASSNAME_USE_BRACKETS = "SYSTEM.CLASSNAME.USE.BRACKETS";

	/**
	 * 是否允许修改年级名称
	 */
	public static final String GRADENAME_IS_RESET = "SYSTEM.GRADENAME";

	// ************************************
	// 班级是否毕业 2009-3-24
	// ************************************
	public static final int GRADUATE_SIGN_NO = 0; // 未毕业

	public static final int GRADUATE_SIGN_YES = 1; // 已毕业

	public int getGradeInt(String curAcadyear) {
		int endyear = Integer.parseInt(curAcadyear.substring(5));
		int startyear = Integer.parseInt(getAcadyear().substring(0, 4));
		int grade = endyear - startyear;

		// 判断年级是否已超出规定学制
		if (getSchoolinglen() > 0) {
			grade = (grade > getSchoolinglen() ? getSchoolinglen() : grade);
		}
		return grade;
	}

	// 取得年级
	public String getGrade(String curAcadyear) {
		long grade = Long.valueOf(curAcadyear.substring(0, 4))
				- Long.valueOf(getAcadyear().substring(0, 4)) + 1;
		return String.valueOf(grade);
	}

	// 取得年级代码
	public String getGradeCode(String curAcadyear) {
		return String.valueOf(getSection()) + getGradeInt(curAcadyear);
	}

	// 取得同类班级代码
	public String getKinClassCode(String curAcadyear) {
		return String.valueOf(getSection()) + getGradeInt(curAcadyear)
				+ artsciencetype;
	}

	public BasicClass() {
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BasicClass other = (BasicClass) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;

		return true;
	}

	public int compareTo(BasicClass o) {
		BasicClass other = o;
		int cmp;

		// 学校代码
		if (getSchid() != null) {
			cmp = getSchid().compareTo(other.getSchid());
			if (cmp != 0)
				return cmp;
		}

		// 学段

		cmp = getSection() - other.getSection();
		if (cmp != 0)
			return cmp;

		// 入学学年(倒序)
		if (getAcadyear() != null) {
			cmp = getAcadyear().compareTo(other.getAcadyear());
			if (cmp != 0)
				return -cmp;
		}

		// 班级代码
		if (getClasscode() != null) {
			cmp = getClasscode().compareTo(other.getClasscode());
			if (cmp != 0)
				return cmp;
		}

		return 0;
	}

	public String getClasstype() {
		return classtype;
	}

	public void setClasstype(String classtype) {
		this.classtype = classtype;
	}

	public int getArtsciencetype() {
		return artsciencetype;
	}

	public void setArtsciencetype(int artsciencetype) {
		this.artsciencetype = artsciencetype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHonor() {
		return honor;
	}

	public void setHonor(String honor) {
		this.honor = honor;
	}

	public boolean getIsDuplexClass() {
		return isDuplexClass;
	}

	public void setIsDuplexClass(boolean isDuplexClass) {
		this.isDuplexClass = isDuplexClass;
	}
}
