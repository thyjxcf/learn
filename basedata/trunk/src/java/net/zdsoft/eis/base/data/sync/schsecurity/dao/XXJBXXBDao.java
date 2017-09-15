package net.zdsoft.eis.base.data.sync.schsecurity.dao;

import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.data.sync.schsecurity.entity.XXJBXXB;

public interface XXJBXXBDao {
	
	/**
	 * 
	 * @param id);
	 * @return
	 */
	public XXJBXXB getById(String id);
	
	/**
	 * 根据起始更新时间获取记录
	 * @param beginTime
	 * @return
	 */
	public List<XXJBXXB> getListByGxsj(Date beginTime);
	
	/**
	 * 获取最新更新时间
	 * @return
	 */
	public String getLastGxsj();
}
