/* 
 * @(#)BaseDeptServiceImpl.java    Created on Nov 20, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.service.impl.DeptServiceImpl;
import net.zdsoft.eis.base.data.dao.BaseDeptDao;
import net.zdsoft.eis.base.data.service.BaseDeptService;
import net.zdsoft.eis.base.event.GroupEvent;
import net.zdsoft.eis.base.event.impl.GroupEventDispatcher;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.BusinessErrorException;
import net.zdsoft.leadin.exception.ItemExistsException;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 20, 2009 10:05:16 AM $
 */
public class BaseDeptServiceImpl extends DeptServiceImpl implements BaseDeptService {
    private BaseDeptDao baseDeptDao;
    private GroupEventDispatcher groupEventDispatcher;

    public void setBaseDeptDao(BaseDeptDao baseDeptDao) {
        this.baseDeptDao = baseDeptDao;
    }

    public void setGroupEventDispatcher(GroupEventDispatcher groupEventDispatcher) {
        this.groupEventDispatcher = groupEventDispatcher;
    }

    public void insertDept(Dept dept) {
        baseDeptDao.insertDept(dept);
    }
    public void saveBatchDepts4Mq(List<Dept> depts) throws BusinessErrorException {
        if (depts == null || depts.size() < 1)
            return;
        baseDeptDao.saveBatchDepts(depts);
    }

    public void updateBatchDepts4Mq(List<Dept> depts) throws BusinessErrorException {
        if (depts == null || depts.size() < 1)
            return;
        baseDeptDao.updateBatchDepts4Mq(depts);

    }
    public void saveDept(Dept dept) throws ItemExistsException {
        if (dept.getId() == null || dept.getId().length() == 0) {
            if (baseDeptDao.isExistsDeptCode(dept.getDeptCode(), dept.getUnitId())) {
                throw new ItemExistsException("该部门编号已存在", "deptCode");
            } else if (isExistsDeptName(dept.getDeptname(), dept.getUnitId())) {
                throw new ItemExistsException("该部门名称已存在", "deptname");
            }
        } else {
            if (isExistsDeptCode(dept.getDeptCode(), dept.getId(), dept.getUnitId())) {
                throw new ItemExistsException("该部门编号已存在", "deptCode");
            } else if (isExistsDeptName(dept.getDeptname(), dept.getId(), dept
                    .getUnitId())) {
                throw new ItemExistsException("该部门名称已存在", "deptname");
            }
        }

        if (dept.getId() == null) {
            baseDeptDao.insertDept(dept);
        } else {
            baseDeptDao.updateDept(dept);
        }
    }
    public void deleteDept(String[] deptIds) {
        baseDeptDao.deleteDept(deptIds,EventSourceType.LOCAL);
        GroupEvent groupEvent = new GroupEvent(deptIds, GroupEvent.GROUP_DELETE);
        groupEventDispatcher.dispatchEvent(groupEvent);
    }
    public void deleteDept(String[] deptIds,EventSourceType eventSource){
    	baseDeptDao.deleteDept(deptIds, eventSource);
    }
    
    public Dept getDept(String unitId,String deptName){
    	return baseDeptDao.getDept(unitId, deptName);
    }

    public Dept getNewDept(String parentId, String unitId) {
    	Long orderid = baseDeptDao.getAvaOrder(unitId);
        String deptCode = baseDeptDao.getAvaDeptCode(unitId);
        Dept parentDept = getDept(parentId);
        Dept dept = new Dept();
        dept.setUnitId(unitId);
        dept.setOrderid(orderid);
        dept.setParentid(parentId);
        dept.setDeptCode(deptCode);
        if (parentDept != null) {
            dept.setParentName(parentDept.getDeptname());
        }
        return dept;
    }

    public Long getAvaOrder(String unitId) {
        return baseDeptDao.getAvaOrder(unitId);
    }

    public boolean isExistsDeptName(String deptname, String unitId) {
        return baseDeptDao.isExistsDeptName(deptname, unitId);
    }
    public boolean isExistsDefaultDeptName(String unitId){
    	return baseDeptDao.isExistsDefaultDeptName( unitId);
    }
    public boolean isExistsDeptName(String deptName, String id, String unitId) {
        return baseDeptDao.isExistsDeptName(deptName, id, unitId);
    }

    public boolean isExistsDeptCode(String deptCode, String id, String unitId) {
        return baseDeptDao.isExistsDeptCode(deptCode, id, unitId);
    }

    public Map<String, Integer> getDeptCount(String[] parentIds) {
        return baseDeptDao.getDeptCount(parentIds);
    }

    public String getAvaDeptCode(String unitId) {
        return baseDeptDao.getAvaDeptCode(unitId);
    }

	@Override
	public Dept getDefaultDept(String unitId) {
		return baseDeptDao.getDefaultDept(unitId);
	}

}
