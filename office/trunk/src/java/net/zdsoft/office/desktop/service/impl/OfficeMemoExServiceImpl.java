package net.zdsoft.office.desktop.service.impl;

import java.util.*;

import net.zdsoft.office.desktop.entity.OfficeMemoEx;
import net.zdsoft.office.desktop.service.OfficeMemoExService;
import net.zdsoft.office.desktop.dao.OfficeMemoExDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_memo_ex
 * @author 
 * 
 */
public class OfficeMemoExServiceImpl implements OfficeMemoExService{
	private OfficeMemoExDao officeMemoExDao;

	@Override
	public OfficeMemoEx save(OfficeMemoEx officeMemoEx){
		return officeMemoExDao.save(officeMemoEx);
	}

	@Override
	public void allSave(List<OfficeMemoEx> officeMemoExs) {
		 officeMemoExDao.allSave(officeMemoExs);
	}

	@Override
	public Integer delete(String[] ids){
		return officeMemoExDao.delete(ids);
	}

	@Override
	public void deletebyMemoId(String[] memoId) {
		officeMemoExDao.deletebyMemoId(memoId);
	}

	@Override
	public Integer update(OfficeMemoEx officeMemoEx){
		return officeMemoExDao.update(officeMemoEx);
	}

	@Override
	public OfficeMemoEx getOfficeMemoExById(String id){
		return officeMemoExDao.getOfficeMemoExById(id);
	}

	@Override
	public Map<String, OfficeMemoEx> getOfficeMemoExMapByIds(String[] ids){
		return officeMemoExDao.getOfficeMemoExMapByIds(ids);
	}

	@Override
	public List<OfficeMemoEx> getOfficeMemoExList(){
		return officeMemoExDao.getOfficeMemoExList();
	}

	@Override
	public List<OfficeMemoEx> getOfficeMemoExPage(Pagination page){
		return officeMemoExDao.getOfficeMemoExPage(page);
	}

	@Override
	public List<OfficeMemoEx> getOfficeMemoExListByMemoId(String memoId) {
		return officeMemoExDao.getOfficeMemoExListByMemoId(memoId);
	}

	public void setOfficeMemoExDao(OfficeMemoExDao officeMemoExDao){
		this.officeMemoExDao = officeMemoExDao;
	}
}
