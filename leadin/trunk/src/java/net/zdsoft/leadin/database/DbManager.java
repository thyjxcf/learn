package net.zdsoft.leadin.database;

import java.sql.Connection;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

import net.zdsoft.keelcnet.config.ContainerManager;

/* 
 * 数据库管理类，通过他可以得到或关闭一个数据库连接对象
 * 在自已写原始的SQL语句（而不用HQL语句）时使用
 * 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: DbManager.java,v 1.3 2007/01/09 10:03:05 jiangl Exp $
 */
public class DbManager {
    private static final Logger log = LoggerFactory.getLogger(DbManager.class);

    /**
     * set to private since all methods are statics
     */
    private DbManager() {
    }

    /**
     * 获得数据库连接 注意调用之后一定要调用closeConnection()方法关闭数据库连接
     * 
     * @return
     */
    public static Connection getConnection() throws ConnectionException {
        try {
            Connection con = DataSourceUtils.getConnection(getDataSource("dataSource"));
            return con;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ConnectionException(e);
        }

    }

    private static DataSource getDataSource(String dataSource) {
        DataSource ds = (DataSource) ContainerManager.getComponent(dataSource);
        return ds;
    }

    /**
     * 关闭数据库连接
     * 
     * @param con
     * @throws ConnectionException
     */
    public static void closeConnection(Connection con) throws ConnectionException {
        if (con == null) {
            return;
        }

        DataSourceUtils.releaseConnection(con, getDataSource("dataSource"));

    }

}
