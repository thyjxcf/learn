/* 
 * @(#)InstituteServiceImpl.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eisu.base.common.dao.InstituteDao;
import net.zdsoft.eisu.base.common.entity.EisuStudent;
import net.zdsoft.eisu.base.common.entity.Institute;
import net.zdsoft.eisu.base.common.entity.Specialty;
import net.zdsoft.eisu.base.common.service.EisuStudentService;
import net.zdsoft.eisu.base.common.service.InstituteService;
import net.zdsoft.eisu.base.common.service.SpecialtyService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 7:31:13 PM $
 */
public class InstituteServiceImpl implements InstituteService {
    private InstituteDao instituteDao;
    private SpecialtyService specialtyService;
    private EisuStudentService eisuStudentService;

    public void setSpecialtyService(SpecialtyService specialtyService) {
		this.specialtyService = specialtyService;
	}

	public void setEisuStudentService(EisuStudentService eisuStudentService) {
		this.eisuStudentService = eisuStudentService;
	}

	public void setInstituteDao(InstituteDao instituteDao) {
        this.instituteDao = instituteDao;
    }

    @Override
	public Institute getInstituteByStudentId(String studentId) {
    	if(StringUtils.isNotBlank(studentId)){
    		EisuStudent student = eisuStudentService.getStudent(studentId);
    		if(student != null && StringUtils.isNotBlank(student.getSpecId())){
    			Specialty spec = specialtyService.getSpecialty(student.getSpecId());
    			if(spec != null && StringUtils.isNotBlank(spec.getParentId())){
    				return getInstitute(spec.getParentId());
    			}
    		}
    	}
		return null;
	}

	public Institute getInstitute(String instituteId) {
        return instituteDao.getInstitute(instituteId);
    }

    public Map<String, Institute> getInstituteMap(String[] instituteIds) {
        return instituteDao.getInstituteMap(instituteIds);
    }

    public List<Institute> getInstitutesByParent(String parentId, int parentType) {
        return instituteDao.getInstitutesByParent(parentId, parentType);
    }

    public List<Institute> getAllInstitutesByParent(String parentId, int parentType,
            boolean isContainSelf) {
        List<Institute> list = null;
        if (Institute.PARENT_SCHOOL == parentType) {
            list = getInstitutesByUnitId(parentId);
        } else {
            list = new ArrayList<Institute>();
            if (isContainSelf) {
                Institute institute = getInstitute(parentId);
                list.add(institute);
            }
            getAllInstitutesByInstituteId(list, parentId);

            // 排序
            Collections.sort(list, new Comparator<Institute>() {

                @Override
                public int compare(Institute o1, Institute o2) {
                    return o1.getInstituteCode().compareTo(o2.getInstituteCode());
                }
            });
        }

        return list;
    }

    public String[] getAllInstituteIdsByParent(String parentId, int parentType,
            boolean isContainSelf) {
        List<Institute> institutes = getAllInstitutesByParent(parentId, parentType, isContainSelf);
        Set<String> instituteIds = new HashSet<String>();
        for (Institute institute : institutes) {
            instituteIds.add(institute.getId());
        }

        return instituteIds.toArray(new String[0]);
    }

    private void getAllInstitutesByInstituteId(List<Institute> list, String parentId) {
        List<Institute> institues = getInstitutesByParent(parentId, Institute.PARENT_INSTITUTE);
        if (institues.size() > 0) {
            list.addAll(institues);
            for (Institute institute : institues) {
                getAllInstitutesByInstituteId(list, institute.getId());
            }
        }
    }

    public List<Institute> getInstitutesByParent(String parentId, int parentType, int instituteKind) {
        if (instituteKind == 0) {
            return getInstitutesByParent(parentId, parentType);
        } else {
            return instituteDao.getInstitutesByParent(parentId, parentType, instituteKind);
        }
    }
    public List<Institute> getInstitutesByParent(String parentId, int parentType, int instituteKind,boolean isShow) {
    	if (instituteKind == 0) {
    		return instituteDao.getInstitutesByParent(parentId, parentType,isShow);
    	} else {
    		return instituteDao.getInstitutesByParent(parentId, parentType, instituteKind,isShow);
    	}
    }

    public List<Institute> getInstitutesByUnitId(String unitId) {
        return instituteDao.getInstitutesByUnitId(unitId);
    }

	@Override
	public Map<String, Institute> getMapByUnitId(String unitId) {
		return instituteDao.getMapByUnitId(unitId);
	}

	@Override
	public String[] getAllInstituteIdsByParent(String[] parentId,
			int parentType, boolean isContainSelf) {
		 List<Institute> institutes = getAllInstitutesByParent(parentId, parentType, isContainSelf);
	        Set<String> instituteIds = new HashSet<String>();
	        for (Institute institute : institutes) {
	            instituteIds.add(institute.getId());
	        }

	        return instituteIds.toArray(new String[0]);
	}

	@Override
	public List<Institute> getAllInstitutesByParent(String[] parentId,
			int parentType, boolean isContainSelf) {
		List<Institute> list = null;
        list = new ArrayList<Institute>();
        if (isContainSelf) {
            Map<String,Institute> instituteMap = getInstituteMap(parentId);
            for(String id : instituteMap.keySet()){
            	list.add(instituteMap.get(id));
            }
        }
        getAllInstitutesByInstituteId(list, parentId);
        return list;
	}
	private void getAllInstitutesByInstituteId(List<Institute> list, String[] parentId) {
        List<Institute> institues = getInstitutesByParent(parentId, Institute.PARENT_INSTITUTE);
        if (institues.size() > 0) {
            list.addAll(institues);
            for (Institute institute : institues) {
                getAllInstitutesByInstituteId(list, institute.getId());
            }
        }
    }

	@Override
	public List<Institute> getInstitutesByParent(String[] parentId,
			int parentType) {
		 return instituteDao.getInstitutesByParent(parentId, parentType);
	}

	@Override
	public List<Institute> getInstituteList(String[] instituteIds, Integer state) {
		return instituteDao.getInstituteList(instituteIds, state);
	}

}
