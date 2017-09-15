package net.zdsoft.eis.base.data.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import net.zdsoft.eis.base.common.entity.ColsDisplay;
import net.zdsoft.eis.base.common.service.impl.ColsDisplayServiceImpl;
import net.zdsoft.eis.base.data.dao.BaseColsDisplayDao;
import net.zdsoft.eis.base.data.service.BaseColsDisplayService;

/**
 * @author yanb
 * 
 */
public class BaseColsDisplayServiceImpl extends ColsDisplayServiceImpl
		implements BaseColsDisplayService {
	private BaseColsDisplayDao baseColsDisplayDao;

	public void setBaseColsDisplayDao(BaseColsDisplayDao baseColsDisplayDao) {
		this.baseColsDisplayDao = baseColsDisplayDao;
	}

	// 两种情况， 一种是已保存有学校设置，Update更新即可，
	// 一种要New添加学校设置的记录（根据schGUID）
	public void saveColsDisplay(String schID, String schGUID,
			String columnsIds, String type) {
		List<ColsDisplay> list = new ArrayList<ColsDisplay>();
		String colsID;
		Integer requireDisplay = 0; // 1表示启用，0不启用

		// 折分已设置id
		String[] adjustRowsId = columnsIds.split("\\$");
		ColsDisplay colsEntity;
		List<ColsDisplay> listOfAllCols = this.getColsDisplays(schGUID, type);
		if (listOfAllCols == null || listOfAllCols.size() <= 0) {
			schID = DEFAULT_SCHID;
			if(type.equals(ColsDisplay.COLSTYPE_TEACHER)){
				listOfAllCols = this.getColsDisplays(DEFAULT_SCHID, new String[]{ColsDisplay.COLSTYPE_TEACHER, ColsDisplay.COLSTYPE_TEACHER_EX});
			}else{
				listOfAllCols = this.getColsDisplays(DEFAULT_SCHID, type);
			}
		}

		for (int i = 0; i < listOfAllCols.size(); i++) { // 现所有记录设置一遍
			colsEntity = listOfAllCols.get(i);
			colsID = colsEntity.getId();
			if (colsEntity.getColsType().equals(type)) {
				if (ArrayUtils.contains(adjustRowsId, colsID)) {
					requireDisplay = 1;// 1表示启用，0不启用
				} else {
					requireDisplay = 0;
				}
				colsEntity.setColsUse(requireDisplay);
			}
			if (schID.equals(DEFAULT_SCHID)) { // 未有记录，要新增设置
				colsEntity.setId(null);
				colsEntity.setUnitId(schGUID);
			}
			list.add(colsEntity);
		}
		if (schID.equals(DEFAULT_SCHID)) {// 未有记录，批量新增
			baseColsDisplayDao.batchInsertColsDisplay(list);
		} else {//已有记录，批量更新
			baseColsDisplayDao.batchUpdateColsDisplay(list);
		}
	}
}
