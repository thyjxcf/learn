package net.zdsoft.eis.base.form;

import java.util.List;
import java.util.Map;


public interface FieldDao {
	/**
	 * 获取列表
	 * @param businessType
	 * @return
	 */
	public List<Field> getFieldList(int businessType);
	
	/**
	 * 获取map key 为name
	 * @param businessType
	 * @return
	 */
	public Map<String, Field> getFieldMap(int businessType);
	/**
	 * 获取单个列信息
	 * @param businessType
	 * @param name
	 * @param parentValue
	 * @return
	 */
	public Field getFieldEntity(int businessType, String name, String parentValue);
}
