package net.zdsoft.eis.base.data.sync.schsecurity.dao;

import java.util.List;

import net.zdsoft.eis.base.data.sync.schsecurity.entity.TYSFYHB;

/**
 * 同步统一身份用户表日志 
 * @author 
 * 
 */
public interface SyncTYSFYHBLogDao {
	/**
	 * 新增同步统一身份用户表日志
	 * @param syncTysfyhbLog
	 * @return"
	 */
	public TYSFYHB save(TYSFYHB syncTysfyhbLog);
	
	/**
	 * 更新同步统一身份用户表日志
	 * @param syncTysfyhbLog
	 * @return
	 */
	public Integer update(TYSFYHB syncTysfyhbLog);
	
	/**
	 * 根据id获取同步统一身份用户表日志;
	 * @param yhid);
	 * @return
	 */
	public TYSFYHB getSyncTysfyhbLogById(String id);
	
	/**
	 * 根据同步结果获取记录
	 * @param syncResults
	 * @return
	 */
	public List<TYSFYHB> getListByResults(String[] syncResults);
	
	/**
	 * 获取最新版本号
	 * @return
	 */
	public int getLastVersion();
	
	/**
	 * 关闭以前版本中相同id的失败记录
	 * @param yhid
	 */
	public void closeFailRecord(String yhid);
}
