package net.zdsoft.eis.frame.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import net.zdsoft.leadin.util.ConfigFileUtils;

public final class DeleteXMLDecoder {
    private static final Logger log = LoggerFactory.getLogger(DeleteXMLDecoder.class);

    private static final String UPDATE_PREFIX = "UPDATE ";
    private static final String DELETE_PREFIX = "DELETE FROM ";
    
    @SuppressWarnings("unchecked")
    public static List<List<String>> decodeDeleteXml(String parseFile) {
        List<List<String>> rtnList = new ArrayList<List<String>>(3);
        rtnList.add(new ArrayList<String>());
        rtnList.add(new ArrayList<String>());
        rtnList.add(new ArrayList<String>());
        StringBuffer sb = new StringBuffer();
        List<String> delList = new ArrayList<String>();
        List<String> updUList = new ArrayList<String>();
        List<String> updMList = new ArrayList<String>();
        try {
            List<URL> urls = ConfigFileUtils.getConfigFileUrls(parseFile);
            for (URL url : urls) {
                SAXReader sysSaxReader = new SAXReader();
                Document sysdoc = sysSaxReader.read(url);
                Element sysRootElement = sysdoc.getRootElement();

                Iterator deleteTablesIte = sysRootElement.elementIterator("deleteTables");
                while (deleteTablesIte.hasNext()) {
                    Element tablesEle = (Element) deleteTablesIte.next();
                    Iterator tableIte = tablesEle.elementIterator("table");
                    while (tableIte.hasNext()) {
                        Element tableEle = (Element) tableIte.next();
                        sb.append(DELETE_PREFIX);
                        sb.append(tableEle.attributeValue("name") + " WHERE ");
                        sb.append(tableEle.attributeValue("field"));
                        delList.add(sb.toString());
                        sb = sb.delete(0, sb.length());
                    }
                }

                Iterator updateTablesUIte = sysRootElement
                        .elementIterator("updateTables-updatestamp");
                while (updateTablesUIte.hasNext()) {
                    Element tablesEle = (Element) updateTablesUIte.next();
                    Iterator tableIte = tablesEle.elementIterator("table");
                    while (tableIte.hasNext()) {
                        Element tableEle = (Element) tableIte.next();
                        sb.append(UPDATE_PREFIX);
                        sb.append(tableEle.attributeValue("name") + " SET ");

                        Iterator fieldIte = tableEle.elementIterator("field");
                        Element fieldEle;
                        while (fieldIte.hasNext()) {
                            fieldEle = (Element) fieldIte.next();
                            sb.append(fieldEle.attributeValue("name") + "=");
                            sb.append(fieldEle.attributeValue("value") + ",");
                        }
                        sb = sb.delete(sb.length() - 1, sb.length());
                        sb.append(" WHERE " + tableEle.attributeValue("field"));

                        updUList.add(sb.toString());
                        sb = sb.delete(0, sb.length());
                    }
                }

                Iterator updateTablesMIte = sysRootElement
                        .elementIterator("updateTables-modifytime");
                while (updateTablesMIte.hasNext()) {
                    Element tablesEle = (Element) updateTablesMIte.next();
                    Iterator tableIte = tablesEle.elementIterator("table");
                    while (tableIte.hasNext()) {
                        Element tableEle = (Element) tableIte.next();
                        sb.append(UPDATE_PREFIX);
                        sb.append(tableEle.attributeValue("name") + " SET ");

                        Iterator fieldIte = tableEle.elementIterator("field");
                        Element fieldEle;
                        while (fieldIte.hasNext()) {
                            fieldEle = (Element) fieldIte.next();
                            sb.append(fieldEle.attributeValue("name") + "=");
                            sb.append(fieldEle.attributeValue("value") + ",");
                        }
                        sb = sb.delete(sb.length() - 1, sb.length());
                        sb.append(" WHERE " + tableEle.attributeValue("field"));

                        updMList.add(sb.toString());
                        sb = sb.delete(0, sb.length());
                    }
                }

            }

        } catch (DocumentException e) {
            log.error(e.toString());
        }
        rtnList.set(0, delList);
        rtnList.set(1, updUList);
        rtnList.set(2, updMList);
        return rtnList;
    }

}
