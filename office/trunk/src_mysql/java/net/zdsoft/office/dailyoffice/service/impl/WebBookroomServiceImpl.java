package net.zdsoft.office.dailyoffice.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.WebBookroomDao;
import net.zdsoft.office.dailyoffice.entity.WebBookroom;
import net.zdsoft.office.dailyoffice.service.WebBookroomService;
/**
 * web_bookroom
 * @author 
 * 
 */
public class WebBookroomServiceImpl implements WebBookroomService{
	private WebBookroomDao webBookroomDao;

	@Override
	public WebBookroom save(WebBookroom webBookroom){
		return webBookroomDao.save(webBookroom);
	}
	
	@Override
	public void batchSave(List<WebBookroom> webBookrooms) {
		webBookroomDao.batchSave(webBookrooms);
	}
	
	@Override
	public void batchUpdate(List<WebBookroom> webBookrooms) {
		webBookroomDao.batchUpdate(webBookrooms);
	}

	@Override
	public Integer delete(String[] ids){
		return webBookroomDao.delete(ids);
	}

	@Override
	public Integer update(WebBookroom webBookroom){
		return webBookroomDao.update(webBookroom);
	}

	@Override
	public WebBookroom getWebBookroomById(String id){
		return webBookroomDao.getWebBookroomById(id);
	}

	@Override
	public Map<String, WebBookroom> getWebBookroomMapByIds(String[] ids){
		return webBookroomDao.getWebBookroomMapByIds(ids);
	}

	@Override
	public List<WebBookroom> getWebBookroomList(){
		return webBookroomDao.getWebBookroomList();
	}

	@Override
	public List<WebBookroom> getWebBookroomPage(Pagination page){
		return webBookroomDao.getWebBookroomPage(page);
	}

	public void setWebBookroomDao(WebBookroomDao webBookroomDao){
		this.webBookroomDao = webBookroomDao;
	}
}