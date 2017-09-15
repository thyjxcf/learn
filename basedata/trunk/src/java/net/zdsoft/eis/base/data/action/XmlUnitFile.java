package net.zdsoft.eis.base.data.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import net.zdsoft.keel.util.FileUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.ServletActionContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

/**
 * <p>
 * Title: EZNET3.0单位远程离线注册
 * </p>
 * <p>
 * Description: 提供单位导出数据保存为Xml文件的方法
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: ZDsoft
 * </p>
 * 
 * @author lvl
 * @version 1.0
 */

public class XmlUnitFile {
    public static final String ROOTELEMENTSTR = "main";
    public static final String FIRSTELEMENTSTR = "registers";
    public static final String SECONDELEMENTSTR = "register";
    public static final String UNITELEMENTSTR = "unit";
    public static final String USERELEMENTSTR = "user";

    private Element rootElement;

    public XmlUnitFile() {
    }

    /**
     * 得到注册文件的单位信息
     * 
     * @param xmlfile
     *            String
     * @throws Exception
     * @return List<XmlUnitInfo>
     */    
    public List<XmlUnitInfo> getUnitInfoList(File file) throws Exception {
        List<XmlUnitInfo> xmlList = new ArrayList<XmlUnitInfo>();
        try {
            SAXBuilder saxbuilder = new SAXBuilder();
            Document jdoc = saxbuilder.build(file);
            Element root = jdoc.getRootElement();
            
            @SuppressWarnings("unchecked")
            List list = XPath.selectNodes(root, "/" + ROOTELEMENTSTR + "/"
                    + FIRSTELEMENTSTR + "/" + SECONDELEMENTSTR);
            if (CollectionUtils.isEmpty(list)) {
                return xmlList;
            }

            Element cellElement, unitElement, userElement;
            XmlUnitInfo xmlUnitInfo;
            for (int i = 0; i < list.size(); i++) {
                cellElement = (Element) list.get(i);
                xmlUnitInfo = new XmlUnitInfo();
                unitElement = (Element) XPath.selectSingleNode(cellElement,
                        UNITELEMENTSTR);
                xmlUnitInfo.setUnitDtoElement(unitElement);

                userElement = (Element) XPath.selectSingleNode(cellElement,
                        USERELEMENTSTR);
                xmlUnitInfo.setUserDtoElement(userElement);

                xmlList.add(xmlUnitInfo);
            }
            return xmlList;
        }
        catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 生成注册单位数据的xml内容
     * 
     * @param unitlist
     *            List
     */
    public void buildXmlContent(List<XmlUnitInfo> unitlist) throws Exception {
        try {
            rootElement = new Element(ROOTELEMENTSTR);
            Element registersElement = new Element(FIRSTELEMENTSTR);

            // unitsEle.setAttribute("type", "edu");
            // unitsEle.setAttribute("productId", productid);
            // unitsEle.setAttribute("version", version);

            Element registerElement, unitElement, userElement;
            if (CollectionUtils.isEmpty(unitlist)) {
                return;
            }

            for (XmlUnitInfo xmlInfo : unitlist) {
                unitElement = xmlInfo.getUnitElement();
                userElement = xmlInfo.getUserElement();

                registerElement = new Element(SECONDELEMENTSTR);
                registerElement.addContent(unitElement);
                registerElement.addContent(userElement);

                registersElement.addContent(registerElement);
            }
            rootElement.addContent(registersElement);
        }
        catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 内容保存到指定文件
     * 
     * @param fileName
     *            String
     * @return boolean
     */
    public void saveAsFile(String fileName) throws Exception {
        OutputStream os = null;
        try {
            // XMLOutputter out = new XMLOutputter(" ", true);
            Format f = Format.getCompactFormat();
            f.setEncoding("UTF-8");
            f.setIndent(" ");
            XMLOutputter out = new XMLOutputter(f);
            Document doc = new Document();
            doc.setRootElement(rootElement);
            if (doc != null) {

                // out.setEncoding("UTF-8");
                // out.setIndent(" ");
                // out.setNewlines(true);
                ServletActionContext.getResponse().setHeader("Cache-Control",
                        "");
                ServletActionContext.getResponse().setContentType(
                        "application/data;charset=UTF-8");
                ServletActionContext.getResponse().setHeader(
                        "Content-Disposition",
                        "attachment; filename=" + fileName);
                os = ServletActionContext.getResponse().getOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(os);
                out.output(doc, bos);
                bos.flush();
                bos.close();
            }
        }
        catch (IOException ex) {
            // ingore
        }
        finally {
            FileUtils.close(os);
        }
    }

}
