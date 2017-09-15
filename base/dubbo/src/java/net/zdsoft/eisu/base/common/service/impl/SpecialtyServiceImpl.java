/* 
 * @(#)SpecialtyServiceImpl.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.eisu.base.common.dao.SpecialtyDao;
import net.zdsoft.eisu.base.common.entity.Specialty;
import net.zdsoft.eisu.base.common.service.InstituteService;
import net.zdsoft.eisu.base.common.service.SpecialtyService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 4:55:32 PM $
 */
public class SpecialtyServiceImpl implements SpecialtyService {
    private SpecialtyDao specialtyDao;
    private InstituteService instituteService;

    public void setInstituteService(InstituteService instituteService) {
        this.instituteService = instituteService;
    }

    public void setSpecialtyDao(SpecialtyDao specialtyDao) {
        this.specialtyDao = specialtyDao;
    }

    public Specialty getSpecialty(String specialtyId) {
        return specialtyDao.getSpecialty(specialtyId);
    }

    public Map<String, Specialty> getSpecialtyMap(String[] specialtyIds) {
        return specialtyDao.getSpecialtyMap(specialtyIds);
    }

    public List<Specialty> getSpecialtysByParent(String parentId, int parentType) {
        return specialtyDao.getSpecialtysByParent(parentId, parentType);
    }
    public List<Specialty> getSpecialtysByParent(String parentId, int parentType,boolean isShow) {
    	return specialtyDao.getSpecialtysByParent(parentId, parentType,isShow);
    }

    public String[] getSpecialtyIdsByParent(String parentId, int parentType) {
        List<String> list = specialtyDao.getSpecialtyIdsByParent(new String[] { parentId },
                parentType);
        return list.toArray(new String[0]);
    }
    
    public List<Specialty> getAllSpecialtysByParent(String parentId, int parentType) {
        List<Specialty> list = null;
        if (Specialty.PARENT_SCHOOL == parentType) {
            list = getSpecialtysByUnitId(parentId);
        } else {
            String[] instituteIds = instituteService.getAllInstituteIdsByParent(parentId,
                    parentType, true);

            list = specialtyDao.getSpecialtysByParent(instituteIds, Specialty.PARENT_INSTITUTE);

        }
        return list;
    }
    public List<Specialty> getAllSpecialtysByParent(String parentId, int parentType,boolean isShow) {
    	List<Specialty> list = null;
    	if (Specialty.PARENT_SCHOOL == parentType) {
//    		list = getSpecialtysByUnitId(parentId);
    		list = specialtyDao.getSpecialtysByUnitId(parentId,isShow);
    	} else {
    		String[] instituteIds = instituteService.getAllInstituteIdsByParent(parentId,
    				parentType, true);
    		
    		list = specialtyDao.getSpecialtysByParent(instituteIds, Specialty.PARENT_INSTITUTE,isShow);
    		
    	}
    	return list;
    }

    public String[] getAllSpecialtyIdsByParent(String parentId, int parentType) {
        String[] specialtyIds = null;
        if (Specialty.PARENT_SCHOOL == parentType) {
            List<Specialty> list = getSpecialtysByUnitId(parentId);
            specialtyIds = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                specialtyIds[i] = list.get(i).getId();
            }
        } else {
            String[] instituteIds = instituteService.getAllInstituteIdsByParent(parentId,
                    parentType, true);

            List<String> list = specialtyDao.getSpecialtyIdsByParent(instituteIds,
                    Specialty.PARENT_INSTITUTE);
            specialtyIds = list.toArray(new String[0]);

        }
        return specialtyIds;
    }

    public List<Specialty> getSpecialtysByUnitId(String unitId) {
        return specialtyDao.getSpecialtysByUnitId(unitId);
    }
    public List<Specialty> getSpecialtysByUnitId(String unitId,boolean isShow) {
    	return specialtyDao.getSpecialtysByUnitId(unitId,isShow);
    }

	@Override
	public String[] getAllSpecialtyIdsByParent(String[] parentId, int parentType) {
		String[] specialtyIds = null;
        String[] instituteIds = instituteService.getAllInstituteIdsByParent(parentId,
                parentType, true);

        List<String> list = specialtyDao.getSpecialtyIdsByParent(instituteIds,
                Specialty.PARENT_INSTITUTE);
        specialtyIds = list.toArray(new String[0]);

        return specialtyIds;
	}

	@Override
	public List<Specialty> getSpecialtysByParentSpec(String specId) {
		return specialtyDao.getSpecialtysByParentSpec(specId);
	}

	@Override
	public Map<String, Specialty> getSpecialtymapByUnitId(String unitId,
			boolean isShow) {
		return specialtyDao.getSpecialtymapByUnitId(unitId, isShow);
	}

}
