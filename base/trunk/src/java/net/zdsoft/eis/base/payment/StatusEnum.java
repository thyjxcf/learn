package net.zdsoft.eis.base.payment;

/**
 * 状态
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-11-4 上午10:20:22 $
 */
public enum StatusEnum {

	UN_ORDER {
		@Override
		public int getValue() {
			return 0;
		}

		@Override
		public String getNameValue() {
			return "未下单";
		}
	},

	UN_PAY {
		@Override
		public int getValue() {
			return 1;
		}

		@Override
		public String getNameValue() {
			return "未付款";
		}
	},
	
	PAYED {
		@Override
		public int getValue() {
			return 2;
		}

		@Override
		public String getNameValue() {
			return "已付款";
		}
	};

	/**
	 * 得到类型的整数值
	 * 
	 * @return
	 */
	public abstract int getValue();

	public abstract String getNameValue();

	/**
	 * 根据值得到是否类型
	 * 
	 * @param value
	 * @return 如果没有得到对应的是否类型，返回null
	 */
	public static StatusEnum get(int value) {
		for (StatusEnum type : StatusEnum.values()) {
			if (type.getValue() == value) {
				return type;
			}
		}

		return null;
	}

	public static String getNameValue(int value) {
		for (StatusEnum type : StatusEnum.values()) {
			if (type.getValue() == value) {
				return type.getNameValue();
			}
		}
		return "";
	}

	@Override
	public String toString() {
		return getNameValue();
	}
}
