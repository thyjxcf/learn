package net.zdsoft.eis.base.data.sync.schsecurity.service;

public interface SyncJBService {
	
	/**
	 * 同步数据（获取原始数据）
	 */
	public void syncData();
	
	/**
	 * 处理数据（把原始数据转换成我们系统的数据）
	 */
	public void handleData();
}
