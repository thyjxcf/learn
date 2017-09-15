/* 
 * @(#)RealStringLengthFieldValidator.java    Created on Dec 7, 2006
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44cvsroot/stusys/stusys/src/net/zdsoft/validator/RealStringLengthFieldValidator.java,v 1.3 2006/12/15 08:42:47 linqz Exp $
 */
package net.zdsoft.leadin.validator;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

/**
 * @author Linqz
 * @version $Revision: 1.3 $, $Date: 2006/12/15 08:42:47 $
 */
public class RealStringLengthFieldValidator extends FieldValidatorSupport {

    // ~ Instance fields
    // ////////////////////////////////////////////////////////

    private boolean doTrim = true;
    private int maxLength = -1;
    private int minLength = -1;
    private int maxLengthCN = -1;
    private int minLengthCN = -1;

    // ~ Methods
    // ////////////////////////////////////////////////////////////////

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setTrim(boolean trim) {
        doTrim = trim;
    }

    public boolean getTrim() {
        return doTrim;
    }

    public int getMaxLengthCN() {
        return maxLengthCN;
    }

    public void setMaxLengthCN(int maxLengthCN) {
        this.maxLengthCN = maxLengthCN;
    }

    public int getMinLengthCN() {
        return minLengthCN;
    }

    public void setMinLengthCN(int minLengthCN) {
        this.minLengthCN = minLengthCN;
    }

    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        String val = (String) getFieldValue(fieldName, object);

        if (val == null) {
            addFieldError(fieldName, object);
        }
        else {
            if (doTrim) {
                val = val.trim();
            }

            if ((minLength > -1) && (getRealLength(val) < minLength)) {
                addFieldError(fieldName, object);
            }
            else if ((maxLength > -1) && (getRealLength(val) > maxLength)) {
                addFieldError(fieldName, object);
            }
            else if ((minLengthCN > -1) && (getRealLength(val) < minLengthCN)) {
                addFieldError(fieldName, object);
            }
            else if ((maxLengthCN > -1) && (getRealLength(val) > (maxLengthCN * 2))){
                addFieldError(fieldName, object);
            }
        }
    }

    private int getRealLength(String str) {
        if (str == null) {
            return 0;
        }

        char separator = 256;
        int realLength = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= separator) {
                realLength += 2;
            }
            else {
                realLength++;
            }
        }
        return realLength;
    }
}
