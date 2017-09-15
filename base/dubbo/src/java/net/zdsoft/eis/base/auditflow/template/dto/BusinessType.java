package net.zdsoft.eis.base.auditflow.template.dto;

/**
 * <p>
 * 用于表示一种具体业务类型(区别于<code>BusinessConstants</code>中的业务类型)的类.<br>
 * 它通过<code>BusinessConstants</code>中定义的业务类型(TYPE)和业务模式(MODE)<br>
 * 的组合来表示一种具体的业务类型.
 * </p>
 * <p>
 * 对于各种组合,在不需要特殊控制时,可使用一个默认的<code>BusinessType</code>,<br>
 * 通过方法{@link #defaultValueOf()}获取,<br>
 * 其他情况则可以通过方法{@link #valueOf()}来获取对应的<code>BusinessType</code>.
 * </p>
 * <p>
 * 每种<code>BusinessType</code>,都有一个<tt>int</tt>型的值与之对应:
 * <ul>
 * <li>默认业务类型: 值为<b>-255</b>
 * <li>其他业务类型: 值通过公式 <b>(TYPE * 100) + MODE</b> 计算所得
 * </ul>
 * </p>
 * 
 * @author yaox
 * @version 1.0
 * @see net.zdsoft.eis.stusys.constant.BusinessConstants
 */
public final class BusinessType {

    /**
     * 对应BusinessConstants.BUSINESS_TYPE_ 开头的类型
     */
    private int businessType;

    /**
     * 目前用于异动的审核与不审核,对应BusinessConstants.BUSINESS_TYPE_ABNORMAL_ 开头的类型
     */
    private int subBusinessType;

	private int businessMode;

	private int value;

	private BusinessType(int businessType,int subBusinessType, int businessMode, int value) {
		this.businessType = businessType;
		this.subBusinessType = subBusinessType;
		this.businessMode = businessMode;
		this.value = value;
	}

	/**
	 * 通过一个业务类型和业务模式来获取一个默认的<code>BusinessType</code>.<br>
	 * 默认<code>BusinessType</code>的值为<b>-255</b>.<br>
	 * 业务类型和业务模式的定义可在{@link net.zdsoft.eis.stusys.constant.BusinessConstants}查看.
	 * 
	 * @param businessType
	 *            业务类型值
	 * @param subBusinessType
	 *            子业务模式值
	 * @param businessMode
	 *            业务模式值
	 * @return <code>BusinessType</code>对象
	 * @see net.zdsoft.eis.stusys.constant.BusinessConstants
	 */
	public static BusinessType defaultValueOf(int businessType,int subBusinessType, int businessMode) {
		return new BusinessType(businessType,subBusinessType, businessMode, -255);
	}

	/**
	 * 通过一个业务类型(TYPE)和业务模式(MODE)来获取一个对应的<code>BusinessType</code>.<br>
	 * <code>BusinessType</code>的值通过此公式计算:<br>
	 * <b>(TYPE * 100) + (SUBTYPE * 100) + MODE</b><br>
	 * 业务类型和业务模式的定义可在{@link net.zdsoft.eis.stusys.constant.BusinessConstants}查看.
	 * 
	 * @param businessType
	 *            业务类型值
	 * @param subBusinessType
	 *            子业务模式值
	 * @param businessMode
	 *            业务模式值 
	 * @return <code>BusinessType</code>对象
	 * @see net.zdsoft.eis.stusys.constant.BusinessConstants
	 */
	public static BusinessType valueOf(int businessType,int subBusinessType, int businessMode) {
		return new BusinessType(businessType,subBusinessType, businessMode,
				(businessType * 100) +(subBusinessType * 10)+ businessMode);
	}

	/**
	 * 返回<code>BusinessType</code>的值
	 * 
	 * @return <code>BusinessType</code>的值
	 */
	public int value() {
		return value;
	}

	public int getBusinessMode() {
		return businessMode;
	}

	public int getBusinessType() {
		return businessType;
	}

	public int getSubBusinessType() {
		return subBusinessType;
	}
	
}
