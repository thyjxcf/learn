package net.zdsoft.leadin.util;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * @author zhaosf
 * 
 */
@SuppressWarnings("unchecked")
public final class GenerateReportXml {

	private static Logger logger = LoggerFactory.getLogger(GenerateReportXml.class);

	/**
	 * 普通报表 根据Entity列表生成XML文件
	 * 
	 * @param rowList
	 * @return
	 */	
    public static String genXML(List rowList) {
		return genXMLData(rowList, null, "1", 1);
	}

	/**
	 * 根据列表生成XML文件,第一行为对应字段名
	 * 
	 * @param rowList
	 * @param type
	 * @return
	 */
	public static String genXMLWithFieldName(List rowList) {
		return genXMLData(rowList, null, "8", 1);
	}
	/**
	 * 根据列表生成XML文件,先分组 再分栏
	 * 
	 * @param rowList
	 * @param type
	 * @return
	 */
	public static String genXMLWithGroup(List<List> mapGroup, int numberOfRow,int count) {
		return genGroupXMLData(mapGroup, null, "1", numberOfRow,count);
	}

	/**
	 * 分栏报表 根据Entity列表生成XML文件
	 * 
	 * @param rowList
	 * @param numberOfRow
	 *            分栏数
	 * @return
	 */
	public static String genXML(List rowList, int numberOfRow) {
		return genXMLData(rowList, null, "1", numberOfRow);
	}

	/**
	 * 一般报表、主从报表 根据数据列表、字段名列表生成XML文件
	 * 
	 * @param rowList
	 * @param fieldList
	 * @param type
	 *            2 表示主数据列表、从数据列表(主从报表); 9 表示数据列表、字段名列表(一般报表)
	 * @return
	 */
	public static String genXML(List rowList, List fieldList, String type) {
		return genXMLData(rowList, fieldList, type, 1);
	}

	/**
	 * 根据数据列表、字段名列表生成XML文件
	 * 
	 * @param rowList
	 * @param fieldList
	 * @param type
	 *            2 表示主数据列表、从数据列表(主从报表); 9 表示数据列表、字段名列表(一般报表)
	 * @param numberOfRow
	 *            分栏数
	 * @return
	 */
	public static String genXML(List rowList, List fieldList, String type,
			int numberOfRow) {
		return genXMLData(rowList, fieldList, type, numberOfRow);
	}

	/**
	 * 根据数据列表，生成文本格式
	 * 
	 * @param rowList
	 * @return
	 */
	public static String genText(List rowList) {
		if(null == rowList) return "";
		
		// 数据列表
		StringBuffer sb = new StringBuffer();
		String colName = "";
		// 根据数据列表设置报表数据
		int totalRow = rowList.size();
		if (totalRow > 0) {
			int totalCol = 0;
			Field[] fields;

			try {
				fields = rowList.get(0).getClass().getDeclaredFields();
				Map fieldMap = new HashMap();
				// 如果是对象,则须展开
				for (int i = 0; i < fields.length; i++) {
					if (fields[i].getType().toString().indexOf("java.lang") < 1
							&& !fields[i].getType().isPrimitive()) {
						Field[] subfileds = fields[i].getType()
								.getDeclaredFields();
						if (subfileds.length > 0) {
							for (int j = 0; j < subfileds.length; j++) {
								// 去掉自定义的类字段
								if (subfileds[j].getType().toString().indexOf(
										"java.lang") < 1
										&& !subfileds[j].getType()
												.isPrimitive()) {
									subfileds[j] = null;
								}
							}
							fieldMap.put(fields[i].getName(), subfileds);
						}
					}
				}
				totalCol = fields.length;

				String nodeName = "";
				String subNodeName = "";
				String value = "";
				boolean sign = false;
				for (int i = 0; i < totalRow; i++) {
					sb.append("\r");
					sign = true;
					for (int j = 0; j < totalCol; j++) {
						nodeName = fields[j].getName();
						Object objectRtn = null;
						objectRtn = GetValueMethod.getValue(rowList.get(i),
								nodeName, new Class[] {},
								new Object[] {});
						// 判断是否是对象
						Field[] subfileds = (Field[])fieldMap.get(nodeName);

						if (subfileds != null) {
							if (objectRtn != null) {
								for (int k = 0; k < subfileds.length; k++) {
									if (subfileds[k] == null) {
										continue;
									}

									subNodeName = subfileds[k].getName();
									Object objectRtnSub = GetValueMethod.getValue(
											objectRtn,
											subNodeName,
											new Class[] {}, new Object[] {});
									if (objectRtnSub != null) {										
										//如果是日期型，则转化为字符串形式											
										if (subfileds[k].getType().toString().equals("class java.util.Date")) {
											value = DateUtils.date2String((Date)objectRtnSub);
										}else{
											value = objectRtnSub.toString();
										}
										if (i == 0) {
											colName += "\t" + subNodeName;
										}
										if (sign == true) {
											sb.append(value);
											sign = false;
										} else {
											sb.append("\t");
											sb.append(value);
										}
									}
								}
							}
						} else {
							if (objectRtn != null) {
								//如果是日期型，则转化为字符串形式											
								if (fields[j].getType().toString().equals("class java.util.Date")) {
									value = DateUtils.date2String((Date)objectRtn);
								}else{
									value = objectRtn.toString();
								}								
								if (i == 0) {
									colName += "\t" + nodeName;
								}
								if (j == 0) {
									sb.append(value);
								} else {
									sb.append("\t");
									sb.append(value);
								}
							}
						}
					}
				}

			} catch (SecurityException e) {
				logger.error("生成规定格式的TXT数据文件出错！", e);
			} catch (Exception ex) {
				logger.error( ex.toString());
			}
		}
		colName = colName.substring(1);
		sb.insert(0, colName);
		return sb.toString();
	}

	/**
	 * 根据数据列表、字段名列表生成XML文件
	 * 
	 * @param rowList
	 * @param fieldList
	 * @param type
	 *            1 表示一般的数据列表; 2 表示主数据列表、从数据列表; 8 表示数据列表,第一行为字段名; 9 表示数据列表、字段名列表
	 * @param numberOfRow
	 *            分栏数
	 * @return
	 */
	private static String genXMLData(List rowList, List fieldList, String type,
			int numberOfRow) {
		// xml数据
		String xmlData = "";

		try {
			// 格式化输出,类型IE浏览一样
			OutputFormat format = OutputFormat.createPrettyPrint();

			// 指定XML字符集编码
			format.setEncoding("GBK");

			// 设置对xml中text的内容保持原状，不去除多余的空格
			format.setTrimText(false);

			StringWriter sw = new StringWriter();
			XMLWriter xmlWriter = new XMLWriter(sw, format);

			// 根据已有的数据生成xml文件
			if (type.equals("1")) {
				xmlWriter.write(getDocument(rowList, true, numberOfRow));
			} else if (type.equals("2")) {
				xmlWriter
						.write(getDocumentByPS(rowList, fieldList, numberOfRow));
			} else if (type.equals("8")) {
				xmlWriter.write(getDocument(rowList, new ArrayList(), type));
			} else if (type.equals("9")) {
				xmlWriter.write(getDocument(rowList, fieldList, type));
			}

			xmlData = sw.toString();
			xmlWriter.close();
			sw.close();
		} catch (Exception ex) {
			logger.error("生成规定格式的XML数据文件出错！", ex);
		}
		return xmlData;
	}
	/**
	 * 根据数据列表、字段名列表生成XML文件
	 * 
	 * @param rowList
	 * @param fieldList
	 * @param type
	 *            1 表示一般的数据列表; 2 表示主数据列表、从数据列表; 8 表示数据列表,第一行为字段名; 9 表示数据列表、字段名列表
	 * @param numberOfRow
	 *            分栏数
	 * @return
	 */
	private static String genGroupXMLData(List<List>  mapGroup, List fieldList, String type,
			int numberOfRow,int counts) {
		// xml数据
		String xmlData = "";
		@SuppressWarnings("unused")
        String count=""+counts;
		try {
			// 格式化输出,类型IE浏览一样
			OutputFormat format = OutputFormat.createPrettyPrint();

			// 指定XML字符集编码
			format.setEncoding("GBK");

			// 设置对xml中text的内容保持原状，不去除多余的空格
			format.setTrimText(false);

			StringWriter sw = new StringWriter();
			XMLWriter xmlWriter = new XMLWriter(sw, format);
			// 数据列表
			Document document = DocumentHelper.createDocument();

			// 设置公用部分
			Element root = document.addElement("data");
			// 根据已有的数据生成xml文件
			int totalRow=0;
			Object[] returnObj=null;
			for(int i=0;i<mapGroup.size();i++){
				List rowList=mapGroup.get(i);
				totalRow+=rowList.size();
			returnObj=getDocument(rowList, true, numberOfRow,true,counts,root);
			
			counts=Integer.parseInt(returnObj[1]+"");
			}
			if(mapGroup.size()>0){
				root=(Element)returnObj[0];
				Element count8 = root.addElement("count");
				// 经计算过的总行数
				int calculateRow = (int) Math.ceil(Float.parseFloat(String.valueOf(totalRow)) / numberOfRow);
				count8.setText(String.valueOf(calculateRow));
					xmlWriter.write(document);
				}
				xmlData = sw.toString();
			xmlWriter.close();
			sw.close();
		} catch (Exception ex) {
			logger.error("生成规定格式的XML数据文件出错！", ex);
		}
		return xmlData;
	}
	/**
	 * 根据数据列表、字段名列表生成文档
	 * @param rowList
	 * @param fieldList
	 * @param type 
	 * @return
	 */
	private static Document getDocument(List rowList, List fieldList, String type) {
		// 数据列表
		Document document = DocumentHelper.createDocument();

		// 设置公用部分
		Element root = document.addElement("data");
		Element count = root.addElement("count");

		int totalRow = rowList.size();
		if (totalRow > 0) {
			try {
				//第一行为字段名
				if (type.equals("8")) {
					if(totalRow <= 1) return document;				
					fieldList = (List)rowList.get(0);
				}
				
				count.setText(String.valueOf(totalRow -1));
				String nodename = "";
				String value = "";

				int totalCol = fieldList.size();
                int intRow = 0;
				for (int i = 0; i < totalRow; i++) {
					
					if (type.equals("8")) {
						if(i == 0) continue;
						intRow = i;
					}else{
						intRow = i + 1;
					}
					
					Element row = root.addElement("row" + intRow);
					List cellList = (List) rowList.get(i);
					for (int j = 0; j < totalCol; j++) {
						Element childnode;

						nodename = String.valueOf(fieldList.get(j));
						Object obj = cellList.get(j);
						if(obj == null){
							value="";
						}else{
							value = String.valueOf(obj);
						}
						childnode = row.addElement(nodename);
						childnode.setText(value);
					}
				}
			} catch (Exception e) {
				logger.error(e.toString());
			}
		}
		return document;
	}

	/**
	 * 主从报表(principal and subordinate )
	 * 
	 * @param masterList
	 * @param detailList
	 * @return
	 */
	private static Document getDocumentByPS(List masterList, List detailList,
			int numberOfRow) {

		// 数据列表
		Document document = DocumentHelper.createDocument();

		// 设置公用部分
		Element root = document.addElement("data");

		// 主报表
		Element master = getDocument(masterList, false, 1)
				.getRootElement();
		master.setName("master");
		root.add(master);

		// 从报表
		Element detail = getDocument(detailList, true, numberOfRow)
				.getRootElement();
		detail.setName("detail");
		root.add(detail);

		return document;
	}
	private static Object[] getDocument(List rowList, boolean rowSign,
			int numberOfRow,boolean group,int counts,Element root) {
		
		

		// 根据数据列表设置报表数据
		if(null == rowList) return new Object[]{};		
		int totalRow = rowList.size();
		int returnCount=0;
		if (totalRow > 0) {
			int totalCol = 0;
			Field[] fields;

			try {
				fields = rowList.get(0).getClass().getDeclaredFields();
				Map fieldMap = new HashMap();
				// 如果是对象,则须展开
				for (int i = 0; i < fields.length; i++) {
					if (fields[i].getType().toString().indexOf("java.") < 1
							&& !fields[i].getType().isPrimitive()) {
						Field[] subfileds = fields[i].getType()
								.getDeclaredFields();
						if (subfileds.length > 0) {
							for (int j = 0; j < subfileds.length; j++) {
								// 去掉自定义的类字段
								if (subfileds[j].getType().toString().indexOf(
										"java.lang") < 1
										&& !subfileds[j].getType()
												.isPrimitive()) {
									subfileds[j] = null;
								}
							}
							fieldMap.put(fields[i].getName(), subfileds);
						}
					}
				}
				totalCol = fields.length;

				

				String nodename = "";
				String subNodeName = "";
				String value = "";
				int intRow = counts;
				for (int i = 0; i < totalRow; i += numberOfRow) {
					intRow += 1;
					Element row;
					if (rowSign) {
						row = root.addElement("row" + intRow);
					} else {
						row = root;
					}

					for (int m = 0; m < numberOfRow; m++) {
						// 将i加上m
						// i = +m;
						for (int j = 0; j < totalCol; j++) {
							if(i + m >= totalRow) continue;
							Element childnode;
							nodename = fields[j].getName();
							Object objectRtn = null;
							objectRtn = GetValueMethod.getValue(rowList.get(i + m),
									nodename, new Class[] {},
									new Object[] {});
							// 判断是否是对象
							Field[] subfileds = (Field[])fieldMap.get(nodename);
							if (subfileds != null) {
								if (objectRtn != null) {
									for (int k = 0; k < subfileds.length; k++) {
										if (subfileds[k] == null) {
											continue;
										}
										subNodeName = subfileds[k].getName();
										Object objectRtnSub = GetValueMethod
												.getValue(
														objectRtn,
														subNodeName,
														new Class[] {},
														new Object[] {});
										if (objectRtnSub != null) {
											//如果是日期型，则转化为字符串形式											
											if (subfileds[k].getType().toString().equals("class java.util.Date")) {
												value = DateUtils.date2String((Date)objectRtnSub);
											}else{
												value = objectRtnSub.toString();
											}
										}else{
											value = "";
										}
										if (m == 0) {
											childnode = row
													.addElement(subNodeName);
										} else {
											childnode = row
													.addElement(subNodeName
															+ String
																	.valueOf(m));
										}
										childnode.setText(value);
										
									}
								}
							} else {
								if (objectRtn != null) {
									//如果是日期型，则转化为字符串形式											
									if (fields[j].getType().toString().equals("class java.util.Date")) {
										value = DateUtils.date2StringByDay((Date)objectRtn);
									}else{
										value = objectRtn.toString();
									}
								}else{
									value = "";
								}
								if (m == 0) {
									childnode = row.addElement(nodename);
								} else {
									childnode = row.addElement(nodename
											+ String.valueOf(m));
								}
								childnode.setText(value);
							}
						}
					}
				}
				returnCount=intRow;
			} catch (SecurityException e) {
				logger.error(e.toString());
			} catch (Exception ex) {
				logger.error(ex.toString());
			}
		}
		//document.remove(root);
		Object[] obGroup={root,returnCount};
		return obGroup;
	}
	/**
	 * 根据数据列表生成XML
	 * 
	 * @param rowList
	 * @param rowSign
	 *            表示是否有row节点
	 * @param numberOfRow
	 *            分栏数
	 * @return
	 */
	private static Document getDocument(List rowList, boolean rowSign,
			int numberOfRow) {
		
		// 数据列表
		Document document = DocumentHelper.createDocument();

		// 设置公用部分
		Element root = document.addElement("data");
		Element count = root.addElement("count");

		// 根据数据列表设置报表数据
		if(null == rowList) return document;		
		int totalRow = rowList.size();
		if (totalRow > 0) {
			int totalCol = 0;
			Field[] fields;
			List<Field> fieldList = new ArrayList<Field>();
			Map fieldMap = new HashMap();
			try {
			    Class clazz = rowList.get(0).getClass();
			    for (; !clazz.equals(Object.class); clazz = clazz.getSuperclass()) {
			        fields = clazz.getDeclaredFields();
			        
			        // 如果是对象,则须展开
	                for (int i = 0; i < fields.length; i++) {
	                    fieldList.add(fields[i]);
	                    
	                    if (fields[i].getType().toString().indexOf("java.") < 1
	                            && !fields[i].getType().isPrimitive()) {
	                        Field[] subfileds = fields[i].getType()
	                                .getDeclaredFields();
	                        if (subfileds.length > 0) {
	                            for (int j = 0; j < subfileds.length; j++) {
	                                // 去掉自定义的类字段
	                                if (subfileds[j].getType().toString().indexOf(
	                                        "java.lang") < 1
	                                        && !subfileds[j].getType()
	                                                .isPrimitive()) {
	                                    subfileds[j] = null;
	                                }
	                            }
	                            fieldMap.put(fields[i].getName(), subfileds);
	                        }
	                    }
	                }
                }
			
				totalCol = fieldList.size();

				// 经计算过的总行数
				int calculateRow = (int) Math.ceil(Float.parseFloat(String.valueOf(totalRow)) / numberOfRow);
				count.setText(String.valueOf(calculateRow));

				String nodename = "";
				String subNodeName = "";
				String value = "";
				int intRow = 0;
				for (int i = 0; i < totalRow; i += numberOfRow) {
					intRow += 1;
					Element row;
					if (rowSign) {
						row = root.addElement("row" + intRow);
					} else {
						row = root;
					}

					for (int m = 0; m < numberOfRow; m++) {
						// 将i加上m
						// i = +m;
						for (int j = 0; j < totalCol; j++) {
							if(i + m >= totalRow) continue;
							Element childnode;
							nodename = fieldList.get(j).getName();
							Object objectRtn = null;
							objectRtn = GetValueMethod.getValue(rowList.get(i + m),
									nodename, new Class[] {},
									new Object[] {});
							// 判断是否是对象
							Field[] subfileds = (Field[])fieldMap.get(nodename);
							if (subfileds != null) {
								if (objectRtn != null) {
									for (int k = 0; k < subfileds.length; k++) {
										if (subfileds[k] == null) {
											continue;
										}
										subNodeName = subfileds[k].getName();
										Object objectRtnSub = GetValueMethod
												.getValue(
														objectRtn,
														subNodeName,
														new Class[] {},
														new Object[] {});
										if (objectRtnSub != null) {
											//如果是日期型，则转化为字符串形式											
											if (subfileds[k].getType().toString().equals("class java.util.Date")) {
												value = DateUtils.date2String((Date)objectRtnSub);
											}else{
												value = objectRtnSub.toString();
											}
										}else{
											value = "";
										}
										if (m == 0) {
											childnode = row
													.addElement(subNodeName);
										} else {
											childnode = row
													.addElement(subNodeName
															+ String
																	.valueOf(m));
										}
										childnode.setText(value);
										
									}
								}
							} else {
								if (objectRtn != null) {
									//如果是日期型，则转化为字符串形式											
									if (fieldList.get(j).getType().toString().equals("class java.util.Date")) {
										value = DateUtils.date2StringByDay((Date)objectRtn);
									}else{
										value = objectRtn.toString();
									}
								}else{
									value = "";
								}
								if (m == 0) {
									childnode = row.addElement(nodename);
								} else {
									childnode = row.addElement(nodename
											+ String.valueOf(m));
								}
								childnode.setText(value);
							}
						}
					}
				}

			} catch (SecurityException e) {
				logger.error(e.toString());
			} catch (Exception ex) {
				logger.error(ex.toString());
			}
		}
		return document;
	}
 
	
}
