package net.zdsoft.eis.base.data.action;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Validators;

/**
 * <p>
 * Title: EZNET3.0单位远程离线注册
 * </p>
 * <p>
 * Description: 定义了注册文件的UnitInfo对象
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

public class XmlUnitInfo {
    public static final String UNITDTO_ID = "id";
    public static final String UNITDTO_NAME = "name";
    public static final String UNITDTO_UNIONID = "unionid";
    public static final String UNITDTO_REGCODE = "regcode";
    public static final String UNITDTO_UNITCLASS = "unitclass";
    public static final String UNITDTO_UNITTYPE = "unittype";
    public static final String UNITDTO_CREATEDATE = "createdate";
    public static final String UNITDTO_MARK = "mark";
    public static final String UNITDTO_PARENTID = "parentid";
    public static final String UNITDTO_ORDERID = "orderid";
    public static final String UNITDTO_USETYPE = "usetype";
    public static final String UNITDTO_AUTHORIZED = "authorized";
    public static final String UNITDTO_REGIONLEVEL = "regionlevel";

    private String[] unitProperties = new String[] { UNITDTO_ID, UNITDTO_NAME,
            UNITDTO_UNIONID, UNITDTO_REGCODE, UNITDTO_UNITCLASS,
            UNITDTO_UNITTYPE, UNITDTO_CREATEDATE, UNITDTO_MARK,
            UNITDTO_PARENTID, UNITDTO_ORDERID, UNITDTO_USETYPE,
            UNITDTO_AUTHORIZED, UNITDTO_REGIONLEVEL };

    public static final String USERDTO_ID = "id";
    public static final String USERDTO_NAME = "name";
    public static final String USERDTO_PASSWORD = "password";
    public static final String USERDTO_MARK = "mark";
    public static final String USERDTO_TYPE = "type";
    public static final String USERDTO_REALNAME = "realname";
    public static final String USERDTO_EMPLEEID = "empleeid";
    public static final String USERDTO_UNITID = "unitid";
    public static final String USERDTO_GROUPID = "groupid";
    public static final String USERDTO_EMAIL = "email";
    public static final String USERDTO_CREATEDATE = "createdate";
    public static final String USERDTO_ISDELETED = "isdeleted";
    public static final String USERDTO_ORDERID = "orderid";
    public static final String USERDTO_PANELTYPE = "paneltype";
    public static final String USERDTO_UNITINTID = "unitintid";
    public static final String USERDTO_GROUPINTID = "groupintid";
    public static final String USERDTO_GUID = "guid";

    private String[] userProperties = new String[] { USERDTO_ID, USERDTO_NAME,
            USERDTO_PASSWORD, USERDTO_MARK, USERDTO_TYPE, USERDTO_REALNAME,
            USERDTO_EMPLEEID, USERDTO_UNITID, USERDTO_GROUPID, USERDTO_EMAIL,
            USERDTO_CREATEDATE, USERDTO_ISDELETED, USERDTO_ORDERID,
            USERDTO_PANELTYPE, USERDTO_UNITINTID, USERDTO_GROUPINTID,
            USERDTO_GUID };

    private BaseUnit unitDto;
    private User userDto;

    public XmlUnitInfo() {
    }

    public BaseUnit getUnitDto() {
        return unitDto;
    }

    public void setUnitDto(BaseUnit unitDto) {
        this.unitDto = unitDto;
    }

    public User getUserDto() {
        return userDto;
    }

    public void setUserDto(User userDto) {
        this.userDto = userDto;
    }

    public Element getUnitElement() {
        Element element = new Element(XmlUnitFile.UNITELEMENTSTR);

        Element cellElement;
        for (int i = 0; i < unitProperties.length; i++) {

            try {
                cellElement = new Element(unitProperties[i]);
                cellElement.setText(BeanUtils.getProperty(getUnitDto(),
                        unitProperties[i]));
                element.addContent(cellElement);
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return element;
    }

    public Element getUserElement() {
        Element element = new Element(XmlUnitFile.USERELEMENTSTR);

        Element cellElemnt;
        for (int i = 0; i < userProperties.length; i++) {

            try {
                cellElemnt = new Element(userProperties[i]);
                cellElemnt.setText(BeanUtils.getProperty(getUserDto(),
                        userProperties[i]));
                element.addContent(cellElemnt);
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }
        return element;
    }

    public void setUserDtoElement(Element userElement) throws Exception {
        if (userElement == null) {
            return;
        }
        userDto = new User();
        Element elem;
        String text;
        for (int i = 0; i < userProperties.length; i++) {
            elem = (Element) XPath.selectSingleNode(userElement,
                    userProperties[i]);
            if (elem == null) {
                continue;
            }
            text = elem.getText();
            if (!Validators.isEmpty(text)) {
                if (!userProperties[i].equals(USERDTO_CREATEDATE)) {
                    BeanUtils.setProperty(userDto, userProperties[i], text);
                }
                else {
                    BeanUtils.setProperty(userDto, userProperties[i], DateUtils
                            .string2Date(text));
                }
            }
        }
    }

    public void setUnitDtoElement(Element unitElement) throws Exception {
        if (unitElement == null) {
            return;
        }
        unitDto = new BaseUnit();
        Element elem;
        String text;
        for (int i = 0; i < unitProperties.length; i++) {
            elem = (Element) XPath.selectSingleNode(unitElement,
                    unitProperties[i]);
            if (elem == null) {
                continue;
            }
            text = elem.getText();
            if (!Validators.isEmpty(text)) {
                if (!unitProperties[i].equals(UNITDTO_CREATEDATE)) {
                    BeanUtils.setProperty(unitDto, unitProperties[i], elem
                            .getText());
                }
                else {
                    BeanUtils.setProperty(unitDto, unitProperties[i], DateUtils
                            .string2DateTime(text));
                }
            }
        }
    }
}
