package net.zdsoft.eis.base.payment;

/**
 * * 支付结果<br/>
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-11-4 上午09:58:25 $
 */
public enum PaymentResultEnum {

	ORDER_PAY_SUCCESS {
		@Override
		public int getValue() {
			return 1;
		}

		@Override
		public String getNameValue() {
			return "订单支付成功";
		}
	},

	// ---------------支付宝反馈结果-----------------
	ALIPAY_DATA_MODIFIED {
		@Override
		public int getValue() {
			return 21;
		}

		@Override
		public String getNameValue() {
			return "数据被篡改";
		}
	},

	ALIPAY_FAILURE {
		@Override
		public int getValue() {
			return 22;
		}

		@Override
		public String getNameValue() {
			return "支付宝返回结果失败，订单超过1分钟已失效";
		}
	},

	ALIPAY_TRADE_TIMEOUT {
		@Override
		public int getValue() {
			return 23;
		}

		@Override
		public String getNameValue() {
			return "支付宝返回交易状态为未处理或者处理未成功";
		}
	},

	// ---------------订单情况-----------------
	ORDER_INVALID {
		@Override
		public int getValue() {
			return 31;
		}

		@Override
		public String getNameValue() {
			return "无效的订单";
		}
	},

	ORDER_PAYED {
		@Override
		public int getValue() {
			return 32;
		}

		@Override
		public String getNameValue() {
			return "订单已结算";
		}
	},

	ORDER_AMOUNT_UNMATCH {
		@Override
		public int getValue() {
			return 33;
		}

		@Override
		public String getNameValue() {
			return "订单金额不匹配，前后不一致";
		}
	},

	ORDER_DONE {
		@Override
		public int getValue() {
			return 34;
		}

		@Override
		public String getNameValue() {
			return "订单已下单，请刷新后重试";
		}
	},

	ORDER_DELETEDY {
		@Override
		public int getValue() {
			return 35;
		}

		@Override
		public String getNameValue() {
			return "订单被删除，请刷新后重试";
		}
	},

	ORDER_TIMEOUT {
		@Override
		public int getValue() {
			return 36;
		}

		@Override
		public String getNameValue() {
			return "订单已过期！";
		}
	},

	// ---------------商品结果-----------------

	NO_WARE {
		@Override
		public int getValue() {
			return 41;
		}

		@Override
		public String getNameValue() {
			return "没有找到商品";
		}
	},

	WARE_OUT_SALE {
		@Override
		public int getValue() {
			return 42;
		}

		@Override
		public String getNameValue() {
			return "你订购的商品已下架";
		}
	},

	WARE_OUT_COUNT {
		@Override
		public int getValue() {
			return 43;
		}

		@Override
		public String getNameValue() {
			return "商品数量必须在1至99件之间，不能支付！";
		}
	},

	WARE_TOTAL_FEE {
		@Override
		public int getValue() {
			return 44;
		}

		@Override
		public String getNameValue() {
			return "商品总金额必须大于0！";
		}
	},

	// ---------------其它原因-----------------
	UNKNOWN {
		@Override
		public int getValue() {
			return 91;
		}

		@Override
		public String getNameValue() {
			return "未知原因";
		}
	},

	NO_SELF_LOGIN {
		@Override
		public int getValue() {
			return 92;
		}

		@Override
		public String getNameValue() {
			return "不是本人登录，不能支付！";
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
	 * @return 如果没有得到对应的类型，返回UNKNOW_REASON（未知原因）
	 */
	public static PaymentResultEnum get(int value) {
		for (PaymentResultEnum type : PaymentResultEnum.values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		return PaymentResultEnum.UNKNOWN;
	}

	@Override
	public String toString() {
		return getNameValue();
	}
}
