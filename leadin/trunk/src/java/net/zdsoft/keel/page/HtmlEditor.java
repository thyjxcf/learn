/* 
 * @(#)HtmlEditor.java    Created on 2006-6-21
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/keel/src/net/zdsoft/keel/page/HtmlEditor.java,v 1.9 2007/07/11 09:16:02 liangxiao Exp $
 */
package net.zdsoft.keel.page;

import net.zdsoft.keel.util.StringUtils;

/**
 * html在线编辑器
 * 
 * @author liangxiao
 * @version $Revision: 1.9 $, $Date: 2007/07/11 09:16:02 $
 */
public class HtmlEditor {

    public static final String DEFAULT_BG_COLOR = "#D4D0C8";

    private String html;

    /**
     * 构造方法
     * 
     * @param filePath
     *            资源文件路径，比如：../editor
     * @param content
     *            预显示的内容
     */
    public HtmlEditor(String filePath, String content) {
        generateHtml(filePath, content, DEFAULT_BG_COLOR, null, null);
    }

    /**
     * 构造方法
     * 
     * @param filePath
     *            资源文件路径，比如：../editor
     * @param content
     *            预显示的内容
     * @param bgColor
     *            背景色
     */
    public HtmlEditor(String filePath, String content, String bgColor) {
        generateHtml(filePath, content, bgColor, null, null);
    }

    /**
     * @param filePath
     *            资源文件路径，比如：../editor
     * @param content
     *            预显示的内容
     * @param bgColor
     *            背景色
     * @param designModeCss
     *            设计模式样式表文件
     * @param designModeBodyTagCssClass
     *            设计模式<body>标签使用的样式名称
     */
    public HtmlEditor(String filePath, String content, String bgColor,
            String designModeCss, String designModeBodyTagCssClass) {
        generateHtml(filePath, content, bgColor, designModeCss,
                designModeBodyTagCssClass);
    }

    /**
     * 取得编辑器的id
     * 
     * @return
     */
    public String getId() {
        return "ctl00_bcr_FreeTextBox1";
    }

    private void generateHtml(String filePath, String content, String bgColor,
            String designModeCss, String designModeBodyTagCssClass) {
        StringBuffer htmlBuffer = new StringBuffer();
        // htmlBuffer
        // .append("<style type=\"text/css\"> body { font-size: 84%; }
        // </style>\n");
        htmlBuffer.append("<script src=\"" + filePath
                + "/global.js\" type=\"text/javascript\"></script>\n");
        htmlBuffer.append("<style type=\"text/css\">\n");
        htmlBuffer.append(".ctl00_bcr_FreeTextBox1_OuterTable {\n");
        htmlBuffer.append(" width: 600px;\n");
        htmlBuffer.append(" background-color: " + bgColor + ";\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append(".ctl00_bcr_FreeTextBox1_OuterTable tr {\n");
        htmlBuffer.append(" background-color: " + bgColor + ";\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append(".ctl00_bcr_FreeTextBox1_OuterTable td {\n");
        htmlBuffer.append(" background-color: " + bgColor + ";\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append("#ctl00_bcr_FreeTextBox1_toolbarArea select {\n");
        htmlBuffer.append(" margin: 0px;\n");
        htmlBuffer.append(" padding: 0px;\n");
        htmlBuffer.append(" font: 11px Tahoma,Verdana,sans-serif;\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append(".ctl00_bcr_FreeTextBox1_HtmlBox {\n");
        htmlBuffer.append(" overflow: auto;\n");
        htmlBuffer.append(" font-family: Courier New, Courier;\n");
        htmlBuffer.append(" padding: 4px;\n");
        htmlBuffer.append(" border-right: 1px solid #808080;\n");
        htmlBuffer.append(" border-left: 1px solid #808080;\n");
        htmlBuffer.append(" border-top: 1px solid #808080;\n");
        htmlBuffer.append(" border-bottom: 1px solid #808080;\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append(".ctl00_bcr_FreeTextBox1_DesignBox {\n");
        htmlBuffer.append(" background-color: #FFFFFF;\n");
        htmlBuffer.append(" border: 0; \n");
        htmlBuffer.append(" border-right: 1px solid #808080;\n");
        htmlBuffer.append(" border-left: 1px solid #808080;\n");
        htmlBuffer.append(" border-top: 1px solid #808080;\n");
        htmlBuffer.append(" border-bottom: 1px solid #808080;\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append(".ctl00_bcr_FreeTextBox1_DesignBox body {\n");
        htmlBuffer.append(" background-color: black;\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append(".ctl00_bcr_FreeTextBox1_Toolbar {\n");
        htmlBuffer.append(" margin-bottom: 1px; \n");
        htmlBuffer.append(" margin-right: 2px;\n");
        htmlBuffer.append(" float: left;\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append(".ctl00_bcr_FreeTextBox1_Button_Off_Out {\n");
        htmlBuffer.append(" padding: 1px; \n");
        htmlBuffer.append(" background-color: transparent;\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append(".ctl00_bcr_FreeTextBox1_Button_Off_Over {\n");
        htmlBuffer.append(" padding: 0px;\n");
        htmlBuffer.append(" border-top: 1px solid #3169C6;  \n");
        htmlBuffer.append(" border-left: 1px solid #3169C6;\n");
        htmlBuffer.append(" border-right: 1px solid #3169C6;\n");
        htmlBuffer.append(" border-bottom: 1px solid #3169C6;  \n");
        htmlBuffer.append(" background-color: #B5BDD6;\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append(".ctl00_bcr_FreeTextBox1_Button_On_Out {\n");
        htmlBuffer.append(" padding: 0px;\n");
        htmlBuffer.append(" border-top: 1px solid #3169C6;  \n");
        htmlBuffer.append(" border-left: 1px solid #3169C6;\n");
        htmlBuffer.append(" border-right: 1px solid #3169C6;\n");
        htmlBuffer.append(" border-bottom: 1px solid #3169C6;  \n");
        htmlBuffer.append(" background-color: #D6D6DE;\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append(".ctl00_bcr_FreeTextBox1_Button_On_Over {\n");
        htmlBuffer.append(" padding: 0px;\n");
        htmlBuffer.append(" border-top: 1px solid #3169C6;  \n");
        htmlBuffer.append(" border-left: 1px solid #3169C6;\n");
        htmlBuffer.append(" border-right: 1px solid #3169C6;\n");
        htmlBuffer.append(" border-bottom: 1px solid #3169C6;  \n");
        htmlBuffer.append(" background-color: #8494B5;\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append(".ctl00_bcr_FreeTextBox1_StartTabOn {\n");
        htmlBuffer.append(" font: 10pt MS Sans Serif;\n");
        htmlBuffer.append(" padding: 1px;\n");
        htmlBuffer.append(" border-left: 1px solid #BFBCB6;\n");
        htmlBuffer.append(" border-right: 1px solid #FFFFFF;\n");
        htmlBuffer.append(" border-top: 1px solid #808080;\n");
        htmlBuffer.append(" border-bottom: 1px solid #BFBCB6;\n");
        htmlBuffer.append(" background-color: #BFBCB6;\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append(".ctl00_bcr_FreeTextBox1_StartTabOff {\n");
        htmlBuffer.append(" font: 10pt MS Sans Serif;\n");
        htmlBuffer.append(" padding:1px;\n");
        htmlBuffer.append(" border-left: 1px solid #BFBCB6;\n");
        htmlBuffer.append(" border-right: 1px solid #808080;\n");
        htmlBuffer.append(" border-top: 1px solid #808080;\n");
        htmlBuffer.append(" border-bottom: 1px solid #BFBCB6;\n");
        htmlBuffer.append(" background-color: #BFBCB6;\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append(".ctl00_bcr_FreeTextBox1_TabOn {\n");
        htmlBuffer.append(" font: 8pt MS Sans Serif;\n");
        htmlBuffer.append(" padding:1px;\n");
        htmlBuffer.append(" padding-left:5px;\n");
        htmlBuffer.append(" padding-right:5px;\n");
        htmlBuffer.append(" border-left: 1px solid #FFFFFF;\n");
        htmlBuffer.append(" border-right: 1px solid #808080;\n");
        htmlBuffer.append(" border-top: 1px solid " + bgColor + ";\n");
        htmlBuffer.append(" border-bottom: 1px solid #808080;\n");
        htmlBuffer.append(" background-color: " + bgColor + ";\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append(".ctl00_bcr_FreeTextBox1_TabOffRight {\n");
        htmlBuffer.append(" font: 8pt MS Sans Serif;\n");
        htmlBuffer.append(" padding:1px;\n");
        htmlBuffer.append(" padding-left:5px;\n");
        htmlBuffer.append(" padding-right:5px;\n");
        htmlBuffer.append(" border-left: 1px solid #808080;\n");
        htmlBuffer.append(" border-right: 1px solid #808080;\n");
        htmlBuffer.append(" border-top: 1px solid #808080;\n");
        htmlBuffer.append(" border-bottom: 1px solid #BFBCB6;\n");
        htmlBuffer.append(" background-color: #BFBCB6;\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append(".ctl00_bcr_FreeTextBox1_TabOffLeft {\n");
        htmlBuffer.append(" font: 8pt MS Sans Serif;\n");
        htmlBuffer.append(" padding:1px;\n");
        htmlBuffer.append(" padding-left:5px;\n");
        htmlBuffer.append(" padding-right:5px;\n");
        htmlBuffer.append(" border-left: 1px solid #808080;\n");
        htmlBuffer.append(" border-right: 1px solid #FFFFFF;\n");
        htmlBuffer.append(" border-top: 1px solid #808080;\n");
        htmlBuffer.append(" border-bottom: 1px solid " + bgColor + ";\n");
        htmlBuffer.append(" background-color: #BFBCB6;\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append(".ctl00_bcr_FreeTextBox1_EndTab {\n");
        htmlBuffer.append(" font: 10pt MS Sans Serif;\n");
        htmlBuffer.append(" width: 100%;\n");
        htmlBuffer.append(" padding:1px;\n");
        htmlBuffer.append(" border-left: 1px solid #BFBCB6;\n");
        htmlBuffer.append(" border-right: 1px solid #BFBCB6;\n");
        htmlBuffer.append(" border-top: 1px solid #808080;\n");
        htmlBuffer.append(" border-bottom: 1px solid #BFBCB6;\n");
        htmlBuffer.append(" background-color: #BFBCB6;\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append(".ctl00_bcr_FreeTextBox1_AncestorArea {\n");
        htmlBuffer.append(" margin-left: 4px;\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append(".ctl00_bcr_FreeTextBox1_AncestorArea a {\n");
        htmlBuffer.append(" padding: 1px;\n");
        htmlBuffer.append(" margin-left: 2px;\n");
        htmlBuffer.append(" margin-right: 2px;\n");
        htmlBuffer.append(" border: 1px solid #808080;  \n");
        htmlBuffer.append(" color: #000;\n");
        htmlBuffer.append(" font-family: arial;\n");
        htmlBuffer.append(" font-size: 11px;\n");
        htmlBuffer.append("}\n");
        htmlBuffer
                .append(".ctl00_bcr_FreeTextBox1_AncestorArea a:link, .ctl00_bcr_FreeTextBox1_AncestorArea a:visited, .ctl00_bcr_FreeTextBox1_AncestorArea a:active {\n");
        htmlBuffer.append(" background-color: transparent;\n");
        htmlBuffer.append(" text-decoration: none;\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append(".ctl00_bcr_FreeTextBox1_AncestorArea a:hover {\n");
        htmlBuffer.append(" text-decoration: none;\n");
        htmlBuffer.append(" background-color: #316AC5;\n");
        htmlBuffer.append(" border: 1px solid #fff;\n");
        htmlBuffer.append(" color:#fff;\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append("</style>\n");
        htmlBuffer.append("<script type=\"text/javascript\" src=\"" + filePath
                + "/WebResource.axd\"></script>\n");
        htmlBuffer.append("<script type=\"text/javascript\" src=\"" + filePath
                + "/WebResource_003.axd\"></script>\n");
        htmlBuffer.append("<script type=\"text/javascript\" src=\"" + filePath
                + "/WebResource_002.axd\"></script>\n");
        htmlBuffer.append("<script type=\"text/javascript\" src=\"" + filePath
                + "/WebResource_004.axd\"></script>\n");
        htmlBuffer.append("<script type=\"text/javascript\">\n");
        htmlBuffer.append("<!--\n");
        htmlBuffer.append("function WebForm_OnSubmit() {\n");
        htmlBuffer.append("FTB_API['ctl00_bcr_FreeTextBox1'].StoreHtml();\n");
        htmlBuffer.append("return true;\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append("// -->\n");
        htmlBuffer.append("</script>\n");
        htmlBuffer
                .append("<table class=\"ctl00_bcr_FreeTextBox1_OuterTable\" cellpadding=\"2\" cellspacing=\"0\"><tbody><tr><td>\n");
        htmlBuffer.append("<div>\n");
        htmlBuffer
                .append("<div id=\"ctl00_bcr_FreeTextBox1_toolbarArea\" style=\"padding-bottom: 2px; clear: both;\">\n");
        htmlBuffer.append("<div class=\"ctl00_bcr_FreeTextBox1_Toolbar\">\n");
        htmlBuffer
                .append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr><td border=\"0\" unselectable=\"on\"><img src=\""
                        + filePath
                        + "/WebResource_002.gif\" align=\"middle\"></td><td><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr><td style=\"padding-left: 4px;\" unselectable=\"on\"><select id=\"ctl00_bcr_FreeTextBox1_0_0\" tabindex=\"-1\">\n");
        htmlBuffer.append("<option value=\"\">段落</option>\n");
        htmlBuffer.append("<option value=\"&lt;p&gt;\">普通格式</option>\n");
        htmlBuffer.append("<option value=\"&lt;h1&gt;\">标题 1</option>\n");
        htmlBuffer.append("<option value=\"&lt;h2&gt;\">标题 2</option>\n");
        htmlBuffer.append("<option value=\"&lt;h3&gt;\">标题 3</option>\n");
        htmlBuffer.append("<option value=\"&lt;h4&gt;\">标题 4</option>\n");
        htmlBuffer.append("<option value=\"&lt;h5&gt;\">标题 5</option>\n");
        htmlBuffer.append("<option value=\"&lt;h6&gt;\">标题 6</option>\n");
        htmlBuffer.append("<option value=\"&lt;pre&gt;\">已编排格式</option>\n");
        htmlBuffer.append("<option value=\"&lt;address&gt;\">地址</option>\n");
        htmlBuffer
                .append("</select></td><td style=\"padding-left: 4px;\" unselectable=\"on\"><select id=\"ctl00_bcr_FreeTextBox1_0_1\" tabindex=\"-1\">\n");
        htmlBuffer.append("<option value=\"\">字体</option>\n");
        htmlBuffer.append("<option value=\"宋体\">宋体</option>\n");
        htmlBuffer.append("<option value=\"黑体\">黑体</option>\n");
        htmlBuffer.append("<option value=\"楷体_GB2312\">楷体</option>\n");
        htmlBuffer.append("<option value=\"仿宋_GB2312\">仿宋</option>\n");
        htmlBuffer.append("<option value=\"隶书\">隶书</option>\n");
        htmlBuffer.append("<option value=\"幼圆\">幼圆</option>\n");
        htmlBuffer.append("<option value=\"新宋体\">新宋体</option>\n");
        htmlBuffer.append("<option value=\"细明体\">细明体</option>\n");
        htmlBuffer.append("<option value=\"Arial\">Arial</option>\n");
        htmlBuffer
                .append("<option value=\"Courier New\">Courier New</option>\n");
        htmlBuffer.append("<option value=\"Garamond\">Garamond</option>\n");
        htmlBuffer.append("<option value=\"Georgia\">Georgia</option>\n");
        htmlBuffer.append("<option value=\"Tahoma\">Tahoma</option>\n");
        htmlBuffer.append("<option value=\"Times New Roman\">Times</option>\n");
        htmlBuffer.append("<option value=\"Verdana\">Verdana</option>\n");
        htmlBuffer
                .append("</select></td><td style=\"padding-left: 4px;\" unselectable=\"on\"><select id=\"ctl00_bcr_FreeTextBox1_0_2\" tabindex=\"-1\">\n");
        htmlBuffer.append("<option value=\"\">字号</option>\n");
        htmlBuffer.append("<option value=\"1\">1</option>\n");
        htmlBuffer.append("<option value=\"2\">2</option>\n");
        htmlBuffer.append("<option value=\"3\">3</option>\n");
        htmlBuffer.append("<option value=\"4\">4</option>\n");
        htmlBuffer.append("<option value=\"5\">5</option>\n");
        htmlBuffer.append("<option value=\"6\">6</option>\n");
        htmlBuffer
                .append("</select></td><td style=\"padding-left: 4px;\" unselectable=\"on\"><select id=\"ctl00_bcr_FreeTextBox1_0_3\" tabindex=\"-1\">\n");
        htmlBuffer.append("<option value=\"\">颜色</option>\n");
        htmlBuffer
                .append("<option value=\"#000000\" style=\"background-color: Black; color: rgb(255, 255, 255);\">Black</option>\n");
        htmlBuffer
                .append("<option value=\"#808080\" style=\"background-color: Gray;\">Gray</option>\n");
        htmlBuffer
                .append("<option value=\"#A9A9A9\" style=\"background-color: DarkGray;\">DarkGray</option>\n");
        htmlBuffer
                .append("<option value=\"#D3D3D3\" style=\"background-color: LightGrey;\">LightGray</option>\n");
        htmlBuffer
                .append("<option value=\"#FFFFFF\" style=\"background-color: White;\">White</option>\n");
        htmlBuffer
                .append("<option value=\"#7FFFD4\" style=\"background-color: Aquamarine;\">Aquamarine</option>\n");
        htmlBuffer
                .append("<option value=\"#0000FF\" style=\"background-color: Blue;\">Blue</option>\n");
        htmlBuffer
                .append("<option value=\"#000080\" style=\"background-color: Navy; color: rgb(255, 255, 255);\">Navy</option>\n");
        htmlBuffer
                .append("<option value=\"#800080\" style=\"background-color: Purple; color: rgb(255, 255, 255);\">Purple</option>\n");
        htmlBuffer
                .append("<option value=\"#FF1493\" style=\"background-color: DeepPink;\">DeepPink</option>\n");
        htmlBuffer
                .append("<option value=\"#EE82EE\" style=\"background-color: Violet;\">Violet</option>\n");
        htmlBuffer
                .append("<option value=\"#FFC0CB\" style=\"background-color: Pink;\">Pink</option>\n");
        htmlBuffer
                .append("<option value=\"#006400\" style=\"background-color: DarkGreen; color: rgb(255, 255, 255);\">DarkGreen</option>\n");
        htmlBuffer
                .append("<option value=\"#008000\" style=\"background-color: Green; color: rgb(255, 255, 255);\">Green</option>\n");
        htmlBuffer
                .append("<option value=\"#9ACD32\" style=\"background-color: YellowGreen;\">YellowGreen</option>\n");
        htmlBuffer
                .append("<option value=\"#FFFF00\" style=\"background-color: Yellow;\">Yellow</option>\n");
        htmlBuffer
                .append("<option value=\"#FFA500\" style=\"background-color: Orange;\">Orange</option>\n");
        htmlBuffer
                .append("<option value=\"#FF0000\" style=\"background-color: Red;\">Red</option>\n");
        htmlBuffer
                .append("<option value=\"#A52A2A\" style=\"background-color: Brown;\">Brown</option>\n");
        htmlBuffer
                .append("<option value=\"#DEB887\" style=\"background-color: BurlyWood;\">BurlyWood</option>\n");
        htmlBuffer
                .append("<option value=\"#F5F5DC\" style=\"background-color: Beige;\">Beige</option>\n");
        htmlBuffer
                .append("</select></td></tr></tbody></table></td><td><img src=\""
                        + filePath
                        + "/WebResource_027.gif\" unselectable=\"on\" align=\"middle\" border=\"0\"></td></tr></tbody></table></div><div class=\"ctl00_bcr_FreeTextBox1_Toolbar\">\n");
        htmlBuffer
                .append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr><td border=\"0\" unselectable=\"on\"><img src=\""
                        + filePath
                        + "/WebResource_002.gif\" align=\"middle\"></td><td><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr><td id=\"ctl00_bcr_FreeTextBox1_1_0\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_010.gif\" title=\"加粗\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_1_1\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_019.gif\" title=\"斜体\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_1_2\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_015.gif\" title=\"下划线\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_1_3\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_013.gif\" title=\"删除线\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td><img src=\""
                        + filePath
                        + "/WebResource_020.gif\" unselectable=\"on\" border=\"0\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_1_5\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_018.gif\" title=\"上标\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_1_6\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_007.gif\" title=\"下标\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_1_7\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_003.gif\" title=\"删除所有格式\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("</tr></tbody></table></td><td><img src=\""
                        + filePath
                        + "/WebResource_027.gif\" unselectable=\"on\" align=\"middle\" border=\"0\"></td></tr></tbody></table></div><div class=\"ctl00_bcr_FreeTextBox1_Toolbar\">\n");
        htmlBuffer
                .append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr><td border=\"0\" unselectable=\"on\"><img src=\""
                        + filePath
                        + "/WebResource_002.gif\" align=\"middle\"></td><td><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr><td id=\"ctl00_bcr_FreeTextBox1_2_0\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_021.gif\" title=\"左对齐\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_2_1\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_014.gif\" title=\"右对齐\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_2_2\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_030.gif\" title=\"居中\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_2_3\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_009.gif\" title=\"两端对齐\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td><img src=\""
                        + filePath
                        + "/WebResource_020.gif\" unselectable=\"on\" border=\"0\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_2_5\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_026.gif\" title=\"项目符号\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_2_6\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_022.gif\" title=\"编号\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_2_7\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_011.gif\" title=\"增加缩进\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_2_8\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_005.gif\" title=\"减少缩进\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td><img src=\""
                        + filePath
                        + "/WebResource_020.gif\" unselectable=\"on\" border=\"0\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_2_10\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_012.gif\" title=\"插入链接\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_2_11\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_025.gif\" title=\"删除链接\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 0.25;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_2_12\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource.gif\" title=\"插入图片\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_2_50\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_050.gif\" title=\"插入图释\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_2_51\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_051.gif\" title=\"插入Flash\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_2_52\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_052.gif\" title=\"插入Media\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_2_13\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_023.gif\" title=\"插入水平线\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("</tr></tbody></table></td><td><img src=\""
                        + filePath
                        + "/WebResource_027.gif\" unselectable=\"on\" align=\"middle\" border=\"0\"></td></tr></tbody></table></div><div class=\"ctl00_bcr_FreeTextBox1_Toolbar\">\n");
        htmlBuffer
                .append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr><td border=\"0\" unselectable=\"on\"><img src=\""
                        + filePath
                        + "/WebResource_002.gif\" align=\"middle\"></td><td><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr><td id=\"ctl00_bcr_FreeTextBox1_3_0\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_006.gif\" title=\"剪切\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_3_1\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_029.gif\" title=\"复制\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_3_2\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_016.gif\" title=\"粘贴\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td><img src=\""
                        + filePath
                        + "/WebResource_020.gif\" unselectable=\"on\" border=\"0\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_3_4\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_024.gif\" title=\"撤销\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_3_5\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_004.gif\" title=\"重复\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("<td id=\"ctl00_bcr_FreeTextBox1_3_6\" class=\"ctl00_bcr_FreeTextBox1_Button_Off_Out\">\n");
        htmlBuffer
                .append("<img src=\""
                        + filePath
                        + "/WebResource_028.gif\" title=\"打印\" unselectable=\"on\" tabindex=\"-1\" style=\"margin: 0px; padding: 0px; opacity: 1;\" align=\"middle\" border=\"0\" height=\"20\" width=\"21\"></td>\n");
        htmlBuffer
                .append("</tr></tbody></table></td><td><img src=\""
                        + filePath
                        + "/WebResource_027.gif\" unselectable=\"on\" align=\"middle\" border=\"0\"></td></tr></tbody></table></div></div>\n");
        htmlBuffer
                .append(" <div id=\"ctl00_bcr_FreeTextBox1_designEditorArea\" style=\"clear: both; padding-top: 1px;\">\n");
        htmlBuffer
                .append("     <iframe id=\"ctl00_bcr_FreeTextBox1_designEditor\" style=\"padding: 0px; width: 600px; height: 350px;\" src=\"about:blank\" class=\"ctl00_bcr_FreeTextBox1_DesignBox\"></iframe>\n");
        htmlBuffer.append(" </div>\n");
        htmlBuffer
                .append(" <div id=\"ctl00_bcr_FreeTextBox1_htmlEditorArea\" style=\"clear: both; display: none; padding-bottom: 1px;\">\n");
        htmlBuffer
                .append("     <textarea id=\"ctl00_bcr_FreeTextBox1\" name=\"ctl00_bcr_FreeTextBox1\" style=\"padding: 0px; width: 600px; height: 350px;\" class=\"ctl00_bcr_FreeTextBox1_HtmlBox\">");
        htmlBuffer.append(content);
        htmlBuffer.append("</textarea>\n");
        htmlBuffer.append(" </div>\n");
        htmlBuffer
                .append(" <div id=\"ctl00_bcr_FreeTextBox1_previewPaneArea\" style=\"clear: both; display: none; padding-bottom: 1px;\">\n");
        htmlBuffer
                .append("     <iframe id=\"ctl00_bcr_FreeTextBox1_previewPane\" style=\"padding: 0px; width: 600px; height: 350px;\" src=\"about:blank\" class=\"ctl00_bcr_FreeTextBox1_DesignBox\"></iframe>\n");
        htmlBuffer.append(" </div>\n");
        htmlBuffer.append("<div style=\"clear: both; padding-top: 2px;\">\n");
        htmlBuffer
                .append(" <table style=\"border-collapse: collapse;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
        htmlBuffer
                .append("     <tbody><tr id=\"ctl00_bcr_FreeTextBox1_TabRow\">\n");
        htmlBuffer
                .append("         <td class=\"ctl00_bcr_FreeTextBox1_StartTabOn\">&nbsp;\n");
        htmlBuffer.append("             \n");
        htmlBuffer.append("         </td>\n");
        htmlBuffer
                .append("         <td class=\"ctl00_bcr_FreeTextBox1_TabOn\" id=\"ctl00_bcr_FreeTextBox1_designModeTab\" unselectable=\"on\">\n");
        htmlBuffer
                .append("             <nobr><img unselectable=\"on\" src=\""
                        + filePath
                        + "/WebResource_008.gif\" align=\"absmiddle\" height=\"20\" width=\"21\">&nbsp;设计</nobr>\n");
        htmlBuffer.append("         </td>\n");
        htmlBuffer
                .append("         <td class=\"ctl00_bcr_FreeTextBox1_TabOffRight\" id=\"ctl00_bcr_FreeTextBox1_htmlModeTab\" unselectable=\"on\">\n");
        htmlBuffer
                .append("             <nobr><img unselectable=\"on\" src=\""
                        + filePath
                        + "/WebResource_017.gif\" align=\"absmiddle\" height=\"20\" width=\"21\">&nbsp;HTML</nobr>\n");
        htmlBuffer.append("         </td>\n");
        htmlBuffer
                .append("         <td class=\"ctl00_bcr_FreeTextBox1_EndTab\">\n");
        htmlBuffer
                .append("             <div id=\"ctl00_bcr_FreeTextBox1_AncestorArea\" class=\"ctl00_bcr_FreeTextBox1_AncestorArea\" style=\"display:none;\"></div>\n");
        htmlBuffer.append("         </td>\n");
        htmlBuffer.append("     </tr>\n");
        htmlBuffer.append(" </tbody></table>\n");
        htmlBuffer.append(" \n");
        htmlBuffer.append("</div>\n");
        htmlBuffer.append("</div>\n");
        htmlBuffer.append("</td></tr></tbody></table>\n");
        htmlBuffer.append("<script language=\"JavaScript\">\n");
        htmlBuffer.append("if (window.FTB_AddEvent) { \n");
        htmlBuffer.append(" FTB_AddEvent(window,'load',function () {\n");
        htmlBuffer.append("     FTB_Names.push('ctl00_bcr_FreeTextBox1');\n");
        htmlBuffer
                .append("        FTB_API['ctl00_bcr_FreeTextBox1'] = new FTB_FreeTextBox('ctl00_bcr_FreeTextBox1',\n");
        htmlBuffer.append("                 true,\n");
        htmlBuffer.append("                 false,\n");
        htmlBuffer.append("                 new Array(\n");
        htmlBuffer
                .append("                     new FTB_Button('ctl00_bcr_FreeTextBox1_1_0','bold',null,null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_1_1','italic',null,null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_1_2','underline',null,null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_1_3','strikethrough',null,null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_1_5','superscript',null,null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_1_6','subscript',null,null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_1_7','removeformat',null,null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_2_0','justifyleft',null,null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_2_1','justifyright',null,null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_2_2','justifycenter',null,null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_2_3','justifyfull',null,null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_2_5','insertunorderedlist',null,null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_2_6','insertorderedlist',null,null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_2_7','indent',null,null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_2_8','outdent',null,null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_2_10','createlink',function() { this.ftb.CreateLink(); },null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_2_11','unlink',null,null,false,function() { this.disabled = !(this.ftb.GetParentElement().tagName.toLowerCase() == 'a') }),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_2_12','insertimage',function() { this.ftb.InsertImage(); },null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_2_50','insertimage',function() { this.ftb.InsertEmote(); },null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_2_51','insertflash',function() { this.ftb.InsertFlash(); },null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_2_52','insertmedia',function() { this.ftb.InsertMedia(); },null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_2_13','inserthorizontalrule',null,null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_3_0','cut',function() { this.ftb.Cut(); },null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_3_1','copy',function() { this.ftb.Copy(); },null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_3_2','paste',function() { this.ftb.Paste(); },null,false,null),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_3_4','undo',function() { this.ftb.Undo(); },null,false,function() { this.disabled=!this.ftb.CanUndo(); }),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_3_5','redo',function() { this.ftb.Redo(); },null,false,function() { this.disabled=!this.ftb.CanRedo(); }),\n");
        htmlBuffer
                .append("         new FTB_Button('ctl00_bcr_FreeTextBox1_3_6','print',function() { this.ftb.Print(); },null,false,null)\n");
        htmlBuffer.append("                 ),\n");
        htmlBuffer.append("                 new Array(\n");
        htmlBuffer
                .append("                     new FTB_DropDownList('ctl00_bcr_FreeTextBox1_0_0','formatBlock',null,null,null),\n");
        htmlBuffer
                .append("         new FTB_DropDownList('ctl00_bcr_FreeTextBox1_0_1','fontname',null,null,null),\n");
        htmlBuffer
                .append("         new FTB_DropDownList('ctl00_bcr_FreeTextBox1_0_2','fontsize',null,null,null),\n");
        htmlBuffer
                .append("         new FTB_DropDownList('ctl00_bcr_FreeTextBox1_0_3','forecolor',null,null,null)\n");
        htmlBuffer.append("                 ),              \n");
        htmlBuffer.append("                 FTB_BREAK_P,\n");
        htmlBuffer.append("                 FTB_PASTE_DEFAULT,\n");
        htmlBuffer.append("                 FTB_TAB_INSERTSPACES,\n");
        htmlBuffer
                .append("                 FTB_MODE_DESIGN,                                    \n");
        htmlBuffer.append("                 null,\n");
        htmlBuffer.append("                 '"
                + StringUtils.ignoreNull(designModeCss) + "',\n");
        htmlBuffer.append("                 '"
                + StringUtils.ignoreNull(designModeBodyTagCssClass) + "',\n");
        htmlBuffer.append("                 '',\n");
        htmlBuffer.append("                 '',\n");
        htmlBuffer.append("                 '',\n");
        htmlBuffer
                .append("                 'ftb.imagegallery.aspx?rif={0}&cif={0}',\n");
        htmlBuffer.append("                 '~/images/',\n");
        htmlBuffer.append("                 false,\n");
        htmlBuffer.append("                 21,\n");
        htmlBuffer.append("                 20\n");
        htmlBuffer.append("                 \n");
        htmlBuffer.append("             );\n");
        htmlBuffer.append("         });\n");
        htmlBuffer
                .append(" //FTB_API['ctl00_bcr_FreeTextBox1'].Initialize();\n");
        htmlBuffer.append("} else {\n");
        htmlBuffer
                .append("ed = document.getElementById('ctl00_bcr_FreeTextBox1_designEditor').contentWindow;ed.document.open(); ed.document.write('FreeTextBox has not been correctly installed. To install FreeTextBox either:<br> (1) add a reference to FtbWebResource.axd in web.config:<br>&lt;system.web&gt;<br>&lt;httpHandlers&gt;<br>&lt;add verb=&quot;GET&quot;<br>path=&quot;FtbWebResource.axd&quot;<br>type=&quot;FreeTextBoxControls.AssemblyResourceHandler, FreeTextBox&quot; /&gt;<br>&lt;/httpHandlers&gt;<br>&lt;/system.web&gt;<br><br>(2) Save the FreeTextBox image and javascript files to a location on your website and set up FreeTextBox as follows <br>&lt;FTB:FreeTextBox id=&quot;FreeTextBox1&quot; SupportFolder=&quot;ftbfileslocation&quot; JavaScriptLocation=&quot;ExternalFile&quot; ButtonImagesLocation=&quot;ExternalFile&quot; ToolbarImagesLocation=&quot;ExternalFile&quot; ButtonImagesLocation=&quot;ExternalFile&quot; runat=&quot;server&quot; /&gt;');ed.document.close();\n");
        htmlBuffer.append("}\n");
        htmlBuffer.append("</script>\n");
        html = htmlBuffer.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return html;
    }

}
