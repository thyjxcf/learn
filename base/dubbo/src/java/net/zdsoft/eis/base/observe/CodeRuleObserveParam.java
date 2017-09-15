/* 
 * @(#)CodeRuleObserveParam.java    Created on Aug 17, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.observe;


/**
 * 号码规则参数
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 29, 2009 1:22:56 PM $
 */
public class CodeRuleObserveParam {
    public static final int CODETYPE_ALL = 0;

    private int codeType;

    private String unitId;
    private String section;

    public CodeRuleObserveParam(int codeType) {
        super();
        this.codeType = codeType;
    }

    public CodeRuleObserveParam(int codeType, String unitId) {
        super();
        this.codeType = codeType;
        this.unitId = unitId;
    }

    public CodeRuleObserveParam(int codeType, String unitId, String section) {
        super();
        this.codeType = codeType;
        this.unitId = unitId;
        this.section = section;
    }

    public int getCodeType() {
        return codeType;
    }

    public String getUnitId() {
        return unitId;
    }

    public String getSection() {
        return section;
    }

}
