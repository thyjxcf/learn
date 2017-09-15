package net.zdsoft.eis.base.tree.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;

import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.util.PinyinUtil;

import org.apache.commons.lang.StringUtils;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author lilj
 * @since 1.0
 * @version $Id: BaseTreeAction.java,v 1.3 2006/09/27 03:40:13 zhaosf Exp $
 */

public class BaseTreeAction extends BaseAction {
		
	private static final long serialVersionUID = -5546086959206793109L;
	
    /**
     * 返回普通的树页面
     */
    public static final String COMMON_TREE = "commonTree";
    public static final String COMMON_TREE_2 = "commonTree2";
    public static final String COMMON_TREE_JSON = "commonTree3";
    
	protected String treeJSCode;

	public String getTreeJSCode() {
		return treeJSCode;
	}

	protected void setTreeJSCode(String treeJSCode) {
		this.treeJSCode = treeJSCode;
	}
	
	private String treeJsonCode;
	
	public String getTreeJsonCode() {
		return treeJsonCode;
	}

	public void setTreeJsonCode(String treeJsonCode) {
		this.treeJsonCode = treeJsonCode;
	}

	// ---------------------------另一种样式的树 开始-------------------------
	private String idObjectId;// id控件的id
	private String nameObjectId;// name控件的id

	private String useCheckbox = "true";
	private boolean showLetterIndex = true;// 是否显示首字母索引

	private String callback;// 回调方法
	private boolean treeStop;// 是否可以中停即不需要选择到最底层节点（如部门（多级）、行政区划）
	//ztree
	private String treeType;//默认以前的机构 如果是使用ztree的话 需要使用这个字段
	private String searchTips;//搜索框提示
	public String getTreeType() {
		return treeType;
	}

	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}

	public String getSearchTips() {
		return searchTips;
	}

	public void setSearchTips(String searchTips) {
		this.searchTips = searchTips;
	}

	private Set<String> firstNameLetterSet = new TreeSet<String>();
	private Map<String, List<XLoadTreeItem>> objectMap = new TreeMap<String, List<XLoadTreeItem>>();
	private List<XLoadTreeItem> filterObjects = new ArrayList<XLoadTreeItem>();
	private XLoadTreeItem treeRoot;//根结点 
	
	protected void disposeObjects(XLoadTreeItem root) {
		// 如果根结点可以点击，则需要显示根结点
		if (StringUtils.isNotBlank(root.getAction())) {
			this.treeRoot = root;
		}
		
		List<XLoadTreeItem> items = root.getChildList();
		disposeObjects(items);
	}
	
	protected void disposeObjects(List<XLoadTreeItem> datas) {
		if (null == datas || datas.size() == 0) {
			return;
		}

		// 转化为SimpleObject
		for (XLoadTreeItem object : datas) {
			//可点击
			if (StringUtils.isNotBlank(object.getAction())) {
				String element = getFirstNotIllegalCode(object.getObjectName());
				//String element = PinyinUtil.toHanyuPinyin(object.getObjectName().substring(0, 1),true);
				
				
				if (null != element) {
					firstNameLetterSet.add(element.toUpperCase());
					String letter = element.toUpperCase();
					List<XLoadTreeItem> os = objectMap.get(letter);
					if (os == null) {
						os = new ArrayList<XLoadTreeItem>();
						objectMap.put(letter, os);
					}
					os.add(object);
				}
			} else {
				filterObjects.add(object);
			}
		}
	}

	/**
	 * 
	 * @param objectName
	 * @return
	 */
	private String getFirstNotIllegalCode(String objectName) {
		if(Validators.isBlank(objectName)){
			return null;
		}
		String element = PinyinUtil.toHanyuPinyin(objectName.substring(0, 1),true);
		String reg = "[a-zA-Z]+";
		
		if(Pattern.matches(reg, element)){
			return element;
		}else{
			if(objectName.length()>1){
				objectName = objectName.substring(1);
				return getFirstNotIllegalCode(objectName);
			}else{
				return null;
			}
		}
	}

	public String getIdObjectId() {
		return idObjectId;
	}

	public void setIdObjectId(String idObjectId) {
		this.idObjectId = idObjectId;
	}

	public String getNameObjectId() {
		return nameObjectId;
	}

	public void setNameObjectId(String nameObjectId) {
		this.nameObjectId = nameObjectId;
	}

	public String getUseCheckbox() {
		return useCheckbox;
	}

	public void setUseCheckbox(String useCheckbox) {
		this.useCheckbox = useCheckbox;
	}

	public boolean isShowLetterIndex() {
		return showLetterIndex;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public boolean isTreeStop() {
		return treeStop;
	}

	public void setTreeStop(boolean treeStop) {
		this.treeStop = treeStop;
	}

	public Set<String> getFirstNameLetterSet() {
		return firstNameLetterSet;
	}

	public Map<String, List<XLoadTreeItem>> getObjectMap() {
		return objectMap;
	}

	public List<XLoadTreeItem> getFilterObjects() {
		return filterObjects;
	}

	public XLoadTreeItem getTreeRoot() {
		return treeRoot;
	}
	
	// ---------------------------另一种样式的树 结束-------------------------
}
