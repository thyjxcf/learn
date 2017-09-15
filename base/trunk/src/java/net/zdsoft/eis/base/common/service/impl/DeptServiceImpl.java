package net.zdsoft.eis.base.common.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.dao.DeptDao;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UnitService;

import org.apache.commons.lang.StringUtils;

public class DeptServiceImpl implements DeptService {

	private UnitService unitService;
    private DeptDao deptDao;

    public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setDeptDao(DeptDao deptDao) {
        this.deptDao = deptDao;
    }

    public Dept getDept(String deptId) {
        return deptDao.getDept(deptId);
    }
    
    @Override
    public Dept getDeptByName(String unitId, String deptName) {
    	return deptDao.getDeptByName(unitId, deptName);
    }
    
    public List<Dept> getDepts(String unitId, String parentId) {
        return deptDao.getDepts(unitId, parentId);
    }

    public List<Dept> getDirectDepts(String unitId) {
        return deptDao.getDirectDepts(unitId);
    }

    public List<Dept> getDepts(String unitId) {
        return deptDao.getDepts(unitId);
    }
    
    public List<Dept> getDeptsByDeptId(String deptId, String deptName){
    	return deptDao.getDeptsByDeptId(deptId, deptName);
    }
    
    public List<Dept> getDeptsByParentId(String parentId) {
        return deptDao.getDeptsByParentId(parentId);
    }

    public List<Dept> getDirectDeptsByInstituteId(String instituteId) {
        return deptDao.getDirectDeptsByInstituteId(instituteId);
    }
    
    public Map<String, Dept> getDeptMap(String[] deptIds) {
        return deptDao.getDeptMap(deptIds);
    }

    public Map<String, Dept> getDeptMap(String unitId) {
        return deptDao.getDeptMap(unitId);
    }

	public List<Dept> getDepts(String unitId, String parentId, String deptName) {
		if(StringUtils.isBlank(deptName)){
			return deptDao.getDepts(unitId, parentId);
		}else{
			return deptDao.getDepts(unitId, parentId, deptName);
		}
	}

	public List<Dept> getDeptsByParentId(String parentId, String deptName) {
		if(StringUtils.isBlank(deptName)){
			return deptDao.getDeptsByParentId(parentId);
		}else{
			return deptDao.getDeptsByParentId(parentId, deptName);
		}
	}

	@Override
	public List<Dept> getDirectDepts(String unitId, String deptName) {
		if(StringUtils.isBlank(deptName)){
			return deptDao.getDirectDepts(unitId);
		}else{
			return deptDao.getDirectDepts(unitId, deptName);
		}
	}

	@Override
	public List<Dept> getDirectDepts(String unitId, int deptType,
			String deptName) {
		
		return deptDao.getDirectDepts(unitId, deptType, deptName);
	}

	@Override
	public String isTeacherGroupHead(String teacherId) {
		return deptDao.isTeacherGroupHead(teacherId);
	}

	@Override
	public String isPrincipanGroupHead(String userId) {
		return deptDao.isPrincipanGroupHead(userId);
	}
	
	@Override
	public boolean isDeputyHead(String unitId, String userId) {
		return deptDao.isDeputyHead(unitId,userId);
	}
	
	@Override
	public List<Dept> DeputyHead(String unitId, String userId) {
		return deptDao.getDeputyHead(unitId, userId);
	}

	@Override
	public boolean isLeader(String unitId, String userId) {
		return deptDao.isLeader(unitId,userId);
	}
	
	@Override
	public List<Dept> getDeptsByAreaId(String areaId) {
		return deptDao.getDeptsByAreaId(areaId);
	}
	
	@Override
	public String getDeptDetailNamesStr(String[] deptIds) {
		StringBuffer deptNames = new StringBuffer();
		Map<String, Dept> deptMap = deptDao.getDeptMap(deptIds);
		Set<String> unitIdSet = new HashSet<String>();
		for(Map.Entry<String, Dept> entry:deptMap.entrySet()){
			unitIdSet.add(entry.getValue().getUnitId());
		}
		Map<String, Unit> unitMap = unitService.getUnitMap(unitIdSet.toArray(new String[0]));
		for(String deptId:deptIds){
			Dept dept = deptMap.get(deptId);
			String deptName = "";
			if(dept!=null){
				Unit unit = unitMap.get(dept.getUnitId());
				deptName = dept.getDeptname()+"("+unit.getName()+")";
			}else {
				deptName = "部门已删除";
			}
			if(deptNames.length()==0){
				deptNames.append(deptName);
			}else{
				deptNames.append(","+deptName);
			}
		}
		return deptNames.toString();
	}
	
	@Override
	public List<Dept> getDeptList(String[] deptIds) {
		return deptDao.getDeptList(deptIds);
	}
}
