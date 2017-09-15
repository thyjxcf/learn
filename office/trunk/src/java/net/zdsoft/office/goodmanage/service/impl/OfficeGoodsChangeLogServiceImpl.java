package net.zdsoft.office.goodmanage.service.impl;

import java.util.*;

import net.zdsoft.office.goodmanage.entity.OfficeGoodsChangeLog;
import net.zdsoft.office.goodmanage.service.OfficeGoodsChangeLogService;
import net.zdsoft.office.goodmanage.dao.OfficeGoodsChangeLogDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_goods_change_log
 * @author 
 * 
 */
public class OfficeGoodsChangeLogServiceImpl implements OfficeGoodsChangeLogService{
	private OfficeGoodsChangeLogDao officeGoodsChangeLogDao;

	@Override
	public OfficeGoodsChangeLog save(OfficeGoodsChangeLog officeGoodsChangeLog){
		return officeGoodsChangeLogDao.save(officeGoodsChangeLog);
	}

	@Override
	public Integer delete(String[] ids){
		return officeGoodsChangeLogDao.delete(ids);
	}
	
	@Override
	public Integer deleteByGoodsIds(String[] goodsIds){
		return officeGoodsChangeLogDao.deleteByGoodsIds(goodsIds);
	}

	@Override
	public Integer update(OfficeGoodsChangeLog officeGoodsChangeLog){
		return officeGoodsChangeLogDao.update(officeGoodsChangeLog);
	}

	@Override
	public OfficeGoodsChangeLog getOfficeGoodsChangeLogById(String id){
		return officeGoodsChangeLogDao.getOfficeGoodsChangeLogById(id);
	}

	@Override
	public Map<String, OfficeGoodsChangeLog> getOfficeGoodsChangeLogMapByIds(String[] ids){
		return officeGoodsChangeLogDao.getOfficeGoodsChangeLogMapByIds(ids);
	}

	@Override
	public List<OfficeGoodsChangeLog> getOfficeGoodsChangeLogList(){
		return officeGoodsChangeLogDao.getOfficeGoodsChangeLogList();
	}

	@Override
	public List<OfficeGoodsChangeLog> getOfficeGoodsChangeLogPage(Pagination page){
		return officeGoodsChangeLogDao.getOfficeGoodsChangeLogPage(page);
	}
	
	@Override
	public List<OfficeGoodsChangeLog> getOfficeGoodsChangeLogByGoodsId(String goodsId, Pagination page){
		return officeGoodsChangeLogDao.getOfficeGoodsChangeLogByGoodsId(goodsId, page);
	}

	public void setOfficeGoodsChangeLogDao(OfficeGoodsChangeLogDao officeGoodsChangeLogDao){
		this.officeGoodsChangeLogDao = officeGoodsChangeLogDao;
	}
}	