/* 
 * @(#)Field.java    Created on 2012-11-26
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.form;

import java.util.List;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-11-26 下午07:55:55 $
 */
@SuppressWarnings("serial")
public class Field extends BaseEntity implements Cloneable {
	public static final int SIMPLE_TYPE_STRING = 0;
	public static final int SIMPLE_TYPE_DATE = 1;
	public static final int SIMPLE_TYPE_NUMBERIC = 2;
	public static final int SIMPLE_TYPE_INTEGER = 3;

	public static final int INPUT_TYPE_TEXT = 0;
	public static final int INPUT_TYPE_TEXTAREA = 1;
	public static final int INPUT_TYPE_SELECT = 2;
	public static final int INPUT_TYPE_FILE = 3;
	

	
	private String name;// 属性名
	private String define;// 中文名
	private int order;// 排序号
	private boolean listShow;// 列表中是否显示
	private String mcode;// 微代码

	private String oldValue;// 原值
	private String value;// 新值

	private String wrappedOldValue;// 封装后的原值，如微代码转换等
	private String wrappedValue;// 封装后的新值

	private Field childField; // 子列
	private String parentValue; // 父列的值

	private List<String[]> options;// 当inputType为Select时使用默认第一个为value

	// 当simpleType 为 SIMPLE_TYPE_NUMBERIC SIMPLE_TYPE_INTEGER时有效
	private Float minValue;
	private Float maxValue;

	// 当simpleType 为 SIMPLE_TYPE_NUMBERIC时有效
	private Integer decimalLength;
	private Integer integerLength;
	// --------------输入-------------------
	private boolean require;// 是否是必填项
	private String type;// 类型

	private int simpleType = 0;// 类型
	private int strLength = 0;// String类型的长度
	private String defaultValue;// 默认值
	private int inputType = 0;// 输入类型：text、textarea、select
	private boolean alone;// 是否独自一行

	private String businessType;
	private String childName;
	//======
	private boolean readOnly;//是否不可编辑
	private String otherContent;//后置内容
	//private String trueValue;//用于如果该值是个ID 但想显示ID对应该的NAME值 改为使用 wrappedValue值 了
	private boolean needReset=true;//判断在选择用户的时候  是否需要重置信息

	public Field() {
	}

	public Field(String name, String define) {
		super();
		this.name = name;
		this.define = define;
	}

	public Field(String name, String define, int order) {
		this(name, define);
		this.order = order;
	}

	public Field(String name, String define, int order, boolean listShow) {
		this(name, define, order);
		this.listShow = listShow;
	}

	public Field(String name, String define, int order, boolean listShow, String mcode) {
		this(name, define, order, listShow);
		this.mcode = mcode;
	}

	public String getName() {
		return name;
	}

	public String getDefine() {
		return define;
	}

	public String getOldValue() {
		return oldValue;
	}

	public String getMcode() {
		return mcode;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDefine(String define) {
		this.define = define;
	}

	public void setMcode(String mcode) {
		this.mcode = mcode;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public int getOrder() {
		return order;
	}

	public boolean isListShow() {
		return listShow;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getWrappedValue() {
		return wrappedValue;
	}

	public void setWrappedValue(String wrappedValue) {
		this.wrappedValue = wrappedValue;
	}

	public String getWrappedOldValue() {
		return wrappedOldValue;
	}

	public void setWrappedOldValue(String wrappedOldValue) {
		this.wrappedOldValue = wrappedOldValue;
	}

	public boolean isRequire() {
		return require;
	}

	public void setRequire(boolean require) {
		this.require = require;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type.toLowerCase();
		if (this.type.contains("(")) {
			int index = this.type.indexOf("(");
			String typeLength = this.type.substring(index + 1);
			int index1 = typeLength.indexOf(")");
			if (this.type.contains("number")) {
				if (!typeLength.contains(",")) {
					this.simpleType = SIMPLE_TYPE_INTEGER;
					strLength = Integer.parseInt(typeLength.substring(0, index1).trim());
				} else {
					this.simpleType = SIMPLE_TYPE_NUMBERIC;
					int index2 = typeLength.indexOf(",");
					strLength = Integer.parseInt(typeLength.substring(0, index2).trim());
					decimalLength = Integer.parseInt(typeLength.substring(index2 + 1, index1)
							.trim());
					integerLength = strLength - 1 - decimalLength;
				}
			} else {
				strLength = Integer.parseInt(typeLength.substring(0, index1).trim());
			}
		} else if (this.type.contains("date")) {
			this.simpleType = SIMPLE_TYPE_DATE;
		} else if (this.type.contains("float")) {
			simpleType = SIMPLE_TYPE_NUMBERIC;
			integerLength = 9;
			decimalLength = 2;
		}
	}

	public int getStrLength() {
		return strLength;
	}

	public void setStrLength(int strLength) {
		this.strLength = strLength;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public int getSimpleType() {
		return simpleType;
	}

	public void setSimpleType(int simpleType) {
		this.simpleType = simpleType;
	}

	public int getInputType() {
		return inputType;
	}

	public void setInputType(int inputType) {
		this.inputType = inputType;
	}

	public boolean isAlone() {
		return alone;
	}

	public void setAlone(boolean alone) {
		this.alone = alone;
	}

	public Field getChildField() {
		return childField;
	}

	public void setChildField(Field childField) {
		this.childField = childField;
	}

	public String getShowValue() {
		return parentValue;
	}

	public void setParentValue(String parentValue) {
		this.parentValue = parentValue;
	}

	public List<String[]> getOptions() {
		return options;
	}

	public void setOptions(List<String[]> options) {
		this.options = options;
	}

	public Float getMinValue() {
		return minValue;
	}

	public void setMinValue(Float minValue) {
		this.minValue = minValue;
	}

	public Float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Float maxValue) {
		this.maxValue = maxValue;
	}

	public Integer getDecimalLength() {
		return decimalLength;
	}

	public void setDecimalLength(Integer decimalLength) {
		this.decimalLength = decimalLength;
	}

	public Integer getIntegerLength() {
		return integerLength;
	}

	public void setIntegerLength(Integer integerLength) {
		this.integerLength = integerLength;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public String getParentValue() {
		return parentValue;
	}

	public void setListShow(boolean listShow) {
		this.listShow = listShow;
	}

	@Override
	public Field clone() {
		try {
			return (Field) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}


	public String getOtherContent() {
		return otherContent;
	}

	public void setOtherContent(String otherContent) {
		this.otherContent = otherContent;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}


	public boolean isNeedReset() {
		return needReset;
	}

	public void setNeedReset(boolean needReset) {
		this.needReset = needReset;
	}

}
