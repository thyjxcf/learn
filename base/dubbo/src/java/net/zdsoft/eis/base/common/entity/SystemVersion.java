package net.zdsoft.eis.base.common.entity;

import java.util.Date;

import net.zdsoft.keelcnet.entity.EntityObject;

public class SystemVersion extends EntityObject {

    private static final long serialVersionUID = 4443941813155345656L;

    public static final String PRODUCT_EIS = "EIS"; // 中小学版
    public static final String PRODUCT_EISU = "EISU"; // 高职版
    public static final String PRODUCT_CONNECTOR = "-"; // 城域综合信息平台标志

    public static final String PRODUCT_EIS_E = PRODUCT_EIS + PRODUCT_CONNECTOR + "E"; // 城域综合信息平台标志
    public static final String PRODUCT_EIS_S = PRODUCT_EIS + PRODUCT_CONNECTOR + "S"; // 校校通软件平台标志
    public static final String PRODUCT_EISU_E = PRODUCT_EISU + PRODUCT_CONNECTOR + "E"; // 高职版城域综合信息平台标志
    public static final String PRODUCT_EISU_S = PRODUCT_EISU + PRODUCT_CONNECTOR + "S"; // 高职版校校通软件平台标志

    private String name;
    private String description;
    private Date createdate;
    private String curversion;
    private String build;
    private String using;
    private String productId;

    // ==================辅助字段 无==============

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUsing() {
        return using;
    }

    public void setUsing(String using) {
        this.using = using;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedate() {
        return this.createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getCurversion() {
        return this.curversion;
    }

    public void setCurversion(String curversion) {
        this.curversion = curversion;
    }
}
