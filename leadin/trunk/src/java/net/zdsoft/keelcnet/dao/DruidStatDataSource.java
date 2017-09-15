package net.zdsoft.keelcnet.dao;

import java.sql.SQLException;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidStatDataSource extends DruidDataSource{

	private static final long serialVersionUID = 1L;

	public DruidStatDataSource() {
	}
	
    public void setDatabaseProperties(Properties properties) throws SQLException {
		String value;
		setDriverClassName(properties.getProperty("hibernate.connection.driver_class"));
		
		// 基本属性 url、user、password
		setUrl(properties.getProperty("hibernate.connection.url"));
		setUsername(properties.getProperty("hibernate.connection.username"));
		setPassword(properties.getProperty("hibernate.connection.password"));
		
		// 配置初始化大小、最小、最大
		value = properties.getProperty("hibernate.dbcp.initialSize");
		if(null!=value && !value.trim().equals("")){setInitialSize(Integer.parseInt(value));}
		value = properties.getProperty("hibernate.dbcp.minIdle");
		if(null!=value && !value.trim().equals("")){setMinIdle(Integer.parseInt(value));}
		value = properties.getProperty("hibernate.dbcp.maxActive");
		if(null!=value && !value.trim().equals("")){setMaxActive(Integer.parseInt(value));}
		
		// 配置获取连接等待超时的时间 
		value = properties.getProperty("hibernate.dbcp.maxWait");
		if(null!=value && !value.trim().equals("")){setMaxWait(Integer.parseInt(value));}
		
		// 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
		value = properties.getProperty("hibernate.dbcp.timeBetweenEvictionRunsMillis");
		if(null!=value && !value.trim().equals("")){setTimeBetweenEvictionRunsMillis(Integer.parseInt(value));}
		
		// 配置一个连接在池中最小生存的时间，单位是毫秒
		value = properties.getProperty("hibernate.dbcp.minEvictableIdleTimeMillis");
		if(null!=value && !value.trim().equals("")){setMinEvictableIdleTimeMillis(Integer.parseInt(value));}
		
		value = properties.getProperty("hibernate.dbcp.autoCommit");
		if(null!=value && !value.trim().equals("")){setDefaultAutoCommit(Boolean.parseBoolean(value));}
		
		value = properties.getProperty("hibernate.dbcp.validationQuery");
		if(null!=value && !value.trim().equals("")){setValidationQuery(value);}
		
		value = properties.getProperty("hibernate.dbcp.testOnBorrow");
		if(null!=value && !value.trim().equals("")){setTestOnBorrow(Boolean.parseBoolean(value));}
		
		value = properties.getProperty("hibernate.dbcp.testOnReturn");
		if(null!=value && !value.trim().equals("")){setTestOnReturn(Boolean.parseBoolean(value));}
		
		value = properties.getProperty("hibernate.dbcp.TestWhileIdle");
		if(null!=value && !value.trim().equals("")){setTestWhileIdle(Boolean.parseBoolean(value));}
		
		// 过时方法 不再使用，一个DruidDataSource只支持一个EvictionRun
//		value = properties.getProperty("hibernate.dbcp.numTestsPerEvictionRun");
//		if(null!=value && !value.trim().equals("")){setNumTestsPerEvictionRun(Integer.parseInt(value));}
		
		// 配置超时的连接销毁
		value = properties.getProperty("hibernate.dbcp.removeAbandoned");
		if(null!=value && !value.trim().equals("")){setRemoveAbandoned(Boolean.parseBoolean(value));}
		value = properties.getProperty("hibernate.dbcp.removeAbandonedTimeout");
		if(null!=value && !value.trim().equals("")){setRemoveAbandonedTimeout(Integer.parseInt(value));}
		value = properties.getProperty("hibernate.dbcp.logAbandoned");
		if(null!=value && !value.trim().equals("")){setLogAbandoned(Boolean.parseBoolean(value));}
		
		// 配置监控统计拦截的filters
		value = properties.getProperty("hibernate.druid.filters");
		if(null!=value && !value.trim().equals("")){setFilters(value);}
		
	}
	
}
