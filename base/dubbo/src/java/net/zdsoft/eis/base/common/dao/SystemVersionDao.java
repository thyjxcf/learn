package net.zdsoft.eis.base.common.dao;

import net.zdsoft.eis.base.common.entity.SystemVersion;

public interface SystemVersionDao {
	/**
	 * 得到系统当前版本
	 * 
	 * @return
	 */
	public SystemVersion getSystemVersion();

    /**
     * 更新是否在用
     * 
     * @param productId
     * @return
     */
    public int updateProduct(String productId);
}
