package net.zdsoft.leadin.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class QueryConditionUtils {
	/**
	 * 把需要得查询得条件以list 传入
	 * sql 得 where 条件 以 and 组装
	 * @param list
	 * @return
	 */
	public static String getSqlWhere(List<QueryCondition> list) {
		StringBuffer sql = new StringBuffer();
		for (QueryCondition q : list) {
			if (!StringUtils.isEmpty(q.getValue())) {
				sql.append(" and ").append(q.getColumn()).append(" ").append(q.getOperator())
                        .append(" ").append(q.getValue());
			}
		}
		return sql.toString();
	}
	
	public static class QueryCondition {
	    private String column; // 列
	    private String operator; // 关系符
	    private String value; // 值


	    public QueryCondition(String column, String value) {
	        super();
	        this.column = column;
	        this.value = value;
	        this.operator = "=";
	    }
	    
	    public QueryCondition(String column, String value, String operator) {
	        super();
	        this.column = column;
	        this.value = value;
	        this.operator = operator;
	    }

	    public String getColumn() {
	        return column;
	    }

	    public void setColumn(String column) {
	        this.column = column;
	    }

	    public String getOperator() {
	        return operator;
	    }

	    public void setOperator(String operator) {
	        this.operator = operator;
	    }

	    public String getValue() {
	        return value;
	    }

	    public void setValue(String value) {
	        this.value = value;
	    }
	}
}
