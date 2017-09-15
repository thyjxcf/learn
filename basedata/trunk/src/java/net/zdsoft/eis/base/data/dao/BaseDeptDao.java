/* 
 * @(#)BaseDeptDao.java    Created on Nov 20, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.sync.EventSourceType;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 20, 2009 10:02:50 AM $
 */
public interface BaseDeptDao {
	public void insertDept(Dept dept);

	public void deleteDept(String[] deptids, EventSourceType eventSource);

	public void updateDept(Dept dept);

	public void saveBatchDepts(List<Dept> depts);

	public void updateBatchDepts4Mq(List<Dept> depts);

	/**
	 * 根据单位id和部门名称获取部门
	 * 
	 * @param unitId
	 * @param deptName
	 * @return
	 */
	public Dept getDept(String unitId, String deptName);

	/**
	 * 在单位内，判断是否存在该部门编码
	 * 
	 * @param deptCode
	 * @param unitId
	 * @return
	 */
	public boolean isExistsDeptCode(String deptCode, String unitId);

	/**
	 * 在该单位下所有部门中（除去指定excludeDeptId部门外）， 判断是否存在指定的部门code。
	 * 
	 * @param deptCode
	 * @param excludeDeptId
	 * @param unitId
	 * @return
	 */
	public boolean isExistsDeptCode(String deptCode, String excludeDeptId,
			String unitId);

	/**
	 * 得到该单位下可用部门最大排序编号
	 * 
	 * @param unitId
	 * @return
	 */
	public Long getAvaOrder(String unitId);

	/**
	 * 在单位内，判断是否存在这个部门名称
	 * 
	 * @param deptName
	 * @param unitId
	 * @return 是返回true,否返回false;
	 */
	public boolean isExistsDeptName(String deptName, String unitId);
	/**
	 * 在单位内，管理员组是否存在
	 * 
	 * @param deptName
	 * @param unitId
	 * @return 是返回true,否返回false;
	 */
	public boolean isExistsDefaultDeptName( String unitId);

	/**
	 * 单位中这些部门名称存在情况
	 * 
	 * @param deptnames
	 * @param unitId
	 * @return
	 */
	public Map<String, String> existsDeptNameMap(String[] deptnames);

	/**
	 * 单位中这些部门编号存在情况
	 * 
	 * @param deptnames
	 * @param unitId
	 * @return
	 */
	public Map<String, String> existsDeptCodesMap(String[] deptcodes);

	/**
	 * 在该单位下所有部门中（除去指定excludeDeptId部门外）， 判断是否存在指定的部门名称。
	 * 
	 * @param deptname
	 * @param excludeDeptId
	 * @param unitId
	 * @return
	 */
	public boolean isExistsDeptName(String deptname, String excludeDeptId,
			String unitId);

	/**
	 * 得到该单位下所有部门，除去指定deptId部门外。
	 * 
	 * @param unitId
	 * @param excludeDeptId
	 * @return
	 */
	public List<Dept> getAllUnitDeptExceptSelf(String unitId,
			String excludeDeptId);

	/**
	 * 根据部门id串得到各个部门的下级部门数量
	 * 
	 * @param parentIds
	 * @return
	 */
	public Map<String, Integer> getDeptCount(String[] parentIds);

	/**
	 * 得到该单位的可用部门代码
	 * 
	 * @param unitId
	 * @return
	 */
	public String getAvaDeptCode(String unitId);
	/**
     * 取单位默认部门
     * 
     * @param deptname
     * @param unitId
     * @return
     */
    public Dept getDefaultDept(String unitId);

}
