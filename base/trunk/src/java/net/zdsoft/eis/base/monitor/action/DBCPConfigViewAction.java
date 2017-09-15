package net.zdsoft.eis.base.monitor.action;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;

import net.zdsoft.eis.frame.action.BaseAction;

public class DBCPConfigViewAction extends BaseAction {
	private static final long serialVersionUID = 3274782796077587183L;
	
	BasicDataSource ds;

	public void setDataSource(BasicDataSource dataSource) {
		this.ds = dataSource;
	}

	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}


	public List<String> getDbcpStatus(){
		List<String> info = new ArrayList<String>();
		info.add("DefaultAutoCommit:"+ds.getDefaultAutoCommit());
		info.add("DefaultTransactionIsolation:"+ds.getDefaultTransactionIsolation());
		
		info.add("MaxActive:"+ds.getMaxActive());
		info.add("MaxIdle:"+ds.getMaxIdle());
		info.add("MaxWait:"+ds.getMaxWait());
		info.add("MinIdle:"+ds.getMinIdle());
		info.add("NumIdle:"+ds.getNumIdle());
		info.add("TestOnBorrow:"+ds.getTestOnBorrow());
		info.add("Url:"+ds.getUrl());
		info.add("ValidationQuery:"+ds.getValidationQuery());
//		info.add("RemoveAbandoned:"+ds.getRemoveAbandoned());
//		info.add("RemoveAbandonedTimeout:"+ds.getRemoveAbandonedTimeout());
//		info.add("LogAbandoned:"+ds.getLogAbandoned());
		info.add("NumActive:"+ds.getNumActive());
		
		return info;
	}
	
//	public void afterPropertiesSet() throws Exception {
//		log.info("===================DBCP setting===================");
//		log.info("DefaultAutoCommit:"+ds.getDefaultAutoCommit());
//		log.info("DefaultTransactionIsolation:"+ds.getDefaultTransactionIsolation());
//		log.info("MaxActive:"+ds.getMaxActive());
//		log.info("MaxIdle:"+ds.getMaxIdle());
//		log.info("MaxWait:"+ds.getMaxWait());
//		log.info("MinIdle:"+ds.getMinIdle());
//		log.info("NumActive:"+ds.getNumActive());
//		log.info("NumIdle:"+ds.getNumIdle());
//		log.info("TestOnBorrow:"+ds.getTestOnBorrow());
//		log.info("Url:"+ds.getUrl());
//		log.info("ValidationQuery:"+ds.getValidationQuery());
//	}
}
