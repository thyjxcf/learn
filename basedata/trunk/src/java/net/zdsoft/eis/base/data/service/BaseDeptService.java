/* 
 * @(#)BaseDeptService.java    Created on Nov 20, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.BusinessErrorException;
import net.zdsoft.leadin.exception.ItemExistsException;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 20, 2009 10:04:59 AM $
 */
public interface BaseDeptService extends DeptService {

    public void insertDept(Dept dept);

    public void saveDept(Dept dept) throws ItemExistsException;

    
    
    public void saveBatchDepts4Mq(List<Dept> depts) throws BusinessErrorException;

    public void updateBatchDepts4Mq(List<Dept> depts) throws BusinessErrorException;

    public void deleteDept(String[] deptIds);

    public void deleteDept(String[] deptIds, EventSourceType eventSource);

    /**
     * 根据单位id和部门名称获取部门
     * @param unitId
     * @param deptName
     * @return
     */
    public Dept getDept(String unitId,String deptName);
    
    /**
     * 生成新部门的相关信息
     * 
     * @param parentId
     * @return
     */
    public Dept getNewDept(String parentId, String unitId);

    /**
     * 得到该单位下可用部门最大排序编号
     * 
     * @param unitId
     * @return
     */
    public Long getAvaOrder(String unitId);

    /**
     * 得到该单位的可用部门代码
     * 
     * @param unitId
     * @return
     */
    public String getAvaDeptCode(String unitId);

    /**
     * 判断该单位中部门名称是否已存在
     * 
     * @param deptname
     * @param unitId
     * @return
     */
    public boolean isExistsDeptName(String deptname, String unitId);
    
    public boolean isExistsDefaultDeptName(String unitId);

    /**
     * 除id以外,判断该单位是否已有该部门名称
     * 
     * @param deptName
     * @param deptid
     * @param unitId
     * @return
     */
    public boolean isExistsDeptName(String deptName, String id, String unitId);

    /**
     * 除id以外,判断该单位是否已由该部门编号
     * 
     * @param deptCode
     * @param id
     * @param unitId
     * @return
     */
    public boolean isExistsDeptCode(String deptCode, String id, String unitId);

    /**
     * 根据部门id串得到各个部门的下级部门数量
     * 
     * @param parentIds
     * @return
     */
    public Map<String, Integer> getDeptCount(String[] parentIds);
    /**
     * 取单位默认部门
     * 
     * @param deptname
     * @param unitId
     * @return
     */
    public Dept getDefaultDept(String unitId);

}
