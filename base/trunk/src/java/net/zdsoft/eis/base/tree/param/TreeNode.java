package net.zdsoft.eis.base.tree.param;

import java.io.Serializable;
import java.util.List;

/**
 * ztree 节点
 * @author feekang
 *
 */
public class TreeNode implements Serializable {

	private static final long serialVersionUID = -7276857765672201747L;

	private String id;
	
	private String parentId;
	
	private String name;
	
	private boolean checked;
	
	private String icon;
	
	private String iconOpen;
	
	private String iconClose;
	
	private String iconSkip;
	
	private boolean open;
	
	private String url;
	
	private String detailName;//详细名称,暂为发东莞消息通讯录使用
	private String type;//类型,当前选中的复选框类型是否有用，暂为发东莞消息通讯录使用
	private String isParent;//是否父节点
	
	private List<TreeNode> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconOpen() {
		return iconOpen;
	}

	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}

	public String getIconClose() {
		return iconClose;
	}

	public void setIconClose(String iconClose) {
		this.iconClose = iconClose;
	}

	public String getIconSkip() {
		return iconSkip;
	}

	public void setIconSkip(String iconSkip) {
		this.iconSkip = iconSkip;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getDetailName() {
		return detailName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	
}
