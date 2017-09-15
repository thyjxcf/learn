package net.zdsoft.eis.base.common.action;

import net.zdsoft.eis.base.common.entity.ProductParam;
import net.zdsoft.eis.base.common.service.ProductParamService;
import net.zdsoft.eis.frame.action.BaseAction;

import org.apache.commons.lang.StringUtils;

public class FootAction extends BaseAction {

	private static final long serialVersionUID = -3439607818327978082L;

	private ProductParamService productParamService;

	private String copyRight;

	public String execute() {
		copyRight = productParamService
				.getProductParamValue(ProductParam.COMPANY_COPYRIGHT);
		if (StringUtils.isNotBlank(copyRight)) {
			copyRight = copyRight.replace("currentYear", getCurrentYear());
		}
		return SUCCESS;
	}

	public String getCopyRight() {
		return copyRight;
	}

	public void setProductParamService(ProductParamService productParamService) {
		this.productParamService = productParamService;
	}
}
