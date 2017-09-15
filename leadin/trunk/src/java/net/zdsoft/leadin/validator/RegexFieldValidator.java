/* 
 * @(#)RegexFieldValidator.java    Created on Dec 7, 2006
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44cvsroot/stusys/stusys/src/net/zdsoft/validator/RegexFieldValidator.java,v 1.3 2006/12/11 09:11:35 linqz Exp $
 */
package net.zdsoft.leadin.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

/**
 * @author Linqz
 * @version $Revision: 1.3 $, $Date: 2006/12/11 09:11:35 $
 */

public class RegexFieldValidator extends FieldValidatorSupport {
    private String expression = "";
    private boolean trim = false;
    private boolean notMatch = false;

    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object value = this.getFieldValue(fieldName, object);
        if (value == null || "".equals(value))
            return;
        String val;
        try {
            val = String.valueOf(value);
            if (true == trim) {
                val = val.trim();
            }

            Pattern p = Pattern.compile(expression);
            Matcher m = p.matcher(val);

            if (false == notMatch) {
                if (!m.find()) {
                    addFieldError(fieldName, object);
                    return;
                }
            }
            else {
                if (m.find()) {
                    addFieldError(fieldName, object);
                    return;
                }
            }
        }
        catch (Exception e) {
            defaultMessage = "输入内容格式不对，请重输！";
            addFieldError(fieldName, object);
            return;
        }
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public boolean isNotMatch() {
        return notMatch;
    }

    public void setNotMatch(boolean notMatch) {
        this.notMatch = notMatch;
    }

    public boolean isTrim() {
        return trim;
    }

    public void setTrim(boolean trim) {
        this.trim = trim;
    }

}
