package net.zdsoft.office.dailyoffice.service.impl;import java.util.List;import java.util.Map;import net.zdsoft.keel.util.Pagination;import net.zdsoft.office.dailyoffice.dao.OfficeLogDao;import net.zdsoft.office.dailyoffice.entity.OfficeLog;import net.zdsoft.office.dailyoffice.service.OfficeLogService;/** * office_log  * @author  *  *//** * office_log  * @author  *  */public class OfficeLogServiceImpl implements OfficeLogService{	private OfficeLogDao officeLogDao;		/**	 * 新增office_log	 * @param officeLog	 * @return	 */	@Override	public OfficeLog save(OfficeLog officeLog){		return officeLogDao.save(officeLog);	}	/**	 * 根据unitId userId获取office_log数据	 * @param unitId userId	 * @return	 */	@Override	public List<OfficeLog> getOfficeList(String unitId,String userId,String modId,String code){		return officeLogDao.getOfficeList(unitId,userId,modId,code);	}	/**	 * 根据ids数组删除office_log数据	 * @param ids	 * @return	 */	@Override	public Integer delete(String[] ids){		return officeLogDao.delete(ids);	}		/**	 * 更新office_log	 * @param officeLog	 * @return	 */	@Override	public Integer update(OfficeLog officeLog){		return officeLogDao.update(officeLog);	}		/**	 * 根据id获取office_log	 * @param id	 * @return	 */	@Override	public OfficeLog getOfficeLogById(String id){		return officeLogDao.getOfficeLogById(id);	}		/**	 * 根据ids数组查询office_logmap	 * @param ids	 * @return	 */	@Override	public Map<String, OfficeLog> getOfficeLogMapByIds(String[] ids){		return officeLogDao.getOfficeLogMapByIds(ids);	}		/**	 * 获取office_log列表	 * @return	 */	@Override	public List<OfficeLog> getOfficeLogList(){		return officeLogDao.getOfficeLogList();	}		/**	 * 分页获取office_log列表	 * @param page	 * @return	 */	@Override	public List<OfficeLog> getOfficeLogPage(Pagination page){		return officeLogDao.getOfficeLogPage(page);	}	

	@Override
	public List<OfficeLog> getOfficeLogByUnitIdList(String unitId){
		return officeLogDao.getOfficeLogByUnitIdList(unitId);
	}

	@Override
	public List<OfficeLog> getOfficeLogByUnitIdPage(String unitId, Pagination page){
		return officeLogDao.getOfficeLogByUnitIdPage(unitId, page);
	}		public void setOfficeLogDao(OfficeLogDao officeLogDao){		this.officeLogDao = officeLogDao;	}}

