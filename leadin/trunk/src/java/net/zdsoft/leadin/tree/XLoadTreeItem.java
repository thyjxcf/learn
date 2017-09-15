package net.zdsoft.leadin.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author lilj
 * @since 1.0
 * @version $Id: XLoadTreeItem.java,v 1.8 2007/03/09 12:51:55 zhanghh Exp $
 */

public class XLoadTreeItem {
	private static final long serialVersionUID = 4281223475130769409L;

	private String text;

	private String action;

	private String icon;

	private String openIcon;

	private XLoadTreeItem parentNode;
    
    private List<XLoadTreeItem> childList;  //子节点list

	private String xmlSrc;

	private String nodeName;

	private String inputValue;
    
    private String checkboxName;

	private boolean disabledFlag = false;

	private boolean checked = false;
	
	private int itemType;//节点类型
	private String itemLinkParams;// 节点参数
    private int itemKind;// 节点类别

    //==============对应SimpleObject================
    private String id;  
    private String objectName; // 对象名
    private String objectCode; // 对象代码：单位内惟一    
    private String fullName;//长名：如行政区划：objectName=西湖区，shortName=浙江省杭州市西湖区
           
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectCode() {
		return objectCode;
	}

	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}
	
	public String getFullName() {
		if (fullName == null) {
			fullName = objectName;
		}
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public XLoadTreeItem(String text, String nodeName, String inputValue) {
		this.text = text;
		this.nodeName = nodeName;
		this.inputValue = inputValue;
	}
	
	public XLoadTreeItem(String text, String nodeName, String inputValue, String action) {
		this.text = text;
		this.nodeName = nodeName;
		this.inputValue = inputValue;
		this.action = action;
	}   
    
	
	public XLoadTreeItem() {
		
	}
    
    
    /**
     * 添加一个子节点
     * @param node
     */
    public void appendNode(XLoadTreeItem node){
        if( childList==null ){
            childList  = new ArrayList<XLoadTreeItem>();
        }
        
        //检查是否以存在该节点
        
        
        childList.add(node);
    }
    
    
    /**
     * 以当前节点为根节点生成javascript脚本
     * @return
     */
    public String getJScript() {     
        return getScript(null);
    }
    
    
     /**
     * 生成当前节点和子节点的javascript脚本 
     * @return
     */
    protected String getScript(String rootName) {
        StringBuffer sbXTree = new StringBuffer();

        // 若当前为根节点  
        if (rootName == null) {
            sbXTree = sbXTree.append( this.toRootJScript() );
        } else {
            // 输出当前节点
            sbXTree .append(" var " + getNodeName() + " = " + this.toItemJScript()
                            + ";\n");
            sbXTree.append(rootName + ".add(" + getNodeName() + ");\n");
        }
        

        if (childList == null || childList.size() == 0)
            return sbXTree.toString();

        // 递归子节点
        for (Iterator<XLoadTreeItem> iter = childList.iterator(); iter.hasNext();) {
            XLoadTreeItem node = (XLoadTreeItem) iter.next();
            String cScript = node.getScript(getNodeName());
            sbXTree.append(cScript);
        }

        return sbXTree.toString();
    }
    
    
    /**
     * 把当前节点转换为子节点的js脚本，不包括子节点。 功能基本同toString()，但是由于关于字符串的替换有点异议，但怕影响到其他调用模块，
     * 所以不敢随便改，就再重新写了这个函数,完整的js如下： 
     * WebFXTreeItem(sText, sAction, eParent, sIcon, sOpenIcon, sInputValue, bDisabledFlag, bChecked)
     * WebFXLoadTreeItem(sText, sXmlSrc, sAction, eParent, sIcon, sOpenIcon)
     * WebFXCheckBoxTreeItem(sText,sCheckboxName,sValue,sAction, bChecked,bDisabled, eParent, sIcon, sOpenIcon)
     * @author zhanghh 2006-11-3
     * @return
     */
    private String toItemJScript() {

        if (xmlSrc != null && xmlSrc.trim().length() > 0) {// 动态树
            return this.toWebFXLoadTreeItemJScript();
        }
        else if (checkboxName != null && checkboxName.length() > 0) {
            return this.toWebFXCheckBoxTreeItem();
        }
        else {
            return this.toWebFXTreeItemJScript();
        }

    }
    
    /**
     * 把当前节点转换为根节点的js脚本，不包括子节点。
     * 完整的js如下：
     * WebFXTree(sText, sAction, sBehavior, sIcon, sOpenIcon, sInputValue)
     */
    private String toRootJScript() {
        StringBuffer sRoot = new StringBuffer();

        sRoot.append(" var " + getNodeName() + " = new WebFXTree( ");
        // sText
        if (text == null) {
            sRoot.append("null,");
        }
        else {
            sRoot.append("'" + text.replace("'", "\\'") + "',");
        }
        // sAction
        if (action == null) {
            sRoot.append("null,");
        }
        else {
            sRoot.append("'" + action.replace("'", "\\'") + "',");
        }
        // sBehavior
        sRoot.append("null,");
        // sIcon
        if (icon == null) {
            sRoot.append("null,");
        }
        else {
            sRoot.append("'" + icon.replace("'", "\\'") + "',");
        }
        // sOpenIcon
        if (openIcon == null) {
            sRoot.append("null,");
        }
        else {
            sRoot.append("'" + openIcon.replace("'", "\\'") + "',");
        }
        // sInputValue
        sRoot.append("null");
        sRoot.append(");\n");

        return sRoot.toString();
    }
    
    
    /**
     * 把当前节点转换为子节点WebFXTreeItem的js脚本，不包括子节点。
     * 完整的js如下：
     * WebFXTreeItem(sText, sAction, eParent, sIcon, sOpenIcon, sInputValue, bDisabledFlag, bChecked)
     */
    private String toWebFXTreeItemJScript() {
        StringBuffer item = new StringBuffer();
                
        item.append("(new WebFXTreeItem( ");
        
        if (text == null) {
            item.append("null,");
        } else {
            item.append("'" + text.replace("'", "\\'") + "',");
        }       

        if (action == null) {
            item.append("null,");
        } else {
            item.append("'" + action.replace("'", "\\'") + "',");
        }

        if (parentNode == null) {
            item.append("null,");
        } else {
            item.append("'" + parentNode.getNodeName().replace("'", "\\'")
                    + "',");
        }

        if (icon == null) {
            item.append("null,");
        } else {
            item.append("'" + icon.replace("'", "\\'") + "',");
        }

        if (openIcon == null) {
            item.append("null,");
        } else {
            item.append("'" + openIcon.replace("'", "\\'") + "',");
        }

        if (inputValue == null) {
            item.append("null,");
        } else {
            item.append("'" + inputValue.replace("'", "\\'") + "',");
        }

        item.append(disabledFlag + ",");
        item.append(checked);
        item.append("))");

        return item.toString();
    }
    
    /**
     * 把当前节点转换为子节点WebFXLoadTreeItem的js脚本，不包括子节点。
     * 完整的js如下：
     * WebFXLoadTreeItem(sText, sXmlSrc, sAction, eParent, sIcon, sOpenIcon)
     */
    private String toWebFXLoadTreeItemJScript(){
        StringBuffer item = new StringBuffer();
        
        item.append("(new WebFXLoadTreeItem( ");
        
        if (text == null) {
            item.append("null,");
        } else {
            item.append("'" + text.replace("'", "\\'") + "',");
        }
        
        if (xmlSrc == null) {
            item.append("null,");
        } else {
            item.append("'" + xmlSrc.replace("'", "\\'") + "',");
        }
        
        if (action == null) {
            item.append("null,");
        } else {
            item.append("'" + action.replace("'", "\\'") + "',");
        }

        if (parentNode == null) {
            item.append("null,");
        } else {
            item.append("'" + parentNode.getNodeName().replace("'", "\\'") + "',");
        }

        if (icon == null) {
            item.append("null,");
        } else {
            item.append("'" + icon.replace("'", "\\'") + "',");
        }

        if (openIcon == null) {
            item.append("null,");
        } else {
            item.append("'" + openIcon.replace("'", "\\'") + "',");
        }
        
        if (inputValue == null) {
            item.append("null,");
        } else {
            item.append("'" + inputValue.replace("'", "\\'") + "',");
        }
        
        if (checkboxName != null && !"".equals(checkboxName)){
        	item.append("'true'");
        }
        else {
        	item.append("null");
        }
        
        item.append("))");
        
        return item.toString();
    }

    /**
     * 把当前节点转换为子节点WebFXCheckBoxTreeItem的js脚本，不包括子节点。
     * 完整的js如下：
     * WebFXCheckBoxTreeItem(sText,sCheckboxName,sValue,sAction, bChecked,bDisabled, eParent, sIcon, sOpenIcon)
     */
    private String toWebFXCheckBoxTreeItem() {
        StringBuffer item = new StringBuffer();

        item.append("(new WebFXCheckBoxTreeItem( ");
        // sText
        if (text == null) {
            item.append("null,");
        }
        else {
            item.append("'" + text.replace("'", "\\'") + "',");
        }
        // sCheckboxName
        if (checkboxName == null) {
            item.append("null,");
        }
        else {
            item.append("'" + checkboxName.replace("'", "\\'") + "',");
        }
        // sValue
        if (inputValue == null) {
            item.append("null,");
        }
        else {
            item.append("'" + inputValue.replace("'", "\\'") + "',");
        }
        // sAction
        if(getAction() == null){
            item.append("null,");
        }else{
            item.append("'" + getAction().replace("'", "\\'") + "',");
        }
        // bChecked
        item.append(checked + ",");
        // bDisabled
        item.append(disabledFlag + ",");
        // parent
        if (parentNode == null) {
            item.append("null,");
        }
        else {
            item.append("'" + parentNode.getNodeName().replace("'", "\\'")
                    + "',");
        }
        // sIcon
        if (icon == null) {
            item.append("null,");
        }
        else {
            item.append("'" + icon.replace("'", "\\'") + "',");
        }
        // sOpenIcon
        if (openIcon == null) {
            item.append("null");
        }
        else {
            item.append("'" + openIcon.replace("'", "\\'") + "'");
        }

        item.append("))");

        return item.toString();
    }  
   

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isDisabledFlag() {
		return disabledFlag;
	}

	public void setDisabledFlag(boolean disabledFlag) {
		this.disabledFlag = disabledFlag;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}

	public String getOpenIcon() {
		return openIcon;
	}

	public void setOpenIcon(String openIcon) {
		this.openIcon = openIcon;
	}

	public XLoadTreeItem getParentNode() {
		return parentNode;
	}

	public void setParentNode(XLoadTreeItem parentNode) {
		this.parentNode = parentNode;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getXmlSrc() {
		return xmlSrc;
	}

	public void setXmlSrc(String xmlSrc) {
		this.xmlSrc = xmlSrc;
	}    
	
    public List<XLoadTreeItem> getChildList() {
        return childList;
    }

    /**
     * 强烈建议使用toItemJScript()
     * 理由一：replace（）返回的字符串覆盖了原来字符串，多次调用的话。。。
     * 理由二：’替换为\\'就可以了
     * @deprecated
     */
    public String toString() {
        text = text.replaceAll("\\\\", "\\\\\\\\");
        text = text.replaceAll("'", "\\\\'");
        inputValue = inputValue.replaceAll("\\\\", "\\\\\\\\");
        inputValue = inputValue.replaceAll("'", "\\\\'");
        StringBuffer sb = new StringBuffer();
        if(xmlSrc!=null && xmlSrc.trim().length()>0){//动态树
            sb.append("(new WebFXLoadTreeItem('");
        }
        else{
            sb.append("(new WebFXTreeItem('");
        }
        
        sb.append(text);
        sb.append("',");
        if(xmlSrc!=null && xmlSrc.trim().length()>0){//动态树
            sb.append("'"+xmlSrc+"',");
        }
        
        if(action==null){
            sb.append("null");
        }
        else{
            sb.append("'"+action+"'");
        }
        
        sb.append(",");
        sb.append(parentNode);
        sb.append(",");
        sb.append((icon==null)?"null":icon);
        sb.append(",");
        sb.append((openIcon==null)?"null":openIcon);
        
        sb.append(",");
        if(inputValue==null){
            sb.append("null");
        }
        else{
            sb.append("'"+inputValue+"'");
        }
        sb.append(",");
        sb.append(disabledFlag);
        sb.append(",");
        sb.append(checked);
        sb.append("))");
        return sb.toString();
    }

    public String getCheckboxName() {
        return checkboxName;
    }

    public void setCheckboxName(String checkboxName) {
        this.checkboxName = checkboxName;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getItemLinkParams() {
        return itemLinkParams;
    }

    public void setItemLinkParams(String itemLinkParams) {
        this.itemLinkParams = itemLinkParams;
    }

    public int getItemKind() {
        return itemKind;
    }

    public void setItemKind(int itemKind) {
        this.itemKind = itemKind;
    }
   
    
}
