package net.zdsoft.eis.base.common.service.impl;

import java.util.List;

import net.zdsoft.eis.base.common.dao.ColsDisplayDao;
import net.zdsoft.eis.base.common.entity.ColsDisplay;
import net.zdsoft.eis.base.common.service.ColsDisplayService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.constant.BaseConstant;

public class ColsDisplayServiceImpl implements ColsDisplayService {

    protected static final String DEFAULT_SCHID = BaseConstant.ZERO_GUID; // 
    private static final String CONFIGE_TYPE_STUDENT_AUDIT = "studentaudit";
    
    private ColsDisplayDao colsDisplayDao;
    private UnitService unitService;

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public void setColsDisplayDao(ColsDisplayDao colsDisplayDao) {
        this.colsDisplayDao = colsDisplayDao;
    }

    public List<ColsDisplay> getColsDisplays(String unitId, String type) {
        return colsDisplayDao.getColsDisplays(unitId, type);
    }
    
    @Override
    public List<ColsDisplay> getColsDisplays(String unitId, String[] type) {
    	return colsDisplayDao.getColsDisplays(unitId, type);
    }

    public List<ColsDisplay> getColsDisplays(String unitId, String type, Integer colsUse) {
        List<ColsDisplay> colsSchTypeList = this.getColsDisplays(unitId, type);
        if (colsSchTypeList == null || colsSchTypeList.size() == 0) {
            unitId = CONFIGE_TYPE_STUDENT_AUDIT.equals(type) ? unitService
                    .getTopEdu().getId() : DEFAULT_SCHID;
        }

        List<ColsDisplay> columnsList = colsDisplayDao.getColsDisplays(unitId, type, colsUse);
        // 如果修改的列字段还为空，则到默认的
        // if((columnsList==null || columnsList.size()==0) &&
        // !DEFAULT_SCHID.equals(unitId)){
        // unitId = DEFAULT_SCHID;
        // columnsList = colsDisplayDao.getColsList(unitId, type, colsUse);
        // }

        return columnsList;
    }
    
    @Override
    public List<ColsDisplay> getColsDisplays(String unitId, String[] type,
    		Integer colsUse) {
    	 List<ColsDisplay> colsSchTypeList = this.getColsDisplays(unitId, type);
         if (colsSchTypeList == null || colsSchTypeList.size() == 0) {
             unitId = CONFIGE_TYPE_STUDENT_AUDIT.equals(type) ? unitService
                     .getTopEdu().getId() : DEFAULT_SCHID;
         }

         List<ColsDisplay> columnsList = colsDisplayDao.getColsDisplays(unitId, type, colsUse);
         // 如果修改的列字段还为空，则到默认的
         // if((columnsList==null || columnsList.size()==0) &&
         // !DEFAULT_SCHID.equals(unitId)){
         // unitId = DEFAULT_SCHID;
         // columnsList = colsDisplayDao.getColsList(unitId, type, colsUse);
         // }

         return columnsList;
    }

    public List<ColsDisplay> getColsWithoutDefault(String unitId, String type, Integer colsUse) {
        List<ColsDisplay> colsSchTypeList = this.getColsDisplays(unitId, type);
        if (colsSchTypeList == null || colsSchTypeList.size() == 0) {
            unitId = CONFIGE_TYPE_STUDENT_AUDIT.equals(type) ? unitService
                    .getTopEdu().getId() : DEFAULT_SCHID;
        }
        return colsDisplayDao.getColsDisplays(unitId, type, colsUse);
    }

}
