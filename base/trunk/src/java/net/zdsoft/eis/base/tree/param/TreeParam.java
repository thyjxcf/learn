/* 
 * @(#)TreeParam.java    Created on May 17, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.tree.param;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

/**
 * 树参数
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 17, 2011 1:38:37 PM $
 */
public class TreeParam {

	/**
	 * 树
	 */
	private String treeServiceBeanId;

	/**
	 * 复选框
	 */
	private boolean checkbox = false;

	/**
	 * 只显示有权限的节点
	 */
	private boolean onlyShowPopedom = false;

	/**
	 * 只显示有权限的节点(教务)
	 */
	private boolean eduadmShowPopedom = false;

	/**
	 * 教师id
	 */
	private String teacherId;

	/**
	 * 直接显示指定的那一层
	 */
	private boolean directShowLayer = false;

	/**
	 * 路径
	 */
	private String contextPath;

	/**
	 * 是否所有的树结点名称点击都需要开放链接。
	 */
	private boolean allLinkOpen = true;

	/**
	 * 树的类别
	 */
	private int treeType;

	/**
	 * 显示到哪一层
	 */
	private int layer;

	/**
	 * 用于自定义业务树的显示层次
	 */
	private int customLayer;

	/**
	 * 公共最低层的xmlSrc，不包括参数，用于自定义业务树
	 */
	private String customXmlSrc;

	/**
	 * 用于自定义业务树链接参数
	 */
	private String customLinkParams;

	/**
	 * 毕业学年
	 */
	private String graduateAcadyear;

	// -------------权限控制--------------

	/**
	 * 是否需要权限控制，所有下属权限
	 */
	private boolean needAllPopedom = false;

	/**
	 * 是否需要权限控制，直接下属权限
	 */
	private boolean needDirectPopedom = false;

	/**
	 * 当前用户：权限控制时使用
	 */
	private String userId;

	/**
	 * 当前用户所在部门：权限控制时使用
	 */
	private String userDeptId;

	// -------------动态变化--------------

	/**
	 * 单位id，值不断的变化
	 */
	private String unitId;
	
	/**
	 * 年级id,值不断的变化
	 */
	private String gradeId;

	/**
	 * 开设学年，值不断的变化，相当于年级
	 */
	private String openAcadyear;

	/**
	 * 上级id，值不断的变化
	 */
	private String parentId;

	/**
	 * 上级类型，值不断的变化
	 */
	private int parentType;

	// -------------共用业务--------------
	/**
	 * 班级id，值不断的变化
	 */
	private String classId;

	/**
	 * 学年
	 */
	private String acadyear;
	
	/**
	 * 学期
	 */
	private String semester;

	/**
	 * 是否显示毕业班
	 */
	private boolean graduateClass;

	/**
	 * 单位类型
	 */
	private int unitClass;

	/**
	 * 是否显示下属教育局
	 */
	private boolean showEdu;

	/**
	 * 是否显示下属学校
	 */
	private boolean showSch;
	
	/**
	 * 是否显示Eis年级
	 */
	private boolean showEisGrade;

	/**
	 * 是否显示下属学区
	 */
	private boolean showSchDistrict;

	/**
	 * 单位类型
	 */
	private int unitType;

	// --------------eisu使用----------
	/**
	 * 院系类别
	 */
	private int instituteKind;

	/**
	 * 专业id，值不断的变化
	 */
	private String specialtyId;

	/**
	 * 专业方向id，值不断的变化
	 */
	private String specialtyPointId;

	/**
	 * 是否显示院系
	 */
	private boolean showInstitute = true;

	/**
	 * 是否显示专业方向
	 */
	private boolean showSpecialtyPoint = true;

	/**
	 * 在显示专业方向时，即使专业下没有专业方向，也显示一个“无方向”
	 */
	private boolean showNoneSpecialtyPoint = false;

	/**
	 * 是否显示年级
	 */
	private boolean showGrade = true;

	/**
	 * 显示哪些节点自定义，不根据配置来，由于一些功能对多级节点都可以进行操作
	 * 如：部门教研组设置：教研组即可以挂在院系下，也可以挂在部门下，故院系和部门都要展现
	 */
	private boolean showInstituteCustom;
	private boolean showSpecialtyPointCustom;
	private boolean showGradeCustom;
	

	// 是否有效
	private boolean isShowValid = true;
	
	private boolean showPreGraduateCls = true;
	
	private String regionCode;
	private String type;
	
	private boolean showDeptShortName = false;
	
	//东莞人事-教师档案-个人信息-地区选择使用(regionTree)
	private boolean dgPersonnel = false;
	
	public boolean isDgPersonnel() {
		return dgPersonnel;
	}

	public void setDgPersonnel(boolean dgPersonnel) {
		this.dgPersonnel = dgPersonnel;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public boolean isAllLinkOpen() {
		return allLinkOpen;
	}

	public void setAllLinkOpen(boolean allLinkOpen) {
		this.allLinkOpen = allLinkOpen;
	}

	public int getTreeType() {
		return treeType;
	}

	public void setTreeType(int treeType) {
		this.treeType = treeType;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getGraduateAcadyear() {
		return graduateAcadyear;
	}

	public void setGraduateAcadyear(String graduateAcadyear) {
		this.graduateAcadyear = graduateAcadyear;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getParentType() {
		return parentType;
	}

	public void setParentType(int parentType) {
		this.parentType = parentType;
	}

	public boolean isNeedPopedom() {
		return needAllPopedom || needDirectPopedom;
	}

	public boolean isNeedAllPopedom() {
		return needAllPopedom;
	}

	public void setNeedAllPopedom(boolean needAllPopedom) {
		this.needAllPopedom = needAllPopedom;
	}

	public boolean isNeedDirectPopedom() {
		return needDirectPopedom;
	}

	public void setNeedDirectPopedom(boolean needDirectPopedom) {
		this.needDirectPopedom = needDirectPopedom;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserDeptId() {
		return userDeptId;
	}

	public void setUserDeptId(String userDeptId) {
		this.userDeptId = userDeptId;
	}

	public String getOpenAcadyear() {
		return openAcadyear;
	}

	public void setOpenAcadyear(String openAcadyear) {
		this.openAcadyear = openAcadyear;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getAcadyear() {
		return acadyear;
	}

	public void setAcadyear(String acadyear) {
		this.acadyear = acadyear;
	}

	public boolean isGraduateClass() {
		return graduateClass;
	}

	public void setGraduateClass(boolean graduateClass) {
		this.graduateClass = graduateClass;
	}

	public int getInstituteKind() {
		return instituteKind;
	}

	public void setInstituteKind(int instituteKind) {
		this.instituteKind = instituteKind;
	}

	public String getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(String specialtyId) {
		this.specialtyId = specialtyId;
	}

	public String getSpecialtyPointId() {
		return specialtyPointId;
	}

	public void setSpecialtyPointId(String specialtyPointId) {
		this.specialtyPointId = specialtyPointId;
	}

	public int getUnitClass() {
		return unitClass;
	}

	public void setUnitClass(int unitClass) {
		this.unitClass = unitClass;
	}

	public int getCustomLayer() {
		return customLayer;
	}

	public void setCustomLayer(int customLayer) {
		this.customLayer = customLayer;
	}

	public String getCustomXmlSrc() {
		return customXmlSrc;
	}

	public void setCustomXmlSrc(String customXmlSrc) {
		this.customXmlSrc = customXmlSrc;
	}

	public String getCustomLinkParams() {
		return customLinkParams;
	}

	public boolean isShowEdu() {
		return showEdu;
	}

	public void setShowEdu(boolean showEdu) {
		this.showEdu = showEdu;
	}

	public boolean isShowSch() {
		return showSch;
	}

	public void setShowSch(boolean showSch) {
		this.showSch = showSch;
	}

	public boolean isShowSchDistrict() {
		return showSchDistrict;
	}

	public void setShowSchDistrict(boolean showSchDistrict) {
		this.showSchDistrict = showSchDistrict;
	}

	public int getUnitType() {
		return unitType;
	}

	public void setUnitType(int unitType) {
		this.unitType = unitType;
	}

	public boolean isShowInstitute() {
		return showInstitute;
	}

	public void setShowInstitute(boolean showInstitute) {
		this.showInstitute = showInstitute;
	}

	public boolean isShowSpecialtyPoint() {
		return showSpecialtyPoint;
	}

	public void setShowSpecialtyPoint(boolean showSpecialtyPoint) {
		this.showSpecialtyPoint = showSpecialtyPoint;
	}

	public boolean isShowNoneSpecialtyPoint() {
		return showNoneSpecialtyPoint;
	}

	public void setShowNoneSpecialtyPoint(boolean showNoneSpecialtyPoint) {
		this.showNoneSpecialtyPoint = showNoneSpecialtyPoint;
	}

	public boolean isShowGrade() {
		return showGrade;
	}

	public void setShowGrade(boolean showGrade) {
		this.showGrade = showGrade;
	}

	public boolean isShowInstituteCustom() {
		return showInstituteCustom;
	}

	public void setShowInstituteCustom(boolean showInstituteCustom) {
		this.showInstituteCustom = showInstituteCustom;
	}

	public boolean isShowSpecialtyPointCustom() {
		return showSpecialtyPointCustom;
	}

	public void setShowSpecialtyPointCustom(boolean showSpecialtyPointCustom) {
		this.showSpecialtyPointCustom = showSpecialtyPointCustom;
	}

	public boolean isShowGradeCustom() {
		return showGradeCustom;
	}

	public void setShowGradeCustom(boolean showGradeCustom) {
		this.showGradeCustom = showGradeCustom;
	}

	public void setCustomLinkParams(String customLinkParams) {
		if (StringUtils.isNotEmpty(customLinkParams)) {
			try {
				this.customLinkParams = URLEncoder.encode(customLinkParams,
						"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

	}

	public boolean isDirectShowLayer() {
		return directShowLayer;
	}

	public void setDirectShowLayer(boolean directShowLayer) {
		this.directShowLayer = directShowLayer;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public boolean isOnlyShowPopedom() {
		return onlyShowPopedom;
	}

	public void setOnlyShowPopedom(boolean onlyShowPopedom) {
		this.onlyShowPopedom = onlyShowPopedom;
	}

	public boolean isEduadmShowPopedom() {
		return eduadmShowPopedom;
	}

	public void setEduadmShowPopedom(boolean eduadmShowPopedom) {
		this.eduadmShowPopedom = eduadmShowPopedom;
	}

	public boolean isShowValid() {
		return isShowValid;
	}

	public void setShowValid(boolean isShowValid) {
		this.isShowValid = isShowValid;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	public String getTreeServiceBeanId() {
        return treeServiceBeanId;
    }

    public void setTreeServiceBeanId(String treeServiceBeanId) {
        this.treeServiceBeanId = treeServiceBeanId;
    }

	public boolean isShowPreGraduateCls() {
		return showPreGraduateCls;
	}

	public void setShowPreGraduateCls(boolean showPreGraduateCls) {
		this.showPreGraduateCls = showPreGraduateCls;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isShowDeptShortName() {
		return showDeptShortName;
	}

	public void setShowDeptShortName(boolean showDeptShortName) {
		this.showDeptShortName = showDeptShortName;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public boolean isShowEisGrade() {
		return showEisGrade;
	}

	public void setShowEisGrade(boolean showEisGrade) {
		this.showEisGrade = showEisGrade;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}
}
