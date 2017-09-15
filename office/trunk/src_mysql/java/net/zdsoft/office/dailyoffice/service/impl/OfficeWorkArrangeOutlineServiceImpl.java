package net.zdsoft.office.dailyoffice.service.impl;/** * office_outline  * @author  *  */import java.util.ArrayList;import java.util.Date;import java.util.List;import java.util.Map;import net.zdsoft.keel.util.Pagination;import net.zdsoft.office.dailyoffice.dao.OfficeWorkArrangeOutlineDao;import net.zdsoft.office.dailyoffice.entity.OfficeWorkArrangeOutline;import net.zdsoft.office.dailyoffice.service.OfficeWorkArrangeContentService;import net.zdsoft.office.dailyoffice.service.OfficeWorkArrangeDetailService;import net.zdsoft.office.dailyoffice.service.OfficeWorkArrangeOutlineService;import org.apache.commons.collections.CollectionUtils;/** * office_outline  * @author  *  */public class OfficeWorkArrangeOutlineServiceImpl implements OfficeWorkArrangeOutlineService{		private OfficeWorkArrangeDetailService officeWorkArrangeDetailService;	private OfficeWorkArrangeContentService officeWorkArrangeContentService;		private OfficeWorkArrangeOutlineDao officeWorkArrangeOutlineDao;		/**	 * @param officeWorkArrangeOutline	 * @return	 */	@Override	public OfficeWorkArrangeOutline save(OfficeWorkArrangeOutline officeWorkArrangeOutline){		return officeWorkArrangeOutlineDao.save(officeWorkArrangeOutline);	}		/**	 * @param ids	 * @return	 */	@Override	public Integer delete(String[] ids){		return officeWorkArrangeOutlineDao.delete(ids);	}		/**	 * @param officeWorkArrangeOutline	 * @return	 */	@Override	public Integer update(OfficeWorkArrangeOutline officeWorkArrangeOutline){		return officeWorkArrangeOutlineDao.update(officeWorkArrangeOutline);	}		@Override	public void updateState(String id, String state) {		officeWorkArrangeDetailService.updateStateByOutLineId(id, state);		officeWorkArrangeContentService.updateStateByOutLineId(id, state);		officeWorkArrangeOutlineDao.updateState(id, state);	}		@Override	public OfficeWorkArrangeOutline getOfficeWorkArrangeOutlineById(String id) {		return officeWorkArrangeOutlineDao.getOfficeWorkArrangeOutlineById(id);	}		@Override	public List<OfficeWorkArrangeOutline> getOfficeWorkArrangeOutlines(String unitId, String year,		  Pagination page) {		List<OfficeWorkArrangeOutline> officeWorkArrangeOutlines = officeWorkArrangeOutlineDao.getOfficeWorkArrangeOutlines(unitId, year, page);		setDetail(officeWorkArrangeOutlines);		return officeWorkArrangeOutlines;	}		@Override	public List<OfficeWorkArrangeOutline> getOfficeWorkArrangeOutlines(			String unitId, String acadyear, String semester, Pagination page) {		List<OfficeWorkArrangeOutline> officeWorkArrangeOutlines =  officeWorkArrangeOutlineDao.getOfficeWorkArrangeOutlines(unitId, acadyear, semester, page);		setDetail(officeWorkArrangeOutlines);		return officeWorkArrangeOutlines;	}		@Override	public List<OfficeWorkArrangeOutline> getOfficeWorkArrangeOutlineList(			String unitId, String acadyear, String semester, String state) {		return officeWorkArrangeOutlineDao.getOfficeWorkArrangeOutlineList(unitId, acadyear, semester, state);	}		@Override	public String getOfficeWorkArrangeOutline(String unitId, String acadyear,			String semester, String state) {		return officeWorkArrangeOutlineDao.getOfficeWorkArrangeOutline(unitId, acadyear, semester, state);	}		@Override	public String getOfficeWorkArrangeOutline(String unitId,			String state) {		return officeWorkArrangeOutlineDao.getOfficeWorkArrangeOutline(unitId, state);	}		@Override	public List<OfficeWorkArrangeOutline> getOfficeWorkArrangeOutlineList(			String unitId, String year, String state) {		return officeWorkArrangeOutlineDao.getOfficeWorkArrangeOutlineList(unitId, year, state);	}		public void setDetail(List<OfficeWorkArrangeOutline> officeWorkArrangeOutlines){		if(CollectionUtils.isNotEmpty(officeWorkArrangeOutlines)){			List<String> outLineIds = new ArrayList<String>();			for(OfficeWorkArrangeOutline officeWorkArrangeOutline:officeWorkArrangeOutlines){				outLineIds.add(officeWorkArrangeOutline.getId());			}			Map<String, String> outLineIdsMap = officeWorkArrangeDetailService.getOutLineIdsMap(outLineIds.toArray(new String[0]));			for(OfficeWorkArrangeOutline officeWorkArrangeOutline:officeWorkArrangeOutlines){				if(outLineIdsMap.containsKey(officeWorkArrangeOutline.getId())){					officeWorkArrangeOutline.setUse(true);				}			}		}	}	    @Override	public OfficeWorkArrangeOutline getOfficeWorkArrangeOutlines(String unitId, Date startTime, Date endTime) {		return officeWorkArrangeOutlineDao.getOfficeWorkArrangeOutlines(unitId, startTime, endTime);	}        @Override    public boolean isExistConflict(String unitId, String id,    		Date startTime, Date endTime) {    	return officeWorkArrangeOutlineDao.isExistConflict(unitId, id, startTime, endTime);    }    	public void setOfficeWorkArrangeDetailService(			OfficeWorkArrangeDetailService officeWorkArrangeDetailService) {		this.officeWorkArrangeDetailService = officeWorkArrangeDetailService;	}	public void setOfficeWorkArrangeContentService(			OfficeWorkArrangeContentService officeWorkArrangeContentService) {		this.officeWorkArrangeContentService = officeWorkArrangeContentService;	}	public void setOfficeWorkArrangeOutlineDao(			OfficeWorkArrangeOutlineDao officeWorkArrangeOutlineDao) {		this.officeWorkArrangeOutlineDao = officeWorkArrangeOutlineDao;	}}
