/**
 * 
 */
package net.zdsoft.eis.base.observe.impl;

import net.zdsoft.eis.base.observe.Topic;

/**
 * 针对某个entity的CRUD操作主题
 * @author zhangkc
 * @date 2014年12月19日 上午11:12:26
 */
public class BasicOperationTopic implements Topic {
	private static final long serialVersionUID = -2898552905544356683L;
	
	private String className;
	private BasicOperation operation;
	
	public BasicOperationTopic(String className, BasicOperation operation){
		this.className = className;
		this.operation = operation;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result
				+ ((operation == null) ? 0 : operation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicOperationTopic other = (BasicOperationTopic) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (operation != other.operation)
			return false;
		return true;
	}

	@Override
	public String getName() {
		return className + "[" + operation.getOperation() + "]";
	}

	/**
	 * 基础操作
	 * 
	 * @author zhangkc
	 * @date 2014年12月19日 上午11:10:24
	 */
	public enum BasicOperation{
		//CRUD基础操作
		CREATE("create"),
		READ("read"),
		UPDATE("update"),
		DELETE("delete");
		
		private String operation;
		
		BasicOperation(String operation){
			this.operation = operation;
		}
		public String getOperation() {
			return operation;
		}
	}
}
