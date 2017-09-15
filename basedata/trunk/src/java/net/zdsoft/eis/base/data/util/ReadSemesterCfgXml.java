package net.zdsoft.eis.base.data.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import net.zdsoft.leadin.util.ConfigFileUtils;


/* 
 * 利用dom4j以XPath方读取cfg\default_semester.xml默认学年学期信息
 * 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: ReadSemesterCfgXml.java,v 1.3 2007/01/09 10:03:05 jiangl Exp $
 */
public final class ReadSemesterCfgXml {
	private static final String XMLFILE = "eis_default_semester.xml";
	
	/**
	 * 返回一个Map对象，里面存储了指定学期下的所有初始信息，Key是字段（如：workbegin），Vlaue是初始值(如：02-20)
	 * @param semester 学期，微代码：1第一学期，2第二学期，如果semester=null，则默认第一学期
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
    public static Map getXmlDefaultValue(String semester){	
		try{
			InputStream in = ConfigFileUtils.getConfigFileStream(XMLFILE);
			
			//读入XML文档，生成document
			SAXReader reader = new SAXReader();
			try {
				Document document = reader.read(in);
				//取得ROOT节点
				//Element root = document.getRootElement();
				
				//组装xPath，如："//Semester/field[../@name='1']"
				if(null == semester){
					semester = "1";
				}
				String xPath = "//Semester/field[../@name='" + semester + "']";
				
				//取得子结点列表
				List list = document.selectNodes(xPath);
				
				//读取各结点的值和属性值可以参考下面的方法
				Map<String, String> map = new HashMap<String, String>();
				for (Iterator it = list.iterator(); it.hasNext(); ) {
					Node node = (Node)it.next();
					String field = node.valueOf("@name");	//得到名称为name的属性值
					String value = node.getText();			//得到该结点的值
					map.put(field, value);
				}
				
				return map;
			} catch (DocumentException ex2) {
				return null;
			}
			
		}catch(Exception ex1){
			return null;
		}
		
	}
	
   
}



