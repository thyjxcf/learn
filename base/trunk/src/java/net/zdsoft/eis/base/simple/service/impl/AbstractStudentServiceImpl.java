/* 
 * @(#)AbstractStudentServiceImpl.java    Created on May 16, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.simple.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.simple.entity.SimpleStudent;
import net.zdsoft.eis.base.simple.service.AbstractStudentService;
import net.zdsoft.eis.base.storage.StorageFileService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.QueryConditionUtils.QueryCondition;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 16, 2011 2:54:00 PM $
 */
public abstract class AbstractStudentServiceImpl<E extends SimpleStudent> extends Observable
        implements AbstractStudentService<E> {
    protected StorageFileService storageFileService;

    public void setStorageFileService(StorageFileService storageFileService) {
        this.storageFileService = storageFileService;
    }
    
    // =====================以上为set方法=======================

    public E getStudent(String studentId) {
        return getStudentDao().getStudent(studentId);
    }

    public E getStudentBy2Code(String schoolId, String code) {
    	E e= getStudentDao().getStudentBy2Code(schoolId, code);
    	if(e!=null){
			if(BaseConstant.LEAVESIGN_OUT==e.getLeavesign()){
				e.setStuname(e.getStuname()+"(离校)");
			}
		}
        return e;
    }

    public E getStudentByUnitivecode(String unitiveCode) {
    	E e= getStudentDao().getStudentByUnitivecode(unitiveCode);
    	if(e!=null){
			if(BaseConstant.LEAVESIGN_OUT==e.getLeavesign()){
				e.setStuname(e.getStuname()+"(离校)");
			}
		}
        return e;
    }

    public E getStudentPwdByUnitivecode(String unitiveCode) {
    	E e= getStudentDao().getStudentByUnitivecode(unitiveCode);
    	if(e!=null){
			if(BaseConstant.LEAVESIGN_OUT==e.getLeavesign()){
				e.setStuname(e.getStuname()+"(离校)");
			}
		}
        return e;
    }

    public E getStudentByUnitivecode(String schoolId, String unitiveCode) {
    	E e= getStudentDao().getStudentByUnitivecode(schoolId, unitiveCode);
    	if(e!=null){
			if(BaseConstant.LEAVESIGN_OUT==e.getLeavesign()){
				e.setStuname(e.getStuname()+"(离校)");
			}
		}
        return e;
    }

    public List<E> getStudentsByIds(String[] studentIds) {
    	List<E> list_ = new ArrayList<E>();
    	List<E> stuList=getStudentDao().getStudentsByIds(studentIds);
    	if(stuList!=null && stuList.size()>0){
    		for(E e:stuList){
    			if(BaseConstant.LEAVESIGN_OUT==e.getLeavesign()){
        			e.setStuname(e.getStuname()+"(离校)");
        		}
    			list_.add(e);
        	}
    	}
        return list_;
    }

    public List<E> getStudents(String schoolId, String[] studentCodes) {
    	List<E> list_ = new ArrayList<E>();
    	List<E> stuList=getStudentDao().getStudents(schoolId, studentCodes);
    	if(stuList!=null && stuList.size()>0){
    		for(E e:stuList){
    			if(BaseConstant.LEAVESIGN_OUT==e.getLeavesign()){
        			e.setStuname(e.getStuname()+"(离校)");
        		}
    			list_.add(e);
        	}
    	}
        return list_;
    }

    public List<E> getStudentsByUnitiveCodes(String[] unitiveCodes) {
    	List<E> list_ = new ArrayList<E>();
    	List<E> stuList=getStudentDao().getStudentsByUnitiveCodes(unitiveCodes);
    	if(stuList!=null && stuList.size()>0){
    		for(E e:stuList){
    			if(BaseConstant.LEAVESIGN_OUT==e.getLeavesign()){
        			e.setStuname(e.getStuname()+"(离校)");
        		}
    			list_.add(e);
        	}
    	}
        return list_;
    }

    public List<E> getStudentsByIdentityCard(String[] identityCard) {
    	List<E> list_ = new ArrayList<E>();
    	List<E> stuList=getStudentDao().getStudentsByIdentityCard(identityCard);
    	if(stuList!=null && stuList.size()>0){
    		for(E e:stuList){
    			if(BaseConstant.LEAVESIGN_OUT==e.getLeavesign()){
        			e.setStuname(e.getStuname()+"(离校)");
        		}
    			list_.add(e);
        	}
    	}
        return list_;
    }

    public List<E> getStudents(String classId) {
        return getStudentDao().getStudents(classId);
    }

    public List<E> getStudentsForAbnormalflow(String classId) {
        return getStudentDao().getStudentsForAbnormalflow(classId);
    }

    public List<E> getAllStudents(String classId) {
    	List<E> list_ = new ArrayList<E>();
    	List<E> stuList=getStudentDao().getAllStudents(classId);
    	if(stuList!=null && stuList.size()>0){
    		for(E e:stuList){
    			if(BaseConstant.LEAVESIGN_OUT==e.getLeavesign()){
        			e.setStuname(e.getStuname()+"(离校)");
        		}
    			list_.add(e);
        	}
    	}
        return list_;
    }

    public List<E> getStudents(String[] classIds) {
        return getStudentDao().getStudents(classIds);
    }

    public List<E> getStudents(String[] classIds, String leaveSign) {
        if (StringUtils.isEmpty(leaveSign)) {
        	List<E> list_ = new ArrayList<E>();
        	List<E> stuList=getStudentDao().getAllStudents(classIds);
        	if(stuList!=null && stuList.size()>0){
        		for(E e:stuList){
        			if(BaseConstant.LEAVESIGN_OUT==e.getLeavesign()){
            			e.setStuname(e.getStuname()+"(离校)");
            		}
        			list_.add(e);
            	}
        	}
            return list_;
        } else {
            return getStudents(classIds);
        }
    }

    public List<E> getStudentsForGraduated(String classId) {
    	List<E> list_ = new ArrayList<E>();
    	List<E> stuList=getStudentDao().getStudentsForGraduated(classId);
    	if(stuList!=null && stuList.size()>0){
    		for(E e:stuList){
    			if(BaseConstant.LEAVESIGN_OUT==e.getLeavesign()){
        			e.setStuname(e.getStuname()+"(离校)");
        		}
    			list_.add(e);
        	}
    	}
        return list_;
    }

    public List<E> getStudentsByFaintness(String schoolId,
            String studentName) {
    	List<E> list_ = new ArrayList<E>();
    	List<E> stuList=getStudentDao().getStudentsByFaintness(schoolId, studentName);
    	if(stuList!=null && stuList.size()>0){
    		for(E e:stuList){
    			if(BaseConstant.LEAVESIGN_OUT==e.getLeavesign()){
        			e.setStuname(e.getStuname()+"(离校)");
        		}
    			list_.add(e);
        	}
    	}
        return list_;
    }

    public List<String> getStudentsByFaintness(String schoolId,
            String studentName, String unitiveCode) {
        return getStudentDao().getStudentsByFaintness(schoolId, studentName,
                unitiveCode);
    }
    
    public List<E> getStudentsByStudentNameClassId(
    		String schoolId,String studentName, String classId, Pagination page){
    	return getStudentDao().getStudentsByStudentNameClassId(schoolId, studentName, classId,page);
    }
    
    public List<E> getStudentsByNameClsIds(String schId, String studentName, String[] clsIds, Pagination page){
    	return getStudentDao().getStudentsByNameClsIds(schId, studentName, clsIds, page);
    }
    
    public List<E> getStudentsByFaintnessStudentCode(String schoolId, String studentName,
            String studentCode) {
    	List<E> list_ = new ArrayList<E>();
    	List<E> stuList=getStudentDao().getStudentsByFaintnessStudentCode(schoolId, studentName, studentCode);
    	if(stuList!=null && stuList.size()>0){
    		for(E e:stuList){
    			if(BaseConstant.LEAVESIGN_OUT==e.getLeavesign()){
        			e.setStuname(e.getStuname()+"(离校)");
        		}
    			list_.add(e);
        	}
    	}
        return list_;
    }
    
    public List<E> queryStudentsFaintness(String unitviecode,
            String stuname, String unionid) {
    	List<E> list_ = new ArrayList<E>();
    	List<E> stuList=getStudentDao().queryStudentsFaintness(unitviecode, stuname, unionid);
    	if(stuList!=null && stuList.size()>0){
    		for(E e:stuList){
    			if(BaseConstant.LEAVESIGN_OUT==e.getLeavesign()){
        			e.setStuname(e.getStuname()+"(离校)");
        		}
    			list_.add(e);
        	}
    	}
        return list_;
    }

    public List<E> getStudentsByField(String field, String value) {
        return getStudentDao().getStudentsByField(field, value);
    }

    public List<E> getStudentsByAnyConditions(List<QueryCondition> list) {
    	List<E> list_ = new ArrayList<E>();
    	List<E> stuList=getStudentDao().getStudentsByAnyConditions(list);
    	if(stuList!=null && stuList.size()>0){
    		for(E e:stuList){
    			if(BaseConstant.LEAVESIGN_OUT==e.getLeavesign()){
        			e.setStuname(e.getStuname()+"(离校)");
        		}
    			list_.add(e);
        	}
    	}
        return list_;
    }

    public int getStudentCount(String classId) {
        return getStudentDao().getStudentCount(classId);
    }
    
    public String getIdentityTypeCard(String stuId,String type){
    	return getStudentDao().getIdentityTypeCard(stuId, type);
    }
    
    public List<E> getStudentsOrderByName(String classId){
    	return getStudentDao().getStudentsOrderByName(classId);
    }
    
    public List<String> filterStudentIdsByClassId(String classId,
			String[] studentIds){
    	return getStudentDao().filterStudentIdsByClassId(classId, studentIds);
    }
    
    public int getStudentCount(String[] classIds) {
        return getStudentDao().getStudentCount(classIds);
    }

    public boolean isExistsStuByDistrict(String schdistriId) {
        return getStudentDao().isExistsStuByDistrict(schdistriId);
    }

    public Map<String, Integer> getStudentCountMap(String[] classIds) {
        return getStudentDao().getStudentCountMap(classIds);
    }
    
    public Map<String, Integer> getStudentSexCountMap(String[] classIds){
    	return getStudentDao().getStudentSexCountMap(classIds);
    }
    
    public Map<String, Integer> getUnderSchoolNumUseTypeMap(String unitId,
    		String unionCode) {
    	return getStudentDao().getUnderSchoolNumUseTypeMap(unitId,unionCode);
    }
    
    public Map<String, Integer> getUnderSchoolNumXSLBMap(String unitId,
    		String unionCode) {
    	return getStudentDao().getUnderSchoolNumXSLBMap(unitId,unionCode);
    }
    
    public Map<String, Integer> getUnderSchoolNumGradeCodeMap(String unionCode)  {
    	return getStudentDao().getUnderSchoolNumGradeCodeMap(unionCode);
    }
    
    public Map<String, Integer> getUnderSchoolNumHkMap(String unitId,
    		String unionCode) {
    	return getStudentDao().getUnderSchoolNumHkMap(unitId,unionCode);
    }
    
    public Map<String, List<E>> getClassStudentMap(String[] classIds) {
        Map<String, List<E>> classStuMap = new HashMap<String, List<E>>();
        List<E> classStuList = getStudentDao().getStudents(classIds);
        for (E stuInfo : classStuList) {
            if (classStuMap.containsKey(stuInfo.getClassid())) {
                List<E> stuList = classStuMap.get(stuInfo.getClassid());
                stuList.add(stuInfo);
            } else {
                List<E> stuList = new ArrayList<E>();
                stuList.add(stuInfo);
                classStuMap.put(stuInfo.getClassid(), stuList);
            }
        }
        return classStuMap;
    }

    // =====================返回Map=============================

    public Map<String, E> getStudentMapByUnitiveCodes(
            String[] unitiveCodes) {
        return getStudentDao().getStudentMapByUnitiveCodes(unitiveCodes);
    }

    public Map<String, E> getStudentMap(String[] studentIds) {
    	Map<String, E> map=new HashMap<String, E>();
    	Map<String, E> eMap=getStudentDao().getStudentMap(studentIds);
    	for(String key:eMap.keySet()){
    		E e= eMap.get(key);
    		if(BaseConstant.LEAVESIGN_OUT==e.getLeavesign()){
    			e.setStuname(e.getStuname()+"(离校)");
    		}
    		map.put(key, e);
    	}
        return map;
    }

    public Map<String, E> getStudentMapBySchoolId(String schoolId) {
    	Map<String, E> map=new HashMap<String, E>();
    	Map<String, E> eMap=getStudentDao().getStudentMapBySchoolId(schoolId);
    	for(String key:eMap.keySet()){
    		E e= eMap.get(key);
    		if(BaseConstant.LEAVESIGN_OUT==e.getLeavesign()){
    			e.setStuname(e.getStuname()+"(离校)");
    		}
    		map.put(key, e);
    	}
        return map;
    }
	
	@Override
	public List<E> getStudentsForState(String classId) {
		return getStudentDao().getStudentsForState(classId);
	}

	@Override
	public List<E> getStudentsByIdsWithDeleted(String[] studentIds) {
		return getStudentDao().getStudentsByIdsWithDeleted(studentIds);
	}

	@Override
	public Map<String, E> getStudentMapWithDeleted(String[] studentIds) {
		return getStudentDao().getStudentMapWithDeleted(studentIds);
	}

	@Override
	public Map<String, Integer> getSchIdStuNumMapByKinClass(String artScienceType, String gradeCode, String curAcadyear){
		return getStudentDao().getSchIdStuNumMapByKinClass(artScienceType,gradeCode, curAcadyear);
	}
	
	public Map<String,Integer> getBackgrouStuNumMapByClaIds(String[] claIds){
		return getStudentDao().getBackgrouStuNumMapByClaIds(claIds);
	}
}
