package net.zdsoft.eis.base.tree.action;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;

public class BaseTreeXmlAction extends BaseTreeAction {
    private static final long serialVersionUID = -3491995623776439873L;
      
    /**
     * 返回普通的xml树页面
     */
    public static final String COMMON_TREE_XML = "commonTreeXml";
    public static final String COMMON_TREE_XML_2 = "commonTreeXml2";
    
    public static final String EMPTY_TREE = "<tree/>"; // 若没有子节点返回该字符串
    protected String treeXML;
    protected String encoding = "UTF-8";

    public String buildTreeXml_old(List<XLoadTreeItem> items) {
        if (items == null || items.size() == 0) {
            treeXML = EMPTY_TREE;
        } else {
            Document doc = DocumentHelper.createDocument();
            Element root = doc.addElement("tree");
            Element e = null;
            if (items != null) {
                for (XLoadTreeItem item : items) {
                    e = root.addElement("tree");
                    e.addAttribute("text", item.getText());
                    e.addAttribute("action", item.getAction());
                    e.addAttribute("src", item.getXmlSrc());
                    e.addAttribute("icon", item.getIcon());
                    e.addAttribute("openIcon", item.getOpenIcon());
                }
            }

            treeXML = this.xmlDomToString(doc);
        }

        return COMMON_TREE_XML;
    }
    
	public String buildTreeXml(List<XLoadTreeItem> items) {
		disposeObjects(items);
		return COMMON_TREE_XML_2;
	}
	
	
    /**
     * 把XMLDOM格式化输出为字符串
     * 
     * @param xmlDom 需要格式化的XMLDOM
     * @return
     */
    protected String xmlDomToString(Document xmlDom) {
        String xmlStr;

        // 格式化输出,类型IE浏览一样
        OutputFormat format = OutputFormat.createPrettyPrint();
        // 指定XML字符集编码
        format.setEncoding(encoding);
        // 设置对xml中text的内容保持原状，不去除多余的空格
        format.setTrimText(false);
        StringWriter sw = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(sw, format);

        // 根据已有的数据生成xml文件
        try {
            xmlWriter.write(xmlDom);
        } catch (IOException e) {
            log.error("Document转化成字符串的时候出错！" + e.getMessage());
        } finally {
            try {
                xmlWriter.close();
            } catch (IOException e) {
                log.error("关闭Document转化字符串对象时出错！" + e.getMessage());
            }
        }

        xmlStr = sw.toString();

        try {
            sw.close();
        } catch (IOException e) {
            log.error("关闭字符串流对象错误！" + e.getMessage());
        }

        return xmlStr;
    }

    public String getBlankTree() {
        return "<?xml version=\"1.0\" encoding=\"" + encoding + "\" ?><tree></tree>";
    }

    public static String getAction(String action) {
        StringBuffer sb = new StringBuffer(TreeConstant.TREE_CLICK_METHOD);
        sb.append("(\'");
        sb.append(action);
        sb.append("\')");
        return sb.toString();
    }

    public static String getAction(String action, int itemType) {
        StringBuffer sb = new StringBuffer(TreeConstant.TREE_CLICK_METHOD);
        sb.append("(\'");
        sb.append(action);
        sb.append("\',\'");
        sb.append(itemType);
        sb.append("\')");
        return sb.toString();
    }

    public static String getAction(String action, int itemType, String name) {
        StringBuffer sb = new StringBuffer(TreeConstant.TREE_CLICK_METHOD);
        sb.append("(\'");
        sb.append(action);
        sb.append("\',\'");
        sb.append(itemType);
        sb.append("\',\'");
        sb.append(name);
        sb.append("\')");
        return sb.toString();
    }

    public static String getAction(String action, int itemType, String name, String parentId) {
        StringBuffer sb = new StringBuffer(TreeConstant.TREE_CLICK_METHOD);
        sb.append("(\'");
        sb.append(action);
        sb.append("\',\'");
        sb.append(itemType);
        sb.append("\',\'");
        sb.append(name);
        sb.append("\',\'");
        sb.append(parentId);
        sb.append("\')");
        return sb.toString();
    }

    public String getAction(String action, String name, String unitId) {
        StringBuffer sb = new StringBuffer(TreeConstant.TREE_CLICK_METHOD);
        sb.append("(\'");
        sb.append(action);
        sb.append("\',\'");
        sb.append(name);
        sb.append("\',\'");
        sb.append(unitId);
        sb.append("\')");
        return sb.toString();
    }

    public String getTreeXML() {
        return treeXML;
    }

    protected void setTreeXML(String treeXML) {
        this.treeXML = treeXML;
    }

    
}
