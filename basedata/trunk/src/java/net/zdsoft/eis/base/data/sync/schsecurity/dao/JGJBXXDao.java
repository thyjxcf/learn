package net.zdsoft.eis.base.data.sync.schsecurity.dao;

import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.data.sync.schsecurity.entity.JGJBXX;

public interface JGJBXXDao {

	/**
	 * 
	 * @param id);
	 * @return
	 */
	public JGJBXX getById(String id);
	
	/**
	 * 根据起始更新时间获取记录
	 * @param beginTime
	 * @return
	 */
	public List<JGJBXX> getListByGxsj(Date beginTime);
	
	/**
	 * 获取最新更新时间
	 * @return
	 */
	public String getLastGxsj();
}
