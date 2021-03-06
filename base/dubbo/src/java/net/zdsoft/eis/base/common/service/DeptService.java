package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Dept;

public interface DeptService {

	/**
     * 根据ID得到部门
     * 
     * @param deptId
     * @return
     */
    public Dept getDept(String deptId);
    
    /**
     * 根据具体部门名称获取部门
     * @param unitId
     * @param deptName
     * @return
     */
    public Dept getDeptByName(String unitId, String deptName);
    
	/**
	 * 根据单位ID和部门ID得到该单位该部门的下级部门
	 * 
	 * @param unitId
	 * @param parentId
	 * @return
	 */
	public List<Dept> getDepts(String unitId, String parentId);
	public List<Dept> getDepts(String unitId, String parentId,String deptName);
	/**
     *  根据部门ID得到该单位该部门的上级部门列表
     * @param deptId
	 * @param deptName TODO
     * @return
     */
    public List<Dept> getDeptsByDeptId(String deptId, String deptName);

	/**
     * 根据单位ID得到所有部门
     * 
     * @param unitId
     * @return
     */
    public List<Dept> getDepts(String unitId);

    /**
     * 取得该单位顶层的部门列表，不包括二级部门及子部门，parentId和instituteId都为32个0
     * 
     * @param unitId
     * @return
     */
    public List<Dept> getDirectDepts(String unitId);      
    public List<Dept> getDirectDepts(String unitId,String deptName); 
    /**
     * 主要用在教研組 dept=2
     * @param unitId
     * @param deptType
     * @param deptName 這個是模糊查詢
     * @return
     */
    public List<Dept> getDirectDepts(String unitId,int deptType,String deptName);
    /**
     * 根据教师id判读是否这个部门下成员，如果是返回教研组id
     * @param teacherId
     * @return
     */
    public String isTeacherGroupHead(String teacherId);
    /**
     * 根据父部门ID得到下属部门列表，根据排序号排序。
     * 
     * @param parentId
     * @return
     */
    public List<Dept> getDeptsByParentId(String parentId);
    public List<Dept> getDeptsByParentId(String parentId,String deptName);
    /**
     * 取院系的直属部门
     * 
     * @param instituteId
     * @return
     */
    public List<Dept> getDirectDeptsByInstituteId(String instituteId);

    /**
     * 根据单位id取部门信息
     * @param unitId
     * @return
     */
    public Map<String, Dept> getDeptMap(String unitId);
    
    /**
     * 根据Id串得到部门map
     * 
     * @param deptIds
     * @return
     */
    public Map<String, Dept> getDeptMap(String[] deptIds);
    
    /**
     * 根据用户id判读是否负责人如果是返回部门id
     * @param teacherId
     * @return
     */
    public String isPrincipanGroupHead(String userId);
    
    /**
     * 是否分管校长
     * @param unitId
     * @param userId
     * @return
     */
    public boolean isDeputyHead(String unitId, String userId);
    
    /**
     * 分管校长
     * @param unitId
     * @param userId
     * @return
     */
    public List<Dept> DeputyHead(String unitId, String userId);
    
    /**
     * 是否分管领导
     * @param unitId
     * @param userId
     * @return
     */
    public boolean isLeader(String unitId, String userId);
    
    /**
     * 取校区下面的所有部门
     * @param areaId
     * @return
     */
    public List<Dept> getDeptsByAreaId(String areaId);
    
    /**
     * 获取部门字符串（格式为：部门（单位）），通讯录使用
     * @param deptIds
     * @return
     */
    public String getDeptDetailNamesStr(String[] deptIds);
    
    /**
     * 根据部门ids获取部门信息
     * @param deptIds
     * @return
     */
    public List<Dept> getDeptList(String[] deptIds);

}
