package net.zdsoft.keelcnet.util;


import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidDataSourceStatLogger;
import com.alibaba.druid.pool.DruidDataSourceStatLoggerImpl;
import com.alibaba.druid.pool.DruidDataSourceStatValue;

/**
 * durid日志类
 */
public class DruidStatLogger extends DruidDataSourceStatLoggerImpl implements DruidDataSourceStatLogger {
	
    @Override
    public void setLoggerName(String loggerName) {
    	super.setLoggerName(Logger.class.getName());
    }
    
    @Override
    public void log(DruidDataSourceStatValue statValue) {
    	// 调用父类的log，也可以自定义存储日志内容
    	super.log(statValue);
    }

	
}
