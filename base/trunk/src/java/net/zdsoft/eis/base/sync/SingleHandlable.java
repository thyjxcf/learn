/**
 * 
 */
package net.zdsoft.eis.base.sync;

import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.leadin.exception.BusinessErrorException;

/**
 * 单个处理的方法
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 20, 2010 5:06:52 PM $
 */
public interface SingleHandlable<E extends BaseEntity> {
	/**
	 * 删除数据
	 * 
	 * @param id
	 * @param eventSource
	 * @throws BusinessErrorException
	 */
	public abstract void deleteData(String id, EventSourceType eventSource)
			throws BusinessErrorException;

	/**
	 * 增加数据
	 * 
	 * @param e
	 * @throws BusinessErrorException
	 */
	public abstract void addData(E e) throws BusinessErrorException;

	/**
	 * 更新数据
	 * 
	 * @param e
	 * @throws BusinessErrorException
	 */
	public abstract void updateData(E e) throws BusinessErrorException;

	/**
	 * 取原来entity数据，更新时使用，如果eis有扩展的字段，则取数据库中直接取。 如果没有扩展字段（即与base表结构完全相同，则直接new
	 * entity或null即可）
	 * 
	 * @param id
	 * @return
	 */
	public abstract E fetchOldEntity(String id);
}
