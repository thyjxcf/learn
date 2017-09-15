package net.zdsoft.eis.base.data.sync.schsecurity.dao;

import java.util.List;

import net.zdsoft.eis.base.data.sync.schsecurity.entity.JGJBXX;
import net.zdsoft.eis.base.data.sync.schsecurity.entity.TYSFYHB;

/**
 * 机构信息表 
 * @author 
 * 
 */
public interface SyncJGJBXXLogDao {
	/**
	 * 新增机构信息表
	 * @param syncJgjbxxbLog
	 * @return"
	 */
	public JGJBXX save(JGJBXX syncJgjbxxbLog);
	
	/**
	 * 更新机构信息表
	 * @param syncJgjbxxbLog
	 * @return
	 */
	public Integer update(JGJBXX syncJgjbxxbLog);
	
	/**
	 * 根据id获取机构信息表;
	 * @param id);
	 * @return
	 */
	public JGJBXX getSyncJgjbxxbLogById(String id);
	
	/**
	 * 根据同步结果获取记录
	 * @param syncResults
	 * @return
	 */
	public List<JGJBXX> getListByResults(String[] syncResults);
	
	/**
	 * 获取最新版本号
	 * @return
	 */
	public int getLastVersion();
	
	/**
	 * 关闭以前版本中相同id的失败记录
	 * @param yhid
	 */
	public void closeFailRecord(String jgid);
		
}
