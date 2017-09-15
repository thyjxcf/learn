package net.zdsoft.eis.base.data.sync.schsecurity.dao;

import java.util.List;

import net.zdsoft.eis.base.data.sync.schsecurity.entity.TYSFYHB;
import net.zdsoft.eis.base.data.sync.schsecurity.entity.XXJBXXB;

/**
 * 学校基本信息表 
 * @author 
 * 
 */
public interface SyncXXJBXXBLogDao {
	/**
	 * 新增学校基本信息表
	 * @param syncXxjbxxbLog
	 * @return"
	 */
	public XXJBXXB save(XXJBXXB syncXxjbxxbLog);
	
	/**
	 * 更新学校基本信息表
	 * @param syncXxjbxxbLog
	 * @return
	 */
	public Integer update(XXJBXXB syncXxjbxxbLog);
	
	/**
	 * 根据id获取学校基本信息表;
	 * @param id);
	 * @return
	 */
	public XXJBXXB getSyncXxjbxxbLogById(String id);
	
	/**
	 * 根据同步结果获取记录
	 * @param syncResults
	 * @return
	 */
	public List<XXJBXXB> getListByResults(String[] syncResults);
	
	/**
	 * 获取最新版本号
	 * @return
	 */
	public int getLastVersion();
	
	/**
	 * 关闭以前版本中相同id的失败记录
	 * @param yhid
	 */
	public void closeFailRecord(String xxid);
		
}
