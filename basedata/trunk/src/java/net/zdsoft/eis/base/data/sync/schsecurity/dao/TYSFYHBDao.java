package net.zdsoft.eis.base.data.sync.schsecurity.dao;

import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.data.sync.schsecurity.entity.TYSFYHB;

public interface TYSFYHBDao {
	
	/**
	 * 根据yhid获取同步统一身份用户表日志;
	 * @param yhid);
	 * @return
	 */
	public TYSFYHB getTysfyhbById(String id);
	
	/**
	 * 根据起始更新时间获取记录
	 * @param beginTime
	 * @return
	 */
	public List<TYSFYHB> getListByGxsj(Date beginTime);
	
	/**
	 * 获取最新更新时间
	 * @return
	 */
	public String getLastGxsj();
	
}
