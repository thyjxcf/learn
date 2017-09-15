package net.zdsoft.eis.base.common.action;

import net.zdsoft.eis.frame.action.BaseDivAction;

/**
 * 主要是为了实现popup模式下的级联
 * 通过div重新load的方式实现 popup模式下的级联
 * @author user
 *
 */

public class PopupDivAction extends BaseDivAction{

	private static final long serialVersionUID = 4411752605412240320L;
	
	private boolean isPopup;
	
	private String style;
	
	private String switchSelector;
	
	private String swithText;
	
	private String switchId;
	
	private String nameObjectValue;
	
	private String loadDataCallBack;
	private boolean tree = false;
	private boolean textarea = false;
	
	@Override
	public String execute() throws Exception {
		
		return SUCCESS;
	}

	public boolean isPopup() {
		return isPopup;
	}

	public void setPopup(boolean isPopup) {
		this.isPopup = isPopup;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getSwitchSelector() {
		return switchSelector;
	}

	public void setSwitchSelector(String switchSelector) {
		this.switchSelector = switchSelector;
	}

	public String getSwithText() {
		return swithText;
	}

	public void setSwithText(String swithText) {
		this.swithText = swithText;
	}

	public String getSwitchId() {
		return switchId;
	}

	public void setSwitchId(String switchId) {
		this.switchId = switchId;
	}

	public String getNameObjectValue() {
		return nameObjectValue;
	}

	public void setNameObjectValue(String nameObjectValue) {
		this.nameObjectValue = nameObjectValue;
	}

	public String getLoadDataCallBack() {
		return loadDataCallBack;
	}

	public void setLoadDataCallBack(String loadDataCallBack) {
		this.loadDataCallBack = loadDataCallBack;
	}

	public boolean isTree() {
		return tree;
	}

	public void setTree(boolean tree) {
		this.tree = tree;
	}

	public boolean isTextarea() {
	    return textarea;
	}

	public void setTextarea(boolean textarea) {
	    this.textarea = textarea;
	}
	
}
